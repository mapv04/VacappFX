package WorkGroupManagement.Models.Implementations;

import Database.DatabaseConnection;
import WorkGroupManagement.Models.Abstracts.IWorkGroupHandleStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WorkGroupHandleStatus implements IWorkGroupHandleStatus {
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    @Override
    public void activateWorkGroup(int groupID){
        String sql="update workgroup set workgroup_status=1 where pk_workgroup_id=?;";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, groupID);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println("ERROR in sql statement on method WorkGroupHandleStatus.activateWorkGroup error: "+e);
        }

    }

    @Override
    public void desactivateWorkGroup(int groupID){
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
