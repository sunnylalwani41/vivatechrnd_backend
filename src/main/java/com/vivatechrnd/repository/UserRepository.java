package com.vivatechrnd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivatechrnd.model.User;

public interface UserRepository extends JpaRepository<User, String>{
	
	public Optional<User> findByContactNumber(String contactNumber);
	
	public Boolean existsByContactNumber(String contactNumber);
}
