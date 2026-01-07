package com.hotel.controller;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.model.Client;
import com.hotel.model.Service;
import com.hotel.service.ClientService;
import com.hotel.service.ServiceService;


import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.List;
@Singleton
public class ServiceController extends BaseController {

    @InjectByType
    private ServiceService services;
    @InjectByType
    private ClientService clientService;

    public ServiceController() {
    }

    public boolean addService() {
        Service.TypeService type;
        try {
            String typeStr = readString("Тип услуги (ROOM/FOOD/OTHER)");
            type = Service.TypeService.valueOf(typeStr.toUpperCase());
        }
        catch (Exception e){
            System.out.println("Недопустимый тип услуги");
            return false;
        }
        String name = readString("Название услуги");

        double price = readDouble("Цена услуги");
        Duration duration;
        int hours = readInt("Продолжительность (часы)");
        int minutes = readInt("Продолжительность (минуты)");

        duration = Duration.ofHours(hours).plusMinutes(minutes);
        Long id = readLong("ID клиента");
        Client client = clientService.getClientById(id);
        Service service = new Service(type, name, BigDecimal.valueOf(price),
                duration,client , new Date());
        services.addService(service);
        return true;
    }

    public List<Service> showAllServices() {
        return services.getAllServices();

    }

    public boolean deleteService() {
        Long id = readLong("ID услуги для удаления");
       if (services.getServiceById(id) == null) {
           return false;
       }
        services.deleteService(id);
        return true;
    }

    public boolean updateService() {
        Long id = readLong("ID услуги для обновления");
        Service service = services.getServiceById(id);
        String change = readString("Изменить (type/name/price/duration/client)");
        switch (change) {
            case "type" -> {
                try {
                    String typeStr = readString("Новый тип услуги (ROOM/FOOD/OTHER)");
                    Service.TypeService type = Service.TypeService.valueOf(typeStr.toUpperCase());
                    service.setTypeService(type);
                }catch (Exception e){
                    System.out.println("Недопустимый тип услуги");
                    return false;
                }
            }
            case "name" -> {String name = readString("Новое название услуги");
                service.setServiceName(name);
            }
            case "price" -> {double price = readDouble("Новая цена услуги");
                service.setServicePrice(BigDecimal.valueOf(price));
            }
            case "duration" -> {int hours = readInt("Новая продолжительность (часы)");
                int minutes = readInt("Продолжительность (минуты)");
                Duration durtion = Duration.ofHours(hours).plusMinutes(minutes);
                service.setDuration(durtion);
            }
            case "client" -> { Long idClient = readLong("ID клиента");
                Client client = clientService.getClientById(idClient);
                service.setClient(client);
            }
            default -> {
                return false;
            }

        }
        services.updateService(service);
        return true;
    }

    public List<Service> sortServices() {
        String sortBy = readString("Сортировать по (price/date/type)");
        return services.sort(sortBy);

    }
    public Service getServiceById() {
        return services.getServiceById(readLong("ID услуги"));
    }
    public void exportServices() {
        services.exportServiceToFile();

    }
    public void importServices() {
        services.addServiceFromFile();

    }
}
