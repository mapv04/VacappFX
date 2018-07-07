package Login.Models;

import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class EmployeeCreate {
    private static Connection conn = DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    public static void addNewEmployee(Employee employee) throws SQLException {//this method add a new employee
        String sql = "insert into usuario(name_user, last_name, username, email, password_user, type_user, status_user) "
                + "values ('" + employee.getName() + "','" + employee.getLastName() + "','" + employee.getUsername() + "','" + employee.getEmail() + "','"
                + employee.getPassword() + "','" + employee.getType() + "','" + employee.getStatus() + "');";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }


}
