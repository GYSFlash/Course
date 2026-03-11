package com.hotel.service;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hotel.config.Config;
import com.hotel.dto.BookingRequestDTO;
import com.hotel.dto.BookingResponseDTO;
import com.hotel.dto.ClientResponseDTO;
import com.hotel.dto.RoomDTO;
import com.hotel.exceptions.NoIllegalArgumentException;
import com.hotel.exceptions.NotFoundException;
import com.hotel.mapper.BookingMapper;
import com.hotel.model.Booking;
import com.hotel.model.Client;
import com.hotel.model.Room;
import com.hotel.repository.BookingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

@Service
@Transactional(readOnly = true)
public class BookingServiceImpl extends FileServiceImpl<Booking> implements BookingService{
    private static final Logger logger = LogManager.getLogger(BookingServiceImpl.class);
    private RoomService roomService;
    private ClientService clientService;
    private BookingRepository bookingRepository;
    private BookingMapper bookingMapper;
    @Value("${booking.history.record.limit}")
    private int limit;
    public BookingServiceImpl(BookingRepository bookingRepository, RoomService roomService, ClientService clientService,BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
        this.clientService = clientService;
        this.bookingMapper = bookingMapper;
    }

   @Override
   @Transactional
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
        logger.info("Бронь успешно удалена");
    }
    @Override
    @Transactional
    public void addBooking(BookingRequestDTO booking) {
        if(booking.getClient() == null || booking.getRoom() == null){
            logger.error("Некорректные данные клиента или номера");
            throw new NoIllegalArgumentException("Некорректные данные клиента или номера");
        }
        booking.setTotalPrice(booking.calculateTotalPrice());
        bookingRepository.create(bookingMapper.toBooking(booking));
        logger.info("Бронь успешно добавлена");
    }
    @Override
    public List<BookingResponseDTO> getAllBookings() {
        logger.info("Получение всех бронирований");
        return bookingMapper.toBookingDTOList(bookingRepository.findAll());
    }
    @Override
    @Transactional
    public void updateBooking(Long id, BookingRequestDTO booking) {
        if(getBookingById(id) == null){
            logger.error("Бронь c заданным id не существует");
            throw new NotFoundException("Бронь c заданным id не существует");
        }
        Booking newBooking = bookingMapper.toBooking(booking);
        newBooking.setId(id);
        bookingRepository.update(newBooking);
        logger.info("Бронь успешно обновлена");
    }
    @Override
    public List<RoomDTO> getFreeRoomsByDate(Date in, Date out) {
        logger.info("Получение свободных номеров по датам");
        List<RoomDTO> busyRooms = new ArrayList<>();
        List<RoomDTO> allRooms = roomService.getAllRooms();

        // Находим занятые номера
        for (BookingResponseDTO booking : getAllBookings()) {
            RoomDTO room = booking.getRoom();
            if (!out.before(booking.getCheckInDate()) && !in.after(booking.getCheckOutDate())) {
                if (!busyRooms.contains(room)) {
                    busyRooms.add(room);
                }
            }
        }


        List<RoomDTO> freeRooms;
        freeRooms = allRooms.stream().filter(room -> !busyRooms.contains(room)&&room.getStatus() != Room.Status.REPAIR).toList();

        return freeRooms;
    }

    @Override
    public List<BookingResponseDTO> lastThreeBookingsByRooms(int roomNumber) {
        logger.info("Получение последних 3 бронирований по номеру");
        return bookingMapper.toBookingDTOList(bookingRepository.threeBookingByRoom(roomNumber));
    }
    @Override
    public List<BookingResponseDTO> sort(String sortBy) {
        logger.info("Сортировка бронирований по : {}",sortBy );
        List<BookingResponseDTO> bookingList = getAllBookings();
        if(bookingList.isEmpty()) {
            throw new NotFoundException("Бронирования отсутствуют");
        }
        switch (sortBy) {
            /*case "client" -> bookingList.sort(Comparator.comparing(BookingResponseDTO::getClient));*/
            case "checkOutDate"-> bookingList.sort(Comparator.comparing(BookingResponseDTO::getCheckOutDate));
            case "checkInDate" -> bookingList.sort(Comparator.comparing(BookingResponseDTO::getCheckInDate));
            default -> {
                logger.error("Некорректный параметр сортировки");
                throw new NoIllegalArgumentException("Некорректный параметр сортировки");
            }
        }
        return bookingList;
    }
    @Override
    public BookingResponseDTO getBookingById(Long id) {
        logger.info("Получение брони по id: {}",id);
        return bookingMapper.toBookingDTO(bookingRepository.findById(id).orElseThrow(()-> new NotFoundException("Бронирование не найдено")));
    }
    @Override
    public void addBookingFromFile(){
        String fileName = "bookings";
        importFromFile(fileName);
    }
    @Override
    public void exportBookingToFile() {
        String fileName = "bookings";
        /*exportToFile(fileName,getAllBookings());*/

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

            /*Room room = roomService.getRoomByRoomNumber(parseInt(values[2]));*/
            /*Client client = clientService.getClientById(parseLong(values[3]));
            addBooking(new Booking(dateIn, room,client, dateOut));*/
        }
        catch (Exception e){
            System.out.println("Ошибка при парсинге строки: " + line);
        }
    }
    @Override
    public List<ClientResponseDTO> getClientsStaysByRoom(int roomNumber) {
        if (getAllBookings().isEmpty()) {
            return null;
        } else {
            List<BookingResponseDTO> newBookings = sort("checkInDate");
            Collections.reverse(newBookings);
            Set<ClientResponseDTO> clients = new LinkedHashSet<>();
            for (BookingResponseDTO booking : newBookings) {
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
            /*addBooking(booking);*/
        }
    }
    @Override
    public TypeReference<List<Booking>> getTypeReference(){
        return new TypeReference<List<Booking>>(){};
    }
}
