package com.microservice.impl;

import java.util.List;
import java.util.UUID;

import com.microservice.communications.RatingServiceCall;
import com.microservice.entities.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.entities.User;
import com.microservice.exceptions.ResourceNotFoundException;
import com.microservice.repo.UserRepository;
import com.microservice.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RatingServiceCall ratingServiceCall;
	
	@Override
	public User saveUser(User user) {
		
		// Generate Unique User Id
		
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {

		User user =  userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User with given id is not found on server!! : "+userId));

		List<Rating> ratingList = ratingServiceCall.getRatingsByUserId(userId);

		user.setRatings(ratingList);

		return user;
	}

}
