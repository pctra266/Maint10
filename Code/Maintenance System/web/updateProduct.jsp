<%-- 
    Document   : updateProduct
    Created on : Jan 20, 2025, 9:01:50 PM
    Author     : sonNH
--%>

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
            /* General page styles */
            body {
                font-family: 'Inter', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f4f7fc;
            }

            /* Wrapper for the page content */
         
            /* Main content styling */
            .main {
                flex-grow: 1;
                background-color: #fff;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
                transition: all 0.3s ease;
            }

            /* Title */
            h1 {
                font-size: 28px;
                font-weight: 600;
                color: #333;
                margin-bottom: 20px;
                text-align: center;
            }

            /* Form styles */
            form {
                display: flex;
                flex-direction: column;
                max-width: 600px;
                margin: 0 auto;
            }

            /* Label styling */
            label {
                font-size: 16px;
                font-weight: 500;
                color: #555;
            }

            /* Input fields */
            input[type="text"], input[type="number"] {
                padding: 12px;
                font-size: 16px;
                border: 1px solid #ddd;
                border-radius: 8px;
                box-sizing: border-box;
                transition: all 0.3s ease;
            }

            input[type="text"]:focus, input[type="number"]:focus {
                border-color: #007bff;
                outline: none;
            }

            /* Submit button */
            button[type="submit"] {
                padding: 12px 20px;
                font-size: 16px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 8px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            button[type="submit"]:hover {
                background-color: #0056b3;
            }

            /* Spacing for form elements */
            form input, form label {
                margin-bottom: 10px;
            }

        </style>
    </head>


    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1>Update Product</h1>
                    <form action="updateproduct" method="post">
                        <input type="hidden" name="productId" value="${param.productId}">

                        <label for="name">Product Name:</label>
                        <input type="text" id="name" name="productName" value="${param.productName}" required><br><br>

                        <label for="quantity">Quantity:</label>
                        <input type="number" id="quantity" name="quantity" value="${param.quantity}" required><br><br>

                        <label for="warranty">Warranty Period (years):</label>
                        <input type="number" id="warranty" name="warrantyPeriod" value="${param.warrantyPeriod}" required><br><br>

                        <label for="image">Image:</label>
                        <input type="text" id="image" name="image"><br><br>

                        <button type="submit">Update Product</button>
                    </form>

                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>


        <script src="js/app.js"></script>

    </body>


</html>
