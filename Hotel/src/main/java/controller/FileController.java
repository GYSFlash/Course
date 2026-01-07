package controller;


import controller.*;

public class FileController extends BaseController {
    private static FileController instance;
    private final BookingController bookingController;
    private final ClientController clientController;
    private final RoomController roomController;
    private final ServiceController serviceController;
    public FileController(BookingController bookingController, ClientController clientController, RoomController roomController, ServiceController serviceController) {
        this.bookingController = bookingController;
        this.clientController = clientController;
        this.roomController = roomController;
        this.serviceController = serviceController;
    }
    public static FileController getInstance(BookingController bookingController, ClientController clientController, RoomController roomController, ServiceController serviceController) {
        if (instance == null) {
            instance = new FileController(bookingController, clientController, roomController, serviceController);
        }
        return instance;
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
