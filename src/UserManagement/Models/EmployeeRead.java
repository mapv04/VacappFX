package UserManagement.Models;

import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeRead {

    private static ResultSet rs;
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    public  static void getAllEmployees(List<Employee> employeeList){
        //List<Employee> employeeList= new ArrayList<>();
        rs = null;
        String sql="select * from usuario";
        try {
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("pk_id_user"));
                employee.setName(rs.getString("name_user"));
                employee.setLastName(rs.getString("last_name"));
                employee.setStatus(rs.getInt("status_user"));
                employee.setType(rs.getInt("type_user"));
                employeeList.add(employee);
            }
        }catch (SQLException e){
            System.out.println("ERROR in sql statement on method EmployeeRead.getAllEmployees error: "+e);
        }
        //return employeeList;
    }

    public static int getStatus(int employeeID){
        String sql="select status_user from usuario where pk_id_user="+employeeID+";";
        try {
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.first()) {
                int id = rs.getInt(1);
                return id;
            }
        }catch(SQLException e){
            System.out.println("ERROR in sql statement on method EmployeeRead.getStatus error: "+e);
        }
        return -1;
    }

    public static void getAllSupervisors(List<Employee> supervisors){
        String sql="select * from usuario where type_user=1;";
        try{
            preparedStatement=conn.prepareStatement(sql);
            rs=preparedStatement.executeQuery();
            while(rs.next()){
                Employee supervisor=new Employee();
                supervisor.setId(rs.getInt("pk_id_user"));
                String fullName=rs.getString("name_user")+" "+rs.getString("last_name");
                supervisor.setName(fullName);
                supervisor.setStatus(rs.getInt("status_user"));
                supervisors.add(supervisor);
            }
        }catch (SQLException e){
            System.out.println("ERROR in sql statement on method EmployeeRead.getAllSupervisors error: "+e);
        }

    }

}
