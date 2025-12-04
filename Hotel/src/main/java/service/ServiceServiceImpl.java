package service;

import model.Booking;
import model.Client;
import model.Service.*;
import model.Service;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class ServiceServiceImpl extends FileServiceImpl<Service> implements ServiceService {
    private static ServiceServiceImpl instance;
    private Map<Long, Service> services = new HashMap<>();
    private ClientService clientService;
    private ServiceServiceImpl(ClientService clientService) {
        this.clientService = clientService;
    }
    public static ServiceServiceImpl getInstance(ClientService clientService) {
        if(instance == null) {
            instance = new ServiceServiceImpl(clientService);
        }
        return instance;
    }
    @Override
    public void addService(Service service) {
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
            services.put(service.getId(), service);
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
        String fileName = "services.csv";
        importFromFile(fileName);

    }
    @Override
    public void exportServiceToFile() {
        String fileName = "services.csv";
        exportToFile(fileName,getAllServices());
    }
    @Override
    public String writeModel(Service service){

        String s = service.getTypeService() + "," + service.getServiceName() + "," + service.getServicePrice() + ","
                + service.getDuration().toMinutes() + "," + service.getClient().getId() + ","
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
}
