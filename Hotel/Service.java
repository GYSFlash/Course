package Hotel;

import java.math.BigDecimal;
import java.time.Duration;

public class Service {
    private String serviceName;
    private BigDecimal servicePrice;
    private Duration duration;
    private Client client;
    public Service(String serviceName, BigDecimal servicePrice, Duration duration, Client client) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.duration = duration;
        this.client = client;
        System.out.println("Добавлен новый сервис");
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
