package com.hotel.view;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.controller.FileController;
import com.hotel.di.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Scanner;


@Component
public class ConsoleViewFactory extends ViewFactory {
    private ClientView clientView;
    private RoomView roomView;
    private BookingView bookingView;
    private ServiceView serviceView;
    private OtherView otherView;
    private FileController fileController;
    public ConsoleViewFactory(ClientView clientView, RoomView roomView, BookingView bookingView, ServiceView serviceView, OtherView otherView, FileController fileController) {
        this.clientView = clientView;
        this.roomView = roomView;
        this.bookingView = bookingView;
        this.serviceView = serviceView;
        this.otherView = otherView;
        this.fileController = fileController;
    }

    public void runApplication() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            showMainMenu();
            int choice = readInt(scanner);

            switch (choice) {
                case 1 -> runMenu(clientView, scanner);
                case 2 -> runMenu(roomView, scanner);
                case 3 -> runMenu(bookingView, scanner);
                case 4 -> runMenu(serviceView, scanner);
                case 5 -> runMenu(otherView, scanner);
                case 0 -> {
                    running = false;
                    System.out.println("Выход из программы...");
                    fileController.saveAll();
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
    private void runMenu(BaseView view, Scanner scanner) {
        boolean inMenu = true;
        while (inMenu) {
            view.showMenu();
            int choice = readInt(scanner);
            inMenu = view.processOperation(choice);
        }
    }
}
