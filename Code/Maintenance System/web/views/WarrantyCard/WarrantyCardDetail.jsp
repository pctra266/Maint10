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
                            <h2>Repair List</h2>

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
                        <div class="col-md-8">  
                            <div class="row ms-0">
                                
                            <button type="button" class="btn btn-primary mb-2 col-md-3" data-bs-toggle="modal" data-bs-target="#addComponentModal">
                                Add new component to list
                            </button>

                            <form action="/MaintenanceSystem/componentRequest" class="mb-2 col-md-3">
                                <input type="hidden" class="form-control" name="action" value="createComponentRequest" readonly> 
                                <input type="hidden" class="form-control" name="warrantyCardID" value="${card.warrantyCardID}" readonly>  
                                <input type="hidden" class="form-control" name="productCode" value="${card.productCode}" readonly>  
                                <button type="submit" class="btn btn-success"><i class="fas fa-add"></i> Request Component</button>
                            </form>
                            </div>
                            <table class="table table-hover my-0">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Name</th>
                                        <th>Status</th>
                                        <th>Price</th>
                                        <th>Quantity</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="detail" items="${cardDetails}" varStatus="status">
                                        <tr class="${status.index % 2 == 0 ? 'table-primary' : ''}">
                                            <td>${status.index + 1}</td>
                                            <td>${detail.component.componentName}</td>
                                            <td>
                                                <form action="WarrantyCard/Detail" id="updateForm-${status.index}" method="post" class="d-inline">
                                                    <input type="hidden" name="action" value="update">
                                                    <input type="hidden" name="warrantyCardDetailID" value="${detail.warrantyCardDetailID}">
                                                    <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                                    <select name="status" class="form-select">
                                                        <option value="under_warranty" ${detail.status == 'under_warranty' ? 'selected' : ''}>Under Warranty</option>
                                                        <option value="repaired" ${detail.status == 'repaired' ? 'selected' : ''}>Repaired</option>
                                                        <option value="replace" ${detail.status == 'replace' ? 'selected' : ''}>Replace</option>
                                                    </select>
                                            </td>
                                            <td>${detail.price}</td>
                                            <td>
                                                <input type="number" name="quantity" class="form-control form-control-sm" value="${detail.quantity}" min="0">
                                                </form>
                                            </td>
                                            <td class="table-action">
                                                <button type="submit" form="updateForm-${status.index}" class="btn btn-sm me-1 save" disabled title="Save">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-save align-middle"><path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"></path><polyline points="17 21 17 13 7 13 7 21"></polyline><polyline points="7 3 7 8 15 8"></polyline></svg>
                                                </button>                                                <form action="WarrantyCard/Detail" method="post" class="d-inline" id="deleteForm-${status.index}">
                                                    <input type="hidden" name="action" value="delete">
                                                    <input type="hidden" name="warrantyCardDetailID" value="${detail.warrantyCardDetailID}">
                                                    <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                                    <button type="submit" class="btn  btn-sm" onclick="return confirm('Are you sure you want to delete this component?');" title="Delete">
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash align-middle">
                                                        <polyline points="3 6 5 6 21 6"></polyline>
                                                        <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                                                        </svg>
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>     
                                </tbody>
                            </table>
                        </div> 

                        <div class="col-md-4">
                            <label class="form-label">Image</label>
                            <div>
                                <img src="${card.image}" alt="Product Image" style="max-width: 100%; height: auto;">
                            </div>
                        </div>
                    </div>
                    <h2>Warranty Card Detail</h2>

                    <div class="row ms-0">       

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

                    </div>
                </main>
            </div>


            <jsp:include page="../../includes/footer.jsp" />
        </div>

    </div>

    <script src="js/app.js"></script>
    <script src="js/format-input.js"></script>
    <script>
                                                        document.querySelectorAll('.form-select, .form-control').forEach(input => {
                                                            input.addEventListener('input', function () {
                                                                this.closest('tr').querySelector('.save').disabled = false;
                                                            });
                                                        });
                                                        document.querySelectorAll('.save').forEach(button => {
                                                            button.disabled = true;
                                                        });
    </script>
</body>

</html>
