<%-- 
    Document   : extendedWarrantyList
    Created on : Mar 16, 2025, 8:56:45 PM
    Author     : Tra Pham
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Extended Warranty List</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Extended Warranty List</h1>
    <c:if test="${not empty message}">
        <div class="alert alert-info">${message}</div>
    </c:if>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>ID</th>
                <th>Warranty Name</th>
                <th>Period (Months)</th>
                <th>Price</th>
                <th>Description</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="ew" items="${extendedWarranties}">
                <tr>
                    <td>${ew.extendedWarrantyID}</td>
                    <td>${ew.extendedWarrantyName}</td>
                    <td>${ew.extendedPeriodInMonths}</td>
                    <td>${ew.price}</td>
                    <td>${ew.extendedWarrantyDescription}</td>
                    <td>
                        <a href="ExtendedWarrantyController?action=edit&amp;extendedWarrantyID=${ew.extendedWarrantyID}" class="btn btn-sm btn-primary">Edit</a>
                        <a href="ExtendedWarrantyController?action=delete&amp;extendedWarrantyID=${ew.extendedWarrantyID}" class="btn btn-sm btn-danger"
                           onclick="return confirm('Are you sure to delete this extended warranty?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="ExtendedWarrantyController?action=new" class="btn btn-success">Add New Extended Warranty</a>
</div>
</body>
</html>

