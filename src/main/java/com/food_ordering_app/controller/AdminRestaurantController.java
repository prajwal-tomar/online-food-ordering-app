package com.food_ordering_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_ordering_app.models.Restaurant;
import com.food_ordering_app.models.User;
import com.food_ordering_app.service.RestaurantService;
import com.food_ordering_app.service.UserService;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping("/create-restaurant")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant,
            @RequestHeader("Authorization") String jwt)
            throws Exception {
        User owner = userService.findUserByJwtToken(jwt);
        restaurant.setOwner(owner);
        return new ResponseEntity<>(restaurantService.createRestaurant(restaurant), HttpStatus.CREATED);
    }

    @PutMapping("/update-restaurant/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody Restaurant restaurant,
            @RequestHeader("Authorization") String jwt, @PathVariable Long id)
            throws Exception {
        return new ResponseEntity<>(restaurantService.updateRestaurant(restaurant, id), HttpStatus.OK);
    }

    @GetMapping("/update-restaurant/{id}/status")
    public ResponseEntity<Restaurant> toggleRestaurantStatus(@RequestHeader("Authorization") String jwt,
            @PathVariable Long id)
            throws Exception {
        return new ResponseEntity<>(restaurantService.updateRestaurantStatus(id), HttpStatus.OK);
    }

    @DeleteMapping("delete-restaurant/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>("Restaurant deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByLoggedInUserId(@RequestHeader("Authorization") String jwt)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
