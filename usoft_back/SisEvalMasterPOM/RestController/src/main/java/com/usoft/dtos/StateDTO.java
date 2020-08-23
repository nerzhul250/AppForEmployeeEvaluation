package com.usoft.dtos;

import java.io.Serializable;

public class StateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private String reftable;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReftable() {
		return reftable;
	}
	public void setReftable(String reftable) {
		this.reftable = reftable;
	}
	
}
