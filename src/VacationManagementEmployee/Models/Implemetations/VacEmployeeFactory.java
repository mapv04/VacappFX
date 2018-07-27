package VacationManagementEmployee.Models.Implemetations;

import VacationManagementEmployee.Models.Abstracts.AVacEmployee;
import VacationManagementEmployee.Models.Abstracts.IVacEmployeeFactory;
import VacationManagementSupervisor.Models.Abstracts.AVacRequest;
import VacationManagementSupervisor.Models.Implementations.VacRequest;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class VacEmployeeFactory implements IVacEmployeeFactory {
    @Override
    public AVacEmployee getVacEmployee(){
        return  new VacEmployee();
    }

    @Override
    public List<AVacRequest> getVacRequestList(){
        return new ArrayList<>();
    }

    @Override
    public AVacRequest getVacRequest(){
        return  new VacRequest();
    }

    @Override
    public Document getDocumentO(){
        return new Document(PageSize.A4.rotate());
    }

    @Override
    public FileOutputStream getFileOutputStream(){
        String dest = "reports/supervisor/employee_report.pdf";
        FileOutputStream fileOutputStream;
        try{
            fileOutputStream = new FileOutputStream(dest);
        }catch (FileNotFoundException e){
            System.out.println("File not found");
            fileOutputStream = null;
        }
        return fileOutputStream;
    }

    @Override
    public PdfPTable getTableO(){
        return  new PdfPTable(6);
    }

    @Override
    public Paragraph getParagraph1(){
        return  new Paragraph("Employee Vacation Requests Historical\n",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD,20,BaseColor.BLUE));
    }

    @Override
    public Paragraph getParagraph2(){
        return new Paragraph(" ");
    }

    @Override
    public File getFileO(){
        String dest = "reports/supervisor/employee_report.pdf";
        return new File(dest);
    }
}
