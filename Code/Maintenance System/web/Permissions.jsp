<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Permission</title>
        <link href="css/light.css" rel="stylesheet">
                <link rel="shortcut icon" href="img/icons/icon-48x48.png" />

        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
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
            select {
                padding: 8px;
                border-radius: 5px;
                border: 1px solid #ccc;
                margin-bottom: 15px;
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
            .btn-load-more {
                display: block;
                width: 200px;
                margin: 15px auto;
                padding: 10px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                text-align: center;
            }
            .btn-load-more:hover {
                background-color: #0056b3;
            }
            .add-permission-form {
                display: none;
                margin-top: 15px;
                padding: 15px;
                border: 1px solid #ccc;
                border-radius: 5px;
                background: #f9f9f9;
            }
            .alert {
                padding: 10px;
                margin-bottom: 15px;
                border-radius: 5px;
            }
            .alert-success {
                background-color: #d4edda;
                color: #155724;
                border: 1px solid #c3e6cb;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center">Permission</h1>
                    <c:if test="${not empty sessionScope.success}">
                        <div class="alert alert-success">${sessionScope.success}</div>
                        <c:remove var="success" scope="session"/>
                    </c:if>

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

                    <input type="text" id="search" class="search-box" placeholder="Search permissions...">
                    <button class="btn btn-success" onclick="toggleAddForm()">+</button>

                    <!-- Form thêm permission -->
                    <div id="addPermissionForm" class="add-permission-form">
                        <h3>Add New Permission</h3>
                        <form action="addPermission" method="post">
                            <label for="permissionName">Permission Name:</label>
                            <input type="text" id="permissionName" name="permissionName" required class="search-box">
                            <label for="link">Link:</label>
                            <input type="text" id="link" name="link" required class="search-box">
                            <label for="description">Description:</label>
                            <input type="text" id="description" name="description" required class="search-box">
                            <button type="submit" class="btn btn-primary">Add</button>
                            <button type="button" class="btn btn-secondary" onclick="toggleAddForm()">Cancel</button>
                        </form>
                    </div>

                    <form action="permissions" method="post">
                        <input type="hidden" name="role_id" value="${roleId}">

                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Permission ID</th>
                                    <th id="sortByName">Permission Name ⬍</th>
                                    <th><input type="checkbox" id="selectAll"> Assign</th>
                                </tr>
                            </thead>
                            <tbody id="permissionTable">
                                <c:forEach var="permission" items="${permissionList}" varStatus="status">
                                    <tr id="permission-${permission.permissionID}" class="permission-row" style="display: ${status.index < 10 ? '' : 'none'};">
                                        <td>${permission.permissionID}</td>
                                        <td>${permission.permissionName}</td>
                                        <td><input type="checkbox" name="permissions" value="${permission.permissionID}" class="permCheckbox" <c:if test="${rolePermissions.contains(permission.permissionID)}">checked</c:if>></td>
                                        </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <button type="submit" class="btn btn-primary">Save Changes</button>
                    </form>

                    <button id="loadMore" class="btn-load-more">More</button>
                </main>
            </div>
        </div>



    </main>
    <jsp:include page="/includes/footer.jsp" />
    <script>
        function toggleAddForm() {
            let form = document.getElementById("addPermissionForm");
            form.style.display = (form.style.display === "none" || form.style.display === "") ? "block" : "none";
        }

        document.addEventListener("DOMContentLoaded", function () {
            const checkboxes = document.querySelectorAll(".permCheckbox");
            const selectAll = document.getElementById("selectAll");
            const loadMoreBtn = document.getElementById("loadMore");
            const rows = document.querySelectorAll(".permission-row");
            let currentVisible = 10;

            function updateSelectAll() {
                selectAll.checked = [...checkboxes].every(cb => cb.checked);
            }

            selectAll.addEventListener("change", function () {
                checkboxes.forEach(cb => cb.checked = this.checked);
            });
            checkboxes.forEach(cb => cb.addEventListener("change", updateSelectAll));

            document.getElementById("search").addEventListener("keyup", function () {
                let searchValue = this.value.toLowerCase();
                rows.forEach(row => {
                    row.style.display = row.cells[1].textContent.toLowerCase().includes(searchValue) ? "" : "none";
                });
            });

            loadMoreBtn.addEventListener("click", function () {
                for (let i = currentVisible; i < currentVisible + 10 && i < rows.length; i++) {
                    rows[i].style.display = "";
                }
                currentVisible += 10;
                if (currentVisible >= rows.length) loadMoreBtn.style.display = "none";
            });
        });
    </script>
    <script src="js/app.js"></script>
</body>
</html>
