package com.hotel.service;

import com.hotel.dto.BookingRequestDTO;
import com.hotel.dto.BookingResponseDTO;
import com.hotel.dto.ClientResponseDTO;
import com.hotel.dto.RoomDTO;
import com.hotel.model.Booking;
import com.hotel.model.Client;
import com.hotel.model.Room;

import java.util.Date;
import java.util.List;

public interface BookingService {
    void addBooking(BookingRequestDTO booking);
    void deleteBooking(Long id);
    void updateBooking(Long id,BookingRequestDTO booking);
    List<BookingResponseDTO> getAllBookings();
    List<RoomDTO> getFreeRoomsByDate(Date in, Date out);
    List<BookingResponseDTO> lastThreeBookingsByRooms(int RoomNumber);
    List<BookingResponseDTO> sort(String sortby);
    BookingResponseDTO getBookingById(Long id);
    void addBookingFromFile();
    void exportBookingToFile();
    public List<ClientResponseDTO> getClientsStaysByRoom(int roomNumber);

}
