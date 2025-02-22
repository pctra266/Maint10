//
//package Controller.Product;
//
//import DAO.ProductDAO;
//import java.io.IOException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//public class DeleteProductServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//   String productIdParam = request.getParameter("id");
//
//        if (productIdParam != null && !productIdParam.isEmpty()) {
//            try {
//                int productId = Integer.parseInt(productIdParam);
//
//                ProductDAO productDAO = new ProductDAO();
//                boolean isDeactivated = productDAO.deactivateProduct(productId);
//
//                if (isDeactivated) {
//                    response.sendRedirect("viewProduct");  
//                } else {
//                    request.setAttribute("errorMessage", "Không thể thay đổi trạng thái sản phẩm.");
//                    request.getRequestDispatcher("/error.jsp").forward(request, response);  
//                }
//            } catch (NumberFormatException e) {
//                request.setAttribute("errorMessage", "ID sản phẩm không hợp lệ.");
//                request.getRequestDispatcher("/error.jsp").forward(request, response);
//            }
//        } else {
//            request.setAttribute("errorMessage", "Sản phẩm không tồn tại.");
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//        }        
//    }
//}
