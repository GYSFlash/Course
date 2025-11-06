package Hotel.Service;

import Hotel.Model.Booking;

import java.util.Map;

public interface BookingServiceImpl {
    void addBooking(Booking booking);
    void deleteBooking(Long id);
    void updateBooking(Booking booking);
    Map<Long, Booking> getAllBookings();



}
