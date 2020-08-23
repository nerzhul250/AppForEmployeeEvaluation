package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CONFIGURATION database table.
 * 
 */
@Entity
@NamedQuery(name="Configuration.findAll", query="SELECT c FROM Configuration c")
public class Configuration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String clave;

	private String valor;

	public Configuration() {
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}