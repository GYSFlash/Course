package Hotel.Service;


import Hotel.Model.Service;

import java.util.List;
import java.util.Map;

public interface ServiceService {
    void addService(Service service);
    void deleteService(Long id);
    void updateService(Service service);
    List<Service> getAllServices();
    List<Service> sort(String sortby);
    Service getServiceById(Long id);
    void addServiceFromFile();
    void exportServiceToFile();
}
