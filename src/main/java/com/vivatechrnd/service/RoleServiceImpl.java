package com.vivatechrnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivatechrnd.exception.RoleException;
import com.vivatechrnd.model.AccessControl;
import com.vivatechrnd.model.Role;
import com.vivatechrnd.repository.AccessControlRepository;
import com.vivatechrnd.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private AccessControlRepository accessControlRepository;

	@Override
	public Role addRole(Role role) throws RoleException {
		Optional<Role> existRole = roleRepository.findByRoleName(role.getRoleName());
		
		if(existRole.isEmpty())
			return roleRepository.save(role);
		
		throw new RoleException("Role already exist");
	}

	@Override
	public Role updateAccessControl(String roleId, List<AccessControl> accessControls) throws RoleException {
		List<AccessControl> list = new ArrayList<>();
		
		for(AccessControl ac: accessControls) {
			if(accessControlRepository.existsById(ac.getAccess())) {
				list.add(ac);
			}
		}
		
		if(list.isEmpty())
			throw new RoleException("These access controls are not exist. Kindly add these access controls");
		
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
