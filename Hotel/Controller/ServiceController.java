package Hotel.Controller;

import Hotel.Model.Client;
import Hotel.Model.Service;
import Hotel.Service.ClientService;
import Hotel.Service.ServiceService;


import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class ServiceController extends BaseController {
    public static ServiceController instance;
    private ServiceService services;
    private ClientService clientService;

    private ServiceController(ServiceService services, ClientService clientService) {
        this.services = services;
        this.clientService = clientService;
    }

    public static ServiceController getInstance(ServiceService services, ClientService clientService) {
        if(instance == null){
            instance = new ServiceController(services,clientService);
        }
        return instance;
    }
    public boolean addService() {
        String typeStr = readString("Тип услуги (ROOM/FOOD/OTHER)");
        Service.TypeService type = Service.TypeService.valueOf(typeStr.toUpperCase());

        String name = readString("Название услуги");

        double price = readDouble("Цена услуги");
        Duration duration;
        int hours = readInt("Продолжительность (часы)");
        int minutes = readInt("Продолжительность (минуты)");

        duration = Duration.ofHours(hours).plusMinutes(minutes);
        Long id = readLong("ID клиента");
        if (clientService.getClientById(id) == null) {
            return false;
        }
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
                String typeStr = readString("Новый тип услуги (ROOM/FOOD/OTHER)");
                Service.TypeService type =  Service.TypeService.valueOf(typeStr.toUpperCase());
                service.setTypeService(type);
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
