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
                <main class="content">
                    <div class="card">
                        <div class="card-header">
                            <h1 class="text-center">Customer Contact Form</h1>
                        </div>
                        
                        <c:if  test="${not empty success}">
                            <div class="alert-success">
                                ${success}
                            </div>
                            
                        </c:if>
                        
                        <c:if  test="${not empty error}">
                            <div class="alert-danger">
                                 ${error}
                            </div>
                        </c:if>
                        
                        <div class="card-body">
                            <form action="customerContact" method="get">
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
                                    <a class="btn btn-secondary" href="Home">Cancel</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </main>
        <script src="js/app.js"></script>
        <script src="js/format-input.js"></script>
    </body>
</html>
