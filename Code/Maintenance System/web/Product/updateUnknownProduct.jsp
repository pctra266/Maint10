<%-- 
    Document   : viewProduct
    Created on : Feb 21, 2025, 11:49:16 PM
    Author     : sonNH
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product List</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <style>
            :root {
                --primary-color: #326ABC;
            }

            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Inter', sans-serif;
                line-height: 1.6;
                background-color: #f0f8ff; /* Nền xanh nhạt */
                color: #333;
            }

            /* Layout tổng thể */
            .wrapper {
                display: flex;
                flex-direction: row;
                min-height: 100vh;
                background-color: #f0f8ff;
            }

            .wrapper .navbar-left,
            .wrapper > jsp\:include[page*="navbar-left.jsp"] {
                width: 250px;
                background-color: var(--primary-color);
                color: #fff;
                padding: 20px;
            }

            .wrapper .main {
                flex: 1;
                display: flex;
                flex-direction: column;
                background-color: #fff;
            }

            .navbar-top,
            .wrapper > jsp\:include[page*="navbar-top.jsp"] {
                background-color: #244b8b;
                color: #fff;
                padding: 15px 20px;
                font-size: 1.1rem;
            }

            .footer,
            .wrapper > jsp\:include[page*="footer.jsp"] {
                background-color: var(--primary-color);
                color: #fff;
                text-align: center;
                padding: 10px;
                margin-top: auto;
            }

            main.content {
                flex: 1;
                padding: 20px;
            }

            /* Container chính của form */
            .container {
                max-width: 1200px;
                margin-top: 20px;
                margin-bottom: 20px;
                background-color: #FAFAFA ;
                border-radius: 8px;
                padding: 30px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            }

            .container h2 {
                color: #003f7f;
                margin-bottom: 20px;
                text-align: center;
            }

            /* ========== Bố cục Form 2 cột ========== */
            .form-row {
                display: grid;
                grid-template-columns: 1fr 1fr; /* 2 cột đều nhau */
                gap: 20px;                     /* Khoảng cách giữa các cột */
                margin-bottom: 20px;           /* Khoảng cách giữa các hàng */
            }

            /* Mỗi .form-group là một “ô” trong grid */
            .form-group {
                display: flex;
                flex-direction: column; /* Label phía trên, Input phía dưới */
            }

            .form-group label {
                margin-bottom: 8px;
                font-weight: 600;
                color: #003f7f;
            }

            .form-group input[type="text"],
            .form-group input[type="datetime-local"],
            .form-group select,
            .form-group textarea {
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 1rem;
            }

            /* Editor mô tả */
            .editor-container {
                position: relative;
                width: 100%;
            }
            .editor-toolbar {
                background-color: var(--primary-color);
                padding: 8px;
                border-radius: 4px 4px 0 0;
                display: flex;
                gap: 10px;
            }
            .editor-toolbar .tool-btn {
                background: transparent;
                border: none;
                color: #fff;
                font-size: 1rem;
                cursor: pointer;
                transition: color 0.3s;
            }
            .editor-toolbar .tool-btn:hover {
                color: #ffdd57;
            }
            .editor-container textarea {
                width: 100%;
                height: 150px;
                padding: 15px;
                border: 1px solid var(--primary-color);
                border-top: none;
                border-radius: 0 0 4px 4px;
                font-size: 1rem;
                resize: vertical;
            }

            /* Hàng chứa nút bấm */
            .btn-row {
                display: flex;
                gap: 20px; /* Khoảng cách giữa 2 nút */
                margin-top: 20px;
            }

            /* ======= Giảm kích thước nút ======= */
            .btn,
            .btn-update {
                background-color: var(--primary-color);
                color: #fff;
                border: none;
                padding: 8px 16px;  /* Giảm padding */
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s;
                text-decoration: none;
                text-align: center;
                font-size: 0.9rem;  /* Giảm font-size */
                display: inline-block;
                width: 120px;       /* Giảm chiều rộng */
                height: 40px;       /* Cố định chiều cao, nếu muốn */
                line-height: 1.2;   /* Điều chỉnh độ cao dòng cho nội dung nút */
            }

            .btn:hover,
            .btn-update:hover {
                background-color: #244b8b;
                color: white;
                text-decoration: none;
            }

            .message {
                background-color: #d1ecf1;
                border: 1px solid #bee5eb;
                padding: 10px;
                border-radius: 4px;
                color: #0c5460;
                margin-bottom: 20px;
            }

            .error-message {
                color: red;
                font-size: 0.9rem;
                margin-top: 5px;
                display: block;
            }

            /* ========== Responsive cho màn hình nhỏ ========== */
            @media (max-width: 768px) {
                .wrapper {
                    flex-direction: column;
                }
                .wrapper .navbar-left {
                    width: 100%;
                }

                /* Form 1 cột khi màn hình nhỏ */
                .form-row {
                    grid-template-columns: 1fr;
                }

                /* Nút bấm full-width */
                .btn-row {
                    flex-direction: column;
                    align-items: flex-start;
                }
                .btn,
                .btn-update {
                    width: 100%;
                    height: auto; /* cho phép tự điều chỉnh chiều cao trên màn hình nhỏ */
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
                    <div class="container">
                        <h2>Update Unknown Product</h2>

                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger">
                                ${errorMessage}
                            </div>
                        </c:if>

                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-success">
                                ${successMessage}
                            </div>
                        </c:if>

                        <form action="updateUnknown" method="post" onsubmit="return validateForm();">

                            <input type="hidden" name="productId" value="${unknownProduct.unknownProductId}">

                            <!-- Hàng 1: Product Code + Product Name -->
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="productCode">Product Code:</label>
                                    <input type="text" id="productCode" name="productCode" 
                                           value="${unknownProduct.productCode}" required>
                                </div>
                                <div class="form-group">
                                    <label for="productName">Product Name:</label>
                                    <input type="text" id="productName" name="productName" 
                                           value="${unknownProduct.productName}" required>
                                </div>
                            </div>

                            <!-- Hàng 2: Customer + Phone Number -->
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="customer">Customer:</label>
                                    <select id="customer" name="customerId" onchange="updatePhoneNumber()">
                                        <option value="">Select Customer</option>
                                        <c:forEach var="c" items="${customerList}">
                                            <option value="${c.customerID}" data-phone="${c.phone}"
                                                    <c:if test="${unknownProduct.customerId eq c.customerID}">selected</c:if>>
                                                ${c.name}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="customerPhone">Phone Number:</label>
                                    <input type="text" id="customerPhone" name="customerPhone" readonly>
                                </div>
                            </div>

                            <!-- Hàng 3: Received Date + Description -->
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="receivedDate">Received Date:</label>
                                    <input type="datetime-local" id="receivedDate" name="receivedDate" 
                                           value="${unknownProduct.receivedDate}" required step="1">
                                </div>
                                <div class="form-group">
                                    <label for="description">Description:</label>
                                    <div class="editor-container">
                                        <div class="editor-toolbar">
                                            <button type="button" class="tool-btn" title="Bold"><i class="fas fa-bold"></i></button>
                                            <button type="button" class="tool-btn" title="Italic"><i class="fas fa-italic"></i></button>
                                            <button type="button" class="tool-btn" title="Strikethrough"><i class="fas fa-strikethrough"></i></button>
                                            <button type="button" class="tool-btn" title="Underline"><i class="fas fa-underline"></i></button>
                                            <button type="button" class="tool-btn" title="Bulleted List"><i class="fas fa-list-ul"></i></button>
                                            <button type="button" class="tool-btn" title="Numbered List"><i class="fas fa-list-ol"></i></button>
                                            <button type="button" class="tool-btn" title="Align Left"><i class="fas fa-align-left"></i></button>
                                            <button type="button" class="tool-btn" title="Align Center"><i class="fas fa-align-center"></i></button>
                                            <button type="button" class="tool-btn" title="Align Right"><i class="fas fa-align-right"></i></button>
                                            <button type="button" class="tool-btn" title="Link"><i class="fas fa-link"></i></button>
                                            <button type="button" class="tool-btn" title="Quote"><i class="fas fa-quote-right"></i></button>
                                            <button type="button" class="tool-btn" title="Source"><i class="fas fa-code"></i></button>
                                        </div>
                                        <textarea id="description" name="description">${unknownProduct.description}</textarea>
                                    </div>
                                </div>
                            </div>

                            <!-- Hàng 4: Nút bấm -->
                            <div class="btn-row">
                                <button type="submit" class="btn">Update</button>
                                <a href="listUnknown" class="btn-update">Back</a>
                            </div>
                        </form>
                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>

        <script>


                                        function updatePhoneNumber() {
                                            const customerSelect = document.getElementById("customer");
                                            const phoneInput = document.getElementById("customerPhone");
                                            const selectedOption = customerSelect.options[customerSelect.selectedIndex];

                                            if (selectedOption) {
                                                const phoneNumber = selectedOption.getAttribute("data-phone") || "";
                                                phoneInput.value = phoneNumber;
                                            }
                                        }

                                        document.addEventListener("DOMContentLoaded", function () {
                                            updatePhoneNumber();
                                        });
        </script>
        <script>
            function validateForm() {
                var productCode = document.getElementById("productCode").value;
                var productName = document.getElementById("productName").value;
                var description = document.getElementById("description").value;

                // Validate product code: chỉ cho phép chữ cái và số, không có khoảng trắng hoặc ký tự đặc biệt
                var productCodeRegex = /^[A-Za-z0-9]+$/;
                if (!productCodeRegex.test(productCode)) {
                    alert("Product Code must contain only letters and numbers without spaces or special characters.");
                    return false;
                }

                // Validate product name: cho phép chữ cái, số, giữa các từ chỉ được phép có 1 khoảng trắng, không nhập toàn khoảng trắng
                var productNameRegex = /^[A-Za-z0-9]+(?: [A-Za-z0-9]+)*$/;
                if (!productNameRegex.test(productName.trim())) {
                    alert("Product Name must contain only letters and numbers with a single space between words and cannot be only spaces.");
                    return false;
                }

                // Validate description: không nhập toàn khoảng trắng, giữa các từ chỉ được phép có 1 dấu cách
                var descriptionRegex = /^\S+(?: \S+)*$/;
                if (!descriptionRegex.test(description.trim())) {
                    alert("Description must not be only spaces and must have only one space between words.");
                    return false;
                }

                return true;
            }

        </script>

    </body>
</html>
