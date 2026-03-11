package com.hotel.dto;

import com.hotel.model.Client;
import com.hotel.model.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;

public class ServiceResponseDTO {
    private Long id;
    private Service.TypeService typeService;
    private String serviceName;
    private BigDecimal servicePrice;
    private Duration duration;
    private Date date;
    private ClientResponseDTO client;

    public ClientResponseDTO getClient() {
        return client;
    }

    public void setClient(ClientResponseDTO client) {
        this.client = client;
    }

    public Service.TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(Service.TypeService typeService) {
        this.typeService = typeService;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
