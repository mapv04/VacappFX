package Login.Models.Implementations;

import Database.DatabaseConnection;
import Login.Models.Abstracts.AEmployee;
import Login.Models.Abstracts.IEmployeeFactory;
import Login.Models.Abstracts.IEmployeeValidation;

import java.sql.*;

public class EmployeeValidation implements IEmployeeValidation {
    private static ResultSet rs;
    private static Connection conn = DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;
    private static Statement st;
    private AEmployee employee;
    private IEmployeeFactory employeeFactory;

    @Override
    public Employee employeeExist(String user) {
        String sql="select * from usuario where username =?;";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user);
            rs = preparedStatement.executeQuery();
            while(rs.first()){
                //Employee employee;
                //employee = new Employee(rs);
                employee=employeeFactory.getEmployee();
                employee.setId(rs.getInt("pk_id_user"));
                employee.setName(rs.getString("name_user"));
                employee.setLastName(rs.getString("last_name"));
                employee.setUsername(rs.getString("username"));
                employee.setEmail(rs.getString(5));
                employee.setPassword(rs.getString("password_user"));
                employee.setStatus(rs.getInt("status_user"));
                employee.setType(rs.getInt("type_user"));
                return (Employee) employee;
            }
        }catch (SQLException e){
            System.out.println("ERROR in sql statement in method EmployeeValidation.employeeExist error: "+e);
        }
        return null;
    }

    @Override
    public void blockUser(String user) throws SQLException {
        rs = null;
        String sql = "UPDATE `usuario` SET `status_user` = '0' WHERE `usuario`.`username` = '"
                + user + "';";
        st = conn.createStatement();
        st.executeUpdate(sql);
    }
}
