package Hotel.Controller;

import Hotel.Model.Booking;
import Hotel.Model.Client;
import Hotel.Model.Room;
import Hotel.Service.BookingService;
import Hotel.Service.ClientService;
import Hotel.Service.RoomService;

import java.util.Date;
import java.util.List;

public class BookingController extends BaseController {
    private BookingService service;
    private ClientService clientService;
    private RoomService roomService;

    public BookingController( BookingService service, ClientService clientService, RoomService roomService) {
        this.service = service;
        this.clientService = clientService;
        this.roomService = roomService;
        }



    public boolean addBooking() {
        String dateStr = readString("Дата въезда (гггг-мм-дд)");
        Date checkIn = parseDate(dateStr);
        dateStr = readString("Дата выезда (гггг-мм-дд)");
        Date checkOut = parseDate(dateStr);
        Long id = readLong("ID клиента");
        Client client = clientService.getClientById(id);
        int roomNumber = readInt("Номер комнаты");
        Room room = roomService.getRoomByRoomNumber(roomNumber);
        Booking booking = new Booking(checkIn, room, client, checkOut);
        service.addBooking(booking);
        return true;

    }

    public List<Booking> showAllBookings() {
        return service.getAllBookings();
    }

    public boolean deleteBooking() {
        Long id = readLong("ID бронирования для удаления");
        if (service.getBookingById(id) == null) {
            return false;
        }
        service.deleteBooking(id);
        return true;
    }

    public boolean updateBooking() {
        Long id = readLong("ID бронирования для обновления");
        if (service.getBookingById(id) == null) {
            return false;
        }
        String chance = readString("Введите поле для изменения (dateIn, dateOut, room, client)");
        Booking booking = service.getBookingById(id);
        switch (chance) {
            case "dateIn" -> {
                String dateStr = readString("Новая дата заезда (гггг-мм-дд)");
                Date newDate = parseDate(dateStr);
                booking.setCheckInDate(newDate);
            }
            case "dateOut" -> {
                String dateStr = readString("Новая дата выезда (гггг-мм-дд)");
                Date newDate = parseDate(dateStr);
                booking.setCheckOutDate(newDate);
            }
            case "client" -> {
                Long idClient = readLong("Новое id клиента");
                Client client = clientService.getClientById(idClient);
                booking.setClient(client);
            }
            case "room" -> {
                int roomNumber = readInt("Новая комната");
                Room room = roomService.getRoomByRoomNumber(roomNumber);
                booking.setRoom(room);
            }
            default -> { return false;
            }

        }
        service.updateBooking(booking);
        return true;
    }

    public List<Room> showFreeRoomsByDate() {
        String checkInStr = readString("Дата заезда (гггг-мм-дд)");
        String checkOutStr = readString("Дата выезда (гггг-мм-дд)");

        Date checkIn = parseDate(checkInStr);
        Date checkOut = parseDate(checkOutStr);

        return service.getFreeRoomsByDate(checkIn, checkOut);

    }

    public List<Booking> showLastThreeBookings() {
        int roomNumber = readInt("Номер комнаты");
       return service.lastThreeBookingsByRooms(roomNumber);

    }

    public List<Booking> sortBookings() {
        String sortBy = readString("Сортировать по (client/checkOutDate)");
        return service.sort(sortBy);
    }
}
