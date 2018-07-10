package WorkGroupManagement.Models;

import Database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkGroupUpdate {
    private static ResultSet rs;
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    public static void editGroup(WorkGroup group){
        String sql="update workgroup set workgroup_name='"+ group.getWorkGroupName()
                +"' where pk_workgroup_id="+group.getWorkGroupID()+";";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("ERROR in sql statement method WorkGroupUpdate.editGroup() error: "+e);
        }

    }

}
