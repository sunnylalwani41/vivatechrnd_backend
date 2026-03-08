package com.vivatechrnd.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.vivatechrnd.dto.UserRequestBody;
import com.vivatechrnd.dto.UserResponse;
import com.vivatechrnd.exception.RoleException;
import com.vivatechrnd.exception.UserException;
import com.vivatechrnd.model.Role;
import com.vivatechrnd.model.User;
import com.vivatechrnd.repository.RoleRepository;
import com.vivatechrnd.repository.UserRepository;
import com.vivatechrnd.util.JwtFilter;

import jakarta.transaction.Transactional;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tools.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private Environment env;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private JwtFilter jwtFilter;
	
	private final String SMSAPI = "https://www.fast2sms.com/dev/bulkV2";
	private final String SMSTYPE = "q";

	@Override
	public User getUserByContactNumber(String contactNumber) throws UserException {
		return userRepository.findByContactNumber(contactNumber).orElseThrow(() -> new UserException("User Not Found"));
	}

	@Override
	public Boolean checkUserExistByContactNumber(String contactNumber) {
		return userRepository.existsByContactNumber(contactNumber);
	}

	@Transactional
	@Override
	public User addUser(User user) throws UserException {
		Boolean isExistUser = checkUserExistByContactNumber(user.getContactNumber());
		
		if (isExistUser)
			throw new UserException("User already exist");
		
		//create the otp
		user.createOtp();
		
		//send otp to the contact number
		String otp = user.getOtp();		
		sendOtp(otp, user.getContactNumber());
		
		//store the otp in the database
		return userRepository.save(user);
	}
	
	private Boolean sendOtp(String otp, String contactNumber) {
//		ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
		//api key
		String apiKey = env.getProperty("fast2sms.api.key");
	
		//data body
		Map<String, String> data = new HashMap<>();
		data.put("route", SMSTYPE);
		data.put("message", "Your otp is "+ otp );
		data.put("numbers", contactNumber);
		data.put("flash", "0");
		data.put("sms_details", "1");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(data);
		
		//api calling
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("application/json");

		RequestBody body = RequestBody.create(mediaType, jsonBody);

		Request request = new Request.Builder()
		        .url(SMSAPI)
		        .post(body)
		        .addHeader("accept", "application/json")
		        .addHeader("authorization", apiKey)
		        .addHeader("content-type", "application/json")
		        .build();
		try (Response response = client.newCall(request).execute()) {

    System.out.println("HTTP CODE: " + response.code());
//    System.out.println("API RESPONSE: " + result);
//    return true;

    			return response.isSuccessful();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			
			return false;
		}
	}

	@Override
	public User parseDataIntoUser(UserRequestBody userRequestBody) throws RoleException {
		Role role = roleRepository.findById(userRequestBody.getRoleId()).orElseThrow(() -> new RoleException("Role Not Found"));
		User user = new User();
		
		user.setContactNumber(userRequestBody.getContactNumber());
		user.setRole(role);
		user.setName(userRequestBody.getName());
		
		return user;
	}

	@Override
	public UserResponse checkOtp(String otp, String contactNumber) throws UserException {
		User user = userRepository.findByContactNumber(contactNumber).orElseThrow(() -> new UserException("User Not Found"));
		
		if(!user.getIsVerify() && user.getOtp().equals(otp)) {
			if(user.getOtpExpiryTime().isBefore(LocalDateTime.now()))
				throw new UserException("Otp has been expired.");
			
			user.setIsVerify(true);
			
			String jwtToken = jwtFilter.generateToken(user);
			
			UserResponse userResponse = new UserResponse();
			userResponse.setContactNumber(contactNumber);
			userResponse.setName(user.getName());
			userResponse.setToken(jwtToken);
			
			userRepository.save(user);
			return userResponse;
		}
		else {
			throw new UserException("Invalid Otp...");
		}
	}

	@Override
	public Boolean sendOtpForLogin(String contactNumber) throws UserException {
		User user = getUserByContactNumber(contactNumber);
		user.createOtp();
		userRepository.save(user);
		
		return sendOtp(user.getOtp(), contactNumber);
	}
}
