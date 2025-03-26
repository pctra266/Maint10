<%-- 
    Document   : Test
    Created on : Mar 23, 2025, 2:16:32 PM
    Author     : ADMIN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link href="css/light.css" rel="stylesheet">

        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
                margin: 0;
                padding: 0;
            }
            .wrapper {
                display: flex;
            }
            .main {
                flex-grow: 1;
                padding: 20px;
            }
            .content {
                background: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            .table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            .table th, .table td {
                padding: 10px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }
            .table tr:nth-child(even) {
                background-color: #f2f2f2;
            }
            .alert {
                padding: 10px;
                background-color: #009926; 
                color: white;
                margin-bottom: 15px;
                border-radius: 5px;
                display: block; 
            }
            .alert1 {
                padding: 10px;
                background-color: red; 
                color: white;
                margin-bottom: 15px;
                border-radius: 5px;
                display: block; 
            }
            .btn-primary {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 8px 16px;
                border-radius: 4px;
                cursor: pointer;
                transition: background 0.3s;
            }
            .btn-primary:hover {
                background-color: #0056b3;
            }
            .pagination a {
                margin: 0 5px;
                padding: 8px 12px;
                background-color: #007bff;
                color: white;
                text-decoration: none;
                border-radius: 4px;
                transition: background 0.3s;
            }
            .pagination a:hover {
                background-color: #0056b3;
            }
            .pagination a.active {
                background-color: #0056b3;
                font-weight: bold;
            }
            .invoice-card {
                border: 1px solid #ccc; 
                border-radius: 12px;
                overflow: hidden;
                background-color: #fff;
                box-shadow: 0 4px 10px rgba(0,0,0,0.1);
                transition: transform 0.2s ease, box-shadow 0.2s ease;
            }
            .invoice-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 8px 20px rgba(0,0,0,0.15);
            }
            .invoice-card .card-img-top {
                width: 100%;
                height: 180px;
                object-fit: cover;
            }

            .invoice-card-title {
                color: #0277bd;
                font-weight: 600;
                margin-bottom: 10px;
            }
            .invoice-card-text {
                font-size: 0.9rem;
                color: #555;
            }

            .invoice-card-btn {
                background-color: #29b6f6;
                border: none;
                color: #fff;
                padding: 10px;
                border-radius: 8px;
                transition: background-color 0.3s ease;
            }
            .invoice-card-btn:hover {
                background-color: #0288d1;
            }

            .invoice-table {
                background-color: #fff;
                border-radius: 8px;
                overflow: hidden;
                border: 1px solid #ccc;  
            }
            .invoice-table thead {
                background-color: #0288d1;
                color: #fff;
                text-align: center;
            }
            .invoice-table th, .invoice-table td {
                padding: 12px;
                text-align: center;
                border: 1px solid #ccc; 
            }

            .invoice-table-btn {
                background-color: #29b6f6;
                border: none;
                color: #fff;
                padding: 8px 12px;
                border-radius: 8px;
                transition: background-color 0.3s ease;
            }
            .invoice-table-btn:hover {
                background-color: #0288d1;
            }

            .card-payment {
                border: 1px solid #ccc;
                border-radius: 12px;
                background-color: #fff;
                box-shadow: 0 4px 10px rgba(0,0,0,0.1);
                transition: transform 0.2s ease;
            }
            .card-payment:hover {
                transform: scale(1.02);
            }

            .badge-status {
                padding: 6px 12px;
                font-size: 0.9rem;
                font-weight: bold;
                border-radius: 20px;
            }
            .bg-success {
                background-color: #4caf50 !important;
            }
            .bg-secondary {
                background-color: #b0bec5 !important;
            }
            .btn-back {
                background-color: #29b6f6;  
                color: #fff;
                border: 2px solid transparent;
                padding: 8px 16px;
                font-size: 1rem;
                font-weight: 600;
                border-radius: 8px;
                cursor: pointer;
                transition: background-color 0.3s ease, border-color 0.3s ease;
            }

            .btn-back:hover {
                background-color: #0288d1; 
                border-color: #0288d1;
            }


            
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                <div class="container ">
                    <c:if test="${not empty info}">
                        <div class="mb-3 text-start">
                            <form action="InvoiceController" method="get" class="d-inline">
                                <button type="submit" class="btn-back">Back</button>
                            </form>
                        </div>

                        <div class="row g-4">
                            <c:forEach var="item" items="${info}">
                                <div class="col-md-3">
                                    <div class="card invoice-card shadow-sm">
                                        <img src="${item.getImage()}" class="card-img-top" alt="${item.getName()}">
                                        <div class="card-body">
                                            <h5 class="card-title invoice-card-title">${item.getName()}</h5>
                                            <p class="card-text invoice-card-text mb-1">${item.getGender()}</p>
                                            <p class="card-text invoice-card-text mb-1">${item.getAddress()}</p>
                                            <form action="InvoiceController" method="post">
                                                <input type="hidden" name="action" value="Allinfor">
                                                <input type="hidden" name="cusID" value="${cusID}">
                                                <input type="hidden" name="staffID" value="${item.getCreatedBy()}">
                                                <button type="submit" class="btn btn-primary w-100 invoice-card-btn">See all</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:if>

                    <c:if test="${not empty allinfo}">
                        <div class="mb-3 text-start">
                            <form action="InvoiceController" method="post" class="d-inline">
                                <input type="hidden" name="action" value="Infor">
                                <input type="hidden" name="cusID" value="${cusID}">
                                <button type="submit" class="btn-back">Back</button>
                            </form>
                        </div>
                        <div class="row g-4 mt-4">
                            <div class="col-md-3">
                                <c:forEach var="item" items="${info1}">
                                    <div class="card invoice-card shadow-sm mb-4">
                                        <img src="${item.getImage()}" class="card-img-top" alt="${item.getName()}">
                                        <div class="card-body">
                                            <h5 class="card-title invoice-card-title">${item.getName()}</h5>
                                            <p class="card-text invoice-card-text">${item.getGender()}</p>
                                            <p class="card-text invoice-card-text">${item.getAddress()}</p>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="col-md-9">
                                <table class="table table-striped table-bordered invoice-table">
                                    <thead>
                                        <tr>
                                            <th>Invoice Number</th>
                                            <th>Invoice Type</th>
                                            <th>Amount</th>
                                            <th>Status</th>
                                            <th class="text-center">Details</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="wa" items="${allinfo}">
                                            <tr>
                                                <td>${wa.getName()}</td>
                                                <td>${wa.getImage()}</td>
                                                <td>${wa.getGender()}</td>
                                                <td>${wa.getAddress()}</td>
                                                <c:if test="${wa.getAddress() eq 'paid'}">
                                                    <td class="text-center">
                                                        <form action="InvoiceController" method="post">
                                                            <input type="hidden" name="action" value="Allinforpayment">
                                                            <input type="hidden" name="cusID" value="${cusID}">
                                                            <input type="hidden" name="staffID" value="${wa.getStaffID()}">
                                                            <input type="hidden" name="invoiceID" value="${wa.getCreatedBy()}">
                                                            <button type="submit" class="btn btn-primary invoice-table-btn">Chi tiết</button>
                                                        </form>
                                                    </td>
                                                </c:if>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <c:if test="${not empty payment}">
                            <div class="row">
                                <c:forEach var="wa" items="${payment}">
                                    <div class="col-md-6 col-lg-4 mb-4">
                                        <div class="card card-payment shadow-sm h-100">
                                            <div class="card-header d-flex justify-content-between align-items-center">
                                                <h5 class="mb-0">Invoice #${wa.invoiceNumber}</h5>
                                                <span class="badge badge-status 
                                                    <c:choose>
                                                        <c:when test="${wa.status eq 'paid'}">bg-success</c:when>
                                                        <c:otherwise>bg-secondary</c:otherwise>
                                                    </c:choose>
                                                ">
                                                    ${wa.status}
                                                </span>
                                            </div>
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between mb-2">
                                                    <strong>Amount:</strong>
                                                    <span>${wa.amount}</span>
                                                </div>
                                                <div class="d-flex justify-content-between mb-2">
                                                    <strong>Invoice Type:</strong>
                                                    <span>${wa.invoiceType}</span>
                                                </div>
                                                <div class="d-flex justify-content-between mb-2">
                                                    <strong>Invoice ID:</strong>
                                                    <span>${wa.invoiceID}</span>
                                                </div>
                                                <div class="d-flex justify-content-between mb-2">
                                                    <strong>Issue:</strong>
                                                    <span>${wa.issue}</span>
                                                </div>
                                                <div class="d-flex justify-content-between mb-2">
                                                    <strong>Staff Name:</strong>
                                                    <span>${wa.staffName}</span>
                                                </div>
                                                <div class="d-flex justify-content-between mb-2">
                                                    <strong>Staff Email:</strong>
                                                    <span>${wa.staffEmail}</span>
                                                </div>
                                                <div class="d-flex justify-content-between mb-2">
                                                    <strong>Staff Phone:</strong>
                                                    <span>${wa.staffPhone}</span>
                                                </div>
                                                <div class="d-flex justify-content-between mb-2">
                                                    <strong>Payment Date:</strong>
                                                    <span>${wa.paymentDate}</span>
                                                </div>
                                                <div class="d-flex justify-content-between mb-2">
                                                    <strong>Payment Method:</strong>
                                                    <span>${wa.paymentMethod}</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>
                    </c:if>
                </div>
            </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
