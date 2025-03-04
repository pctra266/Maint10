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
            .main {
                display: flex;
                flex-direction: column;
                flex-grow: 1;
                padding: 30px;
                background-color: #f4f7f9;
            }

            /* Container chính */
            .container {
                max-width: 600px;
                margin: 0 auto;
                background: #ffffff;
                padding: 25px;
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                transition: transform 0.3s ease-in-out;
            }

            .container:hover {
                transform: translateY(-5px);
            }

            /* Tiêu đề */
            h2 {
                text-align: center;
                font-size: 26px;
                font-weight: bold;
                color: #333;
                margin-bottom: 20px;
            }

            /* Form */
            form {
                display: flex;
                flex-direction: column;
            }

            /* Nhãn */
            label {
                font-weight: 600;
                margin-top: 12px;
                color: #444;
            }

            /* Input, Select và Textarea */
            input, select, textarea {
                width: 100%;
                padding: 12px;
                margin-top: 6px;
                border: 1px solid #ced4da;
                border-radius: 8px;
                font-size: 14px;
                background-color: #f8f9fa;
                transition: border-color 0.3s, box-shadow 0.3s;
            }

            /* Focus hiệu ứng */
            input:focus, select:focus, textarea:focus {
                outline: none;
                border-color: #007bff;
                box-shadow: 0 0 6px rgba(0, 123, 255, 0.3);
            }

            /* Textarea */
            textarea {
                resize: vertical;
                min-height: 120px;
            }

            /* Button chính */
            button {
                margin-top: 20px;
                padding: 12px;
                background: linear-gradient(45deg, #007bff, #0056b3);
                border: none;
                color: #fff;
                font-size: 16px;
                font-weight: bold;
                border-radius: 8px;
                cursor: pointer;
                transition: background 0.3s, transform 0.2s;
            }

            button:hover {
                background: linear-gradient(45deg, #0056b3, #004085);
                transform: scale(1.05);
            }

            /* Nút quay lại */
            .btn-update {
                display: flex;
                align-items: center;
                justify-content: center;
                margin-top: 20px;
                padding: 12px;
                background: #6c757d;
                color: #fff;
                text-decoration: none;
                border-radius: 8px;
                font-weight: bold;
                transition: background 0.3s, transform 0.2s;
            }

            .btn-update:hover {
                background: #5a6268;
                transform: scale(1.05);
            }

            /* Hiển thị thông báo */
            .message {
                background: #d4edda;
                color: #155724;
                padding: 12px;
                border-radius: 8px;
                margin-bottom: 15px;
                text-align: center;
                font-weight: bold;
            }

            /* Hiển thị lỗi */
            .error-message {
                color: red;
                font-size: 13px;
                margin-top: 6px;
                font-style: italic;
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

                            <label for="description">Description:</label>
                            <textarea id="description" name="description">${param.description}</textarea>

                            <label for="receivedDate">Received Date:</label>
                            <input type="datetime-local" id="receivedDate" name="receivedDate" value="${param.receivedDate}" required step="1">

                            <button type="submit" class="btn">Add Product</button>
                        </form>

                        <a href="listUnknown" class="btn-update">
                            <i class="fas fa-backward  "></i>Back
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

                                    // Xử lý sự kiện paste cho productCode: loại bỏ tất cả dấu cách
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

                                    // Sự kiện blur cho productName và description: loại bỏ khoảng trắng ở đầu và cuối
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

// Gọi lại hàm khi trang tải lại để giữ nguyên số điện thoại của khách hàng đã chọn trước đó
                                document.addEventListener("DOMContentLoaded", function () {
                                    updatePhoneNumber();
                                });

        </script>
    </body>
</html>

