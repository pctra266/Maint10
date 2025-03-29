<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Import Excel Customer</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            body {
                font-family: 'Inter', sans-serif;
                background-color: #f4f6f9;
                margin: 0;
                padding: 0;
            }
            .container {
                max-width: 800px;
                margin: 40px auto;
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            h1 {
                text-align: center;
                color: #333;
            }
            .form-group {
                margin-bottom: 20px;
                text-align: center;
            }
            input[type="file"] {
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                width: 100%;
            }
            .btn-upload {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 10px 15px;
                font-size: 16px;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }
            .btn-upload:hover {
                background-color: #0056b3;
            }
            .message, .error {
                text-align: center;
                font-size: 16px;
                padding: 10px;
                border-radius: 5px;
            }
            .message {
                background-color: #d4edda;
                color: #155724;
            }
            .error {
                background-color: #f8d7da;
                color: #721c24;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            table, th, td {
                border: 1px solid #ddd;
            }
            th, td {
                padding: 10px;
                text-align: center;
            }
            th {
                background-color: #007bff;
                color: white;
            }
            .back-link {
                display: block;
                text-align: center;
                margin-top: 20px;
                text-decoration: none;
                color: #007bff;
                font-weight: bold;
            }
            .back-link:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <div class="container">
                        <h1>Import Excel</h1>
                        <form action="addCustomerExcel" method="post" enctype="multipart/form-data" class="form-group">
                            <input type="file" name="customerFile" required>
                            <br><br>
                            <button type="submit" class="btn-upload">Upload</button>
                        </form>

                        <c:if test="${not empty requestScope.mess}">
                            <p class="message">${requestScope.mess}</p>
                        </c:if>
                        <c:if test="${not empty requestScope.error}">
                            <p class="error">${requestScope.error}</p>
                        </c:if>
                        <c:if test="${not empty requestScope.listExistCustomer}">
                            <h3 class="error">Customers already exist in the system:</h3>
                            <table>
                                <tr>
                                    <th>Username</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                </tr>
                                <c:forEach var="customer" items="${requestScope.listExistCustomer}">
                                    <tr>
                                        <td>${customer.usernameC}</td>
                                        <td>${customer.name}</td>
                                        <td>${customer.email}</td>
                                        <td>${customer.phone}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                        <a href="customer" class="back-link">Back</a>
                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
