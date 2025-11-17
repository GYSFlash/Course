package Hotel.View;


import Hotel.Model.Service;
import Hotel.Model.Service.TypeService;
import Hotel.Service.ServiceServiceImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class ServiceView extends BaseView {
    private static ServiceView instance;
    private ServiceServiceImpl services;

    private ServiceView() {
        super();
    }

    public static ServiceView getInstance() {
        if (instance == null) {
            instance = new ServiceView();
        }
        return instance;
    }

    public void setService(ServiceServiceImpl service) {
        this.services = service;
    }

    @Override
    public void showMenu() {
        System.out.println("\n=== УПРАВЛЕНИЕ УСЛУГАМИ ===");
        System.out.println("1. Добавить услугу");
        System.out.println("2. Все услуги");
        System.out.println("3. Удалить услугу");
        System.out.println("4. Обновить услугу");
        System.out.println("5. Сортировка услуг");
        System.out.println("0. Назад");
        System.out.print("Выберите: ");
    }

    @Override
    public boolean processOperation(int choice) {
        switch (choice) {
            case 1 -> addService();
            case 2 -> showAllServices();
            case 3 -> deleteService();
            case 4 -> updateService();
            case 5 -> sortServices();
            case 0 -> { return false; }
            default -> showError("Неверный выбор");
        }
        return true;
    }

    private void addService() {
        Long id = readLong("ID услуги");
        if (id == -1) {
            showError("Неверный ID");
            return;
        }

        String typeStr = readString("Тип услуги (ROOM/FOOD/OTHER)");
        TypeService type;
        try {
            type = TypeService.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            showError("Неверный тип услуги");
            return;
        }

        String name = readString("Название услуги");

        double price = readDouble("Цена услуги");
        if (price == -1) {
            showError("Неверная цена");
            return;
        }

        int hours = readInt("Продолжительность (часы)");
        if (hours == -1) {
            showError("Неверная продолжительность");
            return;
        }

        // Здесь должна быть логика выбора клиента
        // Для простоты создаем заглушку
        Service service = new Service(id, type, name, BigDecimal.valueOf(price),
                Duration.ofHours(hours), null, new Date());
        services.addService(service);
        showMessage("Услуга добавлена");
    }

    private void showAllServices() {
        List<Service> Services = services.getAllServices();
        showList("ВСЕ УСЛУГИ", Services);
    }

    private void deleteService() {
        Long id = readLong("ID услуги для удаления");
        if (id == -1) {
            showError("Неверный ID");
            return;
        }

        services.deleteService(id);
        showMessage("Услуга удалена");
    }

    private void updateService() {
        Long id = readLong("ID услуги для обновления");
        if (id == -1) {
            showError("Неверный ID");
            return;
        }

        String typeStr = readString("Новый тип услуги (ROOM/FOOD/OTHER)");
        TypeService type;
        try {
            type = TypeService.valueOf(typeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            showError("Неверный тип услуги");
            return;
        }

        String name = readString("Новое название услуги");

        double price = readDouble("Новая цена услуги");
        if (price == -1) {
            showError("Неверная цена");
            return;
        }

        int hours = readInt("Новая продолжительность (часы)");
        if (hours == -1) {
            showError("Неверная продолжительность");
            return;
        }

        Service service = new Service(id, type, name, BigDecimal.valueOf(price),
                Duration.ofHours(hours), null, new Date());
        services.updateService(service);
        showMessage("Услуга обновлена");
    }

    private void sortServices() {
        String sortBy = readString("Сортировать по (price/date/type)");
        List<Service> Services = services.sort(sortBy);
        showList("ОТСОРТИРОВАННЫЕ УСЛУГИ", Services);
    }
}