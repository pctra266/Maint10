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
            table {
                border-collapse: collapse;
                width: 100%;
            }
            th, td {
                border: 1px solid #ccc;
                padding: 8px;
            }
            th {
                background-color: #f2f2f2;
            }
            .pagination {
                margin-top: 20px;
                text-align: center;
            }
            .pagination button {
                margin: 0 5px;
            }
            .search-form label {
                margin-right: 5px;
            }
            .search-form input,
            .search-form select {
                margin-right: 15px;
            }
            .flex-container {
                display: flex;
                gap: 20px;
                align-items: center;
                flex-wrap: wrap;
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
                                    <option value="5" 
                                            <c:if test="${param.pageSize == '5'}">selected</c:if>>5</option>
                                            <option value="10" 
                                            <c:if test="${param.pageSize == '10'}">selected</c:if>>10</option>
                                            <option value="15" 
                                            <c:if test="${param.pageSize == '15'}">selected</c:if>>15</option>
                                            <option value="20" 
                                            <c:if test="${param.pageSize == '20'}">selected</c:if>>20</option>
                                            <option value="25" 
                                            <c:if test="${param.pageSize == '25'}">selected</c:if>>25</option>
                                            <option value="custom"
                                            <c:if test="${param.pageSize == 'custom'}">selected</c:if>>Custom</option>
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
                            <label for="warrantyCardCode">Warranty Card Code:</label>
                            <input type="text" name="warrantyCardCode" id="warrantyCardCode" value="${param.warrantyCardCode}" />

                            <label for="staffName">Staff Name:</label>
                            <input type="text" name="staffName" id="staffName" value="${param.staffName}" />

                            <label for="date">Date (yyyy-MM-dd):</label>
                            <input type="text" name="date" id="date" value="${param.date}" />

                            <label for="status">Status:</label>
                            <select name="status" id="status">
                                <option value="waiting" ${param.status eq 'waiting' ? "selected" : ""}>waiting</option>
                                <option value="receive" ${param.status eq 'receive' ? "selected" : ""}>receive</option>
                                <option value="cancel"  ${param.status eq 'cancel'  ? "selected" : ""}>cancel</option>
                                <option value="done"    ${param.status eq 'done'    ? "selected" : ""}>done</option>
                            </select>
                            
                            <label for="note">Note:</label>
                            <input type="text" name="note" id="note" value="${param.note}" />
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

                    </form> <!-- end form -->

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
