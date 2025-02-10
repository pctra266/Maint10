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
</style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center ">Staff List</h1>
                    <form class="" action="StaffController" method="get">
                        <div class="col-md-6" style="width: 500px">
                            <input class="form-control" type="searchname" name="searchname" placeholder="Search"  value="${param.searchname}" style="margin-bottom:5px">
                            <select
                            id="search"
                            type="search"
                            class="add__input"
                            oninput="checkInput(this)"
                            name="search"
                            required
                            >
                            <option></option>
                            <option value="Name" ${param.search == 'Name' ? 'selected' : ''}>Name</option>
                            <option value="Role"${param.search == 'Role' ? 'selected' : ''}>Role</option>
                            </select>
                            <button class="btn btn-primary" style="margin: 2px; color: white;background-color: #007bff" type="submit">Search</button>        
                                                                                                               
                        </div> 
                        <div class="col-md-6" style="width: 500px">
                            <div>
                                <select onchange="this.form.submit;()" name="column" id="column" class="form-select">
                                    <option value="">Sort By</option>
                                    <option ${(column=='Name')?"selected":""} value="Name">Name</option>
                                    <option ${(column=='Role')?"selected":""} value="Role">Role</option>
                                </select>
                            </div>
                            <div>
                                    <select onchange="this.form.submit()" name="sortOrder" id="sortOrder" style="margin-top: 15px" class="form-select">
                                    <option value="">Sort Order</option>
                                    <option ${(sortOrder=='asc')?"selected":""} value="asc">Ascending</option>
                                    <option ${(sortOrder=='desc')?"selected":""} value="desc" >Descending</option>
                                </select>
                            </div>
                        </div>
                                <label style="margin: 5px">Show 
                                <select name="page_size" class="form-select form-select-sm d-inline-block" style="width: auto; " onchange="this.form.submit()">
                                    <option value="5" ${page_size==5?"selected":""}>5</option>
                                    <option value="7" ${page_size==7?"selected":""}>7</option>
                                    <option value="10" ${page_size==10?"selected":""}>10</option>
                                    <option value="15" ${page_size==15?"selected":""}>15</option>
                                </select> 
                                entries
                            </label>
                    </form>  
                            
                    <table class="table table-hover my-0">
                        <div class="row mb-3">
                        <div class="col-md-6 d-flex justify-content-end">
                            <a href="add-staff.jsp" class="btn btn-success btn-lg d-flex align-items-center gap-2 shadow">
                                <i class="fas fa-user-plus"></i> Add More
                            </a>
                        </div>
                        <div class="col-md-6 d-flex justify-content-start">
                            <form action="seeMoreController" method="post">
                                <button type="submit" class="btn btn-dark btn-lg d-flex align-items-center gap-2 shadow">
                                    <i class="fas fa-history"></i> History
                                </button>
                            </form>
                        </div>
                    </div>

                        
                        <c:if test="${not empty message}">
                            <div class="alert">${message}</div>
                        </c:if>
                            
                        <thead>
                            <tr>
                                <th>ID #</th>
                                <th>Name</th>
                                <th>Image</th>
                                <th>Role</th>
                                <th>Email</th>
                                <th>Change</th>
                                <th>Delete</th>
                            </tr>   
                        </thead>
                        <tbody>
                            <c:forEach var="List" items="${list}">
                                <tr>
                                    <td># ${List.getStaffID()}</td>
                                    <td>${List.getName()}</td>
                                    <td>
                                         <img src="${List.getImgage()}" alt="Null" width="100" height="100">
                                         
                                    </td>                                                                         
                                    <td>${List.getRole()}</td>
                                    <td>${List.getEmail()}</td>
                                    <td>
                                        <a href="./StaffController?staffID=${List.getStaffID()}&action=Update" 
                                           class="btn btn-success btn-sm text-white shadow">
                                            <i class="fas fa-edit"></i> Change
                                        </a>
                                    </td>
                                    <td>
                                        <a href="./StaffController?staffID=${List.getStaffID()}&action=Delete" 
                                           class="btn btn-danger btn-sm text-white shadow" 
                                           onclick="return confirm('Are you sure you want to delete this staff member?');">
                                            <i class="fas fa-trash-alt"></i> Delete
                                        </a>
                                    </td>


                                </tr>
                                    </c:forEach>

                        </tbody>
                    </table>
                    <div style="text-align: center; margin-top: 20px;">
                        <c:forEach begin="1" end="${totalPageCount}" var="index">
                            <a href="StaffController?index=${index}&searchname=${param.searchname}&search=${param.search}&column=${param.column}&sortOrder=${param.sortOrder}&page_size=${param.page_size}" style="margin: 0 5px;">${index}</a>
                        </c:forEach>
                    </div>           
                     
                
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>


        <script src="js/app.js"></script>

    </body>
</html>