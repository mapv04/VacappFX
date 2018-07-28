package VacationManagementSupervisor.Models.Implementations;

import Database.DatabaseConnection;
import VacationManagementSupervisor.Models.Abstracts.AVacRequest;
import VacationManagementSupervisor.Models.Abstracts.IVacRequestFactory;
import VacationManagementSupervisor.Models.Abstracts.IVacRequestSearch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VacRequestSearch implements IVacRequestSearch {

    private static ResultSet rs;
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement pStatement;
    private List<AVacRequest> listVacReq;
    private AVacRequest vacReq;
    private IVacRequestFactory vacRequestFactory;

    public VacRequestSearch(List<AVacRequest> listVacReq,AVacRequest vacReq, IVacRequestFactory vacRequestFactory){
        this.listVacReq = listVacReq;
        this.vacReq = vacReq;
        this.vacRequestFactory = vacRequestFactory;
    }

    @Override
    public  List<AVacRequest> searchAllRequests(int employeeID, int supervisorID){
        String sqlQuery = "SELECT * FROM vac_request vr" +
                          " INNER JOIN usuario u ON vr.fk_id_user = u.pk_id_user"+
                          " WHERE fk_id_user = ?" +
                          " AND supervisor_id = ? AND u.status_user = 1"+
                          " ORDER BY pk_id_request ASC;";
        try{
            pStatement = conn.prepareStatement(sqlQuery);
            pStatement.setInt(1, employeeID);
            pStatement.setInt(2,supervisorID);
            rs= pStatement.executeQuery();
            while(rs.next()){
                vacReq = vacRequestFactory.getVacRequest();
                vacReq.setPkIDRequest(rs.getInt(1));
                vacReq.setFkIDUser(rs.getInt(2));
                vacReq.setStartDate(rs.getDate(3).toLocalDate());
                vacReq.setEndDate(rs.getDate(4).toLocalDate());
                vacReq.setStatus(rs.getString(5));
                vacReq.setSupervisorID(rs.getInt(6));
                String name = rs.getString(8)+" "+ rs.getString(9);
                vacReq.setName(name);
                listVacReq.add(vacReq);
            }
        }catch (SQLException e){
            System.out.println("ERROR in sql statement method VacRequestSearch.searchAllRequests error: "+e);
        }
        return listVacReq;
    }


    @Override
    public  AVacRequest searchLatestRequest(int employeeID){
        String sqlQuery = "SELECT * FROM vac_request " +
                          "WHERE fk_id_user = ? " +
                          "ORDER BY pk_id_request DESC LIMIT 1";
        try{
            pStatement = conn.prepareStatement(sqlQuery);
            pStatement.setInt(1, employeeID);
            rs= pStatement.executeQuery();
            if(rs.next()){
                vacReq.setPkIDRequest(rs.getInt(1));
                vacReq.setStartDate(rs.getDate(3).toLocalDate());
                vacReq.setEndDate(rs.getDate(4).toLocalDate());
                vacReq.setStatus(rs.getString(5));
            }
        }catch (SQLException e){
            System.out.println("ERROR in sql statement method VacRequestSearch.searchAllRequests error: "+e);
        }
        return vacReq;
    }







}
