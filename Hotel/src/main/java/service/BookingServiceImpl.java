package service;

import config.HotelConfig;
import model.Booking;
import model.Client;
import model.Room;
import model.Service;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class BookingServiceImpl extends FileServiceImpl<Booking> implements BookingService{
    private static BookingServiceImpl instance;
    private Map<Long, Booking> bookings = new HashMap<>();
    private RoomService roomService;
    private ClientService clientService;
    private BookingServiceImpl(RoomService roomService, ClientService clientService) {
        this.roomService = roomService;
        this.clientService = clientService;
    }
    public static BookingServiceImpl getInstance(RoomService roomService, ClientService clientService) {
        if (instance == null) {
            instance = new BookingServiceImpl(roomService, clientService);
        }
        return instance;
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
    @Override
    public void addBookingFromFile(){
        String fileName = "bookings.csv";
        importFromFile(fileName);
    }
    @Override
    public void exportBookingToFile() {
        String fileName = "bookings.csv";
        exportToFile(fileName,getAllBookings());

    }
    @Override
    public String writeModel(Booking booking){

        String s = dateFormat.format(booking.getCheckInDate()) + "," + dateFormat.format(booking.getCheckOutDate()) + ","
                +  booking.getRoom().getRoomNumber() + "," + booking.getClient().getId();
        return s;
    }
    @Override
    public void parseModel(String line){
        try{
            String[] values = line.split(",");
            Date dateIn = dateFormat.parse(values[0]);
            Date dateOut = dateFormat.parse(values[1]);

            Room room = roomService.getRoomByRoomNumber(parseInt(values[2]));
            Client client = clientService.getClientById(parseLong(values[3]));
            addBooking(new Booking(dateIn, room,client, dateOut));
        }
        catch (Exception e){
            System.out.println("Ошибка при парсинге строки: " + line);
        }
    }
    @Override
    public List<Client> getClientsStaysByRoom(int roomNumber) {
        int limit = HotelConfig.getClientsByRoomLimit();
        if (bookings.isEmpty()) {
            return null;
        } else {
            List<Booking> newBookings =  new ArrayList<>(bookings.values());
            List<Client> clients = new ArrayList<>();
            for (Booking booking : newBookings) {
                if (booking.getRoom().getRoomNumber() == roomNumber) {
                    clients.add(booking.getClient());
                }
            }
            List<Client> result = clients.stream().distinct().limit(limit).toList();
            return result;


        }
    }
}
