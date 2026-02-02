package com.hotel.service;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hotel.model.Client;
import com.hotel.repository.BookingRepository;
import com.hotel.repository.ClientRepository;
import com.hotel.repository.DBConnection;
import com.hotel.repository.ServiceRepository;

import java.util.*;
@Singleton
public class ClientServiceImpl extends FileServiceImpl<Client> implements ClientService  {
    private Map<Long, Client> clients = new HashMap<>();
    @InjectByType
    private ClientRepository clientRepository;
    @InjectByType
    private BookingRepository bookingRepository;
    @InjectByType
    private ServiceRepository serviceRepository;
    @InjectByType
    private DBConnection dbConnection;
    public ClientServiceImpl() {}

    @Override
    public void addClient(Client client) {
        clientRepository.create(client);
    }
    @Override
    public void deleteClient(Long id) {
            dbConnection.beginTransaction();
            try{
                bookingRepository.deleteByClientId(id);
                serviceRepository.deleteByClientId(id);
                clientRepository.deleteById(id);
                dbConnection.commitTransaction();
            } catch (Exception e) {
                dbConnection.rollbackTransaction();
            }
        }
    @Override
    public void updateClient(Client client) {
        clientRepository.update(client);
    }
    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    @Override
    public int clientsCount() {
        return clientRepository.count();
    }
    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }
    @Override
    public void addClientFromFile(){
        String fileName = "clients";
        importFromFile(fileName);
    }
    @Override
    public void exportClientsToFile() {

        String fileName = "clients";
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
    @Override
    public void parseModelJSON(List<Client> clients){
        for(Client client: clients){
            addClient(client);
        }
    }
    @Override
    public TypeReference<List<Client>> getTypeReference(){
        return new TypeReference<List<Client>>(){};
    }

}
