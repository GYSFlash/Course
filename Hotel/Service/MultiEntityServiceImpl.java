package Hotel.Service;

import Hotel.Model.Room;
import Hotel.Model.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MultiEntityServiceImpl implements MultiEntityService {
    private RoomServiceImpl roomService;
    private ServiceServiceImpl serviceService;


    public MultiEntityServiceImpl(RoomServiceImpl roomService, ServiceServiceImpl serviceService){
        this.roomService = roomService;
        this.serviceService = serviceService;


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
