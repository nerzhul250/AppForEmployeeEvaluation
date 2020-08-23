package com.usoft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.usoft.application.Application;
import com.usoft.model.Role;
import com.usoft.service.RoleService;

@SpringBootTest(classes = Application.class)
public class RoleIntegrationTest {
	
	@Autowired
	private RoleService roleService;
	
	@Test
	public void createRoleSuccess() {
		
	}
	@Test
	public void createRoleFailure() {
		
	}

	@Test
	public void findAllRoles() {
		Iterable<Role> allRoles=roleService.findAll();
        ArrayList<Role> list = new ArrayList<>(); 
        allRoles.forEach(list::add);
        assertTrue(list.size()>0);
	}

	@Test
	public void findRoleFailure() {
		String original = "No role registred with id: 1028";
		String message = "";
		try {
			Role role = roleService.find((long)1028);
		} catch (Exception e) {
			message = e.getMessage();
		}
		assertEquals(message, original);
		
	}
	
	
	@Test
	public void findRoleSuccess() {
		
		try {
			Role role = roleService.find((long)0);
			assertNotNull(role);
			assertEquals("ROLE_ADMIN", role.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Transactional
	@Test
	public void deleteRoleSucess() {
		try {
			Role role = roleService.find((long)0);
			assertNotNull(role);
			assertEquals("ROLE_ADMIN", role.getName());
			roleService.delete((long)0);
			role = roleService.find((long)0);
			assertTrue(role.getIsdeleted().equals(BigDecimal.ONE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	@Test
	public void deleteRoleAlreadyExist() {
		String original = "No role registred with id: 1028";
		String message = "";
		try {
			roleService.delete((long)1028);
		} catch (Exception e) {
			message = e.getMessage();
		}
		assertEquals(message, original);
	}

}
