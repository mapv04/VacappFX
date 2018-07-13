package VacationManagementSupervisor.Models.Implementations;


import VacationManagementSupervisor.Models.Abstracts.AVacRequest;
import VacationManagementSupervisor.Models.Abstracts.IVacRequestReport;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class VacRequestReport implements IVacRequestReport {


    @Override
    public  void createReportTable(List<AVacRequest> listRequests){
        if(listRequests.isEmpty()||listRequests==null){
            return;
        }
        try{
            String dest = "reports/supervisor/supervisor_report.pdf";
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            PdfPTable table  = new PdfPTable(7);
            table.setWidthPercentage(100);

            table.addCell("EmployeeID");
            table.addCell("RequestID");
            table.addCell("Name");
            table.addCell("Start Date");
            table.addCell("End date");
            table.addCell("Status");
            table.addCell("Days");

            for(AVacRequest requests: listRequests){
                table.addCell(String.valueOf(requests.getFkIDUser()));
                table.addCell(String.valueOf(requests.getPkIDRequest()));
                table.addCell(requests.getName());
                table.addCell(requests.getStartDate());
                table.addCell(requests.getEndDate());
                table.addCell(requests.getStatus());
                table.addCell(String.valueOf(requests.getDaysRequested()));
            }
            document.open();
            document.add(new Paragraph("Supervisor Vacation Requests Historical\n",
                                             FontFactory.getFont(FontFactory.HELVETICA_BOLD,20,BaseColor.BLUE)));
            document.add(new Paragraph(" "));
            document.add(table);
            document.close();
            Desktop.getDesktop().open(new File(dest));
        }catch (Exception e){
            System.out.println("The pdf couldn't be generated");
        }
    }





}
