<%-- 
    Document   : viewProduct
    Created on : Feb 21, 2025, 11:49:16 PM
    Author     : sonNH
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product List</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">

                    <h2>List of Unknown Products</h2>

                    <button onclick="location.href = 'addUnknown'" >Add Unknown Product</button>

                    <form action="ViewUnknownProductServlet" method="get">

                        <input type="text" name="productCode" placeholder="Product Code" value="${param.productCode}">
                        <input type="text" name="productName" placeholder="Product Name" value="${param.productName}">
                        <input type="text" name="description" placeholder="Description" value="${param.description}">
                        <input type="date" name="receivedDate" value="${param.receivedDate}">
                        <input type="text" name="customerName" placeholder="Customer Name" value="${param.customerName}">
                        <button type="submit">Search</button>

                    </form>

                    <table border="1">
                        <thead>
                            <tr>
                                <th>Product Code</th>
                                <th>Product Name</th>
                                <th>Description</th>
                                <th>Received Date</th>
                                <th>Customer Name</th>
                                <th>Customer Phone</th>
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
                                    <td>${product.customerPhone}</td>
                                    <td>
                                        <form action="addWUP" method="post">
                                            <input type="hidden" name="productId" value="${product.unknownProductId}">
                                            <input type="hidden" name="customerId" value="${product.customerId}">
                                            <input type="hidden" name="type" value="display">
                                            <button type="submit">Create a Repair Request</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="pagination">
                        <c:if test="${totalPages > 1}">
                            <c:if test="${currentPage > 1}">
                                <a href="?page=1">First</a>
                                <a href="?page=${currentPage - 1}">Previous</a>
                            </c:if>

                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <a href="?page=${i}" class="${currentPage == i ? 'active' : ''}">${i}</a>
                            </c:forEach>

                            <c:if test="${currentPage < totalPages}">
                                <a href="?page=${currentPage + 1}">Next</a>
                                <a href="?page=${totalPages}">Last</a>
                            </c:if>
                        </c:if>
                    </div>

                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>

    </body>
</html>
