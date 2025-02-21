/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Component;

import DAO.ComponentDAO;
import Model.Component;
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
@WebServlet(name = "ExportComponents", urlPatterns = {"/ExportComponents"})
public class ExportComponents extends HttpServlet {

    private static ComponentDAO componentDAO = new ComponentDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=components.xlsx");
        String type = request.getParameter("type");
        HttpSession session = request.getSession();
        try (Workbook workbook = new XSSFWorkbook(); OutputStream out = response.getOutputStream();) {

            Sheet sheet = workbook.createSheet("Components");
            Row header = sheet.createRow(0);
            String[] columns = {"ID", "Code", "Name", "Brand", "Type", "Quantity", "Status", "Price"};
            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }
            int rowNum = 1;
            List<Component> components = new ArrayList<>();
            if ("error".equalsIgnoreCase(type)) {
                components = (List<Component>) session.getAttribute("errorComponents");
                if (components == null || components.isEmpty()) {
                    components = componentDAO.getAllComponents();
                }
                session.removeAttribute("errorComponents");
            } else {
                components = componentDAO.getAllComponents();
            }

            for (Component component : components) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(component.getComponentID());
                row.createCell(1).setCellValue(component.getComponentCode());
                row.createCell(2).setCellValue(component.getComponentName());
                row.createCell(3).setCellValue(component.getBrand());
                row.createCell(4).setCellValue(component.getType());
                row.createCell(5).setCellValue(component.getQuantity());
                row.createCell(6).setCellValue(component.isStatus());
                row.createCell(7).setCellValue(component.getPrice());
            }

            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
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

}
