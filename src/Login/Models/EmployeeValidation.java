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
<<<<<<< HEAD
        String sql = "select type_user from usuario where username='" + user + "';";
        preparedStatement = conn.prepareStatement(sql);
        rs = preparedStatement.executeQuery();
        while (rs.next()) {
            int type = rs.getInt("type_user");
            return type;//return the type, Manager, Supervisor or Employee
        }
        return 5;//return other number just in case  
    }


    public static boolean validateEmployeeExists(String user, String password) throws SQLException {
        rs = null;
        String sql = "SELECT * FROM usuario where username='" + user + "' and password_user='" + password + "'";
        preparedStatement = conn.prepareStatement(sql);
        rs = preparedStatement.executeQuery();
        if (rs.first()) {
            rs.close();
            return true;
        } else {
            return false;
        }
=======
        String sql = "UPDATE `usuario` SET `status_user` = '0' WHERE `usuario`.`username` = '"
                + user + "';";
        st = conn.createStatement();
        st.executeUpdate(sql);
>>>>>>> 731ad649ff56c69736651f339146086b072b8bf1
    }


}