package com.wlovec.welovecodeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wlovec.welovecodeapi.model.Validation;

public interface ValidationRepository extends JpaRepository<Validation, Long> {

}
