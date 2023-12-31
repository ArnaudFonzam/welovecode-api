package com.wlovec.welovecodeapi.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.wlovec.welovecodeapi.model.User;
import com.wlovec.welovecodeapi.model.Validation;
import com.wlovec.welovecodeapi.repository.ValidationRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ValidationService {

	private ValidationRepository validationRepository;
	private NotificationService notificationService;
	
	public void saveValidation(User user) {
		Validation validation = new Validation();
		validation.setUser(user);
		Instant creation = Instant.now();
		Instant expiration = creation.plus(10, ChronoUnit.MINUTES);
		Random random = new Random();
		int randomInt = random.nextInt(999999);
		String code = String.format("%d6", randomInt);
		
		validation.setCode(code);
		validation.setCreation(creation);
		validation.setExpiration(expiration);
		
		Validation val = this.validationRepository.save(validation);
		this.notificationService.envoyer(val);
	}
	
	public Validation getValidationByCode(String code) {
		Optional<Validation> validation = this.validationRepository.findByCode(code);
		if(validation.isPresent()) {
			return validation.get();
		}	
		throw new RuntimeException("Code invalid");
	}
	
	public void updateValidation(Validation validation) {
		validation.setActivation(Instant.now());
		this.validationRepository.save(validation);
	}
}
