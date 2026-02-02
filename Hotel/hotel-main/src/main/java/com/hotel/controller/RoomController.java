package com.hotel.controller;


import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.model.Room;
import com.hotel.service.RoomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.math.BigDecimal;

import java.util.List;
@Singleton
public class RoomController extends BaseController {
    private static final Logger logger = LogManager.getLogger(RoomController.class);
    @InjectByType
    private RoomService service;

    public RoomController() {
    }

    public boolean addRoom() {
        logger.info("Добавление комнаты");
        int roomNumber = readInt("Номер комнаты");
        if (service.getRoomByRoomNumber(roomNumber) != null) {
            logger.error("Комната с таким номером уже существует");
            return false;
        }
        double price = readDouble("Цена за ночь");

        int places = readInt("Количество мест");
        Room.RoomType type;
        try {
            String typeStr = readString("Тип (STANDART/STANDARTPLUS/LUX/DELUXE/PRESIDENT)");
            type = Room.RoomType.valueOf(typeStr.toUpperCase());
        }catch (Exception e){
            logger.error("Недопустимый тип комнаты");
            return false;
        }
        Room.Star stars;
        try {
            String starsStr = readString("Звезды (ONE/TWO/THREE/FOUR/FIVE)");
            stars = Room.Star.valueOf(starsStr.toUpperCase());
        }catch (Exception e){
            logger.error("Недопустимое количество звезд");
            return false;
        }
        Room room = new Room(roomNumber, BigDecimal.valueOf(price), places, type, stars);
        Room room1 = service.addRoom(room);
        logger.info("Комната успешно добавлена");
        if (room1 == null) {
            return false;
        }
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
        logger.info("Удаление комнаты {}", roomNumber);
        service.deleteRoom(roomNumber);
        return true;
    }

    public boolean updateRoom() {

        int roomNumber = readInt("Номер комнаты для обновления");
        Room room = service.getRoomByRoomNumber(roomNumber);
        if (room == null) {
            return false;
        }

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
                    logger.error("Недопустимый тип комнаты");
                    return false;
                }
            }
            case "star" -> {
                try {
                    String starsStr = readString("Звезды (ONE/TWO/THREE/FOUR/FIVE)");
                    room.setStars(Room.Star.valueOf(starsStr.toUpperCase()));
                } catch (Exception e) {
                    logger.error("Недопустимое количество звезд");
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
