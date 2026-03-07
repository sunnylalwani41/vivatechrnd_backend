package com.vivatechrnd.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.vivatechrnd.dto.UserRequestBody;
import com.vivatechrnd.exception.RoleException;
import com.vivatechrnd.exception.UserException;
import com.vivatechrnd.model.Role;
import com.vivatechrnd.model.User;
import com.vivatechrnd.repository.RoleRepository;
import com.vivatechrnd.repository.UserRepository;

import jakarta.transaction.Transactional;
import tools.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private Environment env;
	@Autowired
	private RoleRepository roleRepository;
	
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
	public Boolean addUser(User user) throws UserException {
		Boolean isExistUser = checkUserExistByContactNumber(user.getContactNumber());
		
		if (isExistUser)
			throw new UserException("User already exist");
		
		//create the otp
		user.createOtp();
		
		//store the otp in the database
		String otp = user.getOtp();
		userRepository.save(user);
		
		return sendOtp(otp, user.getContactNumber());
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
		try {
			Response response = client.newCall(request).execute();
	
			if(response.isSuccessful()) {
				System.out.println("result: " + response.body().string());
				
				return true;
			}
			else {
				return false;
			}
			
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
}
