<%-- 
    Document   : updateProduct1
    Created on : Feb 6, 2025, 5:07:44 AM
    Author     : sonNH
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            .container {
                display: flex;
                justify-content: space-between;
                max-width: 3000px;
                border: 1px solid #ddd;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                padding: 10px;
            }
            .form-container {
                width: 50%;
                padding-right: 20px;
            }
            .form-container label {
                display: block;
                margin-top: 10px;
            }
            .form-container input,
            .form-container select {
                width: 100%;
                padding: 8px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }
            .image-container {
                width: 50%;
                display: flex;
                flex-direction: column;
                align-items: center;
            }
            .image-preview-wrapper {
                position: relative;
                width: 100%;
                max-width: 300px;
                max-height: 300px;
                overflow: hidden; /* ẩn phần thừa nếu ảnh vượt khung */
                margin-bottom: 20px;
            }
            #imagePreviewContainer img {
                width: 100%;
                height: auto;
                object-fit: cover;
                display: none; /* ẩn mặc định, sẽ hiển thị bằng JS */
                border-radius: 10px;
            }
            .arrow {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                background: rgba(0, 0, 0, 0.5);
                color: white;
                border: none;
                padding: 10px;
                cursor: pointer;
            }
            .prev {
                left: 0;
            }
            .next {
                right: 0;
            }
            .file-update-container {
                margin-top: 20px;
                display: flex;
                flex-direction: column;
                align-items: center;
            }
            .buttons {
                margin-top: 20px;
                display: flex;
                justify-content: center;
                gap: 10px;
            }
            button {
                padding: 10px 15px;
                border: none;
                background-color: #007bff;
                color: white;
                cursor: pointer;
                border-radius: 5px;
            }
            button:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <c:if test="${not empty errorMessage}">
                        <div style="color: red; text-align: center; margin-bottom: 10px;">
                            ${errorMessage}
                        </div>
                    </c:if>
                    <c:if test="${not empty successMessage}">
                        <div style="color: red; text-align: center; margin-bottom: 10px;">
                            ${successMessage}
                        </div>
                    </c:if>

                    <!-- Form bao quanh cả 2 cột -->
                    <form id="updateProductForm" action="viewProduct" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="pid" value="${product.productId}">
                        <input type="hidden" name="status" value="${product.status}">
                        
                        <div class="container">
                            <!-- Cột bên trái: các trường thông tin sản phẩm -->
                            <div class="form-container">
                                <h2>Update Product</h2>
                                <label for="productCode">Product Code:</label>
                                <input type="text" id="productCode" name="productCode" value="${product.code}" required oninput="validateProductCode()">
                                <span id="productCodeError" style="color: red;"></span>

                                <label for="productName">Product Name:</label>
                                <input type="text" id="productName" name="productName" value="${product.productName}" required oninput="validateProductName()">
                                <span id="productNameError" style="color: red;"></span>

                                <label for="brandId">Brand:</label>
                                <select name="brandId" id="brandId" required>
                                    <c:forEach var="brand" items="${listBrand}">
                                        <option value="${brand.brandId}" 
                                            ${product.brandId == brand.brandId ? 'selected' : ''}>
                                            ${brand.brandName}
                                        </option>
                                    </c:forEach>
                                </select>

                                <label for="typeId">Type:</label> 
                                <select name="type" id="typeId" required>
                                    <option value="">All Types</option>
                                    <c:forEach var="t" items="${listType}">
                                        <option value="${t.productTypeId}" 
                                            ${product.productTypeId == t.productTypeId ? 'selected' : ''}>
                                            ${t.typeName}
                                        </option>
                                    </c:forEach>
                                </select>

                                <label for="quantity">Quantity:</label>
                                <input type="number" id="quantity" name="quantity" value="${product.quantity}" min="1" required>

                                <label for="warrantyPeriod">Warranty Period (Months):</label>
                                <input type="number" id="warrantyPeriod" name="warrantyPeriod" min="1" value="${product.warrantyPeriod}" required>
                            </div>

                            <!-- Cột bên phải: preview ảnh, chọn ảnh và nút submit -->
                            <div class="image-container">
                                <!-- Vùng hiển thị slideshow ảnh -->
                                <div class="image-preview-wrapper">
                                    <div id="imagePreviewContainer">
                                        <!-- Hiển thị ảnh cũ của product (nếu có) -->
                                        <c:if test="${not empty product.images}">
                                            <c:forEach var="img" items="${product.images}">
                                                <img src="${img}" alt="Product Image" class="image-preview">
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                    <button type="button" class="arrow prev" onclick="previousImage()">&#10094;</button>
                                    <button type="button" class="arrow next" onclick="nextImage()">&#10095;</button>
                                </div>
                                <!-- Chọn ảnh mới và nút update -->
                                <div class="file-update-container">
                                    <label for="newImage">Product Images:</label>
                                    <input type="file" name="image" id="newImage" 
                                           accept="image/jpeg, image/png" multiple onchange="previewImages(event)">
                                    <div class="buttons">
                                        <button type="button" class="back-btn" 
                                                onclick="location.href = 'viewProduct'">
                                            Back
                                        </button>
                                        <button type="submit" id="submitBtn" onclick="submitForm(event)">
                                            Update Product
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <!-- app.js của bạn (nếu có) -->
        <script src="js/app.js"></script>
        
        <!-- Script xử lý validation, preview và slideshow -->
        <script>
            //======================== VALIDATION ========================
            function validateProductCode() {
                const productCode = document.getElementById("productCode").value.trim();
                const errorSpan = document.getElementById("productCodeError");
                const regex = /^[a-zA-Z0-9]+$/; // Chỉ cho phép chữ cái và số

                if (!productCode) {
                    errorSpan.textContent = "Product code không được để trống.";
                } else if (!regex.test(productCode)) {
                    errorSpan.textContent = "Product code chỉ được chứa chữ cái và số.";
                } else {
                    errorSpan.textContent = "";
                }
                validateForm();
            }

            function validateProductName() {
                const productName = document.getElementById("productName").value.trim();
                const errorSpan = document.getElementById("productNameError");
                // Chỉ cho phép chữ cái, số và khoảng trắng (mỗi từ cách nhau 1 space)
                const regex = /^[a-zA-Z0-9]+(\s[a-zA-Z0-9]+)*$/;

                if (!productName) {
                    errorSpan.textContent = "Product name không được để trống.";
                } else if (!regex.test(productName)) {
                    errorSpan.textContent = "Product name chỉ được chứa chữ cái, số và khoảng trắng.";
                } else {
                    errorSpan.textContent = "";
                }
                validateForm();
            }

            function validateForm() {
                const productCodeError = document.getElementById("productCodeError").textContent;
                const productNameError = document.getElementById("productNameError").textContent;
                const submitBtn = document.getElementById("submitBtn");

                // Nếu còn lỗi thì disable nút submit
                if (productCodeError || productNameError) {
                    submitBtn.disabled = true;
                } else {
                    submitBtn.disabled = false;
                }
            }

            //======================== CHỌN FILE (GIỚI HẠN KÍCH THƯỚC) ========================
            document.getElementById("newImage").addEventListener("change", function () {
                let file = this.files[0];
                if (file) {
                    let maxSize = 5 * 1024 * 1024; // 5MB
                    if (file.size > maxSize) {
                        alert("File không được vượt quá 5MB!");
                        this.value = "";
                    }
                }
            });

            //======================== PREVIEW ẢNH VÀ HIỂN THỊ SLIDESHOW ========================
            let currentImageIndex = 0;

            function previewImages(event) {
                const files = event.target.files;
                const previewContainer = document.getElementById('imagePreviewContainer');
                previewContainer.innerHTML = ""; // Xóa các ảnh cũ
                currentImageIndex = 0;           // Reset index

                // Nếu không chọn file thì thoát
                if (!files || files.length === 0) {
                    return;
                }

                // Đọc từng file và tạo thẻ img
                Array.from(files).forEach((file, idx) => {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        const img = document.createElement("img");
                        img.src = e.target.result;
                        img.className = "image-preview";
                        // Mặc định ẩn ảnh
                        img.style.display = "none";
                        previewContainer.appendChild(img);

                        // Khi đã load hết ảnh, hiển thị ảnh đầu tiên
                        if (idx === files.length - 1) {
                            showImage(0);
                        }
                    };
                    reader.readAsDataURL(file);
                });
            }

            // Hàm hiển thị 1 ảnh tại chỉ số index, ẩn các ảnh còn lại
            function showImage(index) {
                const images = document.querySelectorAll('#imagePreviewContainer img');
                if (images.length === 0) return;

                // Kiểm soát chỉ số ảnh
                if (index < 0) {
                    currentImageIndex = images.length - 1;
                } else if (index >= images.length) {
                    currentImageIndex = 0;
                } else {
                    currentImageIndex = index;
                }

                // Ẩn tất cả, chỉ hiện ảnh ở currentImageIndex
                images.forEach((img, idx) => {
                    img.style.display = (idx === currentImageIndex) ? 'block' : 'none';
                });
            }

            // Nút prev
            function previousImage() {
                showImage(currentImageIndex - 1);
            }

            // Nút next
            function nextImage() {
                showImage(currentImageIndex + 1);
            }

            //======================== GỬI FORM ========================
            function submitForm(e) {
                e.preventDefault(); 
                // Thực hiện các bước kiểm tra khác (nếu cần)...
                document.getElementById("updateProductForm").submit();
            }

            // Khi load trang, nếu có ảnh cũ -> hiển thị ảnh đầu
            window.onload = function () {
                showImage(0);
            };
        </script>
    </body>
</html>
