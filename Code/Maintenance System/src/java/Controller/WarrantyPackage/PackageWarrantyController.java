/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.WarrantyPackage;

import DAO.PackageWarrantyDAO;
import Model.PackageWarranty;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Tra Pham
 */
@WebServlet(name="PackageWarrantyController", urlPatterns={"/PackageWarrantyController"})
public class PackageWarrantyController extends HttpServlet {
   private final PackageWarrantyDAO pkgDao = new PackageWarrantyDAO();
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
            out.println("<title>Servlet PackageWarrantyController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PackageWarrantyController at " + request.getContextPath () + "</h1>");
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
        String action = request.getParameter("action");
        
        if(action == null || action.trim().isEmpty()){
            // Lấy danh sách tất cả PackageWarranty và forward đến JSP danh sách
            ArrayList<PackageWarranty> list = pkgDao.getListPackageWarranty();
            request.setAttribute("packageWarranties", list);
            request.getRequestDispatcher("packageWarrantyList.jsp").forward(request, response);
        } else if(action.equals("edit")){
            // Lấy thông tin một PackageWarranty theo ID để chỉnh sửa
            String id = request.getParameter("packageWarrantyID");
            PackageWarranty pkg = pkgDao.getPackageWarrantyByID(id);
            request.setAttribute("packageWarranty", pkg);
            request.getRequestDispatcher("editPackageWarranty.jsp").forward(request, response);
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
        // Xử lý cập nhật PackageWarranty
        String action = request.getParameter("action");
        if(action.equals("edit")){
            PackageWarranty pkg = new PackageWarranty();
            pkg.setPackageWarrantyID(Integer.parseInt(request.getParameter("packageWarrantyID")));
            // Các trường read-only như productCode, customerName, email, productName, extendedWarrantyName không cần cập nhật
            
            // Định dạng ngày (HTML input type="date" gửi về dạng yyyy-MM-dd)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                pkg.setWarrantyStartDate(sdf.parse(request.getParameter("warrantyStartDate")));
                pkg.setWarrantyEndDate(sdf.parse(request.getParameter("warrantyEndDate")));
                pkg.setStartExtendedWarranty(sdf.parse(request.getParameter("startExtendedWarranty")));
                pkg.setEndExtendedWarranty(sdf.parse(request.getParameter("endExtendedWarranty")));
            } catch(Exception e) {
                e.printStackTrace();
            }
            pkg.setNote(request.getParameter("note"));
            pkg.setActive(Boolean.parseBoolean(request.getParameter("isActive")));
            
            boolean updated0 = pkgDao.updateActive(pkg);
            boolean updated1 = pkgDao.updateDefaultWarrantyPackage(pkg);
            boolean updated2 = pkgDao.updateExtendedWarrantyDetail(pkg);
            String message = updated0 ? "Package warranty updated successfully." : "Update failed.";
            message = updated1 ? "Package warranty updated successfully." : "Update failed.";
            message = updated2 ? "Package warranty updated successfully." : "Update failed.";
            request.setAttribute("message", message);
        }
        // Sau khi xử lý POST, lấy lại danh sách và forward về danh sách
        ArrayList<PackageWarranty> list = pkgDao.getListPackageWarranty();
        request.setAttribute("packageWarranties", list);
        request.getRequestDispatcher("packageWarrantyList.jsp").forward(request, response);
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
