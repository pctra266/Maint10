<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav id="sidebar" class="sidebar js-sidebar collapsed">
    <div class="sidebar-content js-simplebar" data-simplebar="init"><div class="simplebar-wrapper" style="margin: 0px;"><div class="simplebar-height-auto-observer-wrapper"><div class="simplebar-height-auto-observer"></div></div><div class="simplebar-mask"><div class="simplebar-offset" style="right: 0px; bottom: 0px;"><div class="simplebar-content-wrapper" tabindex="0" role="region" aria-label="scrollable content" style="height: 100%; overflow: hidden scroll;"><div class="simplebar-content" style="padding: 0px;">
                            <a class="sidebar-brand" href="Home">
                                <img style="height: 10vh;" src="${pageContext.request.contextPath}/img/logo/logoDesign3.png" alt="Main10 Logo" class="logo-img bg-primary bg-opacity-50 ">
                            </a>

                            <!-- Staff -->
                            <c:if test="${not empty sessionScope.staff}">
                                <div class="sidebar-user">
                                    <div class="d-flex justify-content-center">
                                        <div class="flex-shrink-0">
                                            <img src="${sessionScope.staff.image}" class="avatar img-fluid rounded me-1" alt="${sessionScope.staff.name}" />
                                        </div>
                                        <div class="flex-grow-1 ps-2">
                                            <a class="sidebar-user-title dropdown-toggle" href="#" data-bs-toggle="dropdown">
                                                ${sessionScope.staff.name}
                                            </a>
                                            <div class="dropdown-menu dropdown-menu-start">
                                                <a class="dropdown-item" href="profile"><i class="align-middle me-1" data-feather="user"></i> Profile</a>
                                                <a class="dropdown-item" href="ChangePasswordForm.jsp"><i class="align-middle me-1" data-feather="pie-chart"></i> Change Password</a>
                                                <div class="dropdown-divider"></div>
                                                <a class="dropdown-item" href="#"><i class="align-middle me-1" data-feather="help-circle"></i> Help Center</a>
                                                <div class="dropdown-divider"></div>
                                                <a class="dropdown-item" href="logout">Log out</a>
                                            </div>
                                            <div class="sidebar-user-subtitle">Employee</div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <!-- Customer -->
                            <c:if test="${not empty sessionScope.customer}">
                                <div class="sidebar-user">
                                    <div class="d-flex justify-content-center">

                                        <div class="flex-shrink-0">
                                            <img src="${sessionScope.customer.image}" class="avatar img-fluid rounded me-1" alt="${sessionScope.customer.name}" />
                                        </div>

                                        <div class="flex-grow-1 ps-2">
                                            <a class="sidebar-user-title dropdown-toggle" href="#" data-bs-toggle="dropdown">
                                                ${sessionScope.customer.name}
                                            </a>
                                            <div class="dropdown-menu dropdown-menu-start">
                                                <a class="dropdown-item" href="pages-profile.html">
                                                    <i class="align-middle me-1" data-feather="user"></i> 
                                                    Profile
                                                </a>
                                                <a class="dropdown-item" href="ChangePasswordForm.jsp">
                                                    <i class="align-middle me-1" data-feather="pie-chart"></i> 
                                                    Change Password
                                                </a>

                                                <div class="dropdown-divider"></div>
                                                <a class="dropdown-item" href="pages-settings.html">
                                                    <i class="align-middle me-1" data-feather="settings"></i> 
                                                    Settings & Privacy
                                                </a>
                                                <a class="dropdown-item" href="#">
                                                    <i class="align-middle me-1" data-feather="help-circle"></i> 
                                                    Help Center
                                                </a>
                                                <div class="dropdown-divider"></div>
                                                <a class="dropdown-item" href="logout">Log out</a>
                                            </div>
                                            <div class="sidebar-user-subtitle">Customer</div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <!-- Guest -->
                            <c:if test="${empty sessionScope.staff and empty sessionScope.customer}">
                                <button><a href="LoginForm.jsp">Login</a></button>
                            </c:if>
                            <!--END Guest -->

                            <c:if test="${not empty sessionScope.customer}">
                                <ul class="sidebar-nav">
                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="profile">
                                            <!-- Icon user (gi? nguyên) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-user align-middle">
                                            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                                            <circle cx="12" cy="7" r="4"></circle>
                                            </svg> 
                                            <span class="align-middle">Profile</span>
                                        </a>
                                    </li>


                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="InvoiceController">
                                            <!-- ??i sang icon file (bi?u t??ng cho Invoice) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file align-middle">
                                            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                                            <polyline points="14 2 14 8 20 8"></polyline>
                                            </svg> 
                                            <span class="align-middle">Invoice</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="feedback?action=viewFeedbackDashboard">
                                            <!-- ??i sang icon message-circle (Feedback dành cho Customer) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-message-circle align-middle">
                                            <path d="M21 11.5a8.38 8.38 0 0 1-1.9 5.4 8.5 8.5 0 0 1-15.1 0A8.38 8.38 0 0 1 3 11.5a8.5 8.5 0 0 1 8.5-8.5h.1A8.5 8.5 0 0 1 21 11.5z"></path>
                                            </svg> 
                                            <span class="align-middle">Feedback</span>
                                        </a>
                                    </li>


                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="packageWarranty?action=view">
                                            <!-- ??i sang icon shield (List Package Warranty) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shield align-middle">
                                            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
                                            </svg> 
                                            <span class="align-middle">List Package Warranty</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="extendedWarranty">
                                            <!-- ??i sang icon shield-off (Extended Warranty List) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shield-off align-middle">
                                            <path d="M3 3l18 18"></path>
                                            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
                                            </svg> 
                                            <span class="align-middle">Extended Warranty List</span>
                                        </a>
                                    </li>


                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="yourwarrantycard">
                                            <!-- Gi? nguyên icon file-text (Your Warranty Card) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text align-middle">
                                            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                                            <polyline points="14 2 14 8 20 8"></polyline>
                                            <line x1="16" y1="13" x2="8" y2="13"></line>
                                            <line x1="16" y1="17" x2="8" y2="17"></line>
                                            </svg> 
                                            <span class="align-middle">Your Warranty Card</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="purchaseproduct">
                                            <!-- ??i sang icon shopping-bag (Purchase Product) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shopping-bag align-middle">
                                            <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z"></path>
                                            <line x1="3" y1="6" x2="21" y2="6"></line>
                                            <path d="M16 10a4 4 0 0 1-8 0"></path>
                                            </svg> 
                                            <span class="align-middle">Purchase Product</span>
                                        </a>
                                    </li>

                                </ul>
                            </c:if>

                            <ul class="sidebar-nav">
                        <!-- Profile -->
                        <c:if test="${not empty sessionScope.staff}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="profile">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-user align-middle">
                                        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                                        <circle cx="12" cy="7" r="4"></circle>
                                    </svg> 
                                    <span class="align-middle">Profile</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Component -->
                        <c:if test="${staff.hasPermissions('VIEW_COMPONENTS_WAREHOUSE')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="ComponentWarehouse">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-box align-middle">
                                        <path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4a2 2 0 0 0 1-1.73z"></path>
                                        <polyline points="3.27 6.96 12 12.01 20.73 6.96"></polyline>
                                        <line x1="12" y1="22.08" x2="12" y2="12"></line>
                                    </svg>
                                    <span class="align-middle">Component</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Product -->
                        <c:if test="${staff.hasPermissions('VIEW_PRODUCT')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="viewProduct">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-package align-middle">
                                        <polyline points="16.5 9.5 12 14 7.5 9.5"></polyline>
                                        <rect x="1" y="5" width="22" height="14" rx="2" ry="2"></rect>
                                        <line x1="1" y1="10" x2="23" y2="10"></line>
                                    </svg>
                                    <span class="align-middle">Product</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Product Type -->
                        <c:if test="${staff.hasPermissions('VIEW_PRODUCT_TYPE')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="ProductType">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-layers align-middle">
                                        <polygon points="12 2 2 7 12 12 22 7 12 2"></polygon>
                                        <polyline points="2 17 12 22 22 17"></polyline>
                                        <polyline points="2 12 12 17 22 12"></polyline>
                                    </svg>
                                    <span class="align-middle">Product Type</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Warranty Card -->
                        <c:if test="${staff.hasPermissions('VIEW_WARRANTY_CARD')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="WarrantyCard">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card align-middle">
                                        <rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect>
                                        <line x1="1" y1="10" x2="23" y2="10"></line>
                                    </svg>
                                    <span class="align-middle">Warranty Card</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Customer -->
                        <c:if test="${staff.hasPermissions('VIEW_LIST_CUSTOMER')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="customer">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users align-middle">
                                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                                        <circle cx="9" cy="7" r="4"></circle>
                                        <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                                        <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                                    </svg>
                                    <span class="align-middle">Customer</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Component Type -->
                        <c:if test="${staff.hasPermissions('MANAGE_COMPONENT_TYPE')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="ComponentType">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-settings align-middle">
                                        <circle cx="12" cy="12" r="3"></circle>
                                        <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1-2-2 2 2 0 0 1 2-2h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 2-2 2 2 0 0 1 2 2v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 2 2 2 2 0 0 1-2 2h-.09a1.65 1.65 0 0 0-1.51 1z"></path>
                                    </svg>
                                    <span class="align-middle">Component Type</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Brand -->
                        <c:if test="${staff.hasPermissions('MANAGE_BRAND')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="Brand">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-tag align-middle">
                                        <path d="M20.59 13.41l-7.17 7.17a2 2 0 0 1-2.83 0L2 12V2h10l8.59 8.59a2 2 0 0 1 0 2.82z"></path>
                                        <line x1="7" y1="7" x2="7.01" y2="7"></line>
                                    </svg>
                                    <span class="align-middle">Brand</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Package Warranty -->
                        <c:if test="${staff.hasPermissions('VIEW_PACKAGE_WARRANTY')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="packageWarranty?action=view">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-gift align-middle">
                                        <polyline points="20 12 20 22 4 22 4 12"></polyline>
                                        <rect x="2" y="7" width="20" height="5"></rect>
                                        <line x1="12" y1="22" x2="12" y2="7"></line>
                                        <path d="M12 7H7.5a2.5 2.5 0 0 1 0-5C11 2 12 7 12 7z"></path>
                                        <path d="M12 7h4.5a2.5 2.5 0 0 0 0-5C13 2 12 7 12 7z"></path>
                                    </svg>
                                    <span class="align-middle">Package Warranty</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Extended Warranty -->
                        <c:if test="${staff.hasPermissions('VIEW_EXTENDED_WARRANTY')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="extendedWarranty">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-clock align-middle">
                                        <circle cx="12" cy="12" r="10"></circle>
                                        <polyline points="12 6 12 12 16 14"></polyline>
                                    </svg>
                                    <span class="align-middle">Extended Warranty</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Customer Contact -->
                        <c:if test="${staff.hasPermissions('CUSTOMER_CONTACT')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="customerContact?action=view">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-phone align-middle">
                                        <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"></path>
                                    </svg>
                                    <span class="align-middle">Customer Contact</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Component Request -->
                        <c:if test="${staff.hasPermissions('VIEW_COMPONENT_REQUEST_DASHBOARD')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="componentRequest?action=viewComponentRequestDashboard">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-truck align-middle">
                                        <rect x="1" y="3" width="15" height="13"></rect>
                                        <polygon points="16 8 20 8 23 11 23 16 16 16 16 8"></polygon>
                                        <circle cx="5.5" cy="18.5" r="2.5"></circle>
                                        <circle cx="18.5" cy="18.5" r="2.5"></circle>
                                    </svg>
                                    <span class="align-middle">Component Request</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- List Component Request -->
                        <c:if test="${staff.hasPermissions('VIEW_LIST_COMPONENT_REQUEST')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="componentRequest?action=viewListComponentRequest">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-list align-middle">
                                        <line x1="8" y1="6" x2="21" y2="6"></line>
                                        <line x1="8" y1="12" x2="21" y2="12"></line>
                                        <line x1="8" y1="18" x2="21" y2="18"></line>
                                        <line x1="3" y1="6" x2="3.01" y2="6"></line>
                                        <line x1="3" y1="12" x2="3.01" y2="12"></line>
                                        <line x1="3" y1="18" x2="3.01" y2="18"></line>
                                    </svg>
                                    <span class="align-middle">List Component Request</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Component Request Log -->
                        <c:if test="${staff.hasPermissions('COMPONENT_REQUEST_RESPONSIBLE')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="componentRequestResponsible?action=viewComponentRequestResponsible">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text align-middle">
                                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                                        <polyline points="14 2 14 8 20 8"></polyline>
                                        <line x1="16" y1="13" x2="8" y2="13"></line>
                                        <line x1="16" y1="17" x2="8" y2="17"></line>
                                        <polyline points="10 9 9 9 8 9"></polyline>
                                    </svg>
                                    <span class="align-middle">Component Request Log</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Chat History -->
                        <c:if test="${staff.hasPermissions('CHAT_HISTORY')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="chatHistory">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-message-square align-middle">
                                        <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
                                    </svg>
                                    <span class="align-middle">Chat History</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Warranty Card Repair Contractor -->
                        <c:if test="${staff.hasPermissions('WARRANTY_CARD_REPAIR_CONTRACTOR')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="warrantyCardRepairContractor">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-tool align-middle">
                                        <path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"></path>
                                    </svg>
                                    <span class="align-middle">Warranty Card Repair</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Staff -->
                        <c:if test="${staff.hasPermissions('VIEW_STAFF')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="StaffController">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users align-middle">
                                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                                        <circle cx="9" cy="7" r="4"></circle>
                                        <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                                        <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                                    </svg>
                                    <span class="align-middle">Staff</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Feedback -->
                        <c:if test="${staff.hasPermissions('VIEW_FEEDBACK')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="feedback?action=viewFeedback">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-message-circle align-middle">
                                        <path d="M21 11.5a8.38 8.38 0 0 1-.9 3.8 8.5 8.5 0 0 1-7.6 4.7 8.38 8.38 0 0 1-3.8-.9L3 21l1.9-5.7a8.38 8.38 0 0 1-.9-3.8 8.5 8.5 0 0 1 4.7-7.6 8.38 8.38 0 0 1 3.8-.9h.5a8.48 8.48 0 0 1 8 8v.5z"></path>
                                    </svg>
                                    <span class="align-middle">Feedback</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Setting File Size Limit -->
                        <c:if test="${staff.hasPermissions('ADMIN_DASHBOARD_JSP')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="adminDashboard.jsp">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-sliders align-middle">
                                        <line x1="4" y1="21" x2="4" y2="14"></line>
                                        <line x1="4" y1="10" x2="4" y2="3"></line>
                                        <line x1="12" y1="21" x2="12" y2="12"></line>
                                        <line x1="12" y1="8" x2="12" y2="3"></line>
                                        <line x1="20" y1="21" x2="20" y2="16"></line>
                                        <line x1="20" y1="12" x2="20" y2="3"></line>
                                        <line x1="1" y1="14" x2="7" y2="14"></line>
                                        <line x1="9" y1="8" x2="15" y2="8"></line>
                                        <line x1="17" y1="16" x2="23" y2="16"></line>
                                    </svg>
                                    <span class="align-middle">Setting File Size Limit</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Customize Homepage -->
                        <c:if test="${staff.hasPermissions('CUSTOMIZE_HOMEPAGE')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="customizeHomepage">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-home align-middle">
                                        <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
                                        <polyline points="9 22 9 12 15 12 15 22"></polyline>
                                    </svg>
                                    <span class="align-middle">Customize Homepage</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Permission -->
                        <c:if test="${staff.hasPermissions('PERMISSION')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="permissions">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-lock align-middle">
                                        <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
                                        <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
                                    </svg>
                                    <span class="align-middle">Permission</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Component Report -->
                        <c:if test="${staff.hasPermissions('REPORT_COMPONENT_JSP')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="ReportComponent.jsp">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-pie-chart align-middle">
                                        <path d="M21.21 15.89A10 10 0 1 1 8 2.83"></path>
                                        <path d="M22 12A10 10 0 0 0 12 2v10z"></path>
                                    </svg>
                                    <span class="align-middle">Component Report</span>
                                </a>
                            </li>
                        </c:if>

                        <!-- Report Warranty Card -->
                        <c:if test="${staff.hasPermissions('REPORT_WARRANTY_CARD_JSP')}">
                            <li class="sidebar-item">
                                <a class="sidebar-link" href="reportWarrantyCard.jsp">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-bar-chart-2 align-middle">
                                        <line x1="18" y1="20" x2="18" y2="10"></line>
                                        <line x1="12" y1="20" x2="12" y2="4"></line>
                                        <line x1="6" y1="20" x2="6" y2="14"></line>
                                    </svg>
                                    <span class="align-middle">Report Warranty Card</span>
                                </a>
                            </li>
                        </c:if>
                    </ul>

                        </div>
                    </div>
                </div>
            </div>

            <div class="simplebar-placeholder" style="width: auto; height: 1320px;"></div>

        </div>

        <div class="simplebar-track simplebar-horizontal" style="visibility: hidden;">
            <div class="simplebar-scrollbar" style="width: 0px; display: none;"></div>
        </div>
        <div class="simplebar-track simplebar-vertical" style="visibility: visible;">
            <div class="simplebar-scrollbar" style="height: 472px; transform: translate3d(0px, 0px, 0px); display: block;"></div>  
        </div>  
    </div>
</nav>