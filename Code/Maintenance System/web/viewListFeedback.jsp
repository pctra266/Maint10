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
                    <h1 class="text-center ">Feedback List</h1>
                    <form class="" action="feedback" method="post">
                        <div class="card-body" style="width: 500px">
                                <input class="form-control" type="search" name="customerName" placeholder="Customer Name"  value="${customerName}" >
                            <select style="margin-top: 15px" class="form-select" name="imageAndVideo">
                                <option value="">Image & Video </option>
                                <option ${(imageAndVideo=='empty')?"selected":""} value="empty">Empty</option>
                                <option ${(imageAndVideo=='attached')?"selected":""} value="attached">Attached</option>
                            </select>
                            <button class="btn btn-primary" style="margin-top: 15px" type="submit">Search</button>
                            </div>
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
                                        <td>${o.feedbackID}</td>
                                        <td>${o.customerName}</td>
                                        <td>${o.dateCreated}</td>
                                        <td>${o.note}</td>
                                        <td>${(o.videoURL!=null || o.imageURL != null)?"Attached":"Empty"}</td>
                                        <td><a href="feedback?feedbackID=${o.feedbackID}&action=deleteFeedback">Delete</a></td>
                                        <td><a href="feedback?feedbackID=${o.feedbackID}&action=updateFeedback">Detail</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="text-center">
                        <c:forEach begin="1" end="${endPage}" var="i">
                            <a href="feedback?index=${i}&customerName=${customerName}&imageAndVideo=${imageAndVideo}">${i}</a>
                        </c:forEach>
                    </div> 
                    <a href="feedbacklog">History</a>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>


        <script src="js/app.js"></script>

    </body>


</html>
