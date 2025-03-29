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
        <link rel="shortcut icon" href="img/icons/icon-48x48.png" />

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
    background: white;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease-in-out;
}

.invoice-card:hover {
    transform: scale(1.03);
}

.invoice-image {
    border-top-left-radius: 8px;
    border-top-right-radius: 8px;
    height: 200px;
    object-fit: cover;
}

.invoice-body {
    padding: 20px;
}

.invoice-title {
    font-size: 1.25rem;
    font-weight: 600;
    margin-bottom: 10px;
}

.invoice-text {
    color: #555;
    font-size: 0.9rem;
}

.invoice-form {
    margin-bottom: 0;
}
.invoice-search {
    display: flex;
    justify-content: center;
    align-items: center;
    background: #f8f9fa;
    padding: 15px;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.invoice-search-group {
    border-radius: 5px;
    overflow: hidden;
}

.invoice-search-icon {
    background-color: #007bff;
    color: white;
    border: none;
    font-size: 1.2rem;
}

.invoice-search-input {
    border-left: none;
    font-size: 1rem;
    padding: 10px;
}

.invoice-search-input:focus {
    box-shadow: none;
    border-color: #007bff;
}

.invoice-search-btn {
    background-color: #007bff;
    border: none;
    font-size: 1rem;
    padding: 10px;
    transition: background 0.3s;
}

.invoice-search-btn:hover {
    background-color: #0056b3;
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
                       <form class="invoice-search row align-items-center mb-4" action="InvoiceController" method="get">
                            <!-- Ô nhập tìm kiếm -->
                           <div class="col-md-8">
                               <div class="invoice-search-group input-group">
                                   <span class="input-group-text invoice-search-icon" id="search-icon">
                                       <i class="bi bi-search"></i>
                                   </span>
                                   <input type="text" class="form-control invoice-search-input" name="search"
                                       placeholder="Tìm kiếm hóa đơn..."
                                       aria-label="Search Invoices"
                                       aria-describedby="search-icon"
                                       value="${param.search}">
                               </div>
                           </div>
                           <!-- Nút tìm kiếm -->
                           <div class="col-md-4">
                               <button type="submit" class="btn btn-primary invoice-search-btn w-100">Tìm kiếm</button>
                           </div>
                       </form>

                        
                            <div class="row">                               
                                <c:forEach var="List" items="${list}">
                                    <div class="col-md-4 mb-4">
                                        <div class="invoice-card h-100 shadow-sm">
                                            <!-- Hình ảnh đại diện -->
                                            <img class="invoice-image card-img-top" src="${List.getImage()}">
                                            <div class="invoice-body card-body">
                                                <h5 class="invoice-title card-title">${List.getName()}</h5>
                                                <div class="row align-items-center">
                                                    <!-- Thông tin khách hàng -->
                                                    <div class="col">
                                                        <p class="invoice-text card-text mb-1">${List.getGender()}</p>
                                                        <p class="invoice-text card-text mb-1">${List.getAddress()}</p>
                                                    </div>
                                                    <!-- Nút xem chi tiết -->
                                                    <div class="col-auto">
                                                        <form class="invoice-form row align-items-center mb-4" action="InvoiceController" method="post">
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


                    <jsp:include page="/includes/pagination.jsp" />           
                     
                
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>


        <script src="js/app.js"></script>

    </body>
</html>
