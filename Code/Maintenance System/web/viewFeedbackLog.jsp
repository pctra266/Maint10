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

                    <h1 class="text-center">History</h1>
                    <form class="" action="feedbacklog" method="post">
                        <input type="hidden" name="index" value="${index}">
                        <div class="row" style="justify-content: space-between">
                        <div class="col-md-6" style="width: 500px">
                            <input style="margin-top: 15px" class="form-control" type="search" name="FeedbackID" placeholder="Feedback ID"  value="${feedbackID}" >
                            <select style="margin-top: 15px" class="form-select" name="actionOfLog">
                                <option value="">Action </option>
                                <option ${(actionOfLog=='update')?"selected":""} value="update">Update</option>
                                <option ${(actionOfLog=='delete')?"selected":""} value="delete">Delete</option>
                            </select>

                            <button class="btn btn-primary" style="margin-top: 15px" type="submit">Search</button>
                        </div>
                            
                            <div class="col-md-6" style="width: 500px">
                                <div>
                                    <select  onchange="checkSort()" name="column" id="column" style="margin-top: 15px" class="form-select">
                                        <option value="">Sort By</option>
                                        <option ${(column=='FeedbackID')?"selected":""} value="FeedbackID">Feedback ID</option>
                                        <option ${(column=='DateModified')?"selected":""} value="DateModified">Date Modified</option>
                                    </select>
                                </div>
                                <div>
                                    <select onchange="checkSort()" name="sortOrder" id="sortOrder" style="margin-top: 15px" class="form-select">
                                        <option value="">Sort Order</option>
                                        <option ${(sortOrder=='asc')?"selected":""} value="asc">Ascending</option>
                                        <option ${(sortOrder=='desc')?"selected":""} value="desc" >Descending</option>
                                    </select>
                                </div>
                            </div>   
                                    </div>
                    </form>

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
                                        <td><a href="feedbacklog?feedbackLogID=${o.feedbackLogID}&action=undoFeedback">Undo</a></td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="text-center">
                        <ul class="pagination pagination-lg" style="display: flex; justify-content: center; margin-top: 15px">
                            <c:forEach begin="1" end="${endPage}" var="i">
                                <li class="${index == i ? 'page-item active' : 'page-item'}"><a class="page-link" href="feedbacklog?index=${i}&actionOfLog=${actionOfLog}">${i}</a></li>
                                </c:forEach>
                        </ul>

                    </div>
                    <a href="feedback">Back</a>
                </main>
                <jsp:include page="/includes/footer.jsp" />

            </div>

        </div>
                <script>
                                        function checkSort() {
                                            var column = document.getElementById('column').value;
                                            var sortOrder = document.getElementById('sortOrder').value;
                                            window.location.href = 'feedbacklog?index=${index}&column=' + column + '&sortOrder=' + sortOrder + '&feedbackID=${feedbackID}&actionOfLog=${actionOfLog}';
                                        }
        </script>

        <script src="js/app.js"></script>
    </body>
</html>
