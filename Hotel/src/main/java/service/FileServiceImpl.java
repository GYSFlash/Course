package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import config.HotelConfig;
import model.Client;
import model.Service;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;



public abstract class FileServiceImpl<T> implements FileService<T>{
    public SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final ObjectMapper mapper;
    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    @Override
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
    @Override
    public void exportToFile(String fileName, List<T> list){
        String fileCSV = "Hotel/src/main/resources/data/" + fileName+".csv";
        String fileJSON = "Hotel/src/main/resources/json/" + fileName+".json";
        String fileType = HotelConfig.getNameFileType();
        switch (fileType) {
            case "csv" -> {
                String line;
                File file = checkFile(fileCSV);
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

                    for (T o : list) {
                        line = writeModel(o);
                        bw.write(line);
                        bw.newLine();
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка записи данных в файл");
                }
            }
            case "json" -> {
                File file = checkFile(fileJSON);
                try{
                    mapper.writeValue(file, list);}
                catch (IOException e){
                    System.out.println("Ошибка в записи данных");
                }
            }
            default -> {
                System.out.println("Ошибка типа файла в property файле");
            }

        }
    }
    @Override
    public void importFromFile(String fileName){
            String fileCSV = "Hotel/src/main/resources/data/" + fileName + ".csv";
            String fileJSON = "Hotel/src/main/resources/json/" + fileName + ".json";
            String fileType = HotelConfig.getNameFileType();

            switch (fileType) {
                case "csv" -> {
                    File file = checkFile(fileCSV);
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            parseModel(line);
                        }
                    } catch (Exception e) {
                        System.out.println("Ошибка чтения данных из файла");
                    }
                }
                case "json" -> {
                    try {
                        File file = checkFile(fileJSON);
                        if (!file.exists() || file.length() == 0) return;
                        var list = mapper.readValue(file,getTypeReference());
                        parseModelJSON(list);
                    } catch (IOException e) {
                        System.out.println("Ошибка чтения clients.json");
                    }
                }
                default -> {
                    System.out.println("Ошибка типа файла в property файле");
                }

            }

    }
    abstract String writeModel(T item);
    abstract void parseModel(String line);
    abstract void parseModelJSON(List<T> list);
    protected abstract TypeReference<List<T>> getTypeReference();
}
