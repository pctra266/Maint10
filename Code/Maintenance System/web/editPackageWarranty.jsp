<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Package Warranty</title>
    <link rel="stylesheet" href="css/light.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
</head>
<body>
    <div class="wrapper">
        <jsp:include page="/includes/navbar-left.jsp" />
        <div class="main">
            <jsp:include page="/includes/navbar-top.jsp" />
            
            <main class="content">
                <div class="card shadow-sm mb-4">
                    <a href="packageWarranty" style="margin: 1%; width: 7%" class="btn btn-primary">Back</a>
                    <div class="card-header">
                        <h2>Package Warranty</h2>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty message}">
                            <div class="alert alert-info">${message}</div>
                        </c:if>
                        <form action="${pageContext.request.contextPath}/packageWarranty" method="post">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="packageWarrantyID" value="${packageWarranty.packageWarrantyID}">
                            
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
                            
                            <div class="mb-3">
                                <label for="note" class="form-label">Note:</label>
                                <textarea class="form-control" id="note" name="note" rows="3">${packageWarranty.note}</textarea>
                            </div>
                            <div class="mb-3">
                                <label for="isActive" class="form-label">Is Active:</label>
                                <input type="text" readonly class="form-control" id="isActive" name="isActive" value="${packageWarranty.active == true ? 'active' : 'deactive'}" required>
                            </div>
                            
                            <div class="mb-3">
                                <button type="submit" class="btn btn-primary">Update Package Warranty</button>
                                <a href="packageWarranty" class="btn btn-secondary">Cancel</a>
                            </div>
                        </form>
                    </div>
                </div>
                
                <div class="card shadow-sm mb-4">
                    <div class="card-header">
                        <h3>Extend Default Warranty</h3>
                    </div>
                    <div class="card-body">
                        <p><strong>Product Code:</strong> ${packageWarranty.productCode}</p>
                        <p><strong>Current Warranty Period:</strong> ${packageWarranty.warrantyStartDate} to ${packageWarranty.warrantyEndDate}</p>
                        <p><em>Extension will add Duration of <strong>12 months</strong> (default) based on current date.</em></p>
                        <form action="${pageContext.request.contextPath}/extendWarranty" method="post">
                            <input type="hidden" name="action" value="extendDefault">
                            <input type="hidden" name="packageWarrantyID" value="${packageWarranty.packageWarrantyID}">
                            <button type="submit" class="btn btn-primary">Extend Default Warranty</button>
                            <a href="packageWarranty" class="btn btn-secondary">Cancel</a>
                        </form>
                    </div>
                </div>
                <div class="card shadow-sm mb-4">
                    <div class="card-header">
                        <h3>Extended Warranty History</h3>
                    </div>
                    <div class="card-body">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Extended Warranty Name</th>
                                    <th>Start Extended Warranty</th>
                                    <th>End Extended Warranty</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${extendedWarrantyDetailList}" var="eDetail">
                                    <tr>
                                        <td>${eDetail.extendedWarrantyDetailID}</td>
                                        <td>${eDetail.extendedWarrantyName}</td>
                                        <td>${eDetail.startExtendedWarranty}</td>
                                        <td>${eDetail.endExtendedWarranty}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="card shadow-sm mb-4">
                    <div class="card-header">
                        <h3>Extend Extended Warranty</h3>
                    </div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/extendWarranty" method="post">
                        <p>Select an Extended Warranty option from the list below to extend your warranty:</p>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Period (Months)</th>
                                    <th>Price</th>
                                    <th>Description</th>
                                    <th>Select</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${extendedWarrantyList}" var="ew">
                                    <tr>
                                        <td>${ew.extendedWarrantyID}</td>
                                        <td>${ew.extendedWarrantyName}</td>
                                        <td>${ew.extendedPeriodInMonths}</td>
                                        <td>${ew.price}</td>
                                        <td>${ew.extendedWarrantyDescription}</td>
                                        <td>
                                            <input type="radio" name="extendedWarrantyID" value="${ew.extendedWarrantyID}" required>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        
                            <input type="hidden" name="action" value="extendExtended">
                            <input type="hidden" name="packageWarrantyID" value="${packageWarranty.packageWarrantyID}">
                           
                            <button type="submit" class="btn btn-primary">Extend Extended Warranty</button>
                            <a href="packageWarranty" class="btn btn-secondary">Cancel</a>
                        </form>
                    </div>
                </div>
            </main>
            <jsp:include page="/includes/footer.jsp" />
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
