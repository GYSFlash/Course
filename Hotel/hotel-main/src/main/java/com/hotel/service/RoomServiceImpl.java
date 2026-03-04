package com.hotel.service;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hotel.config.Config;
import com.hotel.dto.RoomDTO;
import com.hotel.exceptions.NoIllegalArgumentException;
import com.hotel.exceptions.NotFoundException;
import com.hotel.mapper.RoomMapper;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
@Service
@Transactional(readOnly = true)
public class RoomServiceImpl extends FileServiceImpl<Room> implements RoomService {
    private static final Logger logger = LogManager.getLogger(RoomServiceImpl.class);

    @Value("${room.status.change.enable}")
    private boolean enable;

    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;
    private RoomMapper roomMapper;
    public RoomServiceImpl(RoomRepository roomRepository, BookingRepository bookingRepository, RoomMapper roomMapper){
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.roomMapper = roomMapper;
    }

    @Override
    @Transactional
    public void addRoom(RoomDTO room) {
        roomRepository.create(roomMapper.toRoom(room));
    }
    @Override
    @Transactional
    public void deleteRoom(int roomNumber) {

            bookingRepository.deleteByRoomNumber(roomNumber);
            roomRepository.deleteById(roomNumber);
            logger.info("Комната успешно удалена");

    }
    @Override
    @Transactional
    public void updateRoom(RoomDTO room) {
        logger.info("Обновление комнаты");
        if(getRoomByRoomNumber(room.getRoomNumber()) == null){
            logger.error("Комната не найдена");
            throw new NotFoundException("Комната не найдена");
        }
        roomRepository.update(roomMapper.toRoom(room));
        logger.info("Комната успешно обновлена");
    }
    @Override
    public List<RoomDTO> getAllRooms() {
        logger.info("Получение всех комнат");
        return roomMapper.toRoomDTOList(roomRepository.findAll());
    }
    @Override
    public List<RoomDTO> getRoomByStatus(Room.Status status) {
        logger.info("Получение комнат по статусу: {}" ,status);
        return roomMapper.toRoomDTOList(roomRepository.findByStatus(status));
    }
    @Override
    public int countFreeRooms() {
        logger.info("Подсчет свободных комнат");
        return roomRepository.countFreeRoom();
    }
    @Override
    public List<RoomDTO> sort(String sortBy) {

        logger.info("Сортировка комнат по : {}" ,sortBy);
        List<RoomDTO> roomList = getAllRooms();
        if(roomList.isEmpty()) {
            logger.error("Список комнат пуст");
            return null;
        }
        switch (sortBy) {
            case "price"-> roomList.sort(Comparator.comparing(RoomDTO::getPrice));
            case "place"-> roomList.sort(Comparator.comparing(RoomDTO::getPlace));
            case "stars"-> roomList.sort(Comparator.comparing(RoomDTO::getStars));
            case "type"-> roomList.sort(Comparator.comparing(RoomDTO::getType));
            default -> {logger.error("Некорректный параметр сортировки");
                throw new NoIllegalArgumentException("Некорректный параметр сортировки");}
        }
        return roomList;
    }
    @Override
    public RoomDTO getRoomByRoomNumber(int roomNumber) {
        logger.info("Получение комнаты по номеру: {}" ,roomNumber);
       return roomMapper.toRoomDTO(roomRepository.findById(roomNumber).orElseThrow(()-> new NotFoundException("Комната не найдена")));
    }
    @Override
    public void addRoomsFromFile(){
        String fileName = "rooms";
        importFromFile(fileName);
    }
    @Override
    public void exportRoomsToFile() {
        String fileName = "rooms";
        /*exportToFile(fileName,getAllRooms());*/

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
            /*addRoom(room);*/
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
            /*addRoom(room);*/
        }
    }
    @Override
    public TypeReference<List<Room>> getTypeReference(){
        return new TypeReference<List<Room>>(){};
    }
}
