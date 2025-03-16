<%-- 
    Document   : customizeHomepage
    Created on : Mar 15, 2025, 9:09:48 AM
    Author     : Tra Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="coverDAO" class="DAO.HomePage_CoverDAO" scope="page" />

<c:set var="backgroundImage" value="${coverDAO.getBackgroundImage()}" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Customize HomePage</title>
    </head>
    <body>
        <h1>Customize HomePage</h1>
        
        <!-- Customize Cover Image -->
        <h2>Customize Cover Image</h2>
        <c:if test="${not empty messBackground}">
            <p style="color: green;">${messBackground}</p>
        </c:if>
        <div>
            <h3>Current Cover Image:</h3>
            <img src="${pageContext.request.contextPath}${backgroundImage}" alt="Cover Image" width="400px">
        </div>

        <form action="updateCover" method="post" enctype="multipart/form-data">
            <label for="coverImage">Select New Cover Image:</label>
            <input type="file" name="coverImage" id="coverImage" accept="image/*" required>
            <button type="submit">Upload</button>
        </form>

        <hr>

        <!-- Customize Footer -->
        <h2>Customize Footer</h2>
        <c:if test="${not empty messFooter}">
            <p style="color: green;">${messFooter}</p>
        </c:if>
        <form action="FooterController" method="post">
            <label>Slogan:</label>
            <input type="text" name="slogan" value="${footer.slogan}" required><br>

            <label>Address:</label>
            <input type="text" name="address" value="${footer.address}" required><br>

            <label>Hotline:</label>
            <input type="text" name="hotline" value="${footer.hotline}" required><br>

            <label>Email:</label>
            <input type="email" name="email" value="${footer.email}" required><br>

            <label>Copyright Year:</label>
            <input type="text" name="copyrightYear" value="${footer.copyrightYear}" required><br>

            <button type="submit">Update</button>
        </form>

        <hr>

        <!-- Customize Warranty Consultation Text -->
        <h2>Customize Warranty Consultation Text</h2>
        <c:if test="${not empty messContactText}">
            <p style="color: green;">${messContactText}</p>
        </c:if>
        <form action="ContactController" method="post">
            <label for="title">H1 Title:</label>
            <input type="text" name="title" value="${contactText.title}" required><br>

            <label for="subtitle">H6 Subtitle:</label>
            <textarea name="subtitle" required>${contactText.subtitle}</textarea><br>

            <button type="submit">Update</button>
        </form>
    </body>
</html>

