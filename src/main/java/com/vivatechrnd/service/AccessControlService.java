package com.vivatechrnd.service;

import java.util.List;

import com.vivatechrnd.model.AccessControl;

public interface AccessControlService {
	public AccessControl addAccessControl(AccessControl accessControl);
	
	public List<AccessControl> getAccessControls();
}
