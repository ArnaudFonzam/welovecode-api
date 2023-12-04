package com.wlovec.welovecodeapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wlovec.welovecodeapi.enumeration.TypeDeRole;
import com.wlovec.welovecodeapi.model.Role;
import com.wlovec.welovecodeapi.model.User;
import com.wlovec.welovecodeapi.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
	private final ValidationService validationService;
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow();
	}

	public void createUser(User user) {
		if (!user.getEmail().contains("@")) {
			throw new RuntimeException("Votre Email est invalide");
		}
		if (!user.getEmail().contains(".")) {
			throw new RuntimeException("Votre Email est invalide");
		}
		Optional<User> userOptional = this.userRepository.findByEmail(user.getEmail()); 
		
		if (userOptional.isPresent()) {
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
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
