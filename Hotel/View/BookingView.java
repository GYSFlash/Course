package Hotel.View;


import Hotel.Model.Booking;
import Hotel.Model.Room;
import Hotel.Service.BookingServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookingView extends BaseView {
    private static BookingView instance;
    private BookingServiceImpl service;

    private BookingView() {
        super();
    }

    public static BookingView getInstance() {
        if (instance == null) {
            instance = new BookingView();
        }
        return instance;
    }

    public void setService(BookingServiceImpl service) {
        this.service = service;
    }

    @Override
    public void showMenu() {
        System.out.println("\n=== УПРАВЛЕНИЕ БРОНИРОВАНИЯМИ ===");
        System.out.println("1. Добавить бронирование");
        System.out.println("2. Все бронирования");
        System.out.println("3. Удалить бронирование");
        System.out.println("4. Обновить бронирование");
        System.out.println("5. Свободные номера на даты");
        System.out.println("6. Последние 3 брони номера");
        System.out.println("7. Сортировка бронирований");
        System.out.println("0. Назад");
        System.out.print("Выберите: ");
    }

    @Override
    public boolean processOperation(int choice) {
        switch (choice) {
            case 1 -> addBooking();
            case 2 -> showAllBookings();
            case 3 -> deleteBooking();
            case 4 -> updateBooking();
            case 5 -> showFreeRoomsByDate();
            case 6 -> showLastThreeBookings();
            case 7 -> sortBookings();
            case 0 -> { return false; }
            default -> showError("Неверный выбор");
        }
        return true;
    }

    private void addBooking() {
        Long id = readLong("ID бронирования");
        if (id == -1) {
            showError("Неверный ID");
            return;
        }

        // Здесь должна быть логика выбора клиента и комнаты
        // Для простоты создаем заглушки
        showMessage("Добавление бронирования (требуется реализация выбора клиента и комнаты)");
    }

    private void showAllBookings() {
        List<Booking> bookings = service.getAllBookings();
        showList("ВСЕ БРОНИРОВАНИЯ", bookings);
    }

    private void deleteBooking() {
        Long id = readLong("ID бронирования для удаления");
        if (id == -1) {
            showError("Неверный ID");
            return;
        }

        service.deleteBooking(id);
        showMessage("Бронирование удалено");
    }

    private void updateBooking() {
        Long id = readLong("ID бронирования для обновления");
        if (id == -1) {
            showError("Неверный ID");
            return;
        }

        showMessage("Обновление бронирования (требуется реализация)");
    }

    private void showFreeRoomsByDate() {
        String checkInStr = readString("Дата заезда (гггг-мм-дд)");
        String checkOutStr = readString("Дата выезда (гггг-мм-дд)");

        Date checkIn = parseDate(checkInStr);
        Date checkOut = parseDate(checkOutStr);

        List<Room> freeRooms = service.getFreeRoomsByDate(checkIn, checkOut, null);
        showList("СВОБОДНЫЕ НОМЕРА НА ДАТЫ", freeRooms);
    }

    private void showLastThreeBookings() {
        int roomNumber = readInt("Номер комнаты");
        if (roomNumber == -1) {
            showError("Неверный номер");
            return;
        }

        List<Booking> bookings = service.lastThreeBookingsByRooms(roomNumber);
        showList("ПОСЛЕДНИЕ 3 БРОНИ КОМНАТЫ " + roomNumber, bookings);
    }

    private void sortBookings() {
        String sortBy = readString("Сортировать по (client/checkOutDate)");
        List<Booking> bookings = service.sort(sortBy);
        showList("ОТСОРТИРОВАННЫЕ БРОНИРОВАНИЯ", bookings);
    }

    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.parse(dateStr);
        } catch (Exception e) {
            showError("Неверный формат даты. Используется текущая дата");
            return new Date();
        }
    }
}