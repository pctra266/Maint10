/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Feedback;

import DAO.FeedbackDAO;
import DAO.FeedbackLogDAO;
import Model.Feedback;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Tra Pham
 */
@WebServlet(name = "UpdateFeedback", urlPatterns = {"/UpdateFeedback"})
public class UpdateFeedback extends HttpServlet {

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
            out.println("<title>Servlet UpdateFeedback</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateFeedback at " + request.getContextPath() + "</h1>");
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
        String feedbackID = request.getParameter("feedbackID");
        FeedbackDAO dao = new FeedbackDAO();
        Feedback feedbackUpdate = dao.getFeedbackById(feedbackID);
        request.setAttribute("feedbackUpdate", feedbackUpdate);
        request.getRequestDispatcher("updateFeedback.jsp").forward(request, response);
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
        response.sendRedirect("ViewListFeedback");

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
