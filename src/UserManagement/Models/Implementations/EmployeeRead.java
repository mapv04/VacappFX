package UserManagement.Models.Implementations;

import Database.DatabaseConnection;
import UserManagement.Models.Abstracts.AEmployee;
import UserManagement.Models.Abstracts.IEmployeeFactory;
import UserManagement.Models.Abstracts.IEmployeeRead;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeRead implements IEmployeeRead {

    private static ResultSet rs;
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    private AEmployee employee;
    private IEmployeeFactory employeeFactory;

    public EmployeeRead (AEmployee employee, IEmployeeFactory employeeFactory ){

        this.employee=employee;
        this.employeeFactory=employeeFactory;
    }


    @Override
    public void getAllEmployees(List<AEmployee> employeeList){
        //List<Employee> employeeList= new ArrayList<>();
        rs = null;
        String sql="select * from usuario where pk_id_user <> 1;";
        try {
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                employee=employeeFactory.getEmployee();
                employee.setId(rs.getInt("pk_id_user"));
                employee.setName(rs.getString("name_user"));
                employee.setLastName(rs.getString("last_name"));
                employee.setEmail(rs.getString(5));
                employee.setStatus(rs.getInt("status_user"));
                employee.setType(rs.getInt("type_user"));
                employeeList.add(employee);

            }
        }catch (SQLException e){
            System.out.println("ERROR in sql statement on method EmployeeRead.getAllEmployees error: "+e);
        }

    }


    @Override
    public int getStatus(int employeeID){
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

    @Override
    public void getAllSupervisors(List<AEmployee> supervisors){
        String sql="select * from usuario where type_user=1;";
        try{
            preparedStatement=conn.prepareStatement(sql);
            rs=preparedStatement.executeQuery();
            while(rs.next()){
                employee=employeeFactory.getEmployee();
                employee.setId(rs.getInt("pk_id_user"));
                String fullName=rs.getString("name_user")+" "+rs.getString("last_name");
                employee.setName(fullName);
                employee.setStatus(rs.getInt("status_user"));
                supervisors.add(employee);
            }
        }catch (SQLException e){
            System.out.println("ERROR in sql statement on method EmployeeRead.getAllSupervisors error: "+e);
        }

    }

    @Override
    public void getJustEmployeesType(List<AEmployee> employees){
        String sql="SELECT * FROM usuario u WHERE NOT EXISTS " +
                "(SELECT NULL FROM workgroup_data wd WHERE wd.fk_usuario_id =u.pk_id_user)";
        try{
            preparedStatement=conn.prepareStatement(sql);
            rs=preparedStatement.executeQuery();
            while(rs.next()){
                if(rs.getInt(7)==2) {
                    employee=employeeFactory.getEmployee();
                    employee.setId(rs.getInt(1));
                    String fullName = rs.getString(2) + " " + rs.getString(3);
                    employee.setName(fullName);
                    employee.setEmail(rs.getString(5));
                    employee.setStatus(rs.getInt(8));
                    employees.add(employee);
                }
            }
        }catch (SQLException e){
            System.out.println("ERROR in sql statement on method EmployeeRead.getJustEmployeesType error: "+e);
        }
    }

}
