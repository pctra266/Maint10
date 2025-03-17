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

        <style>
            main.content {
                padding: 10px;
                display: flex;
                flex-direction: column;
                gap: 20px;
            }

            /* ----------------------- */
            /* Phần trên: Sort, Search, nút */
            /* ----------------------- */
            .controls-section {
                background: #f4f4f4;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                display: flex;
                flex-direction: column;
                padding: 20px;
                gap: 15px;
            }

            /* Sắp xếp, tìm kiếm và các nút được căn chỉnh ngang nếu có đủ không gian */
            .controls-section .top-controls {
                display: flex;
                flex-wrap: wrap;
                gap: 15px;
                align-items: center;
                justify-content: center;
            }

            /* Style cho các dropdown, input và button bên trong phần điều khiển */
            .controls-section select,
            .controls-section input[type="text"],
            .controls-section input[type="number"],
            .controls-section button {
                padding: 8px;
                font-size: 14px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            .controls-section button {
                background-color: #007bff;
                color: #fff;
                border: none;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .controls-section button:hover {
                background-color: #0056b3;
            }

            /* ----------------------- */
            /* Phần dưới: Danh sách sản phẩm */
            /* ----------------------- */
            .table-section {
                background: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            }

            /* Style cho bảng */
            .table-section table {
                width: 100%;
                border-collapse: collapse;
            }

            .table-section th,
            .table-section td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #e0e0e0;
            }

            .table-section th {
                background-color: #f8f8f8;
                font-weight: 600;
            }

            .table-section tr:hover {
                background-color: #f1f1f1;
            }

            /* Style cho phân trang */
            .table-section .pagination {
                margin-top: 15px;
                display: flex;
                justify-content: center;
                gap: 5px;
            }

            .table-section .pagination a {
                padding: 8px 12px;
                border: 1px solid #ccc;
                border-radius: 4px;
                color: #333;
                text-decoration: none;
                transition: background-color 0.3s ease;
            }

            .table-section .pagination a.active {
                background-color: #007bff;
                color: #fff;
                border-color: #007bff;
            }

            .table-section .pagination a.disabled {
                pointer-events: none;
                opacity: 0.6;
            }

            /* ----------------------- */
            /* Các thông báo lỗi / thành công */
            /* ----------------------- */
            .alert {
                padding: 10px 15px;
                border-radius: 4px;
                margin-bottom: 10px;
                font-size: 14px;
            }

            .alert-danger {
                background-color: #f8d7da;
                color: #721c24;
            }

            .alert-success {
                background-color: #d4edda;
                color: #155724;
            }

            /* ----------------------- */
            /* Style cho nút Update và Delete */
            /* ----------------------- */
            .btn-update,
            .btn-delete {
                display: inline-block;
                padding: 8px 12px;
                border-radius: 4px;
                text-decoration: none;
                font-size: 14px;
                transition: opacity 0.3s ease;
            }

            .btn-update {
                background-color: #28a745;
                color: #fff;
            }

            .btn-delete {
                background-color: #dc3545;
                color: #fff;
            }

            .btn-update:hover,
            .btn-delete:hover {
                opacity: 0.9;
            }

            /* ----------------------- */
            /* Style cho carousel hiển thị ảnh sản phẩm */
            /* ----------------------- */
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
            }

            .prev-btn {
                left: -20px;
            }

            .next-btn {
                right: -20px;
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

                    <div class="controls-section">
                        <div style="display: flex; align-items: center; gap: 2%">
                            <div style="margin-left: 1%">
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

                            <div style="display: flex; align-items: center;">
                                <label style="margin-right: 2px" for="importExcel">Import Excel:</label>
                                <form action="importExcel" method="post" enctype="multipart/form-data">
                                    <input id="importExcel" type="file" name="productExcel" required>
                                    <button type="submit">Upload</button>
                                </form>
                            </div>                         
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
                        <div class="top-controls">
                            <button class="search" onclick="window.location.href = 'viewProduct'">
                                All Product
                            </button>

                            <a href="viewProduct?action=add" class="btn-update">
                                <i class="fas fa-add"></i> Add Product
                            </a>

                            <a href="listUnknown" class="btn-update">
                                <i class="fas fa-add"></i> External Repair Products
                            </a>                        
                        </div>
                    </div>

                    <div class="table-section">
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

                                        <td style="width:150px;">
                                            <c:if test="${not empty product.images}">
                                                <div class="image-carousel" id="carousel-${product.productId}" 
                                                     style="position: relative; width: 80px; height: 80px; margin: auto;">
                                                    <!-- Nút mũi tên trái -->
                                                    <button class="carousel-btn prev-btn" 
                                                            style="position: absolute; left: -20px; top: 50%; transform: translateY(-50%); background: transparent; border: none; font-size: 20px; cursor: pointer;"
                                                            onclick="prevImage('${product.productId}')">&#10094;</button>

                                                    <!-- Ảnh hiện tại (hiển thị ảnh đầu tiên) -->
                                                    <img id="carousel-img-${product.productId}" 
                                                         src="${pageContext.request.contextPath}/${fn:replace(product.images[0], '\\', '/')}" 
                                                         alt="Product Image" 
                                                         style="width: 80px; height: 80px; border-radius: 5px;">

                                                    <!-- Nút mũi tên phải -->
                                                    <button class="carousel-btn next-btn" 
                                                            style="position: absolute; right: -20px; top: 50%; transform: translateY(-50%); background: transparent; border: none; font-size: 20px; cursor: pointer;"
                                                            onclick="nextImage('${product.productId}')">&#10095;</button>
                                                </div>
                                            </c:if>
                                        </td>
                                        <td style="width: 14%">

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

                        <!-- Phần phân trang với nút First, Previous, Next, Last và nút nhảy trang -->
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

                                                // Xử lý dropdown và custom input cho Records per Page
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
                                                    url.searchParams.set("page", "1"); // Reset về trang đầu tiên
                                                    window.location.href = url;
                                                }

        </script>

        <script>
            // Lưu context path (nếu có)
            var contextPath = "${pageContext.request.contextPath}";
            // Đối tượng lưu danh sách ảnh của mỗi sản phẩm (đã replace '\' -> '/')
            var productImages = {};
            // Đối tượng lưu chỉ số ảnh hiện tại
            var currentIndex = {};

            <c:forEach var="product" items="${productList}">
            productImages["${product.productId}"] = [
                <c:forEach var="img" items="${product.images}" varStatus="status">
            "${fn:replace(img, '\\', '/')}"<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ];
            currentIndex["${product.productId}"] = 0;
            </c:forEach>
            // Hàm chuyển sang ảnh tiếp theo
            function nextImage(productId) {
                currentIndex[productId]++;
                if (currentIndex[productId] >= productImages[productId].length) {
                    currentIndex[productId] = 0;
                }
                document.getElementById('carousel-img-' + productId).src =
                        contextPath + '/' + productImages[productId][currentIndex[productId]];
            }
            // Hàm chuyển sang ảnh trước đó
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
