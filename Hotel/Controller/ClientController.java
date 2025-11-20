package Hotel.Controller;

import Hotel.Model.Client;
import Hotel.Model.Room;
import Hotel.Service.ClientServiceImpl;
import Hotel.View.ClientView;

import java.util.Date;
import java.util.List;

public class ClientController extends BaseController{
    private ClientView view;
    private ClientServiceImpl service;

    public ClientController(ClientView view, ClientServiceImpl service) {
        this.view = view;
        setView(view);
        this.service = service;
    }
    public boolean run(int choice) {

            switch (choice) {
                case 1 -> addClient();
                case 2 -> showAllClients();
                case 3 -> deleteClient();
                case 4 -> updateClient();
                case 5 -> showClientsCount();
                case 0 -> {
                    return false;
                }
                default -> view.showError("Неверный выбор");
            }
        return true;
    }

    private void addClient() {

        String name = readString("Имя");
        String surname = readString("Фамилия");

        String dateStr = readString("Дата рождения (гггг-мм-дд)");
        Date dateOfBirth = parseDate(dateStr);

        String genderStr = readString("Пол (MALE/FEMALE)");
        Client.Gender gender;
        while (true){
            if (!(genderStr.equals("MALE") || genderStr.equals("FENALE"))) {
                System.out.println("Неправильно введен пол, попробуйте снова: ");
                genderStr = readString("Пол (MALE/FEMALE)");
            }
            break;
        }
        gender = Client.Gender.valueOf(genderStr.toUpperCase());


        Client client = new Client(dateOfBirth, surname, name, gender);
        service.addClient(client);
        view.showMessage("Клиент добавлен");
    }

    private void showAllClients() {
        List<Client> clients = service.getAllClients();
        view.showList("ВСЕ КЛИЕНТЫ", clients);
    }

    private void deleteClient() {
        Long id = readLong("ID клиента для удаления");
        while(service.getClientById(id) == null) {
            view.showError("Клиент с таким ID не найден, попробуйте снова: ");
            id = readLong("ID клиента для удаления");
        }
        service.deleteClient(id);
        view.showMessage("Клиент удален");
    }

    private void updateClient() {
        Long id = readLong("ID клиента для обновления");
        while(service.getClientById(id) == null) {
            view.showError("Клиент с таким ID не найден, попробуйте снова: ");
            id = readLong("ID клиента для удаления");
        }
        String chance = readString("Введите поле для изменения (name, surname, dateOfBirth, gender)");
        Client client = service.getClientById(id);
        switch (chance) {
            case "name" -> {
                String name = readString("Новое имя");
                client.setName(name);
            }
            case "surname" -> {
                String surname = readString("Новая фамилия");
                client.setSurname(surname);
            }
            case "dateOfBirth" -> {
                String dateStr = readString("Новая дата рождения (гггг-мм-дд)");
                Date dateOfBirth = parseDate(dateStr);
                client.setDateOfBirth(dateOfBirth);
            }
            case "gender" -> {
                String genderStr = readString("Новый пол (MALE/FEMALE)");
                Client.Gender gender;
                client.setGender(Client.Gender.valueOf(genderStr.toUpperCase()));
            }
            default -> {
                view.showError("Неверное поле для изменения");
            }
        }
        service.updateClient(client);
        view.showMessage("Клиент обновлен");
    }

    private void showClientsCount() {
        int count = service.clientsCount();
        view.showMessage("\n=== КОЛИЧЕСТВО КЛИЕНТОВ ===");
        view.showMessage("Всего клиентов: " + count);
    }

}
