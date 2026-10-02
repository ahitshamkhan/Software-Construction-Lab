import java.util.ArrayList;
import java.util.List;

public class MailServer {
    private List<MailItem> items;

    public MailServer() {
        items = new ArrayList<>();
    }

    public void post(MailItem item) {
        items.add(item);
    }

    public MailItem getNextMailItem(String who) {
        for (MailItem item : items) {
            if (item.getTo().equals(who)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }
}