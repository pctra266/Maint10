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
                <link rel="shortcut icon" href="img/icons/icon-48x48.png" />

        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="../../includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="../../includes/navbar-top.jsp" />
                <main class="content">
                    <div class="d-flex flex-column align-items-center justify-content-center border-5">
                        <h2 class="text-center">Outsource</h2>

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

                        <form action="WarrantyCard/OutsourceRequest" method="post" class="w-50">
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
                            <textarea type="text" name="note" id="note" class="form-control form-control-sm" style="height: 10rem"></textarea>
                            <div class="mt-2 d-flex justify-content-center">
                                <button type="submit" class="btn btn-primary me-3" style="width: 7rem">Send Outsource Request</button>
                                <a href="WarrantyCard/Detail?ID=${warrantyCardID}"  style="width: 7rem" class="btn btn-secondary d-flex justify-content-center align-items-center">Cancel</a> 
                            </div>

                        </form>
                    </div>

                </main>
                <jsp:include page="../../includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>