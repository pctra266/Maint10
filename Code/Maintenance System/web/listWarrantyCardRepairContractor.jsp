<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh sách Contractor Card</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            /* Reset các thiết lập cơ bản */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            /* Thiết lập chung cho trang */
            body {
                font-family: 'Inter', sans-serif;
                background-color: #f0f2f5;
                color: #333;
            }

            /* Wrapper chứa sidebar và nội dung chính */
            .wrapper {
                display: flex;
                min-height: 100vh;
            }

            /* Phần main chứa nội dung chính */
            .main {
                flex: 1;
                display: flex;
                flex-direction: column;
            }

            /* Navbar trên */
            .navbar-top {
                background: #fff;
                padding: 15px 20px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }

            /* Khu vực nội dung chính */
            .content {
                padding: 30px;
                background: #fff;
                margin: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }

            /* Heading chính */
            h2 {
                font-weight: 600;
                margin-bottom: 20px;
            }

            /* Form và các phần tử bên trong */
            form {
                margin-bottom: 20px;
            }

            .flex-container {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
                align-items: center;
            }

            .search-form {
                display: flex;
                flex-wrap: wrap;
                gap: 15px;
                align-items: center;
                margin-bottom: 20px;
            }

            .search-form label {
                font-weight: 500;
            }

            .search-form input,
            .search-form select {
                padding: 8px 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 14px;
                outline: none;
            }

            .search-form input:focus,
            .search-form select:focus {
                border-color: #66afe9;
            }

            /* Styling cho bảng */
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            th, td {
                padding: 12px 15px;
                border: 1px solid #e0e0e0;
                text-align: left;
            }

            th {
                background-color: #f8f8f8;
                font-weight: 600;
            }

            tr:nth-child(even) {
                background-color: #fafafa;
            }

            /* Phân trang */
            .pagination {
                margin-top: 25px;
                display: flex;
                justify-content: center;
                align-items: center;
                gap: 10px;
            }

            .pagination button {
                padding: 8px 16px;
                background-color: #007bff;
                color: #fff;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.2s ease;
            }

            .pagination button:hover {
                background-color: #0056b3;
            }

            /* Các nút trong form */
            form button {
                padding: 10px 16px;
                border: none;
                background-color: #28a745;
                color: #fff;
                border-radius: 4px;
                cursor: pointer;
                font-size: 14px;
                transition: background-color 0.2s ease;
            }

            form button:hover {
                background-color: #218838;
            }

            /* Nút chi tiết */
            .btn-detail {
                padding: 6px 12px;
                background-color: #17a2b8;
                color: #fff;
                text-decoration: none;
                border-radius: 4px;
                transition: background-color 0.2s ease;
            }

            .btn-detail:hover {
                background-color: #138496;
            }

            /* Styling cho select và input custom page size */
            #pageSize,
            #customPageSize {
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 4px;
                background-color: #fff;
            }

            /* Responsive */
            @media (max-width: 768px) {
                .flex-container, .search-form {
                    flex-direction: column;
                    align-items: flex-start;
                }
                .pagination {
                    flex-direction: column;
                    gap: 5px;
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

                    <h2>List Warranty Card</h2>   

                    <form method="get" action="warrantyCardRepairContractor">
                        <div class="flex-container">
                            <div>
                                <label for="pageSize">Products per page:</label>
                                <select name="pageSize" id="pageSize" onchange="toggleCustomPageSize()">
                                    <option value="5" <c:if test="${param.pageSize == '5'}">selected</c:if>>5</option>
                                    <option value="10" <c:if test="${param.pageSize == '10'}">selected</c:if>>10</option>
                                    <option value="15" <c:if test="${param.pageSize == '15'}">selected</c:if>>15</option>
                                    <option value="20" <c:if test="${param.pageSize == '20'}">selected</c:if>>20</option>
                                    <option value="25" <c:if test="${param.pageSize == '25'}">selected</c:if>>25</option>
                                    <option value="custom" <c:if test="${param.pageSize == 'custom'}">selected</c:if>>Custom</option>
                                    </select>

                                    <input type="number" name="customPageSize" id="customPageSize" 
                                           placeholder="Enter custom page size" min="1" 
                                           style="display:none;"
                                           value="${param.customPageSize != null ? param.customPageSize : ''}" />

                                <button type="submit">Apply</button>
                                <div>
                                    <button type="submit">Search</button>
                                </div>
                            </div>                        
                        </div>

                        <div class="search-form">
                            <!-- Warranty Card Code: Chỉ cho phép chữ cái, không có dấu cách, số hay kí tự đặc biệt -->
                            <label for="warrantyCardCode">Warranty Card Code:</label>
                            <input type="text" name="warrantyCardCode" id="warrantyCardCode" 
                                   value="${param.warrantyCardCode}" 
                                   pattern="[A-Za-z]+" 
                                   title="Chỉ được nhập chữ cái (không dấu cách, số hay kí tự đặc biệt)" />

                            <!-- Staff Name: Cho phép chữ cái, các từ cách nhau đúng 1 dấu cách, không cho số, kí tự đặc biệt và không toàn khoảng trắng -->
                            <label for="staffName">Staff Name:</label>
                            <input type="text" name="staffName" id="staffName" 
                                   value="${param.staffName}" 
                                   pattern="^(?!\s+$)[A-Za-z]+(?:\s[A-Za-z]+)*$" 
                                   title="Nhập chữ cái, các từ cách nhau đúng 1 dấu cách, không nhập số hoặc kí tự đặc biệt và không toàn khoảng trắng" />

                            <label for="date">Date (yyyy-MM-dd):</label>
                            <input type="date" name="date" id="date" value="${param.date}" />

                            <label for="status">Status:</label>
                            <select name="status" id="status">
                                <option value="waiting" ${param.status eq 'waiting' ? "selected" : ""}>waiting</option>
                                <option value="receive" ${param.status eq 'receive' ? "selected" : ""}>receive</option>
                                <option value="cancel"  ${param.status eq 'cancel'  ? "selected" : ""}>cancel</option>
                                <option value="done"    ${param.status eq 'done'    ? "selected" : ""}>done</option>
                            </select>

                            <!-- Note: Không cho phép nhập kí tự đặc biệt và không toàn khoảng trắng -->
                            <label for="note">Note:</label>
                            <input type="text" name="note" id="note" 
                                   value="${param.note}" 
                                   pattern="^(?!\s+$)[A-Za-z0-9 ]+$" 
                                   title="Không được nhập kí tự đặc biệt và không toàn khoảng trắng" />
                        </div>
                        <br/>
                        <br/>
                        <table>
                            <thead>
                                <tr>
                                    <th>Warranty Card Code</th>
                                    <th>Staff Name</th>
                                    <th>Date</th>
                                    <th>Status</th>
                                    <th>Note</th>
                                    <th>Action</th> 
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="card" items="${contractorCards}">
                                    <tr>
                                        <td>${card.warrantyCardCode}</td>
                                        <td>${card.staffName}</td>
                                        <td>${card.date}</td>
                                        <td>${card.status}</td>
                                        <td>${card.note}</td>
                                        <td>
                                            <a class="btn-detail" href="warrantyCardDetailContractor?cardId=${card.warrantyCardID}">
                                                View Detail
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty contractorCards}">
                                    <tr>
                                        <td colspan="6">Không tìm thấy contractor card nào.</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>

                        <!-- Khu vực phân trang -->
                        <div class="pagination">
                            <c:if test="${totalPages > 1}">
                                <button type="submit" name="currentPage" value="1">Start</button>

                                <c:if test="${currentPage > 1}">
                                    <button type="submit" name="currentPage" value="${currentPage - 1}">Previous</button>
                                </c:if>

                                <span>Trang ${currentPage} / ${totalPages}</span>

                                <c:if test="${currentPage < totalPages}">
                                    <button type="submit" name="currentPage" value="${currentPage + 1}">Next</button>
                                </c:if>

                                <button type="submit" name="currentPage" value="${totalPages}">End</button>
                            </c:if>
                        </div>
                    </form>


                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>

        <script src="js/app.js"></script>

        <script>
                                    function toggleCustomPageSize() {
                                        const pageSizeSelect = document.getElementById("pageSize");
                                        const customInput = document.getElementById("customPageSize");
                                        if (pageSizeSelect.value === "custom") {
                                            customInput.style.display = "inline-block";
                                        } else {
                                            customInput.style.display = "none";
                                            customInput.value = ""; // Xoá giá trị cũ nếu người dùng chọn lại
                                        }
                                    }

                                    // Gọi hàm khi trang load (để xử lý trường hợp đã chọn custom trước đó)
                                    window.onload = function () {
                                        toggleCustomPageSize();
                                    };
        </script>
    </body>
</html>
