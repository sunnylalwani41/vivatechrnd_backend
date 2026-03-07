package com.vivatechrnd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vivatechrnd.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{
	
	public Optional<Role> findByRoleName(String roleName);

}
