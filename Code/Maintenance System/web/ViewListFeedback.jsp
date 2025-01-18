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
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Feedback List</h1>
        <table border="1">
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
        <h2>============================================================================================================</h2>
        <h1>History</h1>
        <table border="1">
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

    </body>
</html>
