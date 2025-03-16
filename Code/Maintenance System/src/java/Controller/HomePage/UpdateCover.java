/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.HomePage;

import DAO.HomePage_CoverDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 *
 * @author Tra Pham
 */
@WebServlet(name="UpdateCover", urlPatterns={"/updateCover"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 100 // 100MB
)
public class UpdateCover extends HttpServlet {
   private final HomePage_CoverDAO coverDAO = new HomePage_CoverDAO();
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
            out.println("<title>Servlet UpdateCover</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateCover at " + request.getContextPath () + "</h1>");
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
        Part filePart = request.getPart("coverImage");

        String uploadPath = "/img/backgrounds";
        String result = Utils.OtherUtils.saveImage(filePart, request, uploadPath);

        if (result == null) {
            System.out.println("1111111111111111111");
            request.setAttribute("messBackground", "No file uploaded.");
            request.getRequestDispatcher("customizeHomepage.jsp").forward(request, response);
        } else if (result.startsWith("Invalid") || result.startsWith("File is too large")) {
            System.out.println("2222222222222222222222");
            request.setAttribute("messBackground", result);
            request.getRequestDispatcher("customizeHomepage.jsp").forward(request, response);
        } else {
            System.out.println("33333333333333333333333");
            boolean isUpdated = coverDAO.updateBackground(result);
            if (isUpdated) {
                request.setAttribute("messBackground", "Background updated successfully!");
            } else {
                request.setAttribute("messBackground", "Failed to update background in database.");
            }
        }
        
        request.getRequestDispatcher("customizeHomepage.jsp").forward(request, response);
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
