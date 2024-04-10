package com.food_ordering_app.response;

import com.food_ordering_app.models.USER_ROLE;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthResponse {

    private String jwt;
    private String message;
    private USER_ROLE role;

}
