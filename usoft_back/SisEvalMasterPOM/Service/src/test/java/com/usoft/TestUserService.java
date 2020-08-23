package com.usoft;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.usoft.model.Area;
import com.usoft.model.State;
import com.usoft.model.Usuario;
import com.usoft.repo.UserRepository;
import com.usoft.service.UserService;


class TestUserService {
	
	@Mock
	private UserRepository repo;

	@InjectMocks
	private UserService userService;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void createUserSuccess() {
		Usuario user=new Usuario();
		user.setArea(new Area());
		user.setBirthday(Date.valueOf("1999-6-2"));
		user.setEmail("sada@hotmail.com");
		user.setFirstName("sara");
		user.setLastName("ortis");
		user.setPhone("3116953284");
		user.setIsdeleted(BigDecimal.ZERO);
		assertTrue(userService.createUser(user));
	}
	@Test
	public void createUserFailure() {
		Usuario user=new Usuario();
		user.setArea(new Area());
		user.setBirthday(Date.valueOf("1999-6-2"));
		user.setEmail("sada@@hotmail.com");//invalid
		user.setFirstName("sara");
		user.setLastName("ortis");
		user.setPhone("311 6953 284");//invalid
		user.setIsdeleted(BigDecimal.ZERO);
		assertTrue(!userService.createUser(user));
	}
	
	@Test
	public void updateUserSuccess() {
		Usuario user=new Usuario();
		user.setArea(new Area());
		user.setBirthday(Date.valueOf("1999-6-2"));
		user.setEmail("beycker@hotmail.com");
		user.setFirstName("Beycker");
		user.setLastName("Agredo");
		user.setPhone("3116953284");
		user.setIsdeleted(BigDecimal.ZERO);
		assertTrue(userService.updateUser(user));
	}
	
	@Test
	public void updateUserFailure() {
		Usuario user=new Usuario();
		user.setArea(new Area());
		user.setBirthday(Date.valueOf("1999-6-2"));
		user.setEmail("beycker@@hotmail.com");//invalid
		user.setFirstName("Becyker");
		user.setLastName("Agredo");
		user.setPhone("311 695 3284");//invalid
		user.setIsdeleted(BigDecimal.ZERO);
		assertTrue(!userService.updateUser(user));
	}

	@Test
	public void findAllUsers() {
		Usuario usuario = new Usuario();
		usuario.setId(14);
		ArrayList<Usuario> test= new ArrayList<>();
		test.add(usuario);
		when(repo.findAll()).thenReturn(test);
		Iterable<Usuario> allUsers=userService.findAllUsers();
		assertTrue(allUsers==test);
	}

	@Test
	public void findUserFailure() {
		Usuario usuario= new Usuario();
		usuario.setId(12);
		String original = "No user registred with id: 12";
		when(repo.find(12)).thenThrow(new RuntimeException("No user registred with id: 12"));
		String message = "";
		try {
			Usuario usu = userService.findUser(12);
		} catch (Exception e) {
			message = e.getMessage();
		}
		assertEquals(message, original);
		
	}
	
	
	@Test
	public void findUserSuccess() {
		Usuario usuario= new Usuario();
		usuario.setId(12);
		when(repo.find(12)).thenReturn(usuario);
		try {
			Usuario usu = userService.findUser(12);
			assertTrue(usuario==usu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void deleteUserSucess() {
		Usuario user=new Usuario();
		user.setArea(new Area());
		user.setBirthday(Date.valueOf("1999-6-2"));
		user.setEmail("sada@hotmail.com");
		user.setFirstName("sara");
		user.setLastName("ortis");
		user.setPhone("3116953284");
		user.setIsdeleted(BigDecimal.ZERO);
		user.setId(20);
		assertTrue(userService.createUser(user));
		try {
			when(repo.find(20)).thenReturn(user);
			userService.deleteUser(20);
			int removeState= userService.findUser(20).getIsdeleted().intValue();
			assertTrue(removeState==1);
		} catch (Exception e) {
			fail();
		}		
	}
	@Test
	public void deleteUserAlreadyExist() {
		Usuario user=new Usuario();
		user.setArea(new Area());
		user.setBirthday(Date.valueOf("1999-6-2"));
		user.setEmail("sada@hotmail.com");
		user.setFirstName("sara");
		user.setLastName("ortis");
		user.setPhone("3116953284");
		user.setIsdeleted(BigDecimal.ONE);
		user.setPassword("f");
		user.setId(20);
		try {
			when(repo.find(20)).thenReturn(user);
			userService.deleteUser(20);
			fail();
		} catch (Exception e) {
			System.out.println(true);
			assertTrue(e.getMessage().equals("The user is already in state deleted"));
			//ready 
		}		
	}

	@Test
	public void sendEmail() {
		try {
			String from="emailsendUsoft@gmail.com";
			String to="";
			System.out.println("To testing the sendEmail, introduce the to email");
//			userService.sendEmail(from, to,"emailsendUsoft@gmail.com","usoft2020");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
