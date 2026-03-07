package com.vivatechrnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivatechrnd.exception.UserException;
import com.vivatechrnd.model.User;
import com.vivatechrnd.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;

	@Override
	public User getUserByContactNumber(String contactNumber) throws UserException {
		return userRepository.findByContactNumber(contactNumber).orElseThrow(() -> new UserException("User Not Found"));
	}

	@Override
	public Boolean checkUserExistByContactNumber(String contactNumber) {
		return userRepository.existsByContactNumber(contactNumber);
	}
	
	
}
