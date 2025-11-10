package Hotel.Model;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Comparator;
import java.util.Date;

public class Service implements Comparable<Service> {
    private Long id;
    private String serviceName;
    private BigDecimal servicePrice;
    private Duration duration;
    private Date date;
    private Client client;
    public Service(Long id,String serviceName, BigDecimal servicePrice, Duration duration, Client client, Date date) {
        this.id = id;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.duration = duration;
        this.client = client;
        this.date = date;
        System.out.println("Добавлен новый сервис");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    @Override
    public int compareTo(Service o) {
        return this.date.compareTo(o.date);
    }
    @Override
    public String toString() {
        return "Услуга: " + serviceName + " - " + servicePrice + " руб. (" + duration.toHours() + " ч.)" + " для " + client + " " + date;
    }
    public class ServicePrice implements Comparator<Service> {
        @Override
        public int compare(Service o1, Service o2) {
            return o1.servicePrice.compareTo(o2.servicePrice);
        }
    }

}
