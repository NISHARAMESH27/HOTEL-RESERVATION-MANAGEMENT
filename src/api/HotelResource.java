package api;

import model.*;
import service.*;
import java.util.*;

public class HotelResource {

    private static final HotelResource INSTANCE =
            new HotelResource();

    private final CustomerService customerService =
            CustomerService.getInstance();
    private final ReservationService reservationService =
            ReservationService.getInstance();

    private HotelResource() {}

    public static HotelResource getInstance() {
        return INSTANCE;
    }

    public void createCustomer(String email, String first, String last) {
        customerService.addCustomer(email, first, last);
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public Collection<IRoom> findRooms(Date in, Date out) {
        return reservationService.findAvailableRooms(in, out);
    }

    public Collection<IRoom> findRecommended(Date in, Date out) {
        return reservationService.findRecommendedRooms(in, out);
    }

    public Reservation bookRoom(String email, IRoom room,
                                Date in, Date out) {
        if (getCustomer(email) == null) {
            throw new IllegalArgumentException("Customer does not exist");
        }
        if (room == null) {
            throw new IllegalArgumentException("Invalid room number");
        }
        return reservationService.reserveRoom(
                getCustomer(email), room, in, out);
    }

    public Collection<Reservation> getReservations(String email) {
        return reservationService.getCustomerReservations(
                getCustomer(email));
    }
}
