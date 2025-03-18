package Controller.WarrantyCard;

import DAO.StaffDAO;
import DAO.WarrantyCardDAO;
import Model.Customer;
import Model.Staff;
import Model.WarrantyCard;
import Model.UnknownProduct;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sonNH
 */
public class SearchWarrantyCard extends HttpServlet {

    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    private final StaffDAO staffDAO = new StaffDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String warrantyCardCode = request.getParameter("warrantyCode");
        WarrantyCard wr = warrantyCardDAO.searchWarrantyCardByCode(warrantyCardCode);

        if (wr == null) {
            request.setAttribute("errorMessage", "Warranty card not found!");
        } else {
            Staff staff = staffDAO.getStaffById(wr.getHandlerID());
            Customer customer = warrantyCardDAO.getCustomerByWarrantyProductID(wr.getWarrantyProductID());
            Object product = warrantyCardDAO.getProductByWarrantyProductId(wr.getWarrantyProductID());

            // Kiểm tra xem product là Product hay UnknownProduct
            boolean isUnknownProduct = product instanceof UnknownProduct;

            List<Map<String, Object>> component = warrantyCardDAO.getWarrantyCardDetails(wr.getWarrantyCardID());

            // Lấy ngày hiện tại
            LocalDate today = LocalDate.now();
            // Tách từng phần ngày, tháng, năm
            int day = today.getDayOfMonth();
            int month = today.getMonthValue();
            int year = today.getYear();

            // Đặt các giá trị này vào request
            request.setAttribute("day", day);
            request.setAttribute("month", month);
            request.setAttribute("year", year);
            // Đưa dữ liệu vào request attribute
            request.setAttribute("component", component);
            // Đẩy dữ liệu sang JSP
            request.setAttribute("warrantyCard", wr);
            request.setAttribute("customer", customer);
            request.setAttribute("product", product);
            request.setAttribute("staff", staff);
            request.setAttribute("isUnknownProduct", isUnknownProduct);

        }
        request.getRequestDispatcher("searchWarrantyCard.jsp").forward(request, response);
    }
}
