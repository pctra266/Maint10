<%-- 
    Document   : updateProduct1
    Created on : Feb 6, 2025, 5:07:44 AM
    Author     : sonNH
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">

                    <h2>Invoice</h2>

                    <c:if test="${not empty invoiceDetail}">
                        <table>
                            <tr>
                                <th>Invoice Number</th>
                                <td>${invoiceDetail.InvoiceNumber}</td>
                            </tr>
                            <tr>
                                <th>Amount</th>
                                <td>${invoiceDetail.Amount}</td>
                            </tr>
                            <tr>
                                <th>Issued Date</th>
                                <td>
                                    <fmt:formatDate value="${invoiceDetail.IssuedDate}" pattern="yyyy-MM-dd"/>
                                </td>
                            </tr>
                            <tr>
                                <th>Due Date</th>
                                <td>
                                    <fmt:formatDate value="${invoiceDetail.DueDate}" pattern="yyyy-MM-dd"/>
                                </td>
                            </tr>
                            <tr>
                                <th>Status</th>
                                <td>${invoiceDetail.Status}</td>
                            </tr>
                            <tr>
                                <th>Created By</th>
                                <td>${invoiceDetail.CreatedByName}</td>
                            </tr>
                            <tr>
                                <th>Received By</th>
                                <td>${invoiceDetail.ReceivedByName}</td>
                            </tr>
                            <tr>
                                <th>Warranty Card Code</th>
                                <td>${invoiceDetail.WarrantyCardCode}</td>
                            </tr>
                            <tr>
                                <th>Issue Description</th>
                                <td>${invoiceDetail.IssueDescription}</td>
                            </tr>
                        </table>
                        <div class="center">
                            <a class="back-link" href="listInvoiceRepair">Back</a>
                        </div>
                        <div class="center">
                            <a class="back-link" href="exportInvoicePDF?invoiceId=${invoiceDetail.InvoiceID}">Export PDF</a>
                        </div>
                    </c:if>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
