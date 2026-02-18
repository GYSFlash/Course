package com.hotel.service;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hotel.config.Config;
import com.hotel.model.Booking;
import com.hotel.model.Client;
import com.hotel.model.Room;
import com.hotel.repository.BookingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

@Service
public class BookingServiceImpl extends FileServiceImpl<Booking> implements BookingService{
    private static final Logger logger = LogManager.getLogger(BookingServiceImpl.class);
    private RoomService roomService;
    private ClientService clientService;
    private BookingRepository bookingRepository;
    @Value("${booking.history.record.limit}")
    private int limit;
    public BookingServiceImpl(BookingRepository bookingRepository, RoomService roomService, ClientService clientService) {
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
        this.clientService = clientService;
    }

   @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
        logger.info("Бронь успешно удалена");
    }
    @Override
    public void addBooking(Booking booking) {
        if(booking.getClient() == null || booking.getRoom() == null){
            logger.error("Некорректные данные клиента или номера");
            return;
        }
        booking.setTotalPrice(booking.calculateTotalPrice());
        bookingRepository.create(booking);
        logger.info("Бронь успешно добавлена");
    }
    @Override
    public List<Booking> getAllBookings() {
        logger.info("Получение всех бронирований");
        return bookingRepository.findAll();
    }
    @Override
    public void updateBooking(Booking booking) {
        bookingRepository.update(booking);
        logger.info("Бронь успешно обновлена");
    }
    @Override
    public List<Room> getFreeRoomsByDate(Date in, Date out) {
        logger.info("Получение свободных номеров по датам");
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
        logger.info("Получение последних 3 бронирований по номеру");
        return bookingRepository.threeBookingByRoom(roomNumber);
    }
    @Override
    public List<Booking> sort(String sortBy) {
        logger.info("Сортировка бронирований по : {}",sortBy );
        List<Booking> bookingList = getAllBookings();
        if(bookingList.isEmpty()) {
            return null;
        }
        switch (sortBy) {
            case "client" -> bookingList.sort(Comparator.comparing(Booking::getClient));
            case "checkOutDate"-> bookingList.sort(Comparator.comparing(Booking::getCheckOutDate));
            case "checkInDate" -> bookingList.sort(Comparator.comparing(Booking::getCheckInDate));
            default -> {
                logger.error("Некорректный параметр сортировки");
                return null;
            }
        }
        return bookingList;
    }
    @Override
    public Booking getBookingById(Long id) {
        logger.info("Получение брони по id: {}",id);
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
        if (getAllBookings().isEmpty()) {
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
