package Hotel.Service;

import Hotel.Model.Booking;

import java.util.HashMap;
import java.util.Map;

public class BookingService implements BookingServiceImpl {
    private Map<Long, Booking> bookings = new HashMap<>();

    public void deleteBooking(Long id) {
        if (bookings.containsKey(id)) {
            bookings.remove(id);
        }
    }
    @Override
    public void addBooking(Booking booking) {
        bookings.put(booking.getId(), booking);
    }
    @Override
    public Map<Long, Booking> getAllBookings() {
        return bookings;
    }
    @Override
    public void updateBooking(Booking booking) {
        if (bookings.containsKey(booking.getId())) {
            bookings.put(booking.getId(), booking);
        }
    }
}
