package WorkGroupManagement.Models;

import Database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkGroupDelete {
    private static ResultSet rs;
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    public static void deleteGroup(int groupID){
        String sql="delete from workgroup where pk_workgroup_id='"+groupID+"';";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("ERROR in sql statement on method WorkGroupDelete.deleteGroup error: "+e);
        }
    }
}
