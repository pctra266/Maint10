package Controller.WarrantyCard;

import DAO.CustomerDAO;
import DAO.ProductDAO;
import DAO.StaffDAO;
import DAO.WarrantyCardDAO;
import Model.Customer;
import Model.Product;
import Model.Staff;
import Model.UnknownProduct;
import Utils.OtherUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.List;

/**
 *
 * @author sonNH
 */
@MultipartConfig(
        fileSizeThreshold = 2 * 1024 * 1024, // 2MB
        maxFileSize = 10 * 1024 * 1024, // 10MB
        maxRequestSize = 50 * 1024 * 1024 // 50MB
)
public class AddWarrantyCardUnknownProduct extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddWarrantyCardUnknownProduct</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddWarrantyCardUnknownProduct at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String customerId = request.getParameter("customerId");
        String productId = request.getParameter("productId");

        if (customerId == null || productId == null) {
            response.sendRedirect("error.jsp"); // Chuyển hướng nếu thiếu thông tin
            return;
        }

        CustomerDAO customerDAO = new CustomerDAO();
        ProductDAO productDAO = new ProductDAO();
        StaffDAO staffDAO = new StaffDAO();
        Customer customer = customerDAO.getCustomerByID(Integer.parseInt(customerId));
        UnknownProduct unknownProduct = productDAO.getUnknownProductById(Integer.parseInt(productId));
        int warrantyProductId = productDAO.getWarrantyProductIdByUnknownProductId(Integer.parseInt(productId));

        List<Staff> technicians = staffDAO.getAllTechnicians();

        if (customer == null || unknownProduct == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        // Gửi dữ liệu sang JSP
        request.setAttribute("customer", customer);
        request.setAttribute("unknownProduct", unknownProduct);
        request.setAttribute("warrantyProductId", warrantyProductId);
        request.setAttribute("staffList", technicians);
        PrintWriter out = response.getWriter();

        //out.println("<p>Warranty Product ID: " + warrantyProductId + "</p>");
        request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WarrantyCardDAO d = new WarrantyCardDAO();
        String type = request.getParameter("type");
        if (type.equalsIgnoreCase("display")) {
            doGet(request, response);
        } else {
            try {
                // Nhận dữ liệu từ form
                int warrantyProductId = Integer.parseInt(request.getParameter("warrantyProductId"));
                String warrantyCardCode = request.getParameter("warrantyCardCode");
                int handlerId = Integer.parseInt(request.getParameter("staffId"));
                String issueDescription = request.getParameter("issueDescription");
                String warrantyStatus = request.getParameter("warrantyStatus");
                String createDate = request.getParameter("createDate");
                String returnDate = request.getParameter("returnDate");
                String doneDate = request.getParameter("doneDate");
                String completeDate = request.getParameter("completeDate");
                String cancelDate = request.getParameter("cancelDate");
                Part imagePart = request.getPart("warrantyImage");

                // Kiểm tra định dạng ảnh
                String fileName = imagePart.getSubmittedFileName();
                if (fileName == null || fileName.isEmpty()) {

                    doGet(request, response);
                    return;
                }

                String lowerCaseFileName = fileName.toLowerCase();
                if (!(lowerCaseFileName.endsWith(".jpg") || lowerCaseFileName.endsWith(".jpeg") || lowerCaseFileName.endsWith(".png"))) {

                    doGet(request, response);
                    return;
                }

//                // Lưu ảnh nếu hợp lệ
                String imagePath = OtherUtils.saveImage(imagePart, request, "img/photos");
//                PrintWriter out = response.getWriter();
//                out.println("<p>Warranty Product ID: " + warrantyProductId + "</p>");
//                out.println("<p>Warranty Card Code: " + warrantyCardCode + "</p>");
//                out.println("<p>Handler ID: " + handlerId + "</p>");
//                out.println("<p>Issue Description: " + issueDescription + "</p>");
//                out.println("<p>Warranty Status: " + warrantyStatus + "</p>");
//                out.println("<p>Create Date: " + createDate + "</p>");
//                out.println("<p>Return Date: " + returnDate + "</p>");
//                out.println("<p>Done Date: " + doneDate + "</p>");
//                out.println("<p>Complete Date: " + completeDate + "</p>");
//                out.println("<p>Cancel Date: " + cancelDate + "</p>");
//                out.println(imagePath);

                // Lưu vào DB
                boolean isAdded = d.createWarrantyCard(handlerId,
                        warrantyProductId,
                        warrantyCardCode,
                        issueDescription,
                        warrantyStatus, returnDate, doneDate, completeDate, cancelDate, createDate, imagePath);

                if (isAdded) {
                    response.sendRedirect("HomePage.jsp");
                } else {
                    request.setAttribute("errorMessage", "Failed to create warranty card.");
                    request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);
                }
            } catch (ServletException | IOException | NumberFormatException e) {
                request.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
                request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);
            }
        }
    }

}
