public class Main {
    public static void main(String[] args) {
        // ---- Part 1: borrowing ----
        Library lib = new Library();
        lib.addBook(new Book("Clean Code", "B101"));
        lib.addBook(new Book("Refactoring", "B102"));
        lib.addBook(new Book("Head First Java", "B103"));
        lib.addBook(new Book("Effective Java", "B104"));

        Member ali = new Member("Ali");
        lib.addMember(ali);
        System.out.println("Added member: " + ali.getName());

        String code = new String("B101");          // simulates typed input
        System.out.println("Borrow B101: " + lib.borrowBook(ali, code));
        System.out.println("Borrow B102: " + lib.borrowBook(ali, "B102"));
        System.out.println("Borrow B103: " + lib.borrowBook(ali, "B103"));
        System.out.println("Borrow B104 (limit reached): " + lib.borrowBook(ali, "B104"));

        // ---- Part 2: outside code tries to tamper ----
        ali.getBorrowedBooks().clear();
        System.out.println("Ali loans: " + ali.getBorrowedCount());

        // ---- Part 3: clean-up ----
        Library lib2 = new Library();
        lib2.addBook(new Book("Book P1", "P1"));
        lib2.addBook(new Book("Book P2", "P2"));
        lib2.addBook(new Book("Book P3", "P3"));
        lib2.addBook(new Book("Book P4", "P4"));
        Member sara = new Member("Sara");
        lib2.addMember(sara);
        lib2.borrowBook(sara, "P1");
        lib2.borrowBook(sara, "P4");
        lib2.removeUnpopularBooks();
        System.out.println("Books left after clean-up: " + lib2.getBookCount());
    }
}
