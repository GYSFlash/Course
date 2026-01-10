package com.hotel.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;

public class Service implements Comparable<Service> {

    private static Long counter = 0L;
    private Long id;
    private TypeService typeService;
    private String serviceName;
    private BigDecimal servicePrice;
    private Duration duration;
    private Date date;
    private Client client;
    public Service() {}
    public Service(TypeService typeService,String serviceName, BigDecimal servicePrice, Duration duration, Client client, Date date) {
        this.id = ++counter;
        this.typeService = typeService;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.duration = duration;
        this.client = client;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(id, service.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
    public enum TypeService {
        ROOM,FOOD,OTHER;
    }
}
