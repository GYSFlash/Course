package controller;


import model.Room;
import service.RoomService;


import java.math.BigDecimal;

import java.util.List;

public class RoomController extends BaseController {
    private static RoomController instance;
    private RoomService service;

    private RoomController(RoomService service) {
        this.service = service;
    }

    public static RoomController getInstance(RoomService service) {
        if(instance == null){
            instance = new RoomController(service);
        }
        return instance;
    }
    public boolean addRoom() {
        int roomNumber = readInt("Номер комнаты");
        if (service.getRoomByRoomNumber(roomNumber) != null) {
            System.out.println("Комната с таким номером уже существует");
            return false;
        }
        double price = readDouble("Цена за ночь");

        int places = readInt("Количество мест");
        Room.RoomType type;
        try {
            String typeStr = readString("Тип (STANDART/STANDARTPLUS/LUX/DELUXE/PRESIDENT)");
            type = Room.RoomType.valueOf(typeStr.toUpperCase());
        }catch (Exception e){
            System.out.println("Недопустимый тип комнаты");
            return false;
        }
        Room.Star stars;
        try {
            String starsStr = readString("Звезды (ONE/TWO/THREE/FOUR/FIVE)");
            stars = Room.Star.valueOf(starsStr.toUpperCase());
        }catch (Exception e){
            System.out.println("Недопустимое количество звезд");
            return false;
        }
        Room room = new Room(roomNumber, BigDecimal.valueOf(price), places, type, stars);
        service.addRoom(room);
        return true;
    }

    public List<Room> showAllRooms() {
        return service.getAllRooms();
    }

    public boolean deleteRoom() {
        int roomNumber = readInt("Номер комнаты для удаления");
        if (service.getRoomByRoomNumber(roomNumber) == null) {
            return false;
        }

        service.deleteRoom(roomNumber);
        return true;
    }

    public boolean updateRoom() {

        int roomNumber = readInt("Номер комнаты для обновления");
        if (service.getRoomByRoomNumber(roomNumber) == null) {
            return false;
        }
        Room room = service.getRoomByRoomNumber(roomNumber);
        String change = readString("Изменить (price/place/type/star)");
        switch (change) {
            case "price" -> {
                double price = readDouble("Новая цена номера");
                room.setPrice(BigDecimal.valueOf(price));
            }
            case "place" -> {int places = readInt("Новое количество мест");
                room.setPlace(places);
            }
            case "type" -> {
                try{String typeStr = readString("Тип (STANDART/STANDARTPLUS/LUX/DELUXE/PRESIDENT)");
                room.setType(Room.RoomType.valueOf(typeStr.toUpperCase()));
                }catch (Exception e){
                    System.out.println("Недопустимый тип комнаты");
                    return false;
                }
            }
            case "star" -> {
                try {
                    String starsStr = readString("Звезды (ONE/TWO/THREE/FOUR/FIVE)");
                    room.setStars(Room.Star.valueOf(starsStr.toUpperCase()));
                } catch (Exception e) {
                    System.out.println("Недопустимое количество звезд");
                    return false;
                }
            }
            default -> {
                return false;
            }

        }
        service.updateRoom(room);
        return true;
    }

    public List<Room> showRoomsByStatus() {
        String statusStr = readString("Статус (FREE/REPAIR/OCCUPIED)");
        Room.Status status = Room.Status.valueOf(statusStr.toUpperCase());

        return service.getRoomByStatus(status);

    }

    public int showFreeRoomsCount() {
        return service.countFreeRooms();
    }

    public List<Room> sortRooms() {
        String freeOnlyStr = readString("Только свободные? (y/n)");
        boolean freeOnly = freeOnlyStr.equalsIgnoreCase("y");

        String sortBy = readString("Сортировать по (price/place/stars/type)");

        return service.sort(freeOnly, sortBy);
    }

    public Room findRoomByNumber() {
        int roomNumber = readInt("Номер комнаты для поиска");
        return service.getRoomByRoomNumber(roomNumber);
    }
    public void importRooms() {
        service.addRoomsFromFile();

    }
    public void exportRooms() {
        service.exportRoomsToFile();
    }
    public boolean changeRoomStatus() {
        int roomNumber = readInt("Номер комнаты для смены статуса");
        String statusStr = readString("Новый cтатус (FREE/REPAIR/OCCUPIED)");
        Room.Status status = Room.Status.valueOf(statusStr.toUpperCase());
        service.changeStatus(roomNumber, status);
            return true;

    }
}
