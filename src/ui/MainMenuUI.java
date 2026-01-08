package ui;

import api.HotelResource;
import model.IRoom;

import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenuUI {

    private static final Scanner sc = new Scanner(System.in);
    private static final HotelResource hotel =
            HotelResource.getInstance();

    // Strict date format (prevents auto-correction)
    private static final SimpleDateFormat df =
            new SimpleDateFormat("MM/dd/yyyy");

    static {
        df.setLenient(false);
    }

    static void start() {
        while (true) {
            System.out.println("\nMain Menu");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");

            String choice = sc.nextLine();
            try {
                switch (choice) {
                    case "1":
                        findRoom();
                        break;
                    case "2":
                        viewReservations();
                        break;
                    case "3":
                        createAccount();
                        break;
                    case "4":
                        AdminMenu.show();
                        break;
                    case "5":
                        System.exit(0);
                    default:
                        System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // ===================== STRICT DATE VALIDATION =====================
    private static Date parseDate(String input) throws Exception {
        Date date;
        try {
            date = df.parse(input);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid date format. Please use MM/dd/yyyy"
            );
        }

        if (date.before(new Date())) {
            throw new IllegalArgumentException(
                    "Date cannot be in the past"
            );
        }
        return date;
    }
    // =================================================================

    private static void createAccount() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("First Name: ");
        String fn = sc.nextLine();
        System.out.print("Last Name: ");
        String ln = sc.nextLine();

        hotel.createCustomer(email, fn, ln);
        System.out.println("Account created successfully.");
    }

    // ===================== UPDATED +7 DAYS LOGIC =====================
    private static void findRoom() throws Exception {
        System.out.print("Check-in (MM/dd/yyyy): ");
        Date checkIn = parseDate(sc.nextLine());

        System.out.print("Check-out (MM/dd/yyyy): ");
        Date checkOut = parseDate(sc.nextLine());

        if (!checkOut.after(checkIn)) {
            throw new IllegalArgumentException(
                    "Check-out must be after check-in"
            );
        }

        Collection<IRoom> availableRooms =
                hotel.findRooms(checkIn, checkOut);

        // 🔁 +7 DAYS REQUIREMENT
        if (availableRooms.isEmpty()) {
            System.out.println(
                    "No rooms available for selected dates."
            );
            System.out.println(
                    "Searching for available rooms 7 days later..."
            );

            Calendar cal = Calendar.getInstance();

            cal.setTime(checkIn);
            cal.add(Calendar.DATE, 7);
            Date newCheckIn = cal.getTime();

            cal.setTime(checkOut);
            cal.add(Calendar.DATE, 7);
            Date newCheckOut = cal.getTime();

            // DISPLAY NEW DATES
            System.out.println("New recommended dates:");
            System.out.println("Check-in:  " + df.format(newCheckIn));
            System.out.println("Check-out: " + df.format(newCheckOut));

            Collection<IRoom> recommendedRooms =
                    hotel.findRooms(newCheckIn, newCheckOut);

            if (recommendedRooms.isEmpty()) {
                System.out.println(
                        "No recommended rooms available."
                );
                return;
            }

            System.out.println("Recommended rooms (+7 days):");
            recommendedRooms.forEach(System.out::println);

            // Use shifted dates for booking
            checkIn = newCheckIn;
            checkOut = newCheckOut;
            availableRooms = recommendedRooms;
        }

        availableRooms.forEach(System.out::println);

        System.out.print("Room number: ");
        String roomNum = sc.nextLine();

        IRoom selectedRoom =
                availableRooms.stream()
                        .filter(r -> r.getRoomNumber()
                                .equals(roomNum))
                        .findFirst()
                        .orElse(null);

        if (selectedRoom == null) {
            throw new IllegalArgumentException(
                    "Invalid room number"
            );
        }

        System.out.print("Email: ");
        String email = sc.nextLine();

        hotel.bookRoom(email, selectedRoom, checkIn, checkOut);

        System.out.println("Reservation successful.");
    }
    // ===============================================================

    private static void viewReservations() {
        System.out.print("Email: ");
        String email = sc.nextLine();

        hotel.getReservations(email)
                .forEach(System.out::println);
    }
}
