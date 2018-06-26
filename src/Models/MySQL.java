/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author migue
 */
public class MySQL {
    private static Connection connection;
    private static final String driver="com.mysql.cj.jdbc.Driver";
    private final String user="vacapp_admin";
    private final String password="admin123";
    private final String url="jdbc:mysql://localhost:3306/vacapp?useSSL=false&serverTimezone=UTC";
    
    public MySQL(){
        connection=null;
        try{
            Class.forName(driver);
            connection=DriverManager.getConnection(url, user, password);
            if(connection!=null)
                System.out.println("Connected to the bd");
            
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error: "+ex);
        }
    }
    
    public Connection getConnection(){
        return connection;
    }
    
    public void disconnect(){
        connection=null;
        if(connection==null)
            System.out.println("Disconnect");
    }
    
    
}

