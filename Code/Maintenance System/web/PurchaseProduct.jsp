<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Purchase Product</title>
        <link href="css/light.css" rel="stylesheet">
                <link rel="shortcut icon" href="img/icons/icon-48x48.png" />

        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            .search-box {
                margin-bottom: 15px;
                padding: 8px;
                width: 100%;
                max-width: 300px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 14px;
            }
            
            button {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 10px 15px;
                font-size: 14px;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s, transform 0.2s;
                display: inline-flex;
                align-items: center;
                gap: 5px;
            }

            button:hover {
                background-color: #0056b3;
                transform: scale(1.05);
            }

            button:active {
                background-color: #004494;
                transform: scale(0.98);
            }

            button i {
                font-size: 16px;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center">Purchase Product List</h1>

                    <form action="purchaseproduct" method="get" style="text-align: center; margin-bottom: 20px;">
                        <input type="text" name="productCode" placeholder="Search by Product Code" value="${productCode}" class="search-box" />
                        <input type="text" name="code" placeholder="Search by Code" value="${code}" class="search-box" />
                        <input type="text" name="productName" placeholder="Search by Product Name" value="${productName}" class="search-box" />
                        <input type="date" name="purchaseDate" placeholder="Search by Purchase Date" value="${purchaseDate}" class="search-box" />
                        <button type="submit">Search</button>
                    </form>

                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th>Product Code</th>
                                <th>Code</th>
                                <th>Product Name</th>
                                <th>Warranty Period</th>
                                <th>Purchase Date</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty listPurchaseProduct}">
                                    <tr><td colspan="6" style="text-align:center;">No purchase products found.</td></tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="productDetail" items="${listPurchaseProduct}">
                                        <tr>
                                            <td>${productDetail.productCode}</td>
                                            <td>${productDetail.code}</td>
                                            <td>${productDetail.productName}</td>
                                            <td>${productDetail.warrantyPeriod}</td>
                                            <td><fmt:formatDate value="${productDetail.purchaseDate}" pattern="dd-MM-yyyy" /></td>
                                            <td>
                                                <form action="WarrantyCard/Add" method="post" enctype="multipart/form-data">
                                                    <input type="hidden" name="productCode" value="${productDetail.productCode}">
                                                    <button type="submit"><i class="fas fa-add"></i> Create warranty card</button>
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
