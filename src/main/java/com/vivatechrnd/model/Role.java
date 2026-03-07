package com.vivatechrnd.model;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;
	@Column(nullable = false, unique = true)
	private String roleName;
	private Set<String> accessControl = new LinkedHashSet<>();
	
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
	public Set<String> getAccessControl() {
		return accessControl;
	}
	public void setAccessControl(Set<String> accessControl) {
		this.accessControl = accessControl;
	}
}
