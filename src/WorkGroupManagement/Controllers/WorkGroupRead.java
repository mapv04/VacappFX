package WorkGroupManagement.Controllers;

import Database.DatabaseConnection;
import WorkGroupManagement.Models.WorkGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class WorkGroupRead {
    private static ResultSet rs;
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    public void getAllWorkgroups(List<WorkGroup> workGroupsList) {
        String sql="select * from workgroup;";
        try {
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                WorkGroup group = new WorkGroup();
                group.setWorkGroupID(rs.getInt(1));
                group.setWorkGroupName(rs.getString(2));
                group.setFkLeaderID(rs.getInt(3));
                group.setLeaderName(rs.getString(4));
                group.setCreatedDate(rs.getDate(5).toLocalDate());
                group.setStatus(rs.getInt(6));
                workGroupsList.add(group);
            }
        }catch(SQLException e){
            System.out.println("ERROR in sql statement method WorkGroupRead.getAllWorkGroup  error: "+e);
        }
    }

    public int getMembersCount(int workGorupID){
        String sql="select count(*) as count from workgroup_data where fk_workgroup_id='"+workGorupID + "';";
        int count=0;
        try {
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                count=rs.getInt(1);
            }
        }catch(SQLException e){
            System.out.println("ERROR in sql statement method WorkGroupRead.getMembersCount error: "+e);
        }
        return count;
    }

}
