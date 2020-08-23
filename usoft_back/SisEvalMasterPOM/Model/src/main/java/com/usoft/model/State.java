package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the STATE database table.
 * 
 */
@Entity
@NamedQuery(name="State.findAll", query="SELECT s FROM State s")
public class State implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String name;

	private String reftable;

	public State() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReftable() {
		return this.reftable;
	}

	public void setReftable(String reftable) {
		this.reftable = reftable;
	}

}