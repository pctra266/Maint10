<%-- 
    Document   : ViewListFeedback
    Created on : Jan 17, 2025, 7:09:31 PM
    Author     : Tra Pham
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List Feedback</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center ">Feedback List</h1>
                    <form class="" action="feedback" method="get">
                        <input type="hidden" name="action" value="viewFeedback">
                        <div class="row" style="justify-content: space-between" >
                            <div class="col-md-6" style="width: 500px">

                                <input style="margin-top: 15px" class="form-control" type="search" name="customerName" placeholder="Customer Name"  value="${customerName}" >
                                <input style="margin-top: 15px" class="form-control" type="search" name="customerEmail" placeholder="Customer Email"  value="${customerEmail}" >
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
                                

                            </div >
                            <div class="col-md-6" style="width: 500px">
                                <input style="margin-top: 15px" class="form-control" type="search" name="customerPhone" placeholder="Customer Phone Number"  value="${customerPhone}" >
                                <select style="margin-top: 15px" class="form-select" name="imageAndVideo">
                                    <option value="">Image & Video </option>
                                    <option ${(imageAndVideo=='empty')?"selected":""} value="empty">Empty</option>
                                    <option ${(imageAndVideo=='attached')?"selected":""} value="attached">Attached</option>
                                </select>
                                <div style="float: right">
                                <button  class="btn btn-primary" style="margin-top: 15px" type="submit">Search</button>
                                </div>
                            </div>        
                        </div>
                        
                    </form>         
                    <table class="table table-hover my-0">
                        <thead >
                            <tr>
                                <th>ID</th>
                                <th>
                                    <form action="feedback" method="get">
                                        <input type="hidden" name="page" value="${pagination.currentPage}" />
                                        <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                        <input type="hidden" name="sort" value="CustomerName" />
                                        <input type="hidden" name="order" value="${pagination.sort eq 'CustomerName' and pagination.order eq 'asc' ? 'desc' : 'asc'}" />
                                        <c:if test="${fn:length(pagination.searchFields) > 0}">
                                            <c:forEach var="i" begin="0" end="${fn:length(pagination.searchFields) - 1}">
                                                <input type="hidden" name="${pagination.searchFields[i]}" value="${pagination.searchValues[i]}">
                                            </c:forEach>
                                        </c:if>
                                                Customer Name
                                                <button type="submit" class="btn-sort btn-primary btn">
                                            <i class="align-middle fas fa-fw
                                               ${pagination.sort eq 'CustomerName' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                            </i>
                                            
                                        </button>
                                        
                                    </form>
                                </th>
                                <th>
                                    <form action="feedback" method="get">
                                        <input type="hidden" name="page" value="${pagination.currentPage}" />
                                        <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                        <input type="hidden" name="sort" value="DateCreated" />
                                        <input type="hidden" name="order" value="${pagination.sort eq 'DateCreated' and pagination.order eq 'asc' ? 'desc' : 'asc'}" />

                                        <c:if test="${fn:length(pagination.searchFields) > 0}">
                                            <c:forEach var="i" begin="0" end="${fn:length(pagination.searchFields) - 1}">
                                                <input type="hidden" name="${pagination.searchFields[i]}" value="${pagination.searchValues[i]}">
                                            </c:forEach>
                                        </c:if>
                                                Create Date
                                                <button type="submit" class="btn-sort btn btn-primary">
                                                     
                                            <i class="align-middle fas fa-fw
                                               ${pagination.sort eq 'DateCreated' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                            </i>
                                           
                                        </button>
                                        
                                    </form>
                                </th>
                                <th>Feedback</th>
                                <th>Image & Video </th>
                                <th></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listFeedback}" var="o">
                                <tr>
                                    <td>${o.feedbackID}</td>
                                    <td>${o.customerName}</td>
                                    <td>${o.dateCreated}</td>
                                    <td>${o.note}</td>
                                    <td>${(o.videoURL!=null || o.imageURL != null)?"Attached":"Empty"}</td>
                                    <td>
                                        <form id="myForm${o.feedbackID}" action="feedback" method="post" >
                                            <input type="hidden" name="page" value="${pagination.currentPage}" />
                                            <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                            <input type="hidden" name="action" value="deleteFeedback">
                                            <input type="hidden" name="feedbackID" value="${o.feedbackID}">
                                            <input type="hidden" name="sort" value="${pagination.sort}" />
                                            <input type="hidden" name="order" value="${pagination.order}" />
                                            
                                            <c:if test="${fn:length(pagination.searchFields) > 0}">
                                                <c:forEach var="i" begin="1" end="${fn:length(pagination.searchFields) - 1}">
                                                    <input type="hidden" name="${pagination.searchFields[i]}" value="${pagination.searchValues[i]}">
                                                </c:forEach>
                                            </c:if>
                                                    <button class="btn btn-primary" type="submit" onclick="confirmSubmit(event)" >
                                                Delete
                                            </button>
                                        </form>
                                    </td>
                                    <td><a class="btn btn-primary" href="feedback?action=updateFeedback&feedbackID=${o.feedbackID}">Detail & Edit</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="text-center">
                        <ul class="pagination pagination-lg text-center" style="display: flex; justify-content: center; margin-top: 15px">
                            <c:forEach begin="1" end="${endPage}" var="i">
                                <li class="${index == i ? 'page-item active' : 'page-item'}"><a class="page-link" href="feedback?index=${i}&customerName=${customerName}&customerEmail=${customerEmail}&customerPhone=${customerPhone}&imageAndVideo=${imageAndVideo}&column=${column}&sortOrder=${sortOrder}">${i}</a></li>
                                </c:forEach>
                        </ul>
                    </div> 
                    <jsp:include page="/includes/pagination.jsp" />
                    <a href="feedbacklog?action=viewListFeedbackLog">History</a>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
        <script>
            function doDelete(event) {
                event.preventDefault();

                const url = event.currentTarget.getAttribute('data-url');

                if (confirm("Bạn có chắc chắn muốn xóa feedback này không?")) {
                    window.location.href = url;
                }
            }
            function confirmSubmit(event) {
                event.preventDefault();
                if (confirm("Bạn có chắc chắn muốn xóa feedback này không?")) {
                    event.target.closest('form').submit();
                }
            }
        </script>
    </body>
</html>
