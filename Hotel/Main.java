package Hotel;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        HotelManage hotel = new HotelManage();

        System.out.println("Создание гостиницы.");

        Room room101 = new Room(101, new BigDecimal("2500.00"), "Стандарт");
        Room room102 = new Room(102, new BigDecimal("3500.00"), "Люкс");
        Room room103 = new Room(103, new BigDecimal("3000.00"), "Бизнес");

        hotel.addRoom(room101);
        hotel.addRoom(room102);
        hotel.addRoom(room103);

        Client client1 = new Client(new Date(), "Иванов", "Иван", "М");
        Client client2 = new Client(new Date(), "Петрова", "Мария", "Ж");

        hotel.addClient(client1);
        hotel.addClient(client2);

        Service breakfast = new Service("Завтрак", new BigDecimal("500.00"), Duration.ofHours(2),client1);
        Service spa = new Service("СПА", new BigDecimal("1500.00"), Duration.ofHours(1),client1);

        hotel.addService(breakfast);
        hotel.addService(spa);



        System.out.println("Пример работы программы:\n");

        System.out.println("1. Свободные номера:");
        hotel.showRoomsIsFree();

        System.out.println("\n2. Заселяем клиента Иванова в номер 101:");
        Date checkIn = new Date();
        Date checkOut = new Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000);
        Booking booking1 = new Booking(checkIn, room101, 1, client1, checkOut);
        hotel.addBooking(booking1);

        System.out.println("\n3. Все бронирования:");
        hotel.showAllBookings();

        System.out.println("\n4. Переводим номер 102 в ремонт:");
        room102.repairRoom();

        System.out.println("\n5. Меняем цену номера 103:");
        System.out.println("Старая цена: " + room103.getPrice());
        room103.setPrice(new BigDecimal("4000.00"));
        System.out.println("Новая цена: " + room103.getPrice());

        System.out.println("\n6. Меняем цену услуги 'Завтрак':");
        System.out.println("Старая цена: " + breakfast.getServicePrice());
        breakfast.setServicePrice(new BigDecimal("600.00"));
        System.out.println("Новая цена: " + breakfast.getServicePrice());

        System.out.println("\n7. Выселяем клиента из номера 101:");
        booking1.endBooking();
        System.out.println("Клиент выселен, номер свободен");

        System.out.println("\n8. Заселяем Петрову в номер 103:");
        hotel.addBooking(new Booking(checkIn, room103, 2, client2, checkOut));
        System.out.println("Клиент заселен, номер занят");

        System.out.println("\n9. Итоговое состояние всех номеров:");
        hotel.showAllRooms();

        System.out.println("\n10. Список всех бронирований:");
        hotel.showAllBookings();

        System.out.println("\n11. Список всех предоставленных услуг:");
        hotel.showAllServices();


    }
}