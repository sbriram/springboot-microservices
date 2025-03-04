package com.hotel.services;

import java.util.List;

import com.hotel.entities.Hotel;

public interface HotelServices {
	
	Hotel createHotel(Hotel hotel);
	List<Hotel> getAllHotel();
	Hotel getHotel(String id);

}
