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
        <title>Staff Profile</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
    </head>
    <body>
        <div class="profile-container">
            <h2>Staff Profile</h2>

            <c:choose>
                <c:when test="${not empty staff}">
                    <div class="profile-details">
                        <img src="${empty staff.image ? 'default.png' : staff.image}" alt="Profile Image" class="profile-image">
                        <p><strong>Username:</strong> ${staff.username}</p>
                        <p><strong>Name:</strong> ${staff.name}</p>
                        <p><strong>Email:</strong> ${staff.email}</p>
                        <p><strong>Phone:</strong> ${staff.phone}</p>
                        <p><strong>Address:</strong> ${staff.address}</p>
                        <p><strong>Role:</strong> ${staff.roleName}</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <p>No profile found. Please log in.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>

