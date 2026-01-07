package com.hotel.controller;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.model.Client;

import com.hotel.service.ClientService;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Singleton
public class ClientController extends BaseController{
    @InjectByType
    private ClientService service;

    public ClientController() {
    }
    public boolean addClient() {

        String name = readString("Имя");
        String surname = readString("Фамилия");

        String dateStr = readString("Дата рождения (гггг-мм-дд)");
        Date dateOfBirth = parseDate(dateStr);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -14);
        Date date = cal.getTime();
        if (dateOfBirth == null ) {
            System.out.println("Неверный формат даты");
            return false;
        } else if (dateOfBirth.after(date)) {
            System.out.println("Возраст меньше 14 лет");
            return false;
        }
        String genderStr = readString("Пол (MALE/FEMALE)");
        Client.Gender gender;
        try {
            gender = Client.Gender.valueOf(genderStr.toUpperCase());
        } catch (Exception e) {
            System.out.println("Неверно указан пол");
            return false;
        }
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
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.YEAR, -14);
                Date date = cal.getTime();
                if (dateOfBirth == null ) {
                    System.out.println("Неверный формат даты");
                    return false;
                } else if (dateOfBirth.after(date)) {
                    System.out.println("Возраст меньше 14 лет");
                    return false;
                }
                client.setDateOfBirth(dateOfBirth);
            }
            case "gender" -> {
                String genderStr = readString("Новый пол (MALE/FEMALE)");

                try {
                    client.setGender(Client.Gender.valueOf(genderStr.toUpperCase()));
            }
                catch (Exception e) {
                    System.out.println("Неверно указан пол");
                    return false;
                }client.setGender(Client.Gender.valueOf(genderStr.toUpperCase()));
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
    public void importClients() {
        service.addClientFromFile();

    }
    public void exportClients() {
        service.exportClientsToFile();

    }

}
