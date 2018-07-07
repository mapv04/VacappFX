package UserManagement.Models;

import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    }


}
