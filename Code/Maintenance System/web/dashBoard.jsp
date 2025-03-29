<%-- 
    Document   : updateProduct1
    Created on : Feb 6, 2025, 5:07:44 AM
    Author     : sonNH
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Responsive Admin &amp; Dashboard Template based on Bootstrap 5">
    <meta name="author" content="AdminKit">
    <meta name="keywords" content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="shortcut icon" href="img/icons/icon-48x48.png" />
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&amp;display=swap" rel="stylesheet">
    <link class="js-stylesheet" href="css/light.css" rel="stylesheet">
    <style>
        .card {
            cursor: pointer;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .card:hover {
            transform: scale(1.05);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
        }
    </style>
</head>
<body data-theme="default" data-layout="fluid" data-sidebar-position="left" data-sidebar-layout="default">
    <div class="wrapper">
        <jsp:include page="/includes/navbar-left.jsp" />
        <div class="main">
            <jsp:include page="/includes/navbar-top.jsp" />
            <main class="content">


                <div class="container-fluid p-0">
                    <c:if test="${not empty sessionScope.customer}">
                        <!-- Khách hàng -->
                        <a href="profile" class="btn btn-primary float-end mt-n1">
                            <i class="fas fa-user"></i>
                        </a>
                    </c:if>

                    <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '2'}">
                        <!-- Nhân viên (ví dụ: nhân viên kinh doanh) -->
                        <a href="profile" class="btn btn-primary float-end mt-n1">
                            <i class="fas fa-user-tie"></i>
                        </a>
                    </c:if>

                    <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '5'}">
                        <!-- Kỹ thuật viên/Support (ví dụ) -->
                        <a href="profile" class="btn btn-primary float-end mt-n1">
                            <i class="fas fa-cogs"></i>
                        </a>
                    </c:if>

                    <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '3'}">
                        <!-- Quản lý (ví dụ: quản lý dự án) -->
                        <a href="profile" class="btn btn-primary float-end mt-n1">
                            <i class="fas fa-chart-line"></i>
                        </a>
                    </c:if>

                    <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '4'}">
                        <!-- Giám đốc hoặc quản lý cấp cao -->
                        <a href="profile" class="btn btn-primary float-end mt-n1">
                            <i class="fas fa-briefcase"></i>
                        </a>
                    </c:if>

                    <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '1'}">
                        <!-- Quản trị viên (admin) -->
                        <a href="profile" class="btn btn-primary float-end mt-n1">
                            <i class="fas fa-shield-alt"></i>
                        </a>
                    </c:if>


                    <div class="mb-3" >
                        <h1 class="h3 d-inline align-middle" >
                            <c:if test="${not empty sessionScope.customer}">
                                Customer
                            </c:if>

                            <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '2'}">
                                Technician
                            </c:if>

                            <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '5'}">
                                Customer Service Agent
                            </c:if>

                            <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '3'}">
                                Inventory Manager
                            </c:if>
                            <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '4'}">
                                Repair Contractor
                            </c:if>
                            <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '1'}">
                                Admin
                            </c:if>
                        </h1>
                        <a class="badge bg-primary ms-2" href="dashBoard" target="_blank">
                            Dash Board
                            <i class="fas fa-fw fa-external-link-alt">
                            </i>
                        </a>
                    </div>

                    <div class="row">

                        <c:if test="${not empty sessionScope.customer}">

                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'feedback?action=viewFeedbackDashboard';">
                                <div class="card">
                                    <img class="card-img-top" src="img/photos/feedback.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">FEEDBACK</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>                               
                                </div>
                            </div>
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'purchaseproduct';">
                                <div class="card">
                                    <img class="card-img-top" src="img/photos/purchase-product.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">PURCHASED PRODUCT</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>                               
                                </div>
                            </div>

                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'yourwarrantycard';">
                                <div class="card">
                                    <img class="card-img-top" src="img/photos/your-wc.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">MY WARRANTY CARD</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>                               
                                </div>
                            </div>

                        </c:if>

                        <c:if test="${staff.hasPermissions('VIEW_COMPONENTS_WAREHOUSE')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'ComponentWarehouse';">
                                <div class="card">
                                    <img class="card-img-top" src="img/photos/component.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">COMPONENT</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${staff.hasPermissions('VIEW_PRODUCT')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'viewProduct';">
                                <div class="card">
                                    <img class="card-img-top" src="img/photos/product.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">PRODUCT</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>                               
                                </div>
                            </div>
                        </c:if>
                        
                        <c:if test="${staff.hasPermissions('VIEW_PRODUCT_TYPE')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'ProductType';">
                                <div class="card">
                                    <img class="card-img-top" src="img/photos/product_type.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">PRODUCT TYPE</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>                               
                                </div>
                            </div>
                        </c:if>
                        
                        <c:if test="${staff.hasPermissions('VIEW_WARRANTY_CARD')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'WarrantyCard';">
                                <div class="card">
                                    <img class="card-img-top" src="img/photos/warranty-card.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">WARRANTY CARD</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>                               
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${staff.hasPermissions('VIEW_LIST_CUSTOMER')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'customer';">
                                <div class="card">
                                    <img class="card-img-top" src="img/photos/customer.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">CUSTOMER</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>                               
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${staff.hasPermissions('MANAGE_COMPONENT_TYPE')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'ComponentType';">
                                <div class="card">
                                    <img class="card-img-top" src="img/photos/component-type.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">COMPONENT TYPE</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${staff.hasPermissions('MANAGE_BRAND')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'Brand';">
                                <div class="card">
                                    <img class="card-img-top" src="img/photos/brand.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">BRAND</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${staff.hasPermissions('VIEW_PACKAGE_WARRANTY')}">

                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'packageWarranty?action=view';">
                                <div class="card">
                                    <img class="card-img-top" src="img/photos/package-warranty.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">PACKAGE WARRANTY CARD</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>                               
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${staff.hasPermissions('VIEW_EXTENDED_WARRANTY')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'extendedWarranty';">
                                <div class="card">
                                    <img class="card-img-top" src="img/photos/extended-warranty.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">EXTENDED WARRANTY</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>                               
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${staff.hasPermissions('CUSTOMER_CONTACT')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'customerContact?action=view';">
                                <div class="card">
                                    <img class="card-img-top" src="img/photos/customer-contact.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">CUSTOMER CONTACT</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>                               
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${staff.hasPermissions('VIEW_COMPONENT_REQUEST_DASHBOARD')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'componentRequest?action=viewComponentRequestDashboard';">
                                <div class="card">
                                    <img class="card-img-top"  src="img/photos/component-request.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">COMPONENT REQUEST</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${staff.hasPermissions('VIEW_LIST_COMPONENT_REQUEST')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'componentRequest?action=viewListComponentRequest';">
                                <div class="card">
                                    <img class="card-img-top"  src="img/photos/listcomponent-request.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">LIST COMPONENT REQUEST</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${staff.hasPermissions('COMPONENT_REQUEST_RESPONSIBLE')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'componentRequestResponsible?action=viewComponentRequestResponsible';">
                                <div class="card">
                                    <img class="card-img-top"  src="img/photos/component-requestlog.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">COMPONENT REQUEST LOG</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        
                        <c:if test="${staff.hasPermissions('CHAT_HISTORY')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'chatHistory';">
                                <div class="card">
                                    <img class="card-img-top"  src="" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">CHAT HISTORY</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${staff.hasPermissions('WARRANTY_CARD_REPAIR_CONTRACTOR')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'warrantyCardRepairContractor';">
                                <div class="card">
                                    <img class="card-img-top" src="img/photos/wcrepair-contractor.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">WARRANTY CARD</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${staff.hasPermissions('VIEW_STAFF')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'StaffController';">
                                <div class="card">
                                    <img class="card-img-top" src="img/photos/staff.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">STAFF</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>                               
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${staff.hasPermissions('VIEW_FEEDBACK')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'feedback?action=viewFeedback';">
                                <div class="card">
                                    <img class="card-img-top" src="img/photos/feedback.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">FEEDBACK</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${staff.hasPermissions('ADMIN_DASHBOARD_JSP')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'adminDashboard.jsp';">
                                <div class="card">
                                    <img class="card-img-top" src="img/photos/setting-maxsie.jpg" alt="Unsplash">
                                    <div class="card-header px-4 pt-4" style="text-align: center">                       
                                        <h5 class="card-title mb-0">SETTING</h5>
                                        <div class="badge bg-info my-2">In progress</div>
                                    </div>
                                </div>
                            </div>

                        </c:if>
                        <c:if test="${staff.hasPermissions('CUSTOMIZE_HOMEPAGE')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'customizeHomepage';">
                            <div class="card">
                                <img class="card-img-top" src="img/photos/customize-homepage.jpg" alt="Unsplash">
                                <div class="card-header px-4 pt-4" style="text-align: center">                       
                                    <h5 class="card-title mb-0">CUSTOMIZE HOMEPAGE</h5>
                                    <div class="badge bg-info my-2">In progress</div>
                                </div>                               
                            </div>
                        </div>

                        </c:if>
                        <c:if test="${staff.hasPermissions('ADMIN_DASHBOARD_JSP')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'adminDashboard.jsp';">
                            <div class="card">
                                <img class="card-img-top" src="" alt="Unsplash">
                                <div class="card-header px-4 pt-4" style="text-align: center">                       
                                    <h5 class="card-title mb-0">SETTING FILE SIZE LIMIT</h5>
                                    <div class="badge bg-info my-2">In progress</div>
                                </div>                               
                            </div>
                        </div>

                        </c:if>
                        <c:if test="${staff.hasPermissions('PERMISSION')}">
                            <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'permissions';">
                            <div class="card">
                                <img class="card-img-top" src="" alt="Unsplash">
                                <div class="card-header px-4 pt-4" style="text-align: center">                       
                                    <h5 class="card-title mb-0">PERMISSION</h5>
                                    <div class="badge bg-info my-2">In progress</div>
                                </div>                               
                            </div>
                        </div>

                        </c:if>
                        <c:if test="${staff.hasPermissions('REPORT_COMPONENT_JSP')}">
                             <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'ReportComponent.jsp';">
                            <div class="card">
                                <img class="card-img-top" src="img/photos/component-report.jpg" alt="Unsplash">
                                <div class="card-header px-4 pt-4" style="text-align: center">                       
                                    <h5 class="card-title mb-0">COMPONENT REPORT</h5>
                                    <div class="badge bg-info my-2">In progress</div>
                                </div>                               
                            </div>
                        </div>
                        </c:if>
                        <c:if test="${staff.hasPermissions('REPORT_WARRANTY_CARD_JSP')}">
                                 <div class="col-12 col-md-6 col-lg-3 clickable-card" onclick="window.location.href = 'reportWarrantyCard.jsp';">
                            <div class="card">
                                <img class="card-img-top" src="img/photos/report-wc.jpg" alt="Unsplash">
                                <div class="card-header px-4 pt-4" style="text-align: center">                       
                                    <h5 class="card-title mb-0">REPORT WARRANTY CARD</h5>
                                    <div class="badge bg-info my-2">In progress</div>
                                </div>                               
                            </div>
                        </div>

                        </c:if>

                    </div>
                </div>

            </main>
            <jsp:include page="/includes/footer.jsp" />
        </div>
    </div>
    <script src="js/app.js"></script>

</body>
</html>
