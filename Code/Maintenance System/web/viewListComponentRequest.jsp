<%-- 
    Document   : viewListComponentRequest
    Created on : Feb 20, 2025, 9:10:08 AM
    Author     : Tra Pham
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List Component Request</title>
        
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
                            <input type="hidden" name="action" value="viewListComponentRequest">
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
                        <table class="table table-hover my-0">
                            <thead>
                                <tr>
                                    <th >component Request ID</th>
                                    <th  >warranty Card ID</th>
                                    <th >Date</th>
                                    <th >Status</th>
                                    <th >Note</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${listComponentRequest}" var="o">
                                    <tr>
                                        <td>${o.componentRequestID}</td>
                                        <td>${o.warrantyCardID}</td>
                                        <td>${o.date}</td>
                                        <td>${o.status}</td>
                                        <td>${o.note}</td>
                                        <td><a href="componentRequest?action=detailComponentRequest&componentRequestID=${o.componentRequestID}">Detail</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
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
