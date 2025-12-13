package model;

import java.math.BigDecimal;
import java.util.Objects;

public class Room implements Comparable<Room> {
    private int roomNumber;
    private BigDecimal price;
    private int place;
    private RoomType type;
    private Status status;
    private Star stars;
    public Room() {}
    public Room(int roomNumber, BigDecimal price, int place, RoomType type,Star stars) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.place = place;
        this.type = type;
        this.status = Status.FREE;
        this.stars = stars;
        System.out.println("Новая комната создана");
    }
    public void repairRoom(){
        this.status=Status.REPAIR;
        System.out.println("Комната "+this.roomNumber+" передана на ремонт");
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Star getStars() {
        return stars;
    }

    public void setStars(Star stars) {
        this.stars = stars;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }
    @Override
    public int compareTo(Room o) {
        return this.price.compareTo(o.price);
    }
    @Override
    public String toString() {
        return "Номер " + roomNumber + " (" + type + ") - " + status + " - " + price + " руб./ночь";

    }
    public enum Status{
        FREE,REPAIR,OCCUPIED
    }
    public enum RoomType{
        STANDART,STANDARTPLUS,LUX,DELUXE,PRESIDENT
    }
    public enum Star{
        ONE,TWO,THREE,FOUR,FIVE
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }

}

