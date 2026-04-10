package com.hotel.service;

import com.hotel.dto.ClientRequestDTO;
import com.hotel.dto.UserDTO;
import com.hotel.dto.UserRegisterDTO;
import com.hotel.model.Client;
import com.hotel.model.User;
import com.hotel.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService userService;

    private UserRegisterDTO registerDTO;
    private ClientRequestDTO clientRequest;
    private Client client;
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        clientRequest = new ClientRequestDTO();
        clientRequest.setName("Иван");
        clientRequest.setSurname("Иванов");

        client = new Client();
        client.setId(1L);
        client.setName("Иван");

        registerDTO = new UserRegisterDTO();
        registerDTO.setUsername("ivan123");
        registerDTO.setPassword("password123");
        registerDTO.setClient(clientRequest);

        user = new User();
        user.setId(1L);
        user.setUsername("ivan123");
        user.setPassword("encodedPassword");

        userDTO = new UserDTO();
        userDTO.setUsername("ivan123");
        userDTO.setPassword("password123");
    }

    @Test
    void registerTest_success() {
        when(clientService.addClient(clientRequest)).thenReturn(client);
        when(userRepository.create(any(User.class))).thenReturn(user);

        User result = userService.register(registerDTO);

        assertNotNull(result);
        assertEquals("ivan123", result.getUsername());
        verify(clientService).addClient(clientRequest);
        verify(userRepository).create(any(User.class));
    }

    @Test
    void registerTest_fail() {
        when(clientService.addClient(clientRequest)).thenThrow(new RuntimeException("Client error"));

        assertThrows(RuntimeException.class, () -> userService.register(registerDTO));
        verify(userRepository, never()).create(any());
    }

    @Test
    void loginTest_success() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken("ivan123")).thenReturn("jwt-token-123");

        String result = userService.login(userDTO);

        assertEquals("jwt-token-123", result);
        verify(authenticationManager).authenticate(any());
        verify(jwtService).generateToken("ivan123");
    }

    @Test
    void loginTest_fail_notAuthenticated() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        String result = userService.login(userDTO);

        assertEquals("Error", result);
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void loginTest_fail_throwsException() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Auth failed"));

        assertThrows(RuntimeException.class, () -> userService.login(userDTO));
        verify(jwtService, never()).generateToken(any());
    }
}