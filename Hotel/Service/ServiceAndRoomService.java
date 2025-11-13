package Hotel.Service;

import Hotel.Model.Room;
import Hotel.Model.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceAndRoomService {
    private RoomServiceImpl roomService;
    private ServiceServiceImpl serviceService;

    public ServiceAndRoomService(RoomServiceImpl roomService, ServiceServiceImpl serviceService){
        this.roomService = roomService;
        this.serviceService = serviceService;


    }

    public List<Object> sort(String sortBy){
        List<Room> rooms = roomService.getAllRooms();
        List<Service> services = serviceService.getAllServices();

        switch (sortBy){
            case "price":
                rooms = roomService.sort(false,"price");
                services = serviceService.sort("price");
                break;
            case "type":
                rooms = roomService.sort(false,"type");
                services = serviceService.sort("type");
                break;
        }
        List<Object> result = new ArrayList<>();
        result.addAll(rooms);
        result.addAll(services);
        return result;
    }
}
