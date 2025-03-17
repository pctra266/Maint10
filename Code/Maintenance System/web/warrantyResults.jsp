<%-- 
    Document   : warrantyResults
    Created on : Mar 16, 2025, 8:46:51 AM
    Author     : Tra Pham
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Warranty Search Results</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h2 class="my-3">Warranty Search Results</h2>

                    <c:choose>
                        <c:when test="${not empty warrantyCardList}">
                            <table class="table table-hover my-0">
                                <thead>
                                    <tr>
                                        <th>Warranty Card Code</th>
                                        <th>Customer Name</th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                       
                                        <th>Issue Description</th>
                                        <th>Warranty Status</th>
                                        <th>Repair Product Code</th>
                                        <th>Repair Product Name</th>
                                         <th>Product Name</th>
                                        <th>Product Code</th>
                                        <th>Created Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${warrantyCardList}" var="warranty">
                                        <tr>
                                            <td>${warranty.warrantyCardCode}</td>
                                            <td>${warranty.name}</td>
                                           <td>${warranty.email}</td>
                                            <td>${warranty.phone}</td>
                                            
                                            <td>${warranty.issueDescription}</td>
                                            <td>${warranty.warrantyStatus}</td>
                                            <td>${warranty.unknownProductCode}</td>
                                            <td>${warranty.unknownProductName}</td>
                                            <td>${warranty.productName}</td>
                                            <td>${warranty.productCode}</td>
                                            <td>${warranty.createdDate}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <p>No warranty records found.</p>
                        </c:otherwise>
                    </c:choose>

                    <br>
                    <a href="Home" class="btn btn-primary">Back to HomePage</a>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
        <script src="js/format-input.js"></script>
    </body>
</html>

