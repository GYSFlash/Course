package Hotel;

import java.math.BigDecimal;

public class Room {
    private int roomNumber;
    private BigDecimal price;
    private String type;
    private Status status;
    public Room(int roomNumber, BigDecimal price, String type) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return "Номер " + roomNumber + " (" + type + ") - " + status + " - " + price + " руб./ночь";

    }
}
enum Status{
    FREE,REPAIR,OCCUPIED
}
