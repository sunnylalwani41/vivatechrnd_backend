package com.vivatechrnd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivatechrnd.exception.RoleException;
import com.vivatechrnd.model.AccessControl;
import com.vivatechrnd.model.Role;
import com.vivatechrnd.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role addRole(Role role) throws RoleException {
		Role existRole = getRoleByRoleName(role.getRoleName());
		
		if(existRole == null)
			return roleRepository.save(role);
		
		throw new RoleException("Role already exist");
	}

	@Override
	public Role updateAccessControl(String roleId, List<AccessControl> accessControls) throws RoleException {
		Role role = getRoleById(roleId);
		role.setAccessControl(accessControls);
		
		return roleRepository.save(role);
	}

	@Override
	public List<Role> getAllRole() {
		List<Role> list = roleRepository.findAll();
		
		if(list != null && !list.isEmpty())
			return list;
		
		return new ArrayList<>();
	}

	@Override
	public Role getRoleById(String roleId) throws RoleException {
		return roleRepository.findById(roleId).orElseThrow(() -> new RoleException("Role Not Found"));
	}

	@Override
	public Role getRoleByRoleName(String roleName) throws RoleException {
		return roleRepository.findByRoleName(roleName).orElseThrow(() -> new RoleException("Role Not Found"));
	}	
}
