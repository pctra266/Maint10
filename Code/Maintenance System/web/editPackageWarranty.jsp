<%-- 
    Document   : editPackageWarranty
    Created on : Mar 16, 2025, 9:17:19 PM
    Author     : Tra Pham
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Package Warranty</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>Edit Package Warranty</h1>
    <form action="PackageWarrantyController" method="post">
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="packageWarrantyID" value="${packageWarranty.packageWarrantyID}">
        
        <!-- Các trường chỉ hiển thị thông tin -->
        <div class="mb-3">
            <label class="form-label">Product Code:</label>
            <input type="text" class="form-control" value="${packageWarranty.productCode}" readonly>
        </div>
        <div class="mb-3">
            <label class="form-label">Customer Name:</label>
            <input type="text" class="form-control" value="${packageWarranty.customerName}" readonly>
        </div>
        <div class="mb-3">
            <label class="form-label">Email:</label>
            <input type="email" class="form-control" value="${packageWarranty.email}" readonly>
        </div>
        <div class="mb-3">
            <label class="form-label">Product Name:</label>
            <input type="text" class="form-control" value="${packageWarranty.productName}" readonly>
        </div>
        <div class="mb-3">
            <label class="form-label">Extended Warranty Name:</label>
            <input type="text" class="form-control" value="${packageWarranty.extendedWarrantyName}" readonly>
        </div>
        
        <!-- Các trường có thể chỉnh sửa -->
        <div class="mb-3">
            <label for="warrantyStartDate" class="form-label">Warranty Start Date:</label>
            <input type="date" class="form-control" id="warrantyStartDate" name="warrantyStartDate" value="${packageWarranty.warrantyStartDate}" required>
        </div>
        <div class="mb-3">
            <label for="warrantyEndDate" class="form-label">Warranty End Date:</label>
            <input type="date" class="form-control" id="warrantyEndDate" name="warrantyEndDate" value="${packageWarranty.warrantyEndDate}" required>
        </div>
        <div class="mb-3">
            <label for="note" class="form-label">Note:</label>
            <textarea class="form-control" id="note" name="note" rows="3">${packageWarranty.note}</textarea>
        </div>
        <div class="mb-3">
            <label for="isActive" class="form-label">Is Active:</label>
            <select class="form-control" id="isActive" name="isActive">
                <option value="true" <c:if test="${packageWarranty.active}">selected</c:if>>Yes</option>
                <option value="false" <c:if test="${!packageWarranty.active}">selected</c:if>>No</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="startExtendedWarranty" class="form-label">Start Extended Warranty:</label>
            <input type="date" class="form-control" id="startExtendedWarranty" name="startExtendedWarranty" value="${packageWarranty.startExtendedWarranty}" required>
        </div>
        <div class="mb-3">
            <label for="endExtendedWarranty" class="form-label">End Extended Warranty:</label>
            <input type="date" class="form-control" id="endExtendedWarranty" name="endExtendedWarranty" value="${packageWarranty.endExtendedWarranty}" required>
        </div>
        <button type="submit" class="btn btn-primary">Update Package Warranty</button>
        <a href="PackageWarrantyController" class="btn btn-secondary">Cancel</a>
    </form>
</div>
</body>
</html>

