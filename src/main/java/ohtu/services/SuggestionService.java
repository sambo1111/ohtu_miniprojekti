/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.services;

import java.util.List;
import ohtu.data_access.BookDao;
import ohtu.data_access.SuggestionDao;
import ohtu.domain.Book;
import ohtu.domain.Suggestable;
import ohtu.domain.Suggestion;

/**
 *
 * @author paavo
 * päivityksiä: mkotola
 */
public class SuggestionService {
    private BookDao bookDao;
    private SuggestionDao suggestionDao;
    
    public SuggestionService(BookDao bookDao, SuggestionDao suggestionDao) {
        this.bookDao = bookDao;
        this.suggestionDao = suggestionDao;
    }
    
    public Book addBook(String creator, String title, String description, String ISBN) {
        Book newBook = null;
        if (bookDao.containsTitleAndCreator(creator, title)) {
            newBook = bookDao.findByTitleAndCreator(creator, title);
            return newBook;
        }
        newBook = new Book(creator, title, description, ISBN);
        bookDao.add(newBook);
        return newBook;
    }
    
    public List<Suggestion> listAll() {
        return suggestionDao.listAll();
    }
    
    public List<Book> findBookByCreator(String creator) {
        return bookDao.findByCreator(creator);
    }
    public Suggestable findBookByISBN(String ISBN) {
        return bookDao.findByISBN(ISBN);
    }
    public List<Book> findBookByDescription(String description) {
        return bookDao.findByDescription(description);
    }
    public List<Book> findBookByTitle(String title) {
        return bookDao.findByTitle(title);
    }
    
    public boolean addSuggestionWithBook(String creator, String title, String description, String ISBN) {
        Book suggestionBook = addBook(creator, title, description, ISBN);
        if (suggestionBook != null) {
            //suggestionDao.add(suggestionBook);
            return true;
        }
        return false;
    }
}
