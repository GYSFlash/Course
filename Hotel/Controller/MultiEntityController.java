package Hotel.Controller;

import Hotel.Service.MultiEntityServiceImpl;
import Hotel.View.OtherView;

import java.util.List;

public class MultiEntityController extends BaseController{
    private OtherView view;
    private MultiEntityServiceImpl service;

    public MultiEntityController(MultiEntityServiceImpl service, OtherView view) {
        this.service = service;
        this.view = view;
        setView(view);
    }

    @Override
    public boolean run(int choice) {

            switch (choice) {
                case 1 -> sortRoomAndService();
                case 0 -> {
                    return false;
                }
                default -> view.showError("Неверный выбор");
            }

        return true;
    }
    private void sortRoomAndService() {
        String sortBy = readString("Сортировать по (type/price)");
        List<Object> service1 = service.sort(sortBy);
        view.showList("ОТСОРТИРОВАННЫЕ НОМЕРА И УСЛУГИ", service1);
    }

}
