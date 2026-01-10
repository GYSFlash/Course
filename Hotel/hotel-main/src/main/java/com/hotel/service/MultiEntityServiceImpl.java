package com.hotel.service;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.model.Room;
import com.hotel.model.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Singleton
public class MultiEntityServiceImpl implements MultiEntityService {
    @InjectByType
    private RoomService roomService;
    @InjectByType
    private ServiceService serviceService;


    public MultiEntityServiceImpl(){
    }


    public List<Object> sort(String sortBy){
        List<Room> rooms = roomService.getAllRooms();
        List<Service> services = serviceService.getAllServices();

        switch (sortBy){
            case "price" -> {
                services.sort(Comparator.comparing(Service::getServicePrice));
                rooms.sort(Comparator.comparing(Room::getPrice));
            }
            case "type"->{
                services.sort(Comparator.comparing(Service::getTypeService));
                rooms.sort(Comparator.comparing(Room::getType));
            }
            default -> {
                System.out.println("Некорректный параметр сортировки");
                return null;
            }
        }
        List<Object> result = new ArrayList<>();
        result.addAll(rooms);
        result.addAll(services);
        return result;
    }
}
