public class Book {
    private String title;
    private String isbn;
    private boolean available;
    private int timesBorrowed;

    public Book(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
        this.available = true;
        this.timesBorrowed = 0;
    }

    public String getTitle() { return title; }
    public String getIsbn() { return isbn; }
    public boolean isAvailable() { return available; }
    public int getTimesBorrowed() { return timesBorrowed; }

    public void borrow() {
        available = false;
        timesBorrowed++;
    }

    public void giveBack() {
        available = true;
    }
}
