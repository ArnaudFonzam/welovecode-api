package com.wlovec.welovecodeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wlovec.welovecodeapi.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
