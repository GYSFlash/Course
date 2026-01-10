package com.hotel.service;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hotel.config.Config;
import com.hotel.model.Room;
import com.hotel.model.Room.*;

import java.math.BigDecimal;
import java.util.*;
@Singleton
public class RoomServiceImpl extends FileServiceImpl<Room> implements RoomService {
    private Map<Integer,Room> rooms = new HashMap<>();
    @InjectByType
    private Config config;
    public RoomServiceImpl(){}

    @Override
    public void addRoom(Room room) {
        rooms.put(room.getRoomNumber(),room);
    }
    @Override
    public void deleteRoom(int roomNumber) {
        if(rooms.containsKey(roomNumber)){
            rooms.remove(roomNumber);
        }
    }
    @Override
    public void updateRoom(Room room) {
        if(rooms.containsKey(room.getRoomNumber())){
            rooms.put(room.getRoomNumber(),room);
        }
    }
    @Override
    public List<Room> getAllRooms() {
        List<Room> newRooms = new ArrayList<>(rooms.values());
        return newRooms;
    }
    @Override
    public List<Room> getRoomByStatus(Room.Status status) {
        List<Room> newRooms =  new ArrayList<>(rooms.values());

        List<Room> result = newRooms.stream().filter(room -> room.getStatus() == status).toList();
        return result;
    }
    @Override
    public int countFreeRooms() {
        return getRoomByStatus(Room.Status.FREE).size();
    }
    @Override
    public List<Room> sort(boolean freeRoom,String sortBy) {
        if(rooms.isEmpty()) {
            return null;
        }

        List<Room> roomList = new ArrayList<>(rooms.values());
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
            default -> {System.out.println("Некорректный параметр сортировки");
                return null;}
        }
        return roomList;
    }
    @Override
    public Room getRoomByRoomNumber(int roomNumber) {
        if(rooms.containsKey(roomNumber)){
            return rooms.get(roomNumber);
        }else{
            return null;
        }
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
        if (config.isRoomStatusChangeEnable()) {
            rooms.get(roomNumber).setStatus(status);
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
