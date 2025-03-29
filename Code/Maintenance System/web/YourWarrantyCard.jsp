<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Warranty Card</title>
        <link href="css/light.css" rel="stylesheet">

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

               <link rel="shortcut icon" href="img/icons/icon-48x48.png" />
 <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">


               <link rel="shortcut icon" href="img/icons/icon-48x48.png" />
 <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center">Your Warranty Card</h1>

                    <!-- Form tìm kiếm -->
                    <form action="yourwarrantycard" method="get" style="text-align: center; margin-bottom: 20px;">
                        <input type="text" name="warrantyCardCode" placeholder="Search by Warranty Card Code" value="${warrantyCardCode}" class="search-box"  />
                        <input type="text" name="productName" placeholder="Search by ProductName" value="${productName}"  class="search-box" />
                        <input type="text" name="warrantyStatus" placeholder="Search by Status" value="${warrantyStatus}" class="search-box" />
                        <input type="date" name="createdDate" placeholder="Search by Create Date" value="${createdDate}" class="search-box" />
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
                    <c:if test="${not empty createStatus}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${createStatus}</strong>
                            </div>
                        </div>
                    </c:if>   
                    <!-- Bảng hiển thị sản phẩm -->
                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th>
                                    Warranty Card Code
                                    <a href="yourwarrantycard?sort=warrantyCardCode&order=asc&page-size=${size}&warrantyCardCode=${warrantyCardCode}&productName=${productName}&warrantyStatus=${warrantyStatus}&createdDate=${createdDate}">↑</a>
                                    <a href="yourwarrantycard?sort=warrantyCardCode&order=desc&page-size=${size}&warrantyCardCode=${warrantyCardCode}&productName=${productName}&warrantyStatus=${warrantyStatus}&createdDate=${createdDate}">↓</a>
                                </th>
                                <th>
                                    Product
                                    <a href="yourwarrantycard?sort=productName&order=asc&page-size=${size}&warrantyCardCode=${warrantyCardCode}&productName=${productName}&warrantyStatus=${warrantyStatus}&createdDate=${createdDate}">↑</a>
                                    <a href="yourwarrantycard?sort=productName&order=desc&page-size=${size}&warrantyCardCode=${warrantyCardCode}&productName=${productName}&warrantyStatus=${warrantyStatus}&createdDate=${createdDate}">↓</a>
                                </th>
                                <th>
                                    Create Date
                                    <a href="yourwarrantycard?sort=createdDate&order=asc&page-size=${size}&warrantyCardCode=${warrantyCardCode}&productName=${productName}&warrantyStatus=${warrantyStatus}&createdDate=${createdDate}">↑</a>
                                    <a href="yourwarrantycard?sort=createdDate&order=desc&page-size=${size}&warrantyCardCode=${warrantyCardCode}&productName=${productName}&warrantyStatus=${warrantyStatus}&createdDate=${createdDate}">↓</a>
                                </th>
                                <th>
                                    Status
                                    <a href="yourwarrantycard?sort=warrantyStatus&order=asc&page-size=${size}&warrantyCardCode=${warrantyCardCode}&productName=${productName}&warrantyStatus=${warrantyStatus}&createdDate=${createdDate}">↑</a>
                                    <a href="yourwarrantycard?sort=warrantyStatus&order=desc&page-size=${size}&warrantyCardCode=${warrantyCardCode}&productName=${productName}&warrantyStatus=${warrantyStatus}&createdDate=${createdDate}">↓</a>
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
                                    <tr><td colspan="5" style="text-align:center;">No warranty card found.</td></tr>
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
                                            <td><a href="http://localhost:9999/MaintenanceSystem/WarrantyCard/Detail?ID=${warrantyCard.warrantyCardID}">Detail</a></td>
                                        </tr>


                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>

                    <jsp:include page="/includes/pagination.jsp" />  
                     <jsp:include page="/chatBox.jsp"/>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
