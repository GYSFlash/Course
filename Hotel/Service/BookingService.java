package Hotel.Service;

import Hotel.Model.Booking;
import Hotel.Model.Room;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookingService {
    void addBooking(Booking booking);
    void deleteBooking(Long id);
    void updateBooking(Booking booking);
    List<Booking> getAllBookings();
    List<Room> getFreeRoomsByDate(Date in, Date out,RoomService roomService);
    List<Booking> lastThreeBookingsByRooms(int RoomNumber);
    List<Booking> sort(String sortby);



}
