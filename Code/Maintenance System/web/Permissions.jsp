<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Permissions</title>

    <link href="css/light.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

    <style>
        .search-box { margin-bottom: 15px; padding: 8px; width: 100%; max-width: 300px; border: 1px solid #ccc; border-radius: 5px; font-size: 14px; }
        select { padding: 8px; border-radius: 5px; border: 1px solid #ccc; margin-bottom: 15px; font-size: 14px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f4f4f4; cursor: pointer; }
    </style>
</head>

<body>
    <div class="wrapper">
        <jsp:include page="/includes/navbar-left.jsp" />
        <div class="main">
            <jsp:include page="/includes/navbar-top.jsp" />
            <main class="content">
                <h1 class="text-center">Manage Permissions</h1>

                <!-- Dropdown thủ công với 6 giá trị Role -->
                <form method="get" action="permissions">
                    <label for="customDropdown">Chọn Role:</label>
                    <select name="roleId" id="customDropdown" onchange="this.form.submit()">
                        <option value="">-- Chọn Role --</option>
                        <option value="1" ${param.roleId == '1' ? 'selected' : ''}>Admin</option>
                        <option value="2" ${param.roleId == '2' ? 'selected' : ''}>Technician</option>
                        <option value="3" ${param.roleId == '3' ? 'selected' : ''}>Inventory Manager</option>
                        <option value="4" ${param.roleId == '4' ? 'selected' : ''}>Repair Contractor</option>
                        <option value="5" ${param.roleId == '5' ? 'selected' : ''}>Customer Service Agent</option>
                        <option value="6" ${param.roleId == '6' ? 'selected' : ''}>Inactive</option>
                    </select>
                </form>

                <!-- Search Box -->
                <input type="text" id="search" class="search-box" placeholder="Search permissions...">

                <!-- Back to Role Manage Button -->
                <button class="btn btn-primary" onclick="window.location.href = 'roles'">Back to Role Manage</button>

                <form action="permissions" method="post">
                    <input type="hidden" name="role_id" value="${roleId}">

                    <!-- Permissions Table -->
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Permission ID</th>
                                <th id="sortByName" data-order="${order == 'asc' ? 'desc' : 'asc'}">
                                    Permission Name ⬍
                                </th>
                                <th>
                                    <input type="checkbox" id="selectAll"> Assign
                                </th>
                            </tr>
                        </thead>
                        <tbody id="permissionTable">
                            <c:forEach var="permission" items="${permissionList}">
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

                    <!-- Submit Button -->
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                </form>

            </main>
        </div>
    </div>

    <jsp:include page="/includes/footer.jsp" />

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const checkboxes = document.querySelectorAll(".permCheckbox");
            const selectAll = document.getElementById("selectAll");

            function updateSelectAll() {
                selectAll.checked = [...checkboxes].every(cb => cb.checked);
            }

            selectAll.addEventListener("change", function () {
                checkboxes.forEach(cb => cb.checked = this.checked);
            });

            checkboxes.forEach(cb => cb.addEventListener("change", updateSelectAll));

            document.getElementById("search").addEventListener("keyup", function () {
                let searchValue = this.value.toLowerCase();
                let rows = document.querySelectorAll("#permissionTable tr");
                rows.forEach(row => {
                    let permissionName = row.cells[1].textContent.toLowerCase();
                    row.style.display = permissionName.includes(searchValue) ? "" : "none";
                });
                selectAll.checked = false;
            });

            document.getElementById("sortByName").addEventListener("click", function () {
                let currentUrl = new URL(window.location.href);
                let currentOrder = this.getAttribute("data-order");
                currentUrl.searchParams.set("order", currentOrder);
                currentUrl.searchParams.set("sort", "permissionName");
                window.location.href = currentUrl.toString();
            });

            updateSelectAll();
        });
    </script>

    <script src="js/app.js"></script>
</body>
</html>
