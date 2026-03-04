package com.hotel.dto;

import com.hotel.model.Room;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;

public class RoomDTO {
    @NotNull(message = "Номер комнаты не может быть пустым")
    private int roomNumber;
    @NotNull(message = "Цена не может быть пустой")
    @Min(value = 200, message = "Цена не может быть меньше 200")
    private BigDecimal price;
    @NotNull(message = "Количество мест не может быть пустым")
    private int place;
    @NotNull(message = "Тип комнаты не может быть пустым")
    private Room.RoomType type;
    @NotNull(message = "Статус комнаты не может быть пустым")
    private Room.Status status;
    @NotNull(message = "Количество звезд не может быть пустым")
    private Room.Star stars;

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Room.Star getStars() {
        return stars;
    }

    public void setStars(Room.Star stars) {
        this.stars = stars;
    }

    public Room.Status getStatus() {
        return status;
    }

    public void setStatus(Room.Status status) {
        this.status = status;
    }

    public Room.RoomType getType() {
        return type;
    }

    public void setType(Room.RoomType type) {
        this.type = type;
    }
}
