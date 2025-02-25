<%-- 
    Document   : viewUnknownProduct
    Created on : Feb 25, 2025, 7:52:38 AM
    Author     : sonNH
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Unknown Products List</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
    </head>
    <body>
        <h2>List of Unknown Products</h2>

        <button onclick="location.href = 'AddUnknownProductServlet'">Add Unknown Product</button>

        <table border="1">
            <thead>
                <tr>
                    <th>Product Code</th>
                    <th>Product Name</th>
                    <th>Description</th>
                    <th>Received Date</th>
                    <th>Customer Name</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${listUnknownProduct}">
                    <tr>
                        <td>${product.productCode}</td>
                        <td>${product.productName}</td>
                        <td>${product.description}</td>
                        <td>${product.receivedDate}</td>
                        <td>${product.customerName}</td>
                        <td>
                            <form action="AddWarrantyRequestServlet" method="post">
                                <input type="hidden" name="productId" value="${product.unknowProductId}">
                                <input type="hidden" name="customerId" value="${product.customerId}">
                                <button type="submit">Create a Repair Request</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>

