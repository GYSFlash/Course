package service;

import model.Client;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClientServiceImpl implements ClientService,FileService {
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

        try (BufferedReader br = new BufferedReader(new FileReader(FileService.checkFile(clientFile)))){
            String line;
            Date date;
            String name, surname;
            Client.Gender gender;
            while ((line = br.readLine()) != null) {
                String values[] = line.split(",");
                try {
                if(values.length == 4) {
                    name = values[0];
                    surname = values[1];
                    date = dateFormat.parse(values[2]);
                    gender = Client.Gender.valueOf(values[3]);
                    Client client = new Client(date, name, surname, gender);
                    addClient(client);
                }
                }
                catch (Exception e) {
                    System.out.println("Ошибка в данных клиентов");;
                }

            }
        }
        catch (Exception e) {
            System.out.println("Ошибка чтения данных из файла");
        }
    }
    @Override
    public void exportClientsToFile() {

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FileService.checkFile(clientFile)))) {
            for(Client client : clients.values()) {
                String line = client.getName() + "," + client.getSurname() + "," +
                        dateFormat.format(client.getDateOfBirth()) + "," + client.getGender();
                bw.write(line);
                bw.newLine();
            }
        }
        catch (Exception e) {
            System.out.println("Ошибка записи данных в файл");
        }

    }

}
