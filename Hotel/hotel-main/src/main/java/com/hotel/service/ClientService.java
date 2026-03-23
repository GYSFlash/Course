package com.hotel.service;

import com.hotel.dto.ClientRequestDTO;
import com.hotel.dto.ClientResponseDTO;
import com.hotel.model.Client;

import java.sql.Date;
import java.util.List;

public interface ClientService {
    Client addClient(ClientRequestDTO client);
    void deleteClient(Long id);
    void updateClient(Long id,ClientRequestDTO client);
    List<ClientResponseDTO> getAllClients();
    int clientsCount ();
    ClientResponseDTO getClientById(Long id);
    void addClientFromFile();
    void exportClientsToFile();
}
