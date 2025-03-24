<%-- 
    Document   : Invoice
    Created on : Mar 23, 2025, 12:27:36 PM
    Author     : ADMIN
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
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
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <div class="container">
                       <form class="row align-items-center mb-4" action="InvoiceController" method="get">
                            <!-- Search Input -->
                            <div class="col-md-8">
                              <div class="input-group">
                                <span class="input-group-text" id="search-icon"><i class="bi bi-search"></i></span>
                                <input type="text" class="form-control" name="search" placeholder="Tìm kiếm hóa đơn..." aria-label="Search Invoices" aria-describedby="search-icon" value="${param.search}">
                              </div>
                            </div>
                            <!-- Search Button -->
                            <div class="col-md-4">
                              <button type="submit" class="btn btn-primary w-100">Tìm kiếm</button>
                            </div>
                          </form>
                        
                            <div class="row">                               
                                <c:forEach var="List" items="${list}">
                                    <div class="col-md-4 mb-4">
                                        <div class="card h-100 shadow-sm">
                                            <!-- Hình ảnh đại diện -->
                                            <img class="card-img-top" src="${List.getImage()}">
                                            <div class="card-body">
                                                <h5 class="card-title">${List.getName()}</h5>
                                                <div class="row align-items-center">
                                                    <!-- Nội dung 2 p bên trái -->
                                                    <div class="col">
                                                        <p class="card-text mb-1">${List.getGender()}</p>
                                                        <p class="card-text mb-1">${List.getAddress()}</p>
                                                    </div>
                                                    <!-- Nút button bên phải -->
                                                    <div class="col-auto">
                                                        <form class="row align-items-center mb-4" action="InvoiceController" method="post">
                                                            <input type="hidden" name="action" value="Infor">
                                                            <input type="hidden" name="cusID" value="${List.getCreatedBy()}">
                                                            <button type="submit" class="btn btn-primary">See details</button>
                                                        </form>                                                   
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                    </div>
                    
                    <!-- Phân trang -->
                    <jsp:include page="/includes/pagination.jsp" />           
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
        <!-- Bootstrap Bundle JS (nếu cần) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
