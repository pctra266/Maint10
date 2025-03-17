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
                        <span class="indicator" id="notificationCount" style="display: none;"></span>
                    </div>
                </a>
                <div class="dropdown-menu dropdown-menu-lg dropdown-menu-end py-0" aria-labelledby="alertsDropdown">
                    <div class="dropdown-menu-header" id="notificationHeader">
                        No new notifications
                    </div>
                    <div class="list-group" id="notificationList">
                        <!-- Thông báo s? ???c thêm ??ng b?ng JavaScript -->
                    </div>
                </div>
            </li>

            <c:if test="${not empty sessionScope.staff}">
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
        </ul>
    </div>
</nav>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    let lastCheck = new Date().getTime();

    function showNotification(message, target) {
        const notificationList = document.getElementById("notificationList");
        const notificationItem = document.createElement("a");
        const nowDate = new Date();
        notificationItem.className = "list-group-item";
        notificationItem.href = target;
        notificationItem.innerHTML = `
            <div class="d-flex">
                <div class="flex-shrink-0">
                    <i class="fas fa-tools text-primary me-2"></i>
                </div>
                <div class="flex-grow-1">
                    <p class="mb-0">${message}</p>
                    <small class="text-muted">`+nowDate.getHours()+`:`+nowDate.getMinutes()+`</small>
                </div>
            </div>
        `;
        notificationList.prepend(notificationItem);

        const unreadCount = notificationList.children.length;
        const header = document.getElementById("notificationHeader");
        const countSpan = document.getElementById("notificationCount");
        header.textContent = `${unreadCount} New Notification${unreadCount > 1 ? 's' : ''}`;
        countSpan.textContent = unreadCount;
        countSpan.style.display = unreadCount > 0 ? 'block' : 'none';
    }

    function checkNotifications() {
        let recipientType, recipientID;

        <c:if test="${not empty sessionScope.customer}">
            recipientType = "Customer";
            recipientID = ${sessionScope.customer.customerID};
        </c:if>
        <c:if test="${not empty sessionScope.staff}">
            recipientType = "Staff";
            recipientID = ${sessionScope.staff.staffID};
        </c:if>

        if (!recipientType || !recipientID) {
            setTimeout(checkNotifications, 5000);
            return;
        }

        $.ajax({
            url: "${pageContext.request.contextPath}/Notification/GetUnread",
            type: "GET",
            data: { 
                recipientType: recipientType,
                recipientID: recipientID, 
                lastCheck: lastCheck 
            },
            timeout: 35000,
            success: function (data) {
                console.log("Received data:", data);
                if (data.length > 0) {
                    data.forEach(function (notification) {
                        showNotification(notification.message, notification.target);
                    });
                    lastCheck = new Date().getTime();
                }
                setTimeout(checkNotifications, 0);
            },
            error: function (xhr, status, error) {
                console.error("Error fetching notifications: ", status, error);
                setTimeout(checkNotifications, 5000);
            }
        });
    }

    $(document).ready(function () {
        checkNotifications();
    });
</script>