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
        return controller.run(choice);
    }

}