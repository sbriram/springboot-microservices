package com.microservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.entities.User;

public interface UserRepository extends JpaRepository<User, String>{

}
