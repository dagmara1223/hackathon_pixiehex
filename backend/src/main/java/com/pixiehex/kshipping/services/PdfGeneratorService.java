package com.pixiehex.kshipping.services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pixiehex.kshipping.model.GroupOrder;
import com.pixiehex.kshipping.model.SingleOrder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PdfGeneratorService {

    // Folder, gdzie wylądują PDFy
    private static final String REPORT_DIR = "./reports/";

    public PdfGeneratorService() {
        // Upewnij się, że folder istnieje
        new File(REPORT_DIR).mkdirs();
    }

    public void generateVendorPdf(GroupOrder group) {
        try {
            String filename = REPORT_DIR + "VENDOR_" + group.getName().replace(" ", "_") + ".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));

            document.open();

            // Nagłówek
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            document.add(new Paragraph("ORDER FOR VENDOR (KOREA)", titleFont));
            document.add(new Paragraph("Batch: " + group.getName()));
            document.add(new Paragraph("Date: " + group.getCreatedDate().toString()));
            document.add(new Paragraph(" ")); // Odstęp

            // Tabela: Produkt | Ilość
            PdfPTable table = new PdfPTable(2);
            addTableHeader(table, "Product Name", "Quantity");

            // AGREGACJA: Zliczamy ile sztuk danego produktu zamówiono
            Map<String, Long> productCounts = group.getOrders().stream()
                    .collect(Collectors.groupingBy(SingleOrder::getProductName, Collectors.counting()));

            for (Map.Entry<String, Long> entry : productCounts.entrySet()) {
                table.addCell(entry.getKey());
                table.addCell(entry.getValue().toString());
            }

            document.add(table);

            // Stopka
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Please ship to: K-Shipping Hub, Seoul, Warehouse 42."));

            document.close();
            System.out.println("Wygenerowano PDF dla Vendora: " + filename);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- DOKUMENT 2: WEWNĘTRZNY (PEŁNE DANE) ---
    public void generateInternalSummaryPdf(GroupOrder group) {
        try {
            String filename = REPORT_DIR + "INTERNAL_" + group.getName().replace(" ", "_") + ".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));

            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            document.add(new Paragraph("INTERNAL BATCH SUMMARY", titleFont));
            document.add(new Paragraph("Batch ID: " + group.getId()));
            document.add(new Paragraph("Total Weight: " + group.getTotalWeight() + "g"));
            document.add(new Paragraph(" "));

            // Tabela: Email | Produkt | Cena | Adres
            PdfPTable table = new PdfPTable(4);
            // Ustawiamy szerokości kolumn (żeby adres się zmieścił)
            table.setWidths(new float[] { 3, 3, 2, 4 });

            addTableHeader(table, "User Email", "Product", "Final Price", "Address");

            for (SingleOrder order : group.getOrders()) {
                table.addCell(order.getUserEmail());
                table.addCell(order.getProductName());
                table.addCell(order.getFinalPrice() + " PLN");
                // Obsługa nulla, jeśli adres nie podany
                table.addCell(order.getShippingAddress() != null ? order.getShippingAddress() : "N/A");
            }

            document.add(table);
            document.close();
            System.out.println("Wygenerowano PDF wewnętrzny: " + filename);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addTableHeader(PdfPTable table, String... headers) {
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell();
            headerCell.setPhrase(new Phrase(header));
            // Szary kolor nagłówka
            headerCell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
            table.addCell(headerCell);
        }
    }
}