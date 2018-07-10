package VacationManagementEmployee.Models;

import Database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class VacEmployeeGenerateReq {
    private static ResultSet rs;
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement pStatement;

    public static boolean vacGenerateRequest(int employeeID, LocalDate start_date,LocalDate end_date) {
        int vacDaysAvailable ;
        int supervisorID;
        String sqlQuery1 = "SELECT vac_days_available FROM vac_employee_data WHERE fk_id_user = ?;";
        String sqlQuery2 =  "SELECT fk_leader_id FROM workgroup w" +
                            " INNER JOIN  workgroup_data wd ON w.pk_workgroup_id = wd.fk_workgroup_id" +
                            " WHERE wd.fk_usuario_id = ?;";

        String sqlQuery3 =  " INSERT INTO vac_request (fk_id_user,start_date,end_date,status,supervisor_id) " +
                            " VALUES(?,?,?,?,?);";
        rs = null;
        try {
            pStatement = conn.prepareStatement(sqlQuery1);
            pStatement.setInt(1, employeeID);
            rs = pStatement.executeQuery();
            vacDaysAvailable = rs.next()?rs.getInt(1):0;
            if(start_date.isEqual(end_date)){
                if(vacDaysAvailable<1){
                    return false;
                }
            }else if(ChronoUnit.DAYS.between(start_date, end_date)>vacDaysAvailable){
                return false;
            }

            pStatement = conn.prepareStatement(sqlQuery2);
            pStatement.setInt(1, employeeID);
            rs = pStatement.executeQuery();
            supervisorID = rs.next()?rs.getInt(1):1;

            pStatement = conn.prepareStatement(sqlQuery3);
            pStatement.setInt(1, employeeID);
            pStatement.setString(2, start_date.toString());
            pStatement.setString(3, end_date.toString());
            pStatement.setString(4, "Pending");
            pStatement.setInt(5, supervisorID);
            pStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("ERROR in sql statement method VacEmployeeGenerateReq.vacGenerateRequest error: " + e);
        }

        return false;
    }


}
