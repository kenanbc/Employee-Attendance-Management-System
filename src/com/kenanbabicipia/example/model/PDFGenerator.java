package com.kenanbabicipia.example.model;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.kenanbabicipia.example.service.ActivityService;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PDFGenerator {

    public void generatePDF(JTable table, String filePath, String name, String lastName, String role, String month) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Activity Table", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            month = month.equals("") ? "Every month" : month;

            document.add(new Paragraph("\nName: " + name + " " + lastName + "\nRole: " + role + "\nMonth: " + month + "\n \n"));


            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            pdfTable.setWidthPercentage(100);


            TableModel model = table.getModel();
            for (int col = 0; col < model.getColumnCount(); col++) {
                PdfPCell cell = new PdfPCell(new Phrase(model.getColumnName(col)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(cell);
            }


            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object value = model.getValueAt(row, col);
                    pdfTable.addCell(value != null ? value.toString() : "");
                }
            }

            document.add(pdfTable);

            ActivityService activityService = new ActivityService();
            String totalWork = activityService.calculateTotalTime(table, 4);
            document.add(new Paragraph("\n Total work hours: " + totalWork));
            JOptionPane.showMessageDialog(null, "PDF successfully generated: " + filePath);

        } catch (DocumentException | FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error generating PDF: " + e.getMessage());
        } finally {
            document.close();
        }
    }
}

