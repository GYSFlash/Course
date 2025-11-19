package Hotel.Service;

import Hotel.Model.Client;

import java.util.List;
import java.util.Map;

public interface ClientService {
    void addClient(Client client);
    void deleteClient(Long id);
    void updateClient(Client client);
    List<Client> getAllClients();
    int clientsCount ();
    Client getClientById(Long id);
}
