package Hotel.Service;

import Hotel.Model.Booking;
import Hotel.Model.Room;
import java.util.*;

public class BookingServiceImpl implements BookingService {
    private Map<Long, Booking> bookings = new HashMap<>();
    private RoomServiceImpl roomService;
    public BookingServiceImpl(RoomServiceImpl roomService) {
        this.roomService = roomService;
    }
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
        List<Room> busyRooms = new ArrayList<>();
        List<Room> allRooms = roomService.getAllRooms();

        // Находим занятые номера
        for (Booking booking : bookings.values()) {
            Room room = booking.getRoom();
            if (!out.before(booking.getCheckInDate()) && !in.after(booking.getCheckOutDate())) {
                if (!busyRooms.contains(room)) {
                    busyRooms.add(room);
                }
            }
        }


        List<Room> freeRooms;
        freeRooms = allRooms.stream().filter(room -> !busyRooms.contains(room)&&room.getStatus() != Room.Status.REPAIR).toList();

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
    @Override
    public List<Booking> sort(String sortBy) {
        if(bookings.isEmpty()) {
            return null;
        }

        List<Booking> bookingList = new ArrayList<>(bookings.values());

        switch (sortBy) {
            case "client" -> bookingList.sort(Comparator.comparing(Booking::getClient));
            case "checkOutDate"-> bookingList.sort(Comparator.comparing(Booking::getCheckOutDate));
            default -> {
                System.out.println("Некорректный параметр сортировки");
                return null;
            }
        }
        return bookingList;
    }
    @Override
    public Booking getBookingById(Long id) {
        return bookings.get(id);
    }
}
