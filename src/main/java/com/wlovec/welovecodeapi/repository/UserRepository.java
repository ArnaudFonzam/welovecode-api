package com.wlovec.welovecodeapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wlovec.welovecodeapi.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByName(String name);

	Optional<User> findByEmail(String email);
}
