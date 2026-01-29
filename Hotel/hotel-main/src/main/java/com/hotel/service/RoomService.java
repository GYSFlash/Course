package com.hotel.service;

import com.hotel.model.Room;
import com.hotel.model.Room.*;
import java.util.List;

public interface RoomService {
    Room addRoom(Room room);
    void deleteRoom(int id);
    void updateRoom(Room room);
    List<Room> getAllRooms();
    List<Room> getRoomByStatus(Status status);
    int countFreeRooms();
    List<Room> sort(boolean freeRoom, String sortby);
    Room getRoomByRoomNumber(int roomNumber);
    void addRoomsFromFile();
    void exportRoomsToFile();
    void changeStatus(int roomNumber, Status status);

}
