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

        
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center ">Staff List</h1>
                    <form class="" action="searchStaff" method="get">
                        <h2>All Orders</h2>
                        <div class="card-body" style="width: 500px">
                            <input class="form-control" type="searchname" name="searchname" placeholder="Search"  value="${param.searchname}" style="margin-bottom:5px">
                            <select
                            id="search"
                            type="search"
                            class="add__input"
                            oninput="checkInput(this)"
                            name="search"
                            required
                            
                            >
                            <option value="option1"></option>
                            <option value="Name" ${param.search == 'Name' ? 'selected' : ''}>Name</option>
                            <option value="Role"${param.search == 'Role' ? 'selected' : ''}>Role</option>
                            
                            </select>
                            <button class="btn btn-primary" style="margin: 2px; color: white;background-color: #007bff" type="submit">Search</button>        
                            <a href="./StaffController" style=" padding: 8px 16px; background-color: #007bff; color: white; text-decoration: none; border: none; border-radius: 4px; margin-left: 10px;">
                                All Staff                                
                            </a>                                                      
                        </div>
                            
                    </form>
                            
                    <table class="table table-hover my-0">
                        <div class="detail-header">
                                <a href="add-staff.jsp" style="text-decoration: none;color: white">
                                    <button type="submit" style="margin: 2px; color: white;background-color: #007bff">Add more</button>
                                </a>
                                <form action="seeMoreController" method="post">
                                    <button type="submit" style="margin: 2px; color: white;background-color: #007bff"">History</button>
                                </form>
                        </div>
                        <thead>
                            <tr>
                                <th>ID #</th>
                                <th>Name</th>
                                <th>Status</th>
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
                                        <span class="status confirmed">
                                            <i class="fas fa-circle"> ON</i>
                                        </span>
                                    </td>
                                    <td>${List.getRole()}</td>
                                    <td>${List.getEmail()}</td>
                                    <td>
                                        <a href="./changeStaffController?staffID=${List.getStaffID()}">
                                            <button type="submit">Change </button>
                                        </a>
                                    </td>
                                    <td>
                                        <a href="./deletedStaffController?staffID=${List.getStaffID()}">
                                            <button type="submit">Delete </button>
                                        </a>
                                    </td>
                                </tr>
                                    </c:forEach>

                        </tbody>
                    </table>
                    <div style="text-align: center; margin-top: 20px;">
                        <c:forEach begin="1" end="${totalPageCount}" var="index">
                            <a href="searchStaff?index=${index}&searchname=${param.searchname}&search=${param.search}" style="margin: 0 5px;">${index}</a>
                        </c:forEach>
                    </div>           
                     
                
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>


        <script src="js/app.js"></script>

    </body>
</html>