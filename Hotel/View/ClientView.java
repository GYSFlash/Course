package Hotel.View;


import Hotel.Controller.ClientController;


public class ClientView extends BaseView {
    private ClientController controller;

    public void setController(ClientController controller) {
        this.controller = controller;
    }
    @Override
    public void showMenu() {
        System.out.println("\n=== УПРАВЛЕНИЕ КЛИЕНТАМИ ===");
        System.out.println("1. Добавить клиента");
        System.out.println("2. Все клиенты");
        System.out.println("3. Удалить клиента");
        System.out.println("4. Обновить клиента");
        System.out.println("5. Количество клиентов");
        System.out.println("0. Назад");
        System.out.print("Выберите: ");
    }
    public boolean processOperation(int choice) {
        return controller.run(choice);
    }

}