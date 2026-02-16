package com.hotel.service;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hotel.config.Config;
import com.hotel.model.Room;
import com.hotel.model.Room.*;
import com.hotel.repository.BookingRepository;
import com.hotel.repository.DBConnection;
import com.hotel.repository.HibernateUtil;
import com.hotel.repository.RoomRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
@Service
public class RoomServiceImpl extends FileServiceImpl<Room> implements RoomService {
    private static final Logger logger = LogManager.getLogger(RoomServiceImpl.class);

    @Value("${room.status.change.enable}")
    private boolean enable;

    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;
    public RoomServiceImpl(RoomRepository roomRepository, BookingRepository bookingRepository){
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Room addRoom(Room room) {
        return roomRepository.create(room);
    }
    @Override
    public void deleteRoom(int roomNumber) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try{
            bookingRepository.deleteByRoomNumber(roomNumber);
            roomRepository.deleteById(roomNumber);
            transaction.commit();
            logger.info("Комната успешно удалена");
        } catch (Exception e) {
            logger.error("Ошибка при удалении комнаты");
            transaction.rollback();
        }

    }
    @Override
    public void updateRoom(Room room) {
        roomRepository.update(room);
        logger.info("Комната успешно обновлена");
    }
    @Override
    public List<Room> getAllRooms() {
        logger.info("Получение всех комнат");
        return roomRepository.findAll();
    }
    @Override
    public List<Room> getRoomByStatus(Room.Status status) {
        logger.info("Получение комнат по статусу: {}" ,status);
        return roomRepository.findByStatus(status);
    }
    @Override
    public int countFreeRooms() {
        logger.info("Подсчет свободных комнат");
        return roomRepository.countFreeRoom();
    }
    @Override
    public List<Room> sort(boolean freeRoom,String sortBy) {

        logger.info("Сортировка комнат по : {}" ,sortBy);
        List<Room> roomList = getAllRooms();
        if(roomList.isEmpty()) {
            logger.error("Список комнат пуст");
            return null;
        }
        if (freeRoom) {
            List<Room> result = roomList;
            roomList.clear();
            result.stream().filter(room -> room.getStatus() == Room.Status.FREE).toList();
        }
        switch (sortBy) {
            case "price"-> roomList.sort(Comparator.comparing(Room::getPrice));
            case "place"-> roomList.sort(Comparator.comparing(Room::getPlace));
            case "stars"-> roomList.sort(Comparator.comparing(Room::getStars));
            case "type"-> roomList.sort(Comparator.comparing(Room::getType));
            default -> {logger.error("Некорректный параметр сортировки");
                return null;}
        }
        return roomList;
    }
    @Override
    public Room getRoomByRoomNumber(int roomNumber) {
        logger.info("Получение комнаты по номеру: {}" ,roomNumber);
       return roomRepository.findById(roomNumber).orElse(null);
    }
    @Override
    public void addRoomsFromFile(){
        String fileName = "rooms";
        importFromFile(fileName);
    }
    @Override
    public void exportRoomsToFile() {
        String fileName = "rooms";
        exportToFile(fileName,getAllRooms());

    }
    @Override
    public String writeModel(Room room){

        String s = room.getRoomNumber() + "," + room.getPrice() + "," + room.getPlace() + ","
                + room.getType() + "," + room.getStars();
        return s;
    }
    @Override
    public void parseModel(String line){
        try{
            String[] values = line.split(",");
            Integer roomNumber = Integer.parseInt(values[0]);
            BigDecimal price = new BigDecimal(values[1]);
            int place = Integer.parseInt(values[2]);
            RoomType type = RoomType.valueOf(values[3].toUpperCase());
            Star stars = Star.valueOf(values[4].toUpperCase());
            Room room = new Room(roomNumber,price,place,type,stars);
            addRoom(room);
        }
        catch (Exception e){
            System.out.println("Ошибка при парсинге строки: " + line);
        }
    }
    @Override
    public void changeStatus(int roomNumber, Room.Status status) {
        if (enable) {
            getAllRooms().get(roomNumber).setStatus(status);
        } else {
            System.out.println("Изменение статуса запрещено");
        }
    }
    @Override
    public void parseModelJSON(List<Room> list){
        for (Room room: list) {
            addRoom(room);
        }
    }
    @Override
    public TypeReference<List<Room>> getTypeReference(){
        return new TypeReference<List<Room>>(){};
    }
}
