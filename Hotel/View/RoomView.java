package Hotel.View;

import Hotel.Model.Room;
import Hotel.Model.Room.RoomType;
import Hotel.Model.Room.Star;
import Hotel.Model.Room.Status;
import Hotel.Service.RoomServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public class RoomView extends BaseView {
    private static RoomView instance;
    private RoomServiceImpl service;

    private RoomView() {
        super();
    }

    public static RoomView getInstance() {
        if (instance == null) {
            instance = new RoomView();
        }
        return instance;
    }

    public void setService(RoomServiceImpl service) {
        this.service = service;
    }

    @Override
    public void showMenu() {
        System.out.println("\n=== УПРАВЛЕНИЕ НОМЕРАМИ ===");
        System.out.println("1. Добавить номер");
        System.out.println("2. Все номера");
        System.out.println("3. Удалить номер");
        System.out.println("4. Обновить номер");
        System.out.println("5. Номера по статусу");
        System.out.println("6. Количество свободных номеров");
        System.out.println("7. Сортировка номеров");
        System.out.println("8. Найти номер по номеру комнаты");
        System.out.println("0. Назад");
        System.out.print("Выберите: ");
    }

    @Override
    public boolean processOperation(int choice) {
        switch (choice) {
            case 1 -> addRoom();
            case 2 -> showAllRooms();
            case 3 -> deleteRoom();
            case 4 -> updateRoom();
            case 5 -> showRoomsByStatus();
            case 6 -> showFreeRoomsCount();
            case 7 -> sortRooms();
            case 8 -> findRoomByNumber();
            case 0 -> { return false; }
            default -> showError("Неверный выбор");
        }
        return true;
    }

    private void addRoom() {
        int roomNumber = readInt("Номер комнаты");
        if (roomNumber == -1) {
            showError("Неверный номер");
            return;
        }

        double price = readDouble("Цена за ночь");
        if (price == -1) {
            showError("Неверная цена");
            return;
        }

        int places = readInt("Количество мест");
        if (places == -1) {
            showError("Неверное количество мест");
            return;
        }

        String typeStr = readString("Тип (STANDART/STANDARTPLUS/LUX/DELUXE/PRESIDENT)");
        RoomType type;
        try {
            type = RoomType.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            showError("Неверный тип номера");
            return;
        }

        String starsStr = readString("Звезды (ONE/TWO/THREE/FOUR/FIVE)");
        Star stars;
        try {
            stars = Star.valueOf(starsStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            showError("Неверное количество звезд");
            return;
        }

        Room room = new Room(roomNumber, BigDecimal.valueOf(price), places, type, stars);
        service.addRoom(room);
        showMessage("Номер добавлен");
    }

    private void showAllRooms() {
        List<Room> rooms = service.getAllRooms();
        showList("ВСЕ НОМЕРА", rooms);
    }

    private void deleteRoom() {
        int roomNumber = readInt("Номер комнаты для удаления");
        if (roomNumber == -1) {
            showError("Неверный номер");
            return;
        }

        service.deleteRoom(roomNumber);
        showMessage("Номер удален");
    }

    private void updateRoom() {
        int roomNumber = readInt("Номер комнаты для обновления");
        if (roomNumber == -1) {
            showError("Неверный номер");
            return;
        }

        double price = readDouble("Новая цена за ночь");
        if (price == -1) {
            showError("Неверная цена");
            return;
        }

        int places = readInt("Новое количество мест");
        if (places == -1) {
            showError("Неверное количество мест");
            return;
        }

        String typeStr = readString("Новый тип (STANDART/STANDARTPLUS/LUX/DELUXE/PRESIDENT)");
        RoomType type;
        try {
            type = RoomType.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            showError("Неверный тип номера");
            return;
        }

        String starsStr = readString("Новые звезды (ONE/TWO/THREE/FOUR/FIVE)");
        Star stars;
        try {
            stars = Star.valueOf(starsStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            showError("Неверное количество звезд");
            return;
        }

        Room room = new Room(roomNumber, BigDecimal.valueOf(price), places, type, stars);
        service.updateRoom(room);
        showMessage("Номер обновлен");
    }

    private void showRoomsByStatus() {
        String statusStr = readString("Статус (FREE/REPAIR/OCCUPIED)");
        Status status;
        try {
            status = Status.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            showError("Неверный статус");
            return;
        }

        List<Room> rooms = service.getRoomByStatus(status);
        showList("НОМЕРА СО СТАТУСОМ: " + status, rooms);
    }

    private void showFreeRoomsCount() {
        int count = service.countFreeRooms();
        System.out.println("\n=== СВОБОДНЫЕ НОМЕРА ===");
        System.out.println("Количество свободных номеров: " + count);
    }

    private void sortRooms() {
        String freeOnlyStr = readString("Только свободные? (y/n)");
        boolean freeOnly = freeOnlyStr.equalsIgnoreCase("y");

        String sortBy = readString("Сортировать по (price/place/stars/type)");

        List<Room> rooms = service.sort(freeOnly, sortBy);
        showList("ОТСОРТИРОВАННЫЕ НОМЕРА", rooms);
    }

    private void findRoomByNumber() {
        int roomNumber = readInt("Номер комнаты для поиска");
        if (roomNumber == -1) {
            showError("Неверный номер");
            return;
        }

        Room room = service.getRoomByRoomNumber(roomNumber);
        if (room != null) {
            System.out.println("\n=== НАЙДЕННЫЙ НОМЕР ===");
            System.out.println(room);
        } else {
            showError("Номер не найден");
        }
    }
}