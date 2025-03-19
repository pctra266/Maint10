<%-- 
    Document   : editExtendedWarranty
    Created on : Mar 16, 2025, 8:57:34 PM
    Author     : Tra Pham
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Edit Extended Warranty</title>

        <link rel="stylesheet" href="css/light.css">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <div class="card">
                        <div class="card-header">
                    <h1>Edit Extended Warranty</h1>
                    </div>
                        <div class="card-body">
                    <form action="extendedWarranty" method="post">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" name="extendedWarrantyID" value="${extendedWarranty.extendedWarrantyID}">
                        <div class="mb-3">
                            <label for="extendedWarrantyName" class="form-label">Extended Warranty Name</label>
                            <input type="text" class="form-control" name="extendedWarrantyName" value="${extendedWarranty.extendedWarrantyName}" required>
                            <c:if test="${errors != null && errors.nameError != null}">
                                <span class="text-danger">${errors.nameError}</span>
                            </c:if>
                        </div>
                        <div class="mb-3">
                            <label for="extendedPeriodInMonths" class="form-label">Extended Period (Months)</label>
                            <input type="number" min="1" class="form-control" name="extendedPeriodInMonths" value="${extendedWarranty.extendedPeriodInMonths}" required>
                            <c:if test="${errors != null && errors.monthError != null}">
                                <span class="text-danger">${errors.monthError}</span>
                            </c:if>
                        </div>
                        <div class="mb-3">
                            <label for="price" class="form-label">Price</label>
                            <input type="number" min="0" class="form-control"  name="price" step="0.01" value="${extendedWarranty.price}" required>
                            <c:if test="${errors != null && errors.priceError != null}">
                                <span class="text-danger">${errors.priceError}</span>
                            </c:if>
                        </div>
                        <div class="mb-3">
                            <label for="extendedWarrantyDescription" class="form-label">Description</label>
                            <textarea class="form-control" name="extendedWarrantyDescription" rows="3">${extendedWarranty.extendedWarrantyDescription}</textarea>
                            <c:if test="${errors != null && errors.desError != null}">
                                <span class="text-danger">${errors.desError}</span>
                            </c:if>
                        </div>
                        <button type="submit" class="btn btn-primary">Update</button>
                        <a href="extendedWarranty" class="btn btn-secondary">Cancel</a>
                    </form>
                        </div>
                        </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
            <script src="js/app.js"></script>
        </div>
    </body>
</html>

