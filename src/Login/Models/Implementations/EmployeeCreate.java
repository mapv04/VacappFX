package Login.Models.Implementations;

import Database.DatabaseConnection;
import Login.Models.Abstracts.IEmployeeCreate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class EmployeeCreate implements IEmployeeCreate {
    private static Connection conn = DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement preparedStatement;


    @Override
    public void addNewEmployee(Employee employee)   {
        try {
            String sql = "insert into usuario(name_user, last_name, username, email, password_user, type_user, status_user, answer,question) "
                    + "values ('"
                    + employee.getName() + "','"
                    + employee.getLastName() + "','"
                    + employee.getUsername() + "','"
                    + employee.getEmail() + "','"
                    + employee.getPassword() + "','"
                    + employee.getType() + "','"
                    + employee.getQuestion() + "','"
                    + employee.getResponse() + "','"
                    + employee.getStatus() + "');";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();

            //this is to create new entry on table vac_employee data
            ResultSet rs = null;
            String sqlQuery2 = "SELECT pk_id_user FROM usuario WHERE username = ?;";
            String sqlQuery3 =  "INSERT INTO vac_employee_data" +
                    "(fk_id_user, hired_date, vac_days_available)" +
                    "VALUES(?,?,?);";
            preparedStatement = conn.prepareStatement(sqlQuery2);
            preparedStatement.setString(1, employee.getUsername());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int employeeID = rs.getInt(1);
                LocalDate local = LocalDate.now();
                preparedStatement = conn.prepareStatement(sqlQuery3);
                preparedStatement.setInt(1, employeeID);
                preparedStatement.setDate(2, java.sql.Date.valueOf(local.toString()));
                preparedStatement.setInt(3, 0);
                preparedStatement.executeUpdate();
            } else {
                System.out.println("Error empty set on EmployeeCreate.addNewEmployee");
            }
        }catch(Exception e) {
        }

    }

}
