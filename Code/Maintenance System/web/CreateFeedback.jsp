<%-- 
    Document   : CreateFeedback
    Created on : Jan 17, 2025, 10:26:47 PM
    Author     : Tra Pham
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Feedback</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <div class="card">
                        <div class="card-header">
                            <h1 class="text-center">Create Feedback</h1>
                        </div>
                         <div class="card-body">
                        <form action="feedback" method="post">
                            <input type="hidden" name="action" value="createFeedback">
                            <div>
                                <input name="customerId" type="hidden" value="1" >
                            </div>
                            <div>
                                <label class="form-label">Product: </label>

                                <select class="form-select" name="WarrantyCardID">
                                    <option value=""></option>
                                    <c:forEach items="${listProductByCustomerId}" var="o">
                                        <option value="${o.warrantyCardID}">${o.productName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label class="form-label">Image: </label>
                                    <input class="form-control" name="imageURL" type="file">
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Video: </label>
                                    <input class="form-control" name="videoURL" type="file">
                                </div>
                            </div>
                            <div>
                                <label class="form-label">Note: </label>
                                <textarea class="form-control" name="note" required="" ></textarea>
                            </div>
                            <button class="btn btn-primary" type="submit"> Submit </button>
                        </form>
                         </div>
                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
