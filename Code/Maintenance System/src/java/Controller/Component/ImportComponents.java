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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
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
@WebServlet(name = "ImportComponents", urlPatterns = {"/ImportComponents"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 100 // 100MB
)
public class ImportComponents extends HttpServlet {

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
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=componentscan'tadd.xlsx");

        Part filePart = request.getPart("file");
        List<Component> readComponents = new ArrayList<>();
        if (filePart != null) {
            String fileName = filePart.getSubmittedFileName();
            File file = new File(getServletContext().getRealPath("/") + fileName);
            filePart.write(file.getAbsolutePath());

            try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis);) {
                Sheet sheet = workbook.getSheetAt(0);
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    Component component = new Component();
                    component.setComponentCode(row.getCell(1).getStringCellValue());
                    component.setComponentName(row.getCell(2).getStringCellValue());
                    component.setBrand(row.getCell(3).getStringCellValue());
                    component.setType(row.getCell(4).getStringCellValue());
                    component.setQuantity((int) row.getCell(5).getNumericCellValue());
                    component.setStatus(row.getCell(6).getBooleanCellValue());
                    component.setPrice(row.getCell(7).getNumericCellValue());
                    readComponents.add(component);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        int countAdded = 0;
        int countNotAdded = 0;
        List<Component> notAdded = new ArrayList<>();
        for (Component readComponent : readComponents) {
            System.out.println("test");
            if (componentDAO.add(readComponent)) {
                countAdded++;
            } else {
                countNotAdded++;
                notAdded.add(readComponent);
            }
        }
             HttpSession session = request.getSession();
            session.setAttribute("errorComponents", notAdded);
        response.sendRedirect("ComponentWarehouse");
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
