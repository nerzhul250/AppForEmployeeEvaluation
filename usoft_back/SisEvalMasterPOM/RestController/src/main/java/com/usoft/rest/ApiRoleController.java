package com.usoft.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.usoft.dtos.RoleDTO;
import com.usoft.dtos.UserDTO;
import com.usoft.model.Role;
import com.usoft.model.State;
import com.usoft.model.Usuario;
import com.usoft.service.RoleServiceInt;
import com.usoft.service.StateServiceInt;

@CrossOrigin(origins = {"*"}, methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE})
@RestController
@RequestMapping("/usoftapi/role")
public class ApiRoleController implements ApiRoleControllerInt {
	@Autowired
	private RoleServiceInt roleService;

	@Override
	@PostMapping("")
	public String save(@RequestBody RoleDTO role) {
		Role r=new Role();
		r.setDescription(role.getDescription());
		r.setName(role.getName());
		r.setIsdeleted(BigDecimal.ZERO);
		System.out.println(r.getName());
		if(roleService.save(r)) {
			return "true";
		}else {
			return "false";
		}
	}

	@Override
	@PatchMapping("")
	public String update(@RequestBody RoleDTO role) {
		
		Role roleUpdate;
		boolean result = false;
		
		try {
			
			roleUpdate = roleService.find(role.getId());
			roleUpdate.setName(role.getName());
			roleUpdate.setDescription(role.getDescription());
			
			result = roleService.update(roleUpdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result) {
			return "true";
		}else {
			return "false";
		}
	}

	@Override
	@GetMapping("")
	public Iterable<RoleDTO> findAll() {
		Iterable<Role> allRoles = roleService.findAll();
		List<RoleDTO> allRolesDTO = new ArrayList<RoleDTO>();
		Iterator<Role> roleIterator = allRoles.iterator();
		
		while(roleIterator.hasNext()) {
			Role role = roleIterator.next();
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setId(role.getId());
			roleDTO.setName(role.getName());
			roleDTO.setDescription(role.getDescription());
			
			
			allRolesDTO.add(roleDTO);
		}
		
		return allRolesDTO;
	}

	@Override
	@GetMapping("/{id}")
	public RoleDTO find(@PathVariable Long id) {
		try {
			Role role = roleService.find(id);
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setId(role.getId());
			roleDTO.setName(role.getName());
			roleDTO.setDescription(role.getDescription());
			return roleDTO;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * This method responses the api request by deleteMapping, to remove an
	 * role
	 */
	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		System.out.println(id);
		try {
			roleService.delete(id);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.noContent().build();
	}

}
