package com.vivatechrnd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vivatechrnd.dto.GenericResponse;
import com.vivatechrnd.exception.RoleException;
import com.vivatechrnd.model.AccessControl;
import com.vivatechrnd.model.Role;
import com.vivatechrnd.service.RoleService;

@RestController
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@PostMapping("/add_role")
	public ResponseEntity<GenericResponse<Role>> addRole(@RequestBody Role role) throws RoleException{
		role = roleService.addRole(role);
		GenericResponse<Role> genericResponse = new GenericResponse<Role>("", true, role);
		
		return ResponseEntity.ok(genericResponse);
	}
	
	@PatchMapping("/update_access/{roleId}")
	public ResponseEntity<GenericResponse<Role>> updateAccessForRole(@PathVariable String roleId, @RequestBody List<AccessControl> accessControls) throws RoleException{
		Role role = roleService.updateAccessControl(roleId, accessControls);
		GenericResponse<Role> genericResponse = new GenericResponse<Role>("", true, role);
		
		return ResponseEntity.ok(genericResponse);
	}
	
	@GetMapping("/get_role_by_id")
	public ResponseEntity<GenericResponse<Role>> getRoleByRoleIdController(@RequestParam String roleId) throws RoleException{
		Role role = roleService.getRoleById(roleId);
		GenericResponse<Role> genericResponse = new GenericResponse<Role>("", true, role);
		
		return ResponseEntity.ok(genericResponse);
	}
	
	//it is free from security
	@GetMapping("/get_all_role")
	public ResponseEntity<GenericResponse<List<Role>>> getAllRole(){
		List<Role> roleList = roleService.getAllRole();
		GenericResponse<List<Role>> genericResponse = new GenericResponse<List<Role>>("", true, roleList);
		
		return ResponseEntity.ok(genericResponse);
	}
}
