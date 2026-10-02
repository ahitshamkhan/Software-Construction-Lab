public class MailClient {
    private String name;
    private MailServer server;

    public MailClient(String name, MailServer server) {
        this.name = name;
        this.server = server;
    }

    public void sendMail(String to, String message) {
        MailItem item = new MailItem(name, to, message);
        server.post(item);
    }

    public MailItem getNextMailItem() {
        return server.getNextMailItem(name);
    }

    public void printNextMailItem() {
        MailItem item = getNextMailItem();
        if (item != null) {
            item.print();
        } else {
            System.out.println("No new mail for " + name);
        }
    }
}