package Login.Models.Implementations;

import Database.DatabaseConnection;
import Login.Models.Abstracts.IEmployeeSearch;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeSearch implements IEmployeeSearch {
    private static ResultSet rs;
    private static Connection conn = DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;

    @Override
    public boolean searchEmployeeExists(Employee employee) throws SQLException {
        rs = null;
        String sql = "select username, email from usuario where username='" + employee.getUsername() + "' or email='"
                + employee.getEmail() + "';";
        preparedStatement = conn.prepareStatement(sql);
        rs = preparedStatement.executeQuery();
        return rs.first();//true if the employee already exists
    }

    @Override
    public boolean searchEmployeeValidateAnswer(Employee employee) throws SQLException {
        rs = null;
        String sql = "select question,answer from usuario where " +
                "username ='"+ employee.getUsername() +
                "' and question='"+employee.getQuestion() +
                "' and answer='"+employee.getResponse()+"';";
        preparedStatement = conn.prepareStatement(sql);
        rs = preparedStatement.executeQuery();
        return rs.first();//true if the answer of employee exists
    }

    @Override
    public int searchEmployeeUserName(String user) {
        rs = null;
        String sqlQuery = "SELECT pk_id_user FROM usuario WHERE username = ?";
        try {
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, user);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("ERROR in sql statement method EmployeeSearch.searchEmployeeUserName error: " + e);
        }
        return -1;
    }

    @Override
    public Employee searchEmployeeID(int employeeID) {
        rs = null;
        Employee employee = new Employee();
        String sqlQuery = "SELECT * FROM usuario WHERE pk_id_user = ?";
        try{
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setInt(1,employeeID);
            rs = preparedStatement.executeQuery();
            if(rs.next()){
                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setLastName(rs.getString(3));
                employee.setUsername(rs.getString(4));
                employee.setEmail(rs.getString(5));
                employee.setPassword(rs.getString(6));
                employee.setType(rs.getInt(7));
                employee.setStatus(rs.getInt(8));
            }
            else {
                System.out.println("ERROR empty set method EmployeeSearch.searchEmployeeID");
            }
        }catch(SQLException e){
            System.out.println("ERROR in sql statement method EmployeeSearch.searchEmployeeID error: " + e);
        }
        return employee;
    }
}
