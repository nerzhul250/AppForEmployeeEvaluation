package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the USERROLE database table.
 * 
 */
@Entity
@IdClass(UserrolePK.class)
@NamedQuery(name="Userrole.findAll", query="SELECT u FROM Userrole u")
public class Userrole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private BigDecimal isdeleted;

	@Id
	@Column(name="USER_ID")
	private long userId;
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	@Id
	@Column(name="ROLE_ID")
	private long roleId;
	
	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="ROLE_ID", insertable = false, updatable = false)
	private Role role;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="USER_ID", insertable = false, updatable = false)
	private Usuario usuario;

	public Userrole() {
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(BigDecimal isdeleted) {
		this.isdeleted = isdeleted;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}