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
                                                <a class="dropdown-item" href="pages-profile.html"><i class="align-middle me-1" data-feather="user"></i> Profile</a>
                                                <a class="dropdown-item" href="ChangePasswordForm.jsp"><i class="align-middle me-1" data-feather="pie-chart"></i> Change Password</a>
                                                <div class="dropdown-divider"></div>
                                                <a class="dropdown-item" href="pages-settings.html"><i class="align-middle me-1" data-feather="settings"></i> Settings & Privacy</a>
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
                                        <a class="sidebar-link" href="BlogController">
                                            <!-- ??i sang icon file-text (bi?u t??ng cho Blog) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text align-middle">
                                            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                                            <polyline points="14 2 14 8 20 8"></polyline>
                                            <line x1="16" y1="13" x2="8" y2="13"></line>
                                            <line x1="16" y1="17" x2="8" y2="17"></line>
                                            </svg> 
                                            <span class="align-middle">Blog</span>
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

                            <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '1'}">
                                <ul class="sidebar-nav">
                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="profile">
                                            <!-- S? d?ng icon user cho Profile -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-user align-middle">
                                            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                                            <circle cx="12" cy="7" r="4"></circle>
                                            </svg>
                                            <span class="align-middle">Profile</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="ComponentWarehouse">
                                            <!-- Dùng icon box cho Component -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-box align-middle">
                                            <path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4a2 2 0 0 0 1-1.73z"></path>
                                            <polyline points="3.27 6.96 12 12.01 20.73 6.96"></polyline>
                                            <line x1="12" y1="22.08" x2="12" y2="12"></line>
                                            </svg>
                                            <span class="align-middle">Component</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="viewProduct">
                                            <!-- Dùng icon shopping-cart cho Product -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shopping-cart align-middle">
                                            <circle cx="9" cy="21" r="1"></circle>
                                            <circle cx="20" cy="21" r="1"></circle>
                                            <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
                                            </svg>
                                            <span class="align-middle">Product</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="BlogController">
                                            <!-- Dùng icon file-text cho Blog -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text align-middle">
                                            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                                            <polyline points="14 2 14 8 20 8"></polyline>
                                            <line x1="16" y1="13" x2="8" y2="13"></line>
                                            <line x1="16" y1="17" x2="8" y2="17"></line>
                                            </svg>
                                            <span class="align-middle">Blog</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="StaffController">
                                            <!-- Dùng icon users cho Staff -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-users align-middle">
                                            <path d="M17 21v-2a4 4 0 0 0-3-3.87"></path>
                                            <path d="M7 21v-2a4 4 0 0 1 3-3.87"></path>
                                            <circle cx="12" cy="7" r="4"></circle>
                                            </svg>
                                            <span class="align-middle">Staff</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="InvoiceController">
                                            <!-- Dùng icon file cho Invoice -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file align-middle">
                                            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                                            <polyline points="14 2 14 8 20 8"></polyline>
                                            </svg>
                                            <span class="align-middle">Invoice</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="feedback?action=viewFeedback">
                                            <!-- Dùng icon message-square cho Feedback list -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-message-square align-middle">
                                            <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2z"></path>
                                            </svg>
                                            <span class="align-middle">Feedback list</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="adminDashboard.jsp">
                                            <!-- Dùng icon settings cho Setting Max Size -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-settings align-middle">
                                            <circle cx="12" cy="12" r="3"></circle>
                                            <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l0 0a2 2 0 1 1-2.83 2.83l0 0a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 1 1-4 0v0a1.65 1.65 0 0 0-1-1.51 1.65 1.65 0 0 0-1.82.33l0 0a2 2 0 1 1-2.83-2.83l0 0a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 1 1 0-4h0a1.65 1.65 0 0 0 1.51-1 1.65 1.65 0 0 0-.33-1.82l0 0a2 2 0 1 1 2.83-2.83l0 0a1.65 1.65 0 0 0 1.82.33h0a1.65 1.65 0 0 0 1-1.51V3a2 2 0 1 1 4 0v0a1.65 1.65 0 0 0 1 1.51h0a1.65 1.65 0 0 0 1.82-.33l0 0a2 2 0 1 1 2.83 2.83l0 0a1.65 1.65 0 0 0-.33 1.82h0a1.65 1.65 0 0 0 1.51 1V12a2 2 0 1 1 0 4h0a1.65 1.65 0 0 0-1.51 1z"></path>
                                            </svg>
                                            <span class="align-middle">Setting Max Size</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="packageWarranty?action=view">
                                            <!-- Dùng icon shield cho List Package Warranty -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shield align-middle">
                                            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
                                            </svg>
                                            <span class="align-middle">List Package Warranty</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="extendedWarranty">
                                            <!-- Dùng icon shield-off cho Extended Warranty List -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shield-off align-middle">
                                            <path d="M3 3l18 18"></path>
                                            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
                                            </svg>
                                            <span class="align-middle">Extended Warranty List</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="customizeHomepage">
                                            <!-- Dùng icon layout cho customize Homepage -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-layout align-middle">
                                            <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
                                            <line x1="3" y1="9" x2="21" y2="9"></line>
                                            <line x1="9" y1="21" x2="9" y2="9"></line>
                                            </svg>
                                            <span class="align-middle">customize Homepage</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="customerContact?action=view">
                                            <!-- Dùng icon phone cho list customer Contact -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-phone align-middle">
                                            <path d="M22 16.92V19a2 2 0 0 1-2.18 2A19.79 19.79 0 0 1 3 5.18 2 2 0 0 1 5 3h2.09a2 2 0 0 1 2 1.72 13 13 0 0 0 .57 2.81 2 2 0 0 1-.45 2.11l-1.27 1.27a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 13 13 0 0 0 2.81.57A2 2 0 0 1 22 16.92z"></path>
                                            </svg>
                                            <span class="align-middle">list customer Contact</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="ReportComponent.jsp">
                                            <!-- Dùng icon bar-chart cho Report Component -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-bar-chart-2 align-middle">
                                            <line x1="18" y1="20" x2="18" y2="10"></line>
                                            <line x1="12" y1="20" x2="12" y2="4"></line>
                                            <line x1="6" y1="20" x2="6" y2="14"></line>
                                            </svg>
                                            <span class="align-middle">Report Component</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="customer">
                                            <!-- Dùng icon user-check cho Customer -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-user-check align-middle">
                                            <path d="M20 21v-2a4 4 0 0 0-3-3.87"></path>
                                            <path d="M4 21v-2a4 4 0 0 1 3-3.87"></path>
                                            <circle cx="12" cy="7" r="4"></circle>
                                            <path d="M16 11l2 2 4-4"></path>
                                            </svg>
                                            <span class="align-middle">Customer</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="reportWarrantyCard.jsp">
                                            <!-- Dùng icon pie-chart cho Report Warranty -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-pie-chart align-middle">
                                            <path d="M21.21 15.89A10 10 0 1 1 12 2v10z"></path>
                                            <path d="M22 12A10 10 0 0 0 12 2v10z"></path>
                                            </svg>
                                            <span class="align-middle">Report Warranty</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="Brand">
                                            <!-- Dùng icon briefcase cho Brand -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-briefcase align-middle">
                                            <rect x="2" y="7" width="20" height="14" rx="2" ry="2"></rect>
                                            <path d="M16 3h-8v4h8z"></path>
                                            </svg>
                                            <span class="align-middle">Brand</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="ComponentType">
                                            <!-- Dùng icon tag cho Component Type -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-tag align-middle">
                                            <path d="M20 10V4a2 2 0 0 0-2-2H8L2 10l6 6h10a2 2 0 0 0 2-2z"></path>
                                            <line x1="7" y1="7" x2="7" y2="7"></line>
                                            </svg>
                                            <span class="align-middle">Component Type</span>
                                        </a>
                                    </li>
                                     <li class="sidebar-item">
                                        <a class="sidebar-link" href="componentRequestResponsible?action=viewComponentRequestResponsible">
                                            <!-- ??i sang icon activity (Component Request Log) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-activity align-middle">
                                            <polyline points="22 12 18 12 15 21 9 3 6 12 2 12"></polyline>
                                            </svg> 
                                            <span class="align-middle">Component Request Log</span>
                                        </a>
                                    </li>



                                </ul>
                            </c:if>

                            <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '2'}">
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
                                        <a class="sidebar-link" href="ComponentWarehouse">
                                            <!-- ??i sang icon box (bi?u t??ng cho Component) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-box align-middle">
                                            <path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4a2 2 0 0 0 1-1.73z"></path>
                                            <polyline points="3.27 6.96 12 12.01 20.73 6.96"></polyline>
                                            <line x1="12" y1="22.08" x2="12" y2="12"></line>
                                            </svg> 
                                            <span class="align-middle">Component</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="viewProduct">
                                            <!-- ??i sang icon shopping-cart (bi?u t??ng cho Product) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shopping-cart align-middle">
                                            <circle cx="9" cy="21" r="1"></circle>
                                            <circle cx="20" cy="21" r="1"></circle>
                                            <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
                                            </svg> 
                                            <span class="align-middle">Product</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="BlogController">
                                            <!-- ??i sang icon file-text (bi?u t??ng cho Blog) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text align-middle">
                                            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                                            <polyline points="14 2 14 8 20 8"></polyline>
                                            <line x1="16" y1="13" x2="8" y2="13"></line>
                                            <line x1="16" y1="17" x2="8" y2="17"></line>
                                            </svg> 
                                            <span class="align-middle">Blog</span>
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
                                        <a class="sidebar-link" href="WarrantyCard">
                                            <!-- ??i sang icon id-card (Warranty Card) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-id-card align-middle">
                                            <rect x="2" y="4" width="20" height="16" rx="2" ry="2"></rect>
                                            <circle cx="8" cy="10" r="2"></circle>
                                            <line x1="12" y1="10" x2="22" y2="10"></line>
                                            <line x1="12" y1="14" x2="22" y2="14"></line>
                                            <line x1="12" y1="18" x2="22" y2="18"></line>
                                            </svg> 
                                            <span class="align-middle">Warranty Card</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="customerContact?action=view">
                                            <!-- ??i sang icon phone (List Customer Contact) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-phone align-middle">
                                            <path d="M22 16.92V19a2 2 0 0 1-2.18 2A19.79 19.79 0 0 1 3 5.18 2 2 0 0 1 5 3h2.09a2 2 0 0 1 2 1.72 13 13 0 0 0 .57 2.81 2 2 0 0 1-.45 2.11l-1.27 1.27a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 13 13 0 0 0 2.81.57A2 2 0 0 1 22 16.92z"></path>
                                            </svg> 
                                            <span class="align-middle">list customer Contact</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="Brand">
                                            <!-- ??i sang icon briefcase (Brand) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-briefcase align-middle">
                                            <rect x="2" y="7" width="20" height="14" rx="2" ry="2"></rect>
                                            <path d="M16 3h-8v4h8z"></path>
                                            </svg> 
                                            <span class="align-middle">Brand</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="ComponentType">
                                            <!-- ??i sang icon tag (Component Type) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-tag align-middle">
                                            <path d="M20 10V4a2 2 0 0 0-2-2H8L2 10l6 6h10a2 2 0 0 0 2-2z"></path>
                                            <line x1="7" y1="7" x2="7" y2="7"></line>
                                            </svg> 
                                            <span class="align-middle">Component Type</span>
                                        </a>
                                    </li>

                                </ul>

                            </c:if>

                            <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '3'}">
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
                                        <a class="sidebar-link" href="ComponentWarehouse">
                                            <!-- ??i sang icon box (bi?u t??ng cho Component) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-box align-middle">
                                            <path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4a2 2 0 0 0 1-1.73z"></path>
                                            <polyline points="3.27 6.96 12 12.01 20.73 6.96"></polyline>
                                            <line x1="12" y1="22.08" x2="12" y2="12"></line>
                                            </svg> 
                                            <span class="align-middle">Component</span>
                                        </a>
                                    </li>


                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="viewProduct">
                                            <!-- ??i sang icon shopping-cart (bi?u t??ng cho Product) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shopping-cart align-middle">
                                            <circle cx="9" cy="21" r="1"></circle>
                                            <circle cx="20" cy="21" r="1"></circle>
                                            <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
                                            </svg> 
                                            <span class="align-middle">Product</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="BlogController">
                                            <!-- ??i sang icon file-text (bi?u t??ng cho Blog) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text align-middle">
                                            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                                            <polyline points="14 2 14 8 20 8"></polyline>
                                            <line x1="16" y1="13" x2="8" y2="13"></line>
                                            <line x1="16" y1="17" x2="8" y2="17"></line>
                                            </svg> 
                                            <span class="align-middle">Blog</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="componentRequest?action=viewComponentRequestDashboard">
                                            <!-- ??i sang icon alert-circle (Component Request) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-alert-circle align-middle">
                                            <circle cx="12" cy="12" r="10"></circle>
                                            <line x1="12" y1="8" x2="12" y2="12"></line>
                                            <line x1="12" y1="16" x2="12.01" y2="16"></line>
                                            </svg> 
                                            <span class="align-middle">Component Request</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="componentRequest?action=viewListComponentRequest">
                                            <!-- Gi? nguyên icon list (List Component Request) -->
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

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="componentRequestResponsible?action=viewComponentRequestResponsible">
                                            <!-- ??i sang icon activity (Component Request Log) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-activity align-middle">
                                            <polyline points="22 12 18 12 15 21 9 3 6 12 2 12"></polyline>
                                            </svg> 
                                            <span class="align-middle">Component Request Log</span>
                                        </a>
                                    </li>


                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="Brand">
                                            <!-- ??i sang icon briefcase (Brand) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-briefcase align-middle">
                                            <rect x="2" y="7" width="20" height="14" rx="2" ry="2"></rect>
                                            <path d="M16 3h-8v4h8z"></path>
                                            </svg> 
                                            <span class="align-middle">Brand</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="ComponentType">
                                            <!-- ??i sang icon tag (Component Type) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-tag align-middle">
                                            <path d="M20 10V4a2 2 0 0 0-2-2H8L2 10l6 6h10a2 2 0 0 0 2-2z"></path>
                                            <line x1="7" y1="7" x2="7" y2="7"></line>
                                            </svg> 
                                            <span class="align-middle">Component Type</span>
                                        </a>
                                    </li>
                                </ul>
                            </c:if>

                            <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '4'}">
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
                                        <a class="sidebar-link" href="BlogController">
                                            <!-- ??i sang icon file-text (bi?u t??ng cho Blog) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text align-middle">
                                            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                                            <polyline points="14 2 14 8 20 8"></polyline>
                                            <line x1="16" y1="13" x2="8" y2="13"></line>
                                            <line x1="16" y1="17" x2="8" y2="17"></line>
                                            </svg> 
                                            <span class="align-middle">Blog</span>
                                        </a>
                                    </li>


                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="warrantyCardRepairContractor">
                                            <!-- ??i sang icon tool (Warranty Card Repair Contractor) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-tool align-middle">
                                            <path d="M14.7 9.3l3-3a2 2 0 0 0 0-2.8L15.5 1.7a2 2 0 0 0-2.8 0l-3 3"></path>
                                            <path d="M2 22l7-7"></path>
                                            <path d="M14 14l7 7"></path>
                                            </svg> 
                                            <span class="align-middle">Warranty Card</span>
                                        </a>
                                    </li>
                                </ul>
                            </c:if>

                            <c:if test="${not empty sessionScope.staff and sessionScope.staff.role == '5'}">

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
                                        <a class="sidebar-link" href="viewProduct">
                                            <!-- ??i sang icon shopping-cart (bi?u t??ng cho Product) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-shopping-cart align-middle">
                                            <circle cx="9" cy="21" r="1"></circle>
                                            <circle cx="20" cy="21" r="1"></circle>
                                            <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
                                            </svg> 
                                            <span class="align-middle">Product</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="BlogController">
                                            <!-- ??i sang icon file-text (bi?u t??ng cho Blog) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-file-text align-middle">
                                            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                                            <polyline points="14 2 14 8 20 8"></polyline>
                                            <line x1="16" y1="13" x2="8" y2="13"></line>
                                            <line x1="16" y1="17" x2="8" y2="17"></line>
                                            </svg> 
                                            <span class="align-middle">Blog</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="WarrantyCard">
                                            <!-- ??i sang icon id-card (Warranty Card) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-id-card align-middle">
                                            <rect x="2" y="4" width="20" height="16" rx="2" ry="2"></rect>
                                            <circle cx="8" cy="10" r="2"></circle>
                                            <line x1="12" y1="10" x2="22" y2="10"></line>
                                            <line x1="12" y1="14" x2="22" y2="14"></line>
                                            <line x1="12" y1="18" x2="22" y2="18"></line>
                                            </svg> 
                                            <span class="align-middle">Warranty Card</span>
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
                                        <a class="sidebar-link" href="customerContact?action=view">
                                            <!-- ??i sang icon phone (List Customer Contact) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-phone align-middle">
                                            <path d="M22 16.92V19a2 2 0 0 1-2.18 2A19.79 19.79 0 0 1 3 5.18 2 2 0 0 1 5 3h2.09a2 2 0 0 1 2 1.72 13 13 0 0 0 .57 2.81 2 2 0 0 1-.45 2.11l-1.27 1.27a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 13 13 0 0 0 2.81.57A2 2 0 0 1 22 16.92z"></path>
                                            </svg> 
                                            <span class="align-middle">list customer Contact</span>
                                        </a>
                                    </li>

                                    <li class="sidebar-item">
                                        <a class="sidebar-link" href="customer">
                                            <!-- ??i sang icon user-check (Customer) -->
                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-user-check align-middle">
                                            <path d="M20 21v-2a4 4 0 0 0-3-3.87"></path>
                                            <path d="M4 21v-2a4 4 0 0 1 3-3.87"></path>
                                            <circle cx="12" cy="7" r="4"></circle>
                                            <path d="M16 11l2 2 4-4"></path>
                                            </svg> 
                                            <span class="align-middle">Customer</span>
                                        </a>
                                    </li>

                                </ul>
                            </c:if>
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