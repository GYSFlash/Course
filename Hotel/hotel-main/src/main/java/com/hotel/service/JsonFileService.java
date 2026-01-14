package com.hotel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hotel.model.Booking;
import com.hotel.model.Client;
import com.hotel.model.Room;
import com.hotel.model.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonFileService {
    private static final String CLIENTS_FILE = "Hotel/src/main/resources/json/clients.json";
    private static final String ROOMS_FILE = "Hotel/src/main/resources/json/rooms.json";
    private static final String BOOKINGS_FILE = "Hotel/src/main/resources/json/bookings.json";
    private static final String SERVICES_FILE = "Hotel/src/main/resources/json/services.json";
    private BookingService bookingService;
    private RoomService roomService;
    private ClientService clientService;
    private ServiceService serviceService;
    private static final ObjectMapper mapper;
    private static JsonFileService instance;

    public static JsonFileService getInstance(BookingService bookingService, RoomService roomService, ClientService clientService, ServiceService serviceService){
        if(instance == null) {
            instance = new JsonFileService(bookingService, roomService, clientService, serviceService);
        }
        return instance;
    }
    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    public JsonFileService(BookingService bookingService, RoomService roomService, ClientService clientService, ServiceService serviceService){
        this.bookingService = bookingService;
        this.roomService = roomService;
        this.clientService = clientService;
        this.serviceService = serviceService;
    }
    public File checkFile(String fileName){
        File file = new File(fileName);
        try{
            File parentDir = file.getParentFile();
            if(!parentDir.exists()){
                parentDir.mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Ошибка в создании файла");;
        }
        return file;
    }
    public void saveClients(){
        try{
            mapper.writeValue(checkFile(CLIENTS_FILE), clientService.getAllClients());}
        catch (IOException e){
            System.out.println("Ошибка в записи данных");
        }
    }
    public void saveBookings(){
        try{
            mapper.writeValue(checkFile(BOOKINGS_FILE), bookingService.getAllBookings());}
        catch (IOException e){
            System.out.println("Ошибка в записи данных");
        }
    }
    public void saveRooms(){
        try{
            mapper.writeValue(checkFile(ROOMS_FILE), roomService.getAllRooms());}
        catch (IOException e){
            System.out.println("Ошибка в записи данных");
        }
    }
    public void saveServices(){
        try{
            mapper.writeValue(checkFile(SERVICES_FILE), serviceService.getAllServices());}
        catch (IOException e){
            System.out.println("Ошибка в записи данных");
        }
    }
    public void saveAll(){
        saveClients();
        saveBookings();
        saveRooms();
        saveServices();
    }
    public void loadClients(){
        try {
            File file = new File(CLIENTS_FILE);
            if (!file.exists() || file.length() == 0) return;
            var list = mapper.readValue(file, new TypeReference<List<Client>>() {});
            list.forEach(clientService::addClient);
        } catch (IOException e) {
            System.out.println("Ошибка чтения clients.json");
        }
    }
    public void loadBookings() {
        try {
            File file = new File(BOOKINGS_FILE);
            if (!file.exists() || file.length() == 0) return;
            var list = mapper.readValue(file, new TypeReference<List<Booking>>() {});
            for (Booking booking : list) {

                bookingService.addBooking(booking);
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения bookings.json");
        }
    }
    public void loadRooms() {
        try {
            File file = new File(ROOMS_FILE);
            if (!file.exists() || file.length() == 0) return;
            var list = mapper.readValue(file, new TypeReference<List<Room>>() {});
            list.forEach(roomService::addRoom);
        } catch (IOException e) {
            System.out.println("Ошибка чтения rooms.json");
        }
    }
    public void loadServices() {
        try {
            File file = new File(SERVICES_FILE);
            if (!file.exists() || file.length() == 0) return;
            var list = mapper.readValue(file, new TypeReference<List<Service>>() {});
            list.forEach(serviceService::addService);

        } catch (IOException e) {
            System.out.println("Ошибка чтения services.json");
        }
    }
    public void loadAll() {
        loadClients();
        loadBookings();
        loadRooms();
        loadServices();
    }

}
