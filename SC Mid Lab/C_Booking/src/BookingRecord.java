
public class BookingRecord {
    private String studentName;
    private Equipment equipment;
    private int hours;

    public BookingRecord(String studentName, Equipment equipment, int hours) {
        this.studentName = studentName;
        this.equipment = equipment;
        this.hours = hours;
    }

    public String getStudentName() {
        return studentName;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public String toString() {
        return studentName + " booked " + equipment.getName()
                + " (" + hours + " h)";
    }
}