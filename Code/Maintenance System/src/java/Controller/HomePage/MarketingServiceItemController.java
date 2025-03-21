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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.List;

/**
 *
 * @author Tra Pham
 */
@WebServlet(name="MarketingServiceItemController", urlPatterns={"/MarketingServiceItemController"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
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
            String action = request.getParameter("action");

            if (action == null || action.trim().isEmpty()) {
                List<MarketingServiceItem> items = itemDAO.getItemsBySectionID(1);
                request.setAttribute("items", items);
                request.getRequestDispatcher("customizeHomepage.jsp").forward(request, response);
            } else if (action.equals("edit")) {
                int serviceID = Integer.parseInt(request.getParameter("serviceID"));
                MarketingServiceItem item = itemDAO.getItemByID(serviceID);
                request.setAttribute("item", item);
                request.getRequestDispatcher("editServiceItem.jsp").forward(request, response);
            } else if (action.equals("new")) {
                request.getRequestDispatcher("addServiceItem.jsp").forward(request, response);
            } else if (action.equals("delete")) {
                int serviceID = Integer.parseInt(request.getParameter("serviceID"));
                boolean deleted = itemDAO.deleteItem(serviceID);
                String message = deleted ? "Item deleted successfully." : "Delete failed.";
                request.setAttribute("message", message);

                List<MarketingServiceItem> items = itemDAO.getItemsBySectionID(1);
                request.setAttribute("items", items);
                request.getRequestDispatcher("customizeHomepage.jsp").forward(request, response);
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
    String action = request.getParameter("action");
    String uploadPath = "/img/serviceItems"; // đường dẫn lưu ảnh trên server
    
    if (action.equals("new")) {
        MarketingServiceItem newItem = new MarketingServiceItem();
        newItem.setSectionID(Integer.parseInt(request.getParameter("sectionID")));
        newItem.setTitle(request.getParameter("title"));
        newItem.setDescription(request.getParameter("description"));
        newItem.setSortOrder(Integer.parseInt(request.getParameter("sortOrder")));
        
        // Xử lý upload ảnh từ input có name="imageURL"
        Part filePart = request.getPart("imageURL");
        
        String result = Utils.OtherUtils.saveImage(filePart, request, uploadPath);
        
        if (result == null) {
            request.setAttribute("message", "No file uploaded.");
            List<MarketingServiceItem> items = itemDAO.getItemsBySectionID(1);
            request.setAttribute("items", items);
            request.getRequestDispatcher("customizeHomepage.jsp").forward(request, response);
            return;
        } else if (result.startsWith("Invalid") || result.startsWith("File is too large")) {
            request.setAttribute("message", result);
            List<MarketingServiceItem> items = itemDAO.getItemsBySectionID(1);
            request.setAttribute("items", items);
            request.getRequestDispatcher("customizeHomepage.jsp").forward(request, response);
            return;
        } else {
            newItem.setImageURL(result);
        }
        
        boolean created = itemDAO.createItem(newItem);
        String message = created ? "Item created successfully." : "Create failed.";
        request.setAttribute("message", message);
        
    } else if (action.equals("edit")) {
        MarketingServiceItem item = new MarketingServiceItem();
        item.setServiceID(Integer.parseInt(request.getParameter("serviceID")));
        item.setSectionID(Integer.parseInt(request.getParameter("sectionID")));
        item.setTitle(request.getParameter("title"));
        item.setDescription(request.getParameter("description"));
        item.setSortOrder(Integer.parseInt(request.getParameter("sortOrder")));
        
        // Xử lý upload ảnh nếu có file được chọn
        Part filePart = request.getPart("imageURL");
        String result = Utils.OtherUtils.saveImage(filePart, request, uploadPath);
        if (result == null) {
            request.setAttribute("message", "No file uploaded.");
            request.getRequestDispatcher("customizeHomepage.jsp").forward(request, response);
        } else if (result.startsWith("Invalid") || result.startsWith("File is too large")) {
            request.setAttribute("message", result);
            request.getRequestDispatcher("customizeHomepage.jsp").forward(request, response);
        }else{
            
        item.setImageURL(result);
        boolean updated = itemDAO.updateItem(item);
        String message = updated ? "Item updated successfully." : "Update failed.";
        request.setAttribute("message", message);
        } 
        
        
        
    } else if (action.equals("delete")) {
        int serviceID = Integer.parseInt(request.getParameter("serviceID"));
        boolean deleted = itemDAO.deleteItem(serviceID);
        String message = deleted ? "Item deleted successfully." : "Delete failed.";
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
