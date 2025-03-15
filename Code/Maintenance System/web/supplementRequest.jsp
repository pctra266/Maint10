<%-- 
    Document   : supplementRequest
    Created on : Mar 15, 2025, 7:07:33 PM
    Author     : Tra Pham
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Feedback</title>
        <link href="css/light.css" rel="stylesheet">
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
            <h1 class="text-center">Supplement Request</h1>
        </div>
        
        <c:if test="${param.success eq 'true'}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                Request submitted successfully!
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <c:if test="${param.error eq 'true'}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                Failed to submit request. Please try again.
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <div class="card-body">
            <form action="supplementRequest" method="POST" >
                <div class="row g-3">
                    <div class="col-md-6">
                        <label for="Name" class="form-label">Component Name</label>
                        <input type="text" class="form-control" name="Name" id="Name" value="${name}" required>
                    </div>
                    <div class="col-md-3">
                        <label for="ComponentType" class="form-label">Component Type</label>
                        <select name="ComponentType" class="form-select" id="ComponentType" required>
                            <option value disabled selected>Choose</option>
                            <option value="product" ${ComponentType eq 'product' ? 'selected' : ''}>Product</option>
                            <option value="unknown product" ${ComponentType eq 'unknown product' ? 'selected' : ''}>Unknown Product</option>
                        </select>
                    </div>
                            <div class="col-md-3">
                                <label for="Type" class="form-label">Type</label>
                                <select name="TypeID" class="form-select" id="Type">
                                    <option value disabled selected>Choose</option>
                                    <c:forEach var="t" items="${typeList}">
                                        <option value="${t}" ${t eq type ? "selected" : ""}>${t}</option>
                                    </c:forEach>
                                        <option value="Other" >Other</option>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <label for="Brand" class="form-label">Brand</label>
                                <select name="BrandID" class="form-select" id="Brand">
                                    <option value disabled selected>Choose</option>
                                    <c:forEach var="b" items="${brandList}">
                                        <option value="${b}" ${b eq brand ? "selected" : ""}>${b}</option>
                                    </c:forEach>
                                        <option value="Other">Other</option>
                                </select>
                            </div>
                    <!--<input type="hidden" name="RequestedBy" value="${sessionScope.staffID}">-->
                    <div class="col-md-12">
                        <label for="Note" class="form-label">Note</label>
                        <textarea class="form-control" name="Note"></textarea>
                    </div>
                </div>
                <div class="mt-4">
                    <button class="btn btn-primary" type="submit">Submit</button>
                </div>
            </form>
        </div>
    </div>
</main>


                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>

        <script src="js/app.js"></script><div class="notyf"></div><div class="notyf-announcer" aria-atomic="true" aria-live="polite" style="border: 0px; clip: rect(0px, 0px, 0px, 0px); height: 1px; margin: -1px; overflow: hidden; padding: 0px; position: absolute; width: 1px; outline: 0px;"></div>
        <script src="js/format-input.js"></script>

<svg id="SvgjsSvg1001" width="2" height="0" xmlns="http://www.w3.org/2000/svg" version="1.1" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:svgjs="http://svgjs.dev" style="overflow: hidden; top: -100%; left: -100%; position: absolute; opacity: 0;"><defs id="SvgjsDefs1002"></defs><polyline id="SvgjsPolyline1003" points="0,0"></polyline><path id="SvgjsPath1004" d="M0 0 "></path></svg></body>
</html>
