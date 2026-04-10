package com.hotel.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.dto.ClientRequestDTO;
import com.hotel.dto.UserDTO;
import com.hotel.dto.UserRegisterDTO;
import com.hotel.service.JWTService;
import com.hotel.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;
    private UserDTO userDTO;
    private UserRegisterDTO registerDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        userDTO = new UserDTO();
        userDTO.setUsername("ivan123");
        userDTO.setPassword("password123");

        registerDTO = new UserRegisterDTO();
        registerDTO.setUsername("ivan123");
        registerDTO.setPassword("password123");
        registerDTO.setClient(new ClientRequestDTO());
    }

    @Test
    void loginTest_success() throws Exception {
        String token = "jwt-token-123";
        when(userService.login(any(UserDTO.class))).thenReturn(token);

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());

        verify(userService).login(any(UserDTO.class));
    }

    @Test
    void registerTest_success() throws Exception {
        when(userService.register(any(UserRegisterDTO.class))).thenReturn(new com.hotel.model.User());

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isCreated());

        verify(userService).register(any(UserRegisterDTO.class));
    }
}