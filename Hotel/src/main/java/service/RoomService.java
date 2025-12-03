package service;

import model.Room;
import model.Room.*;

import java.util.List;

public interface RoomService {
    void addRoom(Room room);
    void deleteRoom(int id);
    void updateRoom(Room room);
    List<Room> getAllRooms();
    List<Room> getRoomByStatus(Status status);
    int countFreeRooms();
    List<Room> sort(boolean freeRoom, String sortby);
    Room getRoomByRoomNumber(int roomNumber);
    void addRoomsFromFile();
    void exportRoomsToFile();

}
