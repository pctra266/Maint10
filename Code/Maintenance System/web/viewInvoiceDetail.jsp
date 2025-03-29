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
            /* ======= RESET & GLOBAL ======= */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Inter', sans-serif;
            }
            body {
                background: #f2f2f2;
                color: #333;
                line-height: 1.6;
            }
            a {
                text-decoration: none;
                color: inherit;
            }

            .wrapper {
                display: flex;
                min-height: 100vh;
            }
            .navbar-left,
            .jspIncludeSidebar {
                width: 250px;
                background: #2c3e50;
                color: #fff;
                padding: 20px;
            }
            .main {
                flex: 1;
                display: flex;
                flex-direction: column;
                padding: 20px;
            }
            .content {
                background: #fff;
                max-width: 800px;
                margin:20px auto;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.05);
            }

            /* ======= HEADER & THÔNG TIN CHUNG ======= */
            .invoice-header {
                text-align: center;
                margin-bottom: 20px;
            }
            .invoice-header h1 {
                font-size: 24px;
                font-weight: 600;
                text-transform: uppercase;
                margin-bottom: 5px;
            }
            .invoice-header p {
                font-size: 14px;
                color: #666;
            }
            .invoice-info {
                display: flex;
                justify-content: space-between;
            }
            .invoice-info-left,
            .invoice-info-right {
                width: 48%;
            }
            .invoice-info-left h3,
            .invoice-info-right h3 {
                font-size: 16px;
                margin-bottom: 8px;
                text-transform: uppercase;
                color: #333;
            }
            .invoice-info-left p,
            .invoice-info-right p {
                font-size: 14px;
                line-height: 1.5;
                color: #555;
            }
            .invoice-details {
                display: flex;
                flex-direction: column;
                gap: 15px;
            }
            .invoice-detail-row {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 10px 0;
                border-bottom: 1px dashed #ccc;
            }
            .invoice-detail-row:last-child {
                border-bottom: none;
            }
            .detail-label {
                font-weight: 600;
                color: #333;
                flex-basis: 40%;
            }
            .detail-value {
                flex-basis: 60%;
                text-align: right;
                color: #555;
            }
            .center {
                text-align: center;
                margin-top: 30px;
            }
            .center a {
                display: inline-block;
                margin: 0 10px;
                padding: 12px 25px;
                background: #1565c0;
                color: #fff;
                border-radius: 30px;
                font-weight: 600;
                transition: all 0.3s ease;
            }
            .center a:hover {
                background: #0e3d7a;
                transform: translateY(-2px);
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }

            /* ======= RESPONSIVE ======= */
            @media (max-width: 768px) {
                .content {
                    padding: 20px;
                    margin: 20px;
                }
                .invoice-info {
                    flex-direction: column;
                }
                .invoice-info-left,
                .invoice-info-right {
                    width: 100%;
                    margin-bottom: 15px;
                }
                .invoice-detail-row {
                    flex-direction: column;
                    align-items: flex-start;
                }
                .detail-value {
                    text-align: left;
                    margin-top: 5px;
                }
                .center a {
                    padding: 10px 20px;
                    font-size: 14px;
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
                    <!-- HEADER -->
                    <div class="invoice-header">
                        <h1>CÔNG TY NHS</h1>
                        <p>123 Đường ABC, Thành phố DEF - (+84) 912 345 678</p>
                    </div>
                    <div class="invoice-info">
                        <div class="invoice-info-left">
                            <h3>Receiver</h3>
                            <p>
                                NHS<br>
                                Số 123 Đường ABC, Thành phố DEF
                            </p>
                        </div>
                        <div class="invoice-info-right">
                            <h3>Invoice information</h3>
                            <p>
                                Hóa đơn số: <strong>12345</strong><br>
                                Ngày: <strong>16/06/2025</strong>
                            </p>
                        </div>
                    </div>

                    <h2 style="text-align:center; margin-bottom: 10px;">Invoice</h2>

                    <c:if test="${not empty invoiceDetail}">
                        <div class="invoice-details">
                            <div class="invoice-detail-row">
                                <div class="detail-label">Invoice Number</div>
                                <div class="detail-value">${invoiceDetail.InvoiceNumber}</div>
                            </div>
                            <div class="invoice-detail-row">
                                <div class="detail-label">Warranty Card Code</div>
                                <div class="detail-value">${invoiceDetail.WarrantyCardCode}</div>
                            </div>

                            <div class="invoice-detail-row">
                                <div class="detail-label">Issued Date</div>
                                <div class="detail-value">
                                    <fmt:formatDate value="${invoiceDetail.IssuedDate}" pattern="yyyy-MM-dd"/>
                                </div>
                            </div>
                            <div class="invoice-detail-row">
                                <div class="detail-label">Due Date</div>
                                <div class="detail-value">
                                    <fmt:formatDate value="${invoiceDetail.DueDate}" pattern="yyyy-MM-dd"/>
                                </div>
                            </div>
                            <div class="invoice-detail-row">
                                <div class="detail-label">Status</div>
                                <div class="detail-value">${invoiceDetail.Status}</div>
                            </div>
                            <div class="invoice-detail-row">
                                <div class="detail-label">Created By</div>
                                <div class="detail-value">${invoiceDetail.CreatedByName}</div>
                            </div>
                            <div class="invoice-detail-row">
                                <div class="detail-label">Received By</div>
                                <div class="detail-value">${invoiceDetail.ReceivedByName}</div>
                            </div>

                            <div class="invoice-detail-row">
                                <div class="detail-label">Issue Description</div>
                                <div class="detail-value">${invoiceDetail.IssueDescription}</div>
                            </div>
                            <div class="invoice-detail-row">
                                <div class="detail-label">Amount</div>
                                <div class="detail-value">
                                    <fmt:formatNumber value="${invoiceDetail.Amount}" maxFractionDigits="0" />
                                    $
                                </div>
                            </div>
                        </div>
                        <div class="center">
                            <a style="text-decoration: none" class="back-link" href="listInvoiceRepair">Back</a>
                            <a style="text-decoration: none" class="back-link" href="exportInvoicePDF?invoiceId=${invoiceDetail.InvoiceID}">Export PDF</a>
                        </div>
                    </c:if>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
