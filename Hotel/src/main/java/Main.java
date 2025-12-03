

import controller.*;
import service.*;
import view.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ViewFactory factory = ViewFactory.getFactory("console");

        ClientServiceImpl clientService = ClientServiceImpl.getInstance();
        RoomServiceImpl roomService = RoomServiceImpl.getInstance();
        BookingServiceImpl bookingService = BookingServiceImpl.getInstance(roomService, clientService);
        ServiceServiceImpl serviceService = ServiceServiceImpl.getInstance(clientService);
        MultiEntityServiceImpl multiEntityService = MultiEntityServiceImpl.getInstance(roomService, serviceService);


        ClientView clientView = (ClientView) factory.createView(MenuType.CLIENT);
        RoomView roomView = (RoomView) factory.createView(MenuType.ROOM);
        BookingView bookingView = (BookingView) factory.createView(MenuType.BOOKING);
        ServiceView serviceView = (ServiceView) factory.createView(MenuType.SERVICE);
        OtherView otherView = (OtherView) factory.createView(MenuType.OTHER);

        ClientController clientController = ClientController.getInstance(clientService);
        RoomController roomController = RoomController.getInstance(roomService);
        BookingController bookingController = BookingController.getInstance(bookingService,clientService,roomService);
        ServiceController serviceController = ServiceController.getInstance(serviceService,clientService);
        MultiEntityController multiEntityController =  MultiEntityController.getInstance(multiEntityService);

        clientView.setController(clientController);
        roomView.setController(roomController);
        bookingView.setController(bookingController);
        serviceView.setController(serviceController);
        otherView.setController(multiEntityController);

        clientController.importClients();
        roomController.importRooms();
        bookingController.importBookings();
        serviceController.importServices();

        runApplication(clientView, roomView, bookingView, serviceView, otherView);
    }

    private static void runApplication(ClientView clientView, RoomView roomView,
                                       BookingView bookingView, ServiceView serviceView,
                                       OtherView otherView) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            showMainMenu();
            int choice = readInt(scanner);

            switch (choice) {
                case 1 -> runClientMenu(clientView, scanner);
                case 2 -> runRoomMenu(roomView, scanner);
                case 3 -> runBookingMenu(bookingView, scanner);
                case 4 -> runServiceMenu(serviceView, scanner);
                case 5 -> runOtherMenu(otherView, scanner);
                case 0 -> {
                    running = false;
                    System.out.println("Выход из программы...");
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n=== СИСТЕМА УПРАВЛЕНИЯ ОТЕЛЕМ ===");
        System.out.println("1. Клиенты");
        System.out.println("2. Номера");
        System.out.println("3. Бронирования");
        System.out.println("4. Услуги");
        System.out.println("5. Прочее");
        System.out.println("0. Выход");
        System.out.print("Выберите: ");
    }

    private static void runClientMenu(ClientView view, Scanner scanner) {
        boolean inMenu = true;
        while (inMenu) {
            view.showMenu();
            int choice = readInt(scanner);
            inMenu = view.processOperation(choice);
        }
    }

    private static void runRoomMenu(RoomView view, Scanner scanner) {
        boolean inMenu = true;
        while (inMenu) {
            view.showMenu();
            int choice = readInt(scanner);
            inMenu = view.processOperation(choice);
        }
    }

    private static void runBookingMenu(BookingView view, Scanner scanner) {
        boolean inMenu = true;
        while (inMenu) {
            view.showMenu();
            int choice = readInt(scanner);
            inMenu = view.processOperation(choice);
        }
    }

    private static void runServiceMenu(ServiceView view, Scanner scanner) {
        boolean inMenu = true;
        while (inMenu) {
            view.showMenu();
            int choice = readInt(scanner);
            inMenu = view.processOperation(choice);
        }
    }
    private static void runOtherMenu(OtherView view, Scanner scanner) {
        boolean inMenu = true;
        while (inMenu) {
            view.showMenu();
            int choice = readInt(scanner);
            inMenu = view.processOperation(choice);
        }
    }

    private static int readInt(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
}