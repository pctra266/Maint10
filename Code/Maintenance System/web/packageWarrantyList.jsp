<%-- 
    Document   : packageWarrantyList
    Created on : Mar 16, 2025, 9:16:56 PM
    Author     : Tra Pham
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Package Warranty List</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1  class="text-center">Package Warranty List</h1>
                    <form method="get" action="packageWarranty">
                        <input type="hidden" name="action" value="view">
                        <div class="row"  style="justify-content: space-between">
                            <div style="margin-top: 15px;width: 500px"  class="col-sm-6 col-md-6" >
                                <input style="margin-top: 15px"  class="form-control" type="search" name="searchProductCode" placeholder="Product Code" value="${searchProductCode}">
                                <input style="margin-top: 15px"  class="form-control" type="search" name="searchEmail" placeholder="Email" value="${searchEmail}">
                                <input style="margin-top: 15px"  class="form-control" type="search" name="searchExtendedWarrantyName" placeholder="Extended Warranty Name" value="${searchExtendedWarrantyName}">
                                 <div class="col-md-3" style="margin-top: 1rem;">
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
                            
                            <div style="margin-top: 15px;width: 500px"  class="col-sm-6 col-md-6" >
                                <input style="margin-top: 15px"  class="form-control" type="search" name="searchCustomerName" placeholder="Customer Name" value="${searchCustomerName}">
                                <input style="margin-top: 15px"  class="form-control" type="search" name="searchProductName" placeholder="Product Name" value="${searchProductName}">
                                <select style="margin-top: 15px" class="form-select" name="filterExtendWarranty" onchange="this.form.submit()">
                                    <option value="">Current Extend Warranty</option>
                                    <option ${(filterExtendWarranty=='hasExtend')?"selected":""} value="hasExtend">Has Extend Warranty</option>
                                    <option ${(filterExtendWarranty=='noneExtend')?"selected":""} value="noneExtend">None Extend Warranty</option>
                                </select>
                            <div style="float: right">
                                <button type="submit" style="margin-top: 15px" class="btn btn-primary">Search</button>
                            </div>
                            </div>
                           
                            
                            
                        </div>
                    </form>

                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th>Product Code</th>
                                <th>Customer Name</th>
                                <th>Email</th>
                                <th>Product Name</th>
                                <th>Warranty Start</th>
                                <th>Warranty End</th>
                                <th>Note</th>
                                <!--<th>Is Active</th>-->
                                <th>Extended Warranty Name</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="pkg" items="${packageWarrantyList}">
                                <tr>
                                    <td>${pkg.productCode}</td>
                                    <td>${pkg.customerName}</td>
                                    <td>${pkg.email}</td>
                                    <td>${pkg.productName}</td>
                                    <td>${pkg.warrantyStartDate1}</td>
                                    <td>${pkg.warrantyEndDate1}</td>
                                    <td>${pkg.note}</td>
<!--                                    <td>
                                        <c:choose>
                                            <c:when test="${pkg.active}">Yes</c:when>
                                            <c:otherwise>No</c:otherwise>
                                        </c:choose>
                                    </td>-->
                                    <td>${pkg.extendedWarrantyName}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/packageWarranty?action=edit&amp;packageWarrantyID=${pkg.packageWarrantyID}" class="btn btn-primary">Edit</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <jsp:include page="/includes/pagination.jsp" />

                </main>

                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>

    </body>
</html>


