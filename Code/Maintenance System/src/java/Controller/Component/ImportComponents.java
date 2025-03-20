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
import org.apache.poi.ss.usermodel.Cell;
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
        response.setHeader("Content-Disposition", "attachment; filename=components_cant_add.xlsx");

        Part filePart = request.getPart("file");
        List<Component> readComponents = new ArrayList<>();
        List<String> errorRows = new ArrayList<>(); // Danh sách dòng bị lỗi
        System.out.println("----------------------");
        if (filePart != null) {
            String fileName = filePart.getSubmittedFileName();
            File file = new File(getServletContext().getRealPath("/") + fileName);
            filePart.write(file.getAbsolutePath());

            try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheetAt(0);
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        errorRows.add("Row " + (i + 1) + " is empty");
                        continue; // Bỏ qua hàng trống hoàn toàn
                    }

                    try {
                        // Kiểm tra ô trống
                        if (isRowInvalid(row)) {
                            System.out.println("tét");
                            errorRows.add("Row " + (i + 1) + " has empty cell");
                            continue;
                        }

                        Component component = new Component();
                        component.setComponentCode(getStringCellValue(row.getCell(1)));
                        component.setComponentName(getStringCellValue(row.getCell(2)));
                        component.setBrand(getStringCellValue(row.getCell(3)));
                        component.setType(getStringCellValue(row.getCell(4)));
                        component.setQuantity(getNumericCellValue(row.getCell(5)));
                        component.setStatus(getBooleanCellValue(row.getCell(6)));
                        component.setPrice(getDoubleCellValue(row.getCell(7)));

                        readComponents.add(component);
                    } catch (Exception e) {
                        errorRows.add("Input data error at line " + (i + 1));
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

        // Nếu có lỗi -> Chuyển hướng về ComponentWarehouse và hiển thị lỗi
        if (!errorRows.isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessages", errorRows);
            response.sendRedirect("ComponentWarehouse"); // Chuyển về trang JSP để hiển thị lỗi
            return;
        }

        // Xử lý nhập vào database
        int countAdded = 0;
        int countNotAdded = 0;
        List<Component> notAdded = new ArrayList<>();

        for (Component readComponent : readComponents) {
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

// Kiểm tra dòng có ô nào bị trống
    private boolean isRowInvalid(Row row) {
        for (int j = 1; j <= 7; j++) {
            Cell cell = row.getCell(j);
            if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                return true; // Phát hiện ô trống
            }
        }
        return false;
    }

// Hàm lấy giá trị String từ ô (nếu có)
    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue().trim();
        }
        return "";
    }

// Hàm lấy giá trị số nguyên từ ô
    private int getNumericCellValue(Cell cell) {
        if (cell == null) {
            return 0;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return (int) cell.getNumericCellValue();
        }
        return 0;
    }

// Hàm lấy giá trị Boolean từ ô
    private boolean getBooleanCellValue(Cell cell) {
        if (cell == null) {
            return false;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return cell.getBooleanCellValue();
        }
        return false;
    }

// Hàm lấy giá trị số thực từ ô
    private double getDoubleCellValue(Cell cell) {
        if (cell == null) {
            return 0.0;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return cell.getNumericCellValue();
        }
        return 0.0;
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
