package WorkGroupManagement.Models.Implementations;

import Database.DatabaseConnection;
import UserManagement.Models.Abstracts.AEmployee;
import UserManagement.Models.Abstracts.IEmployeeFactory;
import UserManagement.Models.Abstracts.IEmployeeRead;
import UserManagement.Models.Implementations.EmployeeRead;
import WorkGroupManagement.Models.Abstracts.AWorkGroup;
import WorkGroupManagement.Models.Abstracts.AWorkGroupData;
import WorkGroupManagement.Models.Abstracts.IWorkGroupFactory;
import WorkGroupManagement.Models.Abstracts.IWorkGroupRead;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class WorkGroupRead implements IWorkGroupRead {
    private static ResultSet rs;
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    private IWorkGroupFactory workGroupFactory;
    private IEmployeeFactory employeeFactory;
    private AEmployee employee;
    private AWorkGroup group;
    private AWorkGroupData groupData;

    public WorkGroupRead(AWorkGroup group, AEmployee employee, IWorkGroupFactory workGroupFactory, IEmployeeFactory employeeFactory,
                         AWorkGroupData groupData){
        this.group=group;
        this.employee=employee;
        this.workGroupFactory=workGroupFactory;
        this.employeeFactory= employeeFactory;
        this.groupData=groupData;

    }

    public WorkGroupRead(){

    }

    @Override
    public void getAllWorkgroups(List<AWorkGroup> workGroupsList) {
        String sql="select * from workgroup;";
        try {
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                group=workGroupFactory.getWorkGroup();
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

    @Override
    public int getMembersCount(int workGroupID){
        String sql="select count(*) as count from workgroup_data where fk_workgroup_id='"+workGroupID + "';";
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

    @Override
    public void getWorkgroupData(List<AWorkGroupData> groupDataList, int groupID){
        String sql="select * from workgroup_data where fk_workgroup_id="+groupID+";";;
        try {
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                groupData=workGroupFactory.getWorkGroupData();
                employee= employeeFactory.getEmployee();
                groupData.setGroupID(rs.getInt(1));
                groupData.setEmployeeID(rs.getInt(2));
                groupData.setEmployeeName(rs.getString(3));
                groupData.setAddedDate(rs.getDate(4).toLocalDate());
                IEmployeeRead employeeRead= new EmployeeRead(employee,employeeFactory);
                groupData.setEmployeeStatus(employeeRead.getStatus(groupData.getEmployeeID()));
                groupDataList.add(groupData);
            }
        }catch(SQLException e){
            System.out.println("ERROR in sql statement on method WorkGroupRead. getWorkgroupData error: "+e);
        }
    }

    @Override
    public String getGroupName(int groupID){
        String sql="select workgroup_name from workgroup where pk_workgroup_id=?;";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,groupID);
            rs = preparedStatement.executeQuery();
            while (rs.first()) {
                String name = rs.getString(1);
                return name;
            }
        }catch(SQLException e){
            System.out.println("ERROR in sql statement on method WorkGroupRead.getGroupName error: "+e);
        }
        return null;
    }

    @Override
    public boolean ifGroupExist(String name){
        String sql="select * from workgroup where workgroup_name='"+name+"';";
        try {
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            return rs.first();
        }catch(SQLException e){
            System.out.println("ERROR in sql statement  on method WorkGroupRead.ifGroupExist error: "+e);
        }
        return false;

    }

    @Override
    public boolean isLeader(int id) {
        String sql="select * from workgroup where fk_leader_id=?;";
        try{
            preparedStatement= conn.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            rs=preparedStatement.executeQuery();
            return rs.first();
        }catch(SQLException e){
            System.out.println("ERROR in sql statement in methos WorGroupRead.isLeader error: "+e);
        }
        return false;
    }


}
