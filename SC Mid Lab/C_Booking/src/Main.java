// Ahtisham Khan | Your Roll No | Your Variant
public class Main {
    public static void main(String[] args) {
        BookingDesk desk = new BookingDesk();

        String firstEquipmentId = "014"; // Example: use E42 if your roll number ends in 42
        String laptopId = "Dell-1";
        String routerId = "D-014";

        desk.addEquipment(new Equipment(firstEquipmentId, "Switch", "Electronics"));
        desk.addEquipment(new Equipment(laptopId, "Laptop Dell I5", "Computing"));
        desk.addEquipment(new Equipment(routerId, "Router 1841", "Networking"));
        desk.addEquipment(new Equipment("P01", "Raspberry Pi", "Computing"));

        desk.printInventory();

        System.out.println("T1 Book free laptop: "
                + desk.bookEquipment("Ahtisham", laptopId, 2));

        System.out.println("T2 Reject already-booked laptop: "
                + desk.bookEquipment("Sara", laptopId, 1));

        System.out.println("T3 Reject unknown equipment: "
                + desk.bookEquipment("Sara", "UNKNOWN", 1));

        System.out.println("T4 Reject zero hours: "
                + desk.bookEquipment("Sara", routerId, 0));

        System.out.println("T5 Book router after rejected attempt: "
                + desk.bookEquipment("Sara", routerId, 1));

        System.out.println("T6 Return booked laptop: "
                + desk.returnEquipment(laptopId));

        System.out.println("T7 Reject second return: "
                + desk.returnEquipment(laptopId));

        System.out.println("T8 Book returned laptop: "
                + desk.bookEquipment("Sara", laptopId, 1));
    }
}