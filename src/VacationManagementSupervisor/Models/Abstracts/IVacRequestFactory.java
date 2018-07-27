package VacationManagementSupervisor.Models.Abstracts;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public interface IVacRequestFactory {
    List<AVacRequest> getVacRequestList();
    AVacRequest getVacRequest();
    Document getDocumentO();
    FileOutputStream getFileOutputStream();
    PdfPTable getTableO();
    Paragraph getParagraph1();
    Paragraph getParagraph2();
    File getFileO();
}
