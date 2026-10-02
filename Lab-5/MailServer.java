import java.util.ArrayList;
import java.util.List;

public class MailServer {
    private List<MailClient> clients;

    public MailServer() {
        clients = new ArrayList<>();
    }

    public void registerClient(MailClient client) {
        clients.add(client);
    }

    public void post(MailItem item) {
        for (MailClient client : clients) {
            if (client.getUsername().equals(item.getTo())) {
                client.receive(item);
                return;
            }
        }
        System.out.println("Recipient not found: " + item.getTo());
    }
}