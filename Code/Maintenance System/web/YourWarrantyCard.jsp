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
                    <form action="yourwarrantycard" method="get" style="text-align: center; margin-bottom: 20px;">
                        <input type="text" name="warrantyCardCode" placeholder="Search by Warranty Card Code" value="${searchWarrantyCardCode}" />
                        <input type="text" name="productName" placeholder="Search by ProductName" value="${searchProductName}" />
                        <input type="text" name="status" placeholder="Search by Status" value="${status}" />
                        <input type="date" name="createDate" placeholder="Search by Create Date" value="${searchCreateDate}" />
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
                                    Warranty Card Code
                                    <a href="yourwarrantycard?sort=warrantyCardCode&order=asc&page-size=${size}&warrantyCardCode=${warrantyCardCode}&productName=${productName}&status=${status}&createDate=${createDate}">↑</a>
                                    <a href="yourwarrantycard?sort=warrantyCardCode&order=desc&page-size=${size}&warrantyCardCode=${warrantyCardCode}&productName=${productName}&status=${status}&createDate=${createDate}">↓</a>
                                </th>
                                <th>
                                    Product
                                    <a href="yourwarrantycard?sort=productName&order=asc&page-size=${size}&warrantyCardCode=${warrantyCardCode}&productName=${productName}&status=${status}&createDate=${createDate}">↑</a>
                                    <a href="yourwarrantycard?sort=productName&order=desc&page-size=${size}&warrantyCardCode=${warrantyCardCode}&productName=${productName}&status=${status}&createDate=${createDate}">↓</a>
                                </th>
                                <th>
                                    Create Date
                                    <a href="yourwarrantycard?sort=createDate&order=asc&page-size=${size}&warrantyCardCode=${warrantyCardCode}&productName=${productName}&status=${status}&createDate=${createDate}">↑</a>
                                    <a href="yourwarrantycard?sort=createDate&order=desc&page-size=${size}&warrantyCardCode=${warrantyCardCode}&productName=${productName}&status=${status}&createDate=${createDate}">↓</a>
                                </th>
                                <th>
                                    Status
                                    <a href="yourwarrantycard?sort=status&order=asc&page-size=${size}&warrantyCardCode=${warrantyCardCode}&productName=${productName}&status=${status}&createDate=${createDate}">↑</a>
                                    <a href="yourwarrantycard?sort=status&order=desc&page-size=${size}&warrantyCardCode=${warrantyCardCode}&productName=${productName}&status=${status}&createDate=${createDate}">↓</a>
                                </th>
                                <th>
                                    Issue Description

                                </th>
                                <th>
                                    Action
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty listWarrantyCard}">
                                    <tr><td colspan="5" style="text-align:center;">No purchase products found.</td></tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="warrantyCard" items="${listWarrantyCard}">
                                        <tr>
                                            <td>${warrantyCard.warrantyCardCode}</td>
                                            <td>${warrantyCard.productName}</td>

                                            <td>
                                                <fmt:formatDate value="${warrantyCard.createdDate}" pattern="dd-MM-yyyy" />
                                            </td>
                                            <td style="color: ${warrantyCard.warrantyStatus eq 'waiting' ? 'red' : (warrantyCard.warrantyStatus eq 'completed' ? 'green' : 'black')}">
                                                ${warrantyCard.warrantyStatus}
                                            </td>
                                            <td>${warrantyCard.issueDescription}</td>
                                            <td><a href="yourWarrantyCardDetail?warrantyCardID=${warrantyCard.warrantyCardID}">Detail</a></td>
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
