package com.hotel.view;



import com.hotel.controller.BookingController;


public class BookingView extends BaseView {
    private BookingController controller;

    public void setController(BookingController controller) {
        this.controller = controller;
    }
    @Override
    public void showMenu() {
        showMessage("\n=== УПРАВЛЕНИЕ БРОНИРОВАНИЯМИ ===");
        showMessage("1. Добавить бронирование");
        showMessage("2. Все бронирования");
        showMessage("3. Удалить бронирование");
        showMessage("4. Обновить бронирование");
        showMessage("5. Свободные номера на даты");
        showMessage("6. Последние 3 брони номера");
        showMessage("7. Сортировка бронирований");
        showMessage("8. Экспорт бронирований в файл");
        showMessage("9. Вывод всех бронирований по номеру");
        showMessage("0. Назад");
        showMessage("Выберите: ");
    }

    public boolean processOperation(int choice) {
        switch (choice) {
            case 1 -> {if(controller.addBooking())
                showMessage("Бронирование добавлено");
                else showError("Ошибка при добавлении бронирования");
            }
            case 2 -> showList("Все бронирования",controller.showAllBookings());
            case 3 -> {if(controller.deleteBooking())
                showMessage("Бронирование удалено");
            else showError("Введенного Id не существует");}
            case 4 -> {if(controller.updateBooking())
                showMessage("Бронирование обновлено");
            else showError("Ошибка при обновлении бронирования");}
            case 5 -> showList("Свободные номера на даты ",controller.showFreeRoomsByDate());
            case 6 -> showList("Последние 3 брони номера ",controller.showLastThreeBookings());
            case 7 -> showList("Отсортированные бронирования ",controller.sortBookings());
            case 8 -> {showMessage("Экспорт бронирований завершен "); controller.exportBookings();}
            case 9 -> showList("Все клиенты бронировавшие номер ",controller.getClientsStaysByRoom());
            case 0 -> {
                return false;
            }
            default -> {showError("Неверный выбор");}
        }
        return true;
    }


}