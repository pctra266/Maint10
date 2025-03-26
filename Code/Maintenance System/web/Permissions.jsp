<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Permissions</title>
        <link href="css/light.css" rel="stylesheet">
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
            table {
                width: 100%;
                border-collapse: collapse;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #f4f4f4;
                cursor: pointer;
            }
            .pagination {
                margin-top: 20px;
                text-align: center;
            }
            .pagination a {
                display: inline-block;
                padding: 8px 12px;
                margin: 0 5px;
                text-decoration: none;
                border: 1px solid #ccc;
                color: #333;
                border-radius: 5px;
            }
            .pagination a.active {
                background-color: #007bff;
                color: white;
                border: 1px solid #007bff;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center">Manage Permissions</h1>

                    <input type="text" id="search" class="search-box" placeholder="Search permissions...">
                    <button class="btn btn-primary" onclick="window.location.href = 'roles'">Back to Role Manage</button>

                    <form action="permissions" method="post">
                        <input type="hidden" name="role_id" value="${roleId}">
                     

                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Permission ID</th>
                                    <th id="sortByName" data-order="${order == 'asc' ? 'desc' : 'asc'}">
                                        Permission Name ⬍
                                    </th>
                                    <th><input type="checkbox" id="selectAll"> Assign</th>
                                </tr>
                            </thead>
                            <tbody id="permissionTable">
                                <c:forEach var="permission" items="${allPermissions}">
                                    <tr>
                                        <td>${permission.permissionID}</td>
                                        <td>${permission.permissionName}</td>
                                        <td>
                                            <input type="checkbox" name="permissions" value="${permission.permissionID}" class="permCheckbox"
                                                   <c:if test="${rolePermissions.contains(permission.permissionID)}">checked</c:if> >
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <button type="submit" class="btn btn-primary">Save Changes</button>
                    </form>

                  
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const checkboxes = document.querySelectorAll(".permCheckbox");
                const selectAll = document.getElementById("selectAll");

                // Cập nhật checkbox "Select All" nếu tất cả checkbox con đã chọn
                function updateSelectAll() {
                    selectAll.checked = [...checkboxes].every(cb => cb.checked);
                }

                selectAll.addEventListener("change", function () {
                    checkboxes.forEach(cb => cb.checked = this.checked);
                });

                checkboxes.forEach(cb => cb.addEventListener("change", updateSelectAll));

                // Tìm kiếm
                document.getElementById("search").addEventListener("keyup", function () {
                    let searchValue = this.value.toLowerCase();
                    let rows = document.querySelectorAll("#permissionTable tr");
                    rows.forEach(row => {
                        let permissionName = row.cells[1].textContent.toLowerCase();
                        row.style.display = permissionName.includes(searchValue) ? "" : "none";
                    });
                    selectAll.checked = false;
                });

                // Sắp xếp
                document.getElementById("sortByName").addEventListener("click", function () {
                    let currentUrl = new URL(window.location.href);
                    let currentOrder = this.getAttribute("data-order");
                    currentUrl.searchParams.set("order", currentOrder);
                    currentUrl.searchParams.set("sort", "permissionName");
                    window.location.href = currentUrl.toString();
                });

                // Cập nhật trạng thái Select All khi load trang
                updateSelectAll();
            });
        </script>
    </body>
</html>
