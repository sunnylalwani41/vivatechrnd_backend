package com.vivatechrnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vivatechrnd.dto.GenericResponse;
import com.vivatechrnd.dto.UserRequestBody;
import com.vivatechrnd.model.User;
import com.vivatechrnd.repository.RoleRepository;
import com.vivatechrnd.service.UserService;

@RestControllerAdvice
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleRepository roleRepository;
	
	//free these api from authentication
	@GetMapping("/check_user_exist")
	public ResponseEntity<GenericResponse<Boolean>> checkUserExistController(@RequestParam String contactNumber){
		Boolean result = userService.checkUserExistByContactNumber(contactNumber);
		GenericResponse<Boolean> genericResponse = new GenericResponse<>("", true, result);
		
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping("/add_user")
	public ResponseEntity<GenericResponse<Boolean>> addUserController(@RequestBody UserRequestBody userRequestBody) {
		GenericResponse<Boolean> genericResponse = null;
		
		try {
			User user = userService.parseDataIntoUser(userRequestBody);
			Boolean result = userService.addUser(user);
			String message = "Otp not send";
			
			if(result)
				message = "Otp send";
	
			genericResponse = new GenericResponse<Boolean>(message, true, result);
		}
		catch (Exception e) {
			genericResponse = new GenericResponse<Boolean>(e.getMessage(), false, null);
		}
		
		return ResponseEntity.ok(genericResponse);
	}
}