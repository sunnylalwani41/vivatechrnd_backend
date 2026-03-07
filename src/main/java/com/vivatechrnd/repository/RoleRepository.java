package com.vivatechrnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vivatechrnd.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{
}
