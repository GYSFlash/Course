package Hotel.Model;

import java.math.BigDecimal;

public class Room {
    private int roomNumber;
    private BigDecimal price;
    private RoomType type;
    private Status status;
    public Room(int roomNumber, BigDecimal price, RoomType type) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.type = type;
        this.status = Status.FREE;
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
}

