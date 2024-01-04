package com.wlovec.welovecodeapi.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wlovec.welovecodeapi.config.JwtService;
import com.wlovec.welovecodeapi.dto.UserLogin;
import com.wlovec.welovecodeapi.model.User;
import com.wlovec.welovecodeapi.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	private UserService userService;
	private JwtService jwtService;
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		log.info("Recupération de tous les utilisateurs");
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) throws Exception {
		log.info("Recupération de l'utilisateur");
		Optional<User> user = userService.getUserById(id);
		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		}
		throw new Exception("user not Found.");
	}

	@PostMapping("/users")
	public ResponseEntity<Boolean> createUser(@Valid @RequestBody User user) {
		log.info("Inscription");
		userService.createUser(user);
		return ResponseEntity.ok(true);
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
		log.info("Modification de l'utilisateur");
		return ResponseEntity.ok(userService.updateUser(id, user));
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable Long id) {
		log.info("Suppression de l'utilisateur.");
		userService.deleteUser(id);
	}
	
	@PostMapping("/users/connexion")
	public Map<String, String> login(@Valid @RequestBody UserLogin authenticationDTO) {
		log.info("Connexion {} and {}:", authenticationDTO.getEmail(), authenticationDTO.getPassword());
		return jwtService.generate(authenticationDTO.getEmail());
	}
	
	@PostMapping("/users/activation")
	public void activate(@RequestBody Map<String, String> activation) {
		this.userService.activation(activation);
		log.info("Activation reussi.");
	}
}
