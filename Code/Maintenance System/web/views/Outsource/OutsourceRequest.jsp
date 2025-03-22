<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Outsource Request</title>
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
                    <h2>Select Repair Contractor</h2>

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

                    <form action="WarrantyCard/OutsourceRequest" method="post">
                        <input type="hidden" name="action" value="requestOutsource">
                        <input type="hidden" name="ID" value="${warrantyCardID}">
                        <div class="mb-3">
                            <label for="contractorID" class="form-label">Select Repair Contractor:</label>
                            <select name="contractorID" id="contractorID" class="form-select" required>
                                <option value="">-- Select a Contractor --</option>
                                <c:forEach var="contractor" items="${contractors}">
                                    <option value="${contractor.staffID}">${contractor.name} (ID: ${contractor.usernameS})</option>
                                </c:forEach>
                            </select>
                        </div>
                        <label for="note" class="form-label">Note:</label>
                        <textarea type="text" name="note" id="note" class="form-control form-control-sm"></textarea>
                        <div class="mt-2">
                           <button type="submit" class="btn btn-primary">Send Outsource Request</button>
                        <a href="WarrantyCard/Detail?ID=${warrantyCardID}" class="btn btn-secondary">Cancel</a> 
                        </div>
                        
                    </form>
                </main>
                <jsp:include page="../../includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>