<%-- 
    Document   : ListInvoiceRepair
    Created on : Mar 24, 2025, 11:55:55 PM
    Author     : sonNH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>List Invoices</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            /* Bạn có thể tùy chỉnh CSS theo ý muốn */
            body {
                font-family: 'Inter', sans-serif;
                background-color: #f5f6fa;
                margin: 0;
                padding: 0;
            }
            .container {
                padding: 20px;
            }
            h2 {
                color: #007BFF;
                margin-bottom: 20px;
                text-align: center;
            }
            form.search-form {
                margin-bottom: 20px;
                display: flex;
                flex-wrap: wrap;
                gap: 15px;
                align-items: center;
            }
            .search-form label {
                font-weight: 600;
            }
            .search-form input[type="text"],
            .search-form input[type="date"],
            .search-form select {
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 14px;
            }
            .search-form button {
                padding: 8px 15px;
                border: none;
                background-color: #007BFF;
                color: #fff;
                cursor: pointer;
                border-radius: 4px;
            }
            .search-form button:hover {
                background-color: #0056b3;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                background-color: #fff;
                margin-bottom: 20px;
            }
            table thead {
                background-color: #e9ecef;
            }
            table th, table td {
                padding: 10px;
                text-align: left;
                border: 1px solid #dee2e6;
            }
            table th {
                font-weight: 600;
            }
            .pagination {
                display: flex;
                justify-content: center;
                align-items: center;
                gap: 8px;
            }
            .pagination a, .pagination span {
                display: inline-block;
                padding: 6px 12px;
                border: 1px solid #ccc;
                color: #007BFF;
                text-decoration: none;
                border-radius: 4px;
            }
            .pagination a:hover {
                background-color: #007BFF;
                color: #fff;
            }
            .pagination .active {
                background-color: #007BFF;
                color: #fff;
                pointer-events: none;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>List of Invoices</h2>
            
            <c:if test="${not empty errorMessage}">
                <div style="color: red; margin-bottom: 10px;">${errorMessage}</div>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div style="color: green; margin-bottom: 10px;">${successMessage}</div>
            </c:if>

            <form class="search-form" method="get" action="listInvoiceRepair">
                <label for="invoiceNumber">Invoice Number:</label>
                <input type="text" name="invoiceNumber" id="invoiceNumber" 
                       value="${param.invoiceNumber}" placeholder="VD: INV12345" />
                
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
                
                <button type="submit">Apply</button>
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

            <div class="pagination">
                <c:if test="${pageIndex > 1}">
                    <a href="listInvoiceRepair?pageIndex=1&pageSize=${pageSize}&invoiceNumber=${param.invoiceNumber}&issueDate=${param.issueDate}&dueDate=${param.dueDate}">
                        First
                    </a>
                </c:if>

                <c:if test="${pageIndex > 1}">
                    <a href="listInvoiceRepair?pageIndex=${pageIndex - 1}&pageSize=${pageSize}&invoiceNumber=${param.invoiceNumber}&issueDate=${param.issueDate}&dueDate=${param.dueDate}">
                        Previous
                    </a>
                </c:if>

                <c:forEach var="i" begin="1" end="${totalPage}">
                    <c:choose>
                        <c:when test="${i == pageIndex}">
                            <span class="active">${i}</span>
                        </c:when>
                        <c:otherwise>
                            <a href="listInvoiceRepair?pageIndex=${i}&pageSize=${pageSize}&invoiceNumber=${param.invoiceNumber}&issueDate=${param.issueDate}&dueDate=${param.dueDate}">
                                ${i}
                            </a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${pageIndex < totalPage}">
                    <a href="listInvoiceRepair?pageIndex=${pageIndex + 1}&pageSize=${pageSize}&invoiceNumber=${param.invoiceNumber}&issueDate=${param.issueDate}&dueDate=${param.dueDate}">
                        Next
                    </a>
                </c:if>

                <c:if test="${pageIndex < totalPage}">
                    <a href="listInvoiceRepair?pageIndex=${totalPage}&pageSize=${pageSize}&invoiceNumber=${param.invoiceNumber}&issueDate=${param.issueDate}&dueDate=${param.dueDate}">
                        Last
                    </a>
                </c:if>
            </div>
        </div>
    </body>
</html>

