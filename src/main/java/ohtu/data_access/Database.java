/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.sql.*;

/**
 *
 * @author hcpaavo
 */
public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseAddress);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
        return conn;
        
    }
}
