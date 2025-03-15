<%-- 
    Document   : listSupplementRequest
    Created on : Mar 15, 2025, 11:06:09 PM
    Author     : Tra Pham
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>list Supplement Request</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
               <main class="content">
                   <c:if test="${not empty param.error}">
                       <div class="alert alert-danger" role="alert">
                           ${param.error}
                       </div>
                   </c:if>

                   <c:if test="${not empty param.success}">
                       <div class="alert alert-success" role="alert">
                           ${param.success}
                       </div>
                   </c:if>

        <table class="table table-hover my-0">
    <thead>
        <tr>
            <th>Supplement Request ID</th>
            <th>Component Name</th>
            <th>Component Type</th>
            <th>Request Date</th>
            <th>Status</th>
            <th>Note</th>
            <th>Approve</th>
            <th>Cancel</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${listSupplementRequest}" var="request">
            <tr>
                <td>${request.requestID}</td>
                <td>${request.componentName}</td>
                <td>${request.componentType}</td>
                <td>${request.requestDate}</td>
                <td>${request.status}</td>
                <td>${request.note}</td>
                <td>
                    <form action="supplementRequest" method="post">
                        <input type="hidden" name="action" value="updateSupplementRequest">
                        <input type="hidden" name="requestID" value="${request.requestID}">
                        <input type="hidden" name="status" value="approved">
                        <button class="btn btn-primary" type="submit" 
                                onclick="return confirm('Are you sure you want to approve this request?');">
                            Approve
                        </button>
                    </form>
                </td>
                <td>
                    <form action="supplementRequest" method="post">
                        <input type="hidden" name="action" value="updateSupplementRequest">
                        <input type="hidden" name="requestID" value="${request.requestID}">
                        <input type="hidden" name="status" value="cancel">
                        <button class="btn btn-danger" type="submit"
                                onclick="return confirm('Are you sure you want to cancel this request?');">
                            Cancel
                        </button>
                    </form>
                </td>

            </tr>
        </c:forEach>
    </tbody>
</table>
                        </main>


                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>

        <script src="js/app.js"></script><div class="notyf"></div><div class="notyf-announcer" aria-atomic="true" aria-live="polite" style="border: 0px; clip: rect(0px, 0px, 0px, 0px); height: 1px; margin: -1px; overflow: hidden; padding: 0px; position: absolute; width: 1px; outline: 0px;"></div>
        <script src="js/format-input.js"></script>
    </body>
</html>
