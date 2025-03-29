<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Role</title>
        <link href="css/light.css" rel="stylesheet">
                <link rel="shortcut icon" href="img/icons/icon-48x48.png" />
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            /* Custom CSS for dropdown and permissions */
            .dropdown-container {
                margin-bottom: 30px;
                display: flex;
                justify-content: center;
            }

            select {
                padding: 10px 20px;
                font-size: 16px;
                border-radius: 5px;
                border: 1px solid #ccc;
                width: 200px;
                cursor: pointer;
            }

            .permissions-container {
                display: flex;
                flex-direction: column;
                gap: 10px;
                margin-top: 20px;
                align-items: center;
            }

            .permission-item {
                padding: 8px 20px;
                background-color: #f1f1f1;
                border-radius: 5px;
                width: 250px;
                text-align: center;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }

            .permission-item:hover {
                background-color: #e0e0e0;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center">Role</h1>
                    
                    <!-- Dropdown to select Role -->
                    <div class="dropdown-container">
                        <label for="roleSelect">Select a Role: </label>
                        <select id="roleSelect" name="role" onchange="showPermissions(this)">
                            <option value="">-- Choose a Role --</option>
                            <c:forEach var="role" items="${roleList}">
                                <option value="${role.roleID}">${role.roleName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- Permissions List (Hidden initially) -->
                    <div class="permissions-container" id="permissionsContainer">
                        <!-- Permissions will be displayed here based on selected role -->
                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>

        <script>
            // Function to show permissions for the selected role
            function showPermissions(selectElement) {
                const roleID = selectElement.value;
                const permissionsContainer = document.getElementById("permissionsContainer");
                
                // Clear current permissions
                permissionsContainer.innerHTML = '';

                if (roleID) {
                    // Fetch the permissions for the selected role (you may want to call an API or use a predefined list)
                    fetchPermissions(roleID).then(permissions => {
                        permissions.forEach(permission => {
                            const permissionElement = document.createElement('div');
                            permissionElement.classList.add('permission-item');
                            permissionElement.innerText = permission.permissionName;
                            permissionsContainer.appendChild(permissionElement);
                        });
                    });
                }
            }

            // Sample function to simulate fetching permissions from a server (replace with your actual data source)
            function fetchPermissions(roleID) {
                return new Promise((resolve) => {
                    const permissions = {
                        1: [{ permissionName: 'Add Customer' }, { permissionName: 'Delete Customer' }],
                        2: [{ permissionName: 'View Orders' }, { permissionName: 'Update Orders' }],
                        // Add more roles and permissions as necessary
                    };
                    resolve(permissions[roleID] || []);
                });
            }
        </script>
    </body>
</html>
