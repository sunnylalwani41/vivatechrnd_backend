package com.vivatechrnd.util;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	private final String SECRETKEY = "b1N6eXhVY3R0Q2s4VHJ5bFh3U0Z5aE5kM3Q=";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            
            try {
//            		Claims claims = parseToken(token);
//	            String email = claims.getSubject();
	            String email = extractEmail(token);
	
	            UserDetails user = userDetailsService.loadUserByUsername(email);
	
	            UsernamePasswordAuthenticationToken auth =
	                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	
	            SecurityContextHolder.getContext().setAuthentication(auth);
            }
            catch(ExpiredJwtException e) {
            		response.setStatus(401);
                return;
            }
        }

        filterChain.doFilter(request, response);
	}
	
	public Claims parseToken(String token) {
		return Jwts.parser().setAllowedClockSkewSeconds(60)
				.build().parseClaimsJws(token).getBody();
	}
	
	public String extractEmail(String token) {
		return Jwts.parser().
				setSigningKey(SECRETKEY.getBytes()).
				build().
				parseClaimsJws(token).
				getBody().
				getSubject();
	}

}