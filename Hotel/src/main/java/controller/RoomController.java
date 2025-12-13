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
            return false;
        }
        double price = readDouble("Цена за ночь");

        int places = readInt("Количество мест");

        String typeStr = readString("Тип (STANDART/STANDARTPLUS/LUX/DELUXE/PRESIDENT)");
        Room.RoomType type = Room.RoomType.valueOf(typeStr.toUpperCase());

        String starsStr = readString("Звезды (ONE/TWO/THREE/FOUR/FIVE)");
        Room.Star stars = Room.Star.valueOf(starsStr.toUpperCase());

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
            case "type" -> {String typeStr = readString("Тип (STANDART/STANDARTPLUS/LUX/DELUXE/PRESIDENT)");
                room.setType(Room.RoomType.valueOf(typeStr.toUpperCase()));
            }
            case "star" -> {String starsStr = readString("Звезды (ONE/TWO/THREE/FOUR/FIVE)");
                room.setStars(Room.Star.valueOf(starsStr.toUpperCase()));
            }
            default -> {return false;
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
