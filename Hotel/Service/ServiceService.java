package Hotel.Service;

import Hotel.Model.Service;

import java.util.HashMap;
import java.util.Map;

public class ServiceService implements  ServiceServiceImpl{
    Map<Long, Service> services = new HashMap<>();
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
    public Map<Long, Service> getAllServices() {
        return services;
    }
}
