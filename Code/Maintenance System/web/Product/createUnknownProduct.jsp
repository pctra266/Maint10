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
            /* Tổng thể */
            body {
                font-family: 'Inter', sans-serif;
                background-color: #f7f9fc;
                margin: 0;
                padding: 0;
                color: #333;
            }

            .wrapper {
                display: flex;
                min-height: 100vh;
            }

            .main {
                display: flex;
                flex-direction: column;
                flex-grow: 1;
                padding: 40px;
                background-color: #f7f9fc;
            }

            /* Container chính: tăng chiều ngang */
            .container {
                max-width: 800px;
                margin: 0 auto;
                background: #fff;
                padding: 30px 40px;
                border-radius: 16px;
                box-shadow: 0 6px 18px rgba(50, 106, 188, 0.15);
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }
            .container:hover {
                transform: translateY(-4px);
                box-shadow: 0 8px 24px rgba(50, 106, 188, 0.25);
            }

            /* Tiêu đề */
            h2 {
                text-align: center;
                font-size: 28px;
                font-weight: 600;
                color: #326ABC;
                margin-bottom: 25px;
            }

            /* Form: chia thành 2 cột nếu đủ không gian */
            form {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
            }

            /* Mỗi group form */
            .form-group {
                flex: 1 1 48%;
                display: flex;
                flex-direction: column;
            }

            /* Nhãn */
            .form-group label {
                font-weight: 600;
                margin-bottom: 8px;
                color: #444;
            }

            /* Input, Select và Textarea */
            .form-group input,
            .form-group select,
            .form-group textarea {
                padding: 14px;
                border: 1px solid #ccc;
                border-radius: 8px;
                font-size: 15px;
                background-color: #fafafa;
                transition: border-color 0.3s, box-shadow 0.3s;
            }
            .form-group input:focus,
            .form-group select:focus,
            .form-group textarea:focus {
                outline: none;
                border-color: #326ABC;
                box-shadow: 0 0 8px rgba(50, 106, 188, 0.3);
            }
            .form-group textarea {
                resize: vertical;
                min-height: 140px;
            }

            button.btn {
                width: 100%;
                padding: 14px;
                background: #004085;
                border: none;
                color: #fff;
                font-size: 16px;
                font-weight: 600;
                border-radius: 8px;
                cursor: pointer;
                transition: background 0.3s, transform 0.2s;
                margin-top: 10px;
            }
            button.btn:hover {
                background: linear-gradient(45deg, #1E5AA8, #14457E);
                transform: scale(1.03);
            }

            .btn-update {
                display: block;
                width: 100%;
                text-align: center;
                padding: 14px;
                background: #326ABC;
                color: #fff;
                text-decoration: none;
                border-radius: 8px;
                font-weight: 600;
                transition: background 0.3s, transform 0.2s;
                margin-top: 20px;
            }
            .btn-update:hover {
                transform: scale(1.03);
            }

            /* Hiển thị thông báo */
            .message {
                background: #e6f4ea;
                color: #2d6a4f;
                padding: 14px;
                border-radius: 8px;
                margin-bottom: 20px;
                text-align: center;
                font-weight: 600;
            }

            /* Hiển thị lỗi */
            .error-message {
                color: #e63946;
                font-size: 13px;
                margin-top: 6px;
                font-style: italic;
            }

            /* Toolbox */
            .toolbox {
                background-color: #f0f0f0;
                border: 1px solid #ddd;
                border-radius: 5px;
                padding: 15px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                max-width: 100%;
                margin: 20px auto;
            }
            .toolbox label {
                display: block;
                font-weight: bold;
                margin-bottom: 8px;
            }
            .toolbox textarea {
                width: 200%;
                min-height: 100px;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                resize: vertical;
            }

            /* Editor container */
            .editor-container {
                width: 100%;
                border: 1px solid #ddd;
                border-radius: 8px;
                margin: 15px 0;
                background-color: #fff;
                font-family: 'Inter', sans-serif;
            }

            /* Toolbar */
            .editor-toolbar {
                display: flex;
                flex-wrap: wrap;
                border-bottom: 1px solid #ddd;
                background-color: #f8f9fa;
                padding: 8px;
                border-top-left-radius: 8px;
                border-top-right-radius: 8px;
            }
            .tool-btn {
                background: none;
                border: none;
                cursor: pointer;
                margin-right: 10px;
                font-size: 18px;
                color: #666;
                padding: 6px;
                transition: color 0.2s, background-color 0.2s;
            }
            .tool-btn:hover {
                background-color: #e9ecef;
                color: #326ABC;
            }
            .tool-btn:focus {
                outline: none;
            }

            /* Editor content */
            .editor-content,
            textarea#description {
                min-height: 200px;
                padding: 12px;
                font-size: 15px;
                line-height: 1.6;
                background-color: #fff;
                border-bottom-left-radius: 8px;
                border-bottom-right-radius: 8px;
            }
            .editor-content:empty:before,
            textarea#description:empty:before {
                content: attr(data-placeholder);
                color: #aaa;
            }
            .editor-content:focus:before,
            textarea#description:focus:before {
                content: "";
            }

            /* Căn chỉnh input và dropdown */
            .form-group {
                display: flex;
                align-items: center;
                gap: 12px;
                margin-bottom: 15px;
            }

            .form-group label {
                font-weight: 600;
                min-width: 120px;
                text-align: right;
            }

            .form-group input,
            .form-group select {
                flex: 1;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 6px;
                font-size: 14px;
                background-color: #fff;
            }

            .form-group input:focus,
            .form-group select:focus {
                border-color: #326ABC;
                outline: none;
                box-shadow: 0 0 6px rgba(50, 106, 188, 0.2);
            }

            /* Căn chỉnh phần trình soạn thảo */
            .editor-container {
                width: 100%;
                border: 1px solid #ccc;
                border-radius: 8px;
                background: #fff;
                padding: 10px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }

            /* Toolbar của trình soạn thảo */
            .editor-toolbar {
                display: flex;
                gap: 8px;
                padding: 8px;
                background-color: #f5f5f5;
                border-bottom: 1px solid #ddd;
                border-top-left-radius: 8px;
                border-top-right-radius: 8px;
            }

            .editor-toolbar button {
                background: none;
                border: none;
                cursor: pointer;
                font-size: 16px;
                padding: 6px;
                color: #444;
                transition: all 0.2s;
            }

            .editor-toolbar button:hover {
                color: #326ABC;
            }

            /* Khu vực nhập nội dung */
            .editor-content {
                width: 100%;
                min-height: 180px;
                padding: 12px;
                font-size: 14px;
                border-bottom-left-radius: 8px;
                border-bottom-right-radius: 8px;
                background: #fff;
                border: none;
                outline: none;
                resize: vertical;
            }

            /* Căn chỉnh ô nhập ngày */
            .date-input {
                display: flex;
                align-items: center;
                gap: 8px;
            }

            .date-input input {
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 6px;
                font-size: 14px;
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
                        <h2>Add Unknown Product</h2>

                        <c:if test="${not empty message}">
                            <p class="message">${message}</p>
                        </c:if>

                        <form action="addUnknown" method="post" onsubmit="return validateForm();">      
                            <label for="productCode">Product Code:</label>
                            <input type="text" id="productCode" name="productCode" value="${param.productCode}" required>

                            <label for="productName">Product Name:</label>
                            <input type="text" id="productName" name="productName" value="${param.productName}" required>

                            <label for="customer">Customer:</label>
                            <select id="customer" name="customerId" onchange="updatePhoneNumber()">
                                <option value="">Select Customer</option>
                                <c:forEach var="c" items="${listCustomer}">
                                    <option value="${c.customerID}" data-phone="${c.phone}"
                                            <c:if test="${param.customerId eq c.customerID}">selected</c:if>>
                                        ${c.name}
                                    </option>
                                </c:forEach>
                            </select>

                            <label for="customerPhone">Phone Number:</label>
                            <input type="text" id="customerPhone" name="customerPhone" readonly>

                            <label for="receivedDate">Received Date:</label>
                            <input type="datetime-local" id="receivedDate" name="receivedDate" value="${param.receivedDate}" required step="1">

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
                                <textarea id="description" name="description">${param.description}</textarea>
                            </div>

                            <button type="submit" class="btn">Add Product</button>
                        </form>

                        <a href="listUnknown" class="btn-update">
                            Back
                        </a>
                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>

        <script>
                                document.addEventListener("DOMContentLoaded", function () {
                                    const form = document.querySelector("form");
                                    const productCode = document.getElementById("productCode");
                                    const productName = document.getElementById("productName");
                                    const description = document.getElementById("description");
                                    const receivedDate = document.getElementById("receivedDate");
                                    const customer = document.getElementById("customer");
                                    const submitButton = document.querySelector("button[type='submit']");

                                    function showError(input, message) {
                                        let errorSpan = input.nextElementSibling;
                                        if (!errorSpan || !errorSpan.classList.contains("error-message")) {
                                            errorSpan = document.createElement("span");
                                            errorSpan.classList.add("error-message");
                                            input.parentNode.insertBefore(errorSpan, input.nextSibling);
                                        }
                                        errorSpan.textContent = message;
                                        input.style.borderColor = "red";
                                    }

                                    function clearError(input) {
                                        let errorSpan = input.nextElementSibling;
                                        if (errorSpan && errorSpan.classList.contains("error-message")) {
                                            errorSpan.textContent = "";
                                        }
                                        input.style.borderColor = "";
                                    }

                                    // Ngăn không cho nhập dấu cách từ bàn phím cho productCode
                                    productCode.addEventListener("keydown", function (e) {
                                        if (e.key === " ") {
                                            e.preventDefault();
                                        }
                                    });

                                    productCode.addEventListener("paste", function (e) {
                                        e.preventDefault();
                                        let pasteData = (e.clipboardData || window.clipboardData).getData("text");
                                        pasteData = pasteData.replace(/\s+/g, "");
                                        const start = productCode.selectionStart;
                                        const end = productCode.selectionEnd;
                                        const currentValue = productCode.value;
                                        productCode.value = currentValue.slice(0, start) + pasteData + currentValue.slice(end);
                                        productCode.setSelectionRange(start + pasteData.length, start + pasteData.length);
                                    });

                                    productName.addEventListener("blur", function () {
                                        productName.value = productName.value.trim();
                                        updateSubmitButton();
                                    });
                                    description.addEventListener("blur", function () {
                                        description.value = description.value.trim();
                                        updateSubmitButton();
                                    });

                                    function validateProductCode() {
                                        const regex = /^[A-Za-z0-9]+$/; // chỉ cho phép chữ cái và số
                                        if (!productCode.value.match(regex)) {
                                            showError(productCode, "Product Code chỉ được chứa chữ cái và số, không được có khoảng trắng hoặc ký tự đặc biệt.");
                                            return false;
                                        }
                                        clearError(productCode);
                                        return true;
                                    }

                                    function validateProductName() {
                                        const regex = /^[A-Za-z]+(?:\s[A-Za-z]+)*$/; // chỉ cho phép chữ cái, mỗi từ cách nhau 1 khoảng trắng
                                        if (!productName.value.match(regex)) {
                                            showError(productName, "Product Name chỉ được chứa chữ cái, mỗi từ cách nhau 1 khoảng trắng, không chứa số hoặc ký tự đặc biệt.");
                                            return false;
                                        }
                                        clearError(productName);
                                        return true;
                                    }

                                    function validateDescription() {
                                        const regex = /^\S+(?:\s\S+)*$/; // không được nhập toàn dấu cách
                                        if (!description.value.match(regex)) {
                                            showError(description, "Description không được nhập toàn dấu cách, mỗi từ chỉ được cách nhau bởi 1 dấu cách.");
                                            return false;
                                        }
                                        clearError(description);
                                        return true;
                                    }

                                    function validateReceivedDate() {
                                        const today = new Date();
                                        today.setHours(0, 0, 0, 0);
                                        const inputDate = new Date(receivedDate.value);
                                        if (inputDate < today) {
                                            showError(receivedDate, "Received Date không được là ngày trong quá khứ.");
                                            return false;
                                        }
                                        clearError(receivedDate);
                                        return true;
                                    }

                                    // Kiểm tra tổng hợp các trường, trả về true nếu hợp lệ
                                    function validateFormOnSubmit() {
                                        let isValid = true;
                                        if (!productCode.value.trim()) {
                                            showError(productCode, "Vui lòng nhập Product Code.");
                                            isValid = false;
                                        } else if (!validateProductCode()) {
                                            isValid = false;
                                        }
                                        if (!productName.value.trim()) {
                                            showError(productName, "Vui lòng nhập Product Name.");
                                            isValid = false;
                                        } else if (!validateProductName()) {
                                            isValid = false;
                                        }
                                        if (!description.value.trim()) {
                                            showError(description, "Vui lòng nhập Description.");
                                            isValid = false;
                                        } else if (!validateDescription()) {
                                            isValid = false;
                                        }
                                        if (!receivedDate.value) {
                                            showError(receivedDate, "Vui lòng chọn ngày nhận.");
                                            isValid = false;
                                        } else if (!validateReceivedDate()) {
                                            isValid = false;
                                        }
                                        if (!customer.value) {
                                            showError(customer, "Vui lòng chọn khách hàng.");
                                            isValid = false;
                                        }
                                        return isValid;
                                    }

                                    // Hàm kiểm tra tính hợp lệ của form mà không hiển thị lỗi (dùng để enable/disable nút submit)
                                    function isFormValid() {
                                        return (
                                                productCode.value.trim() &&
                                                /^[A-Za-z0-9]+$/.test(productCode.value) &&
                                                productName.value.trim() &&
                                                /^[A-Za-z]+(?:\s[A-Za-z]+)*$/.test(productName.value.trim()) &&
                                                description.value.trim() &&
                                                /^\S+(?:\s\S+)*$/.test(description.value.trim()) &&
                                                receivedDate.value &&
                                                (new Date(receivedDate.value) >= new Date(new Date().setHours(0, 0, 0, 0))) &&
                                                customer.value
                                                );
                                    }

                                    function updateSubmitButton() {
                                        submitButton.disabled = !isFormValid();
                                    }

                                    // Cập nhật nút submit khi có thay đổi input
                                    productCode.addEventListener("input", function () {
                                        validateProductCode();
                                        updateSubmitButton();
                                    });
                                    productName.addEventListener("input", function () {
                                        validateProductName();
                                        updateSubmitButton();
                                    });
                                    description.addEventListener("input", function () {
                                        validateDescription();
                                        updateSubmitButton();
                                    });
                                    receivedDate.addEventListener("input", function () {
                                        validateReceivedDate();
                                        updateSubmitButton();
                                    });
                                    customer.addEventListener("change", updateSubmitButton);

                                    form.addEventListener("submit", function (event) {
                                        if (!validateFormOnSubmit()) {
                                            event.preventDefault();
                                        }
                                    });

                                    // Disable submit button ngay khi load trang
                                    updateSubmitButton();
                                });

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
    </body>
</html>
