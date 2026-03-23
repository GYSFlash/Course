package com.hotel.service;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hotel.dto.ClientRequestDTO;
import com.hotel.dto.ClientResponseDTO;
import com.hotel.exceptions.NotFoundException;
import com.hotel.mapper.ClientMapper;
import com.hotel.model.Client;
import com.hotel.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@Service
@Transactional(readOnly = true)
public class ClientServiceImpl extends FileServiceImpl<Client> implements ClientService  {
    private static final Logger logger = LogManager.getLogger(ClientServiceImpl.class);
    private ClientRepository clientRepository;
    private BookingRepository bookingRepository;
    private ServiceRepository serviceRepository;
    private ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, BookingRepository bookingRepository, ServiceRepository serviceRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.bookingRepository = bookingRepository;
        this.serviceRepository = serviceRepository;
        this.clientMapper = clientMapper;
    }
    @Override
    @Transactional
    public Client addClient(ClientRequestDTO client) {
        Client newClient = clientRepository.create(clientMapper.toClient(client));
        logger.info("Успешное добавление клиента");
        return newClient;
    }
    @Override
    @Transactional
    public void deleteClient(Long id) {
            try{
                bookingRepository.deleteByClientId(id);
                serviceRepository.deleteByClientId(id);
                clientRepository.deleteById(id);
                logger.info("Успешное удаление клиента");
            } catch (Exception e) {
                logger.error("Ошибка при удалении клиента");
                throw e;
            }
        }
    @Override
    @Transactional
    public void updateClient(Long id,ClientRequestDTO client) {
        Client newClient = clientMapper.toClient(client);
        newClient.setId(id);
        clientRepository.update(newClient);
        logger.info("Успешное обновление клиента");
    }
    @Override
    public List<ClientResponseDTO> getAllClients() {
        logger.info("Получение всех клиентов");
        return clientMapper.toClientDTOList(clientRepository.findAll());
    }
    @Override
    public int clientsCount() {
        logger.info("Получение количества клиентов");
        return clientRepository.count();
    }
    @Override
    public ClientResponseDTO getClientById(Long id) {
        logger.info("Получение клиента с id: {}",id);
        return clientMapper.toClientDTO(clientRepository.findById(id).orElseThrow(()-> new NotFoundException("Клиент не найден")));
    }
    @Override
    public void addClientFromFile(){
        String fileName = "clients";
        importFromFile(fileName);
    }
    @Override
    public void exportClientsToFile() {

        String fileName = "clients";
        /*exportToFile(fileName,getAllClients());*/

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
            //addClient(client);
        }
        catch (Exception e){
            System.out.println("Ошибка при парсинге строки: " + line);
        }
    }
    @Override
    public void parseModelJSON(List<Client> clients){
        for(Client client: clients){
           // addClient(client);
        }
    }
    @Override
    public TypeReference<List<Client>> getTypeReference(){
        return new TypeReference<List<Client>>(){};
    }

}
