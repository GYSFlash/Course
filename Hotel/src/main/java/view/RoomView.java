package view;


import controller.FileController;
import controller.RoomController;

public class RoomView extends BaseView {

    private RoomController controller;
    private FileController fileController;
    public void setController(RoomController controller, FileController fileController) {
        this.controller = controller;
        this.fileController = fileController;
    }


    @Override
    public void showMenu() {
        showMessage("\n=== УПРАВЛЕНИЕ НОМЕРАМИ ===");
        showMessage("1. Добавить номер");
        showMessage("2. Все номера");
        showMessage("3. Удалить номер");
        showMessage("4. Обновить номер");
        showMessage("5. Номера по статусу");
        showMessage("6. Количество свободных номеров");
        showMessage("7. Сортировка номеров");
        showMessage("8. Найти номер по номеру комнаты");
        showMessage("9. Экспорт комнат в файл");
        showMessage("10. Смена статуса номера");
        showMessage("0. Назад");
        showMessage("Выберите: ");
    }
    public boolean processOperation(int choice) {
        switch (choice) {
            case 1 -> {if(controller.addRoom())
            showMessage("Номер добавлен");
            else showError("Номер уже существует");}
            case 2 -> showList("Все номера",controller.showAllRooms());
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
            case 9 -> {showMessage("Экспорт комнат завершен"); fileController.saveRooms();}
            case 10 -> {showMessage("Статус изменен"); controller.changeRoomStatus(); }
            case 0 -> {
                return false;
            }
            default -> showError("Неверный выбор");
        }

        return true;
    }

}