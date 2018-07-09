package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author migue
 * @author roberto
 * This is a singleton pattern so  that we don't create multiples instances of the same class
 * we only need one instance shared between all
 * how to get the connection:
 * import Database.DatabaseConnection;
 * Connection conn = DatabaseConnection.getInstance().getConnection();
 */

public  class DatabaseConnection {

    private static  DatabaseConnection instance = new DatabaseConnection();
    private static  Connection connection;
    private static final String user="root";
    private static final String password="car1118";
    private static final String bd = "vacapp";
    private static final String url="jdbc:mysql://localhost:3306/"+bd+"?useSSL=false&serverTimezone=UTC";


    private DatabaseConnection() { }


    public static DatabaseConnection getInstance(){
        return instance;
    }


    public Connection getConnection() {
        return connection;
    }

    public boolean openConnection(){
        connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("SUCCESS Connection established to mysql db");
            return true;

        } catch (SQLException ex) {
            System.out.println("ERROR Couldn't establish mysql connection error: " + ex);
            return false;
        }
    }


    public void closeConnection() {
       try{
           if (connection != null) {
               connection.close();
               System.out.println("SUCCESS Connection closed");
           }
           else{
               System.out.println("ERROR Connection is null");
           }

       }catch (SQLException e){
           System.out.println("ERROR Couldn't close connection error: "+ e);
       }

    }





}
