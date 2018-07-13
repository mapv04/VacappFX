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
        }

        //@RobertoR this is to update vac_request tables
        String sqlQuery1 = "SELECT fk_id_user FROM vac_request WHERE supervisor_id = ?;";
        String sqlQuery2 = "UPDATE vac_request SET  supervisor_id = 1 WHERE supervisor_id = ?;";
        try{
            preparedStatement = conn.prepareStatement(sqlQuery1);
            preparedStatement.setInt(1, employee.getId());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                preparedStatement = conn.prepareStatement(sqlQuery2);
                preparedStatement.setInt(1, employee.getId());
                preparedStatement.executeUpdate();
            }
        }catch (SQLException e){
            System.out.println("ERROR in statemen method EmployeeUpdate.modifyEmployee error: "+e);
        }





    }


}
