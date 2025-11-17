package Hotel.View;

import Hotel.Model.Client;
import Hotel.Model.Client.Gender;
import Hotel.Service.ClientServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ClientView extends BaseView {
    private static ClientView instance;
    private ClientServiceImpl service;

    private ClientView() {
        super();
    }

    public static ClientView getInstance() {
        if (instance == null) {
            instance = new ClientView();
        }
        return instance;
    }

    public void setService(ClientServiceImpl service) {
        this.service = service;
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

    @Override
    public boolean processOperation(int choice) {
        switch (choice) {
            case 1 -> addClient();
            case 2 -> showAllClients();
            case 3 -> deleteClient();
            case 4 -> updateClient();
            case 5 -> showClientsCount();
            case 0 -> { return false; }
            default -> showError("Неверный выбор");
        }
        return true;
    }

    private void addClient() {
        Long id = readLong("ID клиента");
        while (id < 1 ) {
            showError("Неверный ID");
            id = readLong("ID клиента");
        }

        String name = readString("Имя");
        String surname = readString("Фамилия");

        String dateStr = readString("Дата рождения (гггг-мм-дд)");
        Date dateOfBirth = parseDate(dateStr);

        String genderStr = readString("Пол (MALE/FEMALE)");
        Gender gender;
        try {
            gender = Gender.valueOf(genderStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            showError("Неверный пол");
            return;
        }

        Client client = new Client(id, dateOfBirth, surname, name, gender);
        service.addClient(client);
        showMessage("Клиент добавлен");
    }

    private void showAllClients() {
        List<Client> clients = service.getAllClients();
        showList("ВСЕ КЛИЕНТЫ", clients);
    }

    private void deleteClient() {
        Long id = readLong("ID клиента для удаления");
        if (id == -1) {
            showError("Неверный ID");
            return;
        }

        service.deleteClient(id);
        showMessage("Клиент удален");
    }

    private void updateClient() {
        Long id = readLong("ID клиента для обновления");
        if (id == -1) {
            showError("Неверный ID");
            return;
        }

        String name = readString("Новое имя");
        String surname = readString("Новая фамилия");

        String dateStr = readString("Новая дата рождения (гггг-мм-дд)");
        Date dateOfBirth = parseDate(dateStr);

        String genderStr = readString("Новый пол (MALE/FEMALE)");
        Gender gender;
        try {
            gender = Gender.valueOf(genderStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            showError("Неверный пол");
            return;
        }

        Client client = new Client(id, dateOfBirth, surname, name, gender);
        service.updateClient(client);
        showMessage("Клиент обновлен");
    }

    private void showClientsCount() {
        int count = service.clientsCount();
        System.out.println("\n=== КОЛИЧЕСТВО КЛИЕНТОВ ===");
        System.out.println("Всего клиентов: " + count);
    }

    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.parse(dateStr);
        } catch (Exception e) {
            showError("Неверный формат даты. Используется текущая дата");
            return new Date();
        }
    }
}