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

            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
            }

            h1 {
                text-align: center;
                margin-top: 30px;
            }

            .form-container {
                max-width: 600px;
                margin: 20px auto;
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }

            form {
                display: flex;
                flex-direction: column;
                gap: 15px;
            }

            label {
                font-size: 1rem;
                font-weight: bold;
            }

            input, select {
                padding: 10px;
                font-size: 1rem;
                border-radius: 5px;
                border: 1px solid #ddd;
            }

            input[type="number"] {
                -moz-appearance: textfield; /* For removing the up/down arrows in the number field */
                appearance: textfield;
            }

            input[type="number"]::-webkit-outer-spin-button, input[type="number"]::-webkit-inner-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }

            button[type="submit"] {
                background-color: #4CAF50;
                color: white;
                border: none;
                padding: 12px 20px;
                font-size: 1rem;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            button[type="submit"]:hover {
                background-color: #45a049;
            }

            .add-product {
                background-color: #f44336;
                color: white;
                border: none;
                padding: 12px 20px;
                font-size: 1rem;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .add-product:hover {
                background-color: #da190b;
            }

            .form-container h2 {
                text-align: center;
                color: #333;
            }

            .error-message {
                color: red;
                font-size: 14px;
                display: block;
                margin-top: 5px;
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

                    <form method="post" action="updateproduct" enctype="multipart/form-data">
                        <input type="hidden" name="pid" value="${product.productId}">
                        <input type="hidden" name="status" value="${product.status}">

                        <label for="productName">Product Name:</label>
                        <input type="text" id="productName" name="productName" value="${product.productName}" required>

                        <label for="productCode">Product Code:</label>
                        <input type="text" id="productCode" name="productCode" value="${product.code}" required>

                        <label for="brandId">Brand:</label>
                        <select name="brandId" id="brandId" required>
                            <c:forEach var="brand" items="${listBrand}">
                                <option value="${brand.brandId}" ${product.brandId == brand.brandId ? 'selected' : ''}>
                                    ${brand.brandName}
                                </option>
                            </c:forEach>
                        </select>

                        <label for="type">Type:</label>
                        <select name="type" id="type" required>
                            <c:forEach var="t" items="${listType}">
                                <option value="${t}" ${product.type eq t ? 'selected' : ''}>
                                    ${t}
                                </option>
                            </c:forEach>
                        </select>

                        <label for="quantity">Quantity:</label>
                        <input type="number" id="quantity" name="quantity" value="${product.quantity}" min="1" required>

                        <label for="warrantyPeriod">Warranty Period (Months):</label>
                        <input type="number" id="warrantyPeriod" name="warrantyPeriod" min="1" value="${product.warrantyPeriod}" required>

                        <label for="newImage">Product Image:</label>
                        <img src="${product.image}" id="currentImage" alt="No Image" style="max-width: 100%; height: auto;">
                        <input type="file" name="image" id="newImage" accept="image/*" onchange="previewImage(event)">

                        <button type="submit">Update Product</button>
                    </form>
                    <c:if test="${sessionScope.viewProductFrom ne null}">
                        <form action="Redirect" style="margin-top: 10px">
                            <input type="hidden" name="target" value="${sessionScope.viewProductFrom}">
                            <button class="add-product" type="submit">Back</button>
                        </form>                        
                    </c:if>
                    <c:if test="${sessionScope.viewProductFrom eq null}">
                        <form action="viewProduct" style="margin-top: 10px">
                            <button class="add-product" type="submit">Back</button>
                        </form>
                    </c:if>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
        <script>
                            document.addEventListener("DOMContentLoaded", function () {
                                let form = document.querySelector("form[action='updateproduct']");
                                let codeInput = document.getElementById("productCode");
                                let nameInput = document.getElementById("productName");
                                let quantityInput = document.getElementById("quantity");
                                let warrantyInput = document.getElementById("warrantyPeriod");
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

                                codeInput.addEventListener("input", function () {
                                    let validValue = this.value.replace(/[^a-zA-Z0-9]/g, '');
                                    this.value = validValue;
                                    if (validValue) {
                                        clearError(codeInput);
                                    } else {
                                        showError(codeInput, "Only letters and numbers are allowed!");
                                    }
                                });

                                nameInput.addEventListener("input", function () {
                                    let validValue = this.value.replace(/[^a-zA-Z0-9 ]/g, '').replace(/\s+/g, ' ');
                                    this.value = validValue;
                                    if (validValue) {
                                        clearError(nameInput);
                                    } else {
                                        showError(nameInput, "Only letters, numbers, and single spaces are allowed!");
                                    }
                                });

                                imageInput.addEventListener("change", function () {
                                    let file = imageInput.files[0];
                                    if (file) {
                                        let fileType = file.type;
                                        if (fileType !== "image/jpeg" && fileType !== "image/png") {
                                            showError(imageInput, "Only JPG and PNG files are allowed!");
                                            imageInput.value = ""; // Reset file input
                                        } else {
                                            clearError(imageInput);
                                            previewImage(event);
                                        }
                                    }
                                });

                                form.addEventListener("submit", function (event) {
                                    let isValid = true;

                                    if (!codeInput.value.trim()) {
                                        showError(codeInput, "Product Code is required!");
                                        isValid = false;
                                    } else {
                                        clearError(codeInput);
                                    }

                                    if (!nameInput.value.trim()) {
                                        showError(nameInput, "Product Name is required!");
                                        isValid = false;
                                    } else {
                                        clearError(nameInput);
                                    }

                                    if (!quantityInput.value.trim() || quantityInput.value <= 0) {
                                        showError(quantityInput, "Quantity must be at least 1!");
                                        isValid = false;
                                    } else {
                                        clearError(quantityInput);
                                    }

                                    if (!warrantyInput.value.trim() || warrantyInput.value <= 0) {
                                        showError(warrantyInput, "Warranty must be at least 1 month!");
                                        isValid = false;
                                    } else {
                                        clearError(warrantyInput);
                                    }

                                    if (!isValid) {
                                        event.preventDefault();
                                    }
                                });
                            });

                            function previewImage(event) {
                                const reader = new FileReader();
                                reader.onload = function () {
                                    document.getElementById('currentImage').src = reader.result;
                                };
                                reader.readAsDataURL(event.target.files[0]);
                            }


        </script>
    </body>
</html>