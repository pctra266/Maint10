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
            }
            .form-container {
                width: 100%;
                padding-right: 20px;
            }
            .form-container form label {
                display: block;
                margin-top: 10px;
            }
            .form-container form input,
            .form-container form select {
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
                max-width: 300px;     /* Giới hạn bề ngang */
                max-height: 300px;    /* Giới hạn bề cao */
                overflow: hidden;     /* Ẩn phần ảnh vượt khung */
                margin-bottom: 20px;  /* Tạo khoảng trống bên dưới */
            }
            /* Ảnh sẽ co/zoom vừa khung mà không vượt quá kích thước */
            #imagePreviewContainer img {
                width: 100%;
                height: auto;
                object-fit: cover;    /* Cắt ảnh cho vừa khung, tránh vỡ layout */
                display: block;
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
            main.buttons {
                margin-top: 20px;
                display: flex;
                justify-content: center;
                gap: 10px;
            }
            main button {
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

                    <div class="container">
                        <!-- Cột form nhập thông tin -->
                        <div class="form-container">
                            <h2>Update Product</h2>
                            <form action="viewProduct" method="post" enctype="multipart/form-data">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="pid" value="${product.productId}">
                                <input type="hidden" name="status" value="${product.status}">

                                <label for="productCode">Product Code:</label>
                                <input type="text" id="productCode" name="productCode" value="${product.code}" required oninput="validateProductCode()">
                                <span id="productCodeError" style="color: red;"></span>

                                <label for="productName">Product Name:</label>
                                <input type="text" id="productName" name="productName" value="${product.productName}" required oninput="validateProductName()">
                                <span id="productNameError" style="color: red;"></span>

                                <label for="brandId">Brand:</label>
                                <select name="brandId" id="brandId" required>
                                    <c:forEach var="brand" items="${listBrand}">
                                        <option value="${brand.brandId}" ${product.brandId == brand.brandId ? 'selected' : ''}>
                                            ${brand.brandName}
                                        </option>
                                    </c:forEach>
                                </select>

                                <label for="typeId">Type:</label> 
                                <select name="type" id="typeId" required>
                                    <option value="">All Types</option>
                                    <c:forEach var="t" items="${listType}">
                                        <option value="${t.productTypeId}"${ product.productTypeId == t.productTypeId ? 'selected' : ''}>
                                            ${t.typeName}
                                        </option>
                                    </c:forEach>
                                </select>

                                <label for="quantity">Quantity:</label>
                                <input type="number" id="quantity" name="quantity" value="${product.quantity}" min="1" required>

                                <label for="warrantyPeriod">Warranty Period (Months):</label>
                                <input type="number" id="warrantyPeriod" name="warrantyPeriod" min="1" value="${product.warrantyPeriod}" required>

                                <!-- Input file vẫn nằm trong form để submit được dữ liệu -->
                                <label for="newImage">Product Images:</label>
                                <input type="file" name="image" id="newImage" accept="image/jpeg, image/png" multiple onchange="previewImages(event)">

                            </form>
                        </div>
                        <!-- Cột hiển thị ảnh kèm mũi tên điều hướng -->
                        <div class="image-container">
                            <div class="image-preview-wrapper">
                                <div id="imagePreviewContainer">
                                    <c:if test="${not empty product.images}">
                                        <c:forEach var="img" items="${product.images}">
                                            <img src="${img}" alt="Product Image" class="image-preview">
                                        </c:forEach>
                                    </c:if>
                                </div>
                                <button class="arrow prev" onclick="previousImage()">&#10094;</button>
                                <button class="arrow next" onclick="nextImage()">&#10095;</button>
                            </div>
                            <div class="buttons">
                                <button type="button" class="back-btn" onclick="location.href = 'viewProduct'">Back</button>

                                <button type="submit" id="submitBtn">Update Product</button>
                            </div>
                        </div>
                    </div>

                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
        <script>
                                    function validateProductCode() {
                                        const productCode = document.getElementById("productCode").value.trim();
                                        const errorSpan = document.getElementById("productCodeError");
                                        const regex = /^[a-zA-Z0-9]+$/; // Chỉ cho phép chữ cái và số

                                        if (!productCode) {
                                            errorSpan.textContent = "Product code không được để trống hoặc chỉ chứa dấu cách.";
                                        } else if (!regex.test(productCode)) {
                                            errorSpan.textContent = "Product code chỉ được chứa chữ cái và số, không có dấu cách hoặc ký tự đặc biệt.";
                                        } else {
                                            errorSpan.textContent = "";
                                        }
                                        validateForm();
                                    }

                                    function validateProductName() {
                                        const productName = document.getElementById("productName").value.trim();
                                        const errorSpan = document.getElementById("productNameError");
                                        const regex = /^[a-zA-Z0-9]+(\s[a-zA-Z0-9]+)*$/; // Chỉ cho phép chữ cái, số và khoảng trắng hợp lệ

                                        if (!productName) {
                                            errorSpan.textContent = "Product name không được để trống hoặc chỉ chứa dấu cách.";
                                        } else if (!regex.test(productName)) {
                                            errorSpan.textContent = "Product name chỉ được chứa chữ cái, số và mỗi từ chỉ có 1 dấu cách giữa.";
                                        } else {
                                            errorSpan.textContent = "";
                                        }
                                        validateForm();
                                    }

                                    function validateForm() {
                                        const productCodeError = document.getElementById("productCodeError").textContent;
                                        const productNameError = document.getElementById("productNameError").textContent;
                                        const submitBtn = document.getElementById("submitBtn");

                                        if (productCodeError || productNameError) {
                                            submitBtn.disabled = true;
                                        } else {
                                            submitBtn.disabled = false;
                                        }
                                    }

                                    document.getElementById("newImage").addEventListener("change", function () {
                                        let file = this.files[0]; // Lấy file được chọn
                                        if (file) {
                                            let maxSize = 5 * 1024 * 1024; // 5MB
                                            if (file.size > maxSize) {
                                                alert("File không được vượt quá 5MB!");
                                                this.value = ""; // Reset input file nếu file quá lớn
                                            }
                                        }
                                    });

                                    function previewImages(event) {
                                        const files = event.target.files;
                                        const previewContainer = document.getElementById('imagePreviewContainer');
                                        previewContainer.innerHTML = ""; // Xóa các ảnh preview cũ

                                        if (files) {
                                            Array.from(files).forEach(file => {
                                                const reader = new FileReader();
                                                reader.onload = function (e) {
                                                    const img = document.createElement("img");
                                                    img.src = e.target.result;
                                                    img.className = "image-preview";
                                                    previewContainer.appendChild(img);
                                                };
                                                reader.readAsDataURL(file);
                                            });
                                        }
                                    }

                                    // Chức năng điều hướng ảnh
                                    let currentImageIndex = 0;
                                    function showImage(index) {
                                        const images = document.querySelectorAll('#imagePreviewContainer img');
                                        if (images.length === 0)
                                            return;
                                        if (index < 0) {
                                            currentImageIndex = images.length - 1;
                                        } else if (index >= images.length) {
                                            currentImageIndex = 0;
                                        } else {
                                            currentImageIndex = index;
                                        }
                                        images.forEach((img, idx) => {
                                            img.style.display = (idx === currentImageIndex) ? 'block' : 'none';
                                        });
                                    }
                                    function previousImage() {
                                        showImage(currentImageIndex - 1);
                                    }
                                    function nextImage() {
                                        showImage(currentImageIndex + 1);
                                    }
                                    window.onload = function () {
                                        showImage(0);
                                    };
        </script>
    </body>
</html>
