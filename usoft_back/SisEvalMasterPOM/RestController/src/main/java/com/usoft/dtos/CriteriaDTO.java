package com.usoft.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

public class CriteriaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String description;
	private BigDecimal percent;
	
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
	public BigDecimal getPercent() {
		return percent;
	}
	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
	@Override
	public String toString() {
		return "CriteriaDTO [id=" + id + ", description=" + description + ", percent=" + percent + "]";
	}
	
	
}
