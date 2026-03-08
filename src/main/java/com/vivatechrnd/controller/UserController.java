package com.vivatechrnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vivatechrnd.dto.GenericResponse;
import com.vivatechrnd.dto.UserRequestBody;
import com.vivatechrnd.dto.UserResponse;
import com.vivatechrnd.exception.UserException;
import com.vivatechrnd.model.User;
import com.vivatechrnd.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	//free these api from authentication
	@GetMapping("/check_user_exist")
	public ResponseEntity<GenericResponse<Boolean>> checkUserExistController(@RequestParam String contactNumber){
		Boolean result = userService.checkUserExistByContactNumber(contactNumber);
		GenericResponse<Boolean> genericResponse = new GenericResponse<>("", true, result);
		
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping("/add_user")
	public ResponseEntity<GenericResponse<User>> addUserController(@RequestBody UserRequestBody userRequestBody) {
		GenericResponse<User> genericResponse = null;
		
		try {
			User user = userService.parseDataIntoUser(userRequestBody);
			user = userService.addUser(user);
	
			genericResponse = new GenericResponse<User>("", true, user);
		}
		catch (Exception e) {
			genericResponse = new GenericResponse<User>(e.getMessage(), false, null);
		}
		
		return ResponseEntity.ok(genericResponse);
	}
	
	@PostMapping("/otp_submit")
	public ResponseEntity<GenericResponse<UserResponse>> otpSubmitController(@RequestParam String otp, @RequestParam String contactNumber) throws UserException{
		UserResponse userResponse = userService.checkOtp(otp, contactNumber);
		GenericResponse<UserResponse> genericResponse = new GenericResponse<UserResponse>("", true, userResponse);
		
		return ResponseEntity.ok(genericResponse);
	}
	
	@GetMapping("/send_otp")
	public ResponseEntity<GenericResponse<Boolean>> sendOtpController(@RequestParam String 
			contactNumber) throws UserException{
		Boolean result = userService.sendOtpForLogin(contactNumber);
		GenericResponse<Boolean> genericResponse = new GenericResponse<Boolean>("Otp send", result, result);
		
		if(!result)
			genericResponse.setMessage("Otp not send");
		
		return ResponseEntity.ok(genericResponse);
	}
}