package ui;

import api.AdminResource;
import model.*;

import java.util.Scanner;

public class AdminMenu {

    private static final AdminResource admin =
            AdminResource.getInstance();

    static void seed() {
        admin.addRoom(new Room("101", 100.0, RoomType.SINGLE));
        admin.addRoom(new FreeRoom("102", RoomType.DOUBLE));
    }

    static void show() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. View Customers");
            System.out.println("2. View Rooms");
            System.out.println("3. View Reservations");
            System.out.println("4. Add Room");
            System.out.println("5. Back");

            String choice = sc.nextLine();
            try {
                switch (choice) {
                    case "1" -> admin.getAllCustomers().forEach(System.out::println);
                    case "2" -> admin.getAllRooms().forEach(System.out::println);
                    case "3" -> admin.getAllReservations().forEach(System.out::println);
                    case "4" -> addRoom(sc);
                    case "5" -> { return; }
                    default -> System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void addRoom(Scanner sc) {
        System.out.print("Room Number: ");
        String num = sc.nextLine();

        System.out.print("Price (0 for free): ");
        double price = Double.parseDouble(sc.nextLine());

        System.out.print("Type (SINGLE/DOUBLE): ");
        RoomType type = RoomType.valueOf(sc.nextLine().toUpperCase());

        if (price == 0) {
            admin.addRoom(new FreeRoom(num, type));
        } else {
            admin.addRoom(new Room(num, price, type));
        }

        System.out.println("Room added successfully.");
    }
}
