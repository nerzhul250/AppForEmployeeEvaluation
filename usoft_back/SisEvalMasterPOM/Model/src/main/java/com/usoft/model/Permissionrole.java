package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PERMISSIONROLE database table.
 * 
 */
@Entity
@IdClass(PermissionrolePK.class)
@NamedQuery(name="Permissionrole.findAll", query="SELECT p FROM Permissionrole p")
public class Permissionrole implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal isdeleted;

	@Id
	@Column(name="PERMISSION_ID")
	private long permissionId;
	
	@Id
	@Column(name="ROLE_ID")
	private long roleId;
	
	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="ROLE_ID", insertable = false, updatable = false)
	private Role role;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="PERMISSION_ID", insertable = false, updatable = false)
	private Permission permission;

	public Permissionrole() {
	}

	public BigDecimal getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(BigDecimal isdeleted) {
		this.isdeleted = isdeleted;
	}

	public Permission getPermission() {
		return this.permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}