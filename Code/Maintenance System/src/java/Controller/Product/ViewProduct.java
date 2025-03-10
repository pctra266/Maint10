package Controller.Product;

import DAO.ProductDAO;
import Model.Brand;
import Model.Product;
import Model.ProductType;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

@MultipartConfig(
        fileSizeThreshold = 2 * 1024 * 1024, // 2MB
        maxFileSize = 10 * 1024 * 1024, // 10MB
        maxRequestSize = 50 * 1024 * 1024 // 50MB
)
public class ViewProduct extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("update".equals(action)) {
            loadProductForUpdate(request, response);
        } else if ("add".equals(action)) {
            loadAddProductPage(request, response);
        } else if ("delete".equals(action)) {
            deleteProduct(request, response);
        } else {
            loadProductList(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            addProduct(request, response);
        } else if ("update".equals(action)) {
            updateProduct(request, response);
        }
    }

    // ========== [1] Hiển thị danh sách sản phẩm ==========
    private void loadProductList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Brand> listBrand = productDAO.getAllBrands();
        List<ProductType> listType = productDAO.getAllProductTypes();

        String errorMessage = null;

        String brandIdParam = request.getParameter("brandId");
        String searchName = request.getParameter("searchName") != null ? request.getParameter("searchName").trim() : "";
        String searchCode = request.getParameter("searchCode") != null ? request.getParameter("searchCode").trim() : "";

        String typeIdParam = request.getParameter("type");
        String sortQuantity = request.getParameter("sortQuantity") != null ? request.getParameter("sortQuantity").trim() : "";
        String sortWarranty = request.getParameter("sortWarranty") != null ? request.getParameter("sortWarranty").trim() : "";

        Integer brandId = (brandIdParam != null && !brandIdParam.isEmpty()) ? Integer.valueOf(brandIdParam) : null;
        Integer typeId = (typeIdParam != null && !typeIdParam.isEmpty()) ? Integer.valueOf(typeIdParam) : null;

        searchName = searchName.replaceAll("\\s+", " ");

        if (!searchName.matches("^[a-zA-Z0-9 ]*$")) {
            errorMessage = "Tên sản phẩm chỉ được chứa chữ cái, số và dấu cách!";
        } else if (!searchCode.matches("^[a-zA-Z0-9 ]*$")) {
            errorMessage = "Mã sản phẩm chỉ được chứa chữ cái, số và dấu cách!";
        }

        int page = 1;
        int recordsPerPage = 5; // Mặc định là 5

        // Lấy số trang hiện tại
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        // Xử lý recordsPerPage:
        // Nếu có tham số thì chuyển về số nguyên và kiểm tra phải là số nguyên dương
        if (request.getParameter("recordsPerPage") != null) {
            try {
                int tempRecords = Integer.parseInt(request.getParameter("recordsPerPage"));
                if (tempRecords < 1) {
                    recordsPerPage = 5;
                } else {
                    // Cho phép giá trị là 5, 10, 15, 20, 25 hoặc giá trị tùy chỉnh khác do người dùng nhập
                    recordsPerPage = tempRecords;
                }
            } catch (NumberFormatException e) {
                recordsPerPage = 5;
            }
        }

        int offset = (page - 1) * recordsPerPage;

        List<Product> productList = productDAO.searchProducts(searchName, searchCode, brandId, typeId, sortQuantity, sortWarranty, offset, recordsPerPage);
        int totalRecords = productDAO.getTotalProducts(searchName, searchCode, brandId, typeId);
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

        if (productList.isEmpty()) {
            errorMessage = "Không tìm thấy sản phẩm phù hợp với tìm kiếm của bạn.";
        }

        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("productList", productList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchName", searchName);
        request.setAttribute("searchCode", searchCode);
        request.setAttribute("brandID", brandId);
        request.setAttribute("productTypeId", typeId);
        request.setAttribute("listBrand", listBrand);
        request.setAttribute("sortQuantity", sortQuantity);
        request.setAttribute("sortWarranty", sortWarranty);
        request.setAttribute("listType", listType);
        request.setAttribute("recordsPerPage", recordsPerPage);

        request.getRequestDispatcher("Product/viewProduct.jsp").forward(request, response);
    }

    // ========== [2] Hiển thị form thêm sản phẩm ==========
    private void loadAddProductPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("listBrand", productDAO.getAllBrands());
        request.setAttribute("listType", productDAO.getAllProductTypes());
        request.getRequestDispatcher("Product/addProduct.jsp").forward(request, response);
    }

    // ========== [4] Thêm sản phẩm ==========
    private void addProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Brand> listBrand = productDAO.getAllBrands();
        List<ProductType> listType = productDAO.getAllProductTypes();
        // Lấy dữ liệu từ request
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        int brandId = Integer.parseInt(request.getParameter("brandId"));
        int typeId = Integer.parseInt(request.getParameter("type")); // product type
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int warrantyPeriod = Integer.parseInt(request.getParameter("warrantyPeriod"));
        String status = request.getParameter("status");

        List<String> tempImages = new ArrayList<>();

        // Nhận giá trị ảnh cũ từ hidden input (nếu có)
        String[] existingImages = request.getParameterValues("existingImages");
        if (existingImages != null) {
            for (String img : existingImages) {
                tempImages.add(img);
            }
        }

        String applicationPath = request.getServletContext().getRealPath("");
        String tempUploadDir = applicationPath + File.separator + "img" + File.separator + "tempUploads";
        File tempUploadDirFile = new File(tempUploadDir);
        if (!tempUploadDirFile.exists()) {
            tempUploadDirFile.mkdirs();
        }

        // ========== [A] Xử lý và validate ảnh mới ========== 
        boolean hasNewValidImage = false;
        for (Part part : request.getParts()) {
            String fileName = part.getSubmittedFileName();
            if (fileName != null && !fileName.isEmpty()) {
                String lowerCaseFileName = fileName.toLowerCase();
                if (lowerCaseFileName.endsWith(".jpg") || lowerCaseFileName.endsWith(".jpeg") || lowerCaseFileName.endsWith(".png")) {
                    hasNewValidImage = true;
                    // Lưu ảnh tạm thời
                    String tempFilePath = tempUploadDir + File.separator + fileName;
                    part.write(tempFilePath);
                    // Sử dụng đường dẫn tương đối để hiển thị trong JSP
                    tempImages.add("img/tempUploads/" + fileName);
                } else {
                    request.setAttribute("errorMessage", "Ảnh phải có định dạng JPG, JPEG hoặc PNG.");
                    request.setAttribute("code", code);
                    request.setAttribute("name", name);
                    request.setAttribute("brandId", brandId);
                    request.setAttribute("productTypeId", typeId);
                    request.setAttribute("quantity", quantity);
                    request.setAttribute("warrantyPeriod", warrantyPeriod);
                    request.setAttribute("status", status);
                    request.setAttribute("uploadedImages", tempImages);
                    request.setAttribute("listBrand", listBrand);
                    request.setAttribute("listType", listType);
                    request.getRequestDispatcher("Product/addProduct.jsp").forward(request, response);
                    return;
                }
            }
        }

        // Kiểm tra nếu không có ảnh mới và không có ảnh cũ
        if (!hasNewValidImage && tempImages.isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng tải lên ít nhất một ảnh sản phẩm.");
            request.setAttribute("code", code);
            request.setAttribute("name", name);
            request.setAttribute("brandId", brandId);
            request.setAttribute("productTypeId", typeId);
            request.setAttribute("quantity", quantity);
            request.setAttribute("warrantyPeriod", warrantyPeriod);
            request.setAttribute("status", status);
            request.setAttribute("listBrand", listBrand);
            request.setAttribute("listType", listType);
            request.getRequestDispatcher("Product/addProduct.jsp").forward(request, response);
            return;
        }

        // ========== [B] Kiểm tra mã sản phẩm đã tồn tại ==========
        if (productDAO.isProductCodeExists(code)) {
            request.setAttribute("errorMessage", "Mã sản phẩm đã tồn tại. Vui lòng nhập mã khác.");
            request.setAttribute("code", code);
            request.setAttribute("name", name);
            request.setAttribute("brandId", brandId);
            request.setAttribute("productTypeId", typeId);
            request.setAttribute("quantity", quantity);
            request.setAttribute("warrantyPeriod", warrantyPeriod);
            request.setAttribute("status", status);
            request.setAttribute("uploadedImages", tempImages);
            request.setAttribute("listBrand", listBrand);
            request.setAttribute("listType", listType);
            request.getRequestDispatcher("Product/addProduct.jsp").forward(request, response);
            return;
        }

        // ========== [C] Thêm sản phẩm vào database ==========
        Product product = new Product(code, name, brandId, quantity, warrantyPeriod, status, typeId);
        boolean success = productDAO.addProduct(product);
        if (!success) {
            request.setAttribute("errorMessage", "Thêm sản phẩm thất bại. Vui lòng thử lại.");
            request.setAttribute("code", code);
            request.setAttribute("name", name);
            request.setAttribute("brandId", brandId);
            request.setAttribute("productTypeId", typeId);
            request.setAttribute("quantity", quantity);
            request.setAttribute("warrantyPeriod", warrantyPeriod);
            request.setAttribute("status", status);
            request.setAttribute("uploadedImages", tempImages);
            request.setAttribute("listBrand", listBrand);
            request.setAttribute("listType", listType);
            request.getRequestDispatcher("Product/addProduct.jsp").forward(request, response);
            return;
        }

        // Lấy Product ID vừa insert
        int productId = productDAO.getProductIdByCode(code);

        // ========== [D] Di chuyển ảnh từ tempUploads sang thư mục Product và lưu vào database ==========
        String uploadDir = applicationPath + File.separator + "img" + File.separator + "Product";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        for (String tempImage : tempImages) {
            File tempFile = new File(applicationPath + File.separator + tempImage);
            if (tempFile.exists()) {
                File newFile = new File(uploadDir + File.separator + tempFile.getName());
                if (tempFile.renameTo(newFile)) {
                    String fileUrl = "img\\Product\\" + tempFile.getName();
                    productDAO.insertMedia(productId, "Product", fileUrl, "image");
                }
            }
        }

        // Sau khi xử lý xong, chuyển hướng tới trang danh sách sản phẩm hoặc thông báo thành công
        response.sendRedirect("viewProduct");
    }

    // ========== [3] Hiển thị thông tin cập nhật sản phẩm ==========
    private void loadProductForUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.getProductById(productId);
        request.setAttribute("product", product);
        request.setAttribute("listBrand", productDAO.getAllBrands());
        request.setAttribute("listType", productDAO.getAllProductTypes());

        request.getRequestDispatcher("Product/updateProduct.jsp").forward(request, response);
    }

    // ========== [5] Cập nhật sản phẩm ==========
    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("pid"));
            String productCode = request.getParameter("productCode");
            String productName = request.getParameter("productName");
            int brandId = Integer.parseInt(request.getParameter("brandId"));
            int productTypeId = Integer.parseInt(request.getParameter("type")); // trường 'type' từ JSP
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int warrantyPeriod = Integer.parseInt(request.getParameter("warrantyPeriod"));
            String status = request.getParameter("status");

            // Xử lý nhiều file upload
            Collection<Part> parts = request.getParts();
            List<String> imagePaths = new ArrayList<>();

            // Các biến kiểm tra lỗi
            String errorMessage = "";
            boolean error = false;

            // Lấy đường dẫn lưu ảnh trên server: thư mục /img/Product trong webapp
            String uploadPath = getServletContext().getRealPath("/img/Product");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Duyệt qua các Part, chỉ xử lý các file từ input có name "image"
            for (Part part : parts) {
                if (part.getName().equals("image") && part.getSize() > 0) {
                    String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    String lowerFileName = fileName.toLowerCase();
                    // Kiểm tra đuôi file, chỉ cho phép JPG và PNG
                    if (!(lowerFileName.endsWith(".jpg") || lowerFileName.endsWith(".png"))) {
                        errorMessage = "Chỉ cho phép tải lên file ảnh có đuôi JPG hoặc PNG.";
                        error = true;
                        break;
                    }
                    part.write(uploadPath + File.separator + fileName);
                    // Lưu đường dẫn tương đối (để hiển thị trên trang web)
                    imagePaths.add("img/Product/" + fileName);
                }
            }

            // Nếu không có ảnh nào được chọn
            if (!error && imagePaths.isEmpty()) {
                errorMessage = "Vui lòng chọn ít nhất một ảnh cho sản phẩm.";
                error = true;
            }

            // Nếu có lỗi thì load lại dữ liệu cũ và forward sang trang update
            if (error) {
                Product currentProduct = productDAO.getProductById(productId);
                request.setAttribute("product", currentProduct);
                request.setAttribute("listBrand", productDAO.getAllBrands());
                request.setAttribute("listType", productDAO.getAllProductTypes());
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("Product/updateProduct.jsp").forward(request, response);
                return;
            }

            // Tạo đối tượng Product từ dữ liệu nhận được
            Product product = new Product();
            product.setProductId(productId);
            product.setCode(productCode);
            product.setProductName(productName);
            product.setBrandId(brandId);
            product.setProductTypeId(productTypeId);
            product.setQuantity(quantity);
            product.setWarrantyPeriod(warrantyPeriod);
            product.setStatus(status);

            boolean updated = productDAO.updateProduct(product);
            if (updated) {
                // Nếu có ảnh mới được upload, cập nhật vào bảng Media
                if (!imagePaths.isEmpty()) {
                    productDAO.updateProductImages(productId, imagePaths);
                }
                Product currentProduct = productDAO.getProductById(productId);
                request.setAttribute("product", currentProduct);
                request.setAttribute("listBrand", productDAO.getAllBrands());
                request.setAttribute("listType", productDAO.getAllProductTypes());
                request.setAttribute("successMessage", "Update Successfull!");
                request.getRequestDispatcher("Product/updateProduct.jsp").forward(request, response);
            } else {
                // Nếu cập nhật thất bại, load lại dữ liệu cũ và thông báo lỗi
                Product currentProduct = productDAO.getProductById(productId);
                request.setAttribute("product", currentProduct);
                request.setAttribute("errorMessage", "Cập nhật sản phẩm thất bại!");
                request.getRequestDispatcher("Product/updateProduct.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Dữ liệu gửi lên không hợp lệ!");
            request.getRequestDispatcher("Product/updateProduct.jsp").forward(request, response);
        }
    }

    // ========== [6] Xóa sản phẩm ==========
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        if (productDAO.deactivateProduct(productId)) {
            response.sendRedirect("viewProduct");
        } else {
            request.setAttribute("errorMessage", "Xóa sản phẩm thất bại.");
            loadProductList(request, response);
        }
    }
}
