package com.hotel.service;

import com.hotel.dto.ClientRequestDTO;
import com.hotel.dto.ClientResponseDTO;
import com.hotel.exceptions.NotFoundException;
import com.hotel.mapper.ClientMapper;
import com.hotel.model.Client;
import com.hotel.repository.BookingRepository;
import com.hotel.repository.ClientRepository;
import com.hotel.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private ClientMapper clientMapper;
    @InjectMocks
    private ClientServiceImpl clientService;

    private ClientRequestDTO request;
    private ClientResponseDTO response;
    private Client client;

    @BeforeEach
    public void setUp() {
        request = new ClientRequestDTO();
        request.setName("Иван");
        request.setSurname("Иванов");
        request.setGender(Client.Gender.MALE);
        request.setDateOfBirth(new Date());
        client = new Client();
        client.setId(1L);
        client.setName("Иван");
        client.setSurname("Иванов");
        client.setGender(Client.Gender.MALE);
        client.setDateOfBirth(new Date());

        response = new ClientResponseDTO();
        response.setId(1L);
        response.setName("Иван");
        response.setSurname("Иванов");
    }
    @Test
    void addClient_success() {
        when(clientMapper.toClient(request)).thenReturn(client);
        when(clientRepository.create(client)).thenReturn(client);

        Client result = clientService.addClient(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(clientRepository).create(client);
    }

    @Test
    void deleteClient_success() {
        when(bookingRepository.deleteByClientId(1L)).thenReturn(true);
        when(serviceRepository.deleteByClientId(1L)).thenReturn(true);
        when(clientRepository.deleteById(1L)).thenReturn(true);

        clientService.deleteClient(1L);

        verify(bookingRepository).deleteByClientId(1L);
        verify(serviceRepository).deleteByClientId(1L);
        verify(clientRepository).deleteById(1L);
    }

    @Test
    void deleteClient_fail() {
        doThrow(new RuntimeException("DB error")).when(clientRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> clientService.deleteClient(1L));
        verify(bookingRepository).deleteByClientId(1L);
        verify(serviceRepository).deleteByClientId(1L);
        verify(clientRepository).deleteById(1L);
    }

    @Test
    void updateClient_success() {
        when(clientMapper.toClient(request)).thenReturn(client);

        clientService.updateClient(1L, request);

        verify(clientMapper).toClient(request);
        verify(clientRepository).update(client);
    }

    @Test
    void updateClient_fail() {
        assertThrows(NullPointerException.class, () -> clientService.updateClient(1L, null));
        verify(clientRepository, never()).update(any());
    }

    @Test
    void getAllClients_success() {
        when(clientRepository.findAll()).thenReturn(List.of(client));
        when(clientMapper.toClientDTOList(List.of(client))).thenReturn(List.of(response));

        List<ClientResponseDTO> result = clientService.getAllClients();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(clientRepository).findAll();
    }

    @Test
    void getAllClients_empty() {
        when(clientRepository.findAll()).thenReturn(List.of());
        when(clientMapper.toClientDTOList(List.of())).thenReturn(List.of());

        List<ClientResponseDTO> result = clientService.getAllClients();

        assertTrue(result.isEmpty());
        verify(clientRepository).findAll();
    }

    @Test
    void getClientById_success() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientMapper.toClientDTO(client)).thenReturn(response);

        ClientResponseDTO result = clientService.getClientById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(clientRepository).findById(1L);
    }

    @Test
    void getClientById_fail() {
        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clientService.getClientById(999L));
        verify(clientRepository).findById(999L);
    }

    @Test
    void clientsCount_success() {
        when(clientRepository.count()).thenReturn(5);

        int result = clientService.clientsCount();

        assertEquals(5, result);
        verify(clientRepository).count();
    }

    @Test
    void clientsCount_zero() {
        when(clientRepository.count()).thenReturn(0);

        int result = clientService.clientsCount();

        assertEquals(0, result);
        verify(clientRepository).count();
    }
}
