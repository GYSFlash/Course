package service;

import model.Client;

import java.util.List;

public interface ClientService {
    void addClient(Client client);
    void deleteClient(Long id);
    void updateClient(Client client);
    List<Client> getAllClients();
    int clientsCount ();
    Client getClientById(Long id);
    void addClientFromFile();
    void exportClientsToFile();
}
