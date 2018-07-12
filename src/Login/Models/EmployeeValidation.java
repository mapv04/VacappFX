package Login.Models;

import Database.DatabaseConnection;

import java.sql.*;

/**
 * @author migue
 */
public class EmployeeValidation {

    private static ResultSet rs;
    private static Connection conn = DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;
    private static Statement st;

    public static Employee employeeExist(String user){
        String sql="select * from usuario where username =?;";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user);
            rs = preparedStatement.executeQuery();
            while(rs.first()){
                Employee employee= new Employee();
                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setLastName(rs.getString(3));
                employee.setUsername(rs.getString(4));
                employee.setPassword(rs.getString(6));
                employee.setType(rs.getInt(7));
                employee.setStatus(rs.getInt(8));
                return employee;
            }
        }catch (SQLException e){
            System.out.println("ERROR in sql statement in method EmployeeValidation.employeeExist error: "+e);
        }
        return null;
    }

    public static void blockUser(String user) throws SQLException {
        rs = null;
        String sql = "UPDATE `usuario` SET `status_user` = '0' WHERE `usuario`.`username` = '"
                + user + "';";
        st = conn.createStatement();
        st.executeUpdate(sql);
    }


}