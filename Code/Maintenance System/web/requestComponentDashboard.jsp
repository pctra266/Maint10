<%-- 
    Document   : requestComponentDashboard
    Created on : Feb 19, 2025, 11:55:33 AM
    Author     : Tra Pham
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List Product Under Maintain</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
     <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <div class="alert-success">${param.mess}</div>
                    <h1 class="text-center">List Product Maintain</h1>
                    <div>
                         <form method="get" action="componentRequest" >
                            <input type="hidden" name="action" value="viewComponentRequestDashboard">
                            <div class="row"  style="justify-content: space-between">
                        <div class="col-sm-6 col-md-6" style="width: 500px">
                           <input style="margin-top: 15px" class="form-control" type="search" name="warrantyCardCode" placeholder="Warranty Card Code"  value="${warrantyCardCode}" >
                           <div style="margin-top: 15px"> 
                               <div>
                            <input style="margin-top: 15px" class="form-control" type="search" name="productCode" placeholder="Product Code"  value="${productCode}" >
                               </div>
                               <div style="margin-top: 15px" >
                               <label >Show 
                                <select name="page-size" class="form-select form-select-sm d-inline-block" style="width: auto;" onchange="this.form.submit()">
                                    <c:forEach items="${pagination.listPageSize}" var="s">
                                        <option value="${s}" ${pagination.pageSize==s?"selected":""}>${s}</option>
                                    </c:forEach>
                                </select> 
                                entries
                            </label>
                                   </div>
                               </div>
                        </div>
                        <div class="col-sm-6 col-md-6" style="width: 500px">
                                 <select style="margin-top: 15px" class="form-select" name="warrantyStatus">
                                     <option ${(warrantyStatus=='')?"selected":""} value="all" >Warranty Status </option>
                                    <option ${(warrantyStatus=='fixing')?"selected":""} value="fixing">Fixing</option>
                                    <option ${(warrantyStatus=='done')?"selected":""} value="done">Done</option>
                                    <option ${(warrantyStatus=='completed')?"selected":""} value="completed">Completed</option>
                                    <option ${(warrantyStatus=='cancel')?"selected":""} value="cancel">Cancel</option>
                                </select>
                                 <select style="margin-top: 15px" class="form-select" name="typeMaintain">
                                    <option value="">Type Maintain</option>
                                    <option ${(typeMaintain=='maintain')?"selected":""} value="maintain">Maintain</option>
                                    <option ${(typeMaintain=='repair')?"selected":""} value="repair">Repair</option>
                                </select>
                                <div style="float: right">
                                <button class="btn btn-primary" style="margin-top: 15px" type="submit">Search</button>
                                </div>
                        </div>
                                </div>
                        <table class="table table-hover my-0">
                            <thead>
                            <tr>
                                <th>Warranty Card Code</th>
                                <th>
                                    <form action="feedbacklog" method="get">
                                        <input type="hidden" name="page" value="${pagination.currentPage}" />
                                        <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                        <input type="hidden" name="sort" value="CreatedDate" />
                                        <input type="hidden" name="order" value="${pagination.sort eq 'CreatedDate' and pagination.order eq 'asc' ? 'desc' : 'asc'}" />
                                        <c:if test="${fn:length(pagination.searchFields) > 0}">
                                            <c:forEach var="i" begin="0" end="${fn:length(pagination.searchFields) - 1}">
                                                <input type="hidden" name="${pagination.searchFields[i]}" value="${pagination.searchValues[i]}">
                                            </c:forEach>
                                        </c:if>
                                                Created Date
                                        <button type="submit" class="btn-sort btn-primary btn">
                                            <i class="align-middle fas fa-fw
                                               ${pagination.sort eq 'CreatedDate' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                            </i>
                                        </button>
                                       </form> 
                                </th>
                                <th>Product Code</th>
                                <th>Product Name</th>
                                <th>Type</th>
                                <th>Issue Description</th>
                                <th>Warranty Status</th>
                                <!--<th></th>-->
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listProductUnderMaintain}" var="o">
                                <tr>
                                    <td>${o.warrantyCardCode}</td>
                                    <td>${o.createdDate}</td>
                                    <td>${o.productCode!=null?o.productCode:o.unknownProductCode}</td>
                                    <td>${o.productName!=null?o.productName:o.unknownProductName}</td>
                                    <td>${o.productName!=null?"Maintain":o.unknownProductName!=null?"Repair":"None"}</td>
                                    <td>${o.issueDescription}</td>
                                    <td>${o.warrantyStatus}</td>
                                    <!--<td><a class="btn btn-primary" href="feedback?action=createFeedback&warrantyCardID=${o.warrantyCardID}">Create Feedback</a></td>-->
                                </tr>
                            </c:forEach>
                        </tbody>
                        
                        </table>
                         </form>   
                    </div>
                     <jsp:include page="/includes/pagination.jsp" />
                     <!--<a  href="feedback?action=viewListFeedbackByCustomerId">Feedback History</a>-->
                     
                </main>
                    
               <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
