package com.universales.practica2.service;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universales.practica2.dto.RequestPersonDto;
import com.universales.practica2.repository.PersonRepository;
import com.universales.practica2.entity.Person;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PersonService {
	
	PersonRepository pRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	
	public PersonService(PersonRepository pRepository) {
		this.pRepository = pRepository;
	}
	
	@PostMapping("/noauth/login")
	public ResponseEntity<?> login(
			
			@RequestBody RequestPersonDto personDto
			
	){
		
		try {
			
			if(pRepository.existsByusername(personDto.getUserName())) {
				
				Person person = pRepository.findByusername(personDto.getUserName());
				
				if(person.getPassword() == null || person.getPassword().isEmpty()) {
					
					return ResponseEntity
							.status(HttpStatus.NO_CONTENT)
							.body("Usuario con falta de credenciales");
					
				}
				
				if(person.getPassword().equals(personDto.getPassword())) {
					
					return ResponseEntity.ok("Login Exitoso");
					
				}
				
			}
			
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.body("Usuario o Contraseña Invalidos");
			
		}catch(Exception e) {
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error en el servidor:"+e.getMessage());
			
		}
		
	}

}
