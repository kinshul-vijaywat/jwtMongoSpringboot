package com.jwtLogin.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import lombok.Data;


@Data
public class User implements UserDetails{

	/**
	 * 
	 */
	public static final String SEP = ",";
	private static final long serialVersionUID = 1L;
	private String userData;
	private String tenantId;
	private String userId;
	
	public User(String userDetails) {
		try {
			String[] split = userDetails.split(SEP);
			this.tenantId = split[0];
			this.userId = split[1];
			this.userData = userDetails;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public User(String tenantId, String userId) {
		this.tenantId = tenantId;
		this.userId = userId;
		this.userData = setUserData();		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("API_USER"));
	}

	@Override
	public String getPassword() {
		return userData;
	}

	@Override
	public String getUsername() {
		return userData;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	private String getUserData() {
		return userData;
	}
	
	private String setUserData() {
		return this.tenantId + SEP + this.userId;
	}
}
