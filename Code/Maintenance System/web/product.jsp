

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List Feedback</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

        <style>
            /* General Styles for the Content Section */
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

            a {
                text-decoration: none;
                color: #007bff;
            }

            a:hover {
                color: #0056b3;
            }

            /* Form Search Styling */
            form {
                display: flex;
                justify-content: center;
                gap: 10px;
                margin-bottom: 20px;
            }

            input[type="text"],
            input[type="number"] {
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 1rem;
                width: 200px;
            }

            button[type="submit"] {
                padding: 8px 16px;
                border: none;
                background-color: #007bff;
                color: #fff;
                cursor: pointer;
                border-radius: 4px;
                font-size: 1rem;
            }

            button[type="submit"]:hover {
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

            td {
                background-color: #fff;
            }

            img {
                max-width: 100px;
                height: auto;
                border-radius: 4px;
            }

            /* Action Links Styling */
            a {
                margin: 0 10px;
                color: #28a745;
            }

            a:hover {
                color: #218838;
            }

            .btn-delete {
                color: #dc3545;
            }

            .btn-delete:hover {
                color: #c82333;
            }

            /* Pagination Styling */
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

            /* Form Search Styling */
            form {
                display: flex;
                justify-content: center;
                gap: 10px;
                margin-bottom: 20px;
                flex-wrap: wrap; /* Để nút "Add" và các input không bị tràn */
            }

            input[type="text"],
            input[type="number"] {
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 1rem;
                width: 200px;
            }

            button[type="submit"] {
                padding: 8px 16px;
                border: none;
                background-color: #007bff;
                color: #fff;
                cursor: pointer;
                border-radius: 4px;
                font-size: 1rem;
            }

            button[type="submit"]:hover {
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

            /* Add Button Styling */
            .btn-add {
                padding: 8px 16px;
                background-color: #28a745;
                color: #fff;
                border-radius: 4px;
                text-align: center;
                text-decoration: none;
                font-size: 1rem;
                display: inline-block;
            }

            .btn-add:hover {
                background-color: #218838;
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

            td {
                background-color: #fff;
            }

            img {
                max-width: 100px;
                height: auto;
                border-radius: 4px;
            }

            /* Action Links Styling */
            a {
                margin: 0 10px;
                color: #28a745;
            }

            a:hover {
                color: #218838;
            }

            .btn-delete {
                color: #dc3545;
            }

            .btn-delete:hover {
                color: #c82333;
            }

            /* Pagination Styling */
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
                        <a href="product" class="btn-all">All Products</a>
                        <!-- Nút Add được đặt cạnh nút tìm kiếm -->
                        <a href="addProduct.jsp" class="btn-add">Add</a>
                    </form>


                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Quantity</th>
                                <th>Warranty Period</th>
                                <th>Image</th>
                                <th>Action</th>
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
                                    <td>
                                        <a href="updateProduct.jsp?productId=${product.productId}&productName=${product.productName}&quantity=${product.quantity}&warrantyPeriod=${product.warrantyPeriod}&image=${product.image}">
                                            Update
                                        </a>

                                        <a href="deleteProduct?productId=${product.productId}" class="btn-delete" onclick="return confirm('Are you sure you want to set quantity to 0?');">
                                            Delete
                                        </a>
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


                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
