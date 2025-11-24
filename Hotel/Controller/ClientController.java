package Hotel.Controller;

import Hotel.Model.Client;

import Hotel.Service.ClientService;


import java.util.Date;
import java.util.List;

public class ClientController extends BaseController{

    private ClientService service;

    public ClientController(ClientService service) {

        this.service = service;
    }
    public boolean addClient() {

        String name = readString("Имя");
        String surname = readString("Фамилия");

        String dateStr = readString("Дата рождения (гггг-мм-дд)");
        Date dateOfBirth = parseDate(dateStr);

        String genderStr = readString("Пол (MALE/FEMALE)");
        Client.Gender gender;
        gender = Client.Gender.valueOf(genderStr.toUpperCase());

        Client client = new Client(dateOfBirth, surname, name, gender);
        service.addClient(client);
        return true;
    }

    public List<Client> showAllClients() {
        return service.getAllClients();
    }

    public boolean deleteClient() {
        Long id = readLong("ID клиента для удаления");
        if (service.getClientById(id) == null) {
            return false;
        }
        service.deleteClient(id);
        return true;
    }

    public boolean updateClient() {
        Long id = readLong("ID клиента для обновления");

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
                return false;
            }
        }
        service.updateClient(client);
        return true;
    }

    public int showClientsCount() {
        int count = service.clientsCount();
        return count;
    }

}
