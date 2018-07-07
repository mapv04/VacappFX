package UserManagement.Models;

import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public  class EmployeeDelete {

    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    public  static void deleteEmployee(int employeeID)throws SQLException {
        String sql="delete from usuario where pk_id_user='"+employeeID+"';";
        String sql2 = "";

        preparedStatement=conn.prepareStatement(sql);
        preparedStatement.executeUpdate();

        preparedStatement=conn.prepareStatement(sql2);
        preparedStatement.executeQuery();
    }
}
