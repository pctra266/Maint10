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
            .status-badge {
                font-size: 14px;
                font-weight: 600;
                padding: 8px 12px;
                border-radius: 20px;
                display: inline-flex;
                align-items: center;
                text-transform: capitalize;
                transition: 0.3s ease-in-out;
                cursor: pointer;
            }

            .status-badge i {
                margin-right: 5px;
            }
            .status-badge:hover {
                transform: scale(1.1);
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
            }

        </style>
        
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center ">History of staff</h1>                   
                    <form class="row g-3 align-items-center form-container" action="seeMoreController" method="get">
                        <!-- Search Section -->
                        <div class="col-md-6">
                            <div class="input-group shadow-sm">
                                <input class="form-control" type="text" name="searchname" placeholder="🔍 Search..." value="${param.searchname}">
                                <select id="search" class="form-select" name="search" required>
                                    <option value="">-- Search By --</option>
                                    <option value="Name" ${param.search == 'Name' ? 'selected' : ''}>Name</option>
                                    <option value="Email" ${param.search == 'Email' ? 'selected' : ''}>Email</option>
                                </select>
                                <button class="btn btn-primary" type="submit">Search</button>
                            </div>
                        </div>

                        <!-- Sort Section -->
                        <div class="col-md-3">
                            <select onchange="this.form.submit()" name="column" id="column" class="form-select shadow-sm">
                                <option value="">Sort By</option>
                                <option value="Name" ${column=='Name' ? 'selected' : ''}>Name</option>
                                <option value="DateOfBirth" ${column=='DateOfBirth' ? 'selected' : ''}>Date</option>
                                <option value="Time" ${column=='Time' ? 'selected' : ''}>Time</option>
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
                            <select name="page_size" class="form-select form-select-sm shadow-sm" style="margin-bottom: 5px " onchange="this.form.submit()">
                                <option value="5" ${page_size==5 ? "selected" : ""}>5</option>
                                <option value="7" ${page_size==7 ? "selected" : ""}>7</option>
                                <option value="10" ${page_size==10 ? "selected" : ""}>10</option>
                                <option value="15" ${page_size==15 ? "selected" : ""}>15</option>
                            </select>
                            <label class="form-label ms-2">entries</label>
                        </div>
                    </form>  
                    
                    <form action="ExportStaffLog" method="get" class="ms-1">
                        <input type="hidden" name="searchname" value="${param.searchname}">
                        <input type="hidden" name="search" value="${param.search}">
                        <input type="hidden" name="column" value="${param.column}">
                        <input type="hidden" name="sortOrder" value="${param.sortOrder}">
                        <button type="submit" class="btn btn-primary"><i class="fas fa-print"></i></button>
                    </form> 
                    
                    <table class="table table-hover my-0">                       
                        <thead>
                            <tr>
                                <th>ID #</th>
                                <th>UseNameS</th>
                                <th>Role</th>
                                <th>Name</th>
                                <th>Gender</th>
                                <th>Date</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Address</th>
                                <th>Imgage</th>
                                <th>Time</th>
                                <th>Status</th>
                                
                            </tr>   
                        </thead>
                        <tbody>
                            <p>Size of list: ${list.size()}</p>

                            <c:forEach var="List" items="${list}">
                                <tr>
                                    <td># ${List.getStaffId()}</td>
                                    <td>${List.getUseNameS()}</td>
                                    <td>${List.getRole()}</td>
                                    <td>${List.getName()}</td>
                                    <td>${List.getGender()}</td>
                                    <td>${List.getDate()}</td>
                                    <td>${List.getEmail()}</td>
                                    <td>${List.getPhone()}</td>
                                    <td>${List.getAddress()}</td>
                                    <td>
                                        <img src="${List.getImgage()}" alt="Null" width="100" height="100">
                                    </td>
                                    <td>${List.getTime()}</td>
                                    <td>
                                        <span class="status-badge ${List.getStatus()}">
                                            <i class="${List.getStatus() == 'Delete' ? 'fas fa-times-circle' : 'fas fa-check-circle'}"></i>  
                                            ${List.getStatus()}
                                        </span>
                                    </td>

                                    
                                    
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>
                    <jsp:include page="/includes/pagination.jsp" />           
        
                     
                
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>


        <script src="js/app.js"></script>

    </body>
</html>