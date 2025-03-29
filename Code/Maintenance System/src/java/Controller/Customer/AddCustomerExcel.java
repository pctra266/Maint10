/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Customer;

import DAO.CustomerDAO;
import Model.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author PC
 */
@MultipartConfig
public class AddCustomerExcel extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddCustomerExcel</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddCustomerExcel at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        CustomerDAO customerDao = new CustomerDAO();
        ArrayList<Customer> listCustomer = new ArrayList<>();
        ArrayList<Customer> listExistCustomer = new ArrayList<>();
        Part customerFile = request.getPart("customerFile");
        String checkFile = customerFile.getSubmittedFileName().toLowerCase();
        if (!checkFile.endsWith("xlsx")) {
            request.setAttribute("error", "Please enter xlsx file");
            request.getRequestDispatcher("importExcelCustomer.jsp").forward(request, response);
            return;
        }

        InputStream inputStream = customerFile.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();

        if (rows.hasNext()) {
            rows.next();
        }

        while (rows.hasNext()) {
            Row row = rows.next();
            String usernameC = (String) getCellValue(row.getCell(0));
            String passwordC = (String) getCellValue(row.getCell(1));
            String name = (String) getCellValue(row.getCell(2));
            String gender = (String) getCellValue(row.getCell(3));

            // Xử lý Date of Birth khi lưu dưới dạng số (Numeric)
            java.sql.Date dateOfBirth = null;
            try {
                Cell dateCell = row.getCell(4); 
                if (dateCell != null) {
                    if (dateCell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(dateCell)) {
                        Date excelDate = dateCell.getDateCellValue();
                        dateOfBirth = new java.sql.Date(excelDate.getTime());
                    } else if (dateCell.getCellType() == Cell.CELL_TYPE_STRING) {
                        // Nếu ngày tháng lưu dưới dạng chuỗi "dd/MM/yyyy"
                        String dateStr = dateCell.getStringCellValue().trim();
                        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd");

                        Date parsedDate = inputFormat.parse(dateStr);
                        String formattedDate = outputFormat.format(parsedDate);

                        // Chuyển sang java.sql.Date
                        dateOfBirth = java.sql.Date.valueOf(formattedDate.replace("/", "-"));
                    } else {
                        throw new IllegalArgumentException("Invalid date format in Excel!");
                    }
                } else {
                    throw new IllegalArgumentException("Date cell is empty!");
                }
            } catch (IllegalArgumentException | ParseException e) {
                e.printStackTrace();
                request.setAttribute("error", "Invalid date format in Excel!");
                request.getRequestDispatcher("importExcelCustomer.jsp").forward(request, response);
                return;
            }

            String email = (String) getCellValue(row.getCell(5));
            String phone = (String) getCellValue(row.getCell(6));
            String address = (String) getCellValue(row.getCell(7));
            String image = (String) getCellValue(row.getCell(8));

            if (customerDao.getCustomer(email, phone) != null) {

                listExistCustomer.add(new Customer(usernameC, passwordC, name, gender, dateOfBirth, email, phone, address, image));
            } else {

                listCustomer.add(new Customer(usernameC, passwordC, name, gender, dateOfBirth, email, phone, address, image));
            }

        }

        if (!listExistCustomer.isEmpty()) {
            request.setAttribute("listExistCustomer", listExistCustomer);
            request.getRequestDispatcher("importExcelCustomer.jsp").forward(request, response);

        }

        if (!listCustomer.isEmpty()) {
            customerDao.importExcelCustomer(listCustomer);
            request.setAttribute("mess", "Import successfully");
        } else {
            request.setAttribute("mess", "No customer imported");
        }

        request.getRequestDispatcher("importExcelCustomer.jsp").forward(request, response);

    }

    private Object getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();

            case Cell.CELL_TYPE_NUMERIC:
                return cell.getNumericCellValue();
        }
        return null;
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
