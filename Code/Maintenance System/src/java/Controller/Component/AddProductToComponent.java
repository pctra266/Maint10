/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Component;

import DAO.ComponentDAO;
import DAO.ProductDAO;
import Model.Product;
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
 * @author ADMIN
 */
@WebServlet(name = "AddProductToComponent", urlPatterns = {"/ComponentWarehouse/AddProductToComponent"})
public class AddProductToComponent extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();
    private final ComponentDAO componentDAO = new ComponentDAO();

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
            out.println("<title>Servlet AddProductToComponent</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddProductToComponent at " + request.getContextPath() + "</h1>");
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
        // Lấy ComponentID từ request
        String componentIDParam = request.getParameter("ID");
        if (componentIDParam == null) {
            response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
            return;
        }

        int componentID = Integer.parseInt(componentIDParam);

        // Lấy danh sách sản phẩm để hiển thị
        List<Product> productList = productDAO.getAllProducts();

        // Đặt dữ liệu vào request
        request.setAttribute("productList", productList);
        request.setAttribute("componentID", componentID);

        // Chuyển đến trang JSP mới
        request.getRequestDispatcher("/views/Component/AddProductToComponent.jsp").forward(request, response);
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
        // Lấy dữ liệu từ form
        String componentIDParam = request.getParameter("componentID");
        String productIDParam = request.getParameter("productID");
        String quantityParam = request.getParameter("quantity");

        // Kiểm tra dữ liệu đầu vào
        if (componentIDParam == null || productIDParam == null || quantityParam == null) {
            request.setAttribute("error", "Missing required fields");
            doGet(request, response); // Quay lại trang thêm với thông báo lỗi
            return;
        }

        try {
            int componentID = Integer.parseInt(componentIDParam);
            int productID = Integer.parseInt(productIDParam);
            int quantity = Integer.parseInt(quantityParam);

            // Kiểm tra quantity >= 1
            if (quantity < 1) {
                request.setAttribute("error", "Quantity must be at least 1");
                doGet(request, response);
                return;
            }
            if (componentDAO.existProductComponent(componentID, productID)) {
                request.setAttribute("error", "This product exist in the component");
                doGet(request, response);
            }

            boolean success = componentDAO.addProductComponent(componentID, productID, quantity);
            if (success) {
                // Chuyển hướng về trang chi tiết Component với thông báo thành công
                response.sendRedirect(request.getContextPath() + "/ComponentWarehouse/Detail?ID=" + componentID + "&addSuccess=Product added successfully");
            } else {
                request.setAttribute("error", "Failed to add product");
                doGet(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid input format");
            doGet(request, response);
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
