package Hotel.Service;

import Hotel.Model.Service.*;
import Hotel.Model.Service;

import java.util.*;

public class ServiceServiceImpl implements ServiceService {
    private Map<Long, Service> services = new HashMap<>();
    private final ServicePrice servicePrice = new ServicePrice();
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
            case "price":
                Collections.sort(serviceList,servicePrice);
                break;
            case "date":
                Collections.sort(serviceList);
                break;
        }

        return serviceList;
    }
}
