public class Main {
    public static void main(String[] args) {
        MailServer server = new MailServer();

        MailClient ahtisham = new MailClient("Ahtisham", server);
        MailClient sir = new MailClient("Engr Saad Mazhar", server);

        ahtisham.sendMail("Engr Saad Mazhar", "Hello Sir, Lab check kr layin");

        System.out.println("Sir checking Email");
        sir.printNextMailItem();
    }
}