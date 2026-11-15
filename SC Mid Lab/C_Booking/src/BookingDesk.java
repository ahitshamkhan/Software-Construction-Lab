import java.util.ArrayList;
import java.util.Iterator;

public class BookingDesk {
    // ---- Set these two constants from the Variant Table on your paper ----
    private static final int MAX_ACTIVE = 2;   // max active bookings per student
    private static final int MAX_HOURS  = 5;   // max hours for one booking

    private ArrayList<Equipment> inventory;
    private ArrayList<BookingRecord> bookings;   // BookingRecord = YOU write it (Task C1)

    public BookingDesk() {
        inventory = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public void addEquipment(Equipment e) {
        inventory.add(e);
    }

    // Provided helper: returns the Equipment with this id, or null
    private Equipment findEquipment(String id) {
        for (Equipment e : inventory) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public void printInventory() {
        for (Equipment e : inventory) {
            System.out.println(e);
        }
    }

    public void printBookings() {
        for (BookingRecord r : bookings) {
            System.out.println(r);
        }
    }

    //TODO C2: public boolean bookEquipment(String studentName, String equipmentId, int hours)
    public boolean bookEquipment(String studentName, String equipmentId, int hours) {
        Equipment item = findEquipment(equipmentId);

        if (item == null || item.isBooked()) {
            return false;
        }

        if (hours < 1 || hours > MAX_HOURS) {
            return false;
        }

        int activeBookings = 0;
        for (BookingRecord record : bookings) {
            if (record.getStudentName().equals(studentName)) {
                activeBookings++;
            }
        }

        if (activeBookings >= MAX_ACTIVE) {
            return false;
        }

        bookings.add(new BookingRecord(studentName, item, hours));
        item.setBooked(true);
        return true;
    }
    // TODO C3: public boolean returnEquipment(String equipmentId)   (use an Iterator)
    public boolean returnEquipment(String equipmentId) {
        Iterator<BookingRecord> iterator = bookings.iterator();

        while (iterator.hasNext()) {
            BookingRecord record = iterator.next();
            Equipment item = record.getEquipment();

            if (item.getId().equals(equipmentId)) {
                iterator.remove();
                item.setBooked(false);
                return true;
            }
        }

        return false;
    }

    // TODO C4: the report method for YOUR variant
    // for A
    public ArrayList<Equipment> findAvailableByCategory(String category) {
        ArrayList<Equipment> available = new ArrayList<>();

        for (Equipment item : inventory) {
            if (item.getCategory().equalsIgnoreCase(category) && !item.isBooked()) {
                available.add(item);
            }
        }

        return available;
    }

    //for B:

    public int totalHours(String studentName) {
        int total = 0;

        for (BookingRecord record : bookings) {
            if (record.getStudentName().equals(studentName)) {
                total += record.getHours();
            }
        }

        return total;
    }

    //for C:
    public Equipment longestBooking() {
        BookingRecord longest = null;

        for (BookingRecord record : bookings) {
            if (longest == null || record.getHours() > longest.getHours()) {
                longest = record;
            }
        }

        if (longest == null) {
            return null;
        }

        return longest.getEquipment();
    }

    //for D:
    public ArrayList<String> studentsWithBookings() {
        ArrayList<String> students = new ArrayList<>();

        for (BookingRecord record : bookings) {
            String name = record.getStudentName();

            if (!students.contains(name)) {
                students.add(name);
            }
        }

        return students;
    }



}
