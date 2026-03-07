package com.vivatechrnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vivatechrnd.service.UserService;

@RestControllerAdvice
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/check_user_exist")
}
