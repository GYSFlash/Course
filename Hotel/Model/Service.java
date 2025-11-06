package Hotel.Model;

import java.math.BigDecimal;
import java.time.Duration;

public class Service {
    private Long id;
    private String serviceName;
    private BigDecimal servicePrice;
    private Duration duration;
    private Client client;
    public Service(Long id,String serviceName, BigDecimal servicePrice, Duration duration, Client client) {
        this.id = id;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.duration = duration;
        this.client = client;
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
    @Override
    public String toString() {
        return "Услуга: " + serviceName + " - " + servicePrice + " руб. (" + duration.toHours() + " ч.)" + " для " + client;
    }

}
