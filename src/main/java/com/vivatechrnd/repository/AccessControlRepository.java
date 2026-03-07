package com.vivatechrnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vivatechrnd.model.AccessControl;

@Repository
public interface AccessControlRepository extends JpaRepository<AccessControl, String>{
	
}
