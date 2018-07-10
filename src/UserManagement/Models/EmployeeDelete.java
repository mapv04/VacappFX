package UserManagement.Models;

import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public  class EmployeeDelete {

    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

<<<<<<< HEAD
    public  static void deleteEmployee(int employeeID)throws SQLException {
        // this is to delete employee from the other tables  needs to be before the usuarios table delete
        String sqlQuery1 = "DELETE FROM vac_employee_data WHERE fk_id_user = ?;";
        preparedStatement=conn.prepareStatement(sqlQuery1);
        preparedStatement.setInt(1,employeeID);
        preparedStatement.executeUpdate();

        String sqlQuery2 = "DELETE FROM vac_request WHERE fk_id_user = ?;";
        preparedStatement=conn.prepareStatement(sqlQuery2);
        preparedStatement.setInt(1,employeeID);
        preparedStatement.executeUpdate();

        String sqlQuery3 = "SELECT fk_id_user FROM vac_request WHERE supervisor_id = ?;";
        String sqlQuery4 = "UPDATE vac_request SET  supervisor_id = 1 WHERE supervisor_id = ?;";
        preparedStatement=conn.prepareStatement(sqlQuery3);
        preparedStatement.setInt(1,employeeID);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()){
            preparedStatement=conn.prepareStatement(sqlQuery4);
            preparedStatement.setInt(1,employeeID);
            preparedStatement.executeUpdate();
        }


        String sql="delete from usuario where pk_id_user='"+employeeID+"';";
        preparedStatement=conn.prepareStatement(sql);
        preparedStatement.executeUpdate(sql);
=======
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


>>>>>>> e30ff248c969de37f13de7df6d49b66464574411
    }

}
