package com.food_ordering_app.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
