package com.usoft.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.usoft.model.Area;

public class UserDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long id;
	private Date birthday;
	private String email;
	private AreaDTO area;
	private String firstName;
	private String lastName;
	private String phone;
	private String password;
	private RoleDTO role;
	private PositionDTO position;
	
	
	public PositionDTO getPosition() {
		return position;
	}
	public void setPosition(PositionDTO position) {
		this.position = position;
	}
	public AreaDTO getArea() {
		return area;
	}
	public void setArea(AreaDTO area) {
		this.area = area;
	}
	public RoleDTO getRole() {
		return role;
	}
	public void setRole(RoleDTO role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
