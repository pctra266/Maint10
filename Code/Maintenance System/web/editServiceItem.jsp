<%-- 
    Document   : editServiceItem
    Created on : Mar 16, 2025, 3:19:51 PM
    Author     : Tra Pham
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Service Item</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <h1>Edit Service Item</h1>
    <form action="MarketingServiceItemController" method="post">
        <!-- Đặt action là "edit" -->
        <input type="hidden" name="action" value="edit">
        <!-- Giả sử sectionID luôn là 1 -->
        <input type="hidden" name="sectionID" value="1">
        <!-- ServiceID cần thiết cho việc cập nhật -->
        <input type="hidden" name="serviceID" value="${item.serviceID}">
        
        <div>
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" value="${item.title}" required>
        </div>
        <div>
            <label for="description">Description:</label>
            <textarea id="description" name="description" required>${item.description}</textarea>
        </div>
        <div>
            <label for="imageURL">Image URL:</label>
            <input type="text" id="imageURL" name="imageURL" value="${item.imageURL}" required>
        </div>
        <div>
            <label for="sortOrder">Sort Order:</label>
            <input type="number" id="sortOrder" name="sortOrder" value="${item.sortOrder}" required>
        </div>
        <button type="submit">Update Service Item</button>
    </form>
    <br>
    <a href="customizeHomepage.jsp">Back to Customize HomePage</a>
</body>
</html>

