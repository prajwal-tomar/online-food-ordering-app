package com.food_ordering_app.service;

import java.util.List;

import com.food_ordering_app.models.Restaurant;
import com.food_ordering_app.models.User;

public interface RestaurantService {

    // Create
    Restaurant createRestaurant(Restaurant restaurant);

    // Read
    Restaurant getRestaurantById(Long id);

    List<Restaurant> getAllRestaurants();

    List<Restaurant> getAllRestaurantsBySearchQuery(String keyword);

    // Update
    Restaurant updateRestaurant(Restaurant restaurant, Long id);

    Restaurant updateRestaurantStatus(Long restaurantId);

    User addToFavourites(User user, Long restaurantId);

    // Delete
    void deleteRestaurant(Long id);

    Restaurant getRestaurantByUserId(Long userId) throws Exception;
}
