package Hotel.Service;


import Hotel.Model.Service;

import java.util.Map;

public interface ServiceServiceImpl {
    void addService(Service service);
    void deleteService(Long id);
    void updateService(Service service);
    Map<Long, Service> getAllServices();



}
