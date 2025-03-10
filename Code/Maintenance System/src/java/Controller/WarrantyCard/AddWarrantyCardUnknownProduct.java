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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.PrintWriter;

@MultipartConfig(
        fileSizeThreshold = 2 * 1024 * 1024, // 2MB
        maxFileSize = 10 * 1024 * 1024, // 10MB
        maxRequestSize = 50 * 1024 * 1024 // 50MB
)
public class AddWarrantyCardUnknownProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Nếu cần, xử lý cho GET request
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
        try {
            // Lấy dữ liệu từ form
            String customerIdStr = request.getParameter("customerId");
            String productIdStr = request.getParameter("productId");
            int warrantyProductId = Integer.parseInt(request.getParameter("warrantyProductId"));
            int handlerId = Integer.parseInt(request.getParameter("staffId"));
            String issueDescription = request.getParameter("issueDescription");
            String warrantyStatus = request.getParameter("warrantyStatus");
            String returnDate = request.getParameter("returnDate");
            String doneDate = request.getParameter("doneDate");
            String completeDate = request.getParameter("completeDate");
            String cancelDate = request.getParameter("cancelDate");

            CustomerDAO customerDAO = new CustomerDAO();
            ProductDAO productDAO = new ProductDAO();
            StaffDAO staffDAO = new StaffDAO();
            Customer customer = customerDAO.getCustomerByID(Integer.parseInt(customerIdStr));
            UnknownProduct unknownProduct = productDAO.getUnknownProductById(Integer.parseInt(productIdStr));
            List<Staff> technicians = staffDAO.getAllTechnicians();

            // Lưu các dữ liệu form vào request để hiển thị lại khi có lỗi
            request.setAttribute("warrantyProductId", warrantyProductId);
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

            // Xử lý các file media được upload
            Collection<Part> parts = request.getParts();
            List<Part> mediaParts = new ArrayList<>();
            for (Part part : parts) {
                if (part.getName().equals("warrantyMedia") && part.getSize() > 0) {
                    mediaParts.add(part);
                }
            }
            if (mediaParts.isEmpty()) {
                request.setAttribute("errorMessage", "Please select at least one media file.");
                request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);
                return;
            }

            PrintWriter out = response.getWriter();
            out.println("Customer ID: " + customerIdStr);
            out.println("Product ID: " + productIdStr);
            out.println("Warranty Product ID: " + warrantyProductId);
            out.println("Handler ID: " + handlerId);
            out.println("Issue Description: " + issueDescription);
            out.println("Warranty Status: " + warrantyStatus);
            out.println("Return Date: " + returnDate);
            out.println("Done Date: " + doneDate);
            out.println("Complete Date: " + completeDate);
            out.println("Cancel Date: " + cancelDate);

            // Tạo Warranty Card và lấy ID vừa tạo (giả sử createWarrantyCard trả về ID hoặc -1 nếu thất bại)
            boolean add = warrantyCardDAO.createWarrantyCard(handlerId, warrantyProductId, issueDescription, warrantyStatus, returnDate, doneDate, completeDate, cancelDate);

            if (!add) {
                request.setAttribute("errorMessage", "Failed to create warranty card. Please try again.");
                request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);
                return;
            }

            int warrantyCardId = warrantyCardDAO.getWarrantyCardID(warrantyProductId);

            // Xác định các định dạng file hợp lệ
            String[] allowedImageExtensions = {".jpg", ".jpeg", ".png"};
            String[] allowedVideoExtensions = {".mp4", ".avi", ".mov", ".wmv"};

            // Xử lý từng file media
            for (Part mediaPart : mediaParts) {
                String fileName = mediaPart.getSubmittedFileName();
                if (fileName == null || fileName.isEmpty()) {
                    continue;
                }
                String lowerCaseFileName = fileName.toLowerCase();
                String mediaType = "";
                boolean isValid = false;
                for (String ext : allowedImageExtensions) {
                    if (lowerCaseFileName.endsWith(ext)) {
                        mediaType = "image";
                        isValid = true;
                        break;
                    }
                }
                if (!isValid) {
                    for (String ext : allowedVideoExtensions) {
                        if (lowerCaseFileName.endsWith(ext)) {
                            mediaType = "video";
                            isValid = true;
                            break;
                        }
                    }
                }
                if (!isValid) {
                    request.setAttribute("errorMessage", "Media file " + fileName + " must be in a valid format (JPG, JPEG, PNG for images; MP4, AVI, MOV, WMV for videos).");
                    request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);
                    return;
                }
                // Lưu file media và nhận về đường dẫn file
                String mediaPath = OtherUtils.saveImage(mediaPart, request, "img/photos");
                // Thêm bản ghi vào bảng Media với ObjectType là "WarrantyCard"
                boolean mediaAdded = warrantyCardDAO.addMedia(warrantyCardId, "WarrantyCard", mediaPath, mediaType);
                if (!mediaAdded) {
                    request.setAttribute("errorMessage", "Failed to add media: " + fileName);
                    request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);
                    return;
                }
            }

            response.sendRedirect("listUnknown");

        } catch (ServletException | IOException | NumberFormatException e) {
//            request.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
//            request.getRequestDispatcher("addWarrantyCardUnknownProduct.jsp").forward(request, response);
        }
    }
}
