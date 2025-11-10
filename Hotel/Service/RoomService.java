package Hotel.Service;

import Hotel.Model.Room;
import Hotel.Model.Room.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RoomService {
    void addRoom(Room room);
    void deleteRoom(int id);
    void updateRoom(Room room);
    List<Room> getAllRooms();
    List<Room> getRoomByStatus(Status status);
    int countFreeRooms();



}
