package com.usoft.rest;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.sql.Date;

import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Arrays;

import org.hamcrest.core.IsAnything;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usoft.dtos.AreaDTO;
import com.usoft.dtos.RoleDTO;
import com.usoft.dtos.UserDTO;
import com.usoft.model.Area;
import com.usoft.model.Role;
import com.usoft.model.State;
import com.usoft.model.Usuario;
import com.usoft.repo.UserRepository;
import com.usoft.service.AreaService;
import com.usoft.service.RoleService;
import com.usoft.service.UserService;
import com.usoft.service.UserroleService;

class TestApiUserController {

	@Mock
	private UserService userService;
	
	@Mock
	private AreaService areaService;
	
	@Mock
	private UserroleService userRoleService;
	
	@Mock
	private RoleService roleService;
	
	@Mock
	private PasswordEncoder encoder;
	
	@InjectMocks
	private ApiUserController apiUserController;

	private MockMvc mockMvc;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(apiUserController).build();
	}
	
	@Test
	public void createUser() throws Exception {
		UserDTO userTest = new UserDTO();
		AreaDTO areaTest=new AreaDTO();
		RoleDTO roleTest=new RoleDTO();
		roleTest.setId(0);
		userTest.setFirstName("arnold");
		userTest.setPassword("testing");
		areaTest.setId(0);
		userTest.setRole(roleTest);
		userTest.setArea(areaTest);
		doReturn(true).when(userService).createUser(mock(Usuario.class));
		doReturn(new Area()).when(areaService).findArea(0);
		doReturn(new Role()).when(roleService).find((long) 0);
		doReturn("Iamsecured").when(encoder).encode("testing");

		ObjectMapper objectMapper = new ObjectMapper();
		ResultActions result = mockMvc.perform(post("/usoftapi/users").content(objectMapper.writeValueAsString(userTest))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
	}
	
	@Test
	public void findAllUsers() throws Exception {
		Usuario first = new Usuario();
		Usuario second = new Usuario();
		ArrayList<Usuario> array = new ArrayList<>();
		array.add(first);
		array.add(second);
		doReturn(array).when(userService).findAllUsers();
		mockMvc.perform(get("/usoftapi/users")).andExpect(status().isOk());

	}
	
	@Test
	public void findUser() throws Exception {
		
		Usuario user = new Usuario();
		user.setId(1);
		doReturn(user).when(userService).findUser(1);
		mockMvc.perform(get("/usoftapi/users/1")).andExpect(status().isOk());

	}
	@Test
	public void deleteUserSucces() throws Exception {
		Usuario user=new Usuario();
		user.setArea(new Area());
		user.setBirthday(Date.valueOf("1999-6-2"));
		user.setEmail("sada@hotmail.com");
		user.setFirstName("sara");
		user.setLastName("ortis");
		user.setPhone("3116953284");
		user.setIsdeleted(BigDecimal.ZERO);
		user.setId(12);
		doReturn(true).when(userService).createUser(user);
		ResultActions result = mockMvc.perform(
			      delete("/usoftapi/users/12"));
		result.andExpect(status().isNoContent());
		assertTrue(result.andReturn().getResponse().getStatus()==MockHttpServletResponse.SC_NO_CONTENT);
	}
	@Test
	public void updateUser() {
		
	}
}
