package UserManagement.Models;

import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public  class EmployeeDelete {

    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    public  static void deleteEmployee(int employeeID)throws SQLException {

        // this is to delete employee from the other tables  needs to be before the usuarios table delete
        String sqlQuery2 = "DELETE FROM vac_employee_data WHERE fk_id_user = ?";
        preparedStatement=conn.prepareStatement(sqlQuery2);
        preparedStatement.setInt(1,employeeID);
        preparedStatement.executeUpdate();

        String sqlQuery3 = "DELETE FROM vac_request WHERE fk_id_user = ?";
        preparedStatement=conn.prepareStatement(sqlQuery3);
        preparedStatement.setInt(1,employeeID);
        preparedStatement.executeUpdate();



        String sql="delete from usuario where pk_id_user='"+employeeID+"';";
        preparedStatement=conn.prepareStatement(sql);
        preparedStatement.executeUpdate();



    }
}
