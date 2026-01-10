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
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PdfGeneratorService {

    // ZMIANA: Ścieżka celuje wprost w Twoje źródła w projekcie.
    // Dzięki temu pliki pojawią się w IntelliJ w folderze resources.
    private static final String REPORT_DIR = "./src/main/resources/reports/";

    public PdfGeneratorService() {
        // Tworzymy katalog, jeśli nie istnieje
        File directory = new File(REPORT_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public void generateVendorPdf(GroupOrder group) {
        try {
            // Generowanie nazwy
            String filename = REPORT_DIR + "VENDOR_" + group.getName().replace(" ", "_") + ".pdf";

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));

            document.open();

            // Nagłówek
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            document.add(new Paragraph("ORDER FOR VENDOR (KOREA)", titleFont));
            document.add(new Paragraph("Batch: " + group.getName()));
            document.add(new Paragraph("Date: " + group.getCreatedDate().toString()));
            document.add(new Paragraph(" "));

            // Tabela
            PdfPTable table = new PdfPTable(2);
            addTableHeader(table, "Product Name", "Quantity");

            Map<String, Long> productCounts = group.getOrders().stream()
                    .collect(Collectors.groupingBy(SingleOrder::getProductName, Collectors.counting()));

            for (Map.Entry<String, Long> entry : productCounts.entrySet()) {
                table.addCell(entry.getKey());
                table.addCell(entry.getValue().toString());
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Please ship to: K-Shipping Hub, Seoul, Warehouse 42."));

            document.close();
            System.out.println("✅ Wygenerowano PDF dla Vendora: " + filename);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

            PdfPTable table = new PdfPTable(4);
            table.setWidths(new float[] { 3, 3, 2, 4 });

            addTableHeader(table, "User Email", "Product", "Final Price", "Address");

            for (SingleOrder order : group.getOrders()) {
                table.addCell(order.getUserEmail());
                table.addCell(order.getProductName());
                table.addCell(order.getFinalPrice() + " PLN");
                table.addCell(order.getShippingAddress() != null ? order.getShippingAddress() : "N/A");
                table.addCell(order.getPhoneNumber() != null ? order.getPhoneNumber() : "-");
            }

            document.add(table);
            document.close();
            System.out.println("✅ Wygenerowano PDF wewnętrzny: " + filename);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addTableHeader(PdfPTable table, String... headers) {
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell();
            headerCell.setPhrase(new Phrase(header));
            headerCell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
            table.addCell(headerCell);
        }
    }

    public void generateShippingLabels(GroupOrder group) {
        try {
            String filename = REPORT_DIR + "LABELS_" + group.getName().replace(" ", "_") + ".pdf";

            // Format A6 (typowy dla etykiet) lub A4
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(filename));

            document.open();

            for (SingleOrder order : group.getOrders()) {
                // Nowa strona dla każdego klienta
                document.newPage();

                // --- RAMKA ETYKIETY (Rysujemy tabelą) ---
                PdfPTable labelTable = new PdfPTable(1);
                labelTable.setWidthPercentage(100);

                // --- SEKCJA GÓRNA: KURIER ---
                PdfPCell header = new PdfPCell();
                header.setBorderWidth(2);
                header.setPadding(10);
                header.addElement(new Paragraph("K-SHIPPING EXPRESS", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24)));
                header.addElement(new Paragraph("PRIORITY / LOTNICZA", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
                labelTable.addCell(header);

                // --- SEKCJA: ADRESAT ---
                PdfPCell addressCell = new PdfPCell();
                addressCell.setPadding(15);
                addressCell.addElement(new Paragraph("ODBIORCA:", FontFactory.getFont(FontFactory.HELVETICA, 10)));
                addressCell.addElement(new Paragraph(order.getUserEmail(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
                addressCell.addElement(new Paragraph("Tel: " + (order.getPhoneNumber() != null ? order.getPhoneNumber() : "---")));
                addressCell.addElement(new Paragraph("Adres:"));
                addressCell.addElement(new Paragraph(order.getShippingAddress() != null ? order.getShippingAddress() : "BRAK ADRESU", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
                labelTable.addCell(addressCell);

                PdfPCell barcodeCell = new PdfPCell();
                barcodeCell.setPadding(20);
                barcodeCell.setHorizontalAlignment(Element.ALIGN_CENTER);

                Paragraph fakeBarcode = new Paragraph("||| || ||| | ||| || || |||| ||| ||", FontFactory.getFont(FontFactory.COURIER_BOLD, 20));
                fakeBarcode.setAlignment(Element.ALIGN_CENTER);

                barcodeCell.addElement(fakeBarcode);
                barcodeCell.addElement(new Paragraph("TRACKING: KSH-" + order.getOrderId() + "-PL", FontFactory.getFont(FontFactory.COURIER, 12)));

                labelTable.addCell(barcodeCell);

                document.add(labelTable);

                document.add(new Paragraph("\n------------------------------------------------------------\n"));
            }

            document.close();
            System.out.println("✅ Wygenerowano etykiety: " + filename);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}