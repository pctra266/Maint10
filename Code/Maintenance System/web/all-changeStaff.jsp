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
                    <h1 class="text-center ">History of staff</h1>                   
                        <h2>All staff</h2>                          
                    <table class="table table-hover my-0">                       
                        <thead>
                            <tr>
                                <th>ID #</th>
                                <th>UseNameS</th>
                                <th>PasswordS</th>
                                <th>Role</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Address</th>
                                <th>Imgage</th>
                                <th>time</th>
                                <th>Status</th>
                                
                            </tr>   
                        </thead>
                        <tbody>
                            <c:forEach var="List" items="${list}">
                                <tr>
                                    <td># ${List.getStaffId()}</td>
                                    <td>${List.getUseNameS()}</td>
                                    <td>${List.getPasswordS()}</td>
                                    <td>${List.getRole()}</td>
                                    <td>${List.getName()}</td>
                                    <td>${List.getEmail()}</td>
                                    <td>${List.getPhone()}</td>
                                    <td>${List.getAddress()}</td>
                                    <td>${List.getImgage()}</td>
                                    <td>${List.getTime()}</td>
                                    <td>
                                        <span class="status confirmed">
                                            <i class="fas fa-circle"> ${List.getStatus()}</i>
                                        </span>
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