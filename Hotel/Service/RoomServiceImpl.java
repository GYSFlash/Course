package Hotel.Service;

import Hotel.Model.Room;

import java.util.*;

public class RoomServiceImpl implements RoomService {
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
    public List<Room> getAllRooms() {
        List<Room> newRooms = new ArrayList<>(rooms.values());
        return newRooms;
    }
    @Override
    public List<Room> getRoomByStatus(Room.Status status) {
        List<Room> newRooms =  new ArrayList<>(rooms.values());
        List<Room> result = new ArrayList<>();
        for(Room room : newRooms){
            if(room.getStatus() == status){
                result.add(room);
            }
        }
        return result;
    }
    @Override
    public int countFreeRooms() {
        return getRoomByStatus(Room.Status.FREE).size();
    }

}
