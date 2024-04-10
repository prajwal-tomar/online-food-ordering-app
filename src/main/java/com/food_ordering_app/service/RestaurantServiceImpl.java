package com.food_ordering_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food_ordering_app.models.Restaurant;
import com.food_ordering_app.models.User;
import com.food_ordering_app.repository.RestaurantRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant, Long id) {
        // Fetch the existing restaurant from the database
        Optional<Restaurant> existingRestaurant = restaurantRepository.findById(id);

        // Check if the restaurant exists
        if (!existingRestaurant.isPresent()) {
            throw new EntityNotFoundException("Restaurant with id " + id + " not found");
        }

        // Update the existing restaurant with the new values
        Restaurant restaurantToUpdate = existingRestaurant.get();
        if (restaurant.getName() != null) {
            restaurantToUpdate.setName(restaurant.getName());
        }
        if (restaurant.getDescription() != null) {
            restaurantToUpdate.setDescription(restaurant.getDescription());
        }
        if (restaurant.getCuisineType() != null) {
            restaurantToUpdate.setCuisineType(restaurant.getCuisineType());
        }
        if (restaurant.getAddress() != null) {
            restaurantToUpdate.setAddress(restaurant.getAddress());
        }
        if (restaurant.getContactInformation() != null) {
            restaurantToUpdate.setContactInformation(restaurant.getContactInformation());
        }
        if (restaurant.getOpeningHours() != null) {
            restaurantToUpdate.setOpeningHours(restaurant.getOpeningHours());
        }
        if (restaurant.getImages() != null) {
            restaurantToUpdate.setImages(restaurant.getImages());
        }
        if (restaurant.getOpen() != null) {
            restaurantToUpdate.setOpen(restaurant.getOpen());
        }

        // Save the updated restaurant back to the database
        return restaurantRepository.save(restaurantToUpdate);
    }

    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public List<Restaurant> getAllRestaurantsBySearchQuery(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) {
        // Fetch the existing restaurant from the database
        Optional<Restaurant> existingRestaurant = restaurantRepository.findById(restaurantId);

        // Check if the restaurant exists
        if (!existingRestaurant.isPresent()) {
            throw new EntityNotFoundException("Restaurant with id " + restaurantId + " not found");
        }

        Restaurant restaurantUpdated = existingRestaurant.get();
        restaurantUpdated.setOpen(!restaurantUpdated.getOpen());
        // Save this updated Restaurant into the db, toggling the status.
        return restaurantRepository.save(restaurantUpdated);

    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        return restaurant;
    }

    // To be implemented later
    @Override
    public User addToFavourites(User user, Long restaurantId) {
        return null;
    }
}
