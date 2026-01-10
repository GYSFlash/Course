package com.hotel.controller;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.model.Booking;
import com.hotel.model.Client;
import com.hotel.model.Room;
import com.hotel.service.BookingService;
import com.hotel.service.ClientService;
import com.hotel.service.RoomService;

import java.util.Date;
import java.util.List;

@Singleton
public class BookingController extends BaseController {
    @InjectByType
    private BookingService service;
    @InjectByType
    private ClientService clientService;
    @InjectByType
    private RoomService roomService;

    public BookingController() {
    }

    public boolean addBooking() {
        String dateStr = readString("Дата въезда (гггг-мм-дд)");

        Date checkIn = parseDate(dateStr);
        if (checkIn == null || checkIn.before(new Date())) {
            System.out.println("Некорректная дата въезда");
            return false;
        }
        dateStr = readString("Дата выезда (гггг-мм-дд)");
        Date checkOut = parseDate(dateStr);
        if(checkIn.after(checkOut) || checkIn.equals(checkOut)){
            System.out.println("Некорректная дата въезда");
            return false;
        }
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
                if (newDate == null || newDate.before(new Date())) {
                    System.out.println("Некорректная дата въезда");
                    return false;
                }
                booking.setCheckInDate(newDate);
            }
            case "dateOut" -> {
                String dateStr = readString("Новая дата выезда (гггг-мм-дд)");
                Date newDate = parseDate(dateStr);
                if (newDate == null || newDate.after(booking.getCheckInDate())) {
                    System.out.println("Некорректная дата выезда");
                    return false;
                }
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
        String sortBy = readString("Сортировать по (client/checkOutDate/checkInDate)");
        return service.sort(sortBy);
    }
    public void importBookings() {
        service.addBookingFromFile();
    }
    public void exportBookings() {
        service.exportBookingToFile();
    }
    public List<Client> getClientsStaysByRoom(){
        int roomNumber = readInt("Номер комнаты");
        return service.getClientsStaysByRoom(roomNumber);
    }
}
