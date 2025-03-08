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
            /* CSS cho nội dung trong main */
            main.content {
                background-color: #ffffff;
                font-family: 'Inter', sans-serif;
                color: #333;
                border-radius: 10px;
                margin: 10px;
            }
            /* Tiêu đề */
            main.content h2 {
                font-size: 32px;
                font-weight: 600;
                color: #2c3e50;
                margin-bottom: 30px;
                border-bottom: 2px solid #e1e1e1;
                padding-bottom: 10px;
            }
            /* Form */
            main.content form {
                display: flex;
                flex-wrap: wrap;
                gap: 15px;
                align-items: center;
                margin-bottom: 30px;
            }

            main.content form input[type="text"],
            main.content form input[type="date"] {
                flex: 1;
                min-width: 180px;
                padding: 12px 15px;
                font-size: 16px;
                border: 1px solid #ccc;
                border-radius: 6px;
                transition: border-color 0.3s ease;
            }

            main.content form input[type="text"]:focus,
            main.content form input[type="date"]:focus {
                border-color: #3498db;
                outline: none;
            }

            main.content form span {
                font-size: 14px;
                color: #e74c3c;
                margin-top: 5px;
            }

            /* Nút */
            main.content button {
                background: linear-gradient(135deg, #7faaff, #8bc1ff);
                color: #fff;
                border: none;
                padding: 12px 20px;
                font-size: 16px;
                border-radius: 6px;
                cursor: pointer;
                transition: background 0.3s ease;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                margin-right: 10px;
            }

            main.content button:hover {
                background: linear-gradient(135deg, #0d47a1, #002171);
            }

            /* Nút riêng cho "search" */
            main.content button.search {
                background: linear-gradient(135deg, #7faaff, #8bc1ff);
            }

            main.content button.search:hover {
                background: linear-gradient(135deg, #0d47a1, #002171);
            }

            /* Bảng */
            main.content table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 30px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            }

            main.content table th,
            main.content table td {
                padding: 15px 20px;
                text-align: left;
                border-bottom: 1px solid #e0e0e0;
            }

            main.content table th {
                background-color: #f7f7f7;
                font-weight: 600;
                color: #2c3e50;
            }

            main.content table tr:nth-child(even) {
                background-color: #fafafa;
            }

            /* Lưu ý: CSS này không tác động đến phần phân trang */
        </style>
    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">

                    <h2>List of Unknown Products</h2>

                    <form action="listUnknown" method="get">
                        <input type="text" id="productCode" name="productCode" placeholder="Product Code" value="${param.productCode}" oninput="validateProductCode(this)">
                        <span id="productCodeError" style="color: red;"></span>

                        <input type="text" id="productName" name="productName" placeholder="Product Name" value="${param.productName}" oninput="validateProductName(this)">
                        <span id="productNameError" style="color: red;"></span>

                        <input type="text" id="description" name="description" placeholder="Description" 
                               value="${param.description}" oninput="validateDescription(this)">
                        <span id="descriptionError" style="color: red; font-size: 12px; margin-left: 5px;"></span>

                        <input type="date" name="receivedDate" value="${param.receivedDate}">

                        <input type="text" id="customerName" name="customerName" placeholder="Customer Name"
                               value="${param.customerName}" oninput="validateCustomerName(this)">
                        <span id="customerNameError" style="color: red; font-size: 12px; margin-left: 5px;"></span>

                        <input type="text" id="phone" name="phone" placeholder="Phone"
                               value="${param.phone}" oninput="validatePhone(this)">
                        <span id="phoneError" style="color: red; font-size: 12px; margin-left: 5px;"></span>

                        <button type="submit">Search</button>
                    </form>

                    <button onclick="location.href = 'addUnknown'">Add Unknown Product</button>
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
        <script src="js/app.js"></script>

        <script>
                        function validateProductCode(input) {
                            let regex = /^[a-zA-Z0-9]*$/; // Chỉ cho phép chữ cái và số
                            let errorMessage = document.getElementById("productCodeError");

                            if (!regex.test(input.value)) {
                                input.value = input.value.replace(/[^a-zA-Z0-9]/g, ""); // Xóa ký tự không hợp lệ
                                errorMessage.textContent = "Product Code chỉ được chứa chữ cái và số!";
                            } else {
                                errorMessage.textContent = ""; // Xóa thông báo lỗi nếu hợp lệ
                            }
                        }
        </script>
        <script>
            function validateProductName(input) {
                let errorMessage = document.getElementById("customerNameError");

                // Lấy giá trị nhập vào
                let value = input.value;

                // Nếu input rỗng, xóa lỗi
                if (value.trim() === "") {
                    errorMessage.textContent = value.length > 0 ? "Không được nhập toàn dấu cách!" : "";
                    return;
                }

                // Kiểm tra ký tự hợp lệ: Chỉ cho phép chữ, số và khoảng trắng giữa các từ
                let regex = /^[a-zA-Z0-9]+( [a-zA-Z0-9]+)*$/;
                if (!regex.test(value)) {
                    errorMessage.textContent = "Chỉ được nhập chữ, số và 1 dấu cách giữa các từ!";
                } else {
                    errorMessage.textContent = "";
                }
            }
        </script>
        <script>
            function validateDescription(input) {
                let errorMessage = document.getElementById("descriptionError");

                // Loại bỏ khoảng trắng thừa đầu và cuối
                input.value = input.value.trimStart();

                // Nếu ô nhập trống, không báo lỗi (cho phép để trống)
                if (input.value === "") {
                    errorMessage.textContent = "";
                    return;
                }

                // Xóa khoảng trắng thừa giữa các từ
                input.value = input.value.replace(/\s{2,}/g, " ");

                // Kiểm tra nếu toàn bộ nội dung chỉ có khoảng trắng
                if (input.value.trim() === "") {
                    errorMessage.textContent = "Không được nhập toàn dấu cách!";
                    input.value = ""; // Xóa nội dung nhập sai
                } else {
                    errorMessage.textContent = "";
                }
            }
        </script>

        <script>
            function validateCustomerName(input) {
                let errorMessage = document.getElementById("customerNameError");

                // Lấy giá trị nhập vào
                let value = input.value;

                // Nếu input rỗng, xóa lỗi
                if (value.trim() === "") {
                    errorMessage.textContent = value.length > 0 ? "Không được nhập toàn dấu cách!" : "";
                    return;
                }

                // Kiểm tra ký tự hợp lệ: Chỉ cho phép chữ, số và khoảng trắng giữa các từ
                let regex = /^[a-zA-Z0-9]+( [a-zA-Z0-9]+)*$/;
                if (!regex.test(value)) {
                    errorMessage.textContent = "Chỉ được nhập chữ, số và 1 dấu cách giữa các từ!";
                } else {
                    errorMessage.textContent = "";
                }
            }
        </script>
        <script>
            function validatePhone(input) {
                let errorMessage = document.getElementById("phoneError");

                // Nếu input rỗng, không báo lỗi (cho phép để trống)
                if (input.value === "") {
                    errorMessage.textContent = "";
                    return;
                }

                // Chỉ giữ lại chữ số (loại bỏ dấu cách và ký tự không phải số)
                input.value = input.value.replace(/\D/g, "");

                // Kiểm tra độ dài tối đa 10 số
                if (input.value.length > 10) {
                    input.value = input.value.slice(0, 10); // Cắt bỏ phần dư
                }

                // Kiểm tra số đầu tiên có phải là 0 không
                if (input.value.length > 0 && input.value.charAt(0) !== "0") {
                    errorMessage.textContent = "Số điện thoại phải bắt đầu bằng số 0!";
                } else {
                    errorMessage.textContent = "";
                }
            }
        </script>

    </body>
</html>

