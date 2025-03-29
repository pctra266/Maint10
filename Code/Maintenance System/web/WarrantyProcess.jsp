<%-- 
    Document   : Staff
    Created on : Jan 17, 2025, 8:39:04 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            /* Tổng thể form */
            .form-container {
                background: #fff;
                padding: 20px;
                border-radius: 12px;
                box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
                margin: 15px 0;
            }

            /* Input và Select */
            .input-group, .form-select {
                transition: all 0.3s ease-in-out;
                border-radius: 8px;
            }

            .form-select:focus, 
            .form-control:focus {
                box-shadow: 0 0 8px rgba(0, 123, 255, 0.5);
                border-color: #007bff;
            }

            /* Nút Search */
            button.btn-primary {
                background-color: #007bff;
                border-color: #007bff;
                transition: all 0.3s ease;
                border-radius: 8px;
            }

            button.btn-primary:hover {
                background-color: #0056b3;
                border-color: #004085;
                transform: scale(1.05);
            }

            /* Chỉnh khoảng cách giữa các phần tử */
            .me-2 {
                margin-right: 10px;
            }

            .ms-2 {
                margin-left: 10px;
            }

            .shadow-sm {
                box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1);
            }
            .upload-box { 
                border: 1px dashed #007bff;
                padding: 10px 5px;
                margin: 15px;
                display: inline-block; 
                background: white; width: 100px;
                border-radius: 5px;
                cursor: pointer;
            }
            .upload-box:hover 
            { 
                color: #007bff;
                background: #e9f5ff;
            }
            .upload-box p { margin: 0; color: #007bff; font-weight: bold; }
            input[type="file"] { display: none; }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
            <main class="content">
                <div class="container">
                <!-- Search Dropdown -->
                <div class="search-section">
                    <form id="searchForm" action="WarrantyCardProcessController" method="POST">
                        <input type="hidden" name="action" value="Search">
                        <select class="form-select w-25" name="warrantyCardID" id="warrantySelect" onchange="this.form.submit()">
                            <option  >Select WarrantyCardID</option>
                            <c:forEach var="id" items="${warrantyID}">
                                <option  value="${id}">${id}</option>
                            </c:forEach>
                        </select>
                    </form>
                </div>


                <!-- Table Section -->
                <c:if test="${not empty list1}">
                    <div class="table-section">
                        <div class="table-responsive border">
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>Create</th>
                                        <th>Receive</th>
                                        <th>Fixing</th>
                                        <th>Fixed</th>
                                        <th>Completed</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        
                                        <td>
                                            <c:forEach var="item" items="${list1}">
                                                <c:if test="${item.getAction() eq 'create'}"><div>${item.getFormattedDate2()}</div></c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:forEach var="item" items="${list1}">
                                                <c:if test="${item.getAction() eq 'receive'}"><div>${item.getFormattedDate2()}</div></c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:forEach var="item" items="${list1}">
                                                <c:if test="${item.getAction() eq 'fixing'}"><div>${item.getFormattedDate2()}</div></c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:forEach var="item" items="${list1}">
                                                <c:if test="${item.getAction() eq 'fixed'}"><div>${item.getFormattedDate2()}</div></c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:forEach var="item" items="${list1}">
                                                <c:if test="${item.getAction() eq 'completed'}"><div>${item.getFormattedDate2()}</div></c:if>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </tbody>


                            </table>
                        </div>
                    </div>
                </c:if>

                <form class="row g-3 align-items-center form-container" action="WarrantyCardProcessController" method="get">
                        <div class="col-md-3">
                            <select onchange="this.form.submit()" name="column" id="column" class="form-select shadow-sm">
                                <option value="">Sort By</option>
                                <option value="WarrantyCardID" ${column=='WarrantyCardID' ? 'selected' : ''}>WarrantyCardID</option>
                                <option value="ActionDate" ${column=='ActionDate' ? 'selected' : ''}>ActionDate</option>
                            </select>
                        </div>

                        <div class="col-md-3">
                            <select onchange="this.form.submit()" name="sortOrder" id="sortOrder" class="form-select shadow-sm">
                                <option value="">Sort Order</option>
                                <option value="asc" ${sortOrder=='asc' ? 'selected' : ''}>Ascending</option>
                                <option value="desc" ${sortOrder=='desc' ? 'selected' : ''}>Descending</option>
                            </select>
                        </div>

                        <!-- Page Size -->
                        <div class="col-auto d-flex align-items-center">
                            <label class="form-label fw-bold me-2">Show</label>
                            <select name="page_size" class="form-select form-select-sm shadow-sm" onchange="this.form.submit()">
                                <option value="5" ${page_size==5 ? "selected" : ""}>5</option>
                                <option value="7" ${page_size==7 ? "selected" : ""}>7</option>
                                <option value="10" ${page_size==10 ? "selected" : ""}>10</option>
                                <option value="15" ${page_size==15 ? "selected" : ""}>15</option>
                            </select>
                            <label class="form-label ms-2">entries</label>
                        </div>
                    </form>  

                    <table class="table table-hover my-0">
                        
                               
                        <thead>
                            <tr>
                                <th>WarrantyCardID</th>
                                <th>StaffID</th>
                                <th>Action</th>
                                <th>ActionDate</th>
                                
                            </tr>   
                        </thead>
                        <tbody>
                            <c:forEach var="List" items="${list}">
                                <tr>
                                    <td># ${List.getWarrantyCardID()}</td>
                                    <td>${List.getHandlerID()}</td>                                                                                                            
                                    <td>${List.getAction()}</td>
                                    <td>${List.getFormattedDate()}</td>                                 
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>        
            </div>

                    <jsp:include page="/includes/pagination.jsp" />           
                     
                
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>


        <script src="js/app.js"></script>

    </body>
</html>