package Hotel.View;



import Hotel.Controller.ServiceController;


public class ServiceView extends BaseView {
    private ServiceController controller;

    public void setController(ServiceController controller) {
        this.controller = controller;
    }

    @Override
    public void showMenu() {
        System.out.println("\n=== УПРАВЛЕНИЕ УСЛУГАМИ ===");
        System.out.println("1. Добавить услугу");
        System.out.println("2. Все услуги");
        System.out.println("3. Удалить услугу");
        System.out.println("4. Обновить услугу");
        System.out.println("5. Сортировка услуг");
        System.out.println("0. Назад");
        System.out.print("Выберите: ");
    }

    public boolean processOperation(int choice) {
        return controller.run(choice);
    }

}