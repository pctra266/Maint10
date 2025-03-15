<%-- 
    Document   : customerContactForm
    Created on : Mar 16, 2025, 1:37:39 AM
    Author     : Tra Pham
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Contact</title>
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
                            <h1 class="text-center">Customer Contact Form</h1>
                        </div>
                        
                        <c:if test="${success eq 'true'}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        
                        <c:if test="${error eq 'true'}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        
                        <div class="card-body">
                            <form action="customerContact" method="POST">
                                <input type="hidden" name="action" value="createCustomerContact">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label for="Name" class="form-label">Name</label>
                                        <input type="text" class="form-control" name="Name" id="Name" value="${name}" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="Email" class="form-label">Email</label>
                                        <input type="email" class="form-control" name="Email" id="Email" value="${email}">
                                    </div>
                                    <div class="col-md-6">
                                        <label for="Phone" class="form-label">Phone</label>
                                        <input type="text" class="form-control" name="Phone" id="Phone" value="${phone}" required>
                                    </div>
                                    <div class="col-md-12">
                                        <label for="Message" class="form-label">Message</label>
                                        <textarea class="form-control" name="Message" id="Message">${message}</textarea>
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
        <script src="js/app.js"></script>
        <script src="js/format-input.js"></script>
    </body>
</html>
