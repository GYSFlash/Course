package controller;

import service.JsonFileService;

public class FileController extends BaseController {
    private static FileController instance;
    private JsonFileService fileService;
    public FileController(JsonFileService fileService) {
        this.fileService = fileService;
    }
    public static FileController getInstance(JsonFileService fileService) {
        if (instance == null) {
            instance = new FileController(fileService);
        }
        return instance;
    }
    public void saveAll(){
        fileService.saveAll();
    }
    public void loadAll(){
        fileService.loadAll();
    }
    public void saveClients(){
        fileService.saveClients();
    }
    public void loadClients(){
        fileService.loadClients();
    }
    public void saveBookings(){
        fileService.saveBookings();
    }
    public void loadBookings(){
        fileService.loadBookings();
    }
    public void saveRooms(){
        fileService.saveRooms();
    }
    public void loadRooms(){
        fileService.loadRooms();
    }
    public void saveServices(){
        fileService.saveServices();
    }
    public void loadServices(){
        fileService.loadServices();
    }
}
