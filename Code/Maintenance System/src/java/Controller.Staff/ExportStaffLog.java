/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Staff;

import DAO.StaffDAO;
import DAO.StaffLogDAO;
import Model.Staff;
import Model.StaffLog;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.OutputStream;
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
@WebServlet(name="ExportStaffLog", urlPatterns={"/ExportStaffLog"})
public class ExportStaffLog extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=stafflog.xlsx");
        String type = request.getParameter("type");
        String search = request.getParameter("search");
        String searchname = request.getParameter("searchname");
        String column = request.getParameter("column");
        String sortOrder = request.getParameter("sortOrder");
        
        HttpSession session = request.getSession();
        try (Workbook workbook = new XSSFWorkbook(); OutputStream out = response.getOutputStream();) {

            Sheet sheet = workbook.createSheet("staff");
            Row header = sheet.createRow(0);
            String[] columns = {"ID","StaffID", "Username", "Password", "Name", "RoleID", "Gender",
                            "DateOfBirth", "Email", "Phone", "Address", "Image","Time","Status"};
            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }
            int rowNum = 1;
            List<StaffLog> list = new ArrayList<>();
            StaffLogDAO dao = new StaffLogDAO();
            list = dao.getAllStaff(searchname, search, column, sortOrder);
            


            for (StaffLog stafflist : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(stafflist.getStafflogId());
                row.createCell(1).setCellValue(stafflist.getStaffId());
                row.createCell(2).setCellValue(stafflist.getUseNameS());
                row.createCell(3).setCellValue(stafflist.getPasswordS());
                row.createCell(4).setCellValue(stafflist.getName());
                row.createCell(5).setCellValue(stafflist.getRole());
                row.createCell(6).setCellValue(stafflist.getGender());
                row.createCell(7).setCellValue(stafflist.getDate().toString());
                row.createCell(8).setCellValue(stafflist.getEmail());
                row.createCell(9).setCellValue(stafflist.getPhone());
                row.createCell(10).setCellValue(stafflist.getAddress());
                row.createCell(11).setCellValue(stafflist.getImgage());
                row.createCell(12).setCellValue(stafflist.getTime());
                row.createCell(13).setCellValue(stafflist.getStatus());
            }

            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
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
