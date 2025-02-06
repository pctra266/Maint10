<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product List</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

        <style>



        </style>
    </head>


    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">

                    <form action="viewProduct">
                        <button class="search" type="submit">All Product</button>
                    </form>

                    <form action="addProduct">
                        <button class="add-product" type="submit">Add Product</button>
                    </form>

                    <select id="sortQuantity">
                        <option value="">Sort by Quantity</option>
                        <option value="asc" ${sortQuantity == 'asc' ? 'selected' : ''}>Ascending</option>
                        <option value="desc" ${sortQuantity == 'desc' ? 'selected' : ''}>Descending</option>
                    </select>

                    <select id="sortWarranty">
                        <option value="">Sort by Warranty</option>
                        <option value="asc" ${sortWarranty == 'asc' ? 'selected' : ''}>Ascending</option>
                        <option value="desc" ${sortWarranty == 'desc' ? 'selected' : ''}>Descending</option>
                    </select>
                    <form method="get" action="viewProduct">
                        <input type="text" name="searchCode" placeholder="Search by Code" value="${searchCode}">
                        <input type="text" name="searchName" placeholder="Search by Name" value="${searchName}">

                        <select name="brandId">
                            <option value="">Select Brand</option>
                            <c:forEach var="p" items="${listBrand}">
                                <option value="${p.brandId}"${brandID == p.brandId ? 'selected' : ''}>
                                    ${p.brandName}
                                </option>
                            </c:forEach>
                        </select>

                        <select name="type">
                            <option value="all">All Types</option>
                            <c:forEach var="t" items="${listType}">
                                <option value="${t}" ${type == t ? 'selected' : ''}>${t}</option>
                            </c:forEach>
                        </select>
                        <button class="search" type="submit">Search</button>
                    </form>

                    <h1>Product List</h1>
                    <table>
                        <thead>
                            <tr>
                                <th>Code</th>
                                <th>Name</th>
                                <th>Brand</th>
                                <th>Type</th>
                                <th>Quantity</th>
                                <th>Warranty</th>
                                <th>Image</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="product" items="${productList}">
                                <tr>
                                    <td>${product.code}</td>
                                    <td>${product.productName}</td>
                                    <td>${product.brandName}</td>
                                    <td>${product.type}</td>
                                    <td>${product.quantity}</td>
                                    <td>${product.warrantyPeriod} months</td>
                                    <td>
                                        <c:if test="${not empty product.image}">
                                            <img src="${product.image}" alt="Product Image" width="80">
                                        </c:if>
                                    </td>
                                    <td>
                                        <a href="updateproduct?id=${product.productId}" class="btn-update">Update</a>
                                        <a href="deleteproduct?id=${product.productId}" class="btn-delete" onclick="return confirm('Are you sure you want to delete this product?')">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- Pagination -->
                    <div class="pagination">
                        <c:if test="${page > 1}">
                            <a href="viewProduct?searchCode=${searchCode}&searchName=${searchName}&brandId=${brandID}&type=${type}&sortQuantity=${sortQuantity}&sortWarranty=${sortWarranty}&page=1">
                                First
                            </a>
                            <a href="viewProduct?searchCode=${searchCode}&searchName=${searchName}&brandId=${brandID}&type=${type}&sortQuantity=${sortQuantity}&sortWarranty=${sortWarranty}&page=${page - 1}">
                                Previous
                            </a>
                        </c:if>
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <a href="viewProduct?searchCode=${searchCode}&searchName=${searchName}&brandId=${brandID}&type=${type}&sortQuantity=${sortQuantity}&sortWarranty=${sortWarranty}&page=${i}" 
                               class="${i == page ? 'active' : ''}">
                                ${i}
                            </a>
                        </c:forEach>
                        <c:if test="${page < totalPages}">
                            <a href="viewProduct?searchCode=${searchCode}&searchName=${searchName}&brandId=${brandID}&type=${type}&sortQuantity=${sortQuantity}&sortWarranty=${sortWarranty}&page=${page + 1}">
                                Next
                            </a>
                            <a href="viewProduct?searchCode=${searchCode}&searchName=${searchName}&brandId=${brandID}&type=${type}&sortQuantity=${sortQuantity}&sortWarranty=${sortWarranty}&page=${totalPages}">
                                Last
                            </a>
                        </c:if>

                    </div>


                    <script>
                        document.getElementById("sortQuantity").addEventListener("change", function () {
                            let url = new URL(window.location.href);
                            url.searchParams.set("sortQuantity", this.value);
                            url.searchParams.delete("sortWarranty");
                            window.location.href = url;
                        });

                        document.getElementById("sortWarranty").addEventListener("change", function () {
                            let url = new URL(window.location.href);
                            url.searchParams.set("sortWarranty", this.value);
                            url.searchParams.delete("sortQuantity");
                            window.location.href = url;
                        });
                    </script>

                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
