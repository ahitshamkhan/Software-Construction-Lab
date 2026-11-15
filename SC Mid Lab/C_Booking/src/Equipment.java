public class Equipment {
    private String id;
    private String name;
    private String category;
    private boolean booked;

    public Equipment(String id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.booked = false;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public boolean isBooked() { return booked; }
    public void setBooked(boolean booked) { this.booked = booked; }

    public String toString() {
        return id + " - " + name + " (" + category + ")" + (booked ? " [BOOKED]" : " [FREE]");
    }
}
