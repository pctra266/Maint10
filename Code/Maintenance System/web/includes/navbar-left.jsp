<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav id="sidebar" class="sidebar js-sidebar collapsed">
    <div class="sidebar-content js-simplebar" data-simplebar="init"><div class="simplebar-wrapper" style="margin: 0px;"><div class="simplebar-height-auto-observer-wrapper"><div class="simplebar-height-auto-observer"></div></div><div class="simplebar-mask"><div class="simplebar-offset" style="right: 0px; bottom: 0px;"><div class="simplebar-content-wrapper" tabindex="0" role="region" aria-label="scrollable content" style="height: 100%; overflow: hidden scroll;"><div class="simplebar-content" style="padding: 0px;">
                            <a class="sidebar-brand" href="Home">
                                <img style="height: 10vh;" src="${pageContext.request.contextPath}/img/logo/logoDesign3.png" alt="Main10 Logo" class="logo-img bg-primary bg-opacity-50 ">
                            </a>
                            <c:if test="${not empty sessionScope.staff}">
                                <!-- N?u là nhân viên, hi?n th? thông tin nhân viên -->
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

                            <c:if test="${not empty sessionScope.customer}">
                                <!-- N?u là khách hàng, hi?n th? thông tin khách hàng -->
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
                                                <a class="dropdown-item" href="pages-profile.html"><i class="align-middle me-1" data-feather="user"></i> Profile</a>
                                                <a class="dropdown-item" href="ChangePasswordForm.jsp"><i class="align-middle me-1" data-feather="pie-chart"></i> ChangePassword</a>
                                                <div class="dropdown-divider"></div>
                                                <a class="dropdown-item" href="pages-settings.html"><i class="align-middle me-1" data-feather="settings"></i> Settings & Privacy</a>
                                                <a class="dropdown-item" href="#"><i class="align-middle me-1" data-feather="help-circle"></i> Help Center</a>
                                                <div class="dropdown-divider"></div>
                                                <a class="dropdown-item" href="logout">Log out</a>
                                            </div>
                                            <div class="sidebar-user-subtitle">Customer</div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <!-- N?u ch?a ??ng nh?p, hi?n th? liên k?t ??ng nh?p -->
                            <c:if test="${empty sessionScope.staff and empty sessionScope.customer}">
                                <button><a href="LoginForm.jsp">Login</a></button>
                            </c:if>

                            <ul class="sidebar-nav">

                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="profile">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-user align-middle"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path><circle cx="12" cy="7" r="4"></circle></svg> <span class="align-middle">Profile</span>
                                    </a>
                                </li>

                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="ComponentWarehouse">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card align-middle"><rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect><line x1="1" y1="10" x2="23" y2="10"></line></svg> <span class="align-middle">Component</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="viewProduct">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card align-middle"><rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect><line x1="1" y1="10" x2="23" y2="10"></line></svg> <span class="align-middle">Product</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="StaffController">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card align-middle"><rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect><line x1="1" y1="10" x2="23" y2="10"></line></svg> <span class="align-middle">Staff</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="feedback">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card align-middle"><rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect><line x1="1" y1="10" x2="23" y2="10"></line></svg> <span class="align-middle">Feedback list(Admin)</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="feedback?action=viewFeedbackDashboard">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card align-middle"><rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect><line x1="1" y1="10" x2="23" y2="10"></line></svg> <span class="align-middle">Feedback (Customer)</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="adminDashboard.jsp">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card align-middle"><rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect><line x1="1" y1="10" x2="23" y2="10"></line></svg> <span class="align-middle">Setting MaxSize</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="componentRequest">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card align-middle"><rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect><line x1="1" y1="10" x2="23" y2="10"></line></svg> <span class="align-middle">Component Request</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="componentRequest?action=viewListComponentRequest">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card align-middle"><rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect><line x1="1" y1="10" x2="23" y2="10"></line></svg> <span class="align-middle">List Component Request</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="componentRequestResponsible">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card align-middle"><rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect><line x1="1" y1="10" x2="23" y2="10"></line></svg> <span class="align-middle">Component Request Log</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="ReportComponent.jsp">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card align-middle"><rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect><line x1="1" y1="10" x2="23" y2="10"></line></svg> <span class="align-middle">Report Component</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="WarrantyCard">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card align-middle"><rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect><line x1="1" y1="10" x2="23" y2="10"></line></svg> <span class="align-middle">WarrantyCard</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="customer">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card align-middle"><rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect><line x1="1" y1="10" x2="23" y2="10"></line></svg> <span class="align-middle">Customer</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="yourwarrantycard">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-credit-card align-middle"><rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect><line x1="1" y1="10" x2="23" y2="10"></line></svg> <span class="align-middle">Your Warranty Card</span>
                                    </a>
                                </li>
                                <li class="sidebar-item">
                                    <a class="sidebar-link" href="purchaseproduct">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-list align-middle"><line x1="8" y1="6" x2="21" y2="6"></line><line x1="8" y1="12" x2="21" y2="12"></line><line x1="8" y1="18" x2="21" y2="18"></line><line x1="3" y1="6" x2="3.01" y2="6"></line><line x1="3" y1="12" x2="3.01" y2="12"></line><line x1="3" y1="18" x2="3.01" y2="18"></line></svg> <span class="align-middle">Purchase Product</span>

                                    </a>
                                </li>

                            </ul>

                        </div></div></div></div><div class="simplebar-placeholder" style="width: auto; height: 1320px;"></div></div><div class="simplebar-track simplebar-horizontal" style="visibility: hidden;"><div class="simplebar-scrollbar" style="width: 0px; display: none;"></div></div><div class="simplebar-track simplebar-vertical" style="visibility: visible;"><div class="simplebar-scrollbar" style="height: 472px; transform: translate3d(0px, 0px, 0px); display: block;"></div></div></div>
</nav>