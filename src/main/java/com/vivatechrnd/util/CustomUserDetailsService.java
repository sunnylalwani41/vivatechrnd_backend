package com.vivatechrnd.util;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vivatechrnd.model.AccessControl;
import com.vivatechrnd.model.Role;
import com.vivatechrnd.model.User;
import com.vivatechrnd.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;
	
	@Override
    public UserDetails loadUserByUsername(String contactNumber) throws UsernameNotFoundException {

        User user = userRepository.findByContactNumber(contactNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<GrantedAuthority> authorities =
                Optional.ofNullable(user.getRole())
                        .map(Role::getAccessControl)
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(AccessControl::getAccess)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getContactNumber())
                .password("") // OTP login usually doesn't use password
                .authorities(authorities)
                .build();
    }
}