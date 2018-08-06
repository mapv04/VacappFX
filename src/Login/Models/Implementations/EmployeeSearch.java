package Login.Models.Implementations;

import Database.DatabaseConnection;
import Login.Models.Abstracts.AEmployee;
import Login.Models.Abstracts.IEmployeeFactory;
import Login.Models.Abstracts.IEmployeeSearch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeSearch implements IEmployeeSearch {
    private static ResultSet rs;
    private static Connection conn = DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;
    private AEmployee employee;
    private IEmployeeFactory employeeFactory;

    public EmployeeSearch (AEmployee employee, IEmployeeFactory employeeFactory ){

        this.employee=employee;
        this.employeeFactory=employeeFactory;
    }


    @Override
    public boolean searchEmployeeExists(AEmployee employee) throws SQLException {
        rs = null;
        String sql = "select username, email from usuario where username='" + employee.getUsername() + "' or email='"
                + employee.getEmail() + "';";
        preparedStatement = conn.prepareStatement(sql);
        rs = preparedStatement.executeQuery();
        return rs.first();//true if the employee already exists
    }

    @Override
    public boolean searchEmployeeValidateAnswer(AEmployee employee) throws SQLException {
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
    public AEmployee searchEmployeeID(int employeeID) {
        rs = null;
        String sqlQuery = "SELECT * FROM usuario WHERE pk_id_user = ?";
        try{
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setInt(1,employeeID);
            rs = preparedStatement.executeQuery();
            if(rs.next()){
                employee=employeeFactory.getEmployee();
                employee.setId(rs.getInt("pk_id_user"));
                employee.setName(rs.getString("name_user"));
                employee.setLastName(rs.getString("last_name"));
                employee.setUsername(rs.getString("username"));
                employee.setEmail(rs.getString(5));
                employee.setPassword(rs.getString("password_user"));
                employee.setStatus(rs.getInt("status_user"));
                employee.setType(rs.getInt("type_user"));
            }
            else {
                System.out.println("ERROR empty set method EmployeeSearch.searchEmployeeID");
            }
        }catch(SQLException e){
            System.out.println("ERROR in sql statement method EmployeeSearch.searchEmployeeID error: " + e);
        }
        return  employee;
    }
}
