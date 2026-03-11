package com.hotel.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

public class BookingRequestDTO {
    @NotNull(message = "Дата должна быть в будущем")
    @Future(message = "Дата должна быть в будущем")
    private Date checkInDate;
    @NotNull(message = "Дата должна быть в будущем")
    @Future(message = "Дата должна быть в будущем")
    private Date checkOutDate;
    @NotNull(message = "Цена не может быть пустой")
    @Min(value = 1, message = "Цена не может быть меньше 1")
    private BigDecimal totalPrice;
    @NotNull(message = "Комната не может быть пустой")
    private RoomDTO room;
    @NotNull(message = "Клиент не может быть пустым")
    private ClientResponseDTO client;

    public BigDecimal calculateTotalPrice(){
        int days = (int) ((checkOutDate.getTime() - checkInDate.getTime()) / (1000*60*60*24));
        BigDecimal total = room.getPrice().multiply(new BigDecimal(days));
        return total;
    }
    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public ClientResponseDTO getClient() {
        return client;
    }

    public void setClient(ClientResponseDTO client) {
        this.client = client;
    }
}
