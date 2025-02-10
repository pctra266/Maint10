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
        <title>Product List</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
            }

            h1 {
                text-align: center;
                margin-top: 30px;
            }

            .form-container {
                max-width: 600px;
                margin: 20px auto;
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }

            form {
                display: flex;
                flex-direction: column;
                gap: 15px;
            }

            label {
                font-size: 1rem;
                font-weight: bold;
            }

            input, select {
                padding: 10px;
                font-size: 1rem;
                border-radius: 5px;
                border: 1px solid #ddd;
            }

            input[type="number"] {
                -moz-appearance: textfield; /* For removing the up/down arrows in the number field */
                appearance: textfield;
            }

            input[type="number"]::-webkit-outer-spin-button, input[type="number"]::-webkit-inner-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }

            button[type="submit"] {
                background-color: #4CAF50;
                color: white;
                border: none;
                padding: 12px 20px;
                font-size: 1rem;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            button[type="submit"]:hover {
                background-color: #45a049;
            }

            .add-product {
                background-color: #f44336;
                color: white;
                border: none;
                padding: 12px 20px;
                font-size: 1rem;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .add-product:hover {
                background-color: #da190b;
            }

            .form-container h2 {
                text-align: center;
                color: #333;
            }
        </style>

    </head>


    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">

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

                        <label for="imag">Image:</label>
                        <input type="text" id="image" name="image" >

                        <button type="submit">Update Product</button>

                        <form action="viewProduct">
                            <button class="add-product" type="submit">Back</button>
                        </form>
                    </form>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>

