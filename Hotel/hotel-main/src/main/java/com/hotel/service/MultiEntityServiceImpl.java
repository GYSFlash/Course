package com.hotel.service;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.dto.RoomDTO;
import com.hotel.dto.ServiceResponseDTO;
import com.hotel.model.Room;
import com.hotel.model.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@org.springframework.stereotype.Service
@Transactional(readOnly = true)
public class MultiEntityServiceImpl implements MultiEntityService {
    private static final Logger logger = LogManager.getLogger(MultiEntityServiceImpl.class);
    private RoomService roomService;
    private ServiceService serviceService;
    public MultiEntityServiceImpl(RoomService roomService, ServiceService serviceService) {
        this.roomService = roomService;
        this.serviceService = serviceService;
    }


    public List<Object> sort(String sortBy){
        logger.info("Сортировка по {}", sortBy);
        List<RoomDTO> rooms = roomService.getAllRooms();
        List<ServiceResponseDTO> services = serviceService.getAllServices();

        switch (sortBy){
            case "price" -> {
                services.sort(Comparator.comparing(ServiceResponseDTO::getServicePrice));
                rooms.sort(Comparator.comparing(RoomDTO::getPrice));
            }
            case "type"->{
                services.sort(Comparator.comparing(ServiceResponseDTO::getTypeService));
                rooms.sort(Comparator.comparing(RoomDTO::getType));
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
