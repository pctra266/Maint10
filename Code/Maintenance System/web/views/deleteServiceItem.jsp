<%-- 
    Document   : deleteServiceItem
    Created on : Mar 16, 2025, 3:22:06 PM
    Author     : Tra Pham
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Delete Service Item</title>
        <link rel="stylesheet" href="css/style.css">
        <link rel="shortcut icon" href="img/icons/icon-48x48.png" />

    </head>
    <body>
        <h1>Delete Service Item</h1>
        <p>Are you sure you want to delete the following service item?</p>
        <table border="1" cellpadding="5">
            <tr>
                <th>Service ID</th>
                <td>${item.serviceID}</td>
            </tr>
            <tr>
                <th>Title</th>
                <td>${item.title}</td>
            </tr>
            <tr>
                <th>Description</th>
                <td>${item.description}</td>
            </tr>
            <tr>
                <th>Image URL</th>
                <td>${item.imageURL}</td>
            </tr>
            <tr>
                <th>Sort Order</th>
                <td>${item.sortOrder}</td>
            </tr>
        </table>
        <br>
        <form action="MarketingServiceItemController" method="post">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="serviceID" value="${item.serviceID}">
            <button type="submit">Confirm Delete</button>
        </form>
        <br>
        <a href="customizeHomepage.jsp">Cancel</a>
    </body>
</html>

