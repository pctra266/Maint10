<%-- 
    Document   : WarrantyCardAddComponent
    Created on : Feb 28, 2025
    Author     : ADMIN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Responsive Admin & Dashboard Template based on Bootstrap 5">
    <meta name="author" content="AdminKit">
    <meta name="keywords" content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">
    <base href="${pageContext.request.contextPath}/">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="shortcut icon" href="icons/icon-48x48.png" />
    <title>Add Component to Warranty Card</title>
    <link href="css/light.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
</head>
<body>
    <div class="wrapper">
        <jsp:include page="../../includes/navbar-left.jsp" />
        <div class="main">
            <jsp:include page="../../includes/navbar-top.jsp" />
            <main class="content">
                <form action="Redirect" enctype="multipart/form-data">
                    <input type="hidden" name="target" value="WarrantyCard/Detail?ID=${warrantyCardID}">
                    <button type="submit" class="btn btn-primary d-flex align-items-center justify-content-center" style="transform:translate(-30%,-60%); height: 2.5rem; width: 5.2rem">
                        <i class="fas fa-arrow-left fa-4"></i> <span class="ms-2">Back</span>
                    </button>
                </form>
               

                <h2>Add New Component </h2>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        <div class="alert-message"><strong>${error}</strong></div>
                    </div>
                </c:if>
                 <form action="ComponentWarehouse">
                    <button type="submit" class="btn btn-outline-primary d-flex align-items-center justify-content-center mb-2" >
                        <i class="fas fa-search fa-4"></i> <span class="ms-2">Component Warehouse</span>
                    </button>
                </form>
                <form action="WarrantyCard/AddComponent" method="post">
                    <input type="hidden" name="ID" value="${warrantyCardID}">
                    <div class="mb-3">
                        <label for="componentInput" class="form-label">Component</label>
                        <input type="text" list="componentList" id="componentInput" name="componentName" class="form-control" placeholder="Type to search components" required>
                        <datalist id="componentList">
                            <c:forEach var="component" items="${availableComponents}">
                                <option value="${component.componentName}" data-id="${component.componentID}" data-price="${component.price}">
                                    ${component.componentName} (Code: ${component.componentCode})
                                </option>
                            </c:forEach>
                        </datalist>
                        <input type="hidden" name="componentID" id="selectedComponentID">
                    </div>
                    <div class="mb-3">
                        <label for="status" class="form-label">Status</label>
                        <select name="status" id="status" class="form-select" required>
                            <option value="fixing">Fixing</option>
                            <option value="warranty_repaired">Repaired (Warranty)</option>
                            <option value="warranty_replaced">Replaced (Warranty)</option>
                            <option value="repaired">Repaired</option>
                            <option value="replace">Replace</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="price" class="form-label">Price</label>
                        <input type="number" name="price" id="price" class="form-control" min="0" step="0.01" value="0" required>
                    </div>
                    <div class="mb-3">
                        <label for="quantity" class="form-label">Quantity</label>
                        <input type="number" name="quantity" id="quantity" class="form-control" min="0" value="1" required>
                    </div>
                    <div class="d-flex justify-content-end">
                        <a href="WarrantyCard/Detail?ID=${warrantyCardID}" class="btn btn-secondary me-2">Cancel</a>
                        <button type="submit" class="btn btn-primary" id="addComponentSubmit" >Add Component</button>
                    </div>
                </form>
            </main>
            <jsp:include page="../../includes/footer.jsp" />
        </div>
    </div>

    <script src="js/app.js"></script>
    <script src="js/format-input.js"></script>
    <script>
        const componentInput = document.getElementById('componentInput');
        const statusSelect = document.getElementById('status');
        const priceInput = document.getElementById('price');
        const submitButton = document.getElementById('addComponentSubmit');
        const errorMessage = document.getElementById('componentError');
        const selectedComponentID = document.getElementById('selectedComponentID');
        const options = Array.from(document.querySelectorAll('#componentList option'));

        function updatePriceField() {
            const status = statusSelect.value;
            const selectedOption = options.find(opt => opt.getAttribute('data-id') === selectedComponentID.value);
            const defaultPrice = selectedOption ? parseFloat(selectedOption.getAttribute('data-price')) || 0 : 0;

            if (status === 'warranty_repaired' || status === 'warranty_replaced') {
                priceInput.value = 0;
                priceInput.readOnly = true;
            } else {
                priceInput.value = defaultPrice;
                priceInput.readOnly = false;
            }
        }

        componentInput.addEventListener('input', function () {
            const inputValue = this.value.trim();
            let selectedId = null;

            const isValid = options.some(option => {
                if (option.value === inputValue) {
                    selectedId = option.getAttribute('data-id');
                    return true;
                }
                return false;
            });

            selectedComponentID.value = selectedId;
        });

        statusSelect.addEventListener('change', updatePriceField);

        // Initial state
        updatePriceField();
    </script>
</body>
</html>