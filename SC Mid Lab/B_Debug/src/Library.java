import java.util.ArrayList;
import java.util.Iterator;

public class Library {
    private static final int MAX_LOANS = 3;
    private ArrayList<Book> books;
    private ArrayList<Member> members;

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>(); //Bug one fixed
    }

    public void addBook(Book b) { books.add(b); }

    public void addMember(Member m) { members.add(m); }

    public Book findBook(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) { //bug 2 fixed
                return b;
            }
        }
        return null;
    }

    public boolean borrowBook(Member m, String isbn) {
        Book b = findBook(isbn);
        if (b == null || !b.isAvailable()) {
            return false;
        }
        if (m.getBorrowedCount() >= MAX_LOANS) { //bug 3 fixed by adding =
            return false;
        }
        b.borrow();
        m.addBook(b);
        return true;
    }

    public void removeUnpopularBooks() {
        Iterator<Book> iterator = books.iterator(); // FIX-5

        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getTimesBorrowed() == 0) {
                iterator.remove();
            }
        }
    }

    public int getBookCount() { return books.size(); }
}
