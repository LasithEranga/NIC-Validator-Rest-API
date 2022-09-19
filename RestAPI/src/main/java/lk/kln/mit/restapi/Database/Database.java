/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.kln.mit.restapi.Database;
    
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Lasith
 */
public class Database {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/user_db";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        Connection connection = null;
        
        connection = DriverManager.getConnection(DB_URL,USER_NAME,PASSWORD);
        return connection;
        
        
    }
    
}