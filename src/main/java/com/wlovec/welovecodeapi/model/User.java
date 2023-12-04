package com.wlovec.welovecodeapi.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String name;

	@Column(unique = true)
	private String email;

	private String password;
	
	private boolean actif = false;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Role role;

	@Override
	public Collection< ? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+this.role));
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.actif;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.actif;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.actif;
	}

	@Override
	public boolean isEnabled() {
		return this.actif;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

}
