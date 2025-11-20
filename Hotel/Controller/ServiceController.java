package Hotel.Controller;

import Hotel.Model.Client;
import Hotel.Model.Service;
import Hotel.Service.ClientService;
import Hotel.Service.ServiceServiceImpl;
import Hotel.View.ServiceView;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class ServiceController extends BaseController {
    private ServiceView view;
    private ServiceServiceImpl services;
    private ClientService clientService;

    public ServiceController(ServiceServiceImpl services, ClientService clientService,ServiceView view) {
        this.view = view;
        setView(view);
        this.services = services;
        this.clientService = clientService;
    }

    @Override
    public boolean run(int choice) {

            switch (choice) {
                case 1 -> addService();
                case 2 -> showAllServices();
                case 3 -> deleteService();
                case 4 -> updateService();
                case 5 -> sortServices();
                case 0 -> {return false;}
                default -> view.showError("Неверный выбор!");
            }
        return true;
    }

    private void addService() {
        String typeStr = readString("Тип услуги (ROOM/FOOD/OTHER)");
        while (true) {
            if (!(typeStr.equals("ROOM") || typeStr.equals("FOOD") || typeStr.equals("OTHER"))) {
                System.out.println("Неправильно введен тип услуги, попробуйте снова: ");
                typeStr = readString("Тип услуги (ROOM/FOOD/OTHER)");
            }
            break;
        }

        Service.TypeService type = Service.TypeService.valueOf(typeStr.toUpperCase());

        String name = readString("Название услуги");

        double price = readDouble("Цена услуги");
        while (true) {
            if (price < 0) {
                System.out.println("Неправильно введена цена, попробуйте снова: ");
                price = readDouble("Цена услуги");
            }
            break;
        }
        Duration duration;
        int hours = readInt("Продолжительность (часы)");
        while (true) {
            if (hours < 0) {
                System.out.println("Неправильно введены часы, попробуйте снова: ");
                hours = readInt("Продолжительность (часы)");
            }
            break;
        }
        int minutes = readInt("Продолжительность (минуты)");
        while (true) {
            if (minutes < 0) {
                view.showError("Неправильно введены минуты, попробуйте снова: ");
                minutes = readInt("Продолжительность (минуты)");
            }
            break;
        }
        duration = Duration.ofHours(hours).plusMinutes(minutes);
        Long id = readLong("ID клиента");
        while(clientService.getClientById(id) == null) {
            view.showError("Клиент с таким ID не найден, попробуйте снова: ");
            id = readLong("ID клиента");
        }
        Client client = clientService.getClientById(id);
        Service service = new Service(type, name, BigDecimal.valueOf(price),
                duration,client , new Date());
        services.addService(service);
        view.showMessage("Услуга добавлена");
    }

    private void showAllServices() {
        List<Service> Services = services.getAllServices();
        view.showList("ВСЕ УСЛУГИ", Services);
    }

    private void deleteService() {
        Long id = readLong("ID услуги для удаления");
        while(services.getServiceById(id) == null) {
            view.showError("Услуга с таким ID не найдена, попробуйте снова: ");
            id = readLong("ID услуги для удаления");
        }
        services.deleteService(id);
        view.showMessage("Услуга удалена");
    }

    private void updateService() {
        Long id = readLong("ID услуги для обновления");
        Service service = services.getServiceById(id);
        String change = readString("Изменить (type/name/price/duration/client)");
        switch (change) {
            case "type" -> {
                String typeStr = readString("Новый тип услуги (ROOM/FOOD/OTHER)");
                Service.TypeService type =  Service.TypeService.valueOf(typeStr.toUpperCase());
                service.setTypeService(type);
                view.showMessage("Тип услуги обновлен");
            }
            case "name" -> {String name = readString("Новое название услуги");
                service.setServiceName(name);
                view.showMessage("Название обновлено");
            }
            case "price" -> {double price = readDouble("Новая цена услуги");
                service.setServicePrice(BigDecimal.valueOf(price));
                view.showMessage("Цена обновлена");
            }
            case "duration" -> {int hours = readInt("Новая продолжительность (часы)");
                int minutes = readInt("Продолжительность (минуты)");
                Duration durtion = Duration.ofHours(hours).plusMinutes(minutes);
                service.setDuration(durtion);
                view.showMessage("Продолжительность обновлена");
            }
            case "client" -> { Long idClient = readLong("ID клиента");
                Client client = clientService.getClientById(idClient);
                service.setClient(client);
                view.showMessage("Клиент обновлен");
            }
            default -> {
                view.showError("Неверное поле для изменения");
            }

        }
        services.updateService(service);
        view.showMessage("Услуга обновлена");
    }

    private void sortServices() {
        String sortBy = readString("Сортировать по (price/date/type)");
        List<Service> Services = services.sort(sortBy);
        view.showList("ОТСОРТИРОВАННЫЕ УСЛУГИ", Services);
    }
}
