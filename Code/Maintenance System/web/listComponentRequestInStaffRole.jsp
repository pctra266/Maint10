<%-- 
    Document   : listComponentRequestByStaffId
    Created on : Mar 2, 2025, 8:06:01 PM
    Author     : Tra Pham
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Component Request</title>
        
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center">Waiting Component Request</h1>
                    <a href="componentRequest" >Back</a>
                    <div>
                        <form method="get" action="componentRequest" >
                            <input type="hidden" name="action" value="listComponentRequestInStaffRole">
                            <div  class="row"  style="justify-content: space-between" >
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
                                    <th>ID</th>
                                    <th>Warranty Card Code</th>
                                    <th>Component Request Create Date</th>
                                    <th>Status</th>
                                    <th>Note</th>
                                    <th>Detail</th>
                                    <th>Cancel</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${listComponentRequest}" var="o">
                                    <tr>
                                        <td>${o.componentRequestID}</td>
                                        <td>${o.warrantyCode}</td>
                                        <td>${o.date}</td>
                                        <td>${o.status}</td>
                                        <td>${o.note}</td>
                                        <td>
                                        <form action="componentRequest" method="get">
                                            <input type="hidden" name="action" value="getRequestDetails">
                                            <input type="hidden" name="page-size" value="${pagination.pageSize}">
                                            <input type="hidden" name="page" value="${pagination.currentPage}">
                                            <input type="hidden" name="warrantyCardCode" value="${warrantyCardCode}">
                                            <input type="hidden" name="componentRequestID" value="${o.componentRequestID}">
                                            <button type="submit" class="btn btn-primary">Detail</button>
                                        </form>

                                        <c:if test="${not empty requestDetailsList and selectedComponentRequestID == o.componentRequestID}">
                                            <div class="modal fade show" id="detailModal${o.componentRequestID}" tabindex="-1" 
                                                 aria-labelledby="detailModalLabel${o.componentRequestID}" style="display: block;" aria-hidden="false">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="detailModalLabel${o.componentRequestID}">Request Details (Component Request ID: ${componentRequestID}) </h5>
                                                        </div>
                                                        <div class="modal-body">
                                                            
                                                            <c:forEach items="${requestDetailsList}" var="detail">
                                                                Component Code: ${detail.componentCode}<br>
                                                                Component Name:  ${detail.componentName}<br>
                                                                Quantity:  ${detail.quantity}<br>
                                                                <hr>
                                                            </c:forEach>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <form action="componentRequest" method="get">
                                                                <input type="hidden" name="action" value="listComponentRequestInStaffRole">
                                                                <input type="hidden" name="page-size" value="${pagination.pageSize}">
                                                                <input type="hidden" name="page" value="${pagination.currentPage}">
                                                                <input type="hidden" name="warrantyCardCode" value="${warrantyCardCode}">
                                                                <button type="submit" class="btn btn-secondary">Close</button>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-backdrop fade show"></div>
                                        </c:if>
                                    </td>
                                        <td>
                                            <form action="componentRequest" method="get">
                                                    <input type="hidden" name="action" value="cancelComponentRequest">
                                                    <input type="hidden" name="page-size" value="${pagination.pageSize}">
                                                    <input type="hidden" name="page" value="${pagination.currentPage}">
                                                    <input  type="hidden" name="componentRequestID" value="${o.componentRequestID}">
                                                    <input type="hidden" name="componentStatus" value="cancel">
                                                    <button class="btn btn-danger" type="submit">Cancel</button>
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
