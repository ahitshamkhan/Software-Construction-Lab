public class Main {
    public static void main(String[] args) {
        MailServer server = new MailServer();
        MailClient alice = new MailClient("Ahtisham", server);
        MailClient bob = new MailClient("Saad", server);

        alice.sendEmail("Saad", "Sir Date Extend kr dyin");

        System.out.println("** Sir checking mail **");
        bob.printNextMailItem();

        System.out.println("** Sir checking mail again **");
        bob.printNextMailItem();
    }
}