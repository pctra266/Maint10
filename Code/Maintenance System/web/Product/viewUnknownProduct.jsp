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
            /* Đặt font mặc định, reset cơ bản */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Inter', sans-serif;
            }

            body {
                background-color: #F8FAFF; /* Nền xanh dương rất nhạt */
                color: #333;
            }

            /* Layout tổng thể */
            .wrapper {
                display: flex;
                min-height: 100vh;
            }

            /* Thanh điều hướng bên trái */
            .navbar-left {
                width: 220px;
                background-color: #FFFFFF;
                border-right: 1px solid #E0E0E0;
                padding: 20px;
            }

            /* Khu vực chính */
            .main {
                flex: 1;
                display: flex;
                flex-direction: column;
                background-color: #F8FAFF;
            }

            /* Thanh điều hướng trên */
            .navbar-top {
                background-color: #FFFFFF;
                padding: 15px 20px;
                border-bottom: 1px solid #E0E0E0;
                display: flex;
                align-items: center;
                justify-content: space-between;
            }

            /* Nội dung chính */
            main.content {
                flex: 1;
                padding: 20px 30px;
            }

            /* Tiêu đề */
            main.content h2 {
                font-size: 24px;
                font-weight: 600;
                margin-bottom: 20px;
                color: #3b7DDD; /* Màu chủ đạo */
            }

            /* ------------ FORM TÌM KIẾM ------------ */
            main.content form {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
                margin-bottom: 20px;
            }

            /* Các input trong form tìm kiếm */
            main.content form input[type="text"],
            main.content form input[type="date"] {
                padding: 8px 12px;
                border: 1px solid #CBD5E1;
                border-radius: 6px;
                font-size: 14px;
                background-color: #fff;
                transition: border-color 0.2s;
            }

            main.content form input[type="text"]:focus,
            main.content form input[type="date"]:focus {
                outline: none;
                border-color: #3b7ddd;
            }

            /* Nút submit trong form tìm kiếm */
            main.content form button[type="submit"] {
                background-color: #3b7ddd;
                color: #fff;
                border: none;
                padding: 8px 16px;
                border-radius: 6px;
                cursor: pointer;
                font-size: 14px;
                font-weight: 500;
                transition: background-color 0.2s, transform 0.2s;
            }

            main.content form button[type="submit"]:hover {
                background-color: #27509C;
                transform: translateY(-1px);
            }

            /* ------------ NÚT THÊM / ALL PRODUCT ------------ */
            main.content button {
                background-color: #3b7ddd;
                color: #fff;
                border: none;
                padding: 8px 16px;
                border-radius: 6px;
                cursor: pointer;
                font-size: 14px;
                font-weight: 500;
                transition: background-color 0.2s, transform 0.2s;
                margin-right: 10px;
            }

            main.content button:hover {
                background-color: #27509C;
                transform: translateY(-1px);
            }

            /* Kiểu cho thẻ <a> hiển thị như button */
            main.content a.button {
                background-color: #3b7ddd;
                color: #fff;
                text-decoration: none;
                display: inline-block;
                padding: 8px 16px;
                border-radius: 6px;
                font-size: 14px;
                font-weight: 500;
                transition: background-color 0.2s, transform 0.2s;
                margin-right: 10px;
            }
            main.content a.button:hover {
                background-color: #27509C;
                transform: translateY(-1px);
            }

            /* ------------ FORM CHỌN SỐ PHẦN TỬ TRÊN TRANG ------------ */
            .page-size-form {
                display: flex;
                flex-wrap: wrap;
                align-items: center;
                justify-content: center;
                gap: 8px;
                margin: 20px 0;
            }
            .page-size-form label {
                font-size: 14px;
                font-weight: 500;
            }

            .page-size-form select,
            .page-size-form input[type="number"] {
                padding: 6px 10px;
                border-radius: 6px;
                border: 1px solid #CBD5E1;
                font-size: 14px;
                background-color: #fff;
                transition: border-color 0.2s;
            }

            .page-size-form select:focus,
            .page-size-form input[type="number"]:focus {
                outline: none;
                border-color: #3b7ddd;
            }

            /* ------------ BẢNG DANH SÁCH SẢN PHẨM ------------ */
            main.content table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                background-color: #FFFFFF;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            }

            /* Đổi màu tiêu đề bảng */
            main.content table thead {
                background-color: #3b7ddd;
            }

            main.content table th,
            main.content table td {
                padding: 12px 15px;
                text-align: left;
                font-size: 14px;
                border-bottom: 1px solid #E5E7EB;
            }

            /* Chỉnh lại tiêu đề bảng */
            main.content table th {
                font-weight: 600;
                color: #fff;
            }

            main.content table tbody tr:last-child td {
                border-bottom: none;
            }

            /* Hover trên từng hàng */
            main.content table tbody tr:hover {
                background-color: #F1F5F9;
            }

            /* ------------ PHÂN TRANG ------------ */
            .pagination {
                margin-top: 20px;
                display: flex;
                gap: 6px;
                align-items: center;
                flex-wrap: wrap;
                justify-content: center;
            }

            .pagination a {
                display: inline-block;
                padding: 8px 12px;
                border-radius: 6px;
                border: 1px solid #CBD5E1;
                text-decoration: none;
                color: #3b7ddd;
                font-size: 14px;
                transition: background-color 0.2s, color 0.2s, transform 0.2s;
            }

            .pagination a:hover {
                background-color: #27509C;
                color: #fff;
                border-color: #27509C;
                transform: translateY(-1px);
            }

            .pagination a.active {
                background-color: #3b7ddd;
                color: #fff;
                border-color: #3b7ddd;
            }

            /* ------------ CÁC THÔNG BÁO LỖI ------------ */
            span[id$="Error"] {
                display: block;
                margin-top: -8px;
                margin-bottom: 8px;
                font-size: 12px;
                color: #EF4444;
            }

            /* ------------ RESPONSIVE ------------ */
            @media (max-width: 768px) {
                .wrapper {
                    flex-direction: column;
                }

                .navbar-left {
                    width: 100%;
                    border-right: none;
                    border-bottom: 1px solid #E0E0E0;
                }

                .page-size-form {
                    margin-left: 0;
                    margin-top: 10px;
                }

                main.content table th,
                main.content table td {
                    font-size: 13px;
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

                    <h2 style="text-align: center">List of Unknown Products</h2>
                    <c:set var="addUnknowProduct" value="false"/>
                    <c:set var="createRepairRequest" value="false"/>

                    <c:forEach var="perm" items="${sessionScope.permissionIds}">
                        <c:if test="${perm == 70}"><c:set var="addUnknowProduct" value="true"/></c:if>
                        <c:if test="${perm == 126}"><c:set var="updateUnknowProduct" value="true"/></c:if>
                        <c:if test="${perm == 128}"><c:set var="createRepairRequest" value="true"/></c:if>

                    </c:forEach>
                    <form action="listUnknown" method="get">
                        <input type="text" id="productCode" name="productCode" 
                               placeholder="Product Code" 
                               value="${param.productCode}" 
                               oninput="validateProductCode(this)">
                        <span id="productCodeError" style="color: red;"></span>

                        <input type="text" id="productName" name="productName" 
                               placeholder="Product Name" 
                               value="${param.productName}" 
                               oninput="validateProductName(this)">
                        <span id="productNameError" style="color: red;"></span>

                        <input type="text" id="description" name="description" 
                               placeholder="Description" 
                               value="${param.description}" 
                               oninput="validateDescription(this)">
                        <span id="descriptionError" style="color: red; font-size: 12px; margin-left: 5px;"></span>

                        <input type="date" name="receivedDate" value="${param.receivedDate}">

                        <input type="text" id="customerName" name="customerName" 
                               placeholder="Customer Name"
                               value="${param.customerName}" 
                               oninput="validateCustomerName(this)">
                        <span id="customerNameError" style="color: red; font-size: 12px; margin-left: 5px;"></span>

                        <input type="text" id="phone" name="phone" 
                               placeholder="Phone"
                               value="${param.phone}" 
                               oninput="validatePhone(this)">
                        <span id="phoneError" style="color: red; font-size: 12px; margin-left: 5px;"></span>

                        <input type="hidden" name="page" value="${currentPage}" />
                        <input type="hidden" name="pageSize" value="${pageSize}" />

                        <button type="submit">Search</button>

                    </form>

                    <form action="listUnknown" method="get" class="page-size-form">
                        <input type="hidden" name="productCode" value="${param.productCode}" />
                        <input type="hidden" name="productName" value="${param.productName}" />
                        <input type="hidden" name="description" value="${param.description}" />
                        <input type="hidden" name="receivedDate" value="${param.receivedDate}" />
                        <input type="hidden" name="customerName" value="${param.customerName}" />
                        <input type="hidden" name="phone" value="${param.phone}" />
                        <input type="hidden" name="page" value="1" />

                        <label for="pageSizeSelect">Products per page:</label>
                        <select id="pageSizeSelect" name="pageSize" onchange="toggleCustomInput(this.value)">
                            <option value="5"  <c:if test="${pageSize == 5}">selected</c:if>>5</option>
                            <option value="10" <c:if test="${pageSize == 10}">selected</c:if>>10</option>
                            <option value="15" <c:if test="${pageSize == 15}">selected</c:if>>15</option>
                            <option value="20" <c:if test="${pageSize == 20}">selected</c:if>>20</option>
                            <option value="25" <c:if test="${pageSize == 25}">selected</c:if>>25</option>
                                <option value="custom"
                                <c:if test="${pageSize != 5 && pageSize != 10 && pageSize != 15 && pageSize != 20 && pageSize != 25}">
                                    selected
                                </c:if>
                                >Custom</option>
                        </select>

                        <input type="number" name="customPageSize" id="customPageSizeInput" 
                               min="1"
                               value="<c:out value='${pageSize != 5 && pageSize != 10 && pageSize != 15 && pageSize != 20 && pageSize != 25 ? pageSize : ""}'/>"
                               style="display: none;" />
                        <button type="submit">Apply</button>
                        <c:if test="${addUnknowProduct}">
                            <a href="addUnknown" class="button">
                                Add Unknown Product
                            </a>                   
                        </c:if>

                        <button class="search" onclick="window.location.href = 'listUnknown'">
                            All Product
                        </button>

                        <a href="viewProduct" class="button">
                            Back
                        </a>

                    </form>

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
                                    <td style="display: flex">
                                        
                                        <form action="updateUnknown" method="get">
                                            <input type="hidden" name="productId" value="${product.unknownProductId}">
                                            <input type="hidden" name="customerId" value="${product.customerId}">
                                            <button type="submit">Update</button>
                                        </form>

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

                    <div class="pagination">
                        <c:if test="${totalPages > 1}">
                            <!-- First -->
                            <c:url var="firstUrl" value="listUnknown">
                                <c:param name="page" value="1" />
                                <c:param name="pageSize" value="${pageSize}" />
                                <c:param name="productCode" value="${param.productCode}" />
                                <c:param name="productName" value="${param.productName}" />
                                <c:param name="description" value="${param.description}" />
                                <c:param name="receivedDate" value="${param.receivedDate}" />
                                <c:param name="customerName" value="${param.customerName}" />
                                <c:param name="phone" value="${param.phone}" />
                            </c:url>
                            <a href="${firstUrl}">First</a>

                            <!-- Previous -->
                            <c:url var="prevUrl" value="listUnknown">
                                <c:param name="page" value="${currentPage - 1 <= 0 ? 1 : currentPage - 1}" />
                                <c:param name="pageSize" value="${pageSize}" />
                                <c:param name="productCode" value="${param.productCode}" />
                                <c:param name="productName" value="${param.productName}" />
                                <c:param name="description" value="${param.description}" />
                                <c:param name="receivedDate" value="${param.receivedDate}" />
                                <c:param name="customerName" value="${param.customerName}" />
                                <c:param name="phone" value="${param.phone}" />
                            </c:url>
                            <a href="${prevUrl}">Previous</a>

                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <c:url var="pageUrl" value="listUnknown">
                                    <c:param name="page" value="${i}" />
                                    <c:param name="pageSize" value="${pageSize}" />
                                    <c:param name="productCode" value="${param.productCode}" />
                                    <c:param name="productName" value="${param.productName}" />
                                    <c:param name="description" value="${param.description}" />
                                    <c:param name="receivedDate" value="${param.receivedDate}" />
                                    <c:param name="customerName" value="${param.customerName}" />
                                    <c:param name="phone" value="${param.phone}" />
                                </c:url>
                                <a href="${pageUrl}" class="${currentPage == i ? 'active' : ''}">${i}</a>
                            </c:forEach>

                            <!-- Next -->
                            <c:url var="nextUrl" value="listUnknown">
                                <c:param name="page" value="${currentPage + 1 > totalPages ? totalPages : currentPage + 1}" />
                                <c:param name="pageSize" value="${pageSize}" />
                                <c:param name="productCode" value="${param.productCode}" />
                                <c:param name="productName" value="${param.productName}" />
                                <c:param name="description" value="${param.description}" />
                                <c:param name="receivedDate" value="${param.receivedDate}" />
                                <c:param name="customerName" value="${param.customerName}" />
                                <c:param name="phone" value="${param.phone}" />
                            </c:url>
                            <a href="${nextUrl}">Next</a>

                            <!-- Last -->
                            <c:url var="lastUrl" value="listUnknown">
                                <c:param name="page" value="${totalPages}" />
                                <c:param name="pageSize" value="${pageSize}" />
                                <c:param name="productCode" value="${param.productCode}" />
                                <c:param name="productName" value="${param.productName}" />
                                <c:param name="description" value="${param.description}" />
                                <c:param name="receivedDate" value="${param.receivedDate}" />
                                <c:param name="customerName" value="${param.customerName}" />
                                <c:param name="phone" value="${param.phone}" />
                            </c:url>
                            <a href="${lastUrl}">Last</a>
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
                let errorMessage = document.getElementById("productNameError");

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

        <script>
            // Hiển thị/Ẩn ô customPageSize khi dropdown thay đổi
            function toggleCustomInput(value) {
                var customInput = document.getElementById("customPageSizeInput");
                if (value === "custom") {
                    customInput.style.display = "inline-block";
                } else {
                    customInput.style.display = "none";
                }
            }

            // Khi load trang, kiểm tra nếu đang ở "custom" thì show input
            window.onload = function () {
                var pageSizeSelect = document.getElementById("pageSizeSelect");
                toggleCustomInput(pageSizeSelect.value);
            };
        </script>
    </body>
</html>
