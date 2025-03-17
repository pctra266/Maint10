package Controller.Product;

import DAO.StaffDAO;
import DAO.WarrantyCardDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import com.itextpdf.text.BaseColor;
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
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Model.Customer;
import Model.Product;
import Model.Staff;
import Model.WarrantyCard;
import Model.UnknownProduct;
import java.time.LocalDate;

public class ExportPDF extends HttpServlet {

    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    private final StaffDAO staffDAO = new StaffDAO();

    /**
     * Converts a number to English words.
     * This simple implementation works up to billions.
     */
    private String convertNumberToEnglishWords(long number) {
        if (number < 0) {
            return "Invalid";
        }
        if (number == 0) {
            return "zero";
        }
        String[] units = {"", "thousand", "million", "billion"};
        String[] belowTwenty = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
                                  "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
        String[] tens = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
        
        String result = "";
        int unitIndex = 0;
        while (number > 0) {
            if (number % 1000 != 0) {
                result = convertHundred((int) (number % 1000), belowTwenty, tens) + " " + units[unitIndex] + " " + result;
            }
            number /= 1000;
            unitIndex++;
        }
        return result.trim();
    }
    
    private String convertHundred(int number, String[] belowTwenty, String[] tens) {
        String result = "";
        if (number >= 100) {
            result += belowTwenty[number / 100] + " hundred";
            number %= 100;
            if (number > 0) {
                result += " and ";
            }
        }
        if (number < 20) {
            result += belowTwenty[number];
        } else {
            result += tens[number / 10];
            if (number % 10 > 0) {
                result += "-" + belowTwenty[number % 10];
            }
        }
        return result;
    }
    
    /**
     * Formats a price value without decimals.
     */
    private String formatPrice(Object obj) {
        if (obj == null) return "";
        try {
            double value = Double.parseDouble(obj.toString());
            // Remove decimals by casting to long.
            return String.valueOf((long) value);
        } catch (Exception e) {
            return obj.toString();
        }
    }
    
    private String getSafeString(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set content type and header for the PDF file
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"WarrantyCard.pdf\"");
        
        // Retrieve parameters and data
        String warrantyCardCode = request.getParameter("warrantyCardCode");
        WarrantyCard wr = warrantyCardDAO.searchWarrantyCardByCode(warrantyCardCode);
        if (wr == null) {
            return; // You may show an error message if needed
        }
        
        Staff staff = staffDAO.getStaffById(wr.getHandlerID());
        Customer customer = warrantyCardDAO.getCustomerByWarrantyProductID(wr.getWarrantyProductID());
        Object product = warrantyCardDAO.getProductByWarrantyProductId(wr.getWarrantyProductID());
        boolean isUnknownProduct = (product instanceof UnknownProduct);
        
        List<Map<String, Object>> component = warrantyCardDAO.getWarrantyCardDetails(wr.getWarrantyCardID());
        
        // Current date
        LocalDate today = LocalDate.now();
        int day = today.getDayOfMonth();
        int month = today.getMonthValue();
        int year = today.getYear();
        
        // Date formatter
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
            
            // Title
            Paragraph title = new Paragraph("Warranty Card Preview", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // Blank line
            
            // Create a 2-column table with 3 rows:
            // Row 1: Store information (left/right)
            // Row 2: Warranty card details (left) and Staff information (right)
            // Row 3: Customer (left) and Product (right)
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingBefore(5f);
            infoTable.setSpacingAfter(5f);
            
            // --- Row 1 ---
            // Left cell: Store info part 1
            PdfPCell storeLeftCell = new PdfPCell();
            storeLeftCell.setBorder(PdfPCell.NO_BORDER);
            Paragraph storeLeftPara = new Paragraph();
            storeLeftPara.add(new Phrase("Store: ABC Electronics\n", normalFont));
            storeLeftPara.add(new Phrase("Address: 123 XYZ Street, Hanoi\n", normalFont));
            storeLeftPara.add(new Phrase("Contact: 0901 234 567\n", normalFont));
            storeLeftPara.add(new Phrase("Email: support@abc-electronics.com\n", normalFont));
            storeLeftCell.addElement(storeLeftPara);
            infoTable.addCell(storeLeftCell);
            
            // Right cell: Store info part 2
            PdfPCell storeRightCell = new PdfPCell();
            storeRightCell.setBorder(PdfPCell.NO_BORDER);
            Paragraph storeRightPara = new Paragraph();
            storeRightPara.add(new Phrase("Form: support@abc-electronics.com\n", normalFont));
            storeRightPara.add(new Phrase("Serial: support@abc-electronics.com\n", normalFont));
            storeRightPara.add(new Phrase("No: support@abc-electronics.com\n", normalFont));
            storeRightCell.addElement(storeRightPara);
            infoTable.addCell(storeRightCell);
            
            // --- Row 2 ---
            // Left cell: Warranty card details
            PdfPCell warrantyLeftCell = new PdfPCell();
            warrantyLeftCell.setBorder(PdfPCell.NO_BORDER);
            Paragraph warrantyLeftPara = new Paragraph();
            warrantyLeftPara.add(new Phrase("Warranty Card Code: " + wr.getWarrantyCardCode() + "\n", normalFont));
            if (wr.getCreatedDate() != null) {
                warrantyLeftPara.add(new Phrase("Created Date: " + sdf.format(wr.getCreatedDate()) + "\n", normalFont));
            }
            if (wr.getCompletedDate() != null) {
                warrantyLeftPara.add(new Phrase("Return Date: " + sdf.format(wr.getCompletedDate()) + "\n", normalFont));
            }
            warrantyLeftCell.addElement(warrantyLeftPara);
            infoTable.addCell(warrantyLeftCell);
            
            // Right cell: Staff information
            PdfPCell staffRightCell = new PdfPCell();
            staffRightCell.setBorder(PdfPCell.NO_BORDER);
            Paragraph staffRightPara = new Paragraph();
            if (staff != null) {
                staffRightPara.add(new Phrase("Staff in charge: " + staff.getName() + "\n", normalFont));
                staffRightPara.add(new Phrase("Staff phone: " + staff.getPhone() + "\n", normalFont));
                staffRightPara.add(new Phrase("Staff email: " + staff.getEmail() + "\n", normalFont));
            }
            staffRightCell.addElement(staffRightPara);
            infoTable.addCell(staffRightCell);
            
            // --- Row 3 ---
            // Left cell: Customer information
            PdfPCell customerLeftCell = new PdfPCell();
            customerLeftCell.setBorder(PdfPCell.NO_BORDER);
            Paragraph customerLeftPara = new Paragraph("Customer\n", headerFont);
            if (customer != null) {
                customerLeftPara.add(new Phrase("Name: " + customer.getName() + "\n", normalFont));
                if (customer.getDateOfBirth() != null) {
                    customerLeftPara.add(new Phrase("DOB: " + sdf.format(customer.getDateOfBirth()) + "\n", normalFont));
                }
                customerLeftPara.add(new Phrase("Phone: " + customer.getPhone() + "\n", normalFont));
                customerLeftPara.add(new Phrase("Email: " + customer.getEmail() + "\n", normalFont));
                customerLeftPara.add(new Phrase("Address: " + customer.getAddress() + "\n", normalFont));
            } else {
                customerLeftPara.add(new Phrase("No customer info.\n", normalFont));
            }
            customerLeftCell.addElement(customerLeftPara);
            infoTable.addCell(customerLeftCell);
            
            // Right cell: Product information
            PdfPCell productRightCell = new PdfPCell();
            productRightCell.setBorder(PdfPCell.NO_BORDER);
            Paragraph productRightPara = new Paragraph("Product\n", headerFont);
            if (isUnknownProduct) {
                UnknownProduct up = (UnknownProduct) product;
                productRightPara.add(new Phrase("Name: " + up.getProductName() + "\n", normalFont));
                productRightPara.add(new Phrase("Code: " + up.getProductCode() + "\n", normalFont));
                productRightPara.add(new Phrase("Description: " + up.getDescription() + "\n", normalFont));
                if (up.getReceivedDate() != null) {
                    productRightPara.add(new Phrase("Received Date: " + up.getReceivedDate() + "\n", normalFont));
                }
            } else {
                Product p = (Product) product;
                productRightPara.add(new Phrase("Name: " + p.getProductName() + "\n", normalFont));
                productRightPara.add(new Phrase("Code: " + p.getCode() + "\n", normalFont));
                productRightPara.add(new Phrase("Brand: " + p.getBrandName() + "\n", normalFont));
                productRightPara.add(new Phrase("Type: " + p.getProductTypeName() + "\n", normalFont));
                if (p.getPurchaseDate() != null) {
                    productRightPara.add(new Phrase("Purchased Date: " + p.getPurchaseDate() + "\n", normalFont));
                }
            }
            productRightCell.addElement(productRightPara);
            infoTable.addCell(productRightCell);
            
            document.add(infoTable);
            
            // --------------------------------------------------
            // Components details table (8 columns)
            // --------------------------------------------------
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            
            String[] headers = {
                "Component Code", "Component Name", "Brand Name", "Type Name",
                "Price/pc", "Uses", "Note", "Total Price"
            };
            Font headerTableFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerTableFont));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
            
            double grandTotal = 0.0;
            if (component != null && !component.isEmpty()) {
                for (Map<String, Object> detail : component) {
                    table.addCell(new Phrase(getSafeString(detail.get("ComponentCode")), normalFont));
                    table.addCell(new Phrase(getSafeString(detail.get("ComponentName")), normalFont));
                    table.addCell(new Phrase(getSafeString(detail.get("BrandName")), normalFont));
                    table.addCell(new Phrase(getSafeString(detail.get("TypeName")), normalFont));
                    table.addCell(new Phrase(formatPrice(detail.get("pricePerPiece")), normalFont));
                    table.addCell(new Phrase(getSafeString(detail.get("numberOfUses")), normalFont));
                    table.addCell(new Phrase(getSafeString(detail.get("Note")), normalFont));
                    table.addCell(new Phrase(formatPrice(detail.get("totalPrice")), normalFont));
                    
                    if (detail.get("totalPrice") != null) {
                        try {
                            double price = Double.parseDouble(detail.get("totalPrice").toString());
                            grandTotal += price;
                        } catch (NumberFormatException ex) {
                            // Ignore if parsing fails
                        }
                    }
                }
            } else {
                PdfPCell noDataCell = new PdfPCell(new Phrase("No data available.", normalFont));
                noDataCell.setColspan(8);
                noDataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(noDataCell);
            }
            document.add(table);
            
            // Total amount
            Paragraph totalPara = new Paragraph();
            totalPara.add(new Phrase("Total: " + formatPrice(grandTotal) + "\n", normalFont));
            long totalLong = (long) grandTotal;
            totalPara.add(new Phrase("Total (in words): " + convertNumberToEnglishWords(totalLong) + " dong\n", normalFont));
            document.add(totalPara);
            
            // Signature section
            document.add(new Paragraph(" "));
            Paragraph signPara = new Paragraph();
            signPara.add(new Phrase("Signature Confirmation\n\n", normalFont));
            signPara.add(new Phrase("Hanoi, " + day + "/" + month + "/" + year + "\n\n", normalFont));
            signPara.add(new Phrase("Customer: ______________________    Technical Staff: ______________________", normalFont));
            document.add(signPara);
            
        } catch (DocumentException e) {
            throw new IOException("Error while creating PDF: " + e.getMessage());
        } finally {
            document.close();
        }
    }
}
