package service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public interface FileService {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public String bookingFile = "Hotel/src/main/resources/data/bookings.csv";
    public String roomFile = "Hotel/src/main/resources/data/rooms.csv";
    public String clientFile = "Hotel/src/main/resources/data/clients.csv";
    public String serviceFile = "Hotel/src/main/resources/data/service.csv";
    public static String checkFile(String fileName){
        File file = new File(fileName);
        try{
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Ошибка в создании файла");;
        }
        return file.getAbsolutePath();
    }
}
