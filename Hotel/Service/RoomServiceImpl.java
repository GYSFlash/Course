package Hotel.Service;

import Hotel.Model.Room;
import Hotel.Model.Room.*;

import java.util.Map;

public interface RoomServiceImpl {
    void addRoom(Room room);
    void deleteRoom(int id);
    void updateRoom(Room room);
    Map<Integer, Room> getAllRooms();
    Map<Integer, Room> getRoomByStatus(Status status);


}
