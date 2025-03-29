package Controller.WarrantyCard;

import DAO.StaffDAO;
import DAO.WarrantyCardDAO;
import Email.Email;
import Model.Customer;
import Model.Staff;
import Model.UnknownProduct;
import Model.WarrantyCard;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sonNH
 */
public class SendWarrantyCard extends HttpServlet {

    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    private final StaffDAO staffDAO = new StaffDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String warrantyCardCode = request.getParameter("warrantyCardCode");
        WarrantyCard wr = warrantyCardDAO.searchWarrantyCardByCode(warrantyCardCode);

        if (wr == null) {
            request.setAttribute("warrantyCardCode", warrantyCardCode);
            request.setAttribute("errorMessage", "No Warranty Card to Send");
            request.getRequestDispatcher("searchWarrantyCard.jsp").forward(request, response);
        }

        Staff staffH = staffDAO.getStaffById(wr.getHandlerID());
        Customer customer = warrantyCardDAO.getCustomerByWarrantyProductID(wr.getWarrantyProductID());
        Object product = warrantyCardDAO.getProductByWarrantyProductId(wr.getWarrantyProductID());

        // Kiểm tra xem product là Product hay UnknownProduct
        boolean isUnknownProduct = product instanceof UnknownProduct;
        List<Map<String, Object>> component = warrantyCardDAO.getWarrantyCardDetails(wr.getWarrantyCardID());

        LocalDate today = LocalDate.now();
        int day = today.getDayOfMonth();
        int month = today.getMonthValue();
        int year = today.getYear();

        request.setAttribute("day", day);
        request.setAttribute("month", month);
        request.setAttribute("year", year);
        // Đưa dữ liệu vào request attribute
        request.setAttribute("component", component);
        // Đẩy dữ liệu sang JSP
        request.setAttribute("warrantyCard", wr);
        request.setAttribute("customer", customer);
        request.setAttribute("product", product);
        request.setAttribute("staffH", staffH);
        request.setAttribute("isUnknownProduct", isUnknownProduct);

        String customerEmail = request.getParameter("customerEmail");
        String customerName = request.getParameter("customerName");

        // Tạo tiêu đề email
        String subject = "Your Warranty Card " + warrantyCardCode;

        // Tạo nội dung email dạng HTML, có thể tùy chỉnh thêm thông tin phiếu bảo hành
        String htmlContent = "<h1>Warranty Card</h1>"
                + "<p>Dear " + customerName + "</p>"
                + "<p>Please find attached your warranty card with the code: <strong>"
                + warrantyCardCode + "</strong>.</p>"
                + "<p>Thank you for choosing our service.</p>";
        String filePath = "C:/Users/sonNH/Downloads/WarrantyCard" + warrantyCardCode + ".pdf";
        Email emailUtil = new Email();
        emailUtil.sendEmailWithHtmlAndAttachment(customerEmail, subject, htmlContent, filePath);
        request.setAttribute("successMessage", "Send To Customer Successfully");
        request.getRequestDispatcher("searchWarrantyCard.jsp").forward(request, response);
    }

}
