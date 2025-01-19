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

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ComponentAction", urlPatterns = {"/ComponentWarehouse/Detail", "/ComponentWarehouse/Delete", "/ComponentWarehouse/Edit", "/ComponentWarehouse/Add"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class ComponentAction extends HttpServlet {

    private final ComponentDAO componentDAO = new ComponentDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        // Xử lý thêm mới component
        if (action.equals("/ComponentWarehouse/Add")) {
            handleAddComponent(request, response);
        } else if (action.startsWith("/ComponentWarehouse")) {
            handleComponentActions(request, response, action);
        } else {
            response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
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
        Integer newQuantity = NumberUtils.tryParseInt(request.getParameter("Quantity"));
        Double newPrice = NumberUtils.tryParseDouble(request.getParameter("Price"));
        Part imagePart = request.getPart("newImage");

        boolean canAdd = true;

        // Kiểm tra dữ liệu đầu vào
        if (newName == null || newName.isBlank()) {
            request.setAttribute("nameAlert", "Name must not be empty!");
            canAdd = false;
        } else {
            request.setAttribute("name", newName);
        }
        if (newQuantity == null || newQuantity < 0) {
            request.setAttribute("quantityAlert", "Quantity must be an integer greater than or equal to 0");
            canAdd = false;
        } else {
            request.setAttribute("quantity", newQuantity);
        }
        if (newPrice == null || newPrice < 0) {
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
            component.setPrice(newPrice);
            component.setQuantity(newQuantity);
            if (imagePath != null) {
                component.setImage(imagePath);
            }

            componentDAO.add(component);
            Component addedComponent = componentDAO.getLast();

            request.setAttribute("Added", "Added to warehouse");
            request.setAttribute("component", addedComponent);
            request.getRequestDispatcher("/Component/ComponentDetail.jsp").forward(request, response);
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
                request.setAttribute("component", component);
                request.getRequestDispatcher("/Component/ComponentDetail.jsp").forward(request, response);
            }
            case "/ComponentWarehouse/Delete" -> {
                // Xóa component
                componentDAO.delete(id);
                response.sendRedirect(request.getContextPath() + "/ComponentWarehouse");
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
        Part imagePart = request.getPart("newImage");

        boolean canUpdate = true;

        // Kiểm tra dữ liệu đầu vào
        if (newName == null || newName.isBlank()) {
            request.setAttribute("nameAlert", "Name must not be empty!");
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
            component.setComponentName(newName);
            component.setQuantity(newQuantity);
            component.setPrice(newPrice);

            // Lưu ảnh mới nếu có
            String imagePath = saveImage(imagePart, request);
            if (imagePath != null) {
                component.setImage(imagePath);
            }

            componentDAO.update(component);
            request.setAttribute("Updated", "Component updated successfully.");
        } else {
            request.setAttribute("component", component);
        }

        // Trả về trang chi tiết Component
        request.setAttribute("component", component);
        request.getRequestDispatcher("/Component/ComponentDetail.jsp").forward(request, response);
    }

  
}