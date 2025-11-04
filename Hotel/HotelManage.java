package Hotel;

import java.util.ArrayList;
import java.util.List;

public class HotelManage {
    private List<Room> rooms = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    private List<Service> services = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
    public void addClient(Client client) {
        clients.add(client);
    }
    public void addService(Service service) {
        services.add(service);
    }
    public List<Room> showAllRooms() {
        for (Room room : rooms) {
            System.out.println(room);

        }
        return rooms;
    }
    public List<Booking> showAllBookings() {
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
        return bookings;
    }
    public List<Client> showAllClients() {
        for (Client client : clients) {
            System.out.println(client);
        }
        return clients;
    }
    public List<Service> showAllServices() {
        for (Service service : services) {
            System.out.println(service);
        }
        return services;
    }
    public List<Room> showRoomsIsFree() {
        for (Room room : rooms) {
            if (room.getStatus()==Status.FREE) {
                System.out.println(room);
            }
        }
        return rooms;
    }

}
