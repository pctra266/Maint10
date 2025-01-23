/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Component;

import DAO.ComponentDAO;
import Model.Component;
import Utils.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.net.URLEncoder;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ComponentAction", urlPatterns = {"/ComponentWarehouse/Detail", "/ComponentWarehouse/Delete", "/ComponentWarehouse/Edit", "/ComponentWarehouse/Add"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ComponentAction extends HttpServlet {

    private final ComponentDAO componentDAO = new ComponentDAO();
    private static final int PAGE_SIZE = 5;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            if (action.equals("/ComponentWarehouse/Add")) {
                handleAddComponent(request, response);
            } else if (action.startsWith("/ComponentWarehouse")) {
                handleComponentActions(request, response, action);
            } else {
                response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
            }
        } catch (ServletException | IOException e) {
            response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
        }
        // Xử lý thêm mới component

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
        processRequest(request, response);
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
        processRequest(request, response);
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

// Xử lý thêm mới Component
    private void handleAddComponent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String newName = request.getParameter("Name");
        String newCode = request.getParameter("Code");
        Integer newQuantity = NumberUtils.tryParseInt(request.getParameter("Quantity"));
        Double newPrice = NumberUtils.tryParseDouble(request.getParameter("Price"));
        Part imagePart = request.getPart("newImage");

        boolean canAdd = true;

        // Kiểm tra dữ liệu đầu vào
        if (newName == null) {
            canAdd = false;
        } else if (newName.isBlank()) {
            request.setAttribute("nameAlert", "Name must not be empty!");
            canAdd = false;
        } else {
            request.setAttribute("name", newName);
        }
        if (newCode == null) {
            canAdd = false;
        } else if (newCode.isBlank()) {
            request.setAttribute("codeAlert", "Code must not be empty!");
            canAdd = false;
        } else {
            request.setAttribute("code", newCode);
        }
        if (newQuantity == null) {
            canAdd = false;
        } else if (newQuantity < 0) {
            request.setAttribute("quantityAlert", "Quantity must be an integer greater than or equal to 0");
            canAdd = false;
        } else {
            request.setAttribute("quantity", newQuantity);
        }

        if (newPrice == null) {
            canAdd = false;
        } else if (newPrice < 0) {
            request.setAttribute("priceAlert", "Price must be a float greater than or equal to 0");
            canAdd = false;
        } else {
            request.setAttribute("price", newPrice);
        }

        // Nếu dữ liệu hợp lệ, lưu ảnh và thêm Component
        if (canAdd) {
            String imagePath = saveImage(imagePart, request); // Lưu ảnh
            Component component = new Component();
            component.setComponentName(newName);
            component.setComponentCode(newCode);
            component.setPrice(newPrice);
            component.setQuantity(newQuantity);
            if (imagePath != null) {
                component.setImage(imagePath);
            }

            boolean add = componentDAO.add(component);
            Component addedComponent = componentDAO.getLast();
            if (add) {
                request.setAttribute("addAlert1", "Added to warehouse");
                request.setAttribute("component", addedComponent);
                request.getRequestDispatcher("/Component/ComponentDetail.jsp").forward(request, response);
            } else {
                request.setAttribute("addAlert0", "Fail add to warehouse");
                request.getRequestDispatcher("/Component/ComponentAdd.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("/Component/ComponentAdd.jsp").forward(request, response);
        }
    }

// Lưu ảnh vào thư mục /img/Component
    private String saveImage(Part imagePart, HttpServletRequest request) throws IOException {
        if (imagePart == null || imagePart.getSize() == 0) {
            return null;
        }

        // Đường dẫn tuyệt đối đến thư mục img/Component
        String uploadPath = request.getServletContext().getRealPath("/img/Component");
        System.out.println("Upload Path: " + uploadPath); // Kiểm tra đường dẫn
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Tạo thư mục nếu chưa tồn tại
        }

        // Tạo tên file duy nhất
        String originalFileName = imagePart.getSubmittedFileName();
        if (originalFileName == null || originalFileName.isEmpty()) {
            return null; // Trả về null nếu không có tên file
        }
        String fileName = originalFileName;
//    String fileName = System.currentTimeMillis() + "_" + originalFileName;
        String filePath = uploadPath + File.separator + fileName;

        try {
            imagePart.write(filePath); // Ghi file lên server
        } catch (IOException e) {
            e.printStackTrace(); // In ra lỗi nếu có
            return null; // Trả về null nếu có lỗi
        }

        // Trả về đường dẫn tương đối để lưu vào database
        return "img/Component/" + fileName; // Chỉ cần đường dẫn tương đối
    }

    private void handleComponentActions(HttpServletRequest request, HttpServletResponse response, String action) throws IOException, ServletException {
        String componentID = request.getParameter("ID");
        Integer id = NumberUtils.tryParseInt(componentID);

        // Kiểm tra nếu không có ID hợp lệ
        if (id == null) {
            response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
            return;
        }

        // Lấy Component từ database
        Component component = componentDAO.getComponentByID(id);
        if (component == null) {
            response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
            return;
        }

        switch (action) {
            case "/ComponentWarehouse/Detail" -> {
                // Hiển thị chi tiết component
                request.setAttribute("list", componentDAO.getProductsByComponentId(id));
                request.setAttribute("component", component);
                request.getRequestDispatcher("/Component/ComponentDetail.jsp").forward(request, response);
            }
            case "/ComponentWarehouse/Delete" -> {
                String pageParam = request.getParameter("page");
                String paraSearch = request.getParameter("search");
                int page = (NumberUtils.tryParseInt(pageParam) != null) ? NumberUtils.tryParseInt(pageParam) : 1;
                // Lấy page-size từ request, mặc định là PAGE_SIZE
                String pageSizeParam = request.getParameter("page-size");
                String sort = request.getParameter("sort");
                String order = request.getParameter("order");
                Integer pageSize;
                pageSize = (NumberUtils.tryParseInt(pageSizeParam) != null) ? NumberUtils.tryParseInt(pageSizeParam) : PAGE_SIZE;

                // Xóa component
                boolean check = componentDAO.delete(id);
                String redirect = request.getContextPath()
                        + "/ComponentWarehouse?page=" + page
                        + "&page-size=" + pageSize
                        + "&search=" + URLEncoder.encode(paraSearch, "UTF-8")
                        + "&sort=" + sort
                        + "&order=" + order;
                redirect+=check?"&delete=1":"delete=0";
                response.sendRedirect(redirect);
            }
            case "/ComponentWarehouse/Edit" -> {
                // Sửa component
                handleEditComponent(request, response, component);
            }

            default ->
                response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
        }
    }

    private void handleEditComponent(HttpServletRequest request, HttpServletResponse response, Component component)
            throws ServletException, IOException {
        String newName = request.getParameter("Name");
        Integer newQuantity = NumberUtils.tryParseInt(request.getParameter("Quantity"));
        Double newPrice = NumberUtils.tryParseDouble(request.getParameter("Price"));
        String newCode = request.getParameter("Code");
        Part imagePart = request.getPart("newImage");

        boolean canUpdate = true;

        // Kiểm tra dữ liệu đầu vào
        if (newName == null || newName.isBlank()) {
            request.setAttribute("nameAlert", "Name must not be empty!");
            canUpdate = false;
        }
        if (newCode == null || newCode.isBlank()) {
            request.setAttribute("codeAlert", "Code must not be empty!");
            canUpdate = false;
        }
        if (newQuantity == null || newQuantity < 0) {
            request.setAttribute("quantityAlert", "Quantity must be an integer greater than or equal to 0");
            canUpdate = false;
        }
        if (newPrice == null || newPrice < 0) {
            request.setAttribute("priceAlert", "Price must be a float greater than or equal to 0");
            canUpdate = false;
        }

        // Nếu có thể cập nhật, thực hiện cập nhật
        if (canUpdate) {
            component.setComponentCode(newCode);
            component.setComponentName(newName);
            component.setQuantity(newQuantity);
            component.setPrice(newPrice);

            // Lưu ảnh mới nếu có
            String imagePath = saveImage(imagePart, request);
            if (imagePath != null) {
                component.setImage(imagePath);
            }

            boolean update = componentDAO.update(component);
            if (update) {
                request.setAttribute("updateAlert1", "Component updated successfully.");
            } else {
                request.setAttribute("updateAlert0", "Fail to edit");
            }
        } else {
            request.setAttribute("component", component);
        }

        // Trả về trang chi tiết Component
        request.setAttribute("list", componentDAO.getProductsByComponentId(component.getComponentID()));
        request.setAttribute("component", component);
        request.getRequestDispatcher("/Component/ComponentDetail.jsp").forward(request, response);
    }

}
