package WorkGroupManagement.Models;

import Database.DatabaseConnection;

import java.sql.*;

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

    public static void createGroup(WorkGroup group){
        String sql="insert into workgroup (workgroup_name,fk_leader_id,leader_name,created_date,workgroup_status) " +
                "values (?,?,?,?,?);";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,group.getWorkGroupName());
            preparedStatement.setInt(2,group.getFkLeaderID());
            preparedStatement.setString(3,group.getLeaderName());
            preparedStatement.setDate(4, Date.valueOf(group.getCreatedDate()));
            preparedStatement.setInt(5,1);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("ERROR in sql statement on method WorkGroupUpdate.createGroup error: "+e);
        }
    }

}
