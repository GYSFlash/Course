package com.hotel.service;


import com.hotel.model.Service;

import java.util.List;

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
