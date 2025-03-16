<%-- 
    Document   : packageWarrantyList
    Created on : Mar 16, 2025, 9:16:56 PM
    Author     : Tra Pham
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Package Warranty List</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Package Warranty List</h1>
    <c:if test="${not empty message}">
        <div class="alert alert-info">${message}</div>
    </c:if>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Product Code</th>
                <th>Customer Name</th>
                <th>Email</th>
                <th>Product Name</th>
                <th>Warranty Start</th>
                <th>Warranty End</th>
                <th>Note</th>
                <th>Is Active</th>
                <th>Extended Warranty Name</th>
                <th>Start Extended</th>
                <th>End Extended</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="pkg" items="${packageWarranties}">
                <tr>
                    <td>${pkg.productCode}</td>
                    <td>${pkg.customerName}</td>
                    <td>${pkg.email}</td>
                    <td>${pkg.productName}</td>
                    <td>${pkg.warrantyStartDate}</td>
                    <td>${pkg.warrantyEndDate}</td>
                    <td>${pkg.note}</td>
                    <td>
                        <c:choose>
                            <c:when test="${pkg.active}">Yes</c:when>
                            <c:otherwise>No</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${pkg.extendedWarrantyName}</td>
                    <td>${pkg.startExtendedWarranty}</td>
                    <td>${pkg.endExtendedWarranty}</td>
                    <td>
                        <a href="PackageWarrantyController?action=edit&amp;packageWarrantyID=${pkg.packageWarrantyID}" class="btn btn-primary btn-sm">Edit</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>

