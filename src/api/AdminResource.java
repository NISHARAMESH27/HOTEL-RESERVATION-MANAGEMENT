package api;

import model.*;
import service.*;
import java.util.Collection;

public class AdminResource {

    private static final AdminResource INSTANCE =
            new AdminResource();

    private final CustomerService customerService =
            CustomerService.getInstance();
    private final ReservationService reservationService =
            ReservationService.getInstance();

    private AdminResource() {}

    public static AdminResource getInstance() {
        return INSTANCE;
    }

    public void addRoom(IRoom room) {
        reservationService.addRoom(room);
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public Collection<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
}
