package com.wlovec.welovecodeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wlovec.welovecodeapi.model.Formation;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {

}
