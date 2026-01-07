package com.hotel.view;


import com.hotel.controller.ClientController;


public class ClientView extends BaseView {
    private ClientController controller;

    public void setController(ClientController controller) {
        this.controller = controller;
    }
    @Override
    public void showMenu() {
        showMessage("\n=== УПРАВЛЕНИЕ КЛИЕНТАМИ ===");
        showMessage("1. Добавить клиента");
        showMessage("2. Все клиенты");
        showMessage("3. Удалить клиента");
        showMessage("4. Обновить клиента");
        showMessage("5. Количество клиентов");
        showMessage("6. Экспорт клиентов в файл");
        showMessage("0. Назад");
        showMessage("Выберите: ");
    }
    public boolean processOperation(int choice) {
        switch (choice) {
            case 1 -> {if(controller.addClient())
            showMessage("Клиент успешно добавлен ");
            else showMessage("Ошибка при добавлении клиента");}
            case 2 -> showList("",controller.showAllClients());
            case 3 -> {if(controller.deleteClient()) showMessage("Клиент успешно удален");
                    else showMessage("Клиент не найден");}
            case 4 -> {if(controller.updateClient()) showMessage("Клиент успешно обновлен");
                    else showMessage("Клиент не найден");}
            case 5 -> showMessage("Всего клиентов: " + controller.showClientsCount());
            case 6 -> {
            showMessage("Экспорт клиентов в файл выполнен"); controller.exportClients();}
            case 0 -> {
                return false;
            }
            default -> {showError("Неизвестная операция");}
        }
        return true;
    }

}