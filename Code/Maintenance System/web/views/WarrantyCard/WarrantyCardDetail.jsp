<%-- 
    Document   : WarrantyCardDetail
    Created on : Feb 19, 2025, 12:05:43 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="Responsive Admin &amp; Dashboard Template based on Bootstrap 5">
        <meta name="author" content="AdminKit">
        <meta name="keywords" content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">
        <base href="${pageContext.request.contextPath}/">

        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link rel="shortcut icon" href="icons/icon-48x48.png" />

        <link rel="canonical" href="https://demo-basic.adminkit.io/" />

        <title>Warranty Card</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="../../includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="../../includes/navbar-top.jsp" />
                <main class="content">
                    <form action="Redirect">
                        <input type="hidden" name="target" value="${backUrl}">
                        <button type="submit" class="btn btn-primary  d-flex align-items-center justify-content-center" style="transform:translate(-30%,-60%); height: 2.5rem; width: 5.2rem"><i class="fas fa-arrow-left fa-4"></i> <span class="ms-2">Back</span> </button>                 
                    </form>

                    <h2>Warranty Card Detail</h2>
                    <form action="/MaintenanceSystem/componentRequest">
                                       <input type="hidden" class="form-control" name="action" value="createComponentRequest" readonly> 
                                       <input type="hidden" class="form-control" name="warrantyCardID" value="${card.warrantyCardID}" readonly>  
                                       <input type="hidden" class="form-control" name="productCode" value="${card.productCode}" readonly>  
                                        <button type="submit" class="btn btn-success"><i class="fas fa-add"></i> Request Component</button>
                    </form>
                    <!--                                Alert khi du lieu truyen sang sever sai-->
                    <c:if test="${not empty codeAlert}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${codeAlert}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty nameAlert}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${nameAlert}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty quantityAlert}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${quantityAlert}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty pictureAlert}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${pictureAlert}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty priceAlert}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${priceAlert}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty addAlert1}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${addAlert1}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty updateAlert1}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${updateAlert1}</strong>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty updateAlert0}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${updateAlert0}</strong>
                            </div>
                        </div>
                    </c:if>
                <div class="row">
            <div class="col-md-8 row g-2">
               <input type="hidden" class="form-control" value="${card.warrantyCardID}" readonly>  
               <div class="col-md-12">
                    <label class="form-label">Customer Name</label>
                    <input type="text" class="form-control" value="${card.customerName}" readonly>
                </div>
                <div class="col-md-12">
                    <label class="form-label">Customer Phone</label>
                    <input type="text" class="form-control" value="${card.customerPhone}" readonly>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Warranty Code</label>
                    <input type="text" class="form-control" value="${card.warrantyCardCode}" readonly>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Product Code</label>
                    <input type="text" class="form-control" value="${card.productDetailCode}" readonly>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Product Name</label>
                    <input type="text" class="form-control" value="${card.productName}" readonly>
                </div>
                <div class="col-md-12">
                    <label class="form-label">Issue Description</label>
                    <textarea class="form-control" readonly>${card.issueDescription}</textarea>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Warranty Status</label>
                    <input type="text" class="form-control" value="${card.warrantyStatus}" readonly>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Canceled Date</label>
                    <input type="text" class="form-control" value="${card.getFormatCanceldDate()}" readonly>
                </div>
                <div class="col-md-6"></div>
                <div class="col-md-3">
                    <label class="form-label">Created Date</label>
                    <input type="text" class="form-control" value="${card.getFormatCreatedDate()}" readonly>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Estimated Return Date</label>
                    <input type="text" class="form-control" value="${card.getFormatReturnDate()}" readonly>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Repaired Date</label>
                    <input type="text" class="form-control" value="${card.getFormatDonedDate()}" readonly>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Completed Date</label>
                    <input type="text" class="form-control" value="${card.getFormatCompletedDate()}" readonly>
                </div>
            </div>
            <div class="col-md-4">
                <label class="form-label">Image</label>
                <div>
                    <img src="${card.image}" alt="Product Image" style="max-width: 100%; height: auto;">
                </div>
            </div>
        </div>
    </div>

                </main>

                <jsp:include page="../../includes/footer.jsp" />
            </div>

        </div>

        <script src="js/app.js"></script>
        <script src="js/format-input.js"></script>
        <script>
                                function previewImage(event) {
                                    const reader = new FileReader();
                                    reader.onload = function () {
                                        const output = document.getElementById('currentImage');
                                        output.src = reader.result;
                                    };
                                    reader.readAsDataURL(event.target.files[0]);
                                }
        </script>
    </body>

</html>
