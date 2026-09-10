public enum SeatType {
    SILVER("Silver", 150.0),
    GOLD("Gold", 250.0),
    PLATINUM("Platinum", 400.0);

    private final String displayName;
    private final double price;

    SeatType(String displayName, double price) {
        this.displayName = displayName;
        this.price = price;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getPrice() {
        return price;
    }
}
