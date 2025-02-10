<%-- 
    Document   : feedbackDashBoard
    Created on : Feb 7, 2025, 7:48:02 PM
    Author     : Tra Pham
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedback </title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <div class="alert-success">${param.mess}</div>
                    <h1 class="text-center">List Feedback</h1>
                    <div><a class="btn btn-success" href="feedback?action=createFeedback"> Create Feedback</a></div>
                    <div>
                        <table class="table table-hover my-0">
                            <thead>
                            <tr>
                                <th>Create Date</th>
                                <th>Feedback</th>
                                <th>Image</th>
                                <th>Video</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listFeedbackByCustomerId}" var="o">
                                <tr>
                                    <td>${o.dateCreated}</td>
                                    <td>${o.note}</td>
                                    
                                    <td><img src="${o.imageURL}" alt="" style="max-width: 100%; height: auto;"></td>
                                    <td><video src="${o.videoURL}" style="max-width: 100%; height: auto;" controls="" ></video></td>
                                    <td><a href="feedback?action=deleteFeedbackFromCustomer&feedbackIdDeleteFromCustomer=${o.feedbackID}">Delete</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        
                        </table>
                        
                    </div>
                    <a href="feedback?action=deletefeedback">Deleted Feedback</a>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
       
    </body>
</html>
