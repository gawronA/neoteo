package pl.local.neoteo.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import pl.local.neoteo.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class PdfServiceImpl implements PdfService {

    //private final PaymentService paymentService;

    /*public PdfServiceImpl(PaymentService paymentService) {
        this.paymentService = paymentService;
    }*/

    @Override
    public void generatePaymentsPdf(User user, HttpServletResponse response) {
        try {
            OutputStream o = response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=mpark_płatności_" + user.getFirstName() + "_" + user.getLastName());
            Document pdf = new Document();
            PdfWriter.getInstance(pdf, o);
            pdf.open();

            pdf.add(new Paragraph("Mobility Park"));
            pdf.add(new Paragraph(Chunk.NEWLINE));
            pdf.add(new Paragraph("Lista platności uzytkownika " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")"));
            pdf.add(new Paragraph(Chunk.NEWLINE));
            pdf.add(new Paragraph(Chunk.NEWLINE));

            PdfPTable table = new PdfPTable(3);
            table.addCell("Nazwa");
            table.addCell("Kwota");
            table.addCell("Oplacone");

            /*for(Payment payment : user.getPayments()) {
                table.addCell(payment.getName());
                table.addCell(Double.toString(payment.getAmount()));
                if(payment.isPaid()) table.addCell("Tak");
                else table.addCell("Nie");
            }*/

            pdf.add(table);
            pdf.close();
            o.close();
        }
        catch (IOException | DocumentException ex) {
            ex.printStackTrace();
        }

    }
}
