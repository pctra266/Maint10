<%-- 
    Document   : addProduct
    Created on : Jan 20, 2025, 9:01:02 PM
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
            /* Main Content Styling */
            .content {
                padding: 25px;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                max-width: 600px;
                margin: 50px auto;
            }

            /* Title Styling */
            h1 {
                font-size: 1.8rem;
                text-align: center;
                margin-bottom: 25px;
                color: #333;
            }

            /* Form Styling */
            form {
                display: flex;
                flex-direction: column;
                gap: 15px;
                padding: 10px;
                background-color: #f9f9f9;
                border-radius: 8px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }

            label {
                font-size: 1rem;
                color: #555;
            }

            input[type="text"],
            input[type="number"] {
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 1rem;
                width: 100%;
                box-sizing: border-box;
            }

            input[type="text"]:focus,
            input[type="number"]:focus {
                border-color: #007bff;
                outline: none;
            }

            /* Button Styling */
            button[type="submit"] {
                padding: 8px 16px;
                background-color: #007bff;
                color: #fff;
                font-size: 1rem;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            button[type="submit"]:hover {
                background-color: #0056b3;
            }

            /* Back Button Styling */
            .back-button {
                padding: 8px 16px;
                background-color: #ccc;
                color: #333;
                font-size: 1rem;
                text-decoration: none;
                border-radius: 4px;
                display: inline-block;
                margin-top: 10px;
                text-align: center;
                width: 100%;
                box-sizing: border-box;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .back-button:hover {
                background-color: #999;
            }

            /* Responsive Styling */
            @media (max-width: 600px) {
                .content {
                    padding: 20px;
                    margin: 20px;
                }

                form {
                    padding: 15px;
                }
            }
        </style>

    </head>


    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">

                    <h1>Add New Product</h1>
                    <form action="addP" method="post">
                        <label for="name">Product Name:</label>
                        <input type="text" id="name" name="productName" required><br><br>

                        <label for="quantity">Quantity:</label>
                        <input type="number" id="quantity" name="quantity" required><br><br>

                        <label for="warranty">Warranty Period (years):</label>
                        <input type="number" id="warranty" name="warrantyPeriod" required><br><br>

                        <label for="image">Image:</label>
                        <input type="text" id="image" name="image"><br><br>

                        <button type="submit">Add Product</button>
                        <!-- Added Back Button -->
                        <a href="product" class="back-button">Back</a>
                    </form>
                    
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
