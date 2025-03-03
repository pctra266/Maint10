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
                            <div  class="row"  style="justify-content: space-between" >
                                <div class="col-sm-6 col-md-6">
                                    <div>
                                         <select style="width: 500px" style="margin-top: 15px" class="form-select" name="componentRequestAction" onchange="this.form.submit()">
                                     <option ${(componentRequestAction=='')?"selected":""} value="" >Action </option>
                                    <option ${(componentRequestAction=='waiting')?"selected":""} value="waiting">Waiting</option>
                                    <option ${(componentRequestAction=='approved')?"selected":""} value="approved">Approved</option>
                                    <option ${(componentRequestAction=='cancel')?"selected":""} value="cancel">Cancel</option>
                                </select>
                                    </div>
                            <label>Show 
                                <select name="page-size" class="form-select form-select-sm d-inline-block" style="width: auto;" onchange="this.form.submit()">
                                    <c:forEach items="${pagination.listPageSize}" var="s">
                                        <option value="${s}" ${pagination.pageSize==s?"selected":""}>${s}</option>
                                    </c:forEach>
                                </select> 
                                entries
                            </label>
                        </div>
                                <div class="col-sm-6 col-md-6"  style="width: 500px">
                           <input style="margin-top: 15px" class="form-control" type="search" name="warrantyCardCode" placeholder="Warranty Card Code"  value="${warrantyCardCode}" >
                            <div style="float: right">
                                <button class="btn btn-primary" style="margin-top: 15px" type="submit">Search</button>
                                </div>
                        </div>
                            </div>
                           </form>
                        <table class="table table-hover my-0">
                           <thead>
                                <tr>
                                    <th>component Request ID</th>
                                    <th>Warranty Card Code</th>
                                    <th>Warranty Create Date</th>
                                    <th>Component Request Create Date</th>
                                    <th>Status</th>
                                    <th>Note</th>
                                    <th>Advance</th>
                                    <th>Approve</th>
                                    <th>Cancel</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${listComponentRequest}" var="o">
                                    <tr>
                                <input type="hidden" >
                                        <td>${o.componentRequestID}</td>
                                        <td>${o.warrantyCode}</td>
                                        <td>${o.warrantyCreateDate}</td>
                                        <td>${o.date}</td>
                                        <td>${o.status}</td>
                                        <td>${o.note}</td>
                                        <td><a class="btn btn-info" href="componentRequest?action=detailComponentRequest&componentRequestID=${o.componentRequestID}">Advance</td>
                                        <td>
                                            <form action="componentRequest" method="get">
                                                    <input type="hidden" name="action" value="updateStatusComponentRequest">
                                                    
                                                    <input type="hidden" name="page-size" value="${pagination.pageSize}">
                                                    <input type="hidden" name="page" value="${pagination.currentPage}">
                                                    
                                                    <input  type="hidden" name="componentRequestID" value="${o.componentRequestID}">
                                                    <input type="hidden" name="componentStatus" value="approved">
                                                    <input type="hidden" name="componentRequestAction" value="${componentRequestAction}">
                                                    <input type="hidden" name="warrantyCardCode" value="${warrantyCardCode}">
                                                    <button class="btn btn-primary" type="submit">Approve</button>
                                                </form>
                                                </td>
                                        <td>
                                            <form action="componentRequest" method="get">
                                                    <input type="hidden" name="action" value="updateStatusComponentRequest">
                                                    <input type="hidden" name="page-size" value="${pagination.pageSize}">
                                                    <input type="hidden" name="page" value="${pagination.currentPage}">
                                                    
                                                    <input  type="hidden" name="componentRequestID" value="${o.componentRequestID}">
                                                    <input type="hidden" name="warrantyCardCode" value="${warrantyCardCode}">
                                                    <input type="hidden" name="componentRequestAction" value="${componentRequestAction}">
                                                    <input type="hidden" name="componentStatus" value="cancel">
                                                    <button class="btn btn-primary" type="submit">Cancel</button>
                                                </form>
                                            </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                     
                    </div>
                    <jsp:include page="/includes/pagination.jsp" />
                    
                </main>
                <jsp:include page="/includes/footer.jsp" />

            </div>

        </div>

        <script src="js/app.js"></script>

    </body>
</html>
