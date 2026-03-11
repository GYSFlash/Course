package com.hotel.service;


import com.hotel.dto.ServiceRequestDTO;
import com.hotel.dto.ServiceResponseDTO;
import com.hotel.model.Service;

import java.util.List;

public interface ServiceService {
    void addService(ServiceRequestDTO service);
    void deleteService(Long id);
    void updateService(Long id, ServiceRequestDTO service);
    List<ServiceResponseDTO> getAllServices();
    List<ServiceResponseDTO> sort(String sortby);
    ServiceResponseDTO getServiceById(Long id);
    void addServiceFromFile();
    void exportServiceToFile();
}
