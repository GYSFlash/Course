package com.hotel.service;

import com.hotel.dto.UserDTO;
import com.hotel.dto.UserRegisterDTO;
import com.hotel.mapper.ClientMapper;
import com.hotel.model.Client;
import com.hotel.model.User;
import com.hotel.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private UserRepository userRepository;
    private ClientService clientService;
    private JWTService jwtService;
    private AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, ClientService clientService, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.clientService = clientService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Transactional
    public User register(UserRegisterDTO user) {
        Client savedClient = clientService.addClient(user.getClient());
        User newUser = new User(user.getUsername(), user.getPassword());
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setClient(savedClient);
        userRepository.create(newUser);
        return newUser;
    }
    @Transactional
    public String login(UserDTO user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else{
            return "Error";
        }
    }
}
