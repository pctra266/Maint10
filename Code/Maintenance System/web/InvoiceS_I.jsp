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
        <!-- Nếu chưa có Bootstrap, bạn có thể thêm link sau để dùng grid và card -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
            /* Nếu muốn custom thêm style cho card */
            .card {
                border: none;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            }
            .card-img-top {
                border-top-left-radius: 8px;
                border-top-right-radius: 8px;
                object-fit: cover;
                height: 200px;
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
            /* Style cho nút Back */
            .btn-back {
                background-color: #6c757d;
                border: none;
                color: #fff;
                padding: 10px 20px;
                border-radius: 4px;
                text-decoration: none;
                transition: background-color 0.3s;
            }
            .btn-back:hover {
                background-color: #5a6268;
            }
        </style>
    </head>
<body>
    <div class="wrapper">
        <jsp:include page="/includes/navbar-left.jsp" />
        <div class="main">
            <jsp:include page="/includes/navbar-top.jsp" />
            <main class="content">
                <div class="container py-4">
                    <!-- Nếu có thông tin khách hàng -->
                    <c:if test="${not empty info}">
                        <!-- Nút Back căn về bên trái -->
                        <div class="mb-3 text-start">
                            <form action="InvoiceController" method="get" class="d-inline">
                                <button type="submit" class="btn-back">Back</button>
                            </form>
                        </div>
                        <div class="row g-4">
                            <c:forEach var="item" items="${info}">
                                <div class="col-md-3">
                                    <div class="card shadow-sm">
                                        <img src="${item.getImage()}" class="card-img-top" alt="${item.getName()}">
                                        <div class="card-body">
                                            <h5 class="card-title">${item.getName()}</h5>
                                            <p class="card-text mb-1">${item.getGender()}</p>
                                            <p class="card-text mb-1">${item.getAddress()}</p>
                                            <form action="InvoiceController" method="post">
                                                <input type="hidden" name="action" value="Allinfor">
                                                <input type="hidden" name="cusID" value="${cusID}">
                                                <input type="hidden" name="staffID" value="${item.getCreatedBy()}">
                                                <button type="submit" class="btn btn-primary w-100">See all</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:if>
                    
                    <!-- Nếu có thông tin hóa đơn chi tiết -->
                    <c:if test="${not empty allinfo}">
                        <!-- Nút Back căn về bên trái -->
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
                                    <div class="card shadow-sm mb-4">
                                        <img src="${item.getImage()}" class="card-img-top" alt="${item.getName()}">
                                        <div class="card-body">
                                            <h5 class="card-title">${item.getName()}</h5>
                                            <p class="card-text">${item.getGender()}</p>
                                            <p class="card-text">${item.getAddress()}</p>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="col-md-9">
                                <table class="table table-striped table-bordered">
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
                                                            <button type="submit" class="btn btn-primary">Chi tiết</button>
                                                        </form>
                                                    </td>
                                                
                                                </c:if>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!-- PHẦN HIỂN THỊ PAYMENT ĐẸP HƠN -->
<c:if test="${not empty payment}">
    <div class="row">
        <c:forEach var="wa" items="${payment}">
            <div class="col-md-6 col-lg-4 mb-4">
                <!-- Card hiển thị thông tin Payment -->
                <div class="card h-100 border-0 shadow-sm">
                    <!-- Header của card -->
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <!-- Invoice Number -->
                        <h5 class="mb-0">Invoice #${wa.invoiceNumber}</h5>
                        <!-- Trạng thái (status) dưới dạng badge -->
                        <span class="badge 
                            <c:choose>
                                <c:when test="${wa.status eq 'paid'}">bg-success</c:when>
                                <c:otherwise>bg-secondary</c:otherwise>
                            </c:choose>
                        ">
                            ${wa.status}
                        </span>
                    </div>
                    
                    <!-- Body của card -->
                    <div class="card-body">
                        <!-- Số tiền (Amount) -->
                        <div class="d-flex justify-content-between mb-2">
                            <strong>Amount:</strong>
                            <span>${wa.amount}</span>
                        </div>
                        <!-- Loại hóa đơn (Invoice Type) -->
                        <div class="d-flex justify-content-between mb-2">
                            <strong>Invoice Type:</strong>
                            <span>${wa.invoiceType}</span>
                        </div>
                        <!-- Invoice ID -->
                        <div class="d-flex justify-content-between mb-2">
                            <strong>Invoice ID:</strong>
                            <span>${wa.invoiceID}</span>
                        </div>
                        <!-- Issue -->
                        <div class="d-flex justify-content-between mb-2">
                            <strong>Issue:</strong>
                            <span>${wa.issue}</span>
                        </div>
                        <!-- Tên nhân viên (Staff Name) -->
                        <div class="d-flex justify-content-between mb-2">
                            <strong>Staff Name:</strong>
                            <span>${wa.staffName}</span>
                        </div>
                        <!-- Email nhân viên (Staff Email) -->
                        <div class="d-flex justify-content-between mb-2">
                            <strong>Staff Email:</strong>
                            <span>${wa.staffEmail}</span>
                        </div>
                        <!-- Số điện thoại nhân viên (Staff Phone) -->
                        <div class="d-flex justify-content-between mb-2">
                            <strong>Staff Phone:</strong>
                            <span>${wa.staffPhone}</span>
                        </div>
                        <!-- Ngày thanh toán (Payment Date) -->
                        <div class="d-flex justify-content-between mb-2">
                            <strong>Payment Date:</strong>
                            <span>${wa.paymentDate}</span>
                        </div>
                        <!-- Phương thức thanh toán (Payment Method) -->
                        <div class="d-flex justify-content-between mb-2">
                            <strong>Payment Method:</strong>
                            <span>${wa.paymentMethod}</span>
                        </div>
                    </div> <!-- End card-body -->
                </div> <!-- End card -->
            </div> <!-- End col -->
        </c:forEach>
    </div> <!-- End row -->
</c:if>

                    </c:if>
                        
                        
                </div>
            </main>
            <jsp:include page="/includes/footer.jsp" />
        </div>
    </div>
    <!-- Bootstrap JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/app.js"></script>
</body>
</html>
