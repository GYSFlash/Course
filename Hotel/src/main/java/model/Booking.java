package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;


import static model.Room.Status.*;

public class Booking implements Comparable<Booking> {

    private static Long counter = 0L;
    private Long id;
    private Date checkInDate;
    private Date checkOutDate;
    private BigDecimal totalPrice;
    private Room room;
    private Client client;
    public Booking(){}
    public Booking(Date checkInDate, Room room, Client client, Date checkOutDate) {
        this.id = ++counter;
        this.checkInDate = checkInDate;
        this.room = room;
        this.client = client;
        this.checkOutDate = checkOutDate;
    }
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void endBooking(){
        this.room.setStatus(FREE);
    }
    @Override
    public int compareTo(Booking o) {
        return this.client.compareTo(o.client);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Бронирование №" + id + ": " + client.getSurname() + " " + client.getName() +
                " - Комната " + room.getRoomNumber() + " (" + checkInDate + " до " + checkOutDate +
                ") - Итого: " + totalPrice + " руб.";
    }

}
