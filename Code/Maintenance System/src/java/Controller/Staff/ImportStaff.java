/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Staff;

import DAO.StaffDAO;
import Model.Pagination;
import Model.Staff;
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
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ADMIN
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 100 // 100MB
)
@WebServlet(name="ImportStaff", urlPatterns={"/ImportStaff"})
public class ImportStaff extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Part filePart = request.getPart("file"); // Lấy file từ form
        List<Staff> staffList = new ArrayList<>();
        
        if (filePart != null) {
            String fileName = filePart.getSubmittedFileName();
            File file = new File(getServletContext().getRealPath("/") + fileName);
            filePart.write(file.getAbsolutePath()); // Lưu file tạm

            try (FileInputStream fis = new FileInputStream(file);
                 Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên
                
                for (int i = 1; i <= sheet.getLastRowNum(); i++) { 
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        Staff staff = new Staff();
                        staff.setStaffID((int) row.getCell(0).getNumericCellValue());
                        staff.setUsernameS(row.getCell(1).getStringCellValue());
                        staff.setPasswordS(row.getCell(2).getStringCellValue());
                        staff.setName(row.getCell(3).getStringCellValue());
                        staff.setRole((int) row.getCell(4).getNumericCellValue());
                        staff.setGender(row.getCell(5).getStringCellValue());
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
//                        Date date = row.getCell(6).getDateCellValue();
//                        staff.setDate(sdf.format(date)); 
                        staff.setDate(row.getCell(6).getStringCellValue()); 

                        staff.setEmail(row.getCell(7).getStringCellValue());
                        staff.setPhone(row.getCell(8).getStringCellValue());
                        staff.setAddress(row.getCell(9).getStringCellValue());
                        staff.setImage(row.getCell(10).getStringCellValue());
                        staffList.add(staff);
                        PrintWriter out = response.getWriter();
                        out.print("Read staff: " + staff.getStaffID() + " - " + staff.getUsernameS());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (file.exists()) {
                    file.delete(); 
                }
            }
        }
//        for (Staff s : staffList) {
//            PrintWriter out = response.getWriter();
//
//            out.println("Staff ID: " + s.getStaffID());
//            out.println("Username: " + s.getUsernameS());
//            out.println("Password: " + s.getPasswordS());
//            out.println("Role: " + s.getRole());
//        }

        StaffDAO dao = new StaffDAO();
        
        
        try {
            dao.importStaff(staffList);
            request.setAttribute("message", "Nhập dữ liệu thành công!");
        } catch (Exception e) {
            request.setAttribute("message", "Lỗi khi lưu vào database: " + e.getMessage());
        }
        String search = request.getParameter("search");
        String searchname = request.getParameter("searchname");
        String column = request.getParameter("column");
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
        staffList = dao.getAllStaff(name, search, pageIndex, pagesize, column, sortOrder);
        totalStaff = dao.getAllStaff(name, search, column, sortOrder).size();
        int totalPageCount = (int) Math.ceil((double) totalStaff / pagesize);

        Pagination pagination = new Pagination();

        pagination.setUrlPattern( "/StaffController");
        pagination.setCurrentPage(pageIndex);
        pagination.setPageSize(pagesize);
        pagination.setTotalPages(totalPageCount);
        pagination.setTotalPagesToShow(5); // Hiển thị tối đa 5 trang liền kề
        pagination.setSort(column == null ? "" : column);
        pagination.setOrder(sortOrder == null ? "" : sortOrder);

        List<String> searchFields = new ArrayList();
        List<String> searchValues = new ArrayList<>();
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
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("page_size", page_size);
        request.setAttribute("list", staffList);

        request.getRequestDispatcher("Staff.jsp").forward(request, response);
    
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
        processRequest(request, response);
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
