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
        <link rel="shortcut icon" href="img/icons/icon-48x48.png" />
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <style>
            /* CSS for main section */
            .main {
                background-color: #f0f8ff;
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
                color: #326ABC;
                margin-bottom: 20px;
                font-size: 24px;
                font-weight: 600;
                text-align: center;
            }

            /* Search Form: labels sát ô nhập */
            .search-form {
                display: flex;
                flex-direction: column;
                gap: 15px;
                margin-bottom: 20px;
                align-items: center;
            }

            .search-form .input-row {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
                justify-content: center;
                align-items: center;
            }

            .search-form label {
                margin-right: 5px;
                display: inline-block;
                width: auto;
                font-weight: bold;
                color: #333;
            }

            .search-form input,
            .search-form select {
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                outline: none;
                transition: border-color 0.3s ease;
            }

            .search-form input:focus,
            .search-form select:focus {
                border-color: #326ABC;
            }

            .search-form button {
                background-color: #326ABC;
                color: #fff;
                border: none;
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s ease;
                font-weight: 600;
            }

            .search-form button:hover {
                background-color: #265a9e;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
            }

            table thead {
                background-color: #326ABC;
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
                border: 1px solid #326ABC;
                border-radius: 4px;
                text-decoration: none;
                color: #326ABC;
                transition: background-color 0.3s ease;
            }

            .pagination a:hover {
                background-color: #326ABC;
                color: #fff;
            }

            .pagination span.active {
                background-color: #326ABC;
                color: #fff;
                font-weight: bold;
            }

            /* Custom style for custom page size div */
            #customPageSizeDiv {
                display: none;
                flex-wrap: wrap;
                align-items: center;
                gap: 10px;
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
                        <h2>List of Invoices</h2>

                        <c:if test="${not empty errorMessage}">
                            <div style="color: red; margin-bottom: 10px;">${errorMessage}</div>
                        </c:if>
                        <c:if test="${not empty successMessage}">
                            <div style="color: green; margin-bottom: 10px;">${successMessage}</div>
                        </c:if>

                        <!-- Search Form -->
                        <form class="search-form" method="get" action="listInvoiceRepair" onsubmit="return validateInvoiceNumber();">
                            <div class="input-row">
                                <label for="invoiceNumber">Invoice Number:</label>
                                <input type="text" name="invoiceNumber" id="invoiceNumber" value="${param.invoiceNumber}" placeholder="e.g., INV12345" />

                                <label for="issueDate">Issue Date:</label>
                                <input type="date" name="issueDate" id="issueDate" value="${param.issueDate}" />

                                <label for="dueDate">Due Date:</label>
                                <input type="date" name="dueDate" id="dueDate" value="${param.dueDate}" />
                            </div>

                            <!-- Row for Products per page and buttons -->
                            <div class="input-row">
                                <label for="pageSize">Products per page:</label>
                                <select name="pageSize" id="pageSize">
                                    <option value="5" ${param.pageSize == '5' && empty param.customPageSize ? 'selected="selected"' : ''}>5</option>
                                    <option value="10" ${param.pageSize == '10' ? 'selected="selected"' : ''}>10</option>
                                    <option value="15" ${param.pageSize == '15' ? 'selected="selected"' : ''}>15</option>
                                    <option value="20" ${param.pageSize == '20' ? 'selected="selected"' : ''}>20</option>
                                    <option value="25" ${param.pageSize == '25' ? 'selected="selected"' : ''}>25</option>
                                    <!-- Nếu có customPageSize thì hiển thị lựa chọn Custom -->
                                    <option value="Custom" ${not empty param.customPageSize ? 'selected="selected"' : ''}>Custom</option>
                                </select>

                                <div id="customPageSizeDiv">
                                    <label for="customPageSize">Custom Page Size:</label>
                                    <input type="number" name="customPageSize" id="customPageSize" min="1" placeholder="Enter number" value="${param.customPageSize}" />
                                </div>

                                <button type="button" onclick="window.location.href = 'listInvoiceRepair'">All</button>
                                <button type="button" onclick="window.location.href = 'warrantyCardRepairContractor'">Back</button>
                                <button type="submit">Apply</button>
                            </div>
                        </form>

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
                                                    <fmt:formatDate value="${invoice.issuedDate}" pattern="dd-MM-yyyy"/>
                                                </td>
                                                <td>
                                                    <fmt:formatDate value="${invoice.dueDate}" pattern="dd-MM-yyyy"/>
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

                        <!-- Pagination -->
                        <div class="pagination">
                            <c:if test="${pageIndex > 1}">
                                <a href="listInvoiceRepair?pageIndex=1&pageSize=${param.pageSize}&invoiceNumber=${param.invoiceNumber}&issueDate=${param.issueDate}&dueDate=${param.dueDate}&customPageSize=${param.customPageSize}">
                                    First
                                </a>
                            </c:if>

                            <c:if test="${pageIndex > 1}">
                                <a href="listInvoiceRepair?pageIndex=${pageIndex - 1}&pageSize=${param.pageSize}&invoiceNumber=${param.invoiceNumber}&issueDate=${param.issueDate}&dueDate=${param.dueDate}&customPageSize=${param.customPageSize}">
                                    Previous
                                </a>
                            </c:if>

                            <c:forEach var="i" begin="1" end="${totalPage}">
                                <c:choose>
                                    <c:when test="${i == pageIndex}">
                                        <span class="active">${i}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="listInvoiceRepair?pageIndex=${i}&pageSize=${param.pageSize}&invoiceNumber=${param.invoiceNumber}&issueDate=${param.issueDate}&dueDate=${param.dueDate}&customPageSize=${param.customPageSize}">
                                            ${i}
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                            <c:if test="${pageIndex < totalPage}">
                                <a href="listInvoiceRepair?pageIndex=${pageIndex + 1}&pageSize=${param.pageSize}&invoiceNumber=${param.invoiceNumber}&issueDate=${param.issueDate}&dueDate=${param.dueDate}&customPageSize=${param.customPageSize}">
                                    Next
                                </a>
                            </c:if>

                            <c:if test="${pageIndex < totalPage}">
                                <a href="listInvoiceRepair?pageIndex=${totalPage}&pageSize=${param.pageSize}&invoiceNumber=${param.invoiceNumber}&issueDate=${param.issueDate}&dueDate=${param.dueDate}&customPageSize=${param.customPageSize}">
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
            const pageSizeSelect = document.getElementById("pageSize");
            const customPageSizeDiv = document.getElementById("customPageSizeDiv");
            function toggleCustomPageSize() {
                if (pageSizeSelect.value === "Custom") {
                    customPageSizeDiv.style.display = "flex";
                } else {
                    customPageSizeDiv.style.display = "none";
                }
            }
            toggleCustomPageSize();
            pageSizeSelect.addEventListener("change", toggleCustomPageSize);

            function validateInvoiceNumber() {
                const invoiceInput = document.getElementById("invoiceNumber");
                const invoiceVal = invoiceInput.value; // Lấy giá trị nguyên bản, không loại bỏ khoảng trắng
                if (invoiceVal === "") {
                    return true;
                }
                // Kiểm tra nếu có bất kỳ ký tự khoảng trắng nào
                if (invoiceVal.match(/\s/)) {
                    alert("Invoice Number can only contain letters and numbers (no spaces allowed).");
                    return false;
                }
                if (!invoiceVal.match(/^[A-Za-z0-9]+$/)) {
                    alert("Invoice Number can only contain letters and numbers (no spaces allowed).");
                    return false;
                }
                return true;
            }
        </script>
        <script src="js/app.js"></script>
    </body>
</html>
