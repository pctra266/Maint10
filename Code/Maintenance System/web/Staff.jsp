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
                    <form class="" action="searchStaff" method="post">
                        <div class="card-body" style="width: 500px">
                            <input class="form-control" type="searchname" name="searchname" placeholder="Search"  value="${StaffName}" >
                            <select
                            id="search"
                            type="search"
                            class="add__input"
                            oninput="checkInput(this)"
                            name="search"
                            required
                            >
                            <option value="option1"></option>
                            <option value="Name">Name</option>
                            <option value="Role">Role</option>
                            
                        </select>
                            <button class="btn btn-primary" style="margin-top: 15px" type="submit">Search</button>
                        </div>
                    </form>
                    <table class="table table-hover my-0">
                        <div class="detail-header">
                                <h2>All Orders</h2>
                                <a href="add-staff.jsp" style="text-decoration: none;color: white">
                                    <button type="submit">Add more</button>
                                </a>
                                <form action="seeMoreController" method="post">
                                    <button type="submit">See More</button>
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
                                    <td># ${List.getStaffId()}</td>
                                    <td>${List.getName()}</td>
                                    <td>
                                        <span class="status confirmed">
                                            <i class="fas fa-circle"> ON</i>
                                        </span>
                                    </td>
                                    <td>${List.getRole()}</td>
                                    <td>${List.getEmail()}</td>
                                    <td>
                                        <a href="./changeStaffController?staffId=${List.getStaffId()}">
                                            <button type="submit">Change </button>
                                        </a>
                                    </td>
                                    <td>
                                        <a href="./deletedStaffController?staffId=${List.getStaffId()}">
                                            <button type="submit">Delete </button>
                                        </a>
                                    </td>
                                </tr>
                                    </c:forEach>

                        </tbody>
                    </table>
                     
                    <a href="#!">History</a>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>


        <script src="js/app.js"></script>

    </body>
</html>