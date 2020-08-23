package com.usoft.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PeriodDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private Date startDate;
	private Date endDate;
	private BigDecimal isDeleted;
	
	public BigDecimal getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(BigDecimal isDeleted) {
		this.isDeleted = isDeleted;
	}
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
