package com.usoft.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usoft.model.Criteria;
import com.usoft.model.Role;
import com.usoft.service.RoleService;

public class TestApiRoleController {
	@Mock
	private RoleService roleService;

	@InjectMocks
	private ApiRoleController apiRoleController;

	private MockMvc mockMvc;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(apiRoleController).build();
	}
	
	@Test
	public void createRole() throws Exception {
		
	}
	
	@Test
	public void findAllRoles() throws Exception {
		Role first = new Role();
		Role  second = new Role();
		ArrayList<Role> array = new ArrayList<>();
		array.add(first);
		array.add(second);
		doReturn(array).when(roleService).findAll();
		mockMvc.perform(get("/usoftapi/role")).andExpect(status().isOk());

	}
	
	@Test
	public void findRole() throws Exception {
		
		Role role = new Role();
		role.setId(1);
		doReturn(role).when(roleService).find((long)1);
		mockMvc.perform(get("/usoftapi/role/1")).andExpect(status().isOk());

	}
	@Test
	public void deleteRoleSuccess() throws Exception {
		Role role = new Role();
		role.setId(200);
		role.setDescription("NEWINDICATOR");
		ObjectMapper objectMapper = new ObjectMapper();
		ResultActions result = mockMvc.perform(post("/usoftapi/role")
				.content(objectMapper.writeValueAsString(role))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
		result = mockMvc.perform(delete("/usoftapi/role/200"));
		result.andExpect(status().isNoContent());
		assertTrue(result.andReturn().getResponse().getStatus()==MockHttpServletResponse.SC_NO_CONTENT);
	}
	@Test
	public void updateRole() {
		
	}
}
