package com.hotel.dto;

import com.hotel.model.Service;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;

public class ServiceRequestDTO {
    @NotNull(message = "Тип услуги не может быть пустым")
    private Service.TypeService typeService;
    @NotNull(message = "Название услуги не может быть пустым")
    private String serviceName;
    @NotNull(message = "Цена услуги не может быть пустой")
    @Min(value = 1, message = "Цена услуги не может быть меньше 1")
    private BigDecimal servicePrice;
    @NotNull(message = "Длительность услуги не может быть пустой")
    @Min(value = 1, message = "Длительность услуги не может быть меньше 1")
    private Duration duration;
    @NotNull(message = "Дата не может быть пустой")
    private Date date;
    @NotNull(message = "Клиент не может быть пустым")
    private ClientResponseDTO client;

    public ClientResponseDTO getClient() {
        return client;
    }

    public void setClient(ClientResponseDTO client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Service.TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(Service.TypeService typeService) {
        this.typeService = typeService;
    }
}
