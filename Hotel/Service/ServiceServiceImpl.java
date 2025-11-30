package Hotel.Service;

import Hotel.Model.Client;
import Hotel.Model.Service.*;
import Hotel.Model.Service;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class ServiceServiceImpl implements ServiceService {
    private static ServiceServiceImpl instance;
    private Map<Long, Service> services = new HashMap<>();
    private ClientService clientService;
    private ServiceServiceImpl(ClientService clientService) {
        this.clientService = clientService;
    }
    public static ServiceServiceImpl getInstance(ClientService clientService) {
        if(instance == null) {
            instance = new ServiceServiceImpl(clientService);
        }
        return instance;
    }
    @Override
    public void addService(Service service) {
        services.put(service.getId(), service);
    }
    @Override
    public void deleteService(Long id) {
        if (services.containsKey(id)) {
            services.remove(id);
        }
    }
    @Override
    public void updateService(Service service) {
        if (services.containsKey(service.getId())) {
            services.put(service.getId(), service);
        }
    }
    @Override
    public List<Service> getAllServices() {
        List<Service> newServices = new ArrayList<>(services.values());
        return newServices;
    }
    @Override
    public List<Service> sort(String sortBy) {
        if(services.isEmpty()) {
            return null;
        }

        List<Service> serviceList = new ArrayList<>(services.values());

        switch (sortBy) {
            case "price" -> serviceList.sort(Comparator.comparing(Service::getServicePrice));
            case "date"-> serviceList.sort(Comparator.comparing(Service::getDate));
            case "type" -> serviceList.sort(Comparator.comparing(Service::getTypeService));
            default -> {
                System.out.println("Некорректный параметр сортировки");
                return null;
            }
        }

        return serviceList;
    }
    @Override
    public Service getServiceById(Long id) {
        return services.get(id);
    }
    @Override
    public void addServiceFromFile(){
        String fileName = "D:/Java/Course/Hotel/Import/service.csv";
        File file = new File(fileName);
        try{
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            Date date;
            Duration duration;
            BigDecimal price;
            String serviceName;
            Client client;
            TypeService typeService;
            while ((line = br.readLine()) != null) {
                String values[] = line.split(",");
                try {
                    if(values.length == 6) {
                        typeService = TypeService.valueOf(values[0]);
                        serviceName = values[1];
                        price = new BigDecimal(values[2]);
                        String timeStr = values[3];
                        String[] parts = timeStr.split(":");

                        int hours = Integer.parseInt(parts[0].trim());
                        int minutes = Integer.parseInt(parts[1].trim());
                        duration = Duration.ofHours(hours).plusMinutes(minutes);
                        client = clientService.getClientById(Long.parseLong(values[4]));
                        date = dateFormat.parse(values[5]);
                        Service service = new Service(typeService, serviceName, price, duration, client, date);
                        addService(service);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void exportServiceToFile() {
        String fileName = "D:/Java/Course/Hotel/Export/service.csv";
        File file = new File(fileName);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try{
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for(Service service : services.values()) {
                String line = service.getTypeService() + "," + service.getServiceName() + "," + service.getServicePrice() + ","
                        + service.getDuration().toMinutes() + "," + service.getClient().getId() + ","
                        + dateFormat.format(service.getDate());
                bw.write(line);
                bw.newLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
