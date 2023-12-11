package com.wlovec.welovecodeapi.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wlovec.welovecodeapi.enumeration.TypeDeRole;
import com.wlovec.welovecodeapi.model.Role;
import com.wlovec.welovecodeapi.model.User;
import com.wlovec.welovecodeapi.model.Validation;
import com.wlovec.welovecodeapi.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

	private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
	private ValidationService validationService;
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public void createUser(User user) {
		if (!user.getEmail().contains("@")) {
			throw new RuntimeException("Votre Email est invalide");
		}
		if (!user.getEmail().contains(".")) {
			throw new RuntimeException("Votre Email est invalide");
		}
		Optional<User> userOp = this.userRepository.findByEmail(user.getEmail()); 
		if (userOp.isPresent()) {
			throw new RuntimeException("Cette Email est déjà utilisé");
		}
		if (this.userRepository.findByName(user.getName()) != null) {
			throw new RuntimeException("Ce Nom est déjà utilisé");
		}
		String pwd = this.passwordEncoder.encode(user.getPassword());
		user.setPassword(pwd);
		Role roleUser = new Role();
		roleUser.setLibelle(TypeDeRole.USER);
		user.setRole(roleUser);
		User userSave = userRepository.save(user);
		this.validationService.saveValidation(userSave);
		System.out.println("inscription reussi");
	}

	public User updateUser(Long id, User user) {
		User existingUser = userRepository.findById(id).orElseThrow();
		existingUser.setName(user.getName());
		existingUser.setEmail(user.getEmail());
		existingUser.setPassword(user.getPassword());
		return userRepository.save(existingUser);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found.");
        }
        return user.get();
    }
	
	public void activation(Map<String, String> activation) {
		Validation validation = this.validationService.getValidationByCode(activation.get("code"));
		
		if (Instant.now().isAfter(validation.getExpiration())) {
			throw new RuntimeException("Votre code à expirer.");
		}
		
		Optional<User> user = this.userRepository.findByEmail(validation.getUser().getEmail());
		if (!user.isPresent()) {
			throw new RuntimeException("Unknow user.");
		}
		User u = user.get();
		u.setActif(true);
		this.userRepository.save(u);
		this.validationService.updateValidation(validation);
		
	}
}
