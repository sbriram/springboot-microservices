package com.microservice.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.microservice.entities.User;

public interface UserService {
	
	User saveUser(User user);
	
	List<User> getAllUser();
	
	User getUser(String userId);

}
