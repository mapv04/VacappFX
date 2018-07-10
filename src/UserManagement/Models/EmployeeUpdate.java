package UserManagement.Models;

import Database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeUpdate {
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;


    public static void modifyEmployee(Employee employee)throws SQLException {
        String sql="update usuario set name_user='"+employee.getName()+"', last_name='"+employee.getLastName()
                +"', type_user='"+employee.getType()+"', status_user='"+employee.getStatus()
                +"' where pk_id_user='"+employee.getId()+"';";
        preparedStatement=conn.prepareStatement(sql);
        preparedStatement.executeUpdate();

        // this is to modify employee from the other table
        String sqlQuery3 = "SELECT fk_id_user FROM vac_request WHERE supervisor_id = ?;";
        String sqlQuery4 = "UPDATE vac_request SET  supervisor_id = 1 WHERE supervisor_id = ?;";
        preparedStatement=conn.prepareStatement(sqlQuery3);
        preparedStatement.setInt(1,employee.getId());
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()){
            preparedStatement=conn.prepareStatement(sqlQuery4);
            preparedStatement.setInt(1,employee.getId());
            preparedStatement.executeUpdate();
        }
    }


}
