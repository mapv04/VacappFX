package VacationManagementEmployee.Models.Abstracts;

import VacationManagementSupervisor.Models.Abstracts.AVacRequest;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public interface IVacEmployeeFactory {
    AVacEmployee getVacEmployee();
    List<AVacRequest> getVacRequestList();
    AVacRequest getVacRequest();
    Document getDocumentO();
    FileOutputStream getFileOutputStream();
    PdfPTable getTableO();
    Paragraph getParagraph1();
    Paragraph getParagraph2();
    File getFileO();
}
