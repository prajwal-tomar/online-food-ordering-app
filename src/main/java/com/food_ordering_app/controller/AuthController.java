package com.food_ordering_app.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food_ordering_app.models.Cart;
import com.food_ordering_app.models.USER_ROLE;
import com.food_ordering_app.models.User;
import com.food_ordering_app.repository.CartRepository;
import com.food_ordering_app.repository.UserRepository;
import com.food_ordering_app.request.LoginRequest;
import com.food_ordering_app.response.AuthResponse;
import com.food_ordering_app.service.CustomUserDetailsService;
import com.food_ordering_app.service.JwtProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService customUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        // to check if email already exists
        User isEmailExists = userRepository.findByEmail(user.getEmail());
        if (isEmailExists != null) {
            throw new Exception("Email already in use with someother account.");
        }

        // Create new user
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFullName(user.getFullName());
        newUser.setRole(user.getRole());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(newUser);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);

        cartRepository.save(cart);

        // Getting and Setting Authentication
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(jwt, "User registered successfully", savedUser.getRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    // how this is different from signup in terms of security is when registering
    // the user we have to create authentication object and then set it in the
    // context.
    // but when a user is trying to log in, then it means he was registered before
    // and his authentication object is already there, we just need to get it from
    // class CustomerUserDetailsService which we create for overriding the default
    // behaviour or spring security.
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signInUserHandler(@RequestBody LoginRequest req) throws Exception {
        String username = req.getEmail();
        String password = req.getPassword();

        Authentication authentication = authenticate(username, password);
        // For example, if you have a method that processes a collection of
        // GrantedAuthority objects, you can use ? extends GrantedAuthority to allow the
        // method to accept a collection of GrantedAuthority objects or any collection
        // of objects that are subclasses of GrantedAuthority. This makes your code more
        // flexible and reusable, as it can handle different types of GrantedAuthority
        // objects without needing to be modified for each specific type
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String authority = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(jwt, "User logged in successfully", USER_ROLE.valueOf(authority));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        // need to check if this user exists in the db by using our custom
        // implementation of UserDetailService.
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        // If userDetails is null it means it was never set in the context which means
        // it was never registered.
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username.");
        }
        // Check the password
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password. Please try again.");
        }
        // If all good, return the Authenticate object
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
