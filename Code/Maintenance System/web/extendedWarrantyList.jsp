<%-- 
    Document   : extendedWarrantyList
    Created on : Mar 16, 2025, 8:56:45 PM
    Author     : Tra Pham
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Extended Warranty List</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/light.css">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <div class="">
                        <h1 class="text-center">Extended Warranty List</h1>

                        <c:if test="${not empty message}">
                            <div class="alert alert-info">${message}</div>
                        </c:if>

                        <form method="get" action="${pageContext.request.contextPath}/ExtendedWarrantyController">
                            <input type="hidden" name="action" value="view">
                            <div class="row"  style="justify-content: space-between">
                                <div class="col-sm-6 col-md-6" style="width: 500px"><!-- comment -->
                                    <input style="margin-top: 15px" type="text" name="searchExtendedWarrantyName" class="form-control" placeholder="Extended Warranty Name" value="${searchExtendedWarrantyName}">
                                    <div style="margin-top: 15px" >
                                        <label >Show 
                                            <select name="page-size" class="form-select form-select-sm d-inline-block" style="width: auto;" onchange="this.form.submit()">
                                                <c:forEach items="${pagination.listPageSize}" var="s">
                                                    <option value="${s}" ${pagination.pageSize==s?"selected":""}>${s}</option>
                                                </c:forEach>
                                            </select> 
                                            entries
                                        </label>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-md-6" style="width: 500px"><!-- comment -->
                                 <div>
                                   <input type="text" readonly="" style="border: none; background: none; ">
                               </div>
                                    <div style="float: right">
                                        <button class="btn btn-primary" style="margin-top: 15px" type="submit">Search</button>
                                    </div>
                                </div>
                            </div>

                        </form>

                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Warranty Name</th>
                                    <th>Period (Months)</th>
                                    <th>Price</th>
                                    <th>Description</th>
                                    <th>Update</th>
                                    <th>Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="ew" items="${extendedWarranties}">
                                    <tr>
                                        <td>${ew.extendedWarrantyID}</td>
                                        <td>${ew.extendedWarrantyName}</td>
                                        <td>${ew.extendedPeriodInMonths}</td>
                                        <td>${ew.price}</td>
                                        <td>${ew.extendedWarrantyDescription}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/ExtendedWarrantyController?action=edit&amp;extendedWarrantyID=${ew.extendedWarrantyID}" class="btn btn-primary">Update</a>
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/ExtendedWarrantyController?action=delete&amp;extendedWarrantyID=${ew.extendedWarrantyID}" class="btn btn-danger" onclick="return confirm('Are you sure to delete this extended warranty?');">Delete</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                                    <div >               
                        <a href="${pageContext.request.contextPath}/ExtendedWarrantyController?action=new" class="btn btn-primary">Create Extended Warranty</a>
                         </div>
                        <jsp:include page="/includes/pagination.jsp" />
                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
            <script src="js/app.js"></script>
        </div>
    </body>
</html>



