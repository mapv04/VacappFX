package WorkGroupManagement.Models.Implementations;

import Database.DatabaseConnection;
import WorkGroupManagement.Models.Abstracts.AWorkGroup;
import WorkGroupManagement.Models.Abstracts.AWorkGroupData;
import WorkGroupManagement.Models.Abstracts.IWorkGroupUpdate;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WorkGroupUpdate implements IWorkGroupUpdate {
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    @Override
    public void editGroup(AWorkGroup group){
        String sql="update workgroup set workgroup_name='"+ group.getWorkGroupName()
                +"' where pk_workgroup_id="+group.getWorkGroupID()+";";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("ERROR in sql statement method WorkGroupUpdate.editGroup() error: "+e);
        }

    }

    @Override
    public void createGroup(AWorkGroup group){
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

    @Override
    public void addMember(AWorkGroupData newMember){
        String sql="insert into workgroup_data(fk_workgroup_id, fk_usuario_id,employee_name,added_date) values " +
                "(?,?,?,?);";
        try{
            preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setInt(1,newMember.getGroupID());
            preparedStatement.setInt(2,newMember.getEmployeeID());
            preparedStatement.setString(3,newMember.getEmployeeName());
            preparedStatement.setDate(4,Date.valueOf(newMember.getAddedDate()));
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("ERROR in sql statement on method WorkGroupUpdate.addMember error: "+e);
        }
    }

}
