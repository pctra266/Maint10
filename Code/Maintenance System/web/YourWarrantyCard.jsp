<%-- 
    Document   : YourWarrantyCard
    Created on : Feb 3, 2025, 9:08:43 PM
    Author     : PC
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Warranty Card</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>


    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center ">Your Warranty Card</h1>

                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th>Warranty Card ID</th>
                                <th>Warranty Card Code</th>
                                <th>Product</th>
                                <th>Create Date</th>
                                <th>Status</th>
                                <th>Issue Description</th>
                                <th>Customer Name</th>
                                <th>Customer Phone</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${not empty requestScope.listWarrantyCard}">
                                <c:forEach var="yourwarrantycard" items="${requestScope.listWarrantyCard}">
                                    <tr>
                                        <td>${yourwarrantycard.warrantyCardID}</td>
                                        <td>${yourwarrantycard.warrantyCardCode}</td>
                                        <td>
                                           ${yourwarrantycard.productName}
                                        </td>
                                       <td>${yourwarrantycard.createdDate}</td>

                                        <td>${yourwarrantycard.warrantyStatus}</td>
                                        <td>${yourwarrantycard.issueDescription}</td>
                                        <td>${yourwarrantycard.customerName}</td>
                                        <td>${yourwarrantycard.customerPhone}</td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty requestScope.listWarrantyCard}">
                                <tr>
                                    <td colspan="8" class="text-center">No warranty cards found.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>


                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>


        <script src="js/app.js"></script>

    </body>


</html>
