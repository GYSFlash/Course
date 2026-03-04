package com.hotel.controller;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.dto.ServiceRequestDTO;
import com.hotel.dto.ServiceResponseDTO;
import com.hotel.model.Client;
import com.hotel.model.Service;
import com.hotel.service.ClientService;
import com.hotel.service.ServiceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/services")
public class ServiceController extends BaseController {
    private static final Logger logger = LogManager.getLogger(ServiceController.class);
    private ServiceService services;
    private ClientService clientService;
    public ServiceController(ServiceService services,ClientService clientService) {
        this.services = services;
        this.clientService = clientService;
    }
    @PostMapping
    public ResponseEntity<Void> addService(@Valid @RequestBody ServiceRequestDTO service) {
        logger.info("Добавление услуги");
        services.addService(service);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public List<ServiceResponseDTO> showAllServices() {
        return services.getAllServices();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable("id") Long id) {
       if (services.getServiceById(id) == null) {
           logger.error("Услуга с id {} не найдена", id);
           return ResponseEntity.notFound().build();
       }
        logger.info("Удаление услуги c id {}", id);
        services.deleteService(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateService(@PathVariable("id") Long id, @Valid @RequestBody ServiceRequestDTO service) {
        logger.info("Обновление услуги c id {}", id);
        if(services.getServiceById(id) != null) {
            services.updateService(id, service);
        return ResponseEntity.ok().build();
        }
        logger.error("Услуга с id {} не найдена", id);
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/sort/{sortBy}")
    public List<ServiceResponseDTO> sortServices(@PathVariable("sortBy") String sortBy) {
        return services.sort(sortBy);

    }
    @GetMapping("/{id}")
    public ServiceResponseDTO getServiceById(@PathVariable("id") Long id) {
        return services.getServiceById(id);
    }
    public void exportServices() {
        services.exportServiceToFile();

    }
    public void importServices() {
        services.addServiceFromFile();

    }
}
