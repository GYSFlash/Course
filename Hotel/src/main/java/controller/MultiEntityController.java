package controller;

import service.MultiEntityService;

import java.util.List;

public class MultiEntityController extends BaseController{
    private static MultiEntityController instance;
    private MultiEntityService service;

    private MultiEntityController(MultiEntityService service) {
        this.service = service;
    }

    public static MultiEntityController getInstance(MultiEntityService service) {
        if(instance == null){
            instance = new MultiEntityController(service);
        }
        return instance;
    }
    public List<Object> sortRoomAndService() {
        String sortBy = readString("Сортировать по (type/price)");
        return service.sort(sortBy);
    }
}
