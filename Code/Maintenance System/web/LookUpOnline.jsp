<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Responsive Admin & Dashboard Template based on Bootstrap 5">
    <meta name="author" content="AdminKit">
    <meta name="keywords" content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">
    
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="shortcut icon" href="img/icons/icon-48x48.png" />
    <link href="css/light.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    <title>Look Up Online</title>
</head>
<body>
<main class="d-flex w-100">
    <div class="container d-flex flex-column">
        <div class="row vh-100">
            <div class="col-sm-10 col-md-8 col-lg-6 col-xl-5 mx-auto d-table h-100">
                <div class="d-table-cell align-middle">
                    
                    <div class="text-center mt-4">
                        <h1 class="h2">Look Up Online</h1>
                      
                    </div>

                    <div class="card">
                        <div class="card-body">
                            <div class="m-sm-3">
                                
                               

                                <form action="lookuponline" method="post" >
                                    
                                    <!-- Nhập số điện thoại -->
                                    <div class="mb-3">
                                        <label class="form-label">Phone</label>
                                        <input class="form-control form-control-lg ${not empty requestScope.phoneError ? 'is-invalid' : ''}" 
                                               type="text" name="phone" value="${requestScope.phone}"
                                               placeholder="Enter customer's phone number"/>
                                        <c:if test="${not empty requestScope.phoneError}">
                                            <div class="invalid-feedback">${requestScope.phoneError}</div>
                                        </c:if>
                                    </div>

                                    <!-- Nhập mã bảo hành -->
                                    <div class="mb-3">
                                        <label class="form-label">Warranty Card Code</label>
                                        <input class="form-control form-control-lg ${not empty requestScope.codeError ? 'is-invalid' : ''}" 
                                               type="text" name="code" value="${requestScope.warrantyCode}"
                                               placeholder="Enter customer's warranty card code"/>
                                        <c:if test="${not empty requestScope.codeError}">
                                            <div class="invalid-feedback">${requestScope.codeError}</div>
                                        </c:if>
                                    </div>

                                    <!-- Nút submit -->
                                    <div class="d-grid gap-2 mt-3">
                                        <button type="submit" class="btn btn-lg btn-primary">Check</button>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</main>

<script src="js/app.js"></script>
</body>
</html>
