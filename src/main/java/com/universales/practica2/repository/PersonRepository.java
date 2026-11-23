package com.universales.practica2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.universales.practica2.entity.Person;

@Repository("personRepository")
public interface PersonRepository extends JpaRepository<Person, Long>{
	
	Person findByusername(String username);
	
	boolean existsByusername(String username);

}
