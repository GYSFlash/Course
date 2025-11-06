package Hotel.Service;

import Hotel.Model.Room;

import java.util.HashMap;
import java.util.Map;

public class RoomService implements RoomServiceImpl {
    Map<Integer,Room> rooms = new HashMap<>();
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
    public Map<Integer,Room> getAllRooms() {
        return rooms;
    }
    @Override
    public Map<Integer,Room> getRoomByStatus(Room.Status status) {
        Map<Integer,Room> result = new HashMap<>();
        for(Integer key:rooms.keySet()){
            Room room = rooms.get(key);
            if(room.getStatus().equals(status)){
                result.put(key,room);
            }
        }
        return result;
    }
}
