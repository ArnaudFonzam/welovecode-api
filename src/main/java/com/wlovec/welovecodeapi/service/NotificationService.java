package com.wlovec.welovecodeapi.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.wlovec.welovecodeapi.model.Validation;

@Service
public class NotificationService {
	
	private JavaMailSender javaMailSender;
	
	public void envoyer(Validation validation) {
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("coinsdesetudiants@gmail.com");
        message.setTo(validation.getUser().getEmail()); 
        message.setSubject("Votre code d'activation");
        String text = String.format("Bonjour Mr %s <br/> votre code d'activativation est %s", validation.getUser().getName(),validation.getCode());
        message.setText(text);
        javaMailSender.send(message);
	}

}
