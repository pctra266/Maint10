<%-- 
    Document   : addProduct
    Created on : Jan 20, 2025, 9:01:02 PM
    Author     : sonNH
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Product</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            /* Base Styles */
            body {
                font-family: 'Inter', sans-serif;
                background-color: #f9f9f9;
                color: #333;
                margin: 0;
                padding: 0;
            }

            .wrapper {
                display: flex;
                min-height: 100vh;
            }

            .main {
                flex: 1;
                padding: 40px 20px;
                background-color: #ffffff;
            }

            h2 {
                font-size: 2rem;
                color: #444;
                text-align: center;
                margin-bottom: 0px;
            }

            .form-container {
                max-width: 600px;
                margin: auto;
                padding: 30px;
                background: #fff;
                border-radius: 8px;
                box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.1);
                box-sizing: border-box;
            }

            .form-group {
                margin-bottom: 10px;
            }

            label {
                font-size: 1rem;
                font-weight: bold;
                margin-bottom: 8px;
                display: inline-block;
                color: #555;
            }

            input, select {
                width: 100%;
                padding: 12px;
                margin-top: 8px;
                border: 1px solid #ccc;
                border-radius: 6px;
                font-size: 1rem;
                box-sizing: border-box;
                background-color: #f9f9f9;
                transition: border-color 0.3s ease;
            }

            input:focus, select:focus {
                border-color: #007bff;
                outline: none;
            }

            input[type="number"] {
                -moz-appearance: textfield;
            }

            button {
                background-color: #007bff;
                color: white;
                padding: 12px;
                border: none;
                width: 100%;
                cursor: pointer;
                border-radius: 6px;
                font-size: 1rem;
                transition: background-color 0.3s ease;
                margin-top: 20px;
            }

            button:hover {
                background-color: #0056b3;
            }

            /* Back Button */
            form > button.add-product {
                background-color: #3B7DDD;
                color: white;
                padding: 10px;
                text-align: center;
                display: block;
                width: 100%;
                border-radius: 6px;
                font-size: 1rem;
                transition: background-color 0.3s ease;
            }

            form > button.add-product:hover {
                background-color: green;
            }

            /* File Input */
            input[type="file"] {
                background-color: #fff;
                padding: 10px;
                border-radius: 6px;
                border: 1px solid #ccc;
                cursor: pointer;
            }

            /* Form Section Spacing */
            form + form {
                margin-top: 30px;
            }

            /* Responsive Design */
            @media (max-width: 768px) {
                .form-container {
                    width: 100%;
                    padding: 20px;
                }

                h2 {
                    font-size: 1.8rem;
                }
            }

            @media (max-width: 480px) {
                .form-container {
                    padding: 15px;
                }

                button {
                    padding: 10px;
                }
            }

        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <div class="form-container">
                        <h2>Add Product</h2>

                        <c:if test="${not empty errorMessage}">
                            <div style="color: red; text-align: center; margin-bottom: 10px;">
                                ${errorMessage}
                            </div>
                        </c:if>

                        <form action="addP" method="post" enctype="multipart/form-data">
                            <!-- Nhập code -->
                            <div class="form-group">
                                <label for="code">Product Code:</label>
                                <input type="text" id="code" name="code" value="${code}" required>
                            </div>

                            <!-- Nhập name -->
                            <div class="form-group">
                                <label for="name">Product Name:</label>
                                <input type="text" id="name" name="name" value="${name}" required>
                            </div>

                            <!-- Chọn brand -->
                            <div class="form-group">
                                <label for="brand">Brand:</label>
                                <select id="brand" name="brandId">
                                    <option value="">Select Brand</option>
                                    <c:forEach var="p" items="${listBrand}">
                                        <option value="${p.brandId}" ${p.brandId == brandId ? 'selected' : ''}>
                                            ${p.brandName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <!-- Nhập type -->
                            <div class="form-group">
                                <label for="type">Type:</label>
                                <input type="text" id="type" name="type" value="${type}" required>
                            </div>

                            <!-- Nhập quantity -->
                            <div class="form-group">
                                <label for="quantity">Quantity:</label>
                                <input type="number" id="quantity" name="quantity" value="${quantity}" min="1" required>
                            </div>

                            <!-- Nhập warranty -->
                            <div class="form-group">
                                <label for="warranty">Warranty Period (months):</label>
                                <input type="number" id="warranty" name="warrantyPeriod" value="${warrantyPeriod}" min="1" required>
                            </div>

                            <!-- Chọn status -->
                            <input type="hidden" name="status" value="Active">


                            <!-- Chọn image -->
                            <div class="form-group">
                                <label for="newImage">Product Image:</label>
                                <img id="currentImage" alt="No Image" style="max-width: 100%; height: auto;">
                                <input type="file" name="image" id="newImage" accept="image/*" onchange="previewImage(event)">
                            </div>

                            <button type="submit" class="submit-btn">Add Product</button>
                        </form>

                        <form action="viewProduct">
                            <button class="add-product" type="submit">Back</button>
                        </form>

                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>

        <script>
                                    document.addEventListener("DOMContentLoaded", function () {
                                        let form = document.querySelector("form[action='addP']");
                                        let codeInput = document.getElementById("code");
                                        let nameInput = document.getElementById("name");
                                        let brandInput = document.getElementById("brand");
                                        let typeInput = document.getElementById("type");
                                        let quantityInput = document.getElementById("quantity");
                                        let warrantyInput = document.getElementById("warranty");
                                        let imageInput = document.getElementById("newImage");

                                        // Hàm hiển thị lỗi
                                        function showError(input, message) {
                                            let errorSpan = input.parentNode.querySelector(".error-message");
                                            if (!errorSpan) {
                                                errorSpan = document.createElement("span");
                                                errorSpan.className = "error-message";
                                                errorSpan.style.color = "red";
                                                errorSpan.style.fontSize = "14px";
                                                input.parentNode.appendChild(errorSpan);
                                            }
                                            errorSpan.innerText = message;
                                        }

                                        // Hàm xoá lỗi
                                        function clearError(input) {
                                            let errorSpan = input.parentNode.querySelector(".error-message");
                                            if (errorSpan) {
                                                errorSpan.remove();
                                            }
                                        }

                                        // Kiểm tra Product Code chỉ có chữ và số
                                        codeInput.addEventListener("input", function () {
                                            let validValue = this.value.replace(/[^a-zA-Z0-9]/g, '');
                                            if (this.value !== validValue) {
                                                this.value = validValue;
                                                showError(codeInput, "Only letters and numbers are allowed!");
                                            } else {
                                                clearError(codeInput);
                                            }
                                        });

                                        // Kiểm tra định dạng Product Name
                                        nameInput.addEventListener("input", function () {
                                            let validValue = this.value.replace(/[^a-zA-Z0-9 ]/g, ''); // Chỉ cho phép chữ cái, số, và dấu cách
                                            validValue = validValue.replace(/\s{2,}/g, ' '); // Chỉ cho phép 1 dấu cách giữa các từ

                                            if (this.value !== validValue) {
                                                this.value = validValue;
                                                showError(nameInput, "Only letters, numbers, and single spaces between words are allowed!");
                                            } else if (this.value.trim() === "") {
                                                showError(nameInput, "Product Name cannot be only spaces!");
                                            } else {
                                                clearError(nameInput);
                                            }
                                        });


                                        // Kiểm tra khi nhấn submit
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

                                            if (imageInput.files.length === 0) {
                                                showError(imageInput, "Please upload a Product Image!");
                                                isValid = false;
                                            }

                                            if (!isValid) {
                                                event.preventDefault(); // Ngăn form gửi nếu có lỗi
                                            }
                                        });
                                    });

                                    document.addEventListener("DOMContentLoaded", function () {
                                        let imageInput = document.getElementById("newImage");

                                        function showError(input, message) {
                                            let errorSpan = input.parentNode.querySelector(".error-message");
                                            if (!errorSpan) {
                                                errorSpan = document.createElement("span");
                                                errorSpan.className = "error-message";
                                                errorSpan.style.color = "red";
                                                errorSpan.style.fontSize = "14px";
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

                                        function previewImage(event) {
                                            const reader = new FileReader();
                                            reader.onload = function () {
                                                const output = document.getElementById('currentImage');
                                                output.src = reader.result;
                                            };
                                            reader.readAsDataURL(event.target.files[0]);
                                        }
                                    });
        </script>

    </body>
</html>
