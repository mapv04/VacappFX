package VacationManagementEmployee.Models.Implemetations;

import Database.DatabaseConnection;
import VacationManagementEmployee.Models.Abstracts.AVacEmployee;
import VacationManagementEmployee.Models.Abstracts.IVacEmployeeSearch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VacEmployeeSearch implements IVacEmployeeSearch {
    private static ResultSet rs;
    private static Connection conn= DatabaseConnection.getInstance().getConnection();
    private static PreparedStatement pStatement;
    private AVacEmployee vacEmployee;

    public VacEmployeeSearch(AVacEmployee vacEmployee){
        this.vacEmployee = vacEmployee;
    }

    @Override
    public AVacEmployee searchVacEmployeeData(int employeeID){
        String sqlQuery = "SELECT * FROM vac_employee_data WHERE fk_id_user = ?";
        try{
            pStatement = conn.prepareStatement(sqlQuery);
            pStatement.setInt(1,employeeID);
            rs = pStatement.executeQuery();
            if(rs.next()){
                vacEmployee.setPkID(rs.getInt(1));
                vacEmployee.setEmployeeID(rs.getInt(2));
                vacEmployee.setHiredDate(rs.getDate(3).toLocalDate());
                vacEmployee.setVacDaysAvailable(rs.getInt(4));
            }
            else {
                System.out.println("ERROR set empty in VacEmployeeSearch.searchVacEmployeeData");
            }
        }catch(SQLException e ){
            System.out.println("ERROR in sql statement method VacEmployeeSearch.searchVacEmployeeData error: "+e);
        }

        return vacEmployee;
    }

}
