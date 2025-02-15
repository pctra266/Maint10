<%-- 
    Document   : feedbackDashBoard
    Created on : Feb 7, 2025, 7:48:02 PM
    Author     : Tra Pham
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
                    <h1 class="text-center">List Product Maintain</h1>
                    <div>
                         <form method="get" action="feedback" >
                            <input type="hidden" name="action" value="viewFeedbackDashboard">
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
                                <th>Warranty Card Code</th>
                                <th>Created Date</th>
                                <th>Product Name</th>
                                <th>Issue Description</th>
                                <th>Warranty Status</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            
                            <c:forEach items="${listProductCreateFeedback}" var="o">
                                <tr>
                                    <td>${o.warrantyCardCode}</td>
                                    <td>${o.createdDate}</td>
                                    <td>${o.productName}</td>
                                    <td>${o.issueDescription}</td>
                                    <td>${o.warrantyStatus}</td>
                                    <td><a class="btn btn-success" href="feedback?action=createFeedback&warrantyCardID=${o.warrantyCardID}">Create Feedback</a></td>
                                </tr>
                            </c:forEach>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td><a class="btn btn-success" href="feedback?action=createFeedback">Create Feedback</a></td>
                                </tr>
                        </tbody>
                        
                        </table>
                         </form>   
                    </div>
                     <jsp:include page="/includes/pagination.jsp" />
                     <a class="btn btn-primary" href="feedback?action=viewListFeedbackByCustomerId">Feedback History</a>
                     
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
