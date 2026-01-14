package com.hotel.service;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hotel.model.Client;
import com.hotel.model.Service;
import com.hotel.model.Service.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
@Singleton
public class ServiceServiceImpl extends FileServiceImpl<Service> implements ServiceService {
    private Map<Long, Service> services = new HashMap<>();
    @InjectByType
    private ClientService clientService;
    public ServiceServiceImpl() {
    }

    @Override
    public void addService(Service service) {
        if(service.getClient() == null){
            System.out.println("Клиент не найден");
            return;
        }
        services.put(service.getId(), service);
    }
    @Override
    public void deleteService(Long id) {
        if (services.containsKey(id)) {
            services.remove(id);
        }
    }
    @Override
    public void updateService(Service service) {
        if (services.containsKey(service.getId())) {
            addService(service);
        }
    }
    @Override
    public List<Service> getAllServices() {
        List<Service> newServices = new ArrayList<>(services.values());
        return newServices;
    }
    @Override
    public List<Service> sort(String sortBy) {
        if(services.isEmpty()) {
            return null;
        }

        List<Service> serviceList = new ArrayList<>(services.values());

        switch (sortBy) {
            case "price" -> serviceList.sort(Comparator.comparing(Service::getServicePrice));
            case "date"-> serviceList.sort(Comparator.comparing(Service::getDate));
            case "type" -> serviceList.sort(Comparator.comparing(Service::getTypeService));
            default -> {
                System.out.println("Некорректный параметр сортировки");
                return null;
            }
        }

        return serviceList;
    }
    @Override
    public Service getServiceById(Long id) {
        return services.get(id);
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
