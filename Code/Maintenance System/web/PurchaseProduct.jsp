<%-- 
    Document   : PurchaseProduct
    Created on : Feb 3, 2025, 3:38:07 PM
    Author     : PC
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
    </head>


    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center ">Purchase Product List</h1>

                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th>Product Code</th>
                                <th>Customer Name</th>
                                <th>Customer Email</th>
                                <th>Customer Phone</th>
                                <th>Customer Address</th>
                                <th>Product Name</th>
                                <th>Purchase Date</th>
                                <th>Warranty Period</th>
                            </tr>
                        </thead>
                        <tbody>


                            <c:forEach var="purchaseproduct" items="${requestScope.listPurchaseProduct}">
                                <tr>
                                    <td>${purchaseproduct.productCode}</td>
                                    <td>${purchaseproduct.name}</td>
                                    <td>${purchaseproduct.email}</td>
                                    <td>${purchaseproduct.phone}</td>
                                    <td>${purchaseproduct.address}</td>
                                    <td>${purchaseproduct. productName}</td>
                                    <td>${purchaseproduct.purchaseDate}</td>
                                    <td>${purchaseproduct.warrantyPeriod}</td>
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

