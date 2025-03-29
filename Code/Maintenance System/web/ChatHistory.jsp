<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat History</title>
        <link href="css/light.css" rel="stylesheet">
                <link rel="shortcut icon" href="img/icons/icon-48x48.png" />

        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center">Chat History</h1>

                    <!-- Form tìm kiếm -->
                    <form action="chatHistory" method="get" class="text-center mb-3">
                        <div class="row g-2">
                            <div class="col-md-2">
                                <input type="text" name="sender" class="form-control" placeholder="Search by Sender" value="${param.sender}">
                            </div>
                            <div class="col-md-2">
                                <input type="text" name="receiver" class="form-control" placeholder="Search by Receiver" value="${param.receiver}">
                            </div>
                            <div class="col-md-2">
                                <select name="senderType" class="form-select">
                                    <option value="">All Sender Types</option>
                                    <option value="customer" ${param.senderType == 'customer' ? 'selected' : ''}>Customer</option>
                                    <option value="staff" ${param.senderType == 'staff' ? 'selected' : ''}>Staff</option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <select name="receiverType" class="form-select">
                                    <option value="">All Receiver Types</option>
                                    <option value="customer" ${param.receiverType == 'customer' ? 'selected' : ''}>Customer</option>
                                    <option value="staff" ${param.receiverType == 'staff' ? 'selected' : ''}>Staff</option>
                                </select>
                            </div>

                            <div class="col-md-2">
                                <button type="submit" class="btn btn-primary w-100">Search</button>
                            </div>
                        </div>

                        <div class="row mt-3">
                            <div class="col-md-6">
                                <label>Show 
                                    <select name="page-size" class="form-select d-inline-block w-auto" onchange="this.form.submit()">
                                        <c:forEach var="s" items="${[5, 10, 15, 20]}">
                                            <option value="${s}" ${param['page-size'] == s ? "selected" : ""}>${s}</option>
                                        </c:forEach>
                                    </select> entries
                                </label>
                            </div>

                            <div class="col-md-6 text-end">
                                <label>Sort by 
                                    <select name="sort" class="form-select d-inline-block w-auto">
                                        <option value="timestamp" ${param.sortBy == 'Timestamp' ? 'selected' : ''}>Timestamp</option>
                                        <option value=" senderName" ${param.sortBy == 'Sender' ? 'selected' : ''}>Sender</option>
                                        <option value="receiverName" ${param.sortBy == 'Receiver' ? 'selected' : ''}>Receiver</option>
                                    </select>
                                </label>
                                <label>
                                    <select name="order" class="form-select d-inline-block w-auto">
                                        <option value="ASC" ${param.sortOrder == 'ASC' ? 'selected' : ''}>ASC</option>
                                        <option value="DESC" ${param.sortOrder == 'DESC' ? 'selected' : ''}>DESC</option>
                                    </select>
                                </label>
                                <button type="submit" class="btn btn-secondary">Apply</button>
                            </div>
                        </div>
                    </form>

                    <!-- Hiển thị thông báo nếu có -->
                    <c:if test="${not empty createStatus}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message">
                                <strong>${createStatus}</strong>
                            </div>
                        </div>
                    </c:if>

                    <!-- Bảng hiển thị danh sách tin nhắn -->
                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Sender Name</th>
                                <th>Sender Type</th>
                                <th>Message</th>
                                <th>Send Date</th>
                                <th>Receiver Type</th>
                                <th>Receiver Name</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty listChat}">
                                    <tr><td colspan="7" style="text-align:center;">No Information.</td></tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="chat" items="${listChat}">
                                        <tr>
                                            <td>${chat.messageID}</td>
                                            <td>${chat.senderName}</td>
                                            <td>${chat.senderType}</td>
                                            <td>${chat.messageText}</td>
                                            <td><fmt:formatDate value="${chat.timestamp}" pattern="dd-MM-yyyy" /></td>
                                            <td>${chat.receiverType}</td>
                                            <td>${chat.receiverName}</td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>

                    <!-- Phân trang -->
                    <nav aria-label="Pagination">
                        <ul class="pagination justify-content-center">
                            <c:set var="queryString" value="&sender=${param.sender}&receiver=${param.receiver}&senderType=${param.senderType}&receiverType=${param.receiverType}&sort=${param.sort}&order=${param.order}&page-size=${param['page-size']}" />

                            <c:if test="${currentPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=1${queryString}">First</a>
                                </li>
                                <li class="page-item">
                                    <a class="page-link" href="?page=${currentPage - 1}${queryString}">Previous</a>
                                </li>
                            </c:if>

                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="?page=${i}${queryString}">${i}</a>
                                </li>
                            </c:forEach>

                            <c:if test="${currentPage < totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=${currentPage + 1}${queryString}">Next</a>
                                </li>
                                <li class="page-item">
                                    <a class="page-link" href="?page=${totalPages}${queryString}">Last</a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>


                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
