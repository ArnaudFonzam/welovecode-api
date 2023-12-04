package com.wlovec.welovecodeapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.wlovec.welovecodeapi.model.User;
import com.wlovec.welovecodeapi.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		log.info("Recupération de tous les utilisateurs");
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		log.info("Recupération de l'utilisateur");
		return ResponseEntity.ok(userService.getUserById(id));
	}

	@PostMapping("/users")
	public ResponseEntity<Boolean> createUser(@RequestBody User user) {
		log.info("Inscription");
		userService.createUser(user);
		return ResponseEntity.ok(true);
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		log.info("Modification de l'utilisateur");
		return ResponseEntity.ok(userService.updateUser(id, user));
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable Long id) {
		log.info("Suppression de l'utilisateur");
		userService.deleteUser(id);
	}
}
