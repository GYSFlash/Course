package com.hotel.controller;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.dto.ClientRequestDTO;
import com.hotel.dto.ClientResponseDTO;
import com.hotel.model.Client;

import com.hotel.service.ClientService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/clients")
public class ClientController extends BaseController{
    private static final Logger logger = LogManager.getLogger(ClientController.class);
    private ClientService service;
    public ClientController(ClientService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<Void> addClient(@Valid @RequestBody ClientRequestDTO client) {
        service.addClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping
    public List<ClientResponseDTO> showAllClients() {
        return service.getAllClients();
    }
    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteClient(@PathVariable("id") Long id) {
        logger.info("Удаление клиента с id: {}",id);
        service.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ClientResponseDTO showClient(@PathVariable("id") Long id) {
        return service.getClientById(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClient(@PathVariable("id") Long id, @Valid @RequestBody ClientRequestDTO client) {
        logger.info("Обновление клиента с id: {}",id);
        if(showClient(id) != null) {
            service.updateClient(id, client);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/count")
    public int showClientsCount() {
        int count = service.clientsCount();
        return count;
    }
    public void importClients() {
        service.addClientFromFile();

    }
    public void exportClients() {
        service.exportClientsToFile();

    }

}
