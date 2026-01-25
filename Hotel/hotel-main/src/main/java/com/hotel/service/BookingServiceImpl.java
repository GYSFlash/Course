package com.hotel.service;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hotel.config.Config;
import com.hotel.model.Booking;
import com.hotel.model.Client;
import com.hotel.model.Room;
import com.hotel.repository.BookingRepository;

import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

@Singleton
public class BookingServiceImpl extends FileServiceImpl<Booking> implements BookingService{

    private Map<Long, Booking> bookings = new HashMap<>();
    @InjectByType
    private RoomService roomService;
    @InjectByType
    private ClientService clientService;
    @InjectByType
    private Config config;
    @InjectByType
    private BookingRepository bookingRepository;
    public BookingServiceImpl() {
    }


   @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
    @Override
    public void addBooking(Booking booking) {
        if(booking.getClient() == null || booking.getRoom() == null){
            System.out.println("Некорректные данные клиента или номера");
            return;
        }
        booking.setTotalPrice(booking.calculateTotalPrice());
        bookingRepository.create(booking);
    }
    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    @Override
    public void updateBooking(Booking booking) {
        bookingRepository.update(booking);
    }
    @Override
    public List<Room> getFreeRoomsByDate(Date in, Date out) {
        List<Room> busyRooms = new ArrayList<>();
        List<Room> allRooms = roomService.getAllRooms();

        // Находим занятые номера
        for (Booking booking : getAllBookings()) {
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
        return bookingRepository.threeBookingByRoom(roomNumber);
    }
    @Override
    public List<Booking> sort(String sortBy) {

        List<Booking> bookingList = getAllBookings();
        if(bookingList.isEmpty()) {
            return null;
        }
        switch (sortBy) {
            case "client" -> bookingList.sort(Comparator.comparing(Booking::getClient));
            case "checkOutDate"-> bookingList.sort(Comparator.comparing(Booking::getCheckOutDate));
            case "checkInDate" -> bookingList.sort(Comparator.comparing(Booking::getCheckInDate));
            default -> {
                System.out.println("Некорректный параметр сортировки");
                return null;
            }
        }
        return bookingList;
    }
    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }
    @Override
    public void addBookingFromFile(){
        String fileName = "bookings";
        importFromFile(fileName);
    }
    @Override
    public void exportBookingToFile() {
        String fileName = "bookings";
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
        int limit = config.getBookingHistoryRecordLimit();
        if (bookings.isEmpty()) {
            return null;
        } else {
            List<Booking> newBookings = sort("checkInDate");
            Collections.reverse(newBookings);
            Set<Client> clients = new LinkedHashSet<>();
            for (Booking booking : newBookings) {
                if (booking.getRoom().getRoomNumber() == roomNumber) {
                    clients.add(booking.getClient());
                    if (clients.size() >= limit) {
                        break;
                    }
                }
            }
            return new ArrayList<>(clients);
        }
    }
    @Override
    public void parseModelJSON(List<Booking> list){
        for (Booking booking : list) {
            addBooking(booking);
        }
    }
    @Override
    public TypeReference<List<Booking>> getTypeReference(){
        return new TypeReference<List<Booking>>(){};
    }
}
