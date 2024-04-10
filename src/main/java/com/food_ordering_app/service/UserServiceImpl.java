package com.food_ordering_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food_ordering_app.models.User;
import com.food_ordering_app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByJwtToken(String token) throws Exception {
        // Get email from the token
        String email = jwtProvider.getEmailFromJwtToken(token);
        // Find the user using this email
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("No user found");
        }
        return user;
    }

}
