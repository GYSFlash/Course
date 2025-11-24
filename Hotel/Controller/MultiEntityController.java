package Hotel.Controller;

import Hotel.Service.MultiEntityService;
import Hotel.View.OtherView;

import java.util.List;

public class MultiEntityController extends BaseController{
    private MultiEntityService service;

    public MultiEntityController(MultiEntityService service) {
        this.service = service;
    }


    public List<Object> sortRoomAndService() {
        String sortBy = readString("Сортировать по (type/price)");
        return service.sort(sortBy);
    }

}
