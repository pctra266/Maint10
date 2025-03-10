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
            /* CSS cho nội dung trong main */
            main.content {
                background-color: #ffffff;
                font-family: 'Inter', sans-serif;
                color: #333;
                border-radius: 10px;
                margin: 10px;
            }
            /* Tiêu đề */
            main.content h2 {
                font-size: 32px;
                font-weight: 600;
                color: #2c3e50;
                margin-bottom: 30px;
                border-bottom: 2px solid #e1e1e1;
                padding-bottom: 10px;
            }
            /* Form */
            main.content form {
                display: flex;
                flex-wrap: wrap;
                gap: 15px;
                align-items: center;
                margin-bottom: 30px;
            }

            main.content form input[type="text"],
            main.content form input[type="date"] {
                flex: 1;
                min-width: 180px;
                padding: 12px 15px;
                font-size: 16px;
                border: 1px solid #ccc;
                border-radius: 6px;
                transition: border-color 0.3s ease;
            }

            main.content form input[type="text"]:focus,
            main.content form input[type="date"]:focus {
                border-color: #3498db;
                outline: none;
            }

            main.content form span {
                font-size: 14px;
                color: #e74c3c;
                margin-top: 5px;
            }

            /* Nút */
            main.content button {
                background: linear-gradient(135deg, #7faaff, #8bc1ff);
                color: #fff;
                border: none;
                padding: 12px 20px;
                font-size: 16px;
                border-radius: 6px;
                cursor: pointer;
                transition: background 0.3s ease;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                margin-right: 10px;
            }

            main.content button:hover {
                background: linear-gradient(135deg, #0d47a1, #002171);
            }

            /* Nút riêng cho "search" */
            main.content button.search {
                background: linear-gradient(135deg, #7faaff, #8bc1ff);
            }

            main.content button.search:hover {
                background: linear-gradient(135deg, #0d47a1, #002171);
            }

            /* Bảng */
            main.content table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 30px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            }

            main.content table th,
            main.content table td {
                padding: 15px 20px;
                text-align: left;
                border-bottom: 1px solid #e0e0e0;
            }

            main.content table th {
                background-color: #f7f7f7;
                font-weight: 600;
                color: #2c3e50;
            }

            main.content table tr:nth-child(even) {
                background-color: #fafafa;
            }

            /* CSS cho phân trang */
            .pagination {
                display: flex;
                justify-content: center;
                align-items: center;
                margin: 30px 0;
            }

            .pagination a {
                margin: 0 8px;
                padding: 10px 15px;
                font-size: 16px;
                color: #333;
                background-color: #ffffff;
                border: 1px solid #ccc;
                border-radius: 6px;
                text-decoration: none;
                transition: background-color 0.3s ease, color 0.3s ease, transform 0.3s ease, box-shadow 0.3s ease;
            }

            .pagination a:hover {
                background-color: #007bff;
                color: #fff;
                transform: translateY(-3px);
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            }

            .pagination a.active {
                background: linear-gradient(135deg, #007bff, #00c6ff);
                color: #fff;
                border-color: #007bff;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            }

        </style>
    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h2>List of Unknown Products</h2>

                    <!-- Form tìm kiếm -->
                    <form action="listUnknown" method="get">
                        <input type="text" id="productCode" name="productCode" 
                               placeholder="Product Code" 
                               value="${param.productCode}" 
                               oninput="validateProductCode(this)">
                        <span id="productCodeError" style="color: red;"></span>

                        <input type="text" id="productName" name="productName" 
                               placeholder="Product Name" 
                               value="${param.productName}" 
                               oninput="validateProductName(this)">
                        <span id="productNameError" style="color: red;"></span>

                        <input type="text" id="description" name="description" 
                               placeholder="Description" 
                               value="${param.description}" 
                               oninput="validateDescription(this)">
                        <span id="descriptionError" style="color: red; font-size: 12px; margin-left: 5px;"></span>

                        <input type="date" name="receivedDate" value="${param.receivedDate}">

                        <input type="text" id="customerName" name="customerName" 
                               placeholder="Customer Name"
                               value="${param.customerName}" 
                               oninput="validateCustomerName(this)">
                        <span id="customerNameError" style="color: red; font-size: 12px; margin-left: 5px;"></span>

                        <input type="text" id="phone" name="phone" 
                               placeholder="Phone"
                               value="${param.phone}" 
                               oninput="validatePhone(this)">
                        <span id="phoneError" style="color: red; font-size: 12px; margin-left: 5px;"></span>

                        <!-- Giữ lại page và pageSize (nếu có) -->
                        <input type="hidden" name="page" value="${currentPage}" />
                        <input type="hidden" name="pageSize" value="${pageSize}" />

                        <button type="submit">Search</button>
                    </form>

                    <button onclick="location.href = 'addUnknown'">Add Unknown Product</button>
                    <button class="search" onclick="window.location.href = 'listUnknown'">
                        All Product
                    </button>

                    <!-- Form chọn số phần tử trên mỗi trang -->
                    <form action="listUnknown" method="get" class="page-size-form">
                        <!-- Giữ lại các tham số tìm kiếm -->
                        <input type="hidden" name="productCode" value="${param.productCode}" />
                        <input type="hidden" name="productName" value="${param.productName}" />
                        <input type="hidden" name="description" value="${param.description}" />
                        <input type="hidden" name="receivedDate" value="${param.receivedDate}" />
                        <input type="hidden" name="customerName" value="${param.customerName}" />
                        <input type="hidden" name="phone" value="${param.phone}" />
                        <input type="hidden" name="page" value="1" />

                        <label for="pageSizeSelect">Products per page:</label>
                        <select id="pageSizeSelect" name="pageSize" onchange="toggleCustomInput(this.value)">
                            <option value="5"  <c:if test="${pageSize == 5}">selected</c:if>>5</option>
                            <option value="10" <c:if test="${pageSize == 10}">selected</c:if>>10</option>
                            <option value="15" <c:if test="${pageSize == 15}">selected</c:if>>15</option>
                            <option value="20" <c:if test="${pageSize == 20}">selected</c:if>>20</option>
                            <option value="25" <c:if test="${pageSize == 25}">selected</c:if>>25</option>
                                <option value="custom"
                                <c:if test="${pageSize != 5 && pageSize != 10 && pageSize != 15 && pageSize != 20 && pageSize != 25}">
                                    selected
                                </c:if>
                                >Custom</option>
                        </select>

                        <!-- Ô nhập customPageSize, chỉ hiển thị khi dropdown = custom -->
                        <input type="number" name="customPageSize" id="customPageSizeInput" 
                               min="1"
                               value="<c:out value='${pageSize != 5 && pageSize != 10 && pageSize != 15 && pageSize != 20 && pageSize != 25 ? pageSize : ""}'/>"
                               style="display: none;" />

                        <button type="submit">Apply</button>
                    </form>

                    <!-- Bảng danh sách sản phẩm -->
                    <table border="1">
                        <thead>
                            <tr>
                                <th>Product Code</th>
                                <th>Product Name</th>
                                <th>Description</th>
                                <th>Received Date</th>
                                <th>Customer Name</th>
                                <th>Customer Phone</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="product" items="${listUnknownProduct}">
                                <tr>
                                    <td>${product.productCode}</td>
                                    <td>${product.productName}</td>
                                    <td>${product.description}</td>
                                    <td>${product.receivedDate}</td>
                                    <td>${product.customerName}</td>
                                    <td>${product.customerPhone}</td>
                                    <td>
                                        <form action="listUnknown" method="post">
                                            <input type="hidden" name="productId" value="${product.unknownProductId}">
                                            <input type="hidden" name="customerId" value="${product.customerId}">
                                            <input type="hidden" name="type" value="display">
                                            <button type="submit">Create a Repair Request</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- Phân trang sử dụng c:url để tránh khoảng trắng -->
                    <div class="pagination">
                        <c:if test="${totalPages > 1}">
                            <!-- First -->
                            <c:url var="firstUrl" value="listUnknown">
                                <c:param name="page" value="1" />
                                <c:param name="pageSize" value="${pageSize}" />
                                <c:param name="productCode" value="${param.productCode}" />
                                <c:param name="productName" value="${param.productName}" />
                                <c:param name="description" value="${param.description}" />
                                <c:param name="receivedDate" value="${param.receivedDate}" />
                                <c:param name="customerName" value="${param.customerName}" />
                                <c:param name="phone" value="${param.phone}" />
                            </c:url>
                            <a href="${firstUrl}">First</a>

                            <!-- Previous -->
                            <c:url var="prevUrl" value="listUnknown">
                                <c:param name="page" value="${currentPage - 1 <= 0 ? 1 : currentPage - 1}" />
                                <c:param name="pageSize" value="${pageSize}" />
                                <c:param name="productCode" value="${param.productCode}" />
                                <c:param name="productName" value="${param.productName}" />
                                <c:param name="description" value="${param.description}" />
                                <c:param name="receivedDate" value="${param.receivedDate}" />
                                <c:param name="customerName" value="${param.customerName}" />
                                <c:param name="phone" value="${param.phone}" />
                            </c:url>
                            <a href="${prevUrl}">Previous</a>

                            <!-- Các số trang -->
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <c:url var="pageUrl" value="listUnknown">
                                    <c:param name="page" value="${i}" />
                                    <c:param name="pageSize" value="${pageSize}" />
                                    <c:param name="productCode" value="${param.productCode}" />
                                    <c:param name="productName" value="${param.productName}" />
                                    <c:param name="description" value="${param.description}" />
                                    <c:param name="receivedDate" value="${param.receivedDate}" />
                                    <c:param name="customerName" value="${param.customerName}" />
                                    <c:param name="phone" value="${param.phone}" />
                                </c:url>
                                <a href="${pageUrl}" class="${currentPage == i ? 'active' : ''}">${i}</a>
                            </c:forEach>

                            <!-- Next -->
                            <c:url var="nextUrl" value="listUnknown">
                                <c:param name="page" value="${currentPage + 1 > totalPages ? totalPages : currentPage + 1}" />
                                <c:param name="pageSize" value="${pageSize}" />
                                <c:param name="productCode" value="${param.productCode}" />
                                <c:param name="productName" value="${param.productName}" />
                                <c:param name="description" value="${param.description}" />
                                <c:param name="receivedDate" value="${param.receivedDate}" />
                                <c:param name="customerName" value="${param.customerName}" />
                                <c:param name="phone" value="${param.phone}" />
                            </c:url>
                            <a href="${nextUrl}">Next</a>

                            <!-- Last -->
                            <c:url var="lastUrl" value="listUnknown">
                                <c:param name="page" value="${totalPages}" />
                                <c:param name="pageSize" value="${pageSize}" />
                                <c:param name="productCode" value="${param.productCode}" />
                                <c:param name="productName" value="${param.productName}" />
                                <c:param name="description" value="${param.description}" />
                                <c:param name="receivedDate" value="${param.receivedDate}" />
                                <c:param name="customerName" value="${param.customerName}" />
                                <c:param name="phone" value="${param.phone}" />
                            </c:url>
                            <a href="${lastUrl}">Last</a>
                        </c:if>
                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>

        <script src="js/app.js"></script>

        <script>
                            function validateProductCode(input) {
                                let regex = /^[a-zA-Z0-9]*$/; // Chỉ cho phép chữ cái và số
                                let errorMessage = document.getElementById("productCodeError");

                                if (!regex.test(input.value)) {
                                    input.value = input.value.replace(/[^a-zA-Z0-9]/g, ""); // Xóa ký tự không hợp lệ
                                    errorMessage.textContent = "Product Code chỉ được chứa chữ cái và số!";
                                } else {
                                    errorMessage.textContent = ""; // Xóa thông báo lỗi nếu hợp lệ
                                }
                            }
        </script>
        <script>
            function validateProductName(input) {
                let errorMessage = document.getElementById("customerNameError");

                // Lấy giá trị nhập vào
                let value = input.value;

                // Nếu input rỗng, xóa lỗi
                if (value.trim() === "") {
                    errorMessage.textContent = value.length > 0 ? "Không được nhập toàn dấu cách!" : "";
                    return;
                }

                // Kiểm tra ký tự hợp lệ: Chỉ cho phép chữ, số và khoảng trắng giữa các từ
                let regex = /^[a-zA-Z0-9]+( [a-zA-Z0-9]+)*$/;
                if (!regex.test(value)) {
                    errorMessage.textContent = "Chỉ được nhập chữ, số và 1 dấu cách giữa các từ!";
                } else {
                    errorMessage.textContent = "";
                }
            }
        </script>
        <script>
            function validateDescription(input) {
                let errorMessage = document.getElementById("descriptionError");

                // Loại bỏ khoảng trắng thừa đầu và cuối
                input.value = input.value.trimStart();

                // Nếu ô nhập trống, không báo lỗi (cho phép để trống)
                if (input.value === "") {
                    errorMessage.textContent = "";
                    return;
                }

                // Xóa khoảng trắng thừa giữa các từ
                input.value = input.value.replace(/\s{2,}/g, " ");

                // Kiểm tra nếu toàn bộ nội dung chỉ có khoảng trắng
                if (input.value.trim() === "") {
                    errorMessage.textContent = "Không được nhập toàn dấu cách!";
                    input.value = ""; // Xóa nội dung nhập sai
                } else {
                    errorMessage.textContent = "";
                }
            }
        </script>

        <script>
            function validateCustomerName(input) {
                let errorMessage = document.getElementById("customerNameError");

                // Lấy giá trị nhập vào
                let value = input.value;

                // Nếu input rỗng, xóa lỗi
                if (value.trim() === "") {
                    errorMessage.textContent = value.length > 0 ? "Không được nhập toàn dấu cách!" : "";
                    return;
                }

                // Kiểm tra ký tự hợp lệ: Chỉ cho phép chữ, số và khoảng trắng giữa các từ
                let regex = /^[a-zA-Z0-9]+( [a-zA-Z0-9]+)*$/;
                if (!regex.test(value)) {
                    errorMessage.textContent = "Chỉ được nhập chữ, số và 1 dấu cách giữa các từ!";
                } else {
                    errorMessage.textContent = "";
                }
            }
        </script>
        <script>
            function validatePhone(input) {
                let errorMessage = document.getElementById("phoneError");

                // Nếu input rỗng, không báo lỗi (cho phép để trống)
                if (input.value === "") {
                    errorMessage.textContent = "";
                    return;
                }

                // Chỉ giữ lại chữ số (loại bỏ dấu cách và ký tự không phải số)
                input.value = input.value.replace(/\D/g, "");

                // Kiểm tra độ dài tối đa 10 số
                if (input.value.length > 10) {
                    input.value = input.value.slice(0, 10); // Cắt bỏ phần dư
                }

                // Kiểm tra số đầu tiên có phải là 0 không
                if (input.value.length > 0 && input.value.charAt(0) !== "0") {
                    errorMessage.textContent = "Số điện thoại phải bắt đầu bằng số 0!";
                } else {
                    errorMessage.textContent = "";
                }
            }
        </script>

        <script>
            // Hiển thị/Ẩn ô customPageSize khi dropdown thay đổi
            function toggleCustomInput(value) {
                var customInput = document.getElementById("customPageSizeInput");
                if (value === "custom") {
                    customInput.style.display = "inline-block";
                } else {
                    customInput.style.display = "none";
                }
            }

            // Khi load trang, kiểm tra nếu đang ở "custom" thì show input
            window.onload = function () {
                var pageSizeSelect = document.getElementById("pageSizeSelect");
                toggleCustomInput(pageSizeSelect.value);
            };
        </script>

    </body>
</html>

