package Controller.Invoice;

import DAO.InvoiceDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExportInvoicePDF extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String invoiceIdParam = request.getParameter("invoiceId");
        if (invoiceIdParam == null || invoiceIdParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invoice ID is required.");
            return;
        }
        int invoiceId;
        try {
            invoiceId = Integer.parseInt(invoiceIdParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Invoice ID.");
            return;
        }

        Map<String, Object> invoiceDetail = invoiceDAO.getInvoiceDetails(invoiceId);
        if (invoiceDetail == null || invoiceDetail.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found.");
            return;
        }

        // Thiết lập nội dung phản hồi là PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"Invoice_" + invoiceId + ".pdf\"");

        // Khởi tạo tài liệu với lề (margin) tùy chỉnh
        Document document = new Document();
        document.setMargins(30, 30, 20, 20);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Tạo các font
            Font companyFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font normalFont  = FontFactory.getFont(FontFactory.HELVETICA,      11);
            Font boldFont    = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font titleFont   = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);

            // HEADER: Thông tin công ty
            Paragraph companyName = new Paragraph("NHS Company", companyFont);
            companyName.setAlignment(Element.ALIGN_CENTER);
            companyName.setSpacingAfter(2f);
            document.add(companyName);

            Paragraph companyAddress = new Paragraph("123 ABC Street, DEF City - (+84) 912 345 678", normalFont);
            companyAddress.setAlignment(Element.ALIGN_CENTER);
            companyAddress.setSpacingAfter(10f);  // Giãn một chút bên dưới địa chỉ
            document.add(companyAddress);

            // PHẦN THÔNG TIN CHUNG (Receiver & Invoice Info)
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(60); 
            infoTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            infoTable.setSpacingBefore(5f);
            infoTable.setSpacingAfter(10f);
            infoTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

            // Tiêu đề cột trái
            PdfPCell cell = new PdfPCell(new Phrase("Receiver", boldFont));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            infoTable.addCell(cell);

            // Tiêu đề cột phải
            cell = new PdfPCell(new Phrase("Invoice Information", boldFont));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            infoTable.addCell(cell);

            // Nội dung cột trái (Receiver info)
            String receiverInfo = "NHS\n123 ABC Street, DEF City";
            cell = new PdfPCell(new Phrase(receiverInfo, normalFont));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            infoTable.addCell(cell);

            // Nội dung cột phải (Invoice Info)
            String invoiceNumber     = getSafe(invoiceDetail.get("InvoiceNumber"));
            String issuedDateForInfo = formatDate(invoiceDetail.get("IssuedDate"), "dd/MM/yyyy");
            String invoiceInfo       = "No: " + invoiceNumber + "\nDate: " + issuedDateForInfo;

            cell = new PdfPCell(new Phrase(invoiceInfo, normalFont));
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            infoTable.addCell(cell);

            document.add(infoTable);

            // TIÊU ĐỀ INVOICE
            Paragraph invoiceTitle = new Paragraph("Invoice", titleFont);
            invoiceTitle.setAlignment(Element.ALIGN_CENTER);
            invoiceTitle.setSpacingAfter(10f);
            document.add(invoiceTitle);

            // BẢNG CHI TIẾT (mỗi dòng có kẻ chấm bên dưới)
            PdfPTable detailsTable = new PdfPTable(2);
            detailsTable.setWidthPercentage(60);
            detailsTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            detailsTable.setSpacingBefore(5f);
            detailsTable.setSpacingAfter(10f);
            detailsTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

            // Thêm các dòng chi tiết
            addRowWithDottedLine(detailsTable, "Invoice Number",       invoiceNumber,                                                boldFont, normalFont);
            addRowWithDottedLine(detailsTable, "Warranty Card Code",   getSafe(invoiceDetail.get("WarrantyCardCode")),              boldFont, normalFont);
            addRowWithDottedLine(detailsTable, "Issued Date",          formatDate(invoiceDetail.get("IssuedDate"), "yyyy-MM-dd"),   boldFont, normalFont);
            addRowWithDottedLine(detailsTable, "Due Date",             formatDate(invoiceDetail.get("DueDate"),   "yyyy-MM-dd"),    boldFont, normalFont);
            addRowWithDottedLine(detailsTable, "Status",               getSafe(invoiceDetail.get("Status")),                       boldFont, normalFont);
            addRowWithDottedLine(detailsTable, "Created By",           getSafe(invoiceDetail.get("CreatedByName")),                 boldFont, normalFont);
            addRowWithDottedLine(detailsTable, "Received By",          getSafe(invoiceDetail.get("ReceivedByName")),                boldFont, normalFont);
            addRowWithDottedLine(detailsTable, "Issue Description",    getSafe(invoiceDetail.get("IssueDescription")),              boldFont, normalFont);
            addRowWithDottedLine(detailsTable, "Amount",               getSafe(invoiceDetail.get("Amount")),                        boldFont, normalFont);

            document.add(detailsTable);

            // FOOTER
            Paragraph footer = new Paragraph("Generated by our system", normalFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

        } catch (DocumentException e) {
            throw new IOException("Error while creating PDF: " + e.getMessage());
        } finally {
            document.close();
        }
    }

    /**
     * Thêm một hàng thông tin (label, value) và một dòng kẻ chấm (dotted line) bên dưới.
     */
    private void addRowWithDottedLine(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        // Hàng thông tin (label - value)
        PdfPCell cellLabel = new PdfPCell(new Phrase(label, labelFont));
        cellLabel.setBorder(PdfPCell.NO_BORDER);
        cellLabel.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellLabel.setPaddingBottom(6f); // Tăng khoảng cách dưới cho nội dung
        table.addCell(cellLabel);

        PdfPCell cellValue = new PdfPCell(new Phrase(value, valueFont));
        cellValue.setBorder(PdfPCell.NO_BORDER);
        cellValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellValue.setPaddingBottom(6f);
        table.addCell(cellValue);

        // Hàng kẻ chấm
        PdfPCell dottedCell = new PdfPCell();
        dottedCell.setColspan(2);
        dottedCell.setFixedHeight(4f); // Độ cao của vùng vẽ
        dottedCell.setBorder(PdfPCell.NO_BORDER);
        dottedCell.setPaddingTop(0f);  
        dottedCell.setCellEvent(new DottedLineCell());
        table.addCell(dottedCell);
    }

    /**
     * Vẽ một đường kẻ chấm (dotted) chạy hết chiều rộng ô.
     */
    private class DottedLineCell implements PdfPCellEvent {
        @Override
        public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] canvases) {
            PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
            canvas.saveState();
            // Chọn màu xám nhạt và thiết lập dạng gạch chấm
            canvas.setColorStroke(BaseColor.LIGHT_GRAY);
            // (3f, 2f): độ dài nét vẽ = 3f, khoảng trống = 2f, có thể chỉnh theo ý muốn
            canvas.setLineDash(3f, 2f);
            canvas.setLineWidth(0.8f);

            // Vẽ đường từ trái sang phải ở vị trí bottom
            canvas.moveTo(rect.getLeft(),  rect.getBottom());
            canvas.lineTo(rect.getRight(), rect.getBottom());
            canvas.stroke();
            canvas.restoreState();
        }
    }

    private String getSafe(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    /**
     * Định dạng ngày với pattern cho trước.
     */
    private String formatDate(Object dateObj, String pattern) {
        if (dateObj == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.format(dateObj);
        } catch (Exception e) {
            return dateObj.toString();
        }
    }
}
