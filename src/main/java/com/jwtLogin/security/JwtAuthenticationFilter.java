package com.jwtLogin.security;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwtLogin.dto.RestError;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
		try {
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}
			jwt = authHeader.substring(7);
			Claims claims = jwtService.extractAllClaims(jwt);
			log.info("Claims are - {}", claims);
			userEmail = claims.getSubject();
			if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				User userDetails = (User) this.userDetailsService.loadUserByUsername(userEmail);
				if (jwtService.isTokenValid(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			RestError re  = new RestError(HttpStatus.UNAUTHORIZED,"UNAUTHORIZED");;
			if (e instanceof io.jsonwebtoken.MalformedJwtException) {
				re = new RestError(HttpStatus.UNAUTHORIZED,"Invalid Token");
				
			}else if (e instanceof io.jsonwebtoken.ExpiredJwtException) {
				re = new RestError(HttpStatus.UNAUTHORIZED,"Token Expired");
			}
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			OutputStream responseStream = response.getOutputStream();
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(responseStream, re);
			responseStream.flush();
		}
		filterChain.doFilter(request, response);
	}
}
