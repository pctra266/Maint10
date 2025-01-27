/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Feedback;

import DAO.FeedbackDAO;
import DAO.FeedbackLogDAO;
import Model.Feedback;
import Model.FeedbackLog;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author Tra Pham
 */
@WebServlet(name = "FeedbackController", urlPatterns = {"/feedback"})
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
        FeedbackDAO daoFeedback = new FeedbackDAO();
        FeedbackLogDAO daoFeedbackLog = new FeedbackLogDAO();
        String action = request.getParameter("action");
        if (action == null) {
            action = "viewFeedback";
        }
        System.out.println("Action hien tai la: " + action);
        switch (action) {
            case "viewFeedback":
                String customerName = request.getParameter("customerName");
                String imageAndVideo = request.getParameter("imageAndVideo");
                request.setAttribute("customerName", customerName);
                request.setAttribute("imageAndVideo", imageAndVideo);
                //======phan trang
                int totalPages = daoFeedback.getTotalFeedback(customerName, imageAndVideo);
                int endPage = totalPages / 7;
                if (totalPages % 7 != 0) {
                    endPage++;
                }
                request.setAttribute("endPage", endPage);
                String indexStr = request.getParameter("index");
                int index = 1;
                try {
                    index = Integer.parseInt(indexStr);
                } catch (Exception e) {

                }
                ArrayList<Feedback> listFeedback = daoFeedback.getAllFeedback(customerName, imageAndVideo, index);

                //======end phan trang
                request.setAttribute("listFeedback", listFeedback);
                request.getRequestDispatcher("viewListFeedback.jsp").forward(request, response);
                break;
            case "deleteFeedback":
                String feedbackIdDelete = request.getParameter("feedbackID");
                String staffId = "1";
                daoFeedback.inActiveFeedbackById(feedbackIdDelete);
                daoFeedbackLog.createDeleteFeedbackLog(daoFeedback.getFeedbackById(feedbackIdDelete), staffId);
                // chuyen sang view action
                response.sendRedirect("feedback");
                break;
            case "updateFeedback":
                String feedbackIdUpdate = request.getParameter("feedbackID");
                Feedback feedbackUpdate = daoFeedback.getFeedbackById(feedbackIdUpdate);
                request.setAttribute("feedbackUpdate", feedbackUpdate);
                request.getRequestDispatcher("updateFeedback.jsp").forward(request, response);
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
        if (action == null) {
            action = "viewListFeedback";
        }
        switch (action) {
            case "viewListFeedback":
                String customerName = request.getParameter("customerName");
                String imageAndVideo = request.getParameter("imageAndVideo");
                request.setAttribute("customerName", customerName);
                request.setAttribute("imageAndVideo", imageAndVideo);
                //phan trang
                int totalPages = daoFeedback.getTotalFeedback(customerName, imageAndVideo);
                int endPage = totalPages / 7;
                if (totalPages % 7 != 0) {
                    endPage++;
                }
                request.setAttribute("endPage", endPage);
                String indexStr = request.getParameter("index");
                int index = 1;
                try {
                    index = Integer.parseInt(indexStr);
                } catch (Exception e) {

                }
                ArrayList<Feedback> listFeedback = daoFeedback.getAllFeedback(customerName, imageAndVideo, index);
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
                if (!note.trim().endsWith(oldFeedback.getNote())) {
                    daoFeedbackLog.createUpdateFeedbackLog(oldFeedback, staffId, note);
                }
                //
                daoFeedback.updateFeedback(feedbackId, note);
                response.sendRedirect("feedback");
                break;
            case "createFeedback":
                String noteCreate = request.getParameter("note");
                String customerId = request.getParameter("customerId");
                String warrantyCardId = request.getParameter("warrantyCardId");
                daoFeedback.createFeedback("2", "7", noteCreate);
                System.out.println(noteCreate);
                // tam thoi ve trang view list de kiem tra sau nay sua
                response.sendRedirect("feedback");
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
