package com.wex.poc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "permission_set_permissions")
public class PermissionSetPermission {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn(name = "permission_id", nullable = false)
	private Permission permission;

	@ManyToOne
	@JoinColumn(name = "permission_set_id", nullable = false)
	private PermissionSet permissionSet;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public PermissionSet getPermissionSet() {
		return permissionSet;
	}

	public void setPermissionSet(PermissionSet permissionSet) {
		this.permissionSet = permissionSet;
	}

}
