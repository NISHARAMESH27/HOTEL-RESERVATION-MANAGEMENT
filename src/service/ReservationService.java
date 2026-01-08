package service;

import model.*;
import java.util.*;

public class ReservationService {

    private static final ReservationService INSTANCE =
            new ReservationService();

    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Set<Reservation> reservations = new HashSet<>();

    private ReservationService() {}

    public static ReservationService getInstance() {
        return INSTANCE;
    }

    public void addRoom(IRoom room) {
        if (rooms.containsKey(room.getRoomNumber())) {
            throw new IllegalArgumentException("Duplicate room number");
        }
        rooms.put(room.getRoomNumber(), room);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    private boolean isRoomAvailable(IRoom room, Date in, Date out) {
        for (Reservation r : reservations) {
            if (r.getRoom().equals(room)) {
                if (!(out.before(r.getCheckInDate())
                        || in.after(r.getCheckOutDate()))) {
                    return false;
                }
            }
        }
        return true;
    }

    public Collection<IRoom> findAvailableRooms(Date in, Date out) {
        List<IRoom> available = new ArrayList<>();
        for (IRoom room : rooms.values()) {
            if (isRoomAvailable(room, in, out)) {
                available.add(room);
            }
        }
        return available;
    }

    public Collection<IRoom> findRecommendedRooms(Date in, Date out) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(in);
        cal.add(Calendar.DATE, 7);
        Date newIn = cal.getTime();

        cal.setTime(out);
        cal.add(Calendar.DATE, 7);
        Date newOut = cal.getTime();

        return findAvailableRooms(newIn, newOut);
    }

    public Reservation reserveRoom(Customer customer, IRoom room,
                                   Date in, Date out) {
        Reservation reservation =
                new Reservation(customer, room, in, out);
        reservations.add(reservation);
        return reservation;
    }

    public Collection<Reservation> getCustomerReservations(Customer c) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getCustomer().equals(c)) {
                result.add(r);
            }
        }
        return result;
    }

    public Collection<Reservation> getAllReservations() {
        return reservations;
    }
}
