<%-- 
    Document   : updateProduct1
    Created on : Feb 6, 2025, 5:07:44 AM
    Author     : sonNH
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="post" action="updateproduct">
            <input type="hidden" name="pid" value="${product.productId}">
            <input type="hidden" name="status" value="${product.status}">
            <label for="productName">Product Name:</label>
            <input type="text" id="productName" name="productName" value="${product.productName}" required>

            <label for="productCode">Product Code:</label>
            <input type="text" id="productCode" name="productCode" value="${product.code}" required>



            <label for="brandId">Brand:</label>
            <select name="brandId" id="brandId" required>
                <c:forEach var="brand" items="${listBrand}">
                    <option value="${brand.brandId}" ${product.brandId == brand.brandId ? 'selected' : ''}>
                        ${brand.brandName}
                    </option>
                </c:forEach>
            </select>

            <label for="type">Type:</label>
            <select name="type" id="type" required>
                <c:forEach var="t" items="${listType}">
                    <option value="${t}" ${product.type eq t ? 'selected' : ''}>
                        ${t}
                    </option>
                </c:forEach>
            </select>


            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" value="${product.quantity}" required>

            <label for="warrantyPeriod">Warranty Period (Months):</label>
            <input type="number" id="warrantyPeriod" name="warrantyPeriod" value="${product.warrantyPeriod}" required>


            <input type="text" id="image" name="image" required>

            <button type="submit">Update Product</button>

            <form action="viewProduct">
                <button class="add-product" type="submit">Back</button>
            </form>
        </form>
    </body>
</html>
