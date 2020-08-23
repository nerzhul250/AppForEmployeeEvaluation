package com.usoft.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.usoft.dtos.AreaDTO;

import com.usoft.dtos.IndicatorDTO;
import com.usoft.dtos.PositionDTO;
import com.usoft.dtos.RoleDTO;
import com.usoft.dtos.UserDTO;
import com.usoft.model.Area;
import com.usoft.model.Indicator;
import com.usoft.model.Position;
import com.usoft.model.Positionuser;
import com.usoft.model.Role;
import com.usoft.model.Userrole;
import com.usoft.model.UserrolePK;
import com.usoft.model.Usuario;
import com.usoft.service.AreaService;
import com.usoft.service.AreaServiceInt;
import com.usoft.service.PositionServiceInt;
import com.usoft.service.PositionUserServiceInt;
import com.usoft.service.RoleServiceInt;
import com.usoft.service.UserServiceInt;
import com.usoft.service.UserroleServiceInt;

@CrossOrigin(origins = { "*" }, methods = { RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST,
		RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE })
@RestController
@RequestMapping("/usoftapi/users")
public class ApiUserController implements ApiUserControllerInt {

	@Autowired
	private UserServiceInt userService;
	@Autowired
	private RoleServiceInt roleService;
	@Autowired
	private UserroleServiceInt userroleService;
	@Autowired
	private AreaServiceInt areaService;
	@Autowired
	private PositionServiceInt positionService;
	@Autowired
	private PositionUserServiceInt positionUserService;
	@Autowired
	private PasswordEncoder encoder;

	@Override
	@PostMapping("")
	public String createUser(@RequestBody UserDTO user) {
		Usuario us = new Usuario();
		us.setArea(areaService.findArea(user.getArea().getId()));
		us.setBirthday(user.getBirthday());
		us.setEmail(user.getEmail());
		us.setFirstName(user.getFirstName());
		us.setLastName(user.getLastName());
		us.setIsdeleted(BigDecimal.ZERO);
		us.setPhone(user.getPhone());
		us.setPassword(encoder.encode(user.getPassword()));
		boolean result = true;
		try {
			result = userService.createUser(us);
			if (!result)
				throw new Exception("Condiciones de creacion de usuario violadas");
			Role role = roleService.find(user.getRole().getId());
			Userrole usrole = new Userrole();
			usrole.setIsdeleted(BigDecimal.ZERO);
			usrole.setRole(role);
			usrole.setFecha(Calendar.getInstance().getTime());
			usrole.setRoleId(role.getId());
			usrole.setUserId(us.getId());
			usrole.setUsuario(us);
			userroleService.createUserrole(usrole);
			Position posi = positionService.findPosition(user.getPosition().getId());
			Positionuser posUsr = new Positionuser();
			posUsr.setFecha(Calendar.getInstance().getTime());
			posUsr.setIsdeleted(BigDecimal.ZERO);
			posUsr.setPosition(posi);
			posUsr.setUsuario(us);
			positionUserService.createPositionUser(posUsr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// uy fallaaa
			result = false;
		}
		if (result) {
			return "true";
		} else {
			return "false";
		}
	}

	/**
	 * This method exposes a GET rest service which purpose is to return all the
	 * registered users
	 */
	@Override
	@GetMapping("")
	public Iterable<UserDTO> findAllUsers() {
		Iterable<Usuario> allUsers = userService.findAllUsers();
		List<UserDTO> allUsersDTO = new ArrayList<UserDTO>();
		Iterator<Usuario> userIterator = allUsers.iterator();

		while (userIterator.hasNext()) {

			Usuario usu = userIterator.next();

			if (usu.getPositionusers() != null && usu.getUserroles() != null && usu.getArea() != null) {
				if (usu.getPositionusers().size() > 0 && usu.getUserroles().size() > 0) {

					UserDTO usuDTO = new UserDTO();
					usuDTO.setId(usu.getId());
					usuDTO.setBirthday(usu.getBirthday());
					usuDTO.setEmail(usu.getEmail());
					usuDTO.setFirstName(usu.getFirstName());
					usuDTO.setLastName(usu.getLastName());
					usuDTO.setPhone(usu.getPhone());

					RoleDTO role = new RoleDTO();
					Role actualRole = null;
					try {
						actualRole = roleService.find(usu.getUserroles().get(0).getRoleId());
						
						for(int i = 0; i < usu.getUserroles().size() - 1; i++) {
							if(usu.getUserroles().get(i).getFecha().after(usu.getUserroles().get(i + 1).getFecha())) {
								actualRole = roleService.find(usu.getUserroles().get(i).getRoleId());
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (actualRole != null) {
						role.setId(actualRole.getId());
						role.setName(actualRole.getName());
						role.setDescription(actualRole.getDescription());
						usuDTO.setRole(role);
					}

					PositionDTO position = new PositionDTO();
					Position actualPosition = null;
					actualPosition = usu.getPositionusers().get(0).getPosition();

					if (actualPosition != null) {
						position.setId(actualPosition.getId());
						position.setName(actualPosition.getName());
						position.setDescription(actualPosition.getDescription());
						usuDTO.setPosition(position);
					}

					if (usu.getArea() != null) {
						AreaDTO area = new AreaDTO();
						area.setId(usu.getArea().getId());
						area.setName(usu.getArea().getName());
						area.setDescription(usu.getArea().getDescription());
						usuDTO.setArea(area);
					}

					allUsersDTO.add(usuDTO);

				} else {
					// System.out.println(usu.getId());
				}
			}

		}

		return allUsersDTO;
	}

	/**
	 * This method exposes a GET rest service which purpose is to return a specified
	 * (by its id) user
	 */
	@Override
	@GetMapping("/{id}")
	public UserDTO findUser(@PathVariable Integer id) {
		try {
			Usuario user = userService.findUser(id);
			UserDTO usuDTO = new UserDTO();

			if (user.getPositionusers() != null && user.getUserroles() != null && user.getArea() != null) {
				usuDTO.setId(user.getId());
				usuDTO.setBirthday(user.getBirthday());
				usuDTO.setEmail(user.getEmail());
				usuDTO.setPhone(user.getPhone());
				usuDTO.setFirstName(user.getFirstName());
				usuDTO.setLastName(user.getLastName());

				RoleDTO role = new RoleDTO();
				Role actualRole = null;
				try {
					actualRole = roleService.find(user.getUserroles().get(0).getRoleId());
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (actualRole != null) {
					role.setId(actualRole.getId());
					role.setName(actualRole.getName());
					role.setDescription(actualRole.getDescription());
					usuDTO.setRole(role);
				}

				PositionDTO position = new PositionDTO();
				Position actualPosition = null;
				actualPosition = user.getPositionusers().get(0).getPosition();

				if (actualPosition != null) {
					position.setId(actualPosition.getId());
					position.setName(actualPosition.getName());
					position.setDescription(actualPosition.getDescription());
					usuDTO.setPosition(position);
				}

				if (user.getArea() != null) {
					AreaDTO area = new AreaDTO();
					area.setId(user.getArea().getId());
					area.setName(user.getArea().getName());
					area.setDescription(user.getArea().getDescription());
					usuDTO.setArea(area);
				}

			}

			return usuDTO;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * This method improve the method DELETE from the request, to do a logic remove
	 * of a user indicated by id Then return and responseEntity indicates the
	 * success or a bad request
	 */
	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		try {
//			System.out.println("ENTROOOOO");
//			System.out.println(id);
			userService.deleteUser(id);
//			System.out.println("SALIO");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.noContent().build();
	}

	@Override
	@PatchMapping("/profile")
	public String updateProfile(@RequestBody UserDTO user) {

		Usuario updateUser;
		boolean result = false;
		try {

			updateUser = userService.findUser((int) user.getId());

			updateUser.setFirstName(user.getFirstName());
			updateUser.setLastName(user.getLastName());
			updateUser.setEmail(user.getEmail());
			updateUser.setPhone(user.getPhone());
			updateUser.setBirthday(user.getBirthday());
			
			
			if(updateUser.getUserroles().get(0).getRole().getName().equals("ROLE_ADMIN")) {
				updateUser.setArea(areaService.findArea(user.getArea().getId()));
				Position pos = positionService.findPosition(user.getPosition().getId());
				Positionuser posU = updateUser.getPositionusers().get(0);
				posU.setPosition(pos);
				ArrayList<Positionuser> posUList = new ArrayList<Positionuser>();
				posUList.add(posU);
				updateUser.setPositionusers(posUList);
				//System.out.println(updateUser.getArea().getName());
				//System.out.println(pos.getName());
				
				Role role = roleService.find(user.getRole().getId());
				//System.out.println(role.getName());
				
				UserrolePK urpk = new UserrolePK();
				urpk.setUserId(user.getId());
				urpk.setRoleId(user.getRole().getId());
				if(userroleService.findUserrole(urpk) == null) {
					//No encontró el userRole, hay que crear uno nuevo
					//System.out.println("No se encontró, toca crear uno nuevo");
					
					Userrole usrole = new Userrole();
					usrole.setIsdeleted(BigDecimal.ZERO);
					usrole.setRole(role);
					usrole.setFecha(Calendar.getInstance().getTime());
					usrole.setRoleId(role.getId());
					usrole.setUserId(user.getId());
					usrole.setUsuario(updateUser);
					userroleService.createUserrole(usrole);
					
					List<Userrole> arruserrole = updateUser.getUserroles();
					arruserrole.add(usrole);
					updateUser.setUserroles(arruserrole);
					//System.out.println(updateUser.getUserroles().get(0).getRole().getName());
				}else {
					//Sigue como si nada
					//System.out.println("Se encontró, no se hace nada");
				}
			}
			
			
			
			result = userService.updateUser(updateUser);
			

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (result) {
			return "true";
		} else {
			return "false";
		}

	}

	@Override
	@PatchMapping("")
	public String updateUser(@RequestBody UserDTO user) {

		Usuario updateUser;
		boolean result = false;
		try {

			updateUser = userService.findUser((int) user.getId());

			updateUser.setFirstName(user.getFirstName());
			updateUser.setLastName(user.getLastName());
			updateUser.setEmail(user.getEmail());
			updateUser.setPhone(user.getPhone());
			updateUser.setBirthday(user.getBirthday());
			
			
				updateUser.setArea(areaService.findArea(user.getArea().getId()));
				Position pos = positionService.findPosition(user.getPosition().getId());
				Positionuser posU = updateUser.getPositionusers().get(0);
				posU.setPosition(pos);
				ArrayList<Positionuser> posUList = new ArrayList<Positionuser>();
				posUList.add(posU);
				updateUser.setPositionusers(posUList);
				//System.out.println(updateUser.getArea().getName());
				//System.out.println(pos.getName());
				
				Role role = roleService.find(user.getRole().getId());
				//System.out.println(role.getName());
				
				//Tengo que ver si el rol que le llega es el mismo rol que tenia antes
				UserrolePK urpk = new UserrolePK();
				urpk.setUserId(user.getId());
				urpk.setRoleId(user.getRole().getId());
				if(userroleService.findUserrole(urpk) == null) {
					//No encontró el userRole, hay que crear uno nuevo
					//System.out.println("No se encontró, toca crear uno nuevo");
					
					Userrole usrole = new Userrole();
					usrole.setIsdeleted(BigDecimal.ZERO);
					usrole.setRole(role);
					usrole.setFecha(Calendar.getInstance().getTime());
					usrole.setRoleId(role.getId());
					usrole.setUserId(user.getId());
					usrole.setUsuario(updateUser);
					userroleService.createUserrole(usrole);
					
					
					List<Userrole> arruserrol = updateUser.getUserroles();
					arruserrol.add(usrole);
					
					/*
					List<Userrole> arruserrole = new ArrayList<>();
					arruserrole = updateUser.getUserroles();
					//arruserrole.add(usrole);
					
					List<Userrole> resArruserrole = new ArrayList<>();
					for(int i = 0; i < arruserrole.size(); i++) {
						resArruserrole.add(arruserrole.get(i));
					}
					resArruserrole.add(usrole);
					
					//arruserrole.add(arruserrole.size(), usrole);
					System.out.println(resArruserrole.get(0).getRole().getName());
					System.out.println(resArruserrole.get(1).getRole().getName());
					*/
					updateUser.setUserroles(arruserrol);
					
					//System.out.println(updateUser.getUserroles().get(0).getRole().getName());
					//System.out.println(updateUser.getUserroles().get(1).getRole().getName());
				}else {
					//Sigue como si nada
					//System.out.println("Se encontró, no se hace nada");
				}
				
				//updateUser.set
			
			
			result = userService.updateUser(updateUser);

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (result) {
			return "true";
		} else {
			return "false";
		}

	}

	/**
	 * This method response to a post method, that it is improve to send a email
	 * invitation by emails
	 * 
	 * @param data, they recive the data, this indicate the emails to, the receives,
	 *              and here in this method allocated the credentials of the email
	 *              sender
	 * @return a response entity where are indicates the sucess of the request
	 */
	@RequestMapping(value = "/massiveUsers", method = RequestMethod.POST)
	public ResponseEntity<?> createMassiveUsers(@Valid @RequestBody JsonNode data) {
		System.out.println(data);
		int numToCreate = data.get("numToCreate").asInt();
		String from = data.get("from").asText();
		try {
			for (int i = 0; i < numToCreate; i++) {
				Usuario us = new Usuario();
				us.setArea(areaService.findArea(1));
				us.setBirthday(new Date());
				us.setEmail(data.get("email_" + i).asText());
				us.setFirstName(data.get("email_" + i).asText());
				us.setLastName("00000000");
				us.setIsdeleted(BigDecimal.ZERO);
				us.setPhone("00000000");
				us.setPassword(encoder.encode("5f4dcc3b5aa765d61d8327deb882cf99"));
				boolean result = true;
				try {
					result = userService.createUser(us);
					if (!result)
						throw new Exception("Condiciones de creacion de usuario violadas");
					Role role = roleService.find((long) 3);
					Userrole usrole = new Userrole();
					usrole.setIsdeleted(BigDecimal.ZERO);
					usrole.setRole(role);
					usrole.setFecha(Calendar.getInstance().getTime());
					usrole.setRoleId(role.getId());
					usrole.setUserId(us.getId());
					usrole.setUsuario(us);
					userroleService.createUserrole(usrole);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					result = false;
				}
				userService.sendEmail("emailsendUsoft@gmail.com", data.get("email_" + i).asText(),
						"emailsendUsoft@gmail.com", "usoft2020");
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error al enviar el email");
		}
		return ResponseEntity.ok().body(data);
	}
}