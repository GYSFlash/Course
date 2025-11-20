package Hotel.Controller;

import Hotel.Model.Booking;
import Hotel.Model.Client;
import Hotel.Model.Room;
import Hotel.Service.BookingServiceImpl;
import Hotel.Service.ClientService;
import Hotel.Service.RoomService;
import Hotel.View.BookingView;

import java.util.Date;
import java.util.List;

public class BookingController extends BaseController {
    private BookingView view;
    private BookingServiceImpl service;
    private ClientService clientService;
    private RoomService roomService;

    public BookingController( BookingServiceImpl service, ClientService clientService, RoomService roomService,
                               BookingView view) {
        this.service = service;
        this.clientService = clientService;
        this.roomService = roomService;
        this.view = view;
        setView(view);

    }

    @Override
    public boolean run(int choice) {
            switch (choice) {
                case 1 -> addBooking();
                case 2 -> showAllBookings();
                case 3 -> deleteBooking();
                case 4 -> updateBooking();
                case 5 -> showFreeRoomsByDate();
                case 6 -> showLastThreeBookings();
                case 7 -> sortBookings();
                case 0 -> {
                    return false;
                }
                default -> view.showError("Неверный выбор");
            }

        return true;
    }

    private void addBooking() {
        String dateStr = readString("Дата рождения (гггг-мм-дд)");
        Date checkIn = parseDate(dateStr);
        dateStr = readString("Дата рождения (гггг-мм-дд)");
        Date checkOut = parseDate(dateStr);
        Long id = readLong("ID клиента");
        while(clientService.getClientById(id) == null) {
            view.showError("Клиент с таким ID не найден, попробуйте снова: ");
            id = readLong("ID клиента");
        }
        Client client = clientService.getClientById(id);
        int roomNumber = readInt("Номер комнаты");
        while(roomService.getRoomByRoomNumber(roomNumber) == null) {
            view.showError("Комната с таким номером не найдена, попробуйте снова: ");
            roomNumber = readInt("Номер комнаты");
        }
        Room room = roomService.getRoomByRoomNumber(roomNumber);
        Booking booking = new Booking(checkIn, room, client, checkOut);
        service.addBooking(booking);
        view.showMessage("Бронирование добавлено");

    }

    private void showAllBookings() {
        List<Booking> bookings = service.getAllBookings();
            view.showList("ВСЕ БРОНИРОВАНИЯ", bookings);
    }

    private void deleteBooking() {
        Long id = readLong("ID бронирования для удаления");
        while (service.getBookingById(id) == null) {
            view.showError("Бронирование не найдено, попробуйте снова");
            id = readLong("ID бронирования для удаления");
        }

        service.deleteBooking(id);
            view.showMessage("Бронирование удалено");
    }

    private void updateBooking() {
        Long id = readLong("ID бронирования для обновления");


            view.showMessage("Обновление бронирования (требуется реализация)");
    }

    private void showFreeRoomsByDate() {
        String checkInStr = readString("Дата заезда (гггг-мм-дд)");
        String checkOutStr = readString("Дата выезда (гггг-мм-дд)");

        Date checkIn = parseDate(checkInStr);
        Date checkOut = parseDate(checkOutStr);

        List<Room> freeRooms = service.getFreeRoomsByDate(checkIn, checkOut);
            view.showList("СВОБОДНЫЕ НОМЕРА НА ДАТЫ", freeRooms);
    }

    private void showLastThreeBookings() {
        int roomNumber = readInt("Номер комнаты");


        List<Booking> bookings = service.lastThreeBookingsByRooms(roomNumber);
            view.showList("ПОСЛЕДНИЕ 3 БРОНИ КОМНАТЫ " + roomNumber, bookings);
    }

    private void sortBookings() {
        String sortBy = readString("Сортировать по (client/checkOutDate)");
        List<Booking> bookings = service.sort(sortBy);
            view.showList("ОТСОРТИРОВАННЫЕ БРОНИРОВАНИЯ", bookings);
    }
}
