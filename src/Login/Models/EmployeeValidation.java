package Login.Models;

import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author migue
 */
public class EmployeeValidation {

    private static ResultSet rs;
    private static Connection conn = DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    public static boolean validateStatus(String user) throws SQLException {//this method validate the status, if its 0 then the user is desactivate
        rs = null;
        String sql = "select status_user from usuario where username='" + user + "';";
        preparedStatement = conn.prepareStatement(sql);
        rs = preparedStatement.executeQuery();
        boolean isCorrect;
        while (rs.next()) {
            int status;
            if (rs.getInt("status_user") == 1) {
                return true;//the user is activated
            } else {
                return false;//the user is desactivated
            }
        }
        return false;//user desactivated
    }

    public static int validateType(String user) throws SQLException {
        rs = null;
        String sql = "select type_user from usuario where username='" + user + "';";
        preparedStatement = conn.prepareStatement(sql);
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            int type = rs.getInt("type_user");
            return type;//return the type, Manager, Supervisor or Employee
        }
        return 5;//return other number just in case  
    }


    public static boolean validateEmployeeExists(String user, String password) throws SQLException {
        rs = null;
        String sql = "SELECT * FROM usuario where username='" + user + "' and password_user='" + password + "'";
        preparedStatement = conn.prepareStatement(sql);
        rs = preparedStatement.executeQuery();
        if (rs.first()) {
            rs.close();
            return true;
        } else {
            return false;
        }
    }


}
