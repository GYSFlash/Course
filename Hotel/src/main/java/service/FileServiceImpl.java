package service;

import model.Client;
import model.Service;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public abstract class FileServiceImpl<T> implements FileService<T> {
    public SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    @Override
    public void checkFile(String fileName){
        File file = new File(fileName);
        try{
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Ошибка в создании файла");;
        }
    }
    @Override
    public void exportToFile(String fileName, List<T> list){
        String file = "Hotel/src/main/resources/data/" + fileName;
        checkFile(file);
        String line;
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            for (T o : list) {
                    line = writeModel(o);
                bw.write(line);
                bw.newLine();
            }
        }
        catch (Exception e) {
            System.out.println("Ошибка записи данных в файл");
        }

    }
    @Override
    public void importFromFile(String fileName){
        String file = "Hotel/src/main/resources/data/" + fileName;
        checkFile(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = br.readLine()) != null) {
                parseModel(line);
            }
        }
        catch (Exception e) {
            System.out.println("Ошибка чтения данных из файла");
        }
    }
    abstract String writeModel(T item);
    abstract void parseModel(String line);
}
