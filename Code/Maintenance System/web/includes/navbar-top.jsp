<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand navbar-light navbar-bg">
    <a class="sidebar-toggle js-sidebar-toggle">
        <i class="hamburger align-self-center"></i>
    </a>

    <ul class="navbar-nav d-none d-lg-flex">
        <li class="nav-item px-2 dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="megaDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
               Look Up Online
            </a>
            <div class="dropdown-menu dropdown-menu-start dropdown-mega" aria-labelledby="megaDropdown">
                <div class="d-md-flex align-items-start justify-content-start">
                    <div class="dropdown-mega-list">
                        <a class="dropdown-item" href="LookUpOnline.jsp">Warranty Card</a>
                        <a class="dropdown-item" href="#">Product</a>
                       
                    </div>
                   
                </div>
            </div>
        </li>
        
    </ul>

    <div class="navbar-collapse collapse">
        <ul class="navbar-nav navbar-align">
            <li class="nav-item dropdown">
                <a class="nav-icon dropdown-toggle" href="#" id="alertsDropdown" data-bs-toggle="dropdown">
                    <div class="position-relative">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-bell align-middle"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"></path><path d="M13.73 21a2 2 0 0 1-3.46 0"></path></svg>
                        <!--<span class="indicator"></span>-->
                    </div>
                </a>
                <div class="dropdown-menu dropdown-menu-lg dropdown-menu-end py-0" aria-labelledby="alertsDropdown">
                    <div class="dropdown-menu-header">
                    </div>
                    <div class="list-group">
                    </div>
                </div>
            </li>

            <c:if test="${not empty sessionScope.staff}">
                <!-- N?u ?ã ??ng nh?p là nhân viên -->
                <li class="nav-item dropdown">
                    <a class="nav-icon pe-md-0 dropdown-toggle" href="#" data-bs-toggle="dropdown">
                        <img src="${sessionScope.staff.image}" class="avatar img-fluid rounded" alt="${sessionScope.staff.name}">
                    </a>
                    <div class="dropdown-menu dropdown-menu-end">
                        <a class="dropdown-item" href="Profile">Profile</a>
                        <a class="dropdown-item" href="ChangePasswordForm.jsp">Change Password</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="logout">Log out</a>
                    </div>
                </li>
            </c:if>

            <c:if test="${not empty sessionScope.customer}">
                <!-- N?u ?ã ??ng nh?p là khách hàng -->
                <li class="nav-item dropdown">
                    <a class="nav-icon pe-md-0 dropdown-toggle" href="#" data-bs-toggle="dropdown">
                        <img src="${sessionScope.customer.image}" class="avatar img-fluid rounded" alt="${sessionScope.customer.name}">
                    </a>
                    <div class="dropdown-menu dropdown-menu-end">
                        <a class="dropdown-item" href="pages-profile.html">Profile</a>
                         <a class="dropdown-item" href="ChangePasswordForm.jsp">Change Password</a>
                        <a class="dropdown-item" href="#">Analytics</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="pages-settings.html">Settings & Privacy</a>
                        <a class="dropdown-item" href="logout">Log out</a>
                    </div>
                </li>
            </c:if>

            
            <c:if test="${empty sessionScope.staff and empty sessionScope.customer}">
                <a href="LoginForm.jsp">Login</a>
            </c:if>
            <a>
        </ul>
    </div>
</nav>