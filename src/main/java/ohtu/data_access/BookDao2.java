/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ohtu.domain.Book;

/**
 *
 * @author hcpaavo
 */
public class BookDao2 implements BookDao {
    private Database database;
    
    public BookDao2(Database database) {
        this.database = database;
    }

    @Override
    public List<Book> listAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Book> findByCreator(String creator) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Book> findByTitle(String title) throws SQLException {
        List<Book> list = new ArrayList<>();
        
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Book WHERE title LIKE %?%");
        stmt.setObject(1, title);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return null;
        }
        
        while (rs.next()) {
            int id = rs.getInt("id");
            String author = rs.getString("creator");
            String title1 = rs.getString("title");
            String description = rs.getString("description");
            String ISBN = rs.getString("ISBN");
            
            list.add(new Book(title1, author, description, ISBN));
        }
        
        rs.close();
        stmt.close();
        connection.close();
        
        return list;
    }

    @Override
    public List<Book> findByDescription(String description) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Book findByISBN(String ISBN) throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Book WHERE isbn = ?");
        stmt.setObject(1, ISBN);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return null;
        }
        
        String author = rs.getString("creator");
        String title = rs.getString("title");
        String description = rs.getString("description");
        
        rs.close();
        stmt.close();
        connection.close();
        
        return new Book(title, author, description, ISBN);
    }

    @Override
    public boolean containsTitleAndCreator(String title, String creator) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Book findByTitleAndCreator(String title, String creator) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(Book book) {
        try {
          Connection connection = database.getConnection();
          PreparedStatement stmt = connection.prepareStatement("INSERT INTO Book (isbn, title, creator, description) VALUES (?, ?, ?, ?)");
          
          stmt.setString(1, book.getISBN());
          stmt.setString(2, book.getTitle());
          stmt.setString(3, book.getCreator());
          stmt.setString(4, book.getDescription());
          
          stmt.executeUpdate();
          
          stmt.close();
          connection.close();
      } catch (SQLException e) {
          e.printStackTrace();
      }
    }
    
    @Override
    public void remove(Book book) {
        try {
          Connection connection = database.getConnection();
          PreparedStatement stmt = connection.prepareStatement("DELETE FROM Book WHERE isbn = ?");
          stmt.setString(1, book.getISBN());
          stmt.executeUpdate();
          
          stmt.close();
          connection.close();
      } catch (SQLException e) {
          e.printStackTrace();
      }
    }
    
}
