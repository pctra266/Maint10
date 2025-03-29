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
                padding: 20px;
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
            .error-message {
                color: red;
                font-size: 0.9em;
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
                overflow: hidden;
                margin-bottom: 20px;
            }
            #imagePreviewContainer img {
                width: 100%;
                height: auto;
                object-fit: cover;
                display: none;
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
                background-color: #3b7ddd;
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
                        <div style="color: green; text-align: center; margin-bottom: 10px;">
                            ${successMessage}
                        </div>
                    </c:if>

                    <!-- Form wrapping both columns -->
                    <form id="updateProductForm" action="viewProduct" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="pid" value="${product.productId}">
                        <input type="hidden" name="status" value="${product.status}">
                        
                        <div class="container">
                            <!-- Left column: product information fields -->
                            <div class="form-container">
                                <h2 style="text-align: center; color: #326ABC">Update Product</h2>
                                <label for="productCode">Product Code:</label>
                                <input type="text" id="productCode" name="productCode" value="${product.code}" required oninput="validateProductCode()">
                                <span id="productCodeError" class="error-message"></span>

                                <label for="productName">Product Name:</label>
                                <input type="text" id="productName" name="productName" value="${product.productName}" required oninput="validateProductName()">
                                <span id="productNameError" class="error-message"></span>

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
                                <input type="number" id="quantity" name="quantity" value="${product.quantity}" min="1" required oninput="validateQuantity()">
                                <span id="quantityError" class="error-message"></span>

                                <label for="warrantyPeriod">Warranty Period (Months):</label>
                                <input type="number" id="warrantyPeriod" name="warrantyPeriod" min="1" value="${product.warrantyPeriod}" required oninput="validateWarrantyPeriod()">
                                <span id="warrantyError" class="error-message"></span>
                            </div>

                            <!-- Right column: image preview, file selection, and submit button -->
                            <div class="image-container">
                                <!-- Slideshow preview area -->
                                <div class="image-preview-wrapper">
                                    <div id="imagePreviewContainer">
                                        <!-- Display old product images if available -->
                                        <c:if test="${not empty product.images}">
                                            <c:forEach var="img" items="${product.images}">
                                                <img src="${img}" alt="Product Image" class="image-preview">
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                    <button type="button" class="arrow prev" onclick="previousImage()">&#10094;</button>
                                    <button type="button" class="arrow next" onclick="nextImage()">&#10095;</button>
                                </div>
                                <!-- Select new images and update button -->
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
        <!-- Your app.js (if any) -->
        <script src="js/app.js"></script>
        
        <!-- Script for validation, preview, and slideshow -->
        <script>
            //======================== VALIDATION ========================
            function validateProductCode() {
                const productCode = document.getElementById("productCode").value.trim();
                const errorSpan = document.getElementById("productCodeError");
                const regex = /^[a-zA-Z0-9]+$/; // Only letters and numbers allowed

                if (!productCode) {
                    errorSpan.textContent = "Product code cannot be empty.";
                } else if (!regex.test(productCode)) {
                    errorSpan.textContent = "Product code may only contain letters and numbers.";
                } else {
                    errorSpan.textContent = "";
                }
                validateForm();
            }

            function validateProductName() {
                const productName = document.getElementById("productName").value.trim();
                const errorSpan = document.getElementById("productNameError");
                // Only letters, numbers, and single spaces between words are allowed
                const regex = /^[a-zA-Z0-9]+(\s[a-zA-Z0-9]+)*$/;

                if (!productName) {
                    errorSpan.textContent = "Product name cannot be empty.";
                } else if (!regex.test(productName)) {
                    errorSpan.textContent = "Product name may only contain letters, numbers, and a single space between words.";
                } else {
                    errorSpan.textContent = "";
                }
                validateForm();
            }

            function validateQuantity() {
                const quantity = document.getElementById("quantity").value;
                const errorSpan = document.getElementById("quantityError");
                if (!quantity || quantity < 1) {
                    errorSpan.textContent = "Quantity must be at least 1.";
                } else {
                    errorSpan.textContent = "";
                }
                validateForm();
            }

            function validateWarrantyPeriod() {
                const warranty = document.getElementById("warrantyPeriod").value;
                const errorSpan = document.getElementById("warrantyError");
                if (!warranty || warranty < 1) {
                    errorSpan.textContent = "Warranty period must be at least 1 month.";
                } else {
                    errorSpan.textContent = "";
                }
                validateForm();
            }

            function validateForm() {
                const productCodeError = document.getElementById("productCodeError").textContent;
                const productNameError = document.getElementById("productNameError").textContent;
                const quantityError = document.getElementById("quantityError").textContent;
                const warrantyError = document.getElementById("warrantyError").textContent;
                const submitBtn = document.getElementById("submitBtn");

                // Disable submit button if any errors exist
                if (productCodeError || productNameError || quantityError || warrantyError) {
                    submitBtn.disabled = true;
                } else {
                    submitBtn.disabled = false;
                }
            }

            //======================== FILE SELECTION (LIMIT FILE SIZE) ========================
            document.getElementById("newImage").addEventListener("change", function () {
                let file = this.files[0];
                if (file) {
                    let maxSize = 5 * 1024 * 1024; // 5MB
                    if (file.size > maxSize) {
                        alert("File must not exceed 5MB!");
                        this.value = "";
                    }
                }
            });

            //======================== IMAGE PREVIEW AND SLIDESHOW ========================
            let currentImageIndex = 0;

            function previewImages(event) {
                const files = event.target.files;
                const previewContainer = document.getElementById('imagePreviewContainer');
                previewContainer.innerHTML = ""; // Clear old images
                currentImageIndex = 0;           // Reset index

                if (!files || files.length === 0) {
                    return;
                }

                Array.from(files).forEach((file, idx) => {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        const img = document.createElement("img");
                        img.src = e.target.result;
                        img.className = "image-preview";
                        img.style.display = "none"; // Hide by default
                        previewContainer.appendChild(img);

                        // Once all images are loaded, display the first image
                        if (idx === files.length - 1) {
                            showImage(0);
                        }
                    };
                    reader.readAsDataURL(file);
                });
            }

            // Display one image at index and hide others
            function showImage(index) {
                const images = document.querySelectorAll('#imagePreviewContainer img');
                if (images.length === 0) return;

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

            // Previous image button
            function previousImage() {
                showImage(currentImageIndex - 1);
            }

            // Next image button
            function nextImage() {
                showImage(currentImageIndex + 1);
            }

            //======================== SUBMIT FORM ========================
            function submitForm(e) {
                e.preventDefault(); 
                // Additional validation steps can be added here if needed
                document.getElementById("updateProductForm").submit();
            }

            // On page load, if there are old images, display the first one
            window.onload = function () {
                showImage(0);
            };
        </script>
    </body>
</html>
