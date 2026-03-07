package com.vivatechrnd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vivatechrnd.dto.GenericResponse;
import com.vivatechrnd.model.AccessControl;
import com.vivatechrnd.service.AccessControlService;

@RestController
public class AccessControlController {
	@Autowired
	private AccessControlService accessControlService;
	
	@PostMapping("/add_access_control")
	public ResponseEntity<GenericResponse<AccessControl>> addAccessControlController(@RequestBody AccessControl accessControl){
		accessControl = accessControlService.addAccessControl(accessControl);
		GenericResponse<AccessControl> genericResponse = new GenericResponse<AccessControl>("", true, accessControl);
		
		return ResponseEntity.ok(genericResponse);
	}
	
	@GetMapping("/get_all_access_control")
	public ResponseEntity<GenericResponse<List<AccessControl>>> getAllAccessControlsController(){
		List<AccessControl> list = accessControlService.getAccessControls();
		GenericResponse<List<AccessControl>> genericResponse = new GenericResponse<List<AccessControl>>("", true, list);
		
		return ResponseEntity.ok(genericResponse);
	}
}
