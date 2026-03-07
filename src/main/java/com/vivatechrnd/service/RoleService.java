package com.vivatechrnd.service;

import java.util.List;

import com.vivatechrnd.exception.RoleException;
import com.vivatechrnd.model.AccessControl;
import com.vivatechrnd.model.Role;

public interface RoleService {
	public Role addRole(Role role) throws RoleException;
	
	public Role updateAccessControl(String roleId, List<AccessControl> accessControls) throws RoleException;
	
	public List<Role> getAllRole();
	
	public Role getRoleById(String roleId) throws RoleException;
	
	public Role getRoleByRoleName(String roleName) throws RoleException;
}
