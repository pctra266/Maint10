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
                    <h1 class="text-center ">Staff List</h1>
                    <form class="row g-3 align-items-center form-container" action="StaffController" method="get">
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
                        <div class="row mb-3">
                            <div class="col-md-6 d-flex justify-content-end">
                                <a href="add-staff.jsp" class="btn btn-success btn-lg d-flex align-items-center gap-2 shadow">
                                    <i class="fas fa-user-plus"></i> Add More
                                </a>
                            </div>
                            <div class="col-md-6 d-flex justify-content-start">
                                <form action="seeMoreController" method="get">
                                    <button type="submit" class="btn btn-dark btn-lg d-flex align-items-center gap-2 shadow">
                                        <i class="fas fa-history"></i> History
                                    </button>
                                </form>
                            </div>
                            <div class="col-md-6 d-flex justify-content-start">
                                <form action="reportStaffController" method="get">
                                    <button type="submit" class="btn btn-dark btn-lg d-flex align-items-center gap-2 shadow">
                                        <i class="fas fa-history"></i> Report
                                    </button>
                                </form>
                            </div>
                        </div>
                                                
                        <form action="ExportStaff" method="get" class="ms-1">
                            <input type="hidden" name="searchname" value="${param.searchname}">
                            <input type="hidden" name="search" value="${param.search}">
                            <input type="hidden" name="column" value="${param.column}">
                            <input type="hidden" name="sortOrder" value="${param.sortOrder}">
                            <button type="submit" class="btn btn-primary"><i class="fas fa-print"></i></button>
                        </form> 
                        
                        <form action="ImportStaff" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="action" value="Import">
                            <input type="hidden" name="searchname" value="${param.searchname}">
                            <input type="hidden" name="search" value="${param.search}">
                            <input type="hidden" name="column" value="${param.column}">
                            <input type="hidden" name="sortOrder" value="${param.sortOrder}">
                            <label class="upload-box">
                                <span id="fileLabel">Choose Excel</span>
                                <input type="file" name="file" accept=".xlsx" required onchange="document.getElementById('fileLabel').innerText = this.files[0].name">
                            </label>
                            <button type="submit" class="btn">Import</button>
                        </form>
                        
                        <c:if test="${not empty error}">
                            <div class="alert1">${error}</div>
                        </c:if>  
                        <c:if test="${not empty errorRow}">
                            <div class="alert1">${errorRow}</div>
                        </c:if>  
                        <c:if test="${not empty errorColum}">
                            <div class="alert1">${errorColum}</div>
                        </c:if>  
                            
                        <c:if test="${not empty message}">
                            <div class="alert">${message}</div>
                        </c:if>
                        <c:if test="${not empty message1}">
                            <div class="alert">${message1}</div>
                        </c:if>
                               
                        <thead>
                            <tr>
                                <th>ID #</th>
                                <th>Name</th>
                                <th>Image</th>
                                <th>Role</th>
                                <th>Gender</th>
                                <th>Date</th>
                                <th>Email</th>                               
                                <th>Change</th>
                                <c:if test="${not empty Delete}">
                                    <th>Delete</th>
                                </c:if>  
                                
                            </tr>   
                        </thead>
                        <tbody>
                            <p>Size of list: ${list.size()}</p>
                            <c:forEach var="List" items="${list}">
                                <tr>
                                    <td># ${List.getStaffID()}</td>
                                    <td>${List.getName()}</td>
                                    <td>
                                         <img src="${List.getImage()}" alt="Null" width="100" height="100">
                                    </td>                                                                         
                                    <td>${List.getRole()}</td>
                                    <td>${List.getGender()}</td>
                                    <td>
                                        ${List.getDate()}
                                    </td>
                                    <td>${List.getEmail()}</td>
                                    <td>
                                        <a href="./StaffController?staffID=${List.getStaffID()}&action=Update" 
                                           class="btn btn-success btn-sm text-white shadow">
                                            <i class="fas fa-edit"></i> Change
                                        </a>
                                    </td>
                                    
                                    <c:if test="${not empty Delete}">
                                        <td>
                                            <a href="./StaffController?staffID=${List.getStaffID()}&action=Delete" 
                                               class="btn btn-danger btn-sm text-white shadow" 
                                               onclick="return confirm('Are you sure you want to delete this staff member?');">
                                                <i class="fas fa-trash-alt"></i> Delete
                                            </a>
                                        </td>
                                    </c:if>  

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