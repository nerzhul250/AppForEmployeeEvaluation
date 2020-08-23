package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the LOG database table.
 * 
 */
@Entity
@NamedQuery(name="Log.findAll", query="SELECT l FROM Log l")
public class Log implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String description;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private String function;

	private String reftable;

	public Log() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getFunction() {
		return this.function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getReftable() {
		return this.reftable;
	}

	public void setReftable(String reftable) {
		this.reftable = reftable;
	}

}