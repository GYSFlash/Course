package Hotel.Service;

import Hotel.Model.Client;

import java.util.Map;

public interface ClientServiceImpl {
    void addClient(Client client);
    void deleteClient(Long id);
    void updateClient(Client client);
    Map<Long, Client> getAllClients();

}
