package com.hotel.controller;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.service.MultiEntityService;

import java.util.List;
@Singleton
public class MultiEntityController extends BaseController{
    @InjectByType
    private MultiEntityService service;

    public MultiEntityController() {
    }

    public List<Object> sortRoomAndService() {
        String sortBy = readString("Сортировать по (type/price)");
        return service.sort(sortBy);
    }
}
