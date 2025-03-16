package com.microservice.controllers;

import java.util.List;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.entities.User;
import com.microservice.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);


	@Autowired
	private UserService userService;
	
	@PostMapping()
	public ResponseEntity<User> createUser(@RequestBody User user){
		
		User saveUser = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
	}

	@GetMapping("/{userId}")
	@CircuitBreaker(name = "ratingHotelCircuitBreaker", fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId){
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}

	private ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
		logger.info("Fallback is executed because service is down: {}",ex.getMessage());
		User user = User.builder().email("dummy@gmail.com")
				.name("Dummy")
				.about("This is dummy response b/c some services are down")
				.userId("123")
				.build();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<List<User>> fetchAllUser(){
		
		List<User> allUser = userService.getAllUser();
		
		return ResponseEntity.ok(allUser);
	}
}
