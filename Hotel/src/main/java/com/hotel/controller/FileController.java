package com.hotel.controller;


import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;

@Singleton
public class FileController extends BaseController {
    @InjectByType
    private  BookingController bookingController;
    @InjectByType
    private  ClientController clientController;
    @InjectByType
    private  RoomController roomController;
    @InjectByType
    private  ServiceController serviceController;
    public FileController() {
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
