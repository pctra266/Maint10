<%-- 
    Document   : uploadImage
    Created on : Feb 5, 2025, 4:31:05 PM
    Author     : Tra Pham
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Upload Image to Google Drive</title>
</head>
<body>
    <h1>Upload an image to Google Drive</h1>
    <form action="uploadImage" method="post" enctype="multipart/form-data">
        <label for="file">Choose file to upload:</label>
        <input type="file" name="file" id="file" required />
        <br />
        <button type="submit">Upload</button>
    </form>
</body>
</html>

