package com.food_ordering_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food_ordering_app.models.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
