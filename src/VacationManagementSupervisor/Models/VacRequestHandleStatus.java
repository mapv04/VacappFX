package VacationManagementSupervisor.Models;

import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VacRequestHandleStatus {
    private static ResultSet rs;
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement pStatement;


    public static void approveVacation(int requestID, int employeeID , int days){
        rs = null;
        String sqlQuery1 = "UPDATE vac_request SET status='Approved' WHERE pk_id_request = ?;";
        String sqlQuery2 = "SELECT vac_days_available FROM vac_employee_data WHERE fk_id_user = ?;";
        String sqlQuery3 = "UPDATE vac_employee_data SET vac_days_available = ? WHERE fk_id_user = ?;";
        try{
            pStatement = conn.prepareStatement(sqlQuery1);
            pStatement.setInt(1,requestID);
            pStatement.executeUpdate();

            pStatement = conn.prepareStatement(sqlQuery2);
            pStatement.setInt(1,employeeID);
            rs= pStatement.executeQuery();

            if(rs.next()){
                int vacDaysLeft = rs.getInt(1) - days;
                pStatement = conn.prepareStatement(sqlQuery3);
                pStatement.setInt(1,vacDaysLeft);
                pStatement.setInt(2,employeeID);
                pStatement.executeUpdate();
            }
        }catch (SQLException e){
            System.out.println("ERROR in sql statement method VacRequestHandleStatus.approveVacation error: "+e);
        }

    }


    public static void denyVacation(int requestID){
        rs= null;
        String sqlQuery1 = "UPDATE vac_request SET status='Denied' WHERE pk_id_request = ?;";

        try{
            pStatement = conn.prepareStatement(sqlQuery1);
            pStatement.setInt(1,requestID);
            pStatement.executeUpdate();

        }catch (SQLException e){
            System.out.println("ERROR in sql statement method VacRequestHandleStatus.denyVacation error: "+e);
        }
    }





}
