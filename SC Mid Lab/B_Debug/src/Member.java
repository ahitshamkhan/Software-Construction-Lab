import java.util.ArrayList;

public class Member {
    private String name;
    private ArrayList<Book> borrowedBooks;

    public Member(String name) {
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() { return name; }

    public int getBorrowedCount() { return borrowedBooks.size(); }

    public ArrayList<Book> getBorrowedBooks() {
        return new ArrayList<>(borrowedBooks); //bug 4 fixed
    }

    public void addBook(Book b) { borrowedBooks.add(b); }

    public void removeBook(Book b) { borrowedBooks.remove(b); }
}
