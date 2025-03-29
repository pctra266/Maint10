<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Create Invoice</title>
        <base href="${pageContext.request.contextPath}/">
        <link href="css/light.css" rel="stylesheet">
                <link rel="shortcut icon" href="img/icons/icon-48x48.png" />

        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="../../includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="../../includes/navbar-top.jsp" />
                <main class="content">
                    <div class="col-md-12 row d-flex justify-content-center">
                        <div class="col-md-6">
                            <h1 class="text-center">Create Invoice for Card: ${warrantyCard.warrantyCardCode}</h1>

                            <!-- Alerts -->
                            <c:if test="${not empty updateAlert1}">
                                <div class="alert alert-success alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message"><strong>${updateAlert1}</strong></div>
                                </div>
                            </c:if>
                            <c:if test="${not empty updateAlert0}">
                                <div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message"><strong>${updateAlert0}</strong></div>
                                </div>
                            </c:if>

                            <form action="Invoice/Create" method="post" onsubmit="return validateDueDate()">
                                <input type="hidden" name="ID" value="${warrantyCard.warrantyCardID}">
                                <input type="hidden" name="invoiceType" value="TechnicianToCustomer" id="amount" class="form-control" step="0.01" min="0" required>
                                <div class="mb-3">
                                    <label for="staffName" class="form-label">Staff:</label>
                                    <input type="text" readonly="true"  id="staffName" class="form-control" value="${staff.name}">
                                </div>
                                <div class="mb-3">
                                    <label for="customerName" class="form-label">Customer:</label>
                                    <input type="text" readonly="true" id="customerName" class="form-control" value="${warrantyCard.customerName}">
                                </div>
                                <div class="mb-3">
                                    <label for="price" class="form-label">Amount:</label>
                                    <input type="text" readonly="true" id="price" class="form-control format-int" value="${price} VND">
                                </div>
                                <div class="mb-3">
                                    <label for="dueDate" class="form-label">Due Date:</label>
                                    <input type="date" name="dueDate" id="dueDate" class="form-control" required onchange="checkDueDate(this)">
                                    <div id="dueDateError" class="invalid-feedback" style="display: none;">Due Date must be today or later.</div>
                                </div>
                                <div class="d-flex justify-content-center gap-3">
                                    <button type="submit" class="btn btn-primary" style="width:7rem">Create Invoice</button>
                                    <a href="WarrantyCard/Detail?ID=${warrantyCard.warrantyCardID}" style="width:7rem" class="btn btn-secondary d-flex justify-content-center align-items-md-center">Cancel</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </main>
                <jsp:include page="../../includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
        <script src="js/format-input.js"></script>

        <script>
                                        // Thiết lập Due Date mặc định và min
                                        window.onload = function () {
                                            const dueDateInput = document.getElementById('dueDate');
                                            const today = new Date();
                                            const defaultDueDate = new Date(today);
                                            defaultDueDate.setDate(today.getDate() + 3); // Mặc định +3 ngày

                                            const year = today.getFullYear();
                                            const month = String(today.getMonth() + 1).padStart(2, '0');
                                            const day = String(today.getDate()).padStart(2, '0');
                                            const todayFormatted = year + "-" + month + "-" + day;
                                            dueDateInput.min = todayFormatted; // Ngày tối thiểu là hôm nay

                                            const defaultYear = defaultDueDate.getFullYear();
                                            const defaultMonth = String(defaultDueDate.getMonth() + 1).padStart(2, '0');
                                            const defaultDay = String(defaultDueDate.getDate()).padStart(2, '0');
                                            const defaultFormatted = defaultYear + "-" + defaultMonth + "-" + defaultDay;
                                            dueDateInput.value = defaultFormatted; // Giá trị mặc định
                                        };

                                        // Kiểm tra Due Date khi thay đổi
                                        function checkDueDate(input) {
                                            const dueDate = new Date(input.value);
                                            const today = new Date();
                                            today.setHours(0, 0, 0, 0); // Đặt giờ về 0 để so sánh ngày
                                            const errorDiv = document.getElementById('dueDateError');

                                            if (dueDate < today) {
                                                input.classList.add('is-invalid');
                                                errorDiv.style.display = 'block';
                                            } else {
                                                input.classList.remove('is-invalid');
                                                errorDiv.style.display = 'none';
                                            }
                                        }

                                        // Kiểm tra trước khi submit form
                                        function validateDueDate() {
                                            const dueDateInput = document.getElementById('dueDate');
                                            const dueDate = new Date(dueDateInput.value);
                                            const today = new Date();
                                            today.setHours(0, 0, 0, 0);

                                            if (dueDate < today) {
                                                dueDateInput.classList.add('is-invalid');
                                                document.getElementById('dueDateError').style.display = 'block';
                                                return false; // Ngăn submit
                                            }
                                            return true; // Cho phép submit
                                        }
        </script>
    </body>
</html>