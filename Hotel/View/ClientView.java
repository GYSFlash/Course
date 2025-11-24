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
        switch (choice) {
            case 1 -> {controller.addClient();
            showMessage("Клиент успешно добавлен ");}
            case 2 -> showList("",controller.showAllClients());
            case 3 -> {if(controller.deleteClient()) showMessage("Клиент успешно удален");
                    else showMessage("Клиент не найден");}
            case 4 -> {if(controller.updateClient()) showMessage("Клиент успешно обновлен");
                    else showMessage("Клиент не найден");}
            case 5 -> showMessage("Всего клиентов: " + controller.showClientsCount());
            case 0 -> {
                return false;
            }
            default -> {showError("Неизвестная операция");}
        }
        return true;
    }

}