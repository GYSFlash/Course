package com.hotel.controller;

import com.hotel.dto.BookingRequestDTO;
import com.hotel.dto.BookingResponseDTO;
import com.hotel.dto.ClientResponseDTO;
import com.hotel.dto.RoomDTO;
import com.hotel.mapper.BookingMapper;
import com.hotel.model.Booking;
import com.hotel.model.Client;
import com.hotel.model.Room;
import com.hotel.service.BookingService;
import com.hotel.service.ClientService;
import com.hotel.service.RoomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController extends BaseController {
    private static final Logger logger = LogManager.getLogger(BookingController.class);
    private BookingService service;
    private ClientService clientService;
    private RoomService roomService;
    public BookingController(BookingService bookingService, ClientService clientService, RoomService roomService) {
        this.service = bookingService;
        this.clientService = clientService;
        this.roomService = roomService;
    }
    @PostMapping
    public ResponseEntity<Void> addBooking(@Valid @RequestBody BookingRequestDTO bookingDTO) {
        service.addBooking(bookingDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public List<BookingResponseDTO> showAllBookings() {
        return service.getAllBookings();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable("id") Long id) {

        if (service.getBookingById(id) == null) {
            logger.error("Бронирование с id: {} не найдено",id);
            return ResponseEntity.notFound().build();
        }
        logger.info("Удаление брони с id: {}",id);
        service.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBooking(@PathVariable("id") Long id, @Valid @RequestBody BookingRequestDTO booking) {
        service.updateBooking(id,booking);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/showFree/{checkIn}/{checkOut}")
    public List<RoomDTO> showFreeRoomsByDate(@PathVariable("checkIn") Date checkIn, @PathVariable("checkOut") Date checkOut) {
        return service.getFreeRoomsByDate(checkIn, checkOut);

    }
    @GetMapping("/showLastThree/{roomNumber}")
    public List<BookingResponseDTO> showLastThreeBookings(@PathVariable("roomNumber") int roomNumber) {
       return service.lastThreeBookingsByRooms(roomNumber);

    }
    @GetMapping("/sort/{sortBy}")
    public List<BookingResponseDTO> sortBookings(@PathVariable("sortBy") String sortBy) {
        return service.sort(sortBy);
    }
    public void importBookings() {
        service.addBookingFromFile();
    }
    public void exportBookings() {
        service.exportBookingToFile();
    }
    @GetMapping("/showClientStays/{roomNumber}")
    public List<ClientResponseDTO> getClientsStaysByRoom(@PathVariable("roomNumber") int roomNumber){
        return service.getClientsStaysByRoom(roomNumber);
    }
}
