package ohtu;

import ohtu.io.ConsoleIO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import ohtu.data_access.BookDao;
import ohtu.data_access.InMemoryBookDao;
import ohtu.domain.Book;
import ohtu.domain.Suggestable;
import ohtu.io.IO;
import ohtu.services.SuggestionService;

public class App {

    private IO io;
    private SuggestionService sugg;

    public App(IO io, SuggestionService sugg) {
        this.io = io;
        this.sugg = sugg;
    }

    public void run() {

        System.out.println("Welcome!");
        while (true) {
            String command = io.readLine("\nCommand (list or add, empty command exits program)");

            if (command.isEmpty()) {
                break;
            }
            if (command.equals("list")) {
                list();
            } else if (command.equals("add")) {
                add();
            } else if (command.equals("find")) {
                find();
            } else {
                io.print("Unknown command!");
            }
        }

    }

    public void add() {
        String command = io.readLine("What would you like to add? (types: book)");
        if (command.equals("book")) {
            String creator = io.readLine("Author: ");
            String title = io.readLine("Title: ");
            String description = io.readLine("Description: ");
            String ISBN = io.readLine("ISBN: ");
            if (sugg.addBook(creator, title, description, ISBN)) {
                io.print("New book added!");
            } else {
                io.print("Adding a new book failed!");
            }
        } else {
            io.print("Unknown command!");
        }
    }

    public void list() {
        for (Suggestable suggestable : sugg.listAll()) {

            if (suggestable.getClass().getName().equals("ohtu.domain.Book")) {
                Book book = (Book) suggestable;
                io.print("Author: " + book.getCreator() + "\nTitle: " + book.getTitle() + "\nDescription: " + book.getDescription() + "\nISBN: " + book.getISBN() + "\n");
            }

        }
    }

    public void find() {
        //HAKU KIRJALLE
        while (true) {
            String command = io.readLine("Find by (title, description, creator, isbn, q = back): ");
            if (command.equals("title")) {
                break;
            } else if (command.equals("description")) {
                break;
            } else if (command.equals("creator")) {
                break;
            } else if (command.equals("isbn")) {
                break;
            } else if (command.equals("q")) {
                break;
            } else {
                System.out.println("Unknown command!");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        //SQL TESTI

        BookDao bookDao = new InMemoryBookDao();
        IO io = new ConsoleIO();
        SuggestionService sugg = new SuggestionService(bookDao);
        new App(io, sugg).run();

//        Class.forName("org.sqlite.JDBC");
//        Connection c = DriverManager.getConnection("jdbc:sqlite:sql/database.db");
//        Statement s = c.createStatement();
//        
//        ResultSet rs = s.executeQuery("SELECT * FROM Book");
//
//        System.out.println("\nTietokannassa olevat kirjat:\n");
//        
//        while (rs.next()) {
//            System.out.println(
//                    "id: " + rs.getString("id") + "\n" +
//                    "title: " + rs.getString("title") + "\n" + 
//                    "author: " + rs.getString("author") + "\n" + 
//                    "ISBN: " + rs.getString("isbn") + "\n");
//        }
//        UserDao dao = new InMemoryUserDao();
//        IO io = new ConsoleIO();
//        AuthenticationService auth = new AuthenticationService(dao);
//        new App(io, auth).run();
    }

}
