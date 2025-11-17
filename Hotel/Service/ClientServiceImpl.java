package Hotel.Service;

import Hotel.Model.Booking;
import Hotel.Model.Client;

import java.util.*;

public class ClientServiceImpl implements ClientService {
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
    public List<Client> getAllClients() {
        List<Client> newClients = new ArrayList<>(clients.values());
        return newClients;
    }
    @Override
    public int clientsCount() {
        return clients.size();
    }

}
