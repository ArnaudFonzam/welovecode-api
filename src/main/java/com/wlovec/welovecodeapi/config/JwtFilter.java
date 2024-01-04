package com.wlovec.welovecodeapi.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wlovec.welovecodeapi.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtFilter extends OncePerRequestFilter {

	private UserService userService;
	private JwtService jwtService;
	
	public JwtFilter(UserService userService, JwtService jwtService) {
		this.userService = userService;
		this.jwtService = jwtService;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String username = null;
		String token = null;
		boolean isTokenExpired = true;
		final String authorisation = request.getHeader("Authorization");
		if (authorisation != null && authorisation.startsWith("Bearer")) {
			token = authorisation.substring(7);
			isTokenExpired = jwtService.isTokenExpired(token);
			username = jwtService.getUsername(token);
		}
		
		if (!isTokenExpired && username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword());
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		
		filterChain.doFilter(request, response);
	}
	

}
