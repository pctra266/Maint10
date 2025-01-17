<%-- 
    Document   : ViewListFeedback
    Created on : Jan 17, 2025, 7:09:31 PM
    Author     : Tra Pham
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Feedback List</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Customer ID</th>
                    <th>Warranty Card ID</th>
                    <th>Feedback</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
              
            <c:forEach items="${listFeedback}" var="o">
                <tr>
                    <td>${o.feedbackID}</td>
                    <td>${o.customerID}</td>
                    <td>${o.warrantyCardID}</td>
                    <td>${o.note}</td>
                    <td><a href="#">Delete</a></td>
                    <td><a href="#">Update</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </body>
</html>
