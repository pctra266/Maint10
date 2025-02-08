/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Feedback;

import Utils.SearchUtils;
import DAO.FeedbackDAO;
import DAO.FeedbackLogDAO;
import DAO.ProductDAO;
import Model.Customer;
import Model.Staff;
import Model.Feedback;
import Model.FeedbackLog;
import Model.ProductDetail;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Tra Pham
 */
@WebServlet(name = "FeedbackController", urlPatterns = {"/feedback"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class FeedbackController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FeedbackController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FeedbackController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();
        FeedbackDAO daoFeedback = new FeedbackDAO();
        FeedbackLogDAO daoFeedbackLog = new FeedbackLogDAO();
        // session to get customer and customerId
        HttpSession session = request.getSession();
        Customer currentCustomer = null;
        Staff currentStaff = null;
        try {
            currentCustomer = (Customer) session.getAttribute("customer");
        } catch (Exception e) {
        }
        String customerId = "1";
        if (currentCustomer != null) {
            customerId = String.valueOf(currentCustomer.getCustomerID());
        }
        
        try {
            currentStaff = (Staff) session.getAttribute("staff");
        } catch (Exception e) {
        }
        String staffId = "1";
        if (currentStaff != null) {
            staffId = String.valueOf(currentStaff.getStaffID());
        }
        
        System.out.println("Customer ID hien tai la : " + customerId);
        System.out.println("Staff ID hien tai la : " + staffId);
        // end session customer
        String action = request.getParameter("action");
        if (action == null) {
            action = "viewFeedback";
        }
        switch (action) {
            case "viewFeedback":
                String customerName = SearchUtils.preprocessSearchQuery(request.getParameter("customerName"));
                String imageAndVideo = request.getParameter("imageAndVideo");
                String column = request.getParameter("column");
                String sortOrder = request.getParameter("sortOrder");
                String customerEmail = SearchUtils.searchValidateNonSapce(request.getParameter("customerEmail"));
                String customerPhone = SearchUtils.searchValidateNonSapce(request.getParameter("customerPhone"));
                request.setAttribute("customerName", customerName);
                request.setAttribute("imageAndVideo", imageAndVideo);
                request.setAttribute("column", column);
                request.setAttribute("sortOrder", sortOrder);
                request.setAttribute("customerEmail", customerEmail);
                request.setAttribute("customerPhone", customerPhone);
                //======phan trang
                int totalPages = daoFeedback.getTotalFeedback(customerName, customerEmail, customerPhone, imageAndVideo);
                int endPage = totalPages / 7;
                if (totalPages % 7 != 0) {
                    endPage++;
                }
                request.setAttribute("endPage", endPage);
                String indexStr = request.getParameter("index");
                int index = 1;
                try {
                    index = Integer.parseInt(indexStr);
                    if (index == 0) {
                        index = 1;
                    }
                } catch (Exception e) {

                }
                if (endPage < index && endPage != 0) {
                    index = endPage;
                }
                request.setAttribute("index", index);
                ArrayList<Feedback> listFeedback = daoFeedback.getAllFeedback(customerName, customerEmail, customerPhone, imageAndVideo, index, column, sortOrder);

                //======end phan trang
                request.setAttribute("listFeedback", listFeedback);
                request.getRequestDispatcher("viewListFeedback.jsp").forward(request, response);
                break;
            case "viewListFeedbackByCustomerId":
                ArrayList<Feedback> listFeedbackByCustomerId = daoFeedback.getListFeedbackByCustomerId(customerId);
                request.setAttribute("listFeedbackByCustomerId", listFeedbackByCustomerId);
                request.getRequestDispatcher("feedbackDashboard.jsp").forward(request, response);
                break;
            case "deleteFeedback":
                String feedbackIdDelete = request.getParameter("feedbackID");
                daoFeedback.inActiveFeedbackById(feedbackIdDelete);
                daoFeedbackLog.createDeleteFeedbackLog(daoFeedback.getFeedbackById(feedbackIdDelete), staffId);
                // chuyen sang view action
                response.sendRedirect("feedback");
                break;
            case "deleteFeedbackFromCustomer":
                String feedbackIdDeleteFromCustomer = request.getParameter("feedbackIdDeleteFromCustomer");
                daoFeedback.deleteFeedbackById(feedbackIdDeleteFromCustomer);
                request.getRequestDispatcher("feedback?action=viewListFeedbackByCustomerId").forward(request, response);
                break;
            case "updateFeedback":
                String feedbackIdUpdate = request.getParameter("feedbackID");
                Feedback feedbackUpdate = daoFeedback.getFeedbackById(feedbackIdUpdate);
                request.setAttribute("feedbackUpdate", feedbackUpdate);
                request.getRequestDispatcher("updateFeedback.jsp").forward(request, response);
                break;
            case "createFeedback":
                ArrayList<ProductDetail> listProductByCustomerId = productDAO.getListProductByCustomerID(customerId);
                request.setAttribute("listProductByCustomerId", listProductByCustomerId);
                request.getRequestDispatcher("createFeedback.jsp").forward(request, response);
                break;
            default:
                break;
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FeedbackDAO daoFeedback = new FeedbackDAO();
        FeedbackLogDAO daoFeedbackLog = new FeedbackLogDAO();
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Customer currentCustomer = null;
        try {
            currentCustomer = (Customer) session.getAttribute("customer");
        } catch (Exception e) {
        }
        String customerId = "1";
        if (currentCustomer != null) {
            customerId = String.valueOf(currentCustomer.getCustomerID());
        }
        System.out.println("Customer ID hien tai la : " + customerId);
        if (action == null) {
            action = "viewListFeedback";
        }
        switch (action) {
            case "viewListFeedback":
                String customerName = SearchUtils.preprocessSearchQuery(request.getParameter("customerName"));
                String imageAndVideo = request.getParameter("imageAndVideo");
                String column = request.getParameter("column");
                String sortOrder = request.getParameter("sortOrder");
                String customerEmail = SearchUtils.searchValidateNonSapce(request.getParameter("customerEmail"));
                String customerPhone = SearchUtils.searchValidateNonSapce(request.getParameter("customerPhone"));
                request.setAttribute("customerName", customerName);
                request.setAttribute("imageAndVideo", imageAndVideo);
                request.setAttribute("column", column);
                request.setAttribute("sortOrder", sortOrder);
                request.setAttribute("customerEmail", customerEmail);
                request.setAttribute("customerPhone", customerPhone);
                //phan trang
                int totalPages = daoFeedback.getTotalFeedback(customerName, customerEmail, customerPhone, imageAndVideo);
                int endPage = totalPages / 7;
                if (totalPages % 7 != 0) {
                    endPage++;
                }
                request.setAttribute("endPage", endPage);
                String indexStr = request.getParameter("index");

                int index = 1;
                try {
                    index = Integer.parseInt(indexStr);
                    if (index == 0) {
                        index = 1;
                    }
                } catch (Exception e) {
                }

                if (endPage < index && endPage != 0) {
                    index = endPage;
                }
                request.setAttribute("index", index);
                ArrayList<Feedback> listFeedback = daoFeedback.getAllFeedback(customerName, customerEmail, customerPhone, imageAndVideo, index, column, sortOrder);
                request.setAttribute("listFeedback", listFeedback);
                request.getRequestDispatcher("viewListFeedback.jsp").forward(request, response);
                break;
            case "updateFeedback":

                String note = request.getParameter("note");
                String feedbackId = request.getParameter("feedbackId");
                String staffId = "1";
                //save history change to feedback log
                // neu note giong nhu cu thi khong doi
                Feedback oldFeedback = daoFeedback.getFeedbackById(feedbackId);
                if (!note.trim().equals(oldFeedback.getNote())) {
                    daoFeedbackLog.createUpdateFeedbackLog(oldFeedback, staffId, note);
                }
                //
                daoFeedback.updateFeedback(feedbackId, note);
                response.sendRedirect("feedback");
                break;
            case "createFeedback":
                String noteCreate = request.getParameter("note");
                String warrantyCardId = request.getParameter("warrantyCardId");
                Part imagePart = request.getPart("imageURL");
                String imageURL = "";
                if(imagePart != null){
                    String imagePath = saveImage(imagePart, request);
                    imageURL = imagePath;
                }
                System.out.println("image path la : " + imagePart);
                String videoURL = "";
                daoFeedback.createFeedback(customerId, warrantyCardId, noteCreate, imageURL, videoURL);
                response.sendRedirect("feedback?action=viewListFeedbackByCustomerId");
                break;
            default:
                break;
        }

    }
// Lưu ảnh vào thư mục /img/Component
    private String saveImage(Part imagePart, HttpServletRequest request) throws IOException {
        if (imagePart == null || imagePart.getSize() == 0) {
            return null;
        }

        // Đường dẫn tuyệt đối đến thư mục img/Component
        String uploadPath = request.getServletContext().getRealPath("/img/Feedback");
        System.out.println("Upload Path: " + uploadPath); // Kiểm tra đường dẫn
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Tạo thư mục nếu chưa tồn tại
        }

        // Tạo tên file duy nhất
        String originalFileName = imagePart.getSubmittedFileName();
        if (originalFileName == null || originalFileName.isEmpty()) {
            return null; // Trả về null nếu không có tên file
        }
        String fileName = originalFileName;
//    String fileName = System.currentTimeMillis() + "_" + originalFileName;
        String filePath = uploadPath + File.separator + fileName;

        try {
            imagePart.write(filePath); // Ghi file lên server
        } catch (IOException e) {
            e.printStackTrace(); // In ra lỗi nếu có
            return null; // Trả về null nếu có lỗi
        }

        // Trả về đường dẫn tương đối để lưu vào database
        return "img/Feedback/" + fileName; // Chỉ cần đường dẫn tương đối
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
