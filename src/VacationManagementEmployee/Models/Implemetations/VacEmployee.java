package VacationManagementEmployee.Models.Implemetations;

import VacationManagementEmployee.Models.Abstracts.AVacEmployee;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VacEmployee extends AVacEmployee {

    @Override
    public void setPkID(int pkID) {
        super.pkID = pkID;
    }
    @Override
    public void setEmployeeID(int employeeID) {
        super.employeeID = employeeID;
    }
    @Override
    public void setVacDaysAvailable(int vacDaysAvailable) {
        super.vacDaysAvailable = vacDaysAvailable;
    }
    @Override
    public void setHiredDate(LocalDate hiredDate) {
        super.hiredDate = (hiredDate.plusDays(1));
    }

    @Override
    public int getPkID() {
        return  pkID;
    }
    @Override
    public int getEmployeeID() {
        return employeeID;
    }
    @Override
    public int getVacDaysAvailable() {
        return vacDaysAvailable;
    }
    @Override
    public String getHiredDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return formatter.format(hiredDate);
    }
}
