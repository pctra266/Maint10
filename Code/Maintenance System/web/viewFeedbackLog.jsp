<%-- 
    Document   : viewHistoryFeedback
    Created on : Jan 19, 2025, 10:37:07 AM
    Author     : Tra Pham
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedback Log</title>
        
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
        <h1>History</h1>
        <table class="table table-hover my-0">
            <thead>
                <tr>
                    <th>Feedback Log ID</th>
                    <th>Feedback ID</th>
                    <th>Action</th>
                    <th>Old Feedback Text</th>
                    <th>New Feedback Text</th>
                    <th>Modified By</th>
                    <th>Date Modified</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${listFeedbackLog}" var="o">
                <tr>
                    <td>${o.feedbackLogID}</td>
                    <td>${o.feedbackID}</td>
                    <td>${o.action}</td>
                    <td>${o.oldFeedbackText}</td>
                    <td>${o.newFeedbackText}</td>
                    <td>${o.modifiedBy}</td>
                    <td>${o.dateModified}</td>
                    <c:if test="${o.action=='delete'}">
                    <td><a href="UndoFeedback?feedbackLogID=${o.feedbackLogID}">Undo</a></td>
                    </c:if>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="ViewListFeedback">Back</a>
        </main>
                <jsp:include page="/includes/footer.jsp" />

            </div>

        </div>

        <script src="js/app.js"></script>
    </body>
</html>
