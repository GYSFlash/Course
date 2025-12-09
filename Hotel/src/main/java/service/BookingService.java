package service;

import model.Booking;
import model.Room;

import java.util.Date;
import java.util.List;

public interface BookingService {
    void addBooking(Booking booking);
    void deleteBooking(Long id);
    void updateBooking(Booking booking);
    List<Booking> getAllBookings();
    List<Room> getFreeRoomsByDate(Date in, Date out);
    List<Booking> lastThreeBookingsByRooms(int RoomNumber);
    List<Booking> sort(String sortby);
    Booking getBookingById(Long id);
    void addBookingFromFile();
    void exportBookingToFile();


}
