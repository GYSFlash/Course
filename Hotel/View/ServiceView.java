package Hotel.View;



import Hotel.Controller.ServiceController;


public class ServiceView extends BaseView {
    private ServiceController controller;

    public void setController(ServiceController controller) {
        this.controller = controller;
    }

    @Override
    public void showMenu() {
        showMessage("\n=== УПРАВЛЕНИЕ УСЛУГАМИ ===");
        showMessage("1. Добавить услугу");
        showMessage("2. Все услуги");
        showMessage("3. Удалить услугу");
        showMessage("4. Обновить услугу");
        showMessage("5. Сортировка услуг");
        showMessage("6. Поиск услуги");
        showMessage("0. Назад");
        showMessage("Выберите: ");
    }

    public boolean processOperation(int choice) {
        switch (choice) {
            case 1 -> {controller.addService();
                showMessage("Услуга добавлена!");
           }
            case 2 -> showList("",controller.showAllServices());
            case 3 -> {if(controller.deleteService())
                    showMessage("Услуга удалена!");
                else showMessage("Услуга не найдена!");}
            case 4 -> {if(controller.updateService())
            showMessage("Услуга обновлена!");
                else showMessage("Услуга не найдена!");}
            case 5 -> showList("Отсортированные услуги: ",controller.sortServices());
            case 6 -> showMessage("Найденная услуга: " + controller.getServiceById());
            case 0 -> {return false;}
            default -> showError("Неверный выбор!");
        }
        return true;
    }

}