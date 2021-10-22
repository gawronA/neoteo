package pl.local.neoteo.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.collections.list.TreeList;
import org.springframework.stereotype.Service;
import pl.local.neoteo.entity.Record;
import pl.local.neoteo.entity.User;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.TableView;
import java.io.IOException;
import java.io.OutputStream;
import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service
public class PdfServiceImpl implements PdfService {
/*
    private final UtilityTypeService utilityTypeService;

    public PdfServiceImpl(UtilityTypeService utilityTypeService) {
        this.utilityTypeService = utilityTypeService;
    }*/

    @Override
    public void generateReport(User user, OutputStream out) {
        try {
            Document pdf = new Document();
            PdfWriter.getInstance(pdf, out);
            pdf.open();

            Font defaultFont = new Font(Font.FontFamily.HELVETICA, 11);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font tabHeaderFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);

            pdf.add(new Paragraph("Neoteo", headerFont));
            pdf.add(new Paragraph(Chunk.NEWLINE));
            pdf.add(new Paragraph("Raport zuzycia " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")"));
            pdf.add(new Paragraph(Chunk.NEWLINE));
            pdf.add(new Paragraph(Chunk.NEWLINE));

            PdfPTable table = new PdfPTable(3);
            table.addCell("Data");
            table.addCell("Ilosc");
            table.addCell("Cena");

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            var records = getRecordsAndCategories(user);
            for(String cat : records.keySet()) {
                Phrase header = new Phrase(cat, tabHeaderFont);
                PdfPCell headerCell = new PdfPCell(header);
                headerCell.setColspan(3);
                table.addCell(headerCell);

                for(Record record : records.get(cat)) {
                    table.addCell(dateFormat.format(record.getDate()));
                    table.addCell(String.format("%.2f", record.getAmount()) + " " + record.getType().getUnit());
                    table.addCell(String.format("%.2f", record.getPrice()) + " zl");
                }
            }

            Phrase footer = new Phrase("SUMA", tabHeaderFont);
            PdfPCell tableFooter = new PdfPCell(footer);
            tableFooter.setColspan(2);
            table.addCell(tableFooter);

            Phrase sum = new Phrase(String.format("%.2f", getRecordSum(user)) + " zl", tabHeaderFont);
            table.addCell(new PdfPCell(sum));

            pdf.add(table);
            pdf.close();
            out.close();
        }
        catch (IOException | DocumentException ex) {
            ex.printStackTrace();
        }

    }

    double getRecordSum(User user) {
        double sum = 0;
        for(Record record : user.getProperty().getRecords()) {
            sum += record.getPrice();
        }
        return sum;
    }

    Map<String, Set<Record>> getRecordsAndCategories(User user) {
        var tree = new TreeMap<String, Set<Record>>(Collator.getInstance());
        var records = user.getProperty().getRecords();
        for(Record record : records) {
            var cat = record.getType().getUtilityName();
            if(tree.containsKey(cat)) {
                tree.get(cat).add(record);
            }
            else {
                var recordSet = new TreeSet<Record>(new RecordComparator());
                recordSet.add(record);
                tree.put(cat, recordSet);
            }
        }

        return tree;
    }

    static class RecordComparator implements Comparator<Record> {

        @Override
        public int compare(Record o1, Record o2) {
            if(o1.getDate().after(o2.getDate())) return 1;
            else if(o1.getDate().before(o2.getDate())) return -1;
            else return 0;
        }
    }
}
