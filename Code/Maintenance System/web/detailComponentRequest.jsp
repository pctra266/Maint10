<%-- 
    Document   : detailComponentRequest
    Created on : Feb 20, 2025, 9:41:23 AM
    Author     : Tra Pham
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center">List Component Request</h1>
                    <a href="componentRequest?action=viewListComponentRequest">Back</a>
                    <div>
                        <form method="get" action="componentRequest" >
                            <input type="hidden" name="action" value="detailComponentRequest">
                            </form>
<!--                        <div class="col-sm-6 col-md-6">
                            <label>Show 
                                <select name="page-size" class="form-select form-select-sm d-inline-block" style="width: auto;" onchange="this.form.submit()">
                                    <c:forEach items="${pagination.listPageSize}" var="s">
                                        <option value="${s}" ${pagination.pageSize==s?"selected":""}>${s}</option>
                                    </c:forEach>
                                </select> 
                                entries
                            </label>
                        </div>-->
<div class="row" style="justify-content: end" >
    <div class="col-md-1">
        <form action="componentRequest" method="get">
            <input type="hidden" name="action" value="updateStatusComponentRequest">

            <input type="hidden" name="page-size" value="${pagination.pageSize}">
            <input type="hidden" name="page" value="${pagination.currentPage}">

            <input  type="hidden" name="componentRequestID" value="${componentRequestID}">
            <input type="hidden" name="componentStatus" value="approved">
            <input type="hidden" name="componentRequestAction" value="${componentRequestAction}">
            <input type="hidden" name="warrantyCardCode" value="${warrantyCardCode}">
            <button class="btn btn-primary" type="submit">Approve</button>
        </form>
    </div>
    <div class="col-md-1">
        <form action="componentRequest" method="get">
            <input type="hidden" name="action" value="updateStatusComponentRequest">
            <input type="hidden" name="page-size" value="${pagination.pageSize}">
            <input type="hidden" name="page" value="${pagination.currentPage}">

            <input  type="hidden" name="componentRequestID" value="${componentRequestID}">
            <input type="hidden" name="warrantyCardCode" value="${warrantyCardCode}">
            <input type="hidden" name="componentRequestAction" value="${componentRequestAction}">
            <input type="hidden" name="componentStatus" value="cancel">
            <button class="btn btn-danger" type="submit">Cancel</button>
        </form>
    </div>
</div>
                                <table  class="table table-hover my-0">
                                    <thead>
                                        <tr>
                                            <th>Component Request ID</th>
                                            <th>Component Code</th>
                                            <th>Component Name</th>
                                            <th>Quantity</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${listComponentRequestDetail}" var="o">
<!--                                    <div style="display: none">
                                        <td>${o.componentRequestDetailID}</td>
                                    </div>-->
                                            <tr>
                                        <!--<td>${o.componentID}</td>-->
                                        <td>${o.componentRequestID}</td>
                                        <td>${o.componentCode}</td>
                                        <td>${o.componentName}</td>
                                        <td>${o.quantity}</td>
                                            </tr>
                                        
                                </c:forEach> 
                                    </tbody>
                                </table>
                        
                    </div>
                    <%--<jsp:include page="/includes/pagination.jsp" />--%>
                    
                </main>
                <jsp:include page="/includes/footer.jsp" />

            </div>

        </div>

        <script src="js/app.js"></script>

    </body>
</html>
