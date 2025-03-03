<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Purchase Product</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center">Purchase Product List</h1>

                    <!-- Form tìm kiếm -->
                    <form action="purchaseproduct" method="get" style="text-align: center; margin-bottom: 20px;">
                        <input type="text" name="productCode" placeholder="Search by Product Code" value="${productCode}" />
                        <input type="text" name="code" placeholder="Search by Code" value="${code}" />
                        <input type="text" name="productName" placeholder="Search by Product Name" value="${productName}" />
                        <input type="date" name="purchaseDate" placeholder="Search by Purchase Date" value="${purchaseDate}" />
                        <button type="submit">Search</button>

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
                    </form>

                    <!-- Bảng hiển thị sản phẩm -->
                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th>
                                    Product Code
                                    <a href="purchaseproduct?sort=ProductCode&order=asc&page-size=${size}&productCode=${productCode}&code=${code}&productName=${productName}&purchaseDate=${purchaseDate}">↑</a>
                                    <a href="purchaseproduct?sort=ProductCode&order=desc&page-size=${size}&productCode=${productCode}&code=${code}&productName=${productName}&purchaseDate=${purchaseDate}">↓</a>
                                </th>
                                <th>
                                    Code
                                    <a href="purchaseproduct?sort=Code&order=asc&page-size=${size}&productCode=${productCode}&code=${code}&productName=${productName}&purchaseDate=${purchaseDate}">↑</a>
                                    <a href="purchaseproduct?sort=Code&order=desc&page-size=${size}&productCode=${productCode}&code=${code}&productName=${productName}&purchaseDate=${purchaseDate}">↓</a>
                                </th>
                                <th>
                                    Product Name
                                    <a href="purchaseproduct?sort=ProductName&order=asc&page-size=${size}&productCode=${productCode}&code=${code}&productName=${productName}&purchaseDate=${purchaseDate}">↑</a>
                                    <a href="purchaseproduct?sort=ProductName&order=desc&page-size=${size}&productCode=${productCode}&code=${code}&productName=${productName}&purchaseDate=${purchaseDate}">↓</a>
                                </th>
                                <th>
                                    Warranty Period
                                    <a href="purchaseproduct?sort=WarrantyPeriod&order=asc&page-size=${size}&productCode=${productCode}&code=${code}&productName=${productName}&purchaseDate=${purchaseDate}">↑</a>
                                    <a href="purchaseproduct?sort=WarrantyPeriod&order=desc&page-size=${size}&productCode=${productCode}&code=${code}&productName=${productName}&purchaseDate=${purchaseDate}">↓</a>
                                </th>
                                <th>
                                    Purchase Date
                                    <a href="purchaseproduct?sort=PurchaseDate&order=asc&page-size=${size}&productCode=${productCode}&code=${code}&productName=${productName}&purchaseDate=${purchaseDate}">↑</a>
                                    <a href="purchaseproduct?sort=PurchaseDate&order=desc&page-size=${size}&productCode=${productCode}&code=${code}&productName=${productName}&purchaseDate=${purchaseDate}">↓</a>
                                </th>
                                <th>
                                    Action
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty listPurchaseProduct}">
                                    <tr><td colspan="5" style="text-align:center;">No purchase products found.</td></tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="productDetail" items="${listPurchaseProduct}">
                                        <tr>
                                            <td>${productDetail.productCode}</td>
                                            <td>${productDetail.code}</td>
                                            <td>${productDetail.productName}</td>
                                            <td>${productDetail.warrantyPeriod}</td>
                                            <td>
                                                <fmt:formatDate value="${productDetail.purchaseDate}" pattern="dd-MM-yyyy" />
                                            </td>

                                            <td>
                                                <form action="WarrantyCard/Add" method="post" enctype="multipart/form-data">
                                                    <input type="hidden" name="productCode" value="${productDetail.productCode}">
                                                    <button type="submit" class="btn btn-primary  d-flex align-items-center justify-content-center" ><i class="fas fa-add fa-4"></i> <span class="ms-2">Create warranty card</span> </button>                 
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
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
