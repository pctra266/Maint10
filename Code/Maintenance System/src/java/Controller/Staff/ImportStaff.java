/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Staff;

import DAO.StaffDAO;
import Utils.Pagination;
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
import org.apache.poi.ss.usermodel.DataFormatter;
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
        List<Integer> allStaffIds = new ArrayList<>();
        List<Integer> duplicateIds = new ArrayList<>();
        List<Integer> empty = new ArrayList<>();
        List<Integer> emptyColum = new ArrayList<>(); 
        if (filePart != null) {
            String fileName = filePart.getSubmittedFileName();
            File file = new File(getServletContext().getRealPath("/") + fileName);
            filePart.write(file.getAbsolutePath()); // Lưu file tạm
            boolean rowerror = true;
            try (FileInputStream fis = new FileInputStream(file);
                 Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên
                DataFormatter formatter = new DataFormatter();
                for (int i = 1; i <= sheet.getLastRowNum(); i++) { 
                    rowerror = true;
                    Row row = sheet.getRow(i);
                    if(row==null){
                        empty.add(i+1);
                        continue;
                    }
                    for(int j = 0 ; j < 10; j++){
                        String cellValue = formatter.formatCellValue(row.getCell(j)).trim();
                        if(cellValue.isEmpty()){
                            emptyColum.add(i+1);
                            rowerror = false;
                            break;
                        }
                    }
                    if(rowerror == false){
                        continue;
                    }
                    
                    if (row != null) {
                        Staff staff = new Staff();
                        staff.setStaffID((int) row.getCell(0).getNumericCellValue());
                        staff.setUsernameS(formatter.formatCellValue(row.getCell(1)).trim());
                        staff.setPasswordS(formatter.formatCellValue(row.getCell(2)).trim());
                        staff.setName(formatter.formatCellValue(row.getCell(3)).trim());
                        staff.setRole((int) row.getCell(4).getNumericCellValue());
                        staff.setGender(formatter.formatCellValue(row.getCell(5)).trim());
                        staff.setDate(formatter.formatCellValue(row.getCell(6)).trim()); 
                        staff.setEmail(formatter.formatCellValue(row.getCell(7)).trim());
                        staff.setPhone(formatter.formatCellValue(row.getCell(8)).trim());
                        staff.setAddress(formatter.formatCellValue(row.getCell(9)).trim());
                        staff.setImage(formatter.formatCellValue(row.getCell(10)).trim());
                        int staffID = (int)row.getCell(0).getNumericCellValue();
                        if(!allStaffIds.contains(staffID)){
                            allStaffIds.add(staffID);
                        }else
                        {
                            duplicateIds.add(i+1);
                            continue;
                        }
                        // Thêm vào danh sách
                        staffList.add(staff);
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
        if(empty.size()>0){
            request.setAttribute("errorRow", "Cac dong bi trong: "+ empty);
        }
        if(emptyColum.size()>0){
            request.setAttribute("errorColum", "Cac dong bi thieu du lieu: "+ emptyColum);
        }
        if(duplicateIds.size()>0){
            request.setAttribute("error", "Cac dong StaffID bi trung trong file Excel la: "+duplicateIds);
        }
        try {
            int[] i = dao.importStaff(staffList);
            request.setAttribute("message1", "Update: "+i[1]+"\nInsert: "+i[0]);
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
