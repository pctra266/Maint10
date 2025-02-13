<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product List</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <style>
            .gom{
                display: flex;
            }

            body {
                font-family: 'Inter', sans-serif;
                background-color: #f5f5f5;
                color: #333;
                margin: 0;
                padding: 0;
            }

            .wrapper {
                display: flex;
                min-height: 100vh;
            }

            .main {
                flex: 1;
                padding: 20px;
                background-color: #ffffff;
            }

            h1 {
                font-size: 2rem;
                color: #444;
                margin-bottom: 20px;
            }

            button, select, input {
                font-family: 'Inter', sans-serif;
                font-size: 1rem;
                padding: 10px;
                margin: 5px;
                border: 1px solid #ddd;
                border-radius: 4px;
                background-color: #fff;
                color: #333;
                transition: all 0.3s ease;
            }

            button:hover, select:hover, input:hover {
                background-color: #f4f4f4;
                cursor: pointer;
            }

            button.search {
                background-color: #4CAF50;
                color: white;
            }

            button.search:hover {
                background-color: #45a049;
            }

            button.add-product {
                background-color: #008CBA;
                color: white;
            }

            button.add-product:hover {
                background-color: #007bb5;
            }

            /* Table Styles */
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            table th, table td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }

            table th {
                background-color: #f2f2f2;
                color: #333;
                font-weight: bold;
            }

            table tr:hover {
                background-color: #f9f9f9;
            }

            table img {
                max-width: 80px;
                height: auto;
            }

            /* Action Buttons */
            a.btn-update, a.btn-delete {
                text-decoration: none;
                color: #fff;
                padding: 6px 12px;
                border-radius: 4px;
                display: inline-block;
                transition: background-color 0.3s ease;
            }

            a.btn-update {
                background-color: #4CAF50;
            }

            a.btn-update:hover {
                background-color: #45a049;
            }

            a.btn-delete {
                background-color: #f44336;
            }

            a.btn-delete:hover {
                background-color: #d32f2f;
            }

            /* Select and Input Styles */
            select, input[type="text"] {
                width: 200px;
            }

            select option {
                padding: 10px;
            }

            /* Pagination Styles */
            .pagination {
                display: flex;
                justify-content: center;
                margin-top: 20px;
            }

            .pagination a {
                padding: 10px 15px;
                margin: 0 5px;
                background-color: #f0f0f0;
                color: #333;
                border-radius: 4px;
                text-decoration: none;
                transition: background-color 0.3s ease;
            }

            .pagination a:hover {
                background-color: #e0e0e0;
            }

            .pagination a.active {
                background-color: #4CAF50;
                color: white;
            }

            .pagination a:focus {
                outline: none;
            }

            /* Responsive Design */
            @media (max-width: 768px) {
                .wrapper {
                    flex-direction: column;
                }

                .main {
                    padding: 10px;
                }

                table {
                    font-size: 0.9rem;
                }

                button, select, input {
                    width: 100%;
                    margin: 10px 0;
                }

                .pagination a {
                    font-size: 0.9rem;
                    padding: 8px 12px;
                }
            }

            @media (max-width: 480px) {
                h1 {
                    font-size: 1.5rem;
                }

                .pagination a {
                    font-size: 0.8rem;
                    padding: 6px 10px;
                }
            }
        </style>

    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">

                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger">
                            ${errorMessage}
                        </div>
                    </c:if>

                    <label for="recordsPerPage">Products per page:</label>
                    <input type="number" id="recordsPerPage" name="recordsPerPage" min="1" value="${recordsPerPage}">
                    <button onclick="updateRecordsPerPage()">Apply</button>


                    <div class="gom">
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

                            <input type="text" id="searchCode" name="searchCode" oninput="validateCode()" placeholder="Search by Code" value="${searchCode}">

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

                        <form action="viewProduct">
                            <button class="search" type="submit">All Product</button>
                        </form>

                        <form action="addP">
                            <button class="add-product" type="submit">Add Product</button>
                        </form>
                    </div>

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
                                        <a href="updateproduct?id=${product.productId}" class="btn-update">
                                            <i class="fas fa-edit"></i> Update
                                        </a>
                                        <a href="deleteproduct?id=${product.productId}" class="btn-delete" onclick="return confirm('Are you sure you want to delete this product?')">
                                            <i class="fas fa-trash-alt"></i> Delete
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- Pagination -->
                    <div class="pagination">
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <a href="viewProduct?searchCode=${searchCode}&searchName=${searchName}&brandId=${brandID}&type=${type}&sortQuantity=${sortQuantity}&sortWarranty=${sortWarranty}&page=${i}&recordsPerPage=${recordsPerPage}" 
                               class="${i == page ? 'active' : ''}">
                                ${i}
                            </a>
                        </c:forEach>
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

                        function validateCode() {
                            let input = document.getElementById("searchCode");
                            let value = input.value;
                            if (!/^[a-zA-Z0-9]*$/.test(value)) {
                                alert("Mã sản phẩm chỉ được chứa chữ cái và số, không chứa dấu cách hoặc ký tự đặc biệt.");
                                input.value = value.replace(/[^a-zA-Z0-9]/g, "");
                            }
                        }

                        function updateRecordsPerPage() {
                            let recordsInput = document.getElementById("recordsPerPage").value;
                            if (recordsInput < 1) {
                                alert("Number must be at least 1!");
                                return;
                            }
                            let url = new URL(window.location.href);
                            url.searchParams.set("recordsPerPage", recordsInput);
                            url.searchParams.set("page", "1"); // Quay về trang đầu tiên sau khi thay đổi
                            window.location.href = url;
                        }

                    </script>

                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
