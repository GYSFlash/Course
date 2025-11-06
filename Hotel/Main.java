package Hotel;

import Hotel.Service.*;
import Hotel.Model.*;
import Hotel.Model.Room.*;
import Hotel.Model.Client.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        RoomService roomService = new RoomService();
        ClientService clientService = new ClientService();
        BookingService bookingService = new BookingService();
        ServiceService serviceService = new ServiceService();

        System.out.println("Создание гостиницы.\n");

        // Создание номеров
        Room room101 = new Room(101, new BigDecimal("2500.00"), RoomType.STANDART);
        Room room102 = new Room(102, new BigDecimal("3500.00"), RoomType.LUX);
        Room room103 = new Room(103, new BigDecimal("3000.00"), RoomType.PRESIDENT);

        roomService.addRoom(room101);
        roomService.addRoom(room102);
        roomService.addRoom(room103);

        // Создание клиентов
        Client client1 = new Client(1L,new Date(), "Иванов", "Иван", Gender.MALE);
        Client client2 = new Client(2L,new Date(), "Петрова", "Мария", Gender.FEMALE);

        clientService.addClient(client1);
        clientService.addClient(client2);

        // Создание услуг
        Service breakfast = new Service(1L,"Завтрак", new BigDecimal("500.00"), Duration.ofHours(2), client1);
        Service spa = new Service(2L,"СПА", new BigDecimal("1500.00"), Duration.ofHours(1), client1);

        serviceService.addService(breakfast);
        serviceService.addService(spa);

        System.out.println("Пример работы программы:\n");

        System.out.println("1. Свободные номера:");
        System.out.println(roomService.getRoomByStatus(Status.FREE));

        System.out.println("\n2. Заселяем клиента Иванова в номер 101:");
        Date checkIn = new Date();
        Date checkOut = new Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000);
        Booking booking1 = new Booking(1L, checkIn, room101, client1, checkOut);
        bookingService.addBooking(booking1);
        room101.setStatus(Status.OCCUPIED);

        System.out.println("\n3. Все бронирования:");
        System.out.println(bookingService.getAllBookings());

        System.out.println("\n4. Переводим номер 102 в ремонт:");
        room102.repairRoom();
        roomService.updateRoom(room102);

        System.out.println("\n5. Меняем цену номера 103:");
        System.out.println("Старая цена: " + room103.getPrice());
        room103.setPrice(new BigDecimal("4000.00"));
        roomService.updateRoom(room103);
        System.out.println("Новая цена: " + room103.getPrice());

        System.out.println("\n6. Меняем цену услуги 'Завтрак':");
        System.out.println("Старая цена: " + breakfast.getServicePrice());
        breakfast.setServicePrice(new BigDecimal("600.00"));
        serviceService.updateService(breakfast);
        System.out.println("Новая цена: " + breakfast.getServicePrice());

        System.out.println("\n7. Выселяем клиента из номера 101:");
        booking1.endBooking();
        roomService.updateRoom(room101);
        System.out.println("Клиент выселен, номер свободен");

        System.out.println("\n8. Заселяем Петрову в номер 103:");
        Booking booking2 = new Booking(2L, checkIn, room103, client2, checkOut);
        bookingService.addBooking(booking2);
        room103.setStatus(Status.OCCUPIED);
        roomService.updateRoom(room103);
        System.out.println("Клиент заселен, номер занят");

        System.out.println("\n9. Итоговое состояние всех номеров:");
        System.out.println(roomService.getAllRooms());

        System.out.println("\n10. Список всех бронирований:");
        System.out.println(bookingService.getAllBookings());

        System.out.println("\n11. Список всех предоставленных услуг:");
        System.out.println(serviceService.getAllServices());

        System.out.println("\n12. Список всех клиентов:");
        System.out.println(clientService.getAllClients());
    }

}