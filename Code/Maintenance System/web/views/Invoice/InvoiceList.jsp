<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Invoice List</title>
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
                    <div class="col-md-12 row d-flex justify-content-center">
                        <div class="col-md-10">
                            <h1 class="text-center">Invoices for Warranty Card: ${warrantyCard.warrantyCardCode}</h1>

                            <!-- Nút Back -->
                            <a href="WarrantyCard/Detail?ID=${warrantyCard.warrantyCardID}" class="btn btn-primary mb-3">
                                <i class="fas fa-arrow-left"></i> Back to Warranty Card
                            </a>

                            <!-- Danh sách invoice -->
                            <c:choose>
                                <c:when test="${not empty invoices}">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Invoice Number</th>
                                                <th>Type</th>
                                                <th>Amount</th>
                                                <th>Issued Date</th>
                                                <th>Due Date</th>
                                                <th>Status</th>
                                                <th>Created By</th>
                                                <th>Received By</th>
                                                <th>Customer ID</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="invoice" items="${invoices}" varStatus="loop">
                                                <tr>
                                                    <td>${loop.index + 1}</td>
                                                    <td>${invoice.invoiceNumber}</td>
                                                    <td>${invoice.invoiceType}</td>
                                                    <td>${invoice.amount}</td>
                                                    <td>${invoice.issuedDate}</td>
                                                    <td>${invoice.dueDate}</td>
                                                    <td>${invoice.status}</td>
                                                    <td>${invoice.createdBy}</td>
                                                    <td>${invoice.receivedBy != null ? invoice.receivedBy : 'N/A'}</td>
                                                    <td>${invoice.customerID != null ? invoice.customerID : 'N/A'}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:when>
                                <c:otherwise>
                                    <p class="text-center">No invoices found for this warranty card.</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </main>
                <jsp:include page="../../includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>