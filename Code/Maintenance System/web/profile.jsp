<%-- 
    Document   : profile
    Created on : Feb 19, 2025, 11:04:59 PM
    Author     : sonNH
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Customer Profile</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
    </head>
    <body>
        <div class="profile-container">
            <h2>Customer Profile</h2>

            <c:choose>
                <c:when test="${not empty customerProfile}">
                    <div class="profile-details">
                        <img src="${empty customerProfile.image ? 'default.png' : customerProfile.image}" alt="Profile Image" class="profile-image">
                        <p><strong>Username:</strong> ${customerProfile.usernameC}</p>
                        <p><strong>Name:</strong> ${customerProfile.name}</p>
                        <p><strong>Gender:</strong> ${customerProfile.gender}</p>
                        <p><strong>Email:</strong> ${customerProfile.email}</p>
                        <p><strong>Phone:</strong> ${customerProfile.phone}</p>
                        <p><strong>Address:</strong> ${customerProfile.address}</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <p>No profile found. Please log in.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
