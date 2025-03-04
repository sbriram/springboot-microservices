package com.microservice.communications;

import com.microservice.entities.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class RatingServiceCall {

    @Value("${external-url.ratings.user}")
    private String ratingByUserUrl;

    @Autowired
    private WebClient webClient;

    public List<Rating> getRatingsByUserId(String userId){

        List<Rating> ratingList = webClient
                .get()
                .uri(ratingByUserUrl+"/"+userId)
                .retrieve()
                .bodyToMono(List.class)
                .block();

        return ratingList;
    }
}
