import java.util.ArrayList;
import java.util.List;

public class MailClient {
    private String username;
    private MailServer server;
    private List<MailItem> inbox;

    public MailClient(String username, MailServer server) {
        this.username = username;
        this.server = server;
        this.inbox = new ArrayList<>();
        server.registerClient(this);
    }

    public String getUsername() {
        return username;
    }

    public void sendEmail(String to, String message) {
        MailItem item = new MailItem(username, to, message);
        server.post(item);
    }

    public void receive(MailItem item) {
        inbox.add(item);
    }

    public MailItem getNextMailItem() {
        if (!inbox.isEmpty()) {
            return inbox.remove(0);
        }
        return null;
    }

    public int howManyPending() {
        return inbox.size();
    }

    public void printNextMailItem() {
        MailItem item = getNextMailItem();
        if (item != null) {
            item.print();
        } else {
            System.out.println("No new mail for " + username);
        }
    }
}