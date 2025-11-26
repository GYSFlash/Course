package Hotel.Service;

import Hotel.Model.Service.*;
import Hotel.Model.Service;

import java.util.*;

public class ServiceServiceImpl implements ServiceService {
    private static ServiceServiceImpl instance;
    private Map<Long, Service> services = new HashMap<>();
    private ServiceServiceImpl() {}
    public static ServiceServiceImpl getInstance() {
        if(instance == null) {
            instance = new ServiceServiceImpl();
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
}
