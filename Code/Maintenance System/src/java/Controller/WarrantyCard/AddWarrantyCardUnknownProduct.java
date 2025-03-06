package Controller.WarrantyCard;

import DAO.CustomerDAO;
import DAO.ProductDAO;
import DAO.StaffDAO;
import DAO.WarrantyCardDAO;
import Model.Customer;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WarrantyCardDAO d = new WarrantyCardDAO();

        try {
            // Retrieve form data
            String customerId = request.getParameter("customerId");
            String productId = request.getParameter("productId");
            int warrantyProductId = Integer.parseInt(request.getParameter("warrantyProductId"));
            String warrantyCardCode = request.getParameter("warrantyCardCode");
            int handlerId = Integer.parseInt(request.getParameter("staffId"));
            String issueDescription = request.getParameter("issueDescription");
            String warrantyStatus = request.getParameter("warrantyStatus");
            String returnDate = request.getParameter("returnDate");
            String doneDate = request.getParameter("doneDate");
            String completeDate = request.getParameter("completeDate");
            String cancelDate = request.getParameter("cancelDate");
            Part imagePart = request.getPart("warrantyImage");

            CustomerDAO customerDAO = new CustomerDAO();
            ProductDAO productDAO = new ProductDAO();
            StaffDAO staffDAO = new StaffDAO();
            Customer customer = customerDAO.getCustomerByID(Integer.parseInt(customerId));
            UnknownProduct unknownProduct = productDAO.getUnknownProductById(Integer.parseInt(productId));
            List<Staff> technicians = staffDAO.getAllTechnicians();

            // Store form data in request attributes
            
            request.setAttribute("warrantyProductId", warrantyProductId);
            request.setAttribute("warrantyCardCode", warrantyCardCode);
            request.setAttribute("staffId", handlerId);
            request.setAttribute("issueDescription", issueDescription);
            request.setAttribute("warrantyStatus", warrantyStatus);
            
            request.setAttribute("returnDate", returnDate);
            request.setAttribute("doneDate", doneDate);
            request.setAttribute("completeDate", completeDate);
            request.setAttribute("cancelDate", cancelDate);
            
            request.setAttribute("customer", customer);
            request.setAttribute("unknownProduct", unknownProduct);
            request.setAttribute("staffList", technicians);

            // Validate image format
            String fileName = imagePart.getSubmittedFileName();
            if (fileName == null || fileName.isEmpty()) {
                request.setAttribute("errorMessage", "Please select an image file.");
                request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);
                return;
            }

            String lowerCaseFileName = fileName.toLowerCase();
            if (!(lowerCaseFileName.endsWith(".jpg") || lowerCaseFileName.endsWith(".jpeg") || lowerCaseFileName.endsWith(".png"))) {
                request.setAttribute("errorMessage", "Image must be in JPG, JPEG, or PNG format.");
                request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);
                return;
            }

            String imagePath = OtherUtils.saveImage(imagePart, request, "img/photos");
            boolean isAdded = d.createWarrantyCard(handlerId, warrantyProductId, warrantyCardCode,
                    issueDescription, warrantyStatus,
                    returnDate, doneDate, completeDate, cancelDate, imagePath);

            if (isAdded) {
                response.sendRedirect("listUnknown"); // Success: Redirect to list page
            } else {
                request.setAttribute("errorMessage", "Failed to create warranty card. Please try again.");
                request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);
            }

        } catch (ServletException | IOException | NumberFormatException e) {
            request.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
            request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);
        }
    }
}
