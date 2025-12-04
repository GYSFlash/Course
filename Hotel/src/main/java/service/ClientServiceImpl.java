package service;

import model.Client;
import model.Service;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class ClientServiceImpl extends FileServiceImpl<Client> implements ClientService  {
    private static ClientServiceImpl instance;
    private Map<Long, Client> clients = new HashMap<>();
    private ClientServiceImpl() {}
    public static ClientServiceImpl getInstance() {
        if(instance == null) {
            instance = new ClientServiceImpl();
        }
        return instance;
    }
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
    @Override
    public Client getClientById(Long id) {
        return clients.get(id);
    }
    @Override
    public void addClientFromFile(){
        String fileName = "clients.csv";
        importFromFile(fileName);
    }
    @Override
    public void exportClientsToFile() {

        String fileName = "clients.csv";
        exportToFile(fileName,getAllClients());

    }
    @Override
    public String writeModel(Client client){
        String s = client.getName() + "," + client.getSurname() + "," +
                dateFormat.format(client.getDateOfBirth()) + "," + client.getGender();
        return s;
    }
    @Override
    public void parseModel(String line){
        try{
            String[] values = line.split(",");
            String name = values[0];
            String surname = values[1];
            Date date = dateFormat.parse(values[2]);
            Client.Gender gender = Client.Gender.valueOf(values[3]);
            Client client = new Client(date, name, surname, gender);
            addClient(client);
        }
        catch (Exception e){
            System.out.println("Ошибка при парсинге строки: " + line);
        }
    }

}
