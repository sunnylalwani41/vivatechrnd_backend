package com.vivatechrnd.util;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vivatechrnd.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Value("${secret.key}")
	private String secretKey;

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
				setSigningKey(secretKey.getBytes()).
				build().
				parseClaimsJws(token).
				getBody().
				getSubject();
	}
	
	public String generateToken(User user) {
		return Jwts.builder().
				setSubject(user.getContactNumber()).
				claim("role", user.getRole().getRoleName()).
				setIssuedAt(new Date()).
				setExpiration(new Date(System.currentTimeMillis() + 108000000)).
				signWith(Keys.hmacShaKeyFor(secretKey.getBytes())).
				compact();
	}

}