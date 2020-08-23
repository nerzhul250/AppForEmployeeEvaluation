package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the PERMISSION database table.
 * 
 */
@Entity
@NamedQuery(name="Permission.findAll", query="SELECT p FROM Permission p")
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String description;

	private BigDecimal isdeleted;

	private String name;

	//bi-directional many-to-one association to Permissionrole
	@OneToMany(mappedBy="permission")
	private List<Permissionrole> permissionroles;

	//bi-directional many-to-many association to Role
	@ManyToMany(mappedBy="permissions")
	private List<Role> roles;

	public Permission() {
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

	public BigDecimal getIsdeleted() {
		return this.isdeleted;
	}

	public void setIsdeleted(BigDecimal isdeleted) {
		this.isdeleted = isdeleted;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Permissionrole> getPermissionroles() {
		return this.permissionroles;
	}

	public void setPermissionroles(List<Permissionrole> permissionroles) {
		this.permissionroles = permissionroles;
	}

	public Permissionrole addPermissionrole(Permissionrole permissionrole) {
		getPermissionroles().add(permissionrole);
		permissionrole.setPermission(this);

		return permissionrole;
	}

	public Permissionrole removePermissionrole(Permissionrole permissionrole) {
		getPermissionroles().remove(permissionrole);
		permissionrole.setPermission(null);

		return permissionrole;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}