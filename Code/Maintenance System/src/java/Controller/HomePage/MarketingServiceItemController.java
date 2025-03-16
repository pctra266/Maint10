/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.HomePage;
import DAO.HomePage_MarketingServiceItemDAO;
import Model.MarketingServiceItem;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Tra Pham
 */
@WebServlet(name="MarketingServiceItemController", urlPatterns={"/MarketingServiceItemController"})
public class MarketingServiceItemController extends HttpServlet {
   private HomePage_MarketingServiceItemDAO itemDAO = new HomePage_MarketingServiceItemDAO();
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
            out.println("<title>Servlet MarketingServiceItemController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MarketingServiceItemController at " + request.getContextPath () + "</h1>");
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
        String action = request.getParameter("action");
        if (action.equals("new")) {
            MarketingServiceItem newItem = new MarketingServiceItem();
            newItem.setSectionID(Integer.parseInt(request.getParameter("sectionID")));
            newItem.setTitle(request.getParameter("title"));
            newItem.setDescription(request.getParameter("description"));
            newItem.setImageURL(request.getParameter("imageURL"));
            newItem.setSortOrder(Integer.parseInt(request.getParameter("sortOrder")));
            boolean created = itemDAO.createItem(newItem);
            String message = created ? "Item created successfully." : "Create failed.";
            request.setAttribute("message", message);
        } else if (action.equals("edit")) {
            MarketingServiceItem item = new MarketingServiceItem();
            item.setServiceID(Integer.parseInt(request.getParameter("serviceID")));
            item.setSectionID(Integer.parseInt(request.getParameter("sectionID")));
            item.setTitle(request.getParameter("title"));
            item.setDescription(request.getParameter("description"));
            item.setImageURL(request.getParameter("imageURL"));
            item.setSortOrder(Integer.parseInt(request.getParameter("sortOrder")));
            boolean updated = itemDAO.updateItem(item);
            String message = updated ? "Item updated successfully." : "Update failed.";
            request.setAttribute("message", message);
        }
        
        List<MarketingServiceItem> items = itemDAO.getItemsBySectionID(1);
        request.setAttribute("items", items);
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
