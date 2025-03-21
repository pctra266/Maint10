/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.StaffDAO;
import DAO.StaffLogDAO;
import Utils.Pagination;
import Model.Staff;
import Model.StaffLog;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class seeMoreController extends HttpServlet {


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
        StaffLogDAO dao = new StaffLogDAO();
        ArrayList<StaffLog> list ;
        String action = request.getParameter("action");
        if (action == null) {
            action = "viewAll";
        }
        switch(action){
            case "viewAll":
                String search = request.getParameter("search");
                String searchname = request.getParameter("searchname");
                String column = request.getParameter("column");
                String status = request.getParameter("status");
                String sortOrder = request.getParameter("sortOrder");
                String page_size = request.getParameter("page_size");                               
                String name = "";
                if(searchname != null && !searchname.isEmpty()){
                    name = searchname.trim().replaceAll("\\s+", " ");
                }
                
                int pagesize = 5; 

                if (page_size != null && !page_size.isEmpty()) {
                    try {
                        pagesize = Integer.parseInt(page_size);
                    } catch (NumberFormatException e) {
                        pagesize = 5; 
                    }
                }
                    
                int pageIndex = 1;
                String pageIndexStr = request.getParameter("page");

                if (pageIndexStr != null) {
                    try {
                        pageIndex = Integer.parseInt(pageIndexStr);
                    } catch (NumberFormatException e) {
                        pageIndex = 1;
                    }
                }
                int totalStaff;
                list = dao.getAllStaff(name, search, pageIndex, pagesize, column,status, sortOrder);
//                list = dao.getAllStaff(searchname, search, column, sortOrder);
                totalStaff = dao.getAllStaff(name, search, column, sortOrder).size();
                PrintWriter out = response.getWriter();
                out.print(list);
                int totalPageCount = (int) Math.ceil((double) totalStaff / pagesize);
                
                Pagination pagination = new Pagination();
                
                pagination.setUrlPattern( "/seeMoreController");
                pagination.setCurrentPage(pageIndex);
                pagination.setPageSize(pagesize);
                pagination.setTotalPages(totalPageCount);
                pagination.setTotalPagesToShow(5); // Hiển thị tối đa 5 trang liền kề
                pagination.setSort(column == null ? "" : column);
                pagination.setOrder(sortOrder == null ? "" : sortOrder);
                
                List<String> searchFields = new ArrayList();
                List<String> searchValues = new ArrayList();
                if(searchname != null && !searchname.isEmpty()){
                    searchFields.add("searchname");
                    searchValues.add(searchname);
                }
                if(search != null && !search.isEmpty()){
                    searchFields.add("search");
                    searchValues.add(search);
                }
                if(page_size != null && !page_size.isEmpty()){
                    searchFields.add("page_size");
                    searchValues.add(page_size);
                }
                pagination.setSearchFields(searchFields.toArray(new String[searchFields.size()]));
                pagination.setSearchValues(searchValues.toArray(new String[searchValues.size()]));

                
                // Gán các thuộc tính cần thiết cho JSP
                request.setAttribute("pagination", pagination);
                
                request.setAttribute("totalPageCount", totalPageCount);
                request.setAttribute("search", search);
                request.setAttribute("searchname", searchname);
                request.setAttribute("column", column);
                request.setAttribute("status", status);
                request.setAttribute("sortOrder", sortOrder);
                request.setAttribute("pageIndex", pageIndex);
                request.setAttribute("page_size", page_size);
                request.setAttribute("list", list);
                request.getRequestDispatcher("all-changeStaff.jsp").forward(request, response);
                break;
                
            default:
                break;
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
              
    }
    private String saveImage(Part imagePart, HttpServletRequest request) throws IOException {
        if (imagePart == null || imagePart.getSize() == 0 || imagePart.getSubmittedFileName() == null|| imagePart.getSubmittedFileName().isEmpty()) return "img/Staff/default-image.png";

        // Đường dẫn đến thư mục Web Pages/img/Staff
        String devUploadPath = request.getServletContext().getRealPath("/").replace("build\\web\\", "web/") + "img/Staff";

        // Đường dẫn đến thư mục build/web/img/Staff
        String buildUploadPath = request.getServletContext().getRealPath("/") + "img/Staff";


        File devUploadDir = new File(devUploadPath);
        File buildUploadDir = new File(buildUploadPath);

        if (!devUploadDir.exists()) devUploadDir.mkdirs(); // Tạo thư mục nếu chưa có
        if (!buildUploadDir.exists()) buildUploadDir.mkdirs();

        String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();

        if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png")) {
            String error = "Chỉ lấy file png và jpg";
            return error;
        }

        File devFile = new File(devUploadPath, fileName);
        File buildFile = new File(buildUploadPath, fileName);

        // Ghi file vào cả hai thư mục
        imagePart.write(devFile.getAbsolutePath());
        Files.copy(devFile.toPath(), buildFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return "img/Staff/"+ fileName; // Trả về đường dẫn lưu trong DB
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
