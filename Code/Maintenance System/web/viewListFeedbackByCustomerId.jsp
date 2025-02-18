<%-- 
    Document   : viewListFeedbackByCustomerId
    Created on : Feb 12, 2025, 7:42:36 PM
    Author     : Tra Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <h1 class="text-center">List Feedback</h1>
                    <a href="feedback?action=viewFeedbackDashboard" class="btn btn-primary  d-flex align-items-center justify-content-center" style="transform:translate(-30%,-60%); height: 2.5rem; width: 5.2rem"><i class="fas fa-arrow-left fa-4"></i> <span class="ms-2">Back</span> </a>
                    <div>
                        <form method="get" action="feedback" >
                            <input type="hidden" name="action" value="viewListFeedbackByCustomerId">
                        <div class="col-sm-6 col-md-6">
                            <label>Show 
                                <select name="page-size" class="form-select form-select-sm d-inline-block" style="width: auto;" onchange="this.form.submit()">
                                    <c:forEach items="${pagination.listPageSize}" var="s">
                                        <option value="${s}" ${pagination.pageSize==s?"selected":""}>${s}</option>
                                    </c:forEach>
                                </select> 
                                entries
                            </label>
                        </div>
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
                                        <td><a href="#" data-url="feedback?action=deleteFeedbackFromCustomer&feedbackIdDeleteFromCustomer=${o.feedbackID}" onclick="doDelete(event)">Delete</td>
                                    </tr>
                                </c:forEach>
                            </tbody>

                        </table>
                        </form>
                    </div>
                    <jsp:include page="/includes/pagination.jsp" />
                    <!--                    <a href="feedback?action=deletefeedback">Feedback History</a>-->
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script>
            function doDelete(event) {
                event.preventDefault();

                const url = event.currentTarget.getAttribute('data-url');

                if (confirm("Bạn có chắc chắn muốn xóa feedback này không?")) {
                    window.location.href = url;
                }
            }
        </script>
        <script src="js/app.js"></script>

    </body>
</html>
