package com.hotel.controller;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.service.MultiEntityService;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
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
