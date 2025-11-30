package Hotel.Service;

import Hotel.Model.Room;
import Hotel.Model.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MultiEntityServiceImpl implements MultiEntityService {
    private static MultiEntityServiceImpl incstance;
    private RoomService roomService;
    private ServiceService serviceService;


    private MultiEntityServiceImpl(RoomService roomService, ServiceService serviceService){
        this.roomService = roomService;
        this.serviceService = serviceService;
    }
    public static MultiEntityServiceImpl getInstance(RoomService roomService, ServiceService serviceService){
        if(incstance == null) {
            incstance = new MultiEntityServiceImpl(roomService, serviceService);
        }
        return incstance;
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
