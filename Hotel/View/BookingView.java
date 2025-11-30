package Hotel.View;



import Hotel.Controller.BookingController;


public class BookingView extends BaseView {
    private BookingController controller;

    public void setController(BookingController controller) {
        this.controller = controller;
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

    public boolean processOperation(int choice) {
        switch (choice) {
            case 1 -> {controller.addBooking();
                showMessage("Бронирование добавлено");
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
            case 0 -> {
                return false;
            }
            default -> {showError("Неверный выбор");}
        }
        return true;
    }


}