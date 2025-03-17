<%-- 
    Document   : addServiceItem
    Created on : Mar 16, 2025, 3:20:15 PM
    Author     : Tra Pham
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Service Item</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <h1>Add New Service Item</h1>
    <form action="MarketingServiceItemController" method="post">
        <!-- Đặt action là "new" -->
        <input type="hidden" name="action" value="new">
        <!-- Giả sử sectionID luôn là 1 -->
        <input type="hidden" name="sectionID" value="1">
        
        <div>
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" placeholder="Enter title" required>
        </div>
        <div>
            <label for="description">Description:</label>
            <textarea id="description" name="description" placeholder="Enter description" required></textarea>
        </div>
        <div>
            <label for="imageURL">Image URL:</label>
            <input type="text" id="imageURL" name="imageURL" placeholder="Enter image URL" required>
        </div>
        <div>
            <label for="sortOrder">Sort Order:</label>
            <input type="number" id="sortOrder" name="sortOrder" placeholder="Enter sort order" value="1" required>
        </div>
        <button type="submit">Add Service Item</button>
    </form>
    <br>
    <a href="customizeHomepage.jsp">Back to Customize HomePage</a>
</body>
</html>

