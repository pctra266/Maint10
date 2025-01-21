<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Product List</title>
        <style>
            table {
                width: 80%;
                margin: auto;
                border-collapse: collapse;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
        </style>
    </head>
    <body>
        <h1 style="text-align: center;">Product List</h1>

        <form action="product" method="get" style="text-align: center; margin-bottom: 20px;">
            <input 
                type="text" 
                name="keyword" 
                placeholder="Search by product name" 
                value="${param.keyword}" 
                style="width: 200px; padding: 8px;">

            <input 
                type="number" 
                name="quantity" 
                placeholder="Quantity" 
                value="${param.quantity}" 
                style="width: 120px; padding: 8px;">

            <input 
                type="number" 
                name="warrantyPeriod" 
                placeholder="Warranty (years)" 
                value="${param.warrantyPeriod}" 
                style="width: 120px; padding: 8px;">

            <button type="submit" style="padding: 8px 16px;">Search</button>

            <a href="product" style="padding: 8px 16px; background-color: #007bff; color: white; text-decoration: none; border: none; border-radius: 4px; margin-left: 10px;">
                All Products
            </a>
        </form>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Quantity</th>
                    <th>Warranty Period </th>
                    <th>Image</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${productList}">
                    <tr>
                        <td>${product.productId}</td>
                        <td>${product.productName}</td>
                        <td>${product.quantity}</td>
                        <td>${product.warrantyPeriod} years</td>
                        <td>
                            <c:if test="${not empty product.image}">
                                <img src="${product.image}" alt="Product Image" style="width: 100px; height: auto;">
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div style="text-align: center; margin-top: 20px;">
            <c:forEach begin="1" end="${totalPageCount}" var="index">
                <a href="product?index=${index}&keyword=${param.keyword}&quantity=${param.quantity}&warrantyPeriod=${param.warrantyPeriod}" style="margin: 0 5px;">${index}</a>
            </c:forEach>
        </div>

    </body>
</html>