package com.usoft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.usoft.application.Application;
import com.usoft.model.Area;
import com.usoft.model.State;
import com.usoft.model.Usuario;
import com.usoft.service.UserService;

@SpringBootTest(classes = Application.class)
public class UserIntegrationTest {
	@Autowired
	private UserService userService;
	
	@Test
	public void createUserSuccess() {
		
	}
	@Test
	public void createUserFailure() {
		
	}

	@Test
	public void findAllUsers() {
		Iterable<Usuario> allUsers=userService.findAllUsers();
        ArrayList<Usuario> list = new ArrayList<>(); 
        allUsers.forEach(list::add);
        assertTrue(list.size()>0);
	}

	@Test
	public void findUserFailure() {
		String original = "No user registred with id: 1028";
		String message = "";
		try {
			Usuario usu = userService.findUser(1028);
		} catch (Exception e) {
			message = e.getMessage();
		}
		assertEquals(message, original);
		
	}
	
	
	@Test
	public void findUserSuccess() {
		
		try {
			Usuario usu = userService.findUser((102));
			assertNotNull(usu);
			assertEquals("NO BORREN", usu.getFirstName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Transactional
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
		assertTrue(userService.createUser(user));
		System.out.println(user.getId());
		try {
			userService.deleteUser((int) user.getId());
			int removeState= userService.findUser((int) user.getId()).getIsdeleted().intValue();
			assertTrue(removeState==1);
		} catch (Exception e) {
			fail();
		}		
	}
	
	@Transactional
	@Test
	public void deleteUserAlreadyExist() {
		Usuario user=new Usuario();
		user.setArea(new Area());
		user.setBirthday(Date.valueOf("1999-6-2"));
		user.setEmail("sada@hotmail.com");
		user.setFirstName("sara");
		user.setLastName("ortis");
		user.setPhone("3116953284");
		user.setIsdeleted(BigDecimal.ZERO);
		user.setPassword("s");
		assertTrue(userService.createUser(user));
		System.out.println(user.getId());
		try {
			userService.deleteUser((int)user.getId());
			userService.deleteUser((int) user.getId());
			fail();
		} catch (Exception e) {
			System.out.println(true);
			assertTrue(e.getMessage().equals("The user is already in state deleted"));
		}	
	}

	@Test
	public void sendEmail() {
		try {
			String from="testing";
			String to="testing";
			System.out.println("To testing the sendEmail, introduce the credential of your count of google");
//			userService.sendEmail(from, to,"user","password");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
