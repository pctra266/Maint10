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
                         <input type="hidden" name="page" value="${pagination.currentPage}" />
                        <input type="hidden" name="sort" value="${pagination.sort}" />
                        <input type="hidden" name="order" value="${pagination.order}" />
                        <input type="hidden" name="action" value="view">
                        <div class="row"  style="justify-content: space-between">
                            <div style="margin-top: 15px;width: 500px"  class="col-sm-6 col-md-6" >
                                <input style="margin-top: 15px"  class="form-control" type="search" name="searchProductCode" placeholder="Product Code" value="${searchProductCode}">
                                <input style="margin-top: 15px"  class="form-control" type="search" name="searchEmail" placeholder="Email" value="${searchEmail}">
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
                                <select style="margin-top: 15px" class="form-select" name="filterStatusPackage" onchange="this.form.submit()">
                                    <option value="">Package Status</option>
                                    <option ${(filterStatusPackage=='active')?"selected":""} value="active">Active warranty</option>
                                    <option ${(filterStatusPackage=='deactive')?"selected":""} value="deactive">Expired warranty</option>
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
                                <th>Product Name</th>
                                <th>Customer Name</th>
                                <th>Email</th>
                                
                                <th>Warranty Start</th>
                                <th>Warranty End</th>
                                <th>Note</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="pkg" items="${packageWarrantyList}">
                                <tr>
                                    <td>${pkg.productCode}</td>
                                    <td>${pkg.productName}</td>
                                    <td>${pkg.customerName}</td>
                                    <td>${pkg.email}</td>
                                    
                                    <td>${pkg.warrantyStartDate1}</td>
                                    <td>${pkg.warrantyEndDate1}</td>
                                    <td>${pkg.note}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${pkg.active}">Active warranty</c:when>
                                            <c:otherwise>Expired warranty</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/packageWarranty?action=edit&amp;packageWarrantyID=${pkg.packageWarrantyID}" class="btn btn-primary">Package Warranty</a>
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


