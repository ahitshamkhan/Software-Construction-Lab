import java.util.HashMap;

public class MapTester {
    private HashMap<String, String> phoneBook;

    public MapTester() {
        phoneBook = new HashMap<>();
    }

    public void enterNumber(String name, String number) {
        phoneBook.put(name, number);
    }

    public String lookupNumber(String name) {
        return phoneBook.get(name);
    }

    // Changed from 'main' to 'runTest'
    public void runTest() {
        enterNumber("Alice", "555-0100");
        System.out.println("Alice: " + lookupNumber("Alice"));

        enterNumber("Alice", "555-9999");
        System.out.println("Alice (Updated): " + lookupNumber("Alice"));

        enterNumber("Bob", "555-9999");
        System.out.println("Bob: " + lookupNumber("Bob"));
    }
}