package VacationManagementEmployee.Models;

import Database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class VacEmployeeGenerateReq {
    private static ResultSet rs;
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement pStatement;

    private static void vacGenerateRequest(int employeeID, LocalDate start_date,LocalDate end_date){
        String sqlQuery = "INSERT INTO (fk_id_user,start_date,end_date,supervisor_id) VALUES(?,?,?,?); ";
        String sqlQuery2 = "SELECT supervisor_id FROM usuario WHERE pk_id_user = ?; ";



    }
}
