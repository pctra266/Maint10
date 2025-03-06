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
            main.content {
                background: #ffffff;
                padding: 50px;
                border-radius: 20px;
                margin: 40px auto;
                max-width: 1200px;
                box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
                font-family: 'Inter', sans-serif;
            }

            main.content h2 {
                font-size: 36px;
                color: #2c3e50;
                margin-bottom: 30px;
                padding-bottom: 10px;
                border-bottom: 3px solid #bdc3c7;
                text-align: center;
            }

            main.content button {
                background: linear-gradient(135deg, #ff9a9e 0%, #fad0c4 100%);
                color: #fff;
                border: none;
                border-radius: 30px;
                padding: 12px 30px;
                font-size: 18px;
                cursor: pointer;
                transition: all 0.4s ease;
                margin: 10px;
            }

            main.content button:hover {
                transform: translateY(-5px);
                box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
            }

            main.content form {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
                align-items: center;
                margin-bottom: 40px;
            }

            main.content form input[type="text"],
            main.content form input[type="date"] {
                flex: 1 1 200px;
                padding: 15px;
                border: 2px solid #ecf0f1;
                border-radius: 30px;
                font-size: 16px;
                transition: border-color 0.3s ease;
            }

            main.content form input[type="text"]:focus,
            main.content form input[type="date"]:focus {
                border-color: #3498db;
            }

            main.content table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 30px;
                border-radius: 15px;
                overflow: hidden;
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
            }

            main.content table th,
            main.content table td {
                padding: 20px;
                text-align: left;
                font-size: 16px;
                border-bottom: 1px solid #f1f1f1;
            }

            main.content table th {
                background: #3498db;
                color: #fff;
                font-weight: bold;
            }

            main.content table tr:nth-child(even) {
                background: #f8f8f8;
            }

            main.content table tr:hover {
                background: #f1f1f1;
            }

            main.content a.active {
                color: #e74c3c;
                font-weight: bold;
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h2>List of Unknown Products</h2>
                    <button onclick="location.href = 'addUnknown'">Add Unknown Product</button>

                    <!-- Hiển thị lỗi -->
                    <div id="error-messages"></div>

                    <form action="listUnknown" method="get">
                        <input type="text" name="productCode" placeholder="Product Code" value="${param.productCode}">
                        <input type="text" name="productName" placeholder="Product Name" value="${param.productName}">
                        <input type="text" name="description" placeholder="Description" value="${param.description}">
                        <input type="date" name="receivedDate" value="${param.receivedDate}">
                        <input type="text" name="customerName" placeholder="Customer Name" value="${param.customerName}">
                        <input type="text" name="phone" placeholder="Phone" value="${param.phone}">
                        <button type="submit">Search</button>
                    </form>

                    <button class="search" onclick="window.location.href = 'listUnknown'">
                        All Product
                    </button>

                    <table border="1">
                        <thead>
                            <tr>
                                <th>Product Code</th>
                                <th>Product Name</th>
                                <th>Description</th>
                                <th>Received Date</th>
                                <th>Customer Name</th>
                                <th>Customer Phone</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="product" items="${listUnknownProduct}">
                                <tr>
                                    <td>${product.productCode}</td>
                                    <td>${product.productName}</td>
                                    <td>${product.description}</td>
                                    <td>${product.receivedDate}</td>
                                    <td>${product.customerName}</td>
                                    <td>${product.customerPhone}</td>
                                    <td>
                                        <form action="listUnknown" method="post">
                                            <input type="hidden" name="productId" value="${product.unknownProductId}">
                                            <input type="hidden" name="customerId" value="${product.customerId}">
                                            <input type="hidden" name="type" value="display">
                                            <button type="submit">Create a Repair Request</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div>
                        <c:if test="${totalPages > 1}">
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <a href="listUnknown?page=${i}&productCode=${param.productCode}&productName=${param.productName}&description=${param.description}&receivedDate=${param.receivedDate}&customerName=${param.customerName}&phone=${param.phone}"
                                   class="${currentPage == i ? 'active' : ''}">${i}</a>
                            </c:forEach>
                        </c:if>
                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                let form = document.querySelector('form[action="listUnknown"]');

                let productCodeInput = document.querySelector('input[name="productCode"]');
                let productNameInput = document.querySelector('input[name="productName"]');
                let descriptionInput = document.querySelector('input[name="description"]');
                let customerNameInput = document.querySelector('input[name="customerName"]');
                let phoneInput = document.querySelector('input[name="phone"]');
                let receivedDateInput = document.querySelector('input[name="receivedDate"]');

                // Định dạng kiểm tra
                let codePattern = /^[a-zA-Z0-9]+$/; // Chỉ chữ cái và số, không có dấu cách
                let nameDescPattern = /^(?!\s*$)[a-zA-Z0-9\s]+$/;  // Không có dấu cách đầu/cuối, có thể chứa dấu cách giữa các từ
                let phonePattern = /^0\d{0,9}$/; // Bắt đầu bằng số 0, tối đa 10 số
                let datePattern = /^\d{4}-\d{2}-\d{2}$/; // YYYY-MM-DD

                function showError(input, message) {
                    let errorId = input.name + "-error";
                    let errorElement = document.getElementById(errorId);
                    if (!errorElement) {
                        errorElement = document.createElement("p");
                        errorElement.id = errorId;
                        errorElement.style.color = "red";
                        input.parentNode.appendChild(errorElement);
                    }
                    errorElement.textContent = message;
                }

                function clearError(input) {
                    let errorId = input.name + "-error";
                    let errorElement = document.getElementById(errorId);
                    if (errorElement) {
                        errorElement.remove();
                    }
                }

                function cleanAndValidate(input, pattern, errorMessage) {
                    input.addEventListener("input", function () {
                        let value = input.value; // Không cập nhật lại ngay để tránh xóa khoảng trắng

                        if (value.trim() === "") {
                            clearError(input);
                        } else if (!pattern.test(value)) {
                            showError(input, errorMessage);
                        } else {
                            clearError(input);
                        }
                    });
                }

                // Kiểm tra khi nhập
                cleanAndValidate(productCodeInput, codePattern, "❌ Product Code chỉ chứa chữ cái và số, không có dấu cách hoặc ký tự đặc biệt.");
                cleanAndValidate(productNameInput, nameDescPattern, "❌ Product Name không hợp lệ (không nhập toàn dấu cách, chỉ chứa chữ và số).");
                cleanAndValidate(descriptionInput, nameDescPattern, "❌ Description không hợp lệ (không nhập toàn dấu cách).");
                cleanAndValidate(customerNameInput, nameDescPattern, "❌ Customer Name không hợp lệ (không nhập toàn dấu cách, chỉ chứa chữ và số).");
                cleanAndValidate(phoneInput, phonePattern, "❌ Số điện thoại phải bắt đầu bằng số 0 và tối đa 10 số.");

                // Kiểm tra định dạng ngày
                receivedDateInput.addEventListener("input", function () {
                    let value = receivedDateInput.value.trim();
                    if (value !== "" && !datePattern.test(value)) {
                        showError(receivedDateInput, "❌ Ngày phải nhập đúng định dạng YYYY-MM-DD.");
                    } else {
                        clearError(receivedDateInput);
                    }
                });

                // Kiểm tra form khi submit
                form.addEventListener("submit", function (event) {
                    let isValid = true;

                    function checkField(input, pattern, errorMessage) {
                        let value = input.value.replace(/^\s+|\s+$/g, "").replace(/\s+/g, ' '); // Chuẩn hóa dấu cách
                        input.value = value; // Cập nhật lại giá trị đã chuẩn hóa

                        if (value !== "" && !pattern.test(value)) { // Chỉ kiểm tra nếu có dữ liệu
                            showError(input, errorMessage);
                            isValid = false;
                        }
                    }

                    checkField(productCodeInput, codePattern, "❌ Product Code không hợp lệ.");
                    checkField(productNameInput, nameDescPattern, "❌ Product Name không hợp lệ.");
                    checkField(descriptionInput, nameDescPattern, "❌ Description không hợp lệ.");
                    checkField(customerNameInput, nameDescPattern, "❌ Customer Name không hợp lệ.");
                    checkField(phoneInput, phonePattern, "❌ Số điện thoại không hợp lệ.");

                    if (receivedDateInput.value.trim() !== "" && !datePattern.test(receivedDateInput.value.trim())) {
                        showError(receivedDateInput, "❌ Ngày phải nhập đúng định dạng YYYY-MM-DD.");
                        isValid = false;
                    }

                    if (!isValid) {
                        event.preventDefault();
                    }
                });
            });
        </script>
    </body>
</html>
