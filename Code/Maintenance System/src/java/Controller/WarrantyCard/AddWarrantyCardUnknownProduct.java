package Controller.WarrantyCard;

import DAO.CustomerDAO;
import DAO.ProductDAO;
import DAO.StaffDAO;
import Model.Customer;
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
public class AddWarrantyCardUnknownProduct extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddWarrantyCardUnknownProduct</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddWarrantyCardUnknownProduct at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StaffDAO staffDAO = new StaffDAO();
        CustomerDAO dao = new CustomerDAO();
        ProductDAO p = new ProductDAO();
        String customerId = request.getParameter("customerId");
        String productId = request.getParameter("productId");
        Customer customer = dao.getCustomerByID(Integer.parseInt(customerId));
        int warrantyProductId = p.getWarrantyProductIdByUnknownProductId(Integer.parseInt(productId));
        UnknownProduct unknownProduct = p.getUnknownProductById(Integer.parseInt(productId));
//        PrintWriter out = response.getWriter();
//        out.println(customerId);
//        out.println(productId);

    }

   

}
