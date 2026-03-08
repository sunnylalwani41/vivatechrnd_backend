package com.vivatechrnd.service;

import com.vivatechrnd.dto.UserRequestBody;
import com.vivatechrnd.dto.UserResponse;
import com.vivatechrnd.exception.RoleException;
import com.vivatechrnd.exception.UserException;
import com.vivatechrnd.model.User;

public interface UserService {
	public User getUserByContactNumber(String contactNumber) throws UserException;
	
	public Boolean checkUserExistByContactNumber(String contactNumber);
	
	public User addUser(User user) throws UserException;
	
	public User parseDataIntoUser(UserRequestBody userRequestBody) throws RoleException;
	
	public UserResponse checkOtp(String otp, String contactNumber) throws UserException;
	
	public Boolean sendOtpForLogin(String contactNumber)throws UserException;
}
