package com.usoft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.usoft.model.Role;
import com.usoft.repo.RoleRepository;
import com.usoft.service.RoleService;

public class TestRoleService {
	
	@Mock
	private RoleRepository repo;

	@InjectMocks
	private RoleService roleService;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void createRoleSuccess() {
		
	}
	@Test
	public void createRoleFailure() {
		
	}

	@Test
	public void findAllRoles() {
		Role role= new Role();
		role.setId(14);
		ArrayList<Role> test= new ArrayList<>();
		test.add(role);
		when(repo.findAll()).thenReturn(test);
		Iterable<Role> allRoles=roleService.findAll();
		assertTrue(allRoles==test);
	}

	@Test
	public void findRoleFailure() {
		Role role= new Role();
		role.setId(12);
		String original = "No role registred with id: 12";
		when(repo.find((long)12)).thenThrow(new RuntimeException("No role registred with id: 12"));
		String message = "";
		try {
			Role role1 = roleService.find((long) 12);
		} catch (Exception e) {
			message = e.getMessage();
		}
		assertEquals(message, original);
		
	}
	
	
	@Test
	public void findRoleSuccess() {
		Role role= new Role();
		role.setId(12);
		when(repo.find((long)12)).thenReturn(role);
		try {
			Role role1 = roleService.find((long)12);
			assertTrue(role1==role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void deleteRoleSucess() {
		Role role= new Role();
		role.setId(12);
		when(repo.find((long)12)).thenReturn(role);
		try {
			Role role1 = roleService.find((long)12);
			assertTrue(role1==role);
			roleService.delete((long)12);
			role1.setIsdeleted(BigDecimal.ONE);
			assertTrue(role1.getIsdeleted().equals(BigDecimal.ONE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void deleteRoleAlreadyExist() {
		Role role= new Role();
		role.setId(12);
		String original = "No role registred with id: 12";
		when(repo.find((long)12)).thenThrow(new RuntimeException("No role registred with id: 12"));
		String message = "";
		try {
			roleService.delete((long)12);
		} catch (Exception e) {
			message = e.getMessage();
		}
		assertEquals(message, original);
	}
	
	@Test
	public void update() {
		Role rol = new Role();
		rol.setId((long)11);
		rol.setName("RolBYKR");
		rol.setDescription("RolDePrueba");
		assertTrue(roleService.update(rol));
	}

}
