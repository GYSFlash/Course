package com.hotel.controller;


import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import org.springframework.stereotype.Controller;

@Controller
public class FileController extends BaseController {
    private  BookingController bookingController;
    private  ClientController clientController;
    private  RoomController roomController;
    private  ServiceController serviceController;
    public FileController(BookingController bookingController, ClientController clientController, RoomController roomController, ServiceController serviceController) {
        this.bookingController = bookingController;
        this.clientController = clientController;
        this.roomController = roomController;
        this.serviceController = serviceController;
    }

    public void saveAll(){

        clientController.exportClients();
        roomController.exportRooms();
        serviceController.exportServices();
        bookingController.exportBookings();

    }
    public void loadAll(){
        clientController.importClients();
        roomController.importRooms();
        serviceController.importServices();
        bookingController.importBookings();

    }

}
