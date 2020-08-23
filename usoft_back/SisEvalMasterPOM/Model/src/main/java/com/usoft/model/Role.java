package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the ROLE database table.
 * 
 */
@Entity
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	private String description;

	private BigDecimal isdeleted;

	private String name;

	//bi-directional many-to-one association to Permissionrole
	@OneToMany(mappedBy="role")
	private List<Permissionrole> permissionroles;

	//bi-directional many-to-many association to Permission
	@ManyToMany
	@JoinTable(
		name="PERMISSIONROLE"
		, joinColumns={
			@JoinColumn(name="ROLE_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="PERMISSION_ID")
			}
		)
	private List<Permission> permissions;

	//bi-directional many-to-one association to Userrole
	@OneToMany(mappedBy="role")
	private List<Userrole> userroles;

	public Role() {
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
		permissionrole.setRole(this);

		return permissionrole;
	}

	public Permissionrole removePermissionrole(Permissionrole permissionrole) {
		getPermissionroles().remove(permissionrole);
		permissionrole.setRole(null);

		return permissionrole;
	}

	public List<Permission> getPermissions() {
		return this.permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public List<Userrole> getUserroles() {
		return this.userroles;
	}

	public void setUserroles(List<Userrole> userroles) {
		this.userroles = userroles;
	}

	public Userrole addUserrole(Userrole userrole) {
		getUserroles().add(userrole);
		userrole.setRole(this);

		return userrole;
	}

	public Userrole removeUserrole(Userrole userrole) {
		getUserroles().remove(userrole);
		userrole.setRole(null);

		return userrole;
	}

}