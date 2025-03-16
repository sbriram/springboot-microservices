package com.microservice.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.microservice.entities.Hotel;
import com.microservice.entities.Rating;
import com.microservice.external.services.HotelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.entities.User;
import com.microservice.exceptions.ResourceNotFoundException;
import com.microservice.repo.UserRepository;
import com.microservice.services.UserService;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WebClient.Builder webClient;

	@Autowired
	private HotelServices hotelServices;
	
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

		@SuppressWarnings("unchecked")
		Rating[] userRatings = webClient.build()
				.get()
				.uri("http://RATING-SERVICE/ratings/user/"+userId)
				.retrieve()
				.bodyToMono(Rating[].class)
				.block();

        assert userRatings != null;
		List<Rating> ratingList = Arrays.stream(userRatings).toList();
		List<Rating> ratings = ratingList.stream().map(rating -> {

//			Hotel hotel = webClient.build()
//					.get()
//					.uri("http://HOTEL-SERVICE/hotels/" + rating.getHotelId())
//					.retrieve()
//					.bodyToMono(Hotel.class)
//					.block();

			Hotel hotel = hotelServices.getHotel(rating.getHotelId());

			rating.setHotel(hotel);
			return rating;

		}).toList();
		user.setRatings(ratings);

		return user;
	}

}
