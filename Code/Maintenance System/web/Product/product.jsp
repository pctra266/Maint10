<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product List</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

        <style>
            /* General Styles */
            body {
                font-family: 'Inter', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f4f4f9;
            }

            .wrapper {
                display: flex;
                height: 100vh;
            }

            .main {
                padding: 20px;
                width: 100%;
            }

            .content {
                padding: 20px;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }

            h1 {
                font-size: 2rem;
                margin-bottom: 20px;
                text-align: center;
            }

            /* Form Search Styling */
            form {
                display: flex;
                justify-content: center;
                flex-wrap: wrap;
                gap: 10px;
                margin-bottom: 20px;
            }

            input, select {
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 1rem;
                width: 180px;
            }

            button {
                padding: 8px 16px;
                border: none;
                background-color: #007bff;
                color: #fff;
                cursor: pointer;
                border-radius: 4px;
                font-size: 1rem;
            }

            button:hover {
                background-color: #0056b3;
            }

            .btn-all {
                padding: 8px 16px;
                border: 1px solid #007bff;
                color: #007bff;
                border-radius: 4px;
                text-align: center;
            }

            .btn-all:hover {
                background-color: #007bff;
                color: #fff;
            }

            /* Table Styling */
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            th, td {
                padding: 12px;
                text-align: center;
                border: 1px solid #ddd;
                font-size: 1rem;
            }

            th {
                background-color: #f8f9fa;
                font-weight: 600;
            }

            img {
                max-width: 100px;
                height: auto;
                border-radius: 4px;
            }

            /* Action Links Styling */
            .btn-update {
                color: #28a745;
            }

            .btn-update:hover {
                color: #218838;
            }

            .btn-delete {
                color: #dc3545;
            }

            .btn-delete:hover {
                color: #c82333;
            }

            /* Pagination */
            .pagination {
                text-align: center;
                margin-top: 20px;
            }

            .pagination a {
                margin: 0 5px;
                color: #007bff;
                text-decoration: none;
            }

            .pagination a:hover {
                color: #0056b3;
            }

            .pagination a.active {
                font-weight: bold;
                color: #495057;
            }
        </style>
    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">

                    <h1>Product List</h1>

                    <form action="viewP" method="get">
                        <input type="text" name="keyword" placeholder="Product Name" value="${param.keyword}">
                        <input type="text" name="code" placeholder="Code" value="${param.code}">
                        <input type="number" name="brandId" placeholder="Brand ID" value="${param.brandId}">
                        <input type="text" name="type" placeholder="Type" value="${param.type}">
                        <input type="number" name="quantity" placeholder="Quantity" value="${param.quantity}">
                        <input type="number" name="warrantyPeriod" placeholder="Warranty (years)" value="${param.warrantyPeriod}">
                        <select name="status">
                            <option value="">-- Select Status --</option>
                            <option value="Available" ${param.status == 'Available' ? 'selected' : ''}>Available</option>
                            <option value="Out of Stock" ${param.status == 'Out of Stock' ? 'selected' : ''}>Out of Stock</option>
                        </select>
                        <button type="submit">Search</button>
                        <a href="viewP" class="btn-all">All Products</a>
                        <a href="addProduct.jsp" class="btn-add">Add Product</a>
                    </form>

                    <!-- Product Table -->
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Code</th>
                                <th>Name</th>
                                <th>Brand</th>
                                <th>Type</th>
                                <th>Quantity</th>
                                <th>Warranty</th>
                                <th>Status</th>
                                <th>Image</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="product" items="${productList}">
                                <tr>
                                    <td>${product.productId}</td>
                                    <td>${product.code}</td>
                                    <td>${product.productName}</td>
                                    <td>${product.brandName}</td>
                                    <td>${product.type}</td>
                                    <td>${product.quantity}</td>
                                    <td>${product.warrantyPeriod} months</td>
                                    <td>${product.status}</td>
                                    <td>
                                        <c:if test="${not empty product.image}">
                                            <img src="${product.image}" alt="Product Image">
                                        </c:if>
                                    </td>
                                 
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>


                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
