package model;

public class Room implements IRoom {

    protected final String roomNumber;
    protected final Double price;
    protected final RoomType roomType;

    public Room(String roomNumber, Double price, RoomType roomType) {
        if (roomNumber == null || roomNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Room number cannot be empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Room price cannot be negative");
        }
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return price == 0.0;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber +
                ", Type: " + roomType +
                ", Price: " + (isFree() ? "Free" : "$" + price);
    }
}
