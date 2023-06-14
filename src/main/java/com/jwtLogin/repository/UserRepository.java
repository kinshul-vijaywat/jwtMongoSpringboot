package com.jwtLogin.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jwtLogin.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByUserId(String userId);
}
