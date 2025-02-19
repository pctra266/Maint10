package Controller.Product;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import DAO.ProductDAO;
import Model.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // Giới hạn file tối đa 5MB
public class ImportExcel extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductDAO productDao = new ProductDAO();
        ArrayList<Product> listNewProducts = new ArrayList<>();
        ArrayList<Product> listDuplicateProducts = new ArrayList<>();

        Part productFile = request.getPart("productExcel");

        // Kiểm tra định dạng file
        String fileName = productFile.getSubmittedFileName().toLowerCase();
        if (!fileName.endsWith(".xlsx")) {
            request.setAttribute("errorMessage", "Please upload a .xlsx file.");
            request.getRequestDispatcher("Product/product.jsp").forward(request, response);
            return;
        }

        // Đọc file Excel
        InputStream inputStream = productFile.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();

        // Bỏ qua tiêu đề
        if (rows.hasNext()) rows.next();

        // Định dạng dữ liệu từ Excel
        DataFormatter formatter = new DataFormatter();
        while (rows.hasNext()) {
            Row row = rows.next();
            
            String code = formatter.formatCellValue(row.getCell(0)).trim();
            String productName = formatter.formatCellValue(row.getCell(1)).trim();
            int brandID = parseIntCell(row.getCell(2));
            String type = formatter.formatCellValue(row.getCell(3)).trim();
            int quantity = parseIntCell(row.getCell(4));
            int warrantyPeriod = parseIntCell(row.getCell(5));
            String status = formatter.formatCellValue(row.getCell(6)).trim();
            String image = formatter.formatCellValue(row.getCell(7)).trim();

            if (productDao.isProductCodeExists(code)) {
                listDuplicateProducts.add(new Product(code, productName, brandID, type, quantity, warrantyPeriod, status, image));
            } else {
                listNewProducts.add(new Product(code, productName, brandID, type, quantity, warrantyPeriod, status, image));
            }
        }

        workbook.close();

//        // Hiển thị danh sách sản phẩm trùng
//        if (!listDuplicateProducts.isEmpty()) {
//            request.setAttribute("productList", listDuplicateProducts);
//           request.getRequestDispatcher("Product/product.jsp").forward(request, response);
//            return;
//        }

        // Nhập sản phẩm mới vào database
        if (!listNewProducts.isEmpty()) {
            productDao.insertListProducts(listNewProducts);
            request.setAttribute("successMessage", "Import successfully!");
        } else {
            request.setAttribute("errorMessage", "No products imported.");
        }

        request.getRequestDispatcher("Product/product.jsp").forward(request, response);
    }

    // Chuyển đổi giá trị số thành int (tránh lỗi ép kiểu Double -> String)
    private int parseIntCell(Cell cell) {
        if (cell == null) return 0;
        try {
            return (int) cell.getNumericCellValue();
        } catch (Exception e) {
            return 0;
        }
    }
}
