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
            main.content {
                background-color: #f8f9fa;
                padding: 20px;
                border-radius: 12px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                max-width: 800px;
                margin: 20px auto;
                font-family: 'Inter', sans-serif;
            }

            main.content form {
                display: flex;
                flex-direction: column;
                gap: 5px;
            }

            main.content label {
                font-weight: 600;
                color: #333;
                margin-bottom: 5px;
            }

            main.content input,
            main.content select {
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 8px;
                font-size: 16px;
                transition: all 0.3s ease-in-out;
            }

            main.content input:focus,
            main.content select:focus {
                border-color: #007bff;
                box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
                outline: none;
            }

            main.content img {
                display: block;
                margin: 10px auto;
                max-width: 100%;
                border-radius: 8px;
            }

            #submitBtn {
                padding: 12px;
                background: linear-gradient(135deg, #007bff, #0056b3);
                border: none;
                border-radius: 8px;
                font-size: 16px;
                color: #fff;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            main.content.submit-btn button:hover {
                background: linear-gradient(135deg, #0056b3, #003c7a);
                transform: scale(1.05);
            }

            main.content .add-product {
                background: #28a745;
            }

            main.content .add-product:hover {
                background: #218838;
            }

            main.content h2{
                text-align: center;
            }

            .image-preview-container {
                position: relative;
                display: inline-block;
            }

            .image-preview-container img {
                display: block;
                max-width: 100%;
                height: auto;
                border-radius: 8px;
            }

            .clear-btn {
                position: absolute;
                top: 5px;
                right: 5px;
                background: rgba(220, 53, 69, 0.8);
                color: white;
                border: none;
                border-radius: 50%;
                width: 24px;
                height: 24px;
                font-size: 14px;
                font-weight: bold;
                cursor: pointer;
                display: none; /* Mặc định ẩn */
                transition: all 0.3s ease;
            }

            .clear-btn:hover {
                background: rgba(200, 35, 50, 1);
                transform: scale(1.1);
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

                    <h2>Update Product</h2>

                    <form action="viewProduct" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="update">

                        <input type="hidden" name="pid" value="${product.productId}">
                        <input type="hidden" name="status" value="${product.status}">
                        <input type="hidden" name="existingImage" value="${product.image}">

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

                        <!-- Nhập type -->
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

                        <label for="newImage">Product Image:</label>
                        <div class="image-preview-container">
                            <button type="button" id="clearImageBtn" class="clear-btn" onclick="clearImage()">✖</button>
                            <img src="${product.image}" id="currentImage" alt="No Image">
                        </div>
                        <input type="file" name="image" id="newImage" accept="image/*" onchange="previewImage(event)">

                        <button type="submit" id="submitBtn">Update Product</button>
                    </form>

                    <button type="button" id="submitBtn" onclick="goBack()">Back</button>

                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
        <script>
                        function previewImage(event) {
                            const reader = new FileReader();
                            reader.onload = function () {
                                document.getElementById('currentImage').src = reader.result;
                            };
                            reader.readAsDataURL(event.target.files[0]);
                        }

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

                        function goBack() {
                            // Quay về trang trước đó
                            window.history.back();
                        }

                        function previewImage(event) {
                            const reader = new FileReader();
                            reader.onload = function () {
                                document.getElementById('currentImage').src = reader.result;
                                document.getElementById('clearImageBtn').style.display = "block"; // Hiện nút "X"
                            };
                            reader.readAsDataURL(event.target.files[0]);
                        }

                        function clearImage() {
                            document.getElementById("currentImage").src = "images/no-image.png"; // Ảnh mặc định
                            document.getElementById("newImage").value = ""; // Xóa file đã chọn
                            document.getElementById("clearImageBtn").style.display = "none"; // Ẩn nút "X"
                        }

                        // Hiển thị nút "X" khi có ảnh cũ
                        window.onload = function () {
                            if (document.getElementById("currentImage").src) {
                                document.getElementById("clearImageBtn").style.display = "block";
                            }
                        };

        </script>
    </body>
</html>