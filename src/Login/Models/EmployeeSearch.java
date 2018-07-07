package Login.Models;

import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author migue
 */
public class EmployeeSearch {

    private static ResultSet rs;
    private static Connection conn = DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    public static boolean searchEmployeeExists(Employee employee) throws SQLException {//this method validate that the employee entered en the register not exist
        rs = null;
        String sql = "select username, email from usuario where username='" + employee.getUsername() + "' or email='"
                + employee.getEmail() + "';";
        preparedStatement = conn.prepareStatement(sql);
        rs = preparedStatement.executeQuery();
        return rs.first();//true if the employee already exists

    }

    public static int searchEmployeeUserName(String user) {
        rs = null;
        String sqlQuery = "SELECT pk_id_user FROM usuario WHERE username = ?";

        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, user);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("ERROR in sql statement method EmployeeSearch.searchEmployeeUserName error: " + e);
        }
        return -1;
    }


}
