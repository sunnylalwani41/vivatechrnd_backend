package com.vivatechrnd.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AccessControl {
	@Id
	private String access;

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}
}
