package com.wlovec.welovecodeapi.model;

import java.time.Instant;

import com.wlovec.welovecodeapi.enumeration.TypeDeRole;

import jakarta.persistence.CascadeType;
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
public class Validation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Instant creation;
	
	private Instant expiration;
	
	private Instant activation;
	
	private String code;
	
	@OneToOne(cascade = CascadeType.ALL)
	private User user;
	
}
