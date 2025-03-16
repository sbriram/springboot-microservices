package com.microservice.external.services;

import com.microservice.entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelServices {

    @GetMapping("hotels/{hotelId}")
    public Hotel getHotel(@PathVariable String hotelId);
}
