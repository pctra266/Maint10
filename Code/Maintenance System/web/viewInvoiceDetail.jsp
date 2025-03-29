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
            /* ======= MAIN WRAPPER ======= */
            .main {
                background: linear-gradient(135deg, #e0f7fa, #ffffff);
                font-family: 'Inter', sans-serif;
            }

            /* ======= MAIN CONTENT AREA ======= */
            .content {
                background: #ffffff;
                border: none;
                border-radius: 10px;
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
                padding: 30px;
            }

            /* ======= HEADING ======= */
            .content h2 {
                color: #1565c0; /* Màu xanh dương đậm */
                font-size: 28px;
                font-weight: 700;
                margin-bottom: 25px;
                text-align: center;
            }

            /* ======= TABLE STYLING ======= */
            .content table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 25px;
            }

            .content table th,
            .content table td {
                padding: 14px 20px;
                text-align: left;
            }

            .content table th {
                background: linear-gradient(135deg, #2196f3, #1e88e5);
                color: #fff;
                font-size: 16px;
                font-weight: 600;
                border-bottom: 3px solid #1565c0;
            }

            .content table tr {
                transition: background-color 0.3s ease;
            }

            .content table tr:hover {
                background-color: #f1f8ff;
            }

            .content table tr:nth-child(even) td {
                background-color: #f9f9f9;
            }

            /* ======= CENTERED ELEMENTS ======= */
            .content .center {
                text-align: center;
                margin-top: 20px;
            }

            /* ======= BUTTON LINKS ======= */
            .content a.back-link {
                display: inline-block;
                padding: 12px 25px;
                margin: 0 10px;
                background: linear-gradient(135deg, #2196f3, #1e88e5);
                color: #fff;
                text-decoration: none;
                border-radius: 30px;
                font-weight: 600;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }

            .content a.back-link:hover {
                transform: translateY(-3px);
                box-shadow: 0 4px 8px rgba(21, 101, 192, 0.3);
            }

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
