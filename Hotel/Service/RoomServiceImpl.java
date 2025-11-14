package Hotel.Service;

import Hotel.Model.Client;
import Hotel.Model.Room;
import Hotel.Model.Room.*;

import java.util.*;

public class RoomServiceImpl implements RoomService {
    private Map<Integer,Room> rooms = new HashMap<>();
    private final RoomPlace roomPlace = new RoomPlace();
    private final RoomStars roomStars = new RoomStars();
    private final RoomTypes roomTypes = new RoomTypes();
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
    @Override
    public List<Room> sort(boolean freeRoom,String sortBy) {
        if(rooms.isEmpty()) {
            return null;
        }

        List<Room> roomList = new ArrayList<>(rooms.values());
        if (freeRoom) {
            List<Room> result = roomList;
            roomList.clear();
            for(Room room : result){
                if(room.getStatus() == Room.Status.FREE){
                    roomList.add(room);
                }
            }
        }
        switch (sortBy) {
            case "price":
                Collections.sort(roomList);
                break;
            case "place":
                Collections.sort(roomList ,roomPlace );
                break;
            case "stars":
                Collections.sort(roomList , roomStars );
                break;
            case "type":
                Collections.sort(roomList,roomTypes);
                break;
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
}
