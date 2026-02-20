package com.billmaster.analytics_report_service.export;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.billmaster.analytics_report_service.dto.DailySalesReportDTO;
import com.billmaster.analytics_report_service.dto.LowStockProductDTO;
import com.billmaster.analytics_report_service.dto.TransactionCountDTO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Service
public class PdfExportService {

    public byte[] generateManagerReport(
            DailySalesReportDTO daily,
            TransactionCountDTO transactions,
            List<LowStockProductDTO> lowStock) {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("REPORT SUMMARY"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Daily Sales Report"));
            document.add(new Paragraph("Date: " + daily.getDate()));
            document.add(new Paragraph("Total Revenue: ₹" + daily.getTotalRevenue()));
            document.add(new Paragraph("Total Orders: " + daily.getTotalOrders()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Transaction Summary"));
            document.add(new Paragraph("Transactions Today: " + transactions.getTotalTransactions()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Low Stock Products"));
            if (lowStock.isEmpty()) {
                document.add(new Paragraph("No low stock products."));
            } else {
                for (LowStockProductDTO product : lowStock) {
                    document.add(new Paragraph(
                            product.getName() +
                            " - Stock: " + product.getStock()));
                }
            }

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed");
        }
    }
}