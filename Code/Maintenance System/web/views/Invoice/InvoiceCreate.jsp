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
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="../../includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="../../includes/navbar-top.jsp" />
                <main class="content">
                    <h2>Create Invoice for Warranty Card: ${warrantyCard.warrantyCardCode}</h2>

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

                    <form action="Invoice/Create" method="post">
                        <input type="hidden" name="warrantyCardID" value="${warrantyCard.warrantyCardID}">
                        <input type="hidden" name="invoiceType" value="TechnicianToCustomer" id="amount" class="form-control" step="0.01" min="0" required>
                        <div class="mb-3">
                            <label for="dueDate" class="form-label">Due Date:</label>
                            <input type="date" name="dueDate" id="dueDate" class="form-control">
                        </div>

                        <button type="submit" class="btn btn-primary">Create Invoice</button>
                        <a href="WarrantyCard/Detail?ID=${warrantyCard.warrantyCardID}" class="btn btn-secondary">Cancel</a>
                    </form>
                </main>
                <jsp:include page="../../includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
        <script>
            function toggleFields() {
                const invoiceType = document.getElementById("invoiceType").value;
                const receivedByField = document.getElementById("receivedByField");
                const customerIDField = document.getElementById("customerIDField");

                receivedByField.style.display = invoiceType === "RepairContractorToTechnician" ? "block" : "none";
                customerIDField.style.display = invoiceType === "TechnicianToCustomer" ? "block" : "none";
            }
        </script>
    </body>
</html>