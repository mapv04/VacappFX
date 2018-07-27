package VacationManagementEmployee.Models.Implemetations;

import VacationManagementEmployee.Models.Abstracts.IVacEmployeeReport;
import VacationManagementSupervisor.Models.Abstracts.AVacRequest;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


public class VacEmployeeReport implements IVacEmployeeReport {
    private Document document;
    private FileOutputStream fileOutputStream;
    private PdfPTable table;
    private Paragraph paragraph1;
    private Paragraph paragraph2;
    private File file;

    public VacEmployeeReport(Document document, PdfPTable table, Paragraph paragraph1, Paragraph paragraph2,
                            FileOutputStream fileOutputStream, File file){
        this.document = document;
        this.table = table;
        this.paragraph1 = paragraph1;
        this.paragraph2 = paragraph2;
        this.fileOutputStream = fileOutputStream;
        this.file = file;
    }

    @Override
    public  void createReportTable(List<AVacRequest> listRequests){
        if(listRequests.isEmpty()||listRequests==null){
            return;
        }
        try{
            String dest = "reports/employee/employee_report.pdf";
            PdfWriter.getInstance(document, fileOutputStream);
            table.setWidthPercentage(100);

            table.addCell("EmployeeID");
            table.addCell("RequestID");
            table.addCell("Start Date");
            table.addCell("End date");
            table.addCell("Status");
            table.addCell("Days");

            for(AVacRequest requests: listRequests){
                table.addCell(String.valueOf(requests.getFkIDUser()));
                table.addCell(String.valueOf(requests.getPkIDRequest()));
                table.addCell(requests.getStartDate());
                table.addCell(requests.getEndDate());
                table.addCell(requests.getStatus());
                table.addCell(String.valueOf(requests.getDaysRequested()));
            }
            document.open();
            document.add(paragraph1);
            document.add(paragraph2);
            document.add(table);
            document.close();
            Desktop.getDesktop().open(file);
        }catch (Exception e){
            System.out.println("The pdf couldn't be generated");
        }
    }
}
