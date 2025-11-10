package Hotel.Service;

import Hotel.Model.Booking;
import Hotel.Model.Room;

import java.util.*;

public class BookingServiceImpl implements BookingService {
    private Map<Long, Booking> bookings = new HashMap<>();
    private Map<Integer, Room> rooms = new HashMap<>();
   @Override
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
    public List<Booking> getAllBookings() {
        List<Booking> newBookings =  new ArrayList<>(bookings.values());
        return newBookings;
    }
    @Override
    public void updateBooking(Booking booking) {
        if (bookings.containsKey(booking.getId())) {
            bookings.put(booking.getId(), booking);
        }
    }
    @Override
    public List<Room> getFreeRoomsByDate(Date in, Date out) {
        List<Room> busyRooms = new ArrayList<>();  // комнаты занятые в этот период

        // Сначала формируем список занятых комнат
        for (Booking booking : bookings.values()) {
            Room room = booking.getRoom();
            if (!out.before(booking.getCheckInDate()) && !in.after(booking.getCheckOutDate())) {
                if (!busyRooms.contains(room)) {
                    busyRooms.add(room);
                }
            }
        }

        // Теперь формируем список свободных комнат
        List<Room> freeRooms = new ArrayList<>();
        for (Room room : rooms.values()) {
            if (!busyRooms.contains(room)) {
                freeRooms.add(room);
            }
        }

        return freeRooms;
    }

    @Override
    public List<Booking> lastThreeBookingsByRooms(int roomNumber) {

        List<Booking> newBookings =  new ArrayList<>(bookings.values());
        List<Booking> result = new ArrayList<>();
        Collections.reverse(newBookings);
        int count = 0;
        for (Booking booking : newBookings) {
            if (booking.getRoom().getRoomNumber() == roomNumber) {
                result.add(booking);
                count++;
            }
            if (count == 3) {
                break;
            }
        }
        return result;
    }
}
