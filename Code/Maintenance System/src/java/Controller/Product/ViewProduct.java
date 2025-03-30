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

    // ========== [1] Display product list ==========
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
            errorMessage = "Product name can only contain letters, numbers, and spaces!";
        } else if (!searchCode.matches("^[a-zA-Z0-9 ]*$")) {
            errorMessage = "Product code can only contain letters, numbers, and spaces!";
        }

        int page = 1;
        int recordsPerPage = 5;

        // Get current page number
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

        // Process recordsPerPage:
        // If parameter exists, convert to integer and check that it is a positive integer
        if (request.getParameter("recordsPerPage") != null) {
            try {
                int tempRecords = Integer.parseInt(request.getParameter("recordsPerPage"));
                if (tempRecords < 1) {
                    recordsPerPage = 5;
                } else {
                    // Allow values of 5, 10, 15, 20, 25 or a custom value entered by the user
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
            errorMessage = "No products matching your search were found.";
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

    // ========== [2] Display add product form ==========
    private void loadAddProductPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("listBrand", productDAO.getAllBrands());
        request.setAttribute("listType", productDAO.getAllProductTypes());
        request.getRequestDispatcher("Product/addProduct.jsp").forward(request, response);
    }

    // ========== [4] Add product ==========
    private void addProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Brand> listBrand = productDAO.getAllBrands();
        List<ProductType> listType = productDAO.getAllProductTypes();
        // Get data from the request
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        int brandId = Integer.parseInt(request.getParameter("brandId"));
        int typeId = Integer.parseInt(request.getParameter("type")); // product type

        // ========== [Validate quantity and warranty period] ==========
        int quantity = 0;
        int warrantyPeriod = 0;
        try {
            quantity = Integer.parseInt(request.getParameter("quantity"));
            warrantyPeriod = Integer.parseInt(request.getParameter("warrantyPeriod"));
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Quantity or warranty period is too large or invalid.");
            request.setAttribute("code", code);
            request.setAttribute("name", name);
            request.setAttribute("brandId", brandId);
            request.setAttribute("productTypeId", typeId);
            request.setAttribute("quantity", request.getParameter("quantity"));
            request.setAttribute("warrantyPeriod", request.getParameter("warrantyPeriod"));
            request.setAttribute("status", request.getParameter("status"));
            request.setAttribute("listBrand", listBrand);
            request.setAttribute("listType", listType);
            request.getRequestDispatcher("Product/addProduct.jsp").forward(request, response);
            return;
        }
        String status = request.getParameter("status");

        List<String> tempImages = new ArrayList<>();

        // Get existing image values from hidden input (if any)
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

        // ========== [A] Process and validate new images ==========
        boolean hasNewValidImage = false;
        for (Part part : request.getParts()) {
            String fileName = part.getSubmittedFileName();
            if (fileName != null && !fileName.isEmpty()) {
                String lowerCaseFileName = fileName.toLowerCase();
                if (lowerCaseFileName.endsWith(".jpg") || lowerCaseFileName.endsWith(".jpeg") || lowerCaseFileName.endsWith(".png")) {
                    hasNewValidImage = true;
                    // Save temporary image
                    String tempFilePath = tempUploadDir + File.separator + fileName;
                    part.write(tempFilePath);
                    // Use relative path for display in JSP
                    tempImages.add("img/tempUploads/" + fileName);
                } else {
                    request.setAttribute("errorMessage", "Images must be in JPG, JPEG, or PNG format.");
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

        // Check if no new image and no existing images
        if (!hasNewValidImage && tempImages.isEmpty()) {
            request.setAttribute("errorMessage", "Please upload at least one product image.");
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

        // ========== [B] Check if product code already exists ==========
        if (productDAO.isProductCodeExists(code)) {
            request.setAttribute("errorMessage", "Product code already exists. Please enter a different code.");
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

        // ========== [C] Add product to the database ==========
        Product product = new Product(code, name, brandId, quantity, warrantyPeriod, status, typeId);
        boolean success = productDAO.addProduct(product);
        if (!success) {
            request.setAttribute("errorMessage", "Failed to add product. Please try again.");
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

        // Get the Product ID that was just inserted
        int productId = productDAO.getProductIdByCode(code);

        // ========== [D] Move images from tempUploads to the Product folder and save in the database ==========
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

        // After processing, redirect to the product list page
        response.sendRedirect("viewProduct");
    }

    // ========== [3] Display product update information ==========
    private void loadProductForUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        int productId;
        try {
            // Kiểm tra id có phải là số hay không
            productId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            // Nếu id không hợp lệ -> chuyển hướng tới trang 404
            request.getRequestDispatcher("404Page.jsp").forward(request, response);
            return;
        }

        // Lấy thông tin sản phẩm từ CSDL
        Product product = productDAO.getProductById(productId);
        // Nếu sản phẩm không tồn tại hoặc id vượt quá giá trị cho phép
        if (product == null) {
            request.getRequestDispatcher("404Page.jsp").forward(request, response);
            return;
        }

        request.setAttribute("product", product);
        request.setAttribute("listBrand", productDAO.getAllBrands());
        request.setAttribute("listType", productDAO.getAllProductTypes());
        request.getRequestDispatcher("Product/updateProduct.jsp").forward(request, response);
    }
    // ========== [5] Update product ==========
    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int productId = Integer.parseInt(request.getParameter("pid"));
            String productCode = request.getParameter("productCode");
            String productName = request.getParameter("productName");
            int brandId = Integer.parseInt(request.getParameter("brandId"));
            int productTypeId = Integer.parseInt(request.getParameter("type")); // 'type' field from JSP

            // ========== [Validate quantity and warranty period] ==========
            int quantity = 0;
            int warrantyPeriod = 0;
            try {
                quantity = Integer.parseInt(request.getParameter("quantity"));
                warrantyPeriod = Integer.parseInt(request.getParameter("warrantyPeriod"));
            } catch (NumberFormatException e) {
                Product currentProduct = productDAO.getProductById(productId);
                request.setAttribute("errorMessage", "Quantity or warranty period is too large or invalid.");
                request.setAttribute("product", currentProduct);
                request.setAttribute("listBrand", productDAO.getAllBrands());
                request.setAttribute("listType", productDAO.getAllProductTypes());
                request.getRequestDispatcher("Product/updateProduct.jsp").forward(request, response);
                return;
            }
            String status = request.getParameter("status");

            // Retrieve the current product from the database
            Product currentProduct = productDAO.getProductById(productId);
            // If the product code is changed, check for duplicate code
            if (!productCode.equals(currentProduct.getCode()) && productDAO.isProductCodeExists(productCode)) {
                request.setAttribute("errorMessage", "Product code already exists. Please enter a different code.");
                request.setAttribute("product", currentProduct);
                request.setAttribute("listBrand", productDAO.getAllBrands());
                request.setAttribute("listType", productDAO.getAllProductTypes());
                request.getRequestDispatcher("Product/updateProduct.jsp").forward(request, response);
                return;
            }

            // Handle multiple file uploads
            Collection<Part> parts = request.getParts();
            List<String> imagePaths = new ArrayList<>();

            // Error checking variables
            String errorMessage = "";
            boolean error = false;

            String uploadPath = getServletContext().getRealPath("/img/Product");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            for (Part part : parts) {
                if (part.getName().equals("image") && part.getSize() > 0) {
                    String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    String lowerFileName = fileName.toLowerCase();
                    if (!(lowerFileName.endsWith(".jpg") || lowerFileName.endsWith(".png"))) {
                        errorMessage = "Only JPG or PNG image files are allowed.";
                        error = true;
                        break;
                    }
                    part.write(uploadPath + File.separator + fileName);
                    imagePaths.add("img/Product/" + fileName);
                }
            }
            // Note: No check for imagePaths.isEmpty() is made here.
            // If no new image is uploaded, the update will simply keep the existing images.

            if (error) {
                currentProduct = productDAO.getProductById(productId);
                request.setAttribute("product", currentProduct);
                request.setAttribute("listBrand", productDAO.getAllBrands());
                request.setAttribute("listType", productDAO.getAllProductTypes());
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("Product/updateProduct.jsp").forward(request, response);
                return;
            }

            // Create a Product object from the received data
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
                // If new images are uploaded, update the Media table
                if (!imagePaths.isEmpty()) {
                    productDAO.updateProductImages(productId, imagePaths);
                }
                Product updatedProduct = productDAO.getProductById(productId);
                request.setAttribute("product", updatedProduct);
                request.setAttribute("listBrand", productDAO.getAllBrands());
                request.setAttribute("listType", productDAO.getAllProductTypes());
                request.setAttribute("successMessage", "Update Successful!");
                request.getRequestDispatcher("Product/updateProduct.jsp").forward(request, response);
            } else {
                // If update fails, reload existing data and show error message
                currentProduct = productDAO.getProductById(productId);
                request.setAttribute("product", currentProduct);
                request.setAttribute("errorMessage", "Product update failed!");
                request.getRequestDispatcher("Product/updateProduct.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Submitted data is invalid!");
            request.getRequestDispatcher("Product/updateProduct.jsp").forward(request, response);
        }
    }

    // ========== [6] Delete product ==========
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        if (productDAO.deactivateProduct(productId)) {
            response.sendRedirect("viewProduct");
        } else {
            request.setAttribute("errorMessage", "Product deletion failed.");
            loadProductList(request, response);
        }
    }
}
