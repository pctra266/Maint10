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
        <title>Add Product</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            /* Base Styles */
            body {
                font-family: 'Inter', sans-serif;
                background-color: #f9f9f9;
                color: #333;
                margin: 0;
                padding: 0;
            }

            .wrapper {
                display: flex;
                min-height: 100vh;
            }

            .main {
                flex: 1;
                padding: 40px 20px;
                background-color: #ffffff;
            }

            h2 {
                font-size: 2rem;
                color: #444;
                text-align: center;
                margin-bottom: 20px;
            }

            .form-container {
                max-width: 600px;
                margin: auto;
                padding: 30px;
                background: #fff;
                border-radius: 8px;
                box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.1);
                box-sizing: border-box;
            }

            .form-group {
                margin-bottom: 20px;
            }

            label {
                font-size: 1rem;
                font-weight: bold;
                margin-bottom: 8px;
                display: inline-block;
                color: #555;
            }

            input, select {
                width: 100%;
                padding: 12px;
                margin-top: 8px;
                border: 1px solid #ccc;
                border-radius: 6px;
                font-size: 1rem;
                box-sizing: border-box;
                background-color: #f9f9f9;
                transition: border-color 0.3s ease;
            }

            input:focus, select:focus {
                border-color: #007bff;
                outline: none;
            }

            input[type="number"] {
                -moz-appearance: textfield;
            }

            button {
                background-color: #007bff;
                color: white;
                padding: 12px;
                border: none;
                width: 100%;
                cursor: pointer;
                border-radius: 6px;
                font-size: 1rem;
                transition: background-color 0.3s ease;
                margin-top: 20px;
            }

            button:hover {
                background-color: #0056b3;
            }

            /* Back Button */
            form > button.add-product {
                background-color: #e2e2e2;
                color: #333;
                padding: 10px;
                text-align: center;
                display: block;
                width: 100%;
                border-radius: 6px;
                font-size: 1rem;
                transition: background-color 0.3s ease;
            }

            form > button.add-product:hover {
                background-color: #ccc;
            }

            /* File Input */
            input[type="file"] {
                background-color: #fff;
                padding: 10px;
                border-radius: 6px;
                border: 1px solid #ccc;
                cursor: pointer;
            }

            /* Form Section Spacing */
            form + form {
                margin-top: 30px;
            }

            /* Responsive Design */
            @media (max-width: 768px) {
                .form-container {
                    width: 100%;
                    padding: 20px;
                }

                h2 {
                    font-size: 1.8rem;
                }
            }

            @media (max-width: 480px) {
                .form-container {
                    padding: 15px;
                }

                button {
                    padding: 10px;
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
                    <div class="form-container">
                        <h2>Add Product</h2>

                        <form action="viewProduct">
                            <button class="add-product" type="submit">Back</button>
                        </form>
                        <form action="addP" method="post">
                            <div class="form-group">
                                <label for="code">Product Code:</label>
                                <input type="text" id="code" name="code" required>
                            </div>
                            <div class="form-group">
                                <label for="name">Product Name:</label>
                                <input type="text" id="name" name="name" required>
                            </div>

                            <div class="form-group">
                                <label for="brand">Brand:</label>
                                <select id="brand" name="brandId">
                                    <option value="">Select Brand</option>
                                    <c:forEach var="p" items="${listBrand}">
                                        <option value="${p.brandId}">
                                            ${p.brandName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="type">Type:</label>
                                <input type="text" id="type" name="type" required>
                            </div>

                            <div class="form-group">
                                <label for="quantity">Quantity:</label>
                                <input type="number" id="quantity" name="quantity" min="1" required>
                            </div>
                            <div class="form-group">
                                <label for="warranty">Warranty Period (months):</label>
                                <input type="number" id="warranty" name="warrantyPeriod" min="1" required>
                            </div>
                            <div class="form-group">
                                <label for="status">Status:</label>
                                <select id="status" name="status">
                                    <option value="Active">Active</option>
                                    <option value="Inactive">Inactive</option>
                                </select>
                            </div>
                            <button type="submit" class="submit-btn">Add Product</button>
                        </form>

                        <form action="image"method="post" enctype="multipart/form-data">
                            <div class="form-group" >
                                <label for="image">Product Image:</label>
                                <input type="file" id="image" name="image" accept=".jpg,.png">
                            </div>
                            <button type="submit" class="submit-btn">Save</button>
                        </form>
                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
