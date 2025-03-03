package Controller.Product;

import DAO.ProductDAO;
import Model.UnknownProduct;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author sonNH
 */
public class ViewListUnknownProduct extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewListUnknowProduct</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewListUnknowProduct at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();
        List<UnknownProduct> listUnknownProduct = productDAO.getAllUnknownProducts();
        
        request.setAttribute("listUnknownProduct", listUnknownProduct);
        
        request.getRequestDispatcher("Product/viewUnknownProduct.jsp").forward(request, response);
        
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }



}
