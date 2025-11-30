package Hotel.View;


import Hotel.Controller.RoomController;

public class RoomView extends BaseView {

    private RoomController controller;

    public void setController(RoomController controller) {
        this.controller = controller;
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
    public boolean processOperation(int choice) {
        switch (choice) {
            case 1 -> {if(controller.addRoom())
            showMessage("Номер добавлен");
            else showError("Номер уже существует");}
            case 2 -> showList(" ",controller.showAllRooms());
            case 3 -> {if(controller.deleteRoom())
            showMessage("Номер удален");
            else showError("Номер не найден");}
            case 4 -> {if(controller.updateRoom())
            showMessage("Номер обновлен");
            else showError("Номер не найден");}
            case 5 -> showList("",controller.showRoomsByStatus());
            case 6 -> showMessage("Количество свободных номеров: " + controller.showFreeRoomsCount());
            case 7 -> showList("Сортировка номеров",controller.sortRooms());
            case 8 -> showMessage("Номер с заданным id " + controller.findRoomByNumber());
            case 0 -> {
                return false;
            }
            default -> showError("Неверный выбор");
        }

        return true;
    }

}