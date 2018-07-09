package UserManagement.Models;

import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public  class EmployeeDelete {

    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    public  static void deleteEmployee(int employeeID){
        String sql="delete from usuario where pk_id_user='"+employeeID+"';";
        String sql2 = "update workgroup set fk_leader_id='', leader_name='' where fk_leader_id='"+employeeID+"';";
        String sql3 = "delete from workgroup_data where fk_usuario_id='"+employeeID+"';";
        try {
            preparedStatement = conn.prepareStatement(sql3);
            preparedStatement.executeUpdate();
            try {
                preparedStatement = conn.prepareStatement(sql2);
                preparedStatement.executeUpdate();
                try {
                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.executeUpdate();
                }catch(SQLException exp){
                    System.out.println("ERROR in sql statement 1 on method EmployeeDelete.deleteEmployee error: "+exp);
                }
            }catch(SQLException ex){
                System.out.println("ERROR in sql statement 2 on method EmployeeDelete.deleteEmployee error: "+ex);
            }
        }catch(SQLException e){
            System.out.println("ERROR in sql statement 3 on method EmployeeDelete.deleteEmployee error: "+e);
        }


    }
}
