<%-- 
    Document   : updateProduct1
    Created on : Feb 6, 2025, 5:07:44 AM
    Author     : sonNH
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Product</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
           
            /* Bọc toàn bộ form để chia layout 2 cột */
            .add-product-form {
                display: flex;
                justify-content: space-between;
                align-items: flex-start;
                gap: 2rem;
                margin-top: 1rem;
                margin-bottom: 1rem;
            }

            /* Phần bên trái: form nhập thông tin */
            .form-container {
                flex: 1;
                max-width: 50%;
                box-sizing: border-box;
                padding: 1rem;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            /* Tiêu đề */
            .form-container h3 {
                margin-top: 0;
            }

            /* Mỗi nhóm form (label + input) */
            .form-group {
                margin-bottom: 1rem;
            }

            /* Label */
            .form-group label {
                display: block;
                margin-bottom: 0.5rem;
                font-weight: 600;
            }

            /* Input, select */
            .form-group input,
            .form-group select {
                width: 100%;
                padding: 0.5rem;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
                font-size: 1rem;
            }

            /* Thông báo lỗi (nếu có) */
            .error-message {
                display: block;
                margin-top: 0.3rem;
                color: red;
                font-size: 0.875rem;
                font-style: italic;
            }

            /* Phần bên phải: hiển thị ảnh */
            .image-container {
                flex: 1;
                max-width: 50%;
                box-sizing: border-box;
                padding: 1rem;
                border: 1px solid #ccc;
                border-radius: 4px;
                text-align: center;
            }

            /* Ô preview ảnh */
            .preview-box {
                position: relative;
                width: 100%;
                height: 400px;
                border: 1px solid #ddd;
                border-radius: 4px;
                margin-bottom: 1rem;
                display: flex;
                align-items: center;
                justify-content: center;
                overflow: hidden;
                background-color: #f9f9f9;
            }

            .preview-box img {
                max-width: 100%;
                max-height: 100%;
                object-fit: contain;
            }

            /* Mũi tên chuyển ảnh - đặt bên trong preview-box */
            .arrow {
                cursor: pointer;
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                font-size: 2rem;
                background-color: rgba(255, 255, 255, 0.8);
                border-radius: 50%;
                width: 2rem;
                height: 2rem;
                line-height: 2rem;
                text-align: center;
                border: 1px solid #ccc;
                color: #333;
                user-select: none;
                z-index: 10;
            }

            .left-arrow {
                left: 0.5rem;
            }

            .right-arrow {
                right: 0.5rem;
            }

            /* Input chọn ảnh */
            #newImage {
                margin-bottom: 1rem;
            }

            /* Khu vực thumbnail */
            .thumbnail-container {
                display: flex;
                flex-wrap: wrap;
                gap: 0.5rem;
                justify-content: center;
                margin-top: 0.5rem;
            }

            .thumbnail-wrapper {
                position: relative;
            }

            .thumbnail-container img {
                width: 50px;
                height: 50px;
                object-fit: cover;
                cursor: pointer;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            /* Nút Submit và Back */
            .button-container {
                margin-top: 1rem;
                display: flex;
                justify-content: center;
                gap: 1rem;
            }

            .submit-btn,
            .back-btn {
                padding: 0.5rem 1rem;
                cursor: pointer;
                border: none;
                border-radius: 4px;
                font-size: 1rem;
            }

            .submit-btn {
                background-color: #007bff;
                color: #fff;
            }

            .back-btn {
                background-color: #6c757d;
                color: #fff;
            }

            /* Tùy chọn: hiển thị lỗi nếu chưa chọn ảnh */
            #newImage.error {
                border-color: red;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <form id="productForm" class="add-product-form" action="viewProduct" method="post" enctype="multipart/form-data">
                        <!-- Phần nhập thông tin sản phẩm -->
                        <div class="form-container">
                            <c:if test="${not empty errorMessage}">
                                <div style="color: red; font-weight: bold;">
                                    ${errorMessage}
                                </div>
                            </c:if>
                            <h3>Add Product</h3>
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="status" value="${status != null ? status : 'active'}">
                            <div class="form-group">
                                <label for="code">Product Code:</label>
                                <input type="text" id="code" name="code" required value="${code != null ? code : ''}">
                            </div>
                            <div class="form-group">
                                <label for="name">Product Name:</label>
                                <input type="text" id="name" name="name" required value="${name != null ? name : ''}">
                            </div>
                            <div class="form-group">
                                <label for="brand">Brand:</label>
                                <select id="brand" name="brandId" required>
                                    <option value="">Select Brand</option>
                                    <c:forEach var="p" items="${listBrand}">
                                        <option value="${p.brandId}" ${brandId != null && brandId == p.brandId ? 'selected' : ''}>
                                            ${p.brandName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="type">Product Type:</label>
                                <select id="type" name="type" required>
                                    <option value="">Select Type</option>
                                    <c:forEach var="t" items="${listType}">
                                        <option value="${t.productTypeId}" ${productTypeId != null && productTypeId == t.productTypeId ? 'selected' : ''}>
                                            ${t.typeName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="quantity">Quantity:</label>
                                <input type="number" id="quantity" name="quantity" min="1" required value="${quantity != null ? quantity : ''}">
                            </div>
                            <div class="form-group">
                                <label for="warranty">Warranty Period (months):</label>
                                <input type="number" id="warranty" name="warrantyPeriod" min="1" required value="${warrantyPeriod != null ? warrantyPeriod : ''}">
                            </div>
                        </div>

                        <!-- Phần chọn và preview ảnh -->
                        <div class="image-container">
                            <!-- Preview box với mũi tên -->
                            <div class="preview-box">
                                <img id="currentImage" alt="Preview Image" src="${not empty uploadedImages ? uploadedImages[0] : ''}">
                                <div class="arrow left-arrow" onclick="prevImage()">&#10094;</div>
                                <div class="arrow right-arrow" onclick="nextImage()">&#10095;</div>
                            </div>
                            <!-- Input chọn file -->
                            <input name="image" type="file" id="newImage" multiple accept="image/*">

                            <!-- Danh sách thumbnail (với input ẩn giữ lại thông tin ảnh) -->
                            <div class="thumbnail-container" id="thumbnailContainer">
                                <c:if test="${not empty uploadedImages}">
                                    <c:forEach var="image" items="${uploadedImages}">
                                        <div class="thumbnail-wrapper">
                                            <img src="${image}" class="thumbnail" alt="Uploaded Image">
                                            <input type="hidden" name="existingImages" value="${image}">
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div>

                            <!-- Nút submit và back nằm dưới phần chọn ảnh -->
                            <div class="button-container">
                                <button type="submit" class="submit-btn">Add Product</button>
                                <button type="button" class="back-btn" onclick="location.href = 'viewProduct'">Back</button>
                            </div>
                        </div>
                    </form>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>

        <script src="js/app.js"></script>
        <script>
                                    document.addEventListener("DOMContentLoaded", function () {
                                        let form = document.querySelector(".add-product-form");
                                        let codeInput = document.getElementById("code");
                                        let nameInput = document.getElementById("name");
                                        let brandInput = document.getElementById("brand");
                                        let typeInput = document.getElementById("type");
                                        let quantityInput = document.getElementById("quantity");
                                        let warrantyInput = document.getElementById("warranty");
                                        let imageInput = document.getElementById("newImage");

                                        function showError(input, message) {
                                            let errorSpan = input.parentNode.querySelector(".error-message");
                                            if (!errorSpan) {
                                                errorSpan = document.createElement("span");
                                                errorSpan.className = "error-message";
                                                input.parentNode.appendChild(errorSpan);
                                            }
                                            errorSpan.innerText = message;
                                        }
                                        function clearError(input) {
                                            let errorSpan = input.parentNode.querySelector(".error-message");
                                            if (errorSpan) {
                                                errorSpan.remove();
                                            }
                                        }

                                        // Chỉ cho chữ và số ở Product Code
                                        codeInput.addEventListener("input", function () {
                                            let validValue = this.value.replace(/[^a-zA-Z0-9]/g, '');
                                            if (this.value !== validValue) {
                                                this.value = validValue;
                                                showError(codeInput, "Only letters and numbers are allowed!");
                                            } else {
                                                clearError(codeInput);
                                            }
                                        });

                                        // Chỉ cho chữ, số và khoảng trắng ở Product Name
                                        nameInput.addEventListener("input", function () {
                                            let validValue = this.value.replace(/[^a-zA-Z0-9 ]/g, '');
                                            validValue = validValue.replace(/\s{2,}/g, ' ');
                                            if (this.value !== validValue) {
                                                this.value = validValue;
                                                showError(nameInput, "Only letters, numbers, and single spaces between words are allowed!");
                                            } else if (this.value.trim() === "") {
                                                showError(nameInput, "Product Name cannot be only spaces!");
                                            } else {
                                                clearError(nameInput);
                                            }
                                        });

                                        // Khi submit: kiểm tra các trường bắt buộc (không bắt kiểm tra file vì ảnh cũ đã được lưu lại)
                                        form.addEventListener("submit", function (event) {
                                            let isValid = true;
                                            if (codeInput.value.trim() === "") {
                                                showError(codeInput, "Product Code is required!");
                                                isValid = false;
                                            }
                                            if (nameInput.value.trim() === "") {
                                                showError(nameInput, "Product Name is required!");
                                                isValid = false;
                                            }
                                            if (brandInput.value === "") {
                                                showError(brandInput, "Please select a Brand!");
                                                isValid = false;
                                            }
                                            if (typeInput.value.trim() === "") {
                                                showError(typeInput, "Product Type is required!");
                                                isValid = false;
                                            }
                                            if (quantityInput.value.trim() === "" || quantityInput.value <= 0) {
                                                showError(quantityInput, "Quantity must be at least 1!");
                                                isValid = false;
                                            }
                                            if (warrantyInput.value.trim() === "" || warrantyInput.value <= 0) {
                                                showError(warrantyInput, "Warranty Period must be at least 1 month!");
                                                isValid = false;
                                            }
                                            if (!isValid) {
                                                event.preventDefault();
                                            }
                                        });
                                    });

                                    // Xử lý preview ảnh và mũi tên chuyển ảnh
                                    let currentImageIndex = 0;
                                    let imageFiles = [];

                                    document.getElementById("newImage").addEventListener("change", function (event) {
                                        let files = event.target.files;
                                        let maxSize = 5 * 1024 * 1024; // 5MB
                                        for (let i = 0; i < files.length; i++) {
                                            if (files[i].size > maxSize) {
                                                alert("File không được vượt quá 5MB!");
                                                this.value = "";
                                                return;
                                            }
                                        }
                                        previewImages(event);
                                    });

                                    function previewImages(event) {
                                        let files = event.target.files;
                                        if (files.length === 0)
                                            return;
                                        imageFiles = [...files];
                                        currentImageIndex = 0;
                                        showImage(currentImageIndex);

                                        let thumbnailContainer = document.getElementById("thumbnailContainer");
                                        thumbnailContainer.innerHTML = "";
                                        for (let i = 0; i < files.length; i++) {
                                            let reader = new FileReader();
                                            reader.onload = function (e) {
                                                let img = document.createElement("img");
                                                img.src = e.target.result;
                                                img.onclick = function () {
                                                    showImage(i);
                                                };
                                                thumbnailContainer.appendChild(img);
                                            };
                                            reader.readAsDataURL(files[i]);
                                        }
                                    }

                                    function showImage(index) {
                                        if (index >= 0 && index < imageFiles.length) {
                                            let reader = new FileReader();
                                            reader.onload = function (e) {
                                                document.getElementById("currentImage").src = e.target.result;
                                            };
                                            reader.readAsDataURL(imageFiles[index]);
                                            currentImageIndex = index;
                                        }
                                    }

                                    function prevImage() {
                                        if (currentImageIndex > 0) {
                                            showImage(currentImageIndex - 1);
                                        }
                                    }

                                    function nextImage() {
                                        if (currentImageIndex < imageFiles.length - 1) {
                                            showImage(currentImageIndex + 1);
                                        }
                                    }
        </script>
    </body>
</html>
