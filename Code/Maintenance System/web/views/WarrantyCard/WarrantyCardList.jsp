<%-- 
    Document   : ComponentWarehouse
    Created on : Jan 17, 2025, 8:24:34 PM
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

        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link rel="shortcut icon" href="img/icons/icon-48x48.png" />

        <link rel="canonical" href="https://demo-basic.adminkit.io/" />

        <title>Warranty Cards</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            .btn-pagination {
                width: 40px;
                height: 40px;
                display: flex;
                align-items: center;
                justify-content: center;
                text-align: center;
            }
            .btn-sort {
                background: none;
                border: none;
                padding: 0;
                cursor: pointer;
            }

        </style>
    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="../../includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="../../includes/navbar-top.jsp" />
                <main class="content">
                    <h2>Warranty Card</h2>
                    <c:if test="${not empty createStatus}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${createStatus}</strong>
                            </div>
                        </div>
                    </c:if>
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <form action="WarrantyCard/Add" method="POST" enctype="multipart/form-data" style="display: inline;">
                            <button type="submit" class="btn btn-success"><i class="fas fa-add"></i> Create Card</button>
                        </form>
                        <form action="WarrantyCard/Search" method="get" style="display: inline;">
                            <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> Advanced Search</button>
                        </form>
                    </div>
                    <form action="WarrantyCard" method="get" class="row align-items-center">
                        <input type="hidden" name="page" value="${pagination.currentPage}">
                        <input type="hidden" name="sort" value="${pagination.sort}">
                        <input type="hidden" name="order" value="${pagination.order}">
                        <input type="hidden" name="status" value="${pagination.searchValues[1]}" />
                        <div class="col-sm-6 col-md-6">
                            <label>Show 
                                <select name="page-size" class="form-select form-select-sm d-inline-block" style="width: auto;" onchange="this.form.submit()">
                                    <c:forEach items="${pagination.listPageSize}" var="s">
                                        <option value="${s}" ${pagination.pageSize==s?"selected":""}>${s}</option>
                                    </c:forEach>
                                </select> 
                                entries
                            </label>
                        </div>
                        <div class="col-sm-6 col-md-6 text-end">
                            <div class="col-md-3 input-group d-flex justify-content-end">
                                <input type="search" style="flex: 0.5 1 auto" name="search" class="form-control form-control-md" placeholder="Search" value="${pagination.searchValues[0]}" aria-controls="datatables-column-search-text-inputs">
                                <button type="submit" class="btn btn-primary">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search align-middle"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>
                                </button>
                            </div>
                        </div>
                    </form>
                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th style="width:3%">#</th>
                                <th style="width:8%">
                                    Card Code
                                </th>
                                <th style="width:10%">
                                     <form action="WarrantyCard" method="get">
                                        <input type="hidden" name="page" value="${pagination.currentPage}" />                                  
                                        <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                        <input type="hidden" name="search" value="${pagination.searchValues[0]}" />
                                        <input type="hidden" name="status" value="${pagination.searchValues[1]}" />
                                        <input type="hidden" name="sort" value="productCode" />
                                        <input type="hidden" name="order" value="${pagination.sort eq 'productCode' and pagination.order eq 'asc' ? 'desc' : 'asc'}" />
                                        <button type="submit" class="btn-sort">
                                            <i class="align-middle fas fa-fw
                                               ${pagination.sort eq 'productCode' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                            </i>
                                        </button>
                                        Product Code
                                    </form>

                                </th>
                                <th>
                                    Product Name
                                </th>

                                <th style="width:6%">
                                    <form action="WarrantyCard" method="get" class="d-flex">
                                        <input type="hidden" name="page" value="${pagination.currentPage}" />                                  
                                        <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                        <input type="hidden" name="search" value="${pagination.searchValues[0]}" />
                                        <input type="hidden" name="sort" value="${pagination.sort}" />
                                        <input type="hidden" name="order" value="${pagination.order}" />
                                        <select name="status" class="form-select form-select-sm d-inline-block custom-select" 
                                                style="width: auto; padding-right:1px ; border: none; background: transparent; font: inherit; transform: translate(-0.38rem, 0.18rem)" 
                                                onchange="this.form.submit()">
                                            <option disabled selected>Status</option>
                                            <option value="fixing" ${pagination.searchValues[1]=="fixing"?"selected":""}>Fixing</option>
                                            <option value="done" ${pagination.searchValues[1]=="done"?"selected":""}>Done</option>
                                            <option value="completed" ${pagination.searchValues[1]=="completed"?"selected":""}>Completed</option>
                                            <option value="cancel" ${pagination.searchValues[1]=="cancel"?"selected":""}>Cancel</option>
                                        </select> 
                                        <i class="align-middle me-2 fas fa-fw fa-angle-down" style="transform: translate(-0.1rem, 0.7rem)"></i>
                                    </form>
                                </th>

                                <th style="width:10%">
                                    <form action="WarrantyCard" method="get">
                                        <input type="hidden" name="page" value="${pagination.currentPage}" />                                  
                                        <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                        <input type="hidden" name="search" value="${pagination.searchValues[0]}" />
                                        <input type="hidden" name="status" value="${pagination.searchValues[1]}" />
                                        <input type="hidden" name="sort" value="createdDate" />
                                        <input type="hidden" name="order" value="${pagination.sort eq 'createdDate' and pagination.order eq 'asc' ? 'desc' : 'asc'}" />
                                        <button type="submit" class="btn-sort">
                                            <i class="align-middle fas fa-fw
                                               ${pagination.sort eq 'createdDate' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                            </i>
                                        </button>
                                        Created Date
                                    </form>

                                </th>
                                <th style="width:10%">
                                    <form action="WarrantyCard" method="get">
                                        <input type="hidden" name="page" value="${pagination.currentPage}" />                                        
                                        <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                        <input type="hidden" name="search" value="${pagination.searchValues[0]}" />
                                        <input type="hidden" name="status" value="${pagination.searchValues[1]}" />
                                        <input type="hidden" name="sort" value="returnDate" />
                                        <input type="hidden" name="order" value="${pagination.sort eq 'returnDate' and pagination.order eq 'asc' ? 'desc' : 'asc'}" />
                                        <button type="submit" class="btn-sort">
                                            <i class="align-middle fas fa-fw
                                               ${pagination.sort eq 'returnDate' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                            </i>
                                        </button>
                                        Return Date
                                    </form>

                                </th>
                                <th style="width:15%">
                                    Image
                                </th>

                                <th style="width:15%">
                                    Issue
                                </th>

                                <th style="width:8%">Action<a href="?page=${pagination.currentPage}&page-size=${pagination.pageSize}"><i class="fa fa-refresh ms-2"></i></a></th>
                            </tr>
                        </thead>

                        <tbody>

                            <!--varStatus để lấy trạng thái của vòng lặp-->
                            <c:forEach var="card" items="${cardList}" varStatus="status">
                                <tr class="${status.index % 2 == 0 ? 'table-primary' : ''}">
                                    <td>${status.index + 1 + (currentPage - 1) * pageSize}</td>
                                    <td>${card.warrantyCardCode}</td>
                                    <td>${card.productCode}</td>
                                    <td>${card.productName}</td>
                                    <td>${card.warrantyStatus}</td>
                                    <td>${card.createdDate}</td>
                                    <td>${card.returnDate}</td>
                                    <td><img src="${card.image}" width="100%" height="auto" alt="alt"/></td>
                                    <td>${card.issueDescription}</td>
                                    <td class="table-action">
                                        <a href="WarrantyCard/Detail?ID=${card.warrantyCardID}">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-2 align-middle">
                                            <path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path>
                                            </svg>
                                        </a>
                                        <a data-bs-toggle="modal" data-bs-target="#centeredModalPrimary_${card.warrantyCardID}">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash align-middle">
                                            <polyline points="3 6 5 6 21 6"></polyline>
                                            <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                                            </svg>
                                        </a>
                                    </td>
                                </tr>

                                <!-- Modal for each card -->
                            <div class="modal fade" id="centeredModalPrimary_${card.warrantyCardID}" tabindex="-1" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">Delete Confirmation</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body m-3">
                                            <p class="mb-0">Confirm your action. Really want to delete?</p>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                            <a href="WarrantyCard/Delete?ID=${card.warrantyCardID}&page=${pagination.currentPage}&page-size=${pagination.pageSize}&search=${pagination.searchValues[0]}&status=${pagination.searchValues[1]}&sort=${pagination.sort}&order=${pagination.order}" class="btn btn-primary">Delete</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                        </tbody>
                    </table>
                    <c:if test="${totalCards==0}">
                        <div class="alert alert-primary alert-dismissible" role="alert">
                            <div class="alert-message text-center">
                                <strong style="font-size:1.6rem">No suitable card in the filter</strong>
                            </div>
                        </div>

                    </c:if>

                    <jsp:include page="../../includes/pagination.jsp" />

                    <c:if test="${not empty deleteStatus}">
                        <div class="alert alert-warning alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${deleteStatus}</strong>
                            </div>
                        </div>
                    </c:if>
            </div>
        </main>
        <jsp:include page="../../includes/footer.jsp" />
    </div>

</div>

<script src="js/app.js"></script>

</body>

</html>