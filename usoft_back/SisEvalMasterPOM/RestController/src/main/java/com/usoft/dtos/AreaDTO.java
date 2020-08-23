package com.usoft.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

public class AreaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String description;
	private String name;
	private BigDecimal isdeleted;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getIsdeleted() {
		return isdeleted;
	}
	public void setIsdeleted(BigDecimal isdeleted) {
		this.isdeleted = isdeleted;
	}
	
	
	
	

}
