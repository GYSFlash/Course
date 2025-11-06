package Hotel.Service;

import Hotel.Model.Client;

import java.util.HashMap;
import java.util.Map;

public class ClientService implements ClientServiceImpl{
    private Map<Long, Client> clients = new HashMap<>();

    @Override
    public void addClient(Client client) {
        clients.put(client.getId(), client);
    }
    @Override
    public void deleteClient(Long id) {
        if(clients.containsKey(id)) {
            clients.remove(id);
        }
    }
    @Override
    public void updateClient(Client client) {
        if(clients.containsKey(client.getId())) {
        clients.put(client.getId(), client);
        }
    }
    @Override
    public Map<Long, Client> getAllClients() {
        return clients;
    }
}
