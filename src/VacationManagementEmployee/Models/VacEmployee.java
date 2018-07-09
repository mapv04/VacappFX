package VacationManagementEmployee.Models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VacEmployee {
    private int pkID;
    private int employeeID;
    private LocalDate hiredDate;
    private int vacDaysAvailable;


    public void setPkID(int pkID) {
        this.pkID = pkID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setHiredDate(LocalDate hiredDate) {
        this.hiredDate = hiredDate.plusDays(1);
    }

    public void setVacDaysAvailable(int vacDaysAvailable) {
        this.vacDaysAvailable = vacDaysAvailable;
    }

    public int getPkID() {
        return pkID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getHiredDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return formatter.format(this.hiredDate);
    }

    public int getVacDaysAvailable() {
        return vacDaysAvailable;
    }
}
