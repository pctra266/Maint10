<%-- 
    Document   : customerContactList
    Created on : Mar 16, 2025, 1:42:13 AM
    Author     : Tra Pham
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Contact List</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center ">List Customer Contact</h1>
                    <form method="get" action="customerContact">
            <input type="hidden" name="action" value="view">
            <div class="row" style="justify-content: space-between">
                <div class="col-md-6" style="width: 500px">
                    <input style="margin-top: 15px"  type="search" name="searchName" class="form-control" placeholder="Name" value="${searchName}">
                    
                     <div style="margin-top: 15px" class="col-sm-6 col-md-6">
                            <label>Show 
                                <select name="page-size" class="form-select form-select-sm d-inline-block" style="width: auto;" onchange="this.form.submit()">
                                    <c:forEach items="${pagination.listPageSize}" var="s">
                                        <option value="${s}" ${pagination.pageSize==s?"selected":""}>${s}</option>
                                    </c:forEach>
                                </select> 
                                entries
                            </label>
                        </div>
                </div>
                
                <div class="col-md-6" style="width: 500px">
                    <input style="margin-top: 15px"  type="search" name="searchPhone" class="form-control" placeholder="Phone" value="${searchPhone}">
                    <input style="margin-top: 15px"  type="search" name="searchEmail" class="form-control" placeholder="Email" value="${searchEmail}">
                    <div style="float: right">
                                <button  class="btn btn-primary" style="margin-top: 15px" type="submit">Search</button>
                   </div>
                </div>
              
            </div>
        </form>
                

                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <th>Contact ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Message</th>
                                <th>Date Sent</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${contactList}" var="contact">
                                <tr>
                                    <td>${contact.contactID}</td>
                                    <td>${contact.name}</td>
                                    <td>${contact.email}</td>
                                    <td>${contact.phone}</td>
                                    <td>${contact.message}</td>
                                    <td>${contact.createdAt}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                 <jsp:include page="/includes/pagination.jsp" />
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
        <script src="js/format-input.js"></script>
    </body>
</html>

