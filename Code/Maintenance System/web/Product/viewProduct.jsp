<%-- 
    Document   : viewProduct
    Created on : Feb 21, 2025, 11:49:16 PM
    Author     : sonNH
--%>

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
            /* General Styles for Main Content */
            main.content {
                background-color: #f4f6f9;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                margin: 20px;
                font-family: 'Inter', sans-serif;
            }
            /* Alert Messages */
            .alert {
                padding: 15px;
                border-radius: 5px;
                margin-bottom: 20px;
            }
            .alert-danger {
                background-color: #ffcccc;
                color: #a94442;
                border: 1px solid #a94442;
            }
            .alert-success {
                background-color: #ccffcc;
                color: #2d862d;
                border: 1px solid #2d862d;
            }

            /* Form Controls */
            div.gom {
                display: flex;
                flex-wrap: wrap;
                gap: 15px;
                align-items: center;
                padding: 15px;
                border-radius: 8px;
                margin-bottom: 20px;
            }

            select, input[type="text"], input[type="number"] {
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 14px;
            }
            button {
                background-color: #3B7DDD;
                color: white;
                border: none;
                padding: 8px 12px;
                border-radius: 5px;
                cursor: pointer;
                transition: background 0.3s ease;
            }
            button:hover {
                background-color: #0056b3;
            }

            /* Table Styling */
            table {
                width: 100%;
                border-collapse: collapse;
                background: #fff;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0px 3px 10px rgba(0, 0, 0, 0.1);
                margin-top: 10px;
            }
            th, td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }
            th {
                background: #3B7DDD;
                color: white;
                font-weight: 600;
            }
            tr:hover {
                background: #f1f1f1;
            }

            /* Action Buttons */
            .btn-update, .btn-delete {
                text-decoration: none;
                padding: 6px 10px;
                border-radius: 5px;
                font-size: 14px;
                display: inline-block;
                transition: 0.3s;
            }
            .btn-update {
                background: #509E41;
                color: white;
            }
            .btn-update:hover {
                background: #218838;
                text-decoration: none;
                color: white;
            }
            .btn-delete {
                width: 85px;
                background: #EA2939;
                color: white;
            }
            .btn-delete:hover {
                background: #c82333;
                text-decoration: none;
                color: white;
            }
            /* Responsive Design */
            @media (max-width: 768px) {
                main.content {
                    padding: 10px;
                    margin: 10px;
                }
                div.gom {
                    flex-direction: column;
                }
                table {
                    font-size: 14px;
                }
                th, td {
                    padding: 8px;
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

                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success">
                            ${successMessage}
                        </div>
                    </c:if>

                    <div style="display: flex; align-items: center; gap: 2%">
                        <div style="margin-left: 1%">
                            <label for="recordsPerPage">Products per page:</label>
                            <input type="number" id="recordsPerPage" name="recordsPerPage" min="1" value="${recordsPerPage}">
                            <button onclick="updateRecordsPerPage()">Apply</button>
                        </div>

                        <div style="display: flex; align-items: center;">
                            <label style="margin-right: 2px" for="importExcel">Import Excel:</label>
                            <form action="importExcel" method="post" enctype="multipart/form-data">
                                <input style="" id="importExcel" type="file" name="productExcel" required>
                                <button type="submit">Upload</button>
                            </form>
                        </div>
                    </div>

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
                                <option value="">All Types</option>
                                <c:forEach var="t" items="${listType}">
                                    <option value="${t.productTypeId}"${ productTypeId == t.productTypeId ? 'selected' : ''}>
                                        ${t.typeName}
                                    </option>
                                </c:forEach>
                            </select>

                            <button class="search" type="submit">Search</button>
                        </form>

                        <div>
                            <a href="viewProduct?action=add" class="btn-update">
                                <i class="fas fa-add"></i> Add Product
                            </a>
                            <button class="search" onclick="window.location.href = 'viewProduct'">All Product</button>
                        </div>

                    </div>



                    <h1 style="text-align: center">Product List</h1>
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
                                    <td>${product.productTypeName}</td>
                                    <td>${product.quantity}</td>
                                    <td>${product.warrantyPeriod} months</td>
                                    <td>
                                        <c:if test="${not empty product.image}">
                                            <img src="${product.image}" alt="Product Image" width="80">
                                        </c:if>
                                    </td>
                                    <td style="width: 15%">
                                        <a href="viewProduct?action=update&id=${product.productId}" class="btn-update">
                                            <i class="fas fa-edit"></i> Update
                                        </a>
                                        <a href="viewProduct?action=delete&id=${product.productId}" class="btn-delete" onclick="return confirm('Are you sure you want to delete this product?')">
                                            <i class="fas fa-trash-alt"></i> Delete
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="pagination">
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <a href="viewProduct?searchCode=${searchCode}&searchName=${searchName}&brandId=${brandID}&type=${productTypeId}&sortQuantity=${sortQuantity}&sortWarranty=${sortWarranty}&page=${i}&recordsPerPage=${recordsPerPage}" 
                               class="${i == page ? 'active' : ''}">
                                ${i}
                            </a>
                        </c:forEach>
                    </div>             

                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>

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

                                            document.getElementById("importExcel").addEventListener("change", function () {
                                                let file = this.files[0]; // Lấy file được chọn
                                                if (file) {
                                                    let maxSize = 5 * 1024 * 1024; // 5MB
                                                    if (file.size > maxSize) {
                                                        alert("File không được vượt quá 5MB!");
                                                        this.value = ""; // Reset input file nếu file quá lớn
                                                    }
                                                }
                                            });

        </script>

    </body>
</html>
