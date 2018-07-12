package UserManagement.Models;

import Database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeUpdate {
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;


    public static void modifyEmployee(Employee employee) {
        String sql="update usuario set name_user='"+employee.getName()+"', last_name='"+employee.getLastName()
<<<<<<< HEAD
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
=======
                +"', type_user='"+employee.getType()+"' where pk_id_user='"+employee.getId()+"';";
        String sql2="update workgroup_data set employee_name='"+ employee.getName() +" "+employee.getLastName()+"' where " +
                "fk_usuario_id="+employee.getId()+";";
        String sql3="update workgroup set leader_name='"+ employee.getName() +" "+employee.getLastName()+"' where " +
                "fk_leader_id="+employee.getId()+";";
        try {
            preparedStatement = conn.prepareStatement(sql2);
            preparedStatement.executeUpdate();
            try {
                preparedStatement = conn.prepareStatement(sql3);
                preparedStatement.executeUpdate();
                try {
                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.executeUpdate();
                }catch(SQLException e){
                    System.out.println("ERROR in sql statement on methos EmployeeUpdate.modifyEmployee error: "+e);
                }
            }catch(SQLException e){
                System.out.println("ERROR in sql3 statement on method EmployeeUpdate.modifyEmployee error: "+e);
            }
        }catch(SQLException e){
            System.out.println("ERROR in sql2 statement on method EmployeeUpdate.modifyEmployee error: "+e);
>>>>>>> c5c0fdfb1d13405ace91db48ccab5d30aa02efc0
        }
    }


}
