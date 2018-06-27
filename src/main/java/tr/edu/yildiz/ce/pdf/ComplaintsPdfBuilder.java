package tr.edu.yildiz.ce.pdf;

import java.util.List;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import tr.edu.yildiz.ce.model.ComplaintInfo;
import tr.edu.yildiz.ce.pdf.AbstractPdf;;
 
/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 * @author www.codejava.net
 *
 */
public class ComplaintsPdfBuilder extends AbstractPdf {
	
 
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        List<ComplaintInfo> listComplaints = (List<ComplaintInfo>) model.get("listComplaints");
         
        doc.add(new Paragraph("Sikayetler Raporu"));
         
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {1.0f, 2.5f, 2.0f, 2.0f, 1.5f, 1.0f});
        table.setSpacingBefore(10);
         
        // define font for table header row
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(BaseColor.WHITE);
        
        
        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.BLUE);
        cell.setPadding(5);
         
        // write table header
        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Açiklama", font));
        table.addCell(cell);
 
        cell.setPhrase(new Phrase("Konum", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Tarih", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Sikayetçi", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Durum", font));
        table.addCell(cell);
         
        // write table row data
        for (ComplaintInfo complaint : listComplaints) {
            table.addCell(String.valueOf(complaint.getId()));
            table.addCell(complaint.getComplaintText());
            table.addCell(complaint.getLocationInfo().getDescription());
            table.addCell(String.valueOf(complaint.getComplaintTime()));
            table.addCell(complaint.getComplainantUserInfo().getUsername());
            table.addCell(complaint.getResponseText());
        }
         
        doc.add(table);
         
    }
 
}