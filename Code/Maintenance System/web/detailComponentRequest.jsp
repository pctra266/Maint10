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
                    <div>
                        <form method="get" action="componentRequest" >
                            <input type="hidden" name="action" value="detailComponentRequest">
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
                                <c:forEach items="${listComponentRequestDetail}" var="o">
                                    <div style="display: none">
                                        <td>${o.componentRequestDetailID}</td>
                                    </div>
                                        <td>${o.componentID}</td>
                                        <td>${o.componentRequestID}</td>
                                        <td>${o.quantity}</td>
                                </c:forEach>       
                        </form>
                    </div>
                    <jsp:include page="/includes/pagination.jsp" />
                    
                </main>
                <jsp:include page="/includes/footer.jsp" />

            </div>

        </div>

        <script src="js/app.js"></script>

    </body>
</html>
