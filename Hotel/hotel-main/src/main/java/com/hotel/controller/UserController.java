package com.hotel.controller;

import com.hotel.dto.UserDTO;
import com.hotel.dto.UserRegisterDTO;
import com.hotel.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO user, CsrfToken csrfToken) {
        String token = userService.login(user);
        return ResponseEntity.ok(token);
    }
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRegisterDTO user) {
        userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
