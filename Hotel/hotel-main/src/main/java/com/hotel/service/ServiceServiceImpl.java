package com.hotel.service;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hotel.model.Client;
import com.hotel.model.Service;
import com.hotel.model.Service.*;
import com.hotel.repository.ServiceRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
@org.springframework.stereotype.Service
public class ServiceServiceImpl extends FileServiceImpl<Service> implements ServiceService {
    private static final Logger logger = LogManager.getLogger(ServiceServiceImpl.class);
    private ClientService clientService;

    private ServiceRepository serviceRepository;
    public ServiceServiceImpl(ClientService clientService, ServiceRepository serviceRepository) {
        this.clientService = clientService;
        this.serviceRepository = serviceRepository;

    }
    @Override
    public void addService(Service service) {
        if(service.getClient() == null){
            logger.error("Клиент услуги не найден");
            return;
        }
        serviceRepository.create(service);
        logger.info("Успешное добавление услуги");
    }
    @Override
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
        logger.info("Услуга удалена");
    }
    @Override
    public void updateService(Service service) {
        serviceRepository.update(service);
        logger.info("Услуга обновлена");
    }
    @Override
    public List<Service> getAllServices() {
        logger.info("Получение всех услуг");
        return serviceRepository.findAll();
    }
    @Override
    public List<Service> sort(String sortBy) {
        logger.info("Сортировка услуг по {}", sortBy);
        List<Service> serviceList = getAllServices();
        if(serviceList.isEmpty()) {
            return null;
        }
        switch (sortBy) {
            case "price" -> serviceList.sort(Comparator.comparing(Service::getServicePrice));
            case "date"-> serviceList.sort(Comparator.comparing(Service::getDate));
            case "type" -> serviceList.sort(Comparator.comparing(Service::getTypeService));
            default -> {
                logger.error("Некорректный параметр сортировки");
                return null;
            }
        }

        return serviceList;
    }
    @Override
    public Service getServiceById(Long id) {
        logger.info("Получение услуги по id: {}", id);
        return serviceRepository.findById(id).orElse(null);
    }
    @Override
    public void addServiceFromFile(){
        String fileName = "services";
        importFromFile(fileName);

    }
    @Override
    public void exportServiceToFile() {
        String fileName = "services";
        exportToFile(fileName,getAllServices());
    }
    @Override
    public String writeModel(Service service){

        String s = service.getTypeService() + "," + service.getServiceName() + "," + service.getServicePrice() + ","
                +service.getDuration().toHours() + ":" + service.getDuration().toMinutes()%60 + "," + service.getClient().getId() + ","
                + dateFormat.format(service.getDate());
        return s;
    }
    @Override
    public void parseModel(String line){
        try{
        String[] values = line.split(",");
        TypeService typeService = TypeService.valueOf(values[0]);
        String serviceName = values[1];
        BigDecimal price = new BigDecimal(values[2]);
        String timeStr = values[3];
        String[] parts = timeStr.split(":");

        int hours = Integer.parseInt(parts[0].trim());
        int minutes = Integer.parseInt(parts[1].trim());
        Duration duration = Duration.ofHours(hours).plusMinutes(minutes);
        Client client = clientService.getClientById(Long.parseLong(values[4]));
        Date date = dateFormat.parse(values[5]);
        Service service = new Service(typeService, serviceName, price, duration, client, date);
        addService(service);
        }
        catch (Exception e){
            System.out.println("Ошибка при парсинге строки: " + line);
        }
    }
    @Override
    public void parseModelJSON(List<Service> list){
        for(Service service : list){
            addService(service);
        }
    }
    @Override
    public TypeReference<List<Service>> getTypeReference(){
        return new TypeReference<List<Service>>(){};
    }
}
