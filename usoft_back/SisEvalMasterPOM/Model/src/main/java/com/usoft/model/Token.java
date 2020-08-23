package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the TOKEN database table.
 * 
 */
@Entity
@NamedQuery(name="Token.findAll", query="SELECT t FROM Token t")
public class Token implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String token;

	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE")
	private Date endDate;

	@Temporal(TemporalType.DATE)
	@Column(name="START_DATE")
	private Date startDate;

	private BigDecimal status;

	private String type;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private Usuario usuario;

	public Token() {
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public BigDecimal getStatus() {
		return this.status;
	}

	public void setStatus(BigDecimal status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}