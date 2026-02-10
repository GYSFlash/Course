package com.hotel.service;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.model.Room;
import com.hotel.model.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Singleton
public class MultiEntityServiceImpl implements MultiEntityService {
    private static final Logger logger = LogManager.getLogger(MultiEntityServiceImpl.class);
    @InjectByType
    private RoomService roomService;
    @InjectByType
    private ServiceService serviceService;


    public MultiEntityServiceImpl(){
    }


    public List<Object> sort(String sortBy){
        logger.info("Сортировка по {}", sortBy);
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
                logger.error("Некорректный параметр сортировки");
                return null;
            }
        }
        List<Object> result = new ArrayList<>();
        result.addAll(rooms);
        result.addAll(services);
        return result;
    }
}
