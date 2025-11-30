package Hotel.Service;

import Hotel.Model.Client;
import Hotel.Model.Room;
import Hotel.Model.Room.*;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class RoomServiceImpl implements RoomService {
    private static RoomServiceImpl instance;
    private Map<Integer,Room> rooms = new HashMap<>();
    private RoomServiceImpl(){}
    public static RoomServiceImpl getInstance(){
        if(instance == null) {
            instance = new RoomServiceImpl();
        }
        return instance;
    }
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
        String fileName = "D:/Java/Course/Hotel/Import/rooms.csv";
        File file = new File(fileName);
        try{
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            int roomNumber, place;
            BigDecimal price;
            Star stars;
            RoomType type;

            while ((line = br.readLine()) != null) {
                String values[] = line.split(",");
                try {
                    if(values.length == 5) {
                       roomNumber = Integer.parseInt(values[0]);
                       price = new BigDecimal(values[1]);
                       place = Integer.parseInt(values[2]);
                       type = RoomType.valueOf(values[3].toUpperCase());
                       stars = Star.valueOf(values[4].toUpperCase());
                       Room room = new Room(roomNumber,price,place,type,stars);
                       addRoom(room);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void exportRoomsToFile() {
        String fileName = "D:/Java/Course/Hotel/Export/rooms.csv";
        File file = new File(fileName);
        try{
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for(Room room : rooms.values()) {
                String line = room.getRoomNumber() + "," + room.getPrice() + "," + room.getPlace() + ","
                        + room.getType() + "," + room.getStars();
                bw.write(line);
                bw.newLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
