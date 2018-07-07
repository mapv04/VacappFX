package VacationManagement.Models;

import Database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class VacRequestRead {

    private static ResultSet rs;
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement pStatement;


    public static List<VacRequest> getPendingRequest(int supervisorID){

        List<VacRequest> listVacReq = new ArrayList<>();
        String sqlQuery = "SELECT * FROM vac_request WHERE supervisor_id = ? AND status = 'Pending';";
        rs = null;

        try{
            pStatement = conn.prepareStatement(sqlQuery);
            pStatement.setInt(1,supervisorID);
             rs= pStatement.executeQuery();
            while(rs.next()){
                VacRequest vacReq = new VacRequest();
                vacReq.setPkIDRequest(rs.getInt(1));
                vacReq.setFkIDUser(rs.getInt(2));
                vacReq.setName(rs.getString(3));
                vacReq.setStartDate(rs.getDate(4).toLocalDate());
                vacReq.setEndDate(rs.getDate(5).toLocalDate());
                vacReq.setStatus(rs.getString(6));
                vacReq.setSupervisorID(rs.getInt(7));
                listVacReq.add(vacReq);
            }

        }catch (SQLException e){
            System.out.println("ERROR in sql statement method VacRequestRead.getPendingRequest error: "+e);
        }

        return listVacReq;
    }




    public static List<VacRequest> getRequestHistory(int supervisorID){

        List<VacRequest> listVacReq = new ArrayList<>();
        String sqlQuery = "SELECT * FROM vac_request WHERE supervisor_id = ? ;";
        rs = null;

        try{
            pStatement = conn.prepareStatement(sqlQuery);
            pStatement.setInt(1,supervisorID);
            rs= pStatement.executeQuery();
            while(rs.next()){
                VacRequest vacReq = new VacRequest();
                vacReq.setPkIDRequest(rs.getInt(1));
                vacReq.setFkIDUser(rs.getInt(2));
                vacReq.setName(rs.getString(3));

                vacReq.setStartDate(rs.getDate(4).toLocalDate());
                vacReq.setEndDate(rs.getDate(5).toLocalDate());

                vacReq.setStatus(rs.getString(6));
                vacReq.setSupervisorID(rs.getInt(7));
                listVacReq.add(vacReq);
            }

        }catch (SQLException e){
            System.out.println("ERROR in sql statement method VacRequestRead.getRequestHistory error: "+e);
        }

        return listVacReq;
    }




}
