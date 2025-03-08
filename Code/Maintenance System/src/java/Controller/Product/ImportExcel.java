package Controller.Product;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import DAO.ProductDAO;
import Model.Product;
import java.io.PrintWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class ImportExcel extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductDAO productDao = new ProductDAO();
        ArrayList<Product> listNewProducts = new ArrayList<>();
        ArrayList<Product> listDuplicateProducts = new ArrayList<>();

        Part productFile = request.getPart("productExcel");

        // Kiểm tra định dạng file (phải là .xlsx)
        String fileName = productFile.getSubmittedFileName().toLowerCase();
        if (!fileName.endsWith(".xlsx")) {
            request.setAttribute("errorMessage", "Please upload a .xlsx file.");
            request.getRequestDispatcher("Product/viewProduct.jsp").forward(request, response);
            return;
        }

        InputStream inputStream = productFile.getInputStream();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // Kiểm tra xem file có trống không
            if (!rows.hasNext()) {
                request.setAttribute("errorMessage", "Excel file is empty.");
                request.getRequestDispatcher("Product/viewProduct.jsp").forward(request, response);
                return;
            }
            PrintWriter out = response.getWriter();
            // Đọc dòng tiêu đề và kiểm tra số cột
            Row headerRow = rows.next();
            DataFormatter formatter = new DataFormatter();
            int count = 0;
            for (Cell cell : headerRow) {
                if (!formatter.formatCellValue(cell).trim().isEmpty()) {
                    count++;
                }
            }
            int expectedColumnCount = 8; //code, productName, brandID, typeID, quantity, warrantyPeriod, status, image

            if (count != expectedColumnCount) {
                request.setAttribute("errorMessage", "Excel file has extra columns. Please check again.");
                request.getRequestDispatcher("Product/viewProduct.jsp").forward(request, response);
                return;
            }

            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
            Set<String> fileProductCodes = new HashSet<>();
            for (int i = firstRowNum + 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    // Dòng này không được định nghĩa (trống hoàn toàn)
                    request.setAttribute("errorMessage", "Row " + (i + 1) + " in file is empty. Excel file format is not correct.");
                    request.getRequestDispatcher("Product/viewProduct.jsp").forward(request, response);
                    return;
                }

                // Kiểm tra xem dòng này có toàn ô trống không
                boolean rowEmpty = true;
                for (int j = 0; j < expectedColumnCount; j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null && !formatter.formatCellValue(cell).trim().isEmpty()) {
                        rowEmpty = false;
                        break;
                    }
                }

                if (rowEmpty) {
                    request.setAttribute("errorMessage", "Row " + (i + 1) + " is empty. Excel file format is not correct.");
                    request.getRequestDispatcher("Product/viewProduct.jsp").forward(request, response);
                    return;
                }
                // Lấy dữ liệu từ các cột
                String code = formatter.formatCellValue(row.getCell(0)).trim();
                String productName = formatter.formatCellValue(row.getCell(1)).trim();
                int brandID = parseIntCell(row.getCell(2));
                int typeID = parseIntCell(row.getCell(3));
                int quantity = parseIntCell(row.getCell(4));
                int warrantyPeriod = parseIntCell(row.getCell(5));
                String status = formatter.formatCellValue(row.getCell(6)).trim();
                String image = formatter.formatCellValue(row.getCell(7)).trim();

                // Kiểm tra duplicate code trong file Excel
                if (fileProductCodes.contains(code)) {
                    request.setAttribute("errorMessage", "Duplicate product code found in Excel file: " + code);
                    request.getRequestDispatcher("Product/viewProduct.jsp").forward(request, response);
                    return;
                } else {
                    fileProductCodes.add(code);
                }
                // Kiểm tra xem sản phẩm có bị duplicate theo database không
                if (productDao.isProductCodeExists(code)) {
                    listDuplicateProducts.add(new Product(code, productName, brandID, quantity, warrantyPeriod, status, image, typeID));
                } else {
                    listNewProducts.add(new Product(code, productName, brandID, quantity, warrantyPeriod, status, image, typeID));
                }
            }
        }

        // Quyết định import dựa trên danh sách sản phẩm mới và bị duplicate
        if (!listDuplicateProducts.isEmpty() && listNewProducts.isEmpty()) {
            request.setAttribute("errorMessage", "No products imported because all products are duplicate codes in database.");
        } else if (listDuplicateProducts.isEmpty() && !listNewProducts.isEmpty()) {
            productDao.insertListProducts(listNewProducts);
            request.setAttribute("successMessage", "Import successfully!");
        } else if (listDuplicateProducts.isEmpty() && listNewProducts.isEmpty()) {
            request.setAttribute("errorMessage", "No products imported.");
        } else {
            productDao.insertListProducts(listNewProducts);
            request.setAttribute("errorMessage", "There are " + listDuplicateProducts.size() + " products duplicate code in database.");
            request.setAttribute("successMessage", "Import successfully " + listNewProducts.size() + " products.");
        }
        request.getRequestDispatcher("Product/viewProduct.jsp").forward(request, response);
    }

    // Phương thức chuyển đổi giá trị số thành int (tránh lỗi ép kiểu từ Double -> int)
    private int parseIntCell(Cell cell) {
        if (cell == null) {
            return 0;
        }
        try {
            return (int) cell.getNumericCellValue();
        } catch (Exception e) {
            return 0;
        }
    }
}
