<%-- 
    Document   : importExcelCustomer
    Created on : Feb 14, 2025, 9:19:38 PM
    Author     : PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Import Customers from Excel</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }
            .container {
                width: 50%;
                margin: auto;
                text-align: center;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            table, th, td {
                border: 1px solid black;
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
            .message {
                color: green;
                font-weight: bold;
                margin-top: 10px;
            }
            .error {
                color: red;
                font-weight: bold;
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Upload Customer Excel File</h2>
            <form action="addCustomerExcel" method="post" enctype="multipart/form-data">
                <input type="file" name="customerFile" required>
                <button type="submit">Upload</button>
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
        </div>
        <a href="customer">Back</a>
    </body>
</html>
