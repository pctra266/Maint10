<%-- 
    Document   : viewProduct
    Created on : Feb 21, 2025, 11:49:16 PM
    Author     : sonNH
--%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

        <!-- Chỉ thêm/điều chỉnh CSS cho phần main (bảng, nút Action) -->
        <style>
            /* Reset và cơ bản */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: 'Inter', sans-serif;
            }
            body {
                background-color: #f5f6fa;
            }
            .wrapper {
                display: flex;
                min-height: 100vh;
                width: 100%;
                background-color: #f5f6fa;
            }
            .main {
                flex: 1;
                display: flex;
                flex-direction: column;
                padding: 16px; /* Khoảng cách bên trong main */
            }
            .content {
                flex: 1;
                padding: 16px;
            }
            .alert {
                padding: 10px 15px;
                border-radius: 4px;
                margin-bottom: 16px;
                font-size: 14px;
            }
            .alert-danger {
                background-color: #ffdddd;
                color: #d60000;
            }
            .alert-success {
                background-color: #ddffdd;
                color: #006400;
            }

            /* KHU VỰC CHỨA TẤT CẢ CÁC NÚT & FORM: Search, Filter, Upload, Sort, ... */
            .controls-section {
                background-color: #fff;
                border-radius: 8px;
                padding: 16px;
                margin-bottom: 16px;

                display: flex;
                flex-wrap: wrap;
                gap: 16px;
                align-items: center;
                justify-content: space-between;
            }
            .controls-group {
                display: flex;
                flex-wrap: wrap;
                gap: 8px;
                align-items: center;
            }
            .controls-group label {
                font-size: 14px;
                color: #333;
            }
            .controls-group select,
            .controls-group input[type="text"],
            .controls-group input[type="number"],
            .controls-group input[type="file"] {
                padding: 6px 8px;
                font-size: 14px;
                border: 1px solid #ccc;
                border-radius: 4px;
                outline: none;
            }
            .controls-group button,
            .controls-group input[type="submit"] {
                background-color: #6F94C9;
                color: #fff;
                border: none;
                border-radius: 4px;
                padding: 6px 12px;
                font-size: 14px;
                cursor: pointer;
            }
            .controls-group button:hover,
            .controls-group input[type="submit"]:hover {
                background-color: #0056b3;
            }

            /* TABLE SECTION */
            .table-section {
                background-color: #fff;
                border-radius: 8px;
                padding: 10px;
            }
            .table-section h1 {
                text-align: center;
                margin-bottom: 16px;
                font-size: 24px;
                color: #333;
            }
            .table-section table {
                width: 100%;
                border-collapse: collapse;
                border-radius: 8px;
                overflow: hidden;
            }
            .table-section thead {
                background-color: #B2D8F8; /* Màu chủ đạo cho header */
            }
            .table-section thead th {
                text-align: left;
                padding: 12px;
                font-size: 14px;
                color: #333;
            }
            .table-section tbody td {
                padding: 12px;
                border-bottom: 1px solid #eee;
                font-size: 14px;
                color: #333;
                vertical-align: middle;
            }

            /* ACTION COLUMN */
            /* Bọc hai nút Update & Delete trong 1 container để canh dọc hoặc ngang */
            .action-buttons {
                display: flex;
                flex-direction: column; /* xếp dọc */
                gap: 8px;
                align-items: center;    /* canh giữa nếu muốn */
            }
            /* Nếu muốn xếp ngang, đổi flex-direction thành row */

            /* Nút Update: dùng màu chủ đạo #B2D8F8 */
            .btn-update {
                width: 93px;
                display: inline-flex;
                align-items: center;
                justify-content: center;
                gap: 4px;
                background-color: #B2D8F8; /* Màu xanh nhạt */
                color: #003049;           /* Màu chữ đậm hơn để tương phản */
                text-decoration: none;
                padding: 6px 12px;
                border-radius: 6px;
                font-size: 14px;
                font-weight: 500;
                border: 2px solid transparent;
                transition: all 0.2s ease;
            }

            .nut{
                display: inline-flex;
                align-items: center;
                justify-content: center;
                gap: 4px;
                background-color: #B2D8F8; /* Màu xanh nhạt */
                color: #003049;           /* Màu chữ đậm hơn để tương phản */
                text-decoration: none;
                padding: 6px 12px;
                border-radius: 6px;
                font-size: 14px;
                font-weight: 500;
                border: 2px solid transparent;
                transition: all 0.2s ease;
            }
            .nut:hover {
                background-color: #9cc7ee; /* Đậm hơn khi hover */
                border-color: #87bbec;
                color: #002537;
                text-decoration: none;
            }

            .btn-update:hover {
                background-color: #9cc7ee; /* Đậm hơn khi hover */
                border-color: #87bbec;
                color: #002537;
                text-decoration: none;
            }

            /* Nút Delete: dùng tông đỏ để cảnh báo */
            .btn-delete {
                width: 93px;
                display: inline-flex;
                align-items: center;
                justify-content: center;
                gap: 4px;
                background-color: #ff6b6b;
                color: #fff;
                text-decoration: none;
                padding: 6px 12px;
                border-radius: 6px;
                font-size: 14px;
                font-weight: 500;
                border: 2px solid transparent;
                transition: all 0.2s ease;
            }
            .btn-delete:hover {
                background-color: #fa5252; /* Đậm hơn khi hover */
                border-color: #B2D8F8;
                text-decoration: none;

            }

            /* PAGINATION */
            .pagination {
                margin-top: 16px;
                display: flex;
                gap: 8px;
                justify-content: center;
                align-items: center;
                flex-wrap: wrap;
            }
            .pagination a {
                padding: 6px 12px;
                border: 1px solid #ccc;
                border-radius: 4px;
                color: #333;
                text-decoration: none;
                font-size: 14px;
            }
            .pagination a:hover {
                background-color: #eee;
            }
            .pagination a.disabled {
                pointer-events: none;
                opacity: 0.6;
            }
            .pagination a.active {
                background-color: #007bff;
                color: #fff;
                border-color: #007bff;
            }

            /* CAROUSEL ẢNH SẢN PHẨM */
            .image-carousel {
                position: relative;
                width: 80px;
                height: 80px;
                margin: auto;
            }
            .carousel-btn {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                background: transparent;
                border: none;
                font-size: 20px;
                cursor: pointer;
                color: #333;
            }
            .carousel-btn:hover {
                color: #007bff;
            }
            .prev-btn {
                left: -20px;
            }
            .next-btn {
                right: -20px;
            }
            .image-carousel img {
                width: 80px;
                height: 80px;
                border-radius: 5px;
                object-fit: cover;
            }
        </style>
    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />

                <main class="content">
                    <!-- Hiển thị thông báo lỗi hoặc thành công (nếu có) -->
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

                    <!-- Tất cả nút & form gom vào controls-section -->
                    <div class="controls-section">

                        <!-- NHÓM 1: FORM SEARCH, FILTER BRAND/TYPE -->
                        <div class="controls-group">
                            <form method="get" action="viewProduct" style="display: flex; gap: 8px; flex-wrap: wrap;">
                                <input type="text" id="searchCode" name="searchCode" oninput="validateCode()" placeholder="Search by Code" value="${searchCode}">
                                <input type="text" name="searchName" placeholder="Search by Name" value="${searchName}">

                                <select name="brandId">
                                    <option value="">Select Brand</option>
                                    <c:forEach var="p" items="${listBrand}">
                                        <option value="${p.brandId}" ${brandID == p.brandId ? 'selected' : ''}>
                                            ${p.brandName}
                                        </option>
                                    </c:forEach>
                                </select>

                                <select name="type">
                                    <option value="">All Types</option>
                                    <c:forEach var="t" items="${listType}">
                                        <option value="${t.productTypeId}" ${productTypeId == t.productTypeId ? 'selected' : ''}>
                                            ${t.typeName}
                                        </option>
                                    </c:forEach>
                                </select>

                                <button class="search" type="submit">Search</button>
                            </form>
                        </div>

                        <!-- NHÓM 2: SORT QUANTITY, SORT WARRANTY -->
                        <div class="controls-group">
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
                        </div>

                        <!-- NHÓM 3: IMPORT EXCEL -->
                        <div class="controls-group">
                            <form action="importExcel" method="post" enctype="multipart/form-data" style="display: flex; gap: 8px; align-items: center;">
                                <label for="importExcel" style="white-space: nowrap;">Import Excel:</label>
                                <input id="importExcel" type="file" name="productExcel" required>
                                <button type="submit">Upload</button>
                            </form>
                        </div>

                        <!-- NHÓM 4: CHỌN SỐ LƯỢNG SP HIỂN THỊ + CUSTOM -->
                        <div class="controls-group">
                            <label for="recordsPerPageSelect">Products per page:</label>
                            <select id="recordsPerPageSelect" onchange="toggleCustomRecords()">
                                <option value="5" ${recordsPerPage == 5 ? 'selected' : ''}>5</option>
                                <option value="10" ${recordsPerPage == 10 ? 'selected' : ''}>10</option>
                                <option value="15" ${recordsPerPage == 15 ? 'selected' : ''}>15</option>
                                <option value="20" ${recordsPerPage == 20 ? 'selected' : ''}>20</option>
                                <option value="25" ${recordsPerPage == 25 ? 'selected' : ''}>25</option>
                                <option value="custom" ${recordsPerPage != 5 && recordsPerPage != 10 && recordsPerPage != 15 && recordsPerPage != 20 && recordsPerPage != 25 ? 'selected' : ''}>Custom</option>
                            </select>
                            <input type="number" id="customRecordsPerPage" name="customRecordsPerPage" min="1" 
                                   style="${recordsPerPage != 5 && recordsPerPage != 10 && recordsPerPage != 15 && recordsPerPage != 20 && recordsPerPage != 25 ? 'display: inline-block;' : 'display: none;'}"
                                   placeholder="Custom value"
                                   value="${recordsPerPage != 5 && recordsPerPage != 10 && recordsPerPage != 15 && recordsPerPage != 20 && recordsPerPage != 25 ? recordsPerPage : ''}">
                            <button onclick="applyRecordsPerPage()">Apply</button>
                        </div>

                        <div class="controls-group">
                            <button class="search" onclick="window.location.href = 'viewProduct'">
                                <i class="fas fa-list"></i> All Product
                            </button>

                            <a href="viewProduct?action=add" class="nut">
                                <i class="fas fa-plus-square"></i> Add Product
                            </a>

                            <a href="listUnknown" class="nut">
                                <i class="fas fa-box-open"></i> External Product
                            </a>
                        </div>


                    </div>

            </div>


            <div class="table-section">
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
                                <td>${product.productTypeName}</td>
                                <td>${product.quantity}</td>
                                <td>${product.warrantyPeriod} months</td>
                                <td style="width:150px;">
                                    <c:if test="${not empty product.images}">
                                        <div class="image-carousel" id="carousel-${product.productId}">
                                            <!-- Nút mũi tên trái -->
                                            <button class="carousel-btn prev-btn"
                                                    onclick="prevImage('${product.productId}')">&#10094;</button>

                                            <!-- Ảnh hiện tại -->
                                            <img id="carousel-img-${product.productId}" 
                                                 src="${pageContext.request.contextPath}/${fn:replace(product.images[0], '\\', '/')}" 
                                                 alt="Product Image">

                                            <!-- Nút mũi tên phải -->
                                            <button class="carousel-btn next-btn"
                                                    onclick="nextImage('${product.productId}')">&#10095;</button>
                                        </div>
                                    </c:if>
                                </td>
                                <!-- Cột Action -->
                                <td style="width: 14%;">
                                    <!-- Bọc 2 nút vào .action-buttons -->
                                    <div class="action-buttons">
                                        <a href="viewProduct?action=update&id=${product.productId}" class="btn-update">
                                            <i class="fas fa-edit"></i> Update
                                        </a>
                                        <a href="viewProduct?action=delete&id=${product.productId}" class="btn-delete" onclick="return confirm('Are you sure you want to delete this product?')">
                                            <i class="fas fa-trash-alt"></i> Delete
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Phần phân trang -->
                <div class="pagination">
                    <a href="viewProduct?searchCode=${searchCode}&searchName=${searchName}&brandId=${brandID}&type=${productTypeId}&sortQuantity=${sortQuantity}&sortWarranty=${sortWarranty}&page=1&recordsPerPage=${recordsPerPage}"
                       class="${currentPage == 1 ? 'disabled' : ''}">First</a>

                    <a href="viewProduct?searchCode=${searchCode}&searchName=${searchName}&brandId=${brandID}&type=${productTypeId}&sortQuantity=${sortQuantity}&sortWarranty=${sortWarranty}&page=${currentPage > 1 ? currentPage - 1 : 1}&recordsPerPage=${recordsPerPage}"
                       class="${currentPage == 1 ? 'disabled' : ''}">Previous</a>

                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <a href="viewProduct?searchCode=${searchCode}&searchName=${searchName}&brandId=${brandID}&type=${productTypeId}&sortQuantity=${sortQuantity}&sortWarranty=${sortWarranty}&page=${i}&recordsPerPage=${recordsPerPage}"
                           class="${i == currentPage ? 'active' : ''}">
                            ${i}
                        </a>
                    </c:forEach>

                    <a href="viewProduct?searchCode=${searchCode}&searchName=${searchName}&brandId=${brandID}&type=${productTypeId}&sortQuantity=${sortQuantity}&sortWarranty=${sortWarranty}&page=${currentPage < totalPages ? currentPage + 1 : totalPages}&recordsPerPage=${recordsPerPage}"
                       class="${currentPage == totalPages ? 'disabled' : ''}">Next</a>

                    <a href="viewProduct?searchCode=${searchCode}&searchName=${searchName}&brandId=${brandID}&type=${productTypeId}&sortQuantity=${sortQuantity}&sortWarranty=${sortWarranty}&page=${totalPages}&recordsPerPage=${recordsPerPage}"
                       class="${currentPage == totalPages ? 'disabled' : ''}">Last</a>
                </div>
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
                                                // Xoá sortWarranty để tránh xung đột
                                                url.searchParams.delete("sortWarranty");
                                                window.location.href = url;
                                            });

                                            document.getElementById("sortWarranty").addEventListener("change", function () {
                                                let url = new URL(window.location.href);
                                                url.searchParams.set("sortWarranty", this.value);
                                                // Xoá sortQuantity để tránh xung đột
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

                                            document.getElementById("importExcel").addEventListener("change", function () {
                                                let file = this.files[0];
                                                if (file) {
                                                    let maxSize = 5 * 1024 * 1024; // 5MB
                                                    if (file.size > maxSize) {
                                                        alert("File không được vượt quá 5MB!");
                                                        this.value = "";
                                                    }
                                                }
                                            });

                                            function toggleCustomRecords() {
                                                var select = document.getElementById("recordsPerPageSelect");
                                                var customInput = document.getElementById("customRecordsPerPage");
                                                if (select.value === "custom") {
                                                    customInput.style.display = "inline-block";
                                                } else {
                                                    customInput.style.display = "none";
                                                }
                                            }

                                            function applyRecordsPerPage() {
                                                var select = document.getElementById("recordsPerPageSelect");
                                                var recordsPerPage;
                                                if (select.value === "custom") {
                                                    recordsPerPage = document.getElementById("customRecordsPerPage").value;
                                                    if (recordsPerPage < 1) {
                                                        alert("Number must be at least 1!");
                                                        return;
                                                    }
                                                } else {
                                                    recordsPerPage = select.value;
                                                }
                                                let url = new URL(window.location.href);
                                                url.searchParams.set("recordsPerPage", recordsPerPage);
                                                url.searchParams.set("page", "1");
                                                window.location.href = url;
                                            }
</script>

<!-- Carousel chuyển ảnh -->
<script>
            var contextPath = "${pageContext.request.contextPath}";
            var productImages = {};
            var currentIndex = {};

    <c:forEach var="product" items="${productList}">
            productImages["${product.productId}"] = [
        <c:forEach var="img" items="${product.images}" varStatus="status">
            "${fn:replace(img, '\\', '/')}"<c:if test="${!status.last}">,</c:if>
        </c:forEach>
            ];
            currentIndex["${product.productId}"] = 0;
    </c:forEach>

            function nextImage(productId) {
                currentIndex[productId]++;
                if (currentIndex[productId] >= productImages[productId].length) {
                    currentIndex[productId] = 0;
                }
                document.getElementById('carousel-img-' + productId).src =
                        contextPath + '/' + productImages[productId][currentIndex[productId]];
            }

            function prevImage(productId) {
                currentIndex[productId]--;
                if (currentIndex[productId] < 0) {
                    currentIndex[productId] = productImages[productId].length - 1;
                }
                document.getElementById('carousel-img-' + productId).src =
                        contextPath + '/' + productImages[productId][currentIndex[productId]];
            }
</script>
</body>
</html>
