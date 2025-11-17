package Hotel.View;

import Hotel.Service.MultiEntityService;

import java.util.List;

public class OtherView extends BaseView{
    private static OtherView instance;
    private MultiEntityService service;

    private OtherView() {
        super();
    }

    public static OtherView getInstance() {
        if (instance == null) {
            instance = new OtherView();
        }
        return instance;
    }

    public void setService(MultiEntityService service) {
        this.service = service;
    }
    @Override
    public void showMenu() {
        System.out.println("\n=== ПРОЧИЕ ОПЕРАЦИИ ===");
        System.out.println("1. Одновременная сортировка номеров и услуг");
        System.out.println("0. Назад");
        System.out.print("Выберите: ");
    }

    @Override
    public boolean processOperation(int choice) {
        switch (choice) {
            case 1 -> sortRoomAndService();
            case 0 -> { return false; }
            default -> showError("Неверный выбор");
        }
        return true;
    }
    private void sortRoomAndService() {
        String sortBy = readString("Сортировать по (type/price)");
        List<Object> service1 = service.sort(sortBy);
        showList("ОТСОРТИРОВАННЫЕ НОМЕРА И УСЛУГИ", service1);
    }
}
