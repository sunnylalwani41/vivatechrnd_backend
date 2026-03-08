package com.vivatechrnd.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SpringConfig {
	@Autowired
	private JwtFilter jwtFilter;
	
    @Bean
    public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain security(HttpSecurity http) throws Exception {

	    http.csrf(csrf -> csrf.disable());

	    http.authorizeHttpRequests(auth -> auth.requestMatchers("/get_all_role", "/add_role")
	    		.permitAll()
	        .anyRequest().authenticated())
	    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	    		);

	    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}
	
	@Bean
	public AuditorAware<String> auditorAware() {
	    return () -> Optional.of(
	        SecurityContextHolder.getContext()
	            .getAuthentication().getName()
	    );
	}
}
