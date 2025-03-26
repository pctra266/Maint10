<%-- 
    Document   : updateProduct1
    Created on : Feb 6, 2025, 5:07:44 AM
    Author     : sonNH
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            /* CSS cho phần main */
            .main {
                background-color: #f0f8ff; /* nền nhẹ nhàng */
                padding: 20px;
                margin: auto;
                font-family: 'Inter', sans-serif;
            }

            .content {
                background: #ffffff;
                border: 1px solid #b0c4de;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                padding: 20px;
                margin-top: 10px;
            }

            .container h2 {
                color: #1e90ff; /* xanh dương chủ đạo */
                margin-bottom: 20px;
                font-size: 24px;
                font-weight: 600;
            }

            .search-form {
                display: flex;
                flex-wrap: wrap;
                gap: 15px;
                margin-bottom: 20px;
                align-items: center;
            }

            .search-form label {
                flex: 1 0 150px;
                font-weight: bold;
                color: #333;
            }

            .search-form input,
            .search-form select {
                flex: 1 0 200px;
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                outline: none;
                transition: border-color 0.3s ease;
            }

            .search-form input:focus,
            .search-form select:focus {
                border-color: #1e90ff;
            }

            .search-form button {
                background-color: #1e90ff;
                color: #fff;
                border: none;
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s ease;
                font-weight: 600;
            }

            .search-form button:hover {
                background-color: #0f78d1;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
            }

            table thead {
                background-color: #1e90ff;
                color: #fff;
            }

            table th,
            table td {
                padding: 12px;
                text-align: left;
                border: 1px solid #b0c4de;
            }

            table tbody tr:nth-child(even) {
                background-color: #f9f9f9;
            }

            .pagination {
                display: flex;
                justify-content: center;
                gap: 10px;
                padding: 10px 0;
            }

            .pagination a,
            .pagination span {
                padding: 8px 12px;
                border: 1px solid #1e90ff;
                border-radius: 4px;
                text-decoration: none;
                color: #1e90ff;
                transition: background-color 0.3s ease;
            }

            .pagination a:hover {
                background-color: #1e90ff;
                color: #fff;
            }

            .pagination span.active {
                background-color: #1e90ff;
                color: #fff;
                font-weight: bold;
            }

            /* Chỉ tác động vào khu vực form tìm kiếm (search-form) trong main */
            .search-form {
                display: flex;
                flex-wrap: wrap;          /* Cho phép xuống hàng nếu màn hình hẹp */
                align-items: center;      /* Căn giữa theo trục dọc */
                gap: 10px;                /* Khoảng cách giữa các phần tử */
                margin-bottom: 20px;
            }

            /* Label chung */
            .search-form label {
                font-weight: bold;
                color: #333;
                margin-right: 5px;
            }

            /* Riêng label "Products per page" có thể thêm style tùy ý */
            .search-form label[for="pageSize"] {
                /* Ví dụ: màu chữ đậm hơn hoặc thêm margin, tùy thích */
            }

            /* Style cho select */
            .search-form select {
                border: 1px solid #ccc;
                border-radius: 4px;
                padding: 8px;
                outline: none;
                transition: border-color 0.3s ease;
                min-width: 80px; /* Đảm bảo có độ rộng nhất định */
            }

            .search-form select:focus {
                border-color: #1e90ff;
            }

            /* Style cho button "Apply" */
            .search-form button[type="submit"] {
                background-color: #1e90ff;
                color: #fff;
                border: none;
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s ease;
                font-weight: 600;
            }

            .search-form button[type="submit"]:hover {
                background-color: #0f78d1;
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
                        <h2 style="text-align: center">List of Invoices</h2>

                        <c:if test="${not empty errorMessage}">
                            <div style="color: red; margin-bottom: 10px;">${errorMessage}</div>
                        </c:if>
                        <c:if test="${not empty successMessage}">
                            <div style="color: green; margin-bottom: 10px;">${successMessage}</div>
                        </c:if>

                        <!-- Form search -->
                        <form class="search-form" method="get" action="listInvoiceRepair" onsubmit="return validateInvoiceNumber();">
                            <label for="invoiceNumber">Invoice Number:</label>
                            <input type="text" name="invoiceNumber" id="invoiceNumber" 
                                   value="${param.invoiceNumber}" 
                                   placeholder="VD: INV12345" />

                            <label for="issueDate">Issue Date:</label>
                            <input type="date" name="issueDate" id="issueDate" 
                                   value="${param.issueDate}" />

                            <label for="dueDate">Due Date:</label>
                            <input type="date" name="dueDate" id="dueDate" 
                                   value="${param.dueDate}" />

                            <label for="pageSize">Products per page:</label>
                            <select name="pageSize" id="pageSize">
                                <option value="5"  <c:if test="${param.pageSize == '5'}">selected</c:if>>5</option>
                                <option value="10" <c:if test="${param.pageSize == '10'}">selected</c:if>>10</option>
                                <option value="15" <c:if test="${param.pageSize == '15'}">selected</c:if>>15</option>
                                <option value="20" <c:if test="${param.pageSize == '20'}">selected</c:if>>20</option>
                                <option value="25" <c:if test="${param.pageSize == '25'}">selected</c:if>>25</option>
                                <option value="Custom" <c:if test="${param.pageSize == 'Custom'}">selected</c:if>>Custom</option>
                                </select>

                                <div id="customPageSizeDiv">
                                    <label for="customPageSize">Custom Page Size:</label>
                                    <input type="number" name="customPageSize" id="customPageSize" 
                                           min="1" 
                                           placeholder="Enter number" 
                                           value="${param.customPageSize}" />
                            </div>

                            <button type="submit">Apply</button>
                        </form>

                        <!-- Bảng hiển thị -->
                        <table>
                            <thead>
                                <tr>
                                    <th>Invoice Number</th>
                                    <th>Issue Date</th>
                                    <th>Due Date</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${empty listInvoice}">
                                        <tr>
                                            <td colspan="4" style="text-align: center;">No Invoices Found</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="invoice" items="${listInvoice}">
                                            <tr>
                                                <td>${invoice.invoiceNumber}</td>
                                                <td>
                                                    <fmt:formatDate value="${invoice.issuedDate}" pattern="yyyy-MM-dd"/>
                                                </td>
                                                <td>
                                                    <fmt:formatDate value="${invoice.dueDate}" pattern="yyyy-MM-dd"/>
                                                </td>
                                                <td>
                                                    <a href="invoiceDetail?invoiceId=${invoice.invoiceID}">View Detail</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>

                        <!-- Phân trang -->
                        <div class="pagination">
                            <c:if test="${pageIndex > 1}">
                                <a href="listInvoiceRepair?pageIndex=1&pageSize=${pageSize}&invoiceNumber=${param.invoiceNumber}&issueDate=${param.issueDate}&dueDate=${param.dueDate}&customPageSize=${param.customPageSize}">
                                    First
                                </a>
                            </c:if>

                            <c:if test="${pageIndex > 1}">
                                <a href="listInvoiceRepair?pageIndex=${pageIndex - 1}&pageSize=${pageSize}&invoiceNumber=${param.invoiceNumber}&issueDate=${param.issueDate}&dueDate=${param.dueDate}&customPageSize=${param.customPageSize}">
                                    Previous
                                </a>
                            </c:if>

                            <c:forEach var="i" begin="1" end="${totalPage}">
                                <c:choose>
                                    <c:when test="${i == pageIndex}">
                                        <span class="active">${i}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="listInvoiceRepair?pageIndex=${i}&pageSize=${pageSize}&invoiceNumber=${param.invoiceNumber}&issueDate=${param.issueDate}&dueDate=${param.dueDate}&customPageSize=${param.customPageSize}">
                                            ${i}
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                            <c:if test="${pageIndex < totalPage}">
                                <a href="listInvoiceRepair?pageIndex=${pageIndex + 1}&pageSize=${pageSize}&invoiceNumber=${param.invoiceNumber}&issueDate=${param.issueDate}&dueDate=${param.dueDate}&customPageSize=${param.customPageSize}">
                                    Next
                                </a>
                            </c:if>

                            <c:if test="${pageIndex < totalPage}">
                                <a href="listInvoiceRepair?pageIndex=${totalPage}&pageSize=${pageSize}&invoiceNumber=${param.invoiceNumber}&issueDate=${param.issueDate}&dueDate=${param.dueDate}&customPageSize=${param.customPageSize}">
                                    Last
                                </a>
                            </c:if>
                        </div>
                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>

        <script>
            // Ẩn/hiện ô nhập customPageSize khi select "Custom"
            const pageSizeSelect = document.getElementById("pageSize");
            const customPageSizeDiv = document.getElementById("customPageSizeDiv");
            function toggleCustomPageSize() {
                if (pageSizeSelect.value === "Custom") {
                    customPageSizeDiv.style.display = "flex";
                } else {
                    customPageSizeDiv.style.display = "none";
                }
            }
            // Gọi ngay khi trang load
            toggleCustomPageSize();
            // Lắng nghe sự kiện thay đổi
            pageSizeSelect.addEventListener("change", toggleCustomPageSize);
            // Chỉ validate ô "Invoice Number"
            function validateInvoiceNumber() {
                const invoiceInput = document.getElementById("invoiceNumber");
                const invoiceVal = invoiceInput.value.trim();
                // Cho phép để trống (nếu muốn bắt buộc nhập thì bỏ điều kiện này)
                if (invoiceVal === "") {
                    return true;
                }

                // Nếu không để trống thì kiểm tra ký tự đặc biệt
                const regex = /^[A-Za-z0-9\s]+$/;
                if (!regex.test(invoiceVal)) {
                    alert("Invoice Number chỉ được nhập chữ, số hoặc khoảng trắng (không chứa ký tự đặc biệt).");
                    return false;
                }
                return true;
            }
        </script>
        <script src="js/app.js"></script>
    </body>
</html>
