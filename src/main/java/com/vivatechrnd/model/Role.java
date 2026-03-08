package com.vivatechrnd.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;
	@Column(nullable = false, unique = true)
	private String roleName;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<AccessControl> accessControl = new ArrayList<>();
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public List<AccessControl> getAccessControl() {
		return accessControl;
	}
	public void setAccessControl(List<AccessControl> accessControl) {
		this.accessControl = accessControl;
	}
}
