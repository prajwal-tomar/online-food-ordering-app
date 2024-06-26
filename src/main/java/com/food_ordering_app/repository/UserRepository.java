package com.food_ordering_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food_ordering_app.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String username);
}
