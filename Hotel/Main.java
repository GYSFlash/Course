package Hotel;

import Hotel.Service.*;
import Hotel.Model.*;
import Hotel.Model.Room.*;
import Hotel.Model.Client.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        RoomServiceImpl roomService = new RoomServiceImpl();
        ClientServiceImpl clientService = new ClientServiceImpl();
        BookingServiceImpl bookingService = new BookingServiceImpl();
        ServiceServiceImpl serviceService = new ServiceServiceImpl();

        System.out.println("Создание гостиницы.\n");

// --- Комнаты ---
        Room room101 = new Room(101, new BigDecimal("2500.00"), 2, RoomType.STANDART, Star.THREE);
        Room room102 = new Room(102, new BigDecimal("3500.00"), 3, RoomType.LUX, Star.FOUR);
        Room room103 = new Room(103, new BigDecimal("4000.00"), 2, RoomType.PRESIDENT, Star.FIVE);
        Room room104 = new Room(104, new BigDecimal("2000.00"), 1, RoomType.STANDART, Star.TWO);
        Room room105 = new Room(105, new BigDecimal("3000.00"), 2, RoomType.LUX, Star.FOUR);
        Room room106 = new Room(106, new BigDecimal("1500.00"), 1, RoomType.STANDART, Star.TWO);
        Room room107 = new Room(107, new BigDecimal("4500.00"), 4, RoomType.PRESIDENT, Star.FIVE);
        Room room108 = new Room(108, new BigDecimal("2200.00"), 1, RoomType.STANDART, Star.THREE);

        roomService.addRoom(room101);
        roomService.addRoom(room102);
        roomService.addRoom(room103);
        roomService.addRoom(room104);
        roomService.addRoom(room105);
        roomService.addRoom(room106);
        roomService.addRoom(room107);
        roomService.addRoom(room108);

// --- Клиенты ---
        Client client1 = new Client(1L, new Date(), "Иванов", "Иван", Gender.MALE);
        Client client2 = new Client(2L, new Date(), "Петрова", "Мария", Gender.FEMALE);
        Client client3 = new Client(3L, new Date(), "Сидоров", "Алексей", Gender.MALE);
        Client client4 = new Client(4L, new Date(), "Кузнецова", "Ольга", Gender.FEMALE);
        Client client5 = new Client(5L, new Date(), "Николаев", "Дмитрий", Gender.MALE);
        Client client6 = new Client(6L, new Date(), "Смирнова", "Екатерина", Gender.FEMALE);

        clientService.addClient(client1);
        clientService.addClient(client2);
        clientService.addClient(client3);
        clientService.addClient(client4);
        clientService.addClient(client5);
        clientService.addClient(client6);

// --- Услуги ---
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        Date date1 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date date2 = calendar.getTime();

        Service breakfast1 = new Service(1L, "Завтрак", new BigDecimal("500.00"), Duration.ofHours(2), client1, date1);
        Service breakfast2 = new Service(2L, "Завтрак", new BigDecimal("500.00"), Duration.ofHours(2), client3, date2);
        Service spa = new Service(3L, "СПА", new BigDecimal("1500.00"), Duration.ofHours(1), client2, date1);
        Service laundry = new Service(4L, "Прачечная", new BigDecimal("300.00"), Duration.ofHours(3), client1, date2);
        Service dinner = new Service(5L, "Ужин", new BigDecimal("800.00"), Duration.ofHours(2), client4, date1);
        Service gym = new Service(6L, "Фитнес-зал", new BigDecimal("600.00"), Duration.ofHours(1), client5, date2);

        serviceService.addService(breakfast1);
        serviceService.addService(breakfast2);
        serviceService.addService(spa);
        serviceService.addService(laundry);
        serviceService.addService(dinner);
        serviceService.addService(gym);

// --- Бронирования ---
        Date today = new Date();

        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        Date checkIn1 = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        Date checkOut1 = calendar.getTime();

        Booking booking1 = new Booking(1L, checkIn1, room101, client1, checkOut1);
        Booking booking2 = new Booking(2L, checkIn1, room102, client2, checkOut1);
        Booking booking3 = new Booking(3L, checkIn1, room103, client3, checkOut1);
        Booking booking4 = new Booking(4L, checkIn1, room104, client4, checkOut1);
        Booking booking5 = new Booking(5L, checkIn1, room105, client5, checkOut1);
        Booking booking6 = new Booking(6L, checkIn1, room106, client6, checkOut1);

        bookingService.addBooking(booking1);
        bookingService.addBooking(booking2);
        bookingService.addBooking(booking3);
        bookingService.addBooking(booking4);
        bookingService.addBooking(booking5);
        bookingService.addBooking(booking6);

// Обновление статусов комнат

        room102.setStatus(Status.OCCUPIED);


        System.out.println("Данные гостиницы успешно созданы!");


        System.out.println("\n=== ПРОВЕРКА ФУНКЦИОНАЛА ===\n");

        // 1. Список номеров (сортировать по цене, вместимости, количеству звезд)
        System.out.println("1. ВСЕ НОМЕРА:");
        List<Room> allRooms = roomService.getAllRooms();

        System.out.println("\nСортировка по цене:");
        Collections.sort(allRooms);
        System.out.println(allRooms);

        System.out.println("\nСортировка по вместимости:");
        Room.RoomPlace roomPlaceComparator = room101.new RoomPlace();
        Collections.sort(allRooms, roomPlaceComparator);
        System.out.println(allRooms);

        System.out.println("\nСортировка по количеству звезд:");
        Room.RoomStars roomStarsComparator = room101.new RoomStars();
        Collections.sort(allRooms, roomStarsComparator);
        System.out.println(allRooms);

        // 2. Список свободных номеров
        System.out.println("\n2. СВОБОДНЫЕ НОМЕРА:");
        List<Room> freeRooms = roomService.getRoomByStatus(Status.FREE);

        System.out.println("\nСортировка по цене:");
        Collections.sort(freeRooms);
        System.out.println(freeRooms);

        System.out.println("\nСортировка по вместимости:");
        Collections.sort(freeRooms, roomPlaceComparator);
        System.out.println(freeRooms);

        System.out.println("\nСортировка по количеству звезд:");
        Collections.sort(freeRooms, roomStarsComparator);
        System.out.println(freeRooms);

        // 3. Список постояльцев и их номеров (сортировать по алфавиту, дате освобождения номера)
        System.out.println("\n3. ПОСТОЯЛЬЦЫ И ИХ НОМЕРА:");
        List<Client> allClients = clientService.getAllClients();
        List<Booking> allBookings = bookingService.getAllBookings();

        System.out.println("\nСортировка по алфавиту:");
        Collections.sort(allClients);
        System.out.println(allClients);

        System.out.println("\nСортировка по дате освобождения номера:");
        Booking.BookingOutDate bookingOutDateComparator = booking1.new BookingOutDate();
        Collections.sort(allBookings, bookingOutDateComparator);
        System.out.println(allBookings);

        // 4. Общее число свободных номеров
        System.out.println("\n4. ОБЩЕЕ ЧИСЛО СВОБОДНЫХ НОМЕРОВ: " + roomService.countFreeRooms());

        // 5. Общее число постояльцев
        System.out.println("\n5. ОБЩЕЕ ЧИСЛО ПОСТОЯЛЬЦЕВ: " + clientService.clientsCount());

        // 6. Список номеров которые будут свободны по определенной дате в будущем
        System.out.println("\n6. НОМЕРА СВОБОДНЫЕ К ОПРЕДЕЛЕННОЙ ДАТЕ:");
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, 4);
        Date futureDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        Date futureDate1 = calendar.getTime();


        System.out.println("Свободные номера с " + futureDate + " до " + futureDate1 + " :");
        System.out.println(bookingService.getFreeRoomsByDate(futureDate, futureDate1));

        // 7. Сумму оплаты за номер которую должен оплатить постоялец
        System.out.println("\n7. СУММА ОПЛАТЫ ЗА НОМЕР:");
        System.out.println(allBookings);

        // 8. Посмотреть 3-х последних постояльцев номера и даты их пребывания
        System.out.println("\n8. 3 ПОСЛЕДНИХ ПОСТОЯЛЬЦА НОМЕРА 101:");
        List<Booking> lastThreeBookings = bookingService.lastThreeBookingsByRooms(101);
        System.out.println(lastThreeBookings);

        // 9. Посмотреть список услуг постояльца и их цену (сортировать по цене, по дате)
        System.out.println("\n9. УСЛУГИ ПОСТОЯЛЬЦЕВ:");
        List<Service> allServices = serviceService.getAllServices();

        System.out.println("\nУслуги клиента Иванов (сортировка по цене):");
        Service.ServicePrice servicePriceComparator = breakfast1.new ServicePrice();
        List<Service> ivanovServices = new ArrayList<>();
        for (Service service : allServices) {
            if (service.getClient().getSurname().equals("Иванов")) {
                ivanovServices.add(service);
            }
        }
        Collections.sort(ivanovServices, servicePriceComparator);
        System.out.println(ivanovServices);

        System.out.println("\nУслуги клиента Иванов (сортировка по дате):");
        Collections.sort(ivanovServices);
        System.out.println(ivanovServices);

        // 10. Цены услуг и номеров (сортировать по разделу, цене)
        System.out.println("\n10. ЦЕНЫ УСЛУГ И НОМЕРОВ:");

        System.out.println("\nУслуги (сортировка по цене):");
        Collections.sort(allServices, servicePriceComparator);
        System.out.println(allServices);

        System.out.println("\nНомера (сортировка по цене):");
        Collections.sort(allRooms);
        System.out.println(allRooms);

        // 11. Посмотреть детали отдельного номера
        System.out.println("\n11. ДЕТАЛИ НОМЕРА 103:");
        List<Room> rooms = roomService.getAllRooms();
        Room room103Details = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == 103) {
                room103Details = room;
                break;
            }
        }
        if (room103Details != null) {
            System.out.println(room103Details);
        }

        System.out.println("Поселение в номер:");
        Booking newBooking = new Booking(2L, new Date(), room102, client2,
                new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000));
        bookingService.addBooking(newBooking);
        room102.setStatus(Status.OCCUPIED);

        System.out.println("\nВыселение из номера:");
        booking1.endBooking();
        roomService.updateRoom(room101);
        System.out.println("Клиент выселен из номера 101");

        System.out.println("\nПеревод номера в ремонт:");
        room104.repairRoom();
        roomService.updateRoom(room104);

        System.out.println("\nИзменение цен:");
        System.out.println("Старая цена номера 102: " + room102.getPrice());
        room102.setPrice(new BigDecimal("3800.00"));
        roomService.updateRoom(room102);
        System.out.println("Новая цена номера 102: " + room102.getPrice());

        System.out.println("Старая цена услуги 'Завтрак': " + breakfast2.getServicePrice());
        breakfast2.setServicePrice(new BigDecimal("600.00"));
        serviceService.updateService(breakfast2);
        System.out.println("Новая цена услуги 'Завтрак': " + breakfast2.getServicePrice());

        System.out.println("Всего номеров: " + roomService.getAllRooms().size());
        System.out.println("Свободных номеров: " + roomService.countFreeRooms());
        System.out.println("Всего клиентов: " + clientService.clientsCount());
        System.out.println("Всего услуг: " + serviceService.getAllServices().size());

    }
}