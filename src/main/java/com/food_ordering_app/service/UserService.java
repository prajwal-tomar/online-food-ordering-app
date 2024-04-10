package com.food_ordering_app.service;

import com.food_ordering_app.models.User;

public interface UserService {
    public User findUserByJwtToken(String token) throws Exception;

    public User findUserByEmail(String email) throws Exception;
}
