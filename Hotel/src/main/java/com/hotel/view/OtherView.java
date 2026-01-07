package com.hotel.view;


import com.hotel.controller.MultiEntityController;

public class OtherView extends BaseView {

    private MultiEntityController controller;

    public void setController(MultiEntityController controller) {
        this.controller = controller;
    }
    @Override
    public void showMenu() {
        showMessage("\n=== ПРОЧИЕ ОПЕРАЦИИ ===");
        showMessage("1. Одновременная сортировка номеров и услуг");
        showMessage("0. Назад");
        showMessage("Выберите: ");
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
