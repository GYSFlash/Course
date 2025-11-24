package Hotel.View;


import Hotel.Controller.MultiEntityController;
import Hotel.Controller.ServiceController;
import Hotel.Service.MultiEntityServiceImpl;

import java.util.List;

public class OtherView extends BaseView {

    private MultiEntityController controller;

    public void setController(MultiEntityController controller) {
        this.controller = controller;
    }
    @Override
    public void showMenu() {
        System.out.println("\n=== ПРОЧИЕ ОПЕРАЦИИ ===");
        System.out.println("1. Одновременная сортировка номеров и услуг");
        System.out.println("0. Назад");
        System.out.print("Выберите: ");
    }

    public boolean processOperation(int choice) {
        switch (choice) {
            case 1 -> controller.sortRoomAndService();
            case 0 -> {
                return false;
            }
            default -> showError("Неверный выбор");
        }

        return true;
    }
}
