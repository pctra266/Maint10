/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

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
@WebServlet(name="updateMaxSize", urlPatterns={"/updateMaxSize"})
public class updateMaxSize extends HttpServlet {
   
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
            out.println("<title>Servlet updateMaxSize</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateMaxSize at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
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
        String maxSizeImageStr = request.getParameter("maxSizeImage");
        String maxSizeVideoStr = request.getParameter("maxSizeVideo");
        System.out.println("maxSizeImgae hien tai la : " +maxSizeImageStr );
        int maxSizeImageMB = 5; // Mặc định 5MB nếu lỗi
        int maxSizeVideoMB = 50; // Mặc định 50MB nếu lỗi

        try {
            maxSizeImageMB = parseSize(maxSizeImageStr, request.getParameter("customSizeImage"), request.getParameter("customUnitImage"));
            maxSizeVideoMB = parseSize(maxSizeVideoStr, request.getParameter("customSizeVideo"), request.getParameter("customUnitVideo"));
        } catch (NumberFormatException e) {
            response.getWriter().write("Lỗi: Giá trị nhập không hợp lệ!");
            return;
        }
        System.out.println("maxSizeImageMB hientai la : "+ maxSizeImageMB);

        // Cập nhật giá trị trong ServletContext
        getServletContext().setAttribute("maxUploadSizeImageMB", maxSizeImageMB);
        getServletContext().setAttribute("maxUploadSizeVideoMB", maxSizeVideoMB);
        
        // Chuyển về trang admin
        response.sendRedirect("adminDashboard.jsp");
    }
    private int parseSize(String sizeStr, String customSizeStr, String customUnit) throws NumberFormatException {
    int sizeMB = 5; // Mặc định
    
    if ("kb".equals(sizeStr)) {
        sizeMB = 1; // 500KB làm tròn về 1MB
    } else if ("1g".equals(sizeStr)) {
        sizeMB = 1024; // 1GB = 1024MB
    } else if ("custom".equals(sizeStr) && customSizeStr != null && !customSizeStr.isEmpty()) {
        int customSize = Integer.parseInt(customSizeStr);
        System.out.println("custom size hien tai la: " + customSize);
        if ("kb".equals(customUnit)) {
            sizeMB = customSize / 1024; // KB -> MB
        } else if ("gb".equals(customUnit)) {
            sizeMB = customSize * 1024; // GB -> MB
        } else {
            sizeMB = customSize; // MB
        }
        if (sizeMB < 1) {
            sizeMB = 1; // Không cho nhỏ hơn 1MB
        }
    } else {
        sizeMB = Integer.parseInt(sizeStr);
    }
        System.out.println("sizeMB cuoi cung laf" + sizeMB);
    return sizeMB;
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
