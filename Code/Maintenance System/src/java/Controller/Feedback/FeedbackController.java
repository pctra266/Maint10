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
        String customerName = SearchUtils.preprocessSearchQuery(request.getParameter("customerName"));
        String customerEmail = SearchUtils.searchValidateNonSapce(request.getParameter("customerEmail"));
        String customerPhone = SearchUtils.searchValidateNonSapce(request.getParameter("customerPhone"));
        String imageAndVideo = request.getParameter("imageAndVideo");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String warrantyCardCode = SearchUtils.searchValidateNonSapce(request.getParameter("WarrantyCardCode"));
        String warrantyStatus = SearchUtils.preprocessSearchQuery(request.getParameter("WarrantyStatus"));
        String typeMaintain = request.getParameter("typeMaintain");
        request.setAttribute("typeMaintain", typeMaintain);
        request.setAttribute("WarrantyStatus", warrantyStatus);
        request.setAttribute("WarrantyCardCode", warrantyCardCode);
        request.setAttribute("customerName", customerName);
        request.setAttribute("imageAndVideo", imageAndVideo);
        request.setAttribute("sort", sort);
        request.setAttribute("sortOrder", order);
        request.setAttribute("customerEmail", customerEmail);
        request.setAttribute("customerPhone", customerPhone);
        int total1 = 0;
        switch (action) {
            case "viewListFeedbackByCustomerId":
                total1 = daoFeedback.totalFeedbackByCustomerId(customerId);
                break;
            case "viewFeedbackDashboard":
                total1 = daoFeedback.totalProductByCustomerId(customerId,warrantyCardCode,warrantyStatus,typeMaintain);
                break;
            case "viewFeedback":
                total1 = daoFeedback.getTotalFeedback(customerName, customerEmail, customerPhone, imageAndVideo);
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
        System.out.println("Page hien tai: " + page);
        System.out.println("page size hien tai: " + pageSize);
        // end thay tham so de phan trang
        switch (action) {
            case "viewFeedback":
                //======phan trang
                pagination.setListPageSize(total1); 
                pagination.setCurrentPage(page);
                pagination.setTotalPages(totalPages1);
                pagination.setTotalPagesToShow(5); 
                pagination.setPageSize(pageSize);
                pagination.setSort(sort);
                pagination.setOrder(order);
                pagination.setUrlPattern("/feedback");
                pagination.setSearchFields(new String[]{"action","customerName","imageAndVideo","customerPhone","customerEmail"});
                pagination.setSearchValues(new String[]{"viewFeedback",customerName,imageAndVideo,customerPhone,customerEmail});
                request.setAttribute("pagination", pagination);
                //======end phan trang
                ArrayList<Feedback> listFeedback = daoFeedback.getAllFeedback(customerName, customerEmail, customerPhone, imageAndVideo, page,pageSize, sort, order);
                request.setAttribute("listFeedback", listFeedback);
                request.getRequestDispatcher("viewListFeedback.jsp").forward(request, response);
                break;
            case "viewListFeedbackByCustomerId":
                ArrayList<Feedback> listFeedbackByCustomerId = daoFeedback.getListFeedbackByCustomerId(customerId, page, pageSize);
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
                ArrayList<ProductDetail> listProductCreateFeedback = daoFeedback.getListProductByCustomerID(customerId,warrantyCardCode,warrantyStatus,typeMaintain, page, pageSize);
                request.setAttribute("listProductCreateFeedback", listProductCreateFeedback);
                pagination.setListPageSize(total1); // total select count(*)
                pagination.setCurrentPage(page);
                pagination.setTotalPages(totalPages1);
                pagination.setTotalPagesToShow(5); // thich hien thi bao nhieu thi hien
                pagination.setPageSize(pageSize);
                pagination.setSort("");
                pagination.setOrder("");
                pagination.setUrlPattern("/feedback");
                pagination.setSearchFields(new String[]{"action","warrantyCardCode","WarrantyStatus","typeMaintain"});
                pagination.setSearchValues(new String[]{"viewFeedbackDashboard",warrantyCardCode,warrantyStatus,typeMaintain});
                request.setAttribute("pagination", pagination);
                request.getRequestDispatcher("feedbackDashboard.jsp").forward(request, response);
                break;
            case "deleteFeedback":
                //======phan trang
                pagination.setListPageSize(total1); 
                pagination.setCurrentPage(page);
                pagination.setTotalPages(totalPages1);
                pagination.setTotalPagesToShow(5); 
                pagination.setPageSize(pageSize);
                pagination.setSort(sort);
                pagination.setOrder(order);
                pagination.setUrlPattern("/feedback");
                pagination.setSearchFields(new String[]{"action","customerName","imageAndVideo","customerPhone","customerEmail"});
                pagination.setSearchValues(new String[]{"viewFeedback",customerName,imageAndVideo,customerPhone,customerEmail});
                request.setAttribute("pagination", pagination);

                //======end phan trang
                String feedbackIdDelete = request.getParameter("feedbackID");
                daoFeedback.inActiveFeedbackById(feedbackIdDelete);
                daoFeedbackLog.createDeleteFeedbackLog(daoFeedback.getFeedbackById(feedbackIdDelete), staffId);
                
                ArrayList<Feedback> listFeedbackAfterDelete = daoFeedback.getAllFeedback(customerName, customerEmail, customerPhone, imageAndVideo, page,pageSize, sort, order);
                request.setAttribute("listFeedback", listFeedbackAfterDelete);
                request.getRequestDispatcher("viewListFeedback.jsp").forward(request, response);
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
                ArrayList<ProductDetail> listProductByCustomerId = daoFeedback.getListProductByCustomerID(customerId);
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
            maxUploadSizeVideoMB = 10; // Giá trị mặc định 50MB
            request.getServletContext().setAttribute("maxUploadSizeVideoMB", maxUploadSizeVideoMB);
        }
        String feedbackIdDelete = request.getParameter("feedbackID");
        String customerName = SearchUtils.preprocessSearchQuery(request.getParameter("customerName"));
        String customerEmail = SearchUtils.searchValidateNonSapce(request.getParameter("customerEmail"));
        String customerPhone = SearchUtils.searchValidateNonSapce(request.getParameter("customerPhone"));
        String imageAndVideo = request.getParameter("imageAndVideo");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String typeMaintain = request.getParameter("typeMaintain");
        request.setAttribute("typeMaintain", typeMaintain);
        request.setAttribute("customerName", customerName);
        request.setAttribute("imageAndVideo", imageAndVideo);
        request.setAttribute("sort", sort);
        request.setAttribute("sortOrder", order);
        request.setAttribute("customerEmail", customerEmail);
        request.setAttribute("customerPhone", customerPhone);
        FeedbackDAO daoFeedback = new FeedbackDAO();
        FeedbackLogDAO daoFeedbackLog = new FeedbackLogDAO();
        String action = request.getParameter("action");
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
        if (action == null) {
            action = "viewListFeedback";
        }
        int total1 = 0;
        switch (action) {
            case "deleteFeedback":
                total1 = daoFeedback.getTotalFeedback(customerName, customerEmail, customerPhone, imageAndVideo);
                if(feedbackIdDelete != null && !feedbackIdDelete.trim().isEmpty()){
                   total1--;
                }
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
        switch (action) {
            case "viewListFeedback":
//                String customerName = SearchUtils.preprocessSearchQuery(request.getParameter("customerName"));
//                String imageAndVideo = request.getParameter("imageAndVideo");
//                String column = request.getParameter("column");
//                String sortOrder = request.getParameter("sortOrder");
//                String customerEmail = SearchUtils.searchValidateNonSapce(request.getParameter("customerEmail"));
//                String customerPhone = SearchUtils.searchValidateNonSapce(request.getParameter("customerPhone"));
//                request.setAttribute("customerName", customerName);
//                request.setAttribute("imageAndVideo", imageAndVideo);
//                request.setAttribute("column", column);
//                request.setAttribute("sortOrder", sortOrder);
//                request.setAttribute("customerEmail", customerEmail);
//                request.setAttribute("customerPhone", customerPhone);
//                //phan trang
//                int totalPages = daoFeedback.getTotalFeedback(customerName, customerEmail, customerPhone, imageAndVideo);
//                int endPage = totalPages / 7;
//                if (totalPages % 7 != 0) {
//                    endPage++;
//                }
//                request.setAttribute("endPage", endPage);
//                String indexStr = request.getParameter("index");
//
//                int index = 1;
//                try {
//                    index = Integer.parseInt(indexStr);
//                    if (index == 0) {
//                        index = 1;
//                    }
//                } catch (Exception e) {
//                }
//
//                if (endPage < index && endPage != 0) {
//                    index = endPage;
//                }
//                request.setAttribute("index", index);
//                ArrayList<Feedback> listFeedback = daoFeedback.getAllFeedback(customerName, customerEmail, customerPhone, imageAndVideo, index, column, sortOrder);
//                request.setAttribute("listFeedback", listFeedback);
//                request.getRequestDispatcher("viewListFeedback.jsp").forward(request, response);
                response.sendRedirect("feedback");
               
                 return ;
                
            case "updateFeedback":

                String note = request.getParameter("note");
                String feedbackId = request.getParameter("feedbackId");
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
                String warrantyCardID = request.getParameter("warrantyCardID");
                request.setAttribute("warrantyCardID", warrantyCardID);
                
                Part imagePart = request.getPart("imageURL");
                Part videoPart = request.getPart("videoURL");
                String mess = "";
                String imageURL = "";
                boolean valid = true;
                if (imagePart != null) {
                    String imagePath = Utils.OtherUtils.saveImage(imagePart, request, "img/Feedback");
                    if (imagePath == null) {
                    } else if (imagePath.equalsIgnoreCase("Invalid picture")) {
                        valid = false;
                        request.setAttribute("pictureAlert", "Invalid picture");
                    } else {
                        if (imagePath.startsWith("File is too large")) {
                            valid = false;
                            request.setAttribute("pictureAlert", "Picture too large, max size is:" + maxUploadSizeImageMB + " MB");
                        }
                    }
                    imageURL = imagePath;

                }
                System.out.println("image path la : " + imageURL);
                String videoURL = "";
                if (videoPart != null) {
                    String videoPath = Utils.OtherUtils.saveVideo(videoPart, request, "video/Feedback");
                    if (videoPath == null) {
                    } else if (videoPath.equalsIgnoreCase("Invalid video")) {
                        valid = false;
                        request.setAttribute("videoAlert", "Invalid video");
                    } else {
                        if (videoPath.startsWith("File is too large")) {
                            valid = false;
                            request.setAttribute("videoAlert", "Video too large, max size is:" + maxUploadSizeVideoMB + " MB");
                        }
                    }
                    videoURL = videoPath;
                }
                System.out.println("video path la : " + videoURL);

                // valid
                
                if (noteCreate == null || noteCreate.trim().isEmpty()) {
                    valid = false;
                    mess = "You need fill feedback note";
                }else{
                    request.setAttribute("note", noteCreate);
                }
                
                

                if (valid) {
                    daoFeedback.createFeedback(customerId, warrantyCardID, noteCreate, imageURL, videoURL);
                    mess = "Create successfully";
                    response.sendRedirect("feedback?action=viewFeedbackDashboard&mess=" + mess);
                } else {
                    ArrayList<ProductDetail> listProductByCustomerId = daoFeedback.getListProductByCustomerID(customerId);
                    request.setAttribute("listProductByCustomerId", listProductByCustomerId);
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("createFeedback.jsp").forward(request, response);
                }
                break;
            case "deleteFeedback":
                //======phan trang
                pagination.setListPageSize(total1); 
                pagination.setCurrentPage(page);
                pagination.setTotalPages(totalPages1);
                pagination.setTotalPagesToShow(5); 
                pagination.setPageSize(pageSize);
                pagination.setSort(sort);
                pagination.setOrder(order);
                pagination.setUrlPattern("/feedback");
                pagination.setSearchFields(new String[]{"action","customerName","imageAndVideo","customerPhone","customerEmail"});
                pagination.setSearchValues(new String[]{"viewFeedback",customerName,imageAndVideo,customerPhone,customerEmail});
                request.setAttribute("pagination", pagination);
                //======end phan trang
                
                daoFeedback.inActiveFeedbackById(feedbackIdDelete);
                daoFeedbackLog.createDeleteFeedbackLog(daoFeedback.getFeedbackById(feedbackIdDelete), staffId);
                
                ArrayList<Feedback> listFeedbackAfterDelete = daoFeedback.getAllFeedback(customerName, customerEmail, customerPhone, imageAndVideo, page,pageSize, sort, order);
                request.setAttribute("listFeedback", listFeedbackAfterDelete);
                request.getRequestDispatcher("viewListFeedback.jsp").forward(request, response);
                break;
            default:response.sendRedirect("feedback");
                break;
        }

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
