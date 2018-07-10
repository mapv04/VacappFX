package UserManagement.Models;

import Database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeHandleStatus {
    private static ResultSet rs;
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    public static void activateUser(int employeeID){
        String sql="update usuario set status_user=1 where pk_id_user=?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, employeeID);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("ERROR in sql statement in method EmployeeHandleStatus.activateUser error: "+e);
        }
    }

    public static void desactiateUser(int employeeID){
        String sql="update usuario set status_user=0 where pk_id_user=?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, employeeID);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("ERROR in sql statement in method EmployeeHandleStatus.desactivateUser error: "+e);
        }
    }

}
