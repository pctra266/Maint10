<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Contractor Card List</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

        <style>

            main.content {
                background: #ffffff;
                border-radius: 12px;
                padding: 30px 40px;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
                margin: 20px;
            }

            main.content h2 {
                text-align: center;
                font-size: 28px;
                font-weight: 700;
                color: #326ABC;
                margin-bottom: 30px;
                text-shadow: 1px 1px 2px rgba(30, 58, 138, 0.3);
            }

            /*****************************/
            /* Search form - single row */
            /*****************************/
            .search-form {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                align-items: center;
                gap: 16px;
                margin-bottom: 30px;
                overflow-x: auto;
            }

            .form-group {
                display: flex;
                align-items: center;
                gap: 8px;
            }

            .form-group label {
                font-weight: 600;
                color: #326ABC;
                white-space: nowrap;
            }

            .form-group input[type="text"],
            .form-group input[type="date"],
            .form-group select {
                padding: 8px 12px;
                border: 1px solid #cbd5e1;
                border-radius: 6px;
                font-size: 14px;
                outline: none;
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
                max-width: 150px;
            }

            .form-group input[type="text"]:focus,
            .form-group input[type="date"]:focus,
            .form-group select:focus {
                border-color: #2563eb;
                box-shadow: 0 0 6px rgba(37, 99, 235, 0.4);
            }

            /*****************************/
            /* Form chọn pageSize & nút */
            /*****************************/
            .flex-container {
                display: flex;
                justify-content: center;
                align-items: center;
                gap: 20px;
                margin-bottom: 30px;
            }

            .flex-container label {
                font-weight: 600;
                color: #1e40af;
                white-space: nowrap;
            }

            .flex-container select,
            .flex-container input[type="number"] {
                padding: 8px 12px;
                border: 1px solid #cbd5e1;
                border-radius: 6px;
                font-size: 14px;
                outline: none;
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }

            .flex-container select:focus,
            .flex-container input[type="number"]:focus {
                border-color: #2563eb;
                box-shadow: 0 0 6px rgba(37, 99, 235, 0.4);
            }

            .flex-container button {
                padding: 8px 16px;
                background: #326ABC;
                border: none;
                border-radius: 6px;
                color: #fff;
                font-weight: 600;
                cursor: pointer;
                transition: background 0.3s ease, transform 0.3s ease;
            }

            .flex-container button:hover {
                transform: translateY(-2px);
                background: linear-gradient(135deg, #1e40af, #2563eb);
            }

            /*****************************/
            /* Bảng danh sách */
            /*****************************/
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                background: #fff;
            }

            table thead {
                background: #326ABC;
                color: #fff;
            }

            table th,
            table td {
                padding: 14px 20px;
                border: 1px solid #e2e8f0;
                text-align: center;
                font-size: 14px;
            }

            table tbody tr {
                transition: background 0.3s ease;
            }

            table tbody tr:nth-child(even) {
                background: #f9fafb;
            }

            table tbody tr:hover {
                background: #eff6ff;
            }

            .btn-detail {
                display: inline-block;
                padding: 6px 12px;
                background: #326ABC;
                color: #fff;
                border-radius: 4px;
                text-decoration: none;
                font-weight: 500;
                transition: background 0.3s ease, transform 0.3s ease;
            }

            .btn-detail:hover {
                background: #1e40af;
                color: white;
                text-decoration: none;
                transform: translateY(-1px);
            }

            /*****************************/
            /* Phân trang */
            /*****************************/
            .pagination {
                display: flex;
                justify-content: center; /* Canh giữa pagination */
                align-items: center;
                gap: 10px;
                margin-top: 30px;
                flex-wrap: wrap;
            }

            .pagination button {
                padding: 8px 14px;
                background: #2563eb;
                color: #fff;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-weight: 600;
                transition: background 0.3s ease, transform 0.3s ease;
            }

            .pagination button:hover {
                background: #1e40af;
                transform: translateY(-2px);
            }

            .pagination span {
                font-size: 16px;
                color: #1e3a8a;
                font-weight: 600;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">

                    <h2>List of Warranty Cards</h2>   

                    <form method="get" action="warrantyCardRepairContractor">
                        <div class="search-form">
                            <div class="form-group">
                                <label for="warrantyCardCode">Warranty Card Code:</label>
                                <input type="text" name="warrantyCardCode" id="warrantyCardCode" value="${param.warrantyCardCode}" pattern="[A-Za-z]+" title="Only letters allowed (no spaces, digits or special characters)" />
                            </div>

                            <div class="form-group">
                                <label for="staffName">Staff Name:</label>
                                <input type="text" name="staffName" id="staffName" value="${param.staffName}" pattern="^(?!\s+$)[A-Za-z]+(?:\s[A-Za-z]+)*$" title="Only letters, single space between words, no digits/special chars" />
                            </div>

                            <div class="form-group">
                                <label for="date">Date (yyyy-MM-dd):</label>
                                <input type="date" name="date" id="date" value="${param.date}" />
                            </div>

                            <div class="form-group">
                                <label for="status">Status:</label>
                                <select name="status" id="status">
                                    <option value="" ${param.status eq '' ? "selected" : ""}>All</option>
                                    <option value="waiting" ${param.status eq 'waiting' ? "selected" : ""}>waiting</option>
                                    <option value="receive" ${param.status eq 'receive' ? "selected" : ""}>receive</option>
                                    <option value="cancel"  ${param.status eq 'cancel'  ? "selected" : ""}>cancel</option>
                                    <option value="done"    ${param.status eq 'done'    ? "selected" : ""}>done</option>
                                </select>
                            </div>


                            <div class="form-group">
                                <label for="note">Note:</label>
                                <input type="text" name="note" id="note" 
                                       value="${param.note}" 
                                       pattern="^(?!\s+$)[A-Za-z0-9 ]+$" 
                                       title="No special characters, not all whitespace" />
                            </div>

                        </div><!-- /.search-form -->


                        <!-- Chọn pageSize & nút -->
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
                                <button type="submit">Search</button>
                                <button type="button" onclick="window.location.href = 'listInvoiceRepair'">Invoice</button>
                            </div>                        
                        </div>

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
                                        <td colspan="6">No contractor card found.</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>

                        <div class="pagination">
                            <c:if test="${totalPages > 1}">
                                <button type="submit" name="currentPage" value="1">First</button>

                                <c:if test="${currentPage > 1}">
                                    <button type="submit" name="currentPage" value="${currentPage - 1}">Previous</button>
                                </c:if>

                                <span>Page ${currentPage} / ${totalPages}</span>

                                <c:if test="${currentPage < totalPages}">
                                    <button type="submit" name="currentPage" value="${currentPage + 1}">Next</button>
                                </c:if>

                                <button type="submit" name="currentPage" value="${totalPages}">Last</button>
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
                                            customInput.value = "";
                                        }
                                    }
                                    window.onload = function () {
                                        toggleCustomPageSize();
                                    };
        </script>
    </body>
</html>
