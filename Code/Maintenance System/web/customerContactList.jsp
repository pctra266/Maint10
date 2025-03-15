<%-- 
    Document   : customerContactList
    Created on : Mar 16, 2025, 1:42:13 AM
    Author     : Tra Pham
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Contact List</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">

                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th>Contact ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Message</th>
                                <th>Date Sent</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${contactList}" var="contact">
                                <tr>
                                    <td>${contact.contactID}</td>
                                    <td>${contact.name}</td>
                                    <td>${contact.email}</td>
                                    <td>${contact.phone}</td>
                                    <td>${contact.message}</td>
                                    <td>${contact.createdAt}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
        <script src="js/format-input.js"></script>
    </body>
</html>

