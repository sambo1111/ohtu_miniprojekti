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
public class BookDao2 implements Dao<Book, Integer> {
    private Database database;
    
    public BookDao2(Database database) {
        this.database = database;
    }

    @Override
    public Book findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM book WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return null;
        }

        int id = rs.getInt("id");
        String title = rs.getString("title");
        String creator = rs.getString("creator");
        String description = rs.getString("description");
        String ISBN = rs.getString("ISBN");

        rs.close();
        stmt.close();
        connection.close();
        
        return new Book(creator, title, description, ISBN);

    }

    @Override
    public List<Book> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM book");

        ResultSet rs = stmt.executeQuery();
        List<Book> books = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String title = rs.getString("title");
            String creator = rs.getString("creator");
            String description = rs.getString("description");
            String ISBN = rs.getString("ISBN");

            books.add(new Book(creator, title, description, ISBN));
        }

        rs.close();
        stmt.close();
        connection.close();
        
        return books;
    }

    @Override
    public void delete(Integer key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
