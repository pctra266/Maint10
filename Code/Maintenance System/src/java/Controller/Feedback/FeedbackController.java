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
import Model.Pagination;
import Model.ProductDetail;
import Utils.FormatUtils;
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
    private static final int PAGE_SIZE = 5;
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
//        if(currentCustomer == null){
//            response.sendRedirect("LoginForm.jsp");
//            return ;
//        }
        
        try {
            currentStaff = (Staff) session.getAttribute("staff");
        } catch (Exception e) {
        }
        String staffId = "1";
        if (currentStaff != null) {
            staffId = String.valueOf(currentStaff.getStaffID());
        }
        
        // end session customer
        String action = request.getParameter("action");
        if (action == null) {
            action = "viewFeedback";
        }
        
        //lay tham so de phan trang
        int total1 = 0;
        switch(action){
            case "viewListFeedbackByCustomerId": 
                total1 = daoFeedback.totalFeedbackByCustomerId(customerId);
                break;
            case "viewFeedbackDashboard":
                total1 = productDAO.totalProductByCustomerId(customerId);
                break;
        }
        Pagination pagination = new Pagination();
        String pageParam = request.getParameter("page");
                String pageSizeParam = request.getParameter("page-size");
                int page = (FormatUtils.tryParseInt(pageParam) != null) ? FormatUtils.tryParseInt(pageParam) : 1;
                 Integer pageSize;
                pageSize = (FormatUtils.tryParseInt(pageSizeParam) != null) ? FormatUtils.tryParseInt(pageSizeParam) : PAGE_SIZE;
                int totalPages1 = (int) Math.ceil((double) total1 / pageSize);
                if (page > totalPages1) {
                    page = totalPages1;
                }
                page = page < 1 ? 1 : page;
                System.out.println("Page hien tai: "+ page);
                System.out.println("page size hien tai: " + pageSize);
        // end thay tham so de phan trang
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
                ArrayList<Feedback> listFeedbackByCustomerId = daoFeedback.getListFeedbackByCustomerId(customerId,page,pageSize);
                pagination.setListPageSize(total1); // total select count(*)
                pagination.setCurrentPage(page);
                pagination.setTotalPages(totalPages1);
                pagination.setTotalPagesToShow(5); // thich hien thi bao nhieu thi hien
                pagination.setPageSize(pageSize);
                pagination.setSort("");
                pagination.setOrder("");
                pagination.setUrlPattern("/feedback");
                pagination.setSearchFields(new String[]{"action"});
                pagination.setSearchValues(new String[]{"viewListFeedbackByCustomerId"});
                request.setAttribute("pagination", pagination);
                request.setAttribute("listFeedbackByCustomerId", listFeedbackByCustomerId);
                request.getRequestDispatcher("viewListFeedbackByCustomerId.jsp").forward(request, response);
                break;
            case "viewFeedbackDashboard":
                ArrayList<ProductDetail> listProductCreateFeedback = productDAO.getListProductByCustomerID(customerId,page,pageSize);
                request.setAttribute("listProductCreateFeedback", listProductCreateFeedback);
                pagination.setListPageSize(total1); // total select count(*)
                pagination.setCurrentPage(page);
                pagination.setTotalPages(totalPages1);
                pagination.setTotalPagesToShow(5); // thich hien thi bao nhieu thi hien
                pagination.setPageSize(pageSize);
                pagination.setSort("");
                pagination.setOrder("");
                pagination.setUrlPattern("/feedback");
                pagination.setSearchFields(new String[]{"action"});
                pagination.setSearchValues(new String[]{"viewFeedbackDashboard"});
                request.setAttribute("pagination", pagination);
                request.getRequestDispatcher("feedbackDashboard.jsp").forward(request, response);
                break;
            case "deleteFeedback":
                String feedbackIdDelete = request.getParameter("feedbackID");
                daoFeedback.inActiveFeedbackById(feedbackIdDelete);
                daoFeedbackLog.createDeleteFeedbackLog(daoFeedback.getFeedbackById(feedbackIdDelete), staffId);
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
                String warrantyCardID = request.getParameter("warrantyCardID");
                request.setAttribute("warrantyCardID", warrantyCardID);
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
        Integer maxUploadSizeImageMB = (Integer) request.getServletContext().getAttribute("maxUploadSizeImageMB");

    // Nếu maxSizeMB chưa có, đặt giá trị mặc định 5MB
    if (maxUploadSizeImageMB == null) {
        maxUploadSizeImageMB = 5; // Giá trị mặc định
        request.getServletContext().setAttribute("maxUploadSizeImageMB", maxUploadSizeImageMB);
    }
        Integer maxUploadSizeVideoMB = (Integer) request.getServletContext().getAttribute("maxUploadSizeVideoMB");
    if (maxUploadSizeVideoMB == null) {
        maxUploadSizeVideoMB = 50; // Giá trị mặc định 50MB
        request.getServletContext().setAttribute("maxUploadSizeVideoMB", maxUploadSizeVideoMB);
    }
   
        ProductDAO productDAO = new ProductDAO();
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
                String noteCreate = SearchUtils.preprocessSearchQuery(request.getParameter("note"));
                String warrantyCardId = request.getParameter("warrantyCardId");
                Part imagePart = request.getPart("imageURL");
                Part videoPart = request.getPart("videoURL");
                String mess = "";
                String imageURL = "";
                boolean valid = true;
                if(imagePart != null){
                    String imagePath = Utils.OtherUtils.saveImage(imagePart, request, "img/Feedback");
                    if (imagePath == null) {
                        } else if (imagePath.equalsIgnoreCase("Invalid picture")) {
                            valid = false;
                            request.setAttribute("pictureAlert", "Invalid picture");
                        }else{
                            if(imagePath.startsWith("File is too large")){
                            valid = false;
                            request.setAttribute("pictureAlert", "Picture too large, max size is:"+maxUploadSizeImageMB+" MB");
                        }
                    }
                    imageURL = imagePath;
                   
                }
                System.out.println("image path la : " + imageURL);
                String videoURL = "";
                if(videoPart != null){
                    String videoPath = Utils.OtherUtils.saveVideo(videoPart, request,"video/Feedback");
                    if (videoPath == null) {
                        } else if (videoPath.equalsIgnoreCase("Invalid video")) {
                            valid = false;
                            request.setAttribute("videoAlert", "Invalid video");
                        }else{
                            if(videoPath.startsWith("File is too large")){
                            valid = false;
                            request.setAttribute("videoAlert", "Picture too large, max size is:"+maxUploadSizeVideoMB+" MB");
                        }
                    }
                    videoURL = videoPath;
                }
                System.out.println("video path la : " + videoURL);
                
                // valid
                if(noteCreate == null || noteCreate.trim().isEmpty()){
                    valid = false;
                     mess = "You need fill feedback note";
                }
                
               
                if(valid){
                    daoFeedback.createFeedback(customerId, warrantyCardId, noteCreate, imageURL, videoURL);
                    mess = "Create successfully";
                    response.sendRedirect("feedback?action=viewFeedbackDashboard&mess="+mess);
                }else{
                    ArrayList<ProductDetail> listProductByCustomerId = productDAO.getListProductByCustomerID(customerId);
                    request.setAttribute("listProductByCustomerId", listProductByCustomerId);
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("createFeedback.jsp").forward(request, response);
                }
                break;
            default:
                break;
        }

    }
// Lưu ảnh vào thư mục /img/Component
    private String saveMedia(Part mediaPart, HttpServletRequest request) throws IOException {
        if (mediaPart == null || mediaPart.getSize() == 0) {
            return null;
        }
        
        String contentType = mediaPart.getContentType();
        String folder = "";
        if (contentType.startsWith("image/")) {
            folder = "img/Feedback/";
        } else if (contentType.startsWith("video/")) {
            folder = "video/Feedback/";
        } else {
            return null; // Không phải ảnh hoặc video
        }
        
        // Đường dẫn tuyệt đối
        String uploadPath = request.getServletContext().getRealPath("/" + folder);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Xử lý tên file
        String originalFileName = mediaPart.getSubmittedFileName();
        if (originalFileName == null || originalFileName.isEmpty()) {
            return null;
        }
        
        String fileName = System.currentTimeMillis() + "_" + originalFileName;
        String filePath = uploadPath + File.separator + fileName;

        try {
            mediaPart.write(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return folder + fileName;
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
