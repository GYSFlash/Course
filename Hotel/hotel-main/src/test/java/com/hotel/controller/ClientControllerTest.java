package com.hotel.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.dto.ClientRequestDTO;
import com.hotel.dto.ClientResponseDTO;
import com.hotel.exceptions.NotFoundException;
import com.hotel.model.Client;
import com.hotel.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private ObjectMapper objectMapper;
    private ClientRequestDTO requestDTO;
    private ClientResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

        requestDTO = new ClientRequestDTO();
        requestDTO.setName("Иван");
        requestDTO.setSurname("Иванов");
        requestDTO.setGender(Client.Gender.MALE);
        requestDTO.setDateOfBirth(new Date());

        responseDTO = new ClientResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Иван");
    }

    @Test
    void addClientTest_success() throws Exception {
        when(clientService.addClient(any(ClientRequestDTO.class))).thenReturn(new Client());

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated());

        verify(clientService).addClient(any(ClientRequestDTO.class));
    }

    @Test
    void showAllClientsTest_success() throws Exception {
        List<ClientResponseDTO> list = new ArrayList<>();
        list.add(responseDTO);
        when(clientService.getAllClients()).thenReturn(list);

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk());

        verify(clientService).getAllClients();
    }

    @Test
    void deleteClientTest_success() throws Exception {
        doNothing().when(clientService).deleteClient(1L);

        mockMvc.perform(delete("/clients/1"))
                .andExpect(status().isNoContent());

        verify(clientService).deleteClient(1L);
    }

    @Test
    void showClientTest_success() throws Exception {
        when(clientService.getClientById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/clients/1"))
                .andExpect(status().isOk());

        verify(clientService).getClientById(1L);
    }

    @Test
    void updateClientTest_success() throws Exception {
        when(clientService.getClientById(1L)).thenReturn(responseDTO);
        doNothing().when(clientService).updateClient(eq(1L), any(ClientRequestDTO.class));

        mockMvc.perform(put("/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());

        verify(clientService).updateClient(eq(1L), any(ClientRequestDTO.class));
    }

    @Test
    void showClientsCountTest_success() throws Exception {
        when(clientService.clientsCount()).thenReturn(5);

        mockMvc.perform(get("/clients/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }
}