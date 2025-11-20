package Hotel.Controller;

import Hotel.Model.Client;
import Hotel.Model.Room;
import Hotel.Model.Service;
import Hotel.Service.RoomServiceImpl;
import Hotel.View.RoomView;

import java.math.BigDecimal;

import java.util.List;

public class RoomController extends BaseController {
    private static RoomView view;
    private RoomServiceImpl service;

    public RoomController(RoomServiceImpl service, RoomView view) {
        this.service = service;
        this.view = view;
        setView(view);

    }
    @Override
    public boolean run(int choice) {

            switch (choice) {
                case 1 -> addRoom();
                case 2 -> showAllRooms();
                case 3 -> deleteRoom();
                case 4 -> updateRoom();
                case 5 -> showRoomsByStatus();
                case 6 -> showFreeRoomsCount();
                case 7 -> sortRooms();
                case 8 -> findRoomByNumber();
                case 0 -> {
                    return false;
                }
                default -> view.showError("Неверный выбор");
            }

        return true;
    }

    private void addRoom() {
        int roomNumber = readInt("Номер комнаты");
        while (true){
            if (service.getRoomByRoomNumber(roomNumber) == null) break;
            else view.showError("Такой номер уже существует, введите другой номер");
            roomNumber = readInt("Номер комнаты");
        }

        double price = readDouble("Цена за ночь");
        while (true){
            if (price > 0) break;
            else view.showError("Цена не может быть меньше 0");
            price = readDouble("Цена за ночь");
        }

        int places = readInt("Количество мест");
        while (true){
            if (places > 0) break;
            else view.showError("Мест не может быть меньше 0");
            places = readInt("Цена за ночь");
        }

        String typeStr = readString("Тип (STANDART/STANDARTPLUS/LUX/DELUXE/PRESIDENT)");
        Room.RoomType type;
        while (true){
            if (!(typeStr.equals("STANDART") || typeStr.equals("STANDARTPLUS") || typeStr.equals("LUX")||
                    typeStr.equals("DELUX") || typeStr.equals("PRESIDENT"))) {
                view.showError("Неправильно введен тип комнаты, попробуйте снова: ");
                typeStr = readString("Тип (STANDART/STANDARTPLUS/LUX/DELUXE/PRESIDENT)");
        }
        break;}
        type = Room.RoomType.valueOf(typeStr.toUpperCase());

        String starsStr = readString("Звезды (ONE/TWO/THREE/FOUR/FIVE)");
        Room.Star stars;
        while (true){
            if (!(starsStr.equals("ONE") || starsStr.equals("TWO") || starsStr.equals("THREE")
                    ||starsStr.equals("FOUR") || starsStr.equals("FIVE"))) {
                view.showError("Неправильно введен тип услуги, попробуйте снова: ");
                starsStr = readString("Звезды (ONE/TWO/THREE/FOUR/FIVE)");
        }
            break;
        }
        stars = Room.Star.valueOf(starsStr.toUpperCase());

        Room room = new Room(roomNumber, BigDecimal.valueOf(price), places, type, stars);
        service.addRoom(room);
        view.showMessage("Номер добавлен");
    }

    private void showAllRooms() {
        List<Room> rooms = service.getAllRooms();
        view.showList("ВСЕ НОМЕРА", rooms);
    }

    private void deleteRoom() {
        int roomNumber = readInt("Номер комнаты для удаления");
        while (true){
            if (service.getRoomByRoomNumber(roomNumber) != null) break;
            else view.showError("Такой номер не существует, введите другой номер");
            roomNumber = readInt("Номер комнаты");
        }

        service.deleteRoom(roomNumber);
        view.showMessage("Номер удален");
    }

    private void updateRoom() {

        int roomNumber = readInt("Номер комнаты для обновления");
        while (true){
            if (service.getRoomByRoomNumber(roomNumber) == null) break;
            else view.showError("Такой номер уже существует, введите другой номер");
            roomNumber = readInt("Номер комнаты");
        }
        Room room = service.getRoomByRoomNumber(roomNumber);
        String change = readString("Изменить (price/place/type/star)");
        switch (change) {
            case "price" -> {
                double price = readDouble("Новая цена номера");
                room.setPrice(BigDecimal.valueOf(price));
                view.showMessage("Цена номера обновлена");
            }
            case "place" -> {int places = readInt("Новое количество мест");
                room.setPlace(places);
                view.showMessage("Количество мест обновлено");
            }
            case "type" -> {String typeStr = readString("Тип (STANDART/STANDARTPLUS/LUX/DELUXE/PRESIDENT)");
                room.setType(Room.RoomType.valueOf(typeStr.toUpperCase()));
                view.showMessage("Тип номера обновлена");
            }
            case "star" -> {String starsStr = readString("Звезды (ONE/TWO/THREE/FOUR/FIVE)");
                room.setStars(Room.Star.valueOf(starsStr.toUpperCase()));
                view.showMessage("Количество звезд изменено");
            }
            default -> {
                view.showError("Неверное поле для изменения");
            }

        }
        service.updateRoom(room);
        view.showMessage("Номер обновлен");
    }

    private void showRoomsByStatus() {
        String statusStr = readString("Статус (FREE/REPAIR/OCCUPIED)");
        Room.Status status;
        try {
            status = Room.Status.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            view.showError("Неверный статус");
            return;
        }

        List<Room> rooms = service.getRoomByStatus(status);
        view.showList("НОМЕРА СО СТАТУСОМ: " + status, rooms);
    }

    private void showFreeRoomsCount() {
        int count = service.countFreeRooms();
        view.showMessage("\n=== СВОБОДНЫЕ НОМЕРА ===");
        view.showMessage("Количество свободных номеров: " + count);
    }

    private void sortRooms() {
        String freeOnlyStr = readString("Только свободные? (y/n)");
        boolean freeOnly = freeOnlyStr.equalsIgnoreCase("y");

        String sortBy = readString("Сортировать по (price/place/stars/type)");

        List<Room> rooms = service.sort(freeOnly, sortBy);
        view.showList("ОТСОРТИРОВАННЫЕ НОМЕРА", rooms);
    }

    private void findRoomByNumber() {
        int roomNumber = readInt("Номер комнаты для поиска");
        Room room = service.getRoomByRoomNumber(roomNumber);
        if (room != null) {
            view.showMessage("\n=== НАЙДЕННЫЙ НОМЕР ===");
            view.showObject(room);
        } else {
            view.showError("Номер не найден");
        }
    }
}
