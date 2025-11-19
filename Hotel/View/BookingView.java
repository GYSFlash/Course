package Hotel.View;



import Hotel.Model.Booking;
import Hotel.Model.Room;
import Hotel.Service.BookingServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookingView extends BaseView {

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




}