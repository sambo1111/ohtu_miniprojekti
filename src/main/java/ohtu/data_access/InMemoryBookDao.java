package ohtu.data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ohtu.domain.Book;

public class InMemoryBookDao implements InterfaceBookDao {
    private List<Book> books;
    
    public InMemoryBookDao() {
        books = new ArrayList<>();
        books.add(new Book("Clean Code: A Handbook of Agile Software Craftsmanship", "Robert Martin", "Noted software expert Robert C. Martin presents a revolutionary paradigm with Clean Code: A Handbook of Agile Software Craftsmanship", "978-951-98548-9-2"));
    }

    @Override
    public List<Book> listAll() {
        return books;
    }

    @Override
    public HashMap<String, Book> findByAll(String args) {
        return null;
    }
    
    
    @Override
    public List<Book> findByCreator(String creator) {
        ArrayList<Book> booksReturn = new ArrayList();
        
        for (Book book : books) {
            if (book.getCreator().toLowerCase().contains(creator.toLowerCase())) {
                booksReturn.add(book);
            }
        }
        return booksReturn;
    }

    @Override
    public List<Book> findByTitle(String title) {
        List<Book> list = new ArrayList<>();
        for (Book book: books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                list.add(book);
            }
        }
        return list;
    }
    
    //taitaa olla turha
    @Override
    public boolean containsTitleAndCreator(String title, String creator) {
        for (Book book: books) {
            if (book.getTitle().toLowerCase().equals(title.toLowerCase()) && book.getCreator().toLowerCase().equals(creator.toLowerCase()) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Book> findByDescription(String description) {
        ArrayList<Book> booksReturn = new ArrayList();
        for (Book book : books) {
            if (book.getDescription().toLowerCase().contains(description.toLowerCase())) {
                booksReturn.add(book);
            }
        }
        return booksReturn;
    }

    @Override
    public Book findByISBN(String ISBN) {
        for (Book book: books) {
            if (book.getISBN().equals(ISBN)) {
                return book;
            }
        }
        return null;
    }
    
    @Override
    public Book findByTitleAndCreator(String title, String creator) {
        for (Book book : books) {
            if (book.getTitle().toLowerCase().equals(title.toLowerCase()) && book.getCreator().toLowerCase().equals(creator.toLowerCase())) {
                return book;
            }
        }
        return null;
    } 
    
    @Override
    public void add(Book book) {
        books.add(book);
    }

    @Override
    public void remove(Book book) {
        books.remove(book);
    }

    @Override
    public void update(Book book) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
