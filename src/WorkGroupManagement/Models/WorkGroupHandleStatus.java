package WorkGroupManagement.Models;

import Database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkGroupHandleStatus {
    private static ResultSet rs;
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    public static void activateWorkGroup(int groupID){
        String sql="update workgroup set workgroup_status=1 where pk_workgroup_id=?;";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, groupID);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("ERROR in sql statement on method WorkGroupHandleStatus.activateWorkGroup error: "+e);
        }

    }

    public static void desactivateWorkGroup(int groupID){
        String sql="update workgroup set workgroup_status=0 where pk_workgroup_id=?;";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, groupID);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("ERROR in sql statement on method WorkGroupHandleStatus.desactivateWorkGroup error: "+e);
        }

    }

}
