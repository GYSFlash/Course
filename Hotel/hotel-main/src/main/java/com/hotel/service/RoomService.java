package com.hotel.service;

import com.hotel.dto.RoomDTO;
import com.hotel.model.Room;
import com.hotel.model.Room.*;
import java.util.List;

public interface RoomService {
    void addRoom(RoomDTO room);
    void deleteRoom(int id);
    void updateRoom(RoomDTO room);
    List<RoomDTO> getAllRooms();
    List<RoomDTO> getRoomByStatus(Status status);
    int countFreeRooms();
    List<RoomDTO> sort(String sortby);
    RoomDTO getRoomByRoomNumber(int roomNumber);
    void addRoomsFromFile();
    void exportRoomsToFile();
    void changeStatus(int roomNumber, Status status);

}
