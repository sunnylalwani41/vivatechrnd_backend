package com.vivatechrnd.service;

import com.vivatechrnd.exception.UserException;
import com.vivatechrnd.model.User;

public interface UserService {
	public User getUserByContactNumber(String contactNumber) throws UserException;
	
	public Boolean checkUserExistByContactNumber(String contactNumber);
}
