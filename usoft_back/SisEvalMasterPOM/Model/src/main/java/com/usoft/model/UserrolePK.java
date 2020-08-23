package com.usoft.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the USERROLE database table.
 * 
 */
public class UserrolePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long userId;
	private long roleId;

	public UserrolePK() {
	}
	public long getUserId() {
		return this.userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getRoleId() {
		return this.roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserrolePK)) {
			return false;
		}
		UserrolePK castOther = (UserrolePK)other;
		return 
			(this.userId == castOther.userId)
			&& (this.roleId == castOther.roleId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.userId ^ (this.userId >>> 32)));
		hash = hash * prime + ((int) (this.roleId ^ (this.roleId >>> 32)));
		
		return hash;
	}
}