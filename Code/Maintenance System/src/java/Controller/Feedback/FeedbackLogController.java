/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Feedback;

import DAO.FeedbackDAO;
import DAO.FeedbackLogDAO;
import Model.FeedbackLog;
import Model.Pagination;
import Model.Staff;
import Utils.FormatUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author Tra Pham
 */
@WebServlet(name = "FeedbackLogController", urlPatterns = {"/feedbacklog"})
public class FeedbackLogController extends HttpServlet {
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
            out.println("<title>Servlet FeedbackLogController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FeedbackLogController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Staff currentStaff = null;
                try {
            currentStaff = (Staff) session.getAttribute("staff");
        } catch (Exception e) {
        }
        String staffId = "1";
        if (currentStaff != null) {
            staffId = String.valueOf(currentStaff.getStaffID());
        }
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "viewListFeedbackLog";
        }
                //lay tham so de phan trang
        String actionOfLog = request.getParameter("actionOfLog");
        String feedbackID = request.getParameter("feedbackID");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        request.setAttribute("actionOfLog", actionOfLog);
        request.setAttribute("sort", sort);
        request.setAttribute("order", order);
        request.setAttribute("feedbackID", feedbackID);
        int total1 = 0;
        
        switch(action){
            case "viewListFeedbackLog": 
                total1 = daoFeedbackLog.getTotalFeedbackLog(actionOfLog,feedbackID);
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
        // end thay tham so de phan trang
        switch (action) {
            case "viewListFeedbackLog":
                // phan trang moi
                pagination.setListPageSize(total1); // total select count(*)
                pagination.setCurrentPage(page);
                pagination.setTotalPages(totalPages1);
                pagination.setTotalPagesToShow(5); // thich hien thi bao nhieu thi hien
                pagination.setPageSize(pageSize);
                pagination.setSort(sort);
                pagination.setOrder(order);
                pagination.setUrlPattern("/feedbacklog");
                pagination.setSearchFields(new String[]{"action","feedbackID","actionOfLog"});
                pagination.setSearchValues(new String[]{"viewListFeedbackLog",feedbackID,actionOfLog});
                request.setAttribute("pagination", pagination);
                //end phan trang moi
                
                ArrayList<FeedbackLog> listFeedbackLog = daoFeedbackLog.getAllFeedbackLog(actionOfLog,feedbackID,sort,order,page,pageSize);
                request.setAttribute("listFeedbackLog", listFeedbackLog);
                request.getRequestDispatcher("viewFeedbackLog.jsp").forward(request, response);
                break;
            case "undoFeedback":
                String feedbackLogID = request.getParameter("feedbackLogID");
                String feedbackIdneedActive = String.valueOf(daoFeedbackLog.getFeedbackLogById(feedbackLogID).getFeedbackID());
                daoFeedback.activeFeedbackById(feedbackIdneedActive);
                daoFeedbackLog.deleteFeedbackLogById(feedbackLogID);
                response.sendRedirect("feedbacklog");
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
        FeedbackLogDAO daoFeedbackLog = new FeedbackLogDAO();
        String action = request.getParameter("action");
        if (action == null) {
            action = "viewListFeedbackLog";
        }
        switch (action) {
            case "viewListFeedbackLog":
//                String indexStr = request.getParameter("index");
//                String actionOfLog = request.getParameter("actionOfLog");
//                String feedbackID = request.getParameter("feedbackID");
//                String column = request.getParameter("column");
//                String sortOrder = request.getParameter("sortOrder");
//                request.setAttribute("actionOfLog", actionOfLog);
//                request.setAttribute("column", column);
//                request.setAttribute("sortOrder", sortOrder);
//                request.setAttribute("feedbackID", feedbackID);
//                int index = 1;
//                try {
//                    index = Integer.parseInt(indexStr);
//                    if(index == 0){
//                        index = 1;
//                    }
//                } catch (Exception e) {
//                }
//                int totalPage = daoFeedbackLog.getTotalFeedbackLog(actionOfLog,feedbackID);
//                int endPage = totalPage /7;
//                if(totalPage %7 != 0 ){
//                    endPage ++;
//                }
//                if(endPage < index && endPage != 0){
//                    index = endPage;
//                }
//                request.setAttribute("index", index);
//                request.setAttribute("endPage", endPage);
//                
//                request.setAttribute("actionOfLog", actionOfLog);
//                ArrayList<FeedbackLog> listFeedbackLog = daoFeedbackLog.getAllFeedbackLog(actionOfLog,feedbackID,column,sortOrder,index);
//                request.setAttribute("listFeedbackLog", listFeedbackLog);
//                request.getRequestDispatcher("viewFeedbackLog.jsp").forward(request, response);
                break;
            case "undoFeedback":
                break;
            default:
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
