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

    public  static void getAllEmployees(List<Employee> employeeList)throws SQLException {
        //List<Employee> employeeList= new ArrayList<>();
        rs = null;
        String sql="select * from usuario where pk_id_user<>1";
        preparedStatement=conn.prepareStatement(sql);
        rs= preparedStatement.executeQuery();
        while(rs.next()){
            Employee employee= new Employee();
            employee.setId(rs.getInt("pk_id_user"));
            employee.setName(rs.getString("name_user"));
            employee.setLastName(rs.getString("last_name"));
            employee.setStatus(rs.getInt("status_user"));
            employee.setType(rs.getInt("type_user"));
            employeeList.add(employee);
        }
        //return employeeList;
    }



}
