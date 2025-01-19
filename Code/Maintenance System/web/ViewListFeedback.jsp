<%-- 
    Document   : ViewListFeedback
    Created on : Jan 17, 2025, 7:09:31 PM
    Author     : Tra Pham
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
                    <h1 class="text-center">Feedback List</h1>
                    <form action="ViewListFeedback" method="post">
                    <input type="search" name="customerName">
                    <button type="submit">Search</button>
                    </form>
        <table class="table table-hover my-0">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Customer Name</th>
                    <th>Create Date</th>
                    <th>Feedback</th>
                    <th>Image & Video </th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
              
            <c:forEach items="${listFeedback}" var="o">
                <tr>
                    <c:if test="${!o.isDeleted}">
                    <td>${o.feedbackID}</td>
                    <td>${o.customerName}</td>
                    <td>${o.dateCreated}</td>
                    <td>${o.note}</td>
                    <td>${(o.videoURL!=null || o.imageURL != null)?"Attached":"None"}</td>
                    <td><a href="DeleteFeedback?feedbackID=${o.feedbackID}">Delete</a></td>
                    <td><a href="UpdateFeedback?feedbackID=${o.feedbackID}">Detail</a></td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
                <a href="ViewFeedbackLog">History</a>
                </main>
                <jsp:include page="/includes/footer.jsp" />

            </div>

        </div>

        <script src="js/app.js"></script>
        
    </body>
</html>
