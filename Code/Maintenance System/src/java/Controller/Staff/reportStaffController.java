/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Staff;

import DAO.StaffDAO;
import Model.ReportStaff;
import Model.Pagination;
import Model.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="reportStaffController", urlPatterns={"/reportStaffController"})
public class reportStaffController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet reportStaffController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet reportStaffController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        StaffDAO dao = new StaffDAO();
        List<Staff> listactive ;
        List<Staff> listRelax;
        List<ReportStaff> list ;
        String action = request.getParameter("action");
        if (action == null) {
            action = "viewAll";
        }
        
        switch(action){
            case "viewAll":
                
                listactive = dao.getAllReport();
                listRelax = dao.getAllReportOut();
                int count = dao.getAllStaffNewStaff();
                int total = dao.getAllStaff();
                int active = listactive.size();
                int relax = listRelax.size();
                double average = ((double) count / total) * 100;
                double averageactive = ((double) active / total) * 100;
                double averagerelax = ((double) relax / total) * 100;
                String formattedAverage = String.format("%.1f", average);
                String formattedAverageActive = String.format("%.1f", averageactive);
                String formattedAverageRelax = String.format("%.1f", averagerelax);

                request.setAttribute("list", listactive);
                request.setAttribute("listRelax", listRelax);
                request.setAttribute("count", count);
                request.setAttribute("total", total);
                request.setAttribute("average", formattedAverage);
                request.setAttribute("active", active);
                request.setAttribute("relax", relax);
                request.setAttribute("averageactive", formattedAverageActive);
                request.setAttribute("averagerelax", formattedAverageRelax);
                request.getRequestDispatcher("ReportStaff.jsp").forward(request, response);
                break;
            case "viewAllActive":
                listactive = dao.getAllReport();
                request.setAttribute("list", listactive);
                request.setAttribute("action", action);
                request.getRequestDispatcher("Stafflist.jsp").forward(request, response);
                break;
            case "viewAllInactive":
                listRelax = dao.getAllReportOut();
                request.setAttribute("listRelax", listRelax);
                request.setAttribute("action", action);
                request.getRequestDispatcher("Stafflist.jsp").forward(request, response);
                break;
            
            default:
                break;
        } 
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        StaffDAO dao = new StaffDAO();
        StaffDAO staffDAO = new StaffDAO();
        HttpSession session = request.getSession();
        Staff staffOnSession = (Staff) session.getAttribute("staff");
        List<Staff> listactive ;
        List<ReportStaff> list ;
        List<Staff> listRelax;
        String action = request.getParameter("action");
        String roleName = staffDAO.getRoleNameByStaffID(staffOnSession.getStaffID());
        Staff staffProfile = staffDAO.getStaffById(staffOnSession.getStaffID());
        request.setAttribute("staffProfile", staffProfile);
        if (action == null) {
            action = "viewAll";
        }
        
        switch(action){
            case "viewAll":
                listactive = dao.getAllReport();
                listRelax = dao.getAllReportOut();
                int count = dao.getAllStaffNewStaff();
                request.setAttribute("list", listactive);
                request.setAttribute("listRelax", listRelax);
                request.setAttribute("count", count);
                request.getRequestDispatcher("ReportStaff.jsp").forward(request, response);
                break;
            case "Staffinfo":
                String staffID = request.getParameter("staffID");
                list = dao.getAllStaffRepairByID(staffID);
                request.setAttribute("info", list);
                request.setAttribute("staffID", staffID);
                request.getRequestDispatcher("ReportStaffInfor.jsp").forward(request, response);    
                break;
            default:
                break;
        } 
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
