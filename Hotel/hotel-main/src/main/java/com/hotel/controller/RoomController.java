package com.hotel.controller;


import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.dto.RoomDTO;
import com.hotel.exceptions.NotFoundException;
import com.hotel.model.Room;
import com.hotel.service.RoomService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;
import java.math.BigDecimal;

import java.util.List;
@RestController
@RequestMapping("/rooms")
@PreAuthorize("hasRole('ADMIN')")
public class RoomController extends BaseController {
    private static final Logger logger = LogManager.getLogger(RoomController.class);
    private RoomService service;
    public RoomController(RoomService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<Void> addRoom(@Valid @RequestBody RoomDTO room) {
        logger.info("Добавление комнаты");
        service.addRoom(room);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public List<RoomDTO> showAllRooms() {
        return service.getAllRooms();
    }
    @DeleteMapping({"{roomNumber}"})
    public ResponseEntity<Void> deleteRoom(@PathVariable("roomNumber") Integer roomNumber) {
        logger.info("Удаление комнаты {}", roomNumber);
        service.deleteRoom(roomNumber);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("{roomNumber}")
    public ResponseEntity<Void> updateRoom(@PathVariable("roomNumber") Integer roomNumber,@Valid @RequestBody RoomDTO room) {
        logger.info("Обновление комнаты {}", roomNumber);
        if(findRoomByNumber(roomNumber) != null){
        service.updateRoom(room);
        return ResponseEntity.ok().build();
        }
        logger.error("Комната {} не найдена", roomNumber);
        throw new NotFoundException("Комната {} не найдена" + roomNumber);
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/status/{status}")
    public List<RoomDTO> showRoomsByStatus(@PathVariable("status") Room.Status status) {


        return service.getRoomByStatus(status);

    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/countfree")
    public int showFreeRoomsCount() {
        return service.countFreeRooms();
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/sort/{sortBy}")
    public List<RoomDTO> sortRooms(@PathVariable("sortBy") String sortBy) {

        return service.sort(sortBy);
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{roomNumber}")
    public RoomDTO findRoomByNumber(@PathVariable("roomNumber") Integer roomNumber) {
        return service.getRoomByRoomNumber(roomNumber);
    }
    public void importRooms() {
        service.addRoomsFromFile();

    }
    public void exportRooms() {
        service.exportRoomsToFile();
    }
    @PatchMapping("/{roomNumber}/{status}")
    public ResponseEntity<Void> changeRoomStatus(@PathVariable("roomNumber") Integer roomNumber, @PathVariable("status") Room.Status status) {
        service.changeStatus(roomNumber, status);
        return ResponseEntity.ok().build();
    }
}
