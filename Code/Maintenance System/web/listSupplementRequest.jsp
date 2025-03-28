<%-- 
    Document   : listSupplementRequest
    Created on : Mar 15, 2025, 11:06:09 PM
    Author     : Tra Pham
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>list Supplement Request</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
               <main class="content">
                   <c:if test="${not empty param.error}">
                       <div class="alert alert-danger" role="alert">
                           ${param.error}
                       </div>
                   </c:if>

                   <c:if test="${not empty param.success}">
                       <div class="alert alert-success" role="alert">
                           ${param.success}
                       </div>
                   </c:if>
                     <h1 class="text-center ">List Supplement Request</h1>
                     
                   <form method="get" action="supplementRequest">
            <input type="hidden" name="action" value="listSupplementRequest">
            <div class="row" style="justify-content: space-between">
                <div class="col-md-6" style="width: 500px">
                        <select  style="margin-top: 15px" class="form-select" name="status" onchange="this.form.submit()">
                            <option ${(status=='')?"selected":""} value="" >Action </option>
                            <option ${(status=='waiting')?"selected":""} value="waiting">Waiting</option>
                            <option ${(status=='approved')?"selected":""} value="approved">Approved</option>
                            <option ${(status=='cancel')?"selected":""} value="cancel">Cancel</option>
                        </select>
                                
                    <input style="margin-top: 15px" type="search" name="componentName" class="form-control" placeholder="Component Name" value="${componentName}">
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
                    <select style="margin-top: 15px" class="form-select" name="typeID" onchange="this.form.submit()">
                                     <option value="" >Component Type </option>
                                    <c:forEach var="type" items="${typeList}">
                                     <option value="${type.typeID}" ${type.typeID eq typeID ? "selected" : ""}>${type.typeName}</option>
                                </c:forEach>
                                </select>
                    
                     <select style="margin-top: 15px" class="form-select" name="brandID" onchange="this.form.submit()">
                                    <option value="">Component Brand</option>
                                    <c:forEach var="brand" items="${brandList}">
                                    <option value="${brand.brandId}" ${brandID  eq brand.brandId ? "selected" : ""}>${brand.brandName}</option>
                                </c:forEach>
                                </select>
                    
                    <div style="float: right">
                                <button  class="btn btn-primary" style="margin-top: 15px" type="submit">Search</button>
                                </div>
                </div>
                
        </form>

        <table class="table table-hover my-0">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Source</th>
            <th>Type</th>
            <th>Brand</th>
            <th>Request Date</th>
            <th>Status</th>
            <th>Note</th>
            <th>Create Component</th> 
            <th>Approve</th>
            <th>Cancel</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${listSupplementRequest}" var="request">
            <tr>
                <td>${request.requestID}</td>
                <td>${request.componentName}</td>
                <td>${request.componentType}</td>
                <td>${request.type}</td>
                <td>${request.brand}</td>
                <td>${request.requestDate}</td>
                <td>${request.status}</td>
                <td>${request.note}</td>
                <td>
                    <c:if test="${request.componentType eq 'product'}">
                         <form action="ComponentWarehouse/Add" method="POST" enctype="multipart/form-data" style="display: inline;">
                             <input name="Name" value="${request.componentName}" type="hidden">
                             <input name="Type"  value="${request.type}" type="hidden">
                             <input name="Brand"  value="${request.brand}" type="hidden">
                             <button type="submit"class="btn btn-primary"> Add Component</button>
                        </form>
                        
                    </c:if>
                </td>
                <td>
                    <form action="supplementRequest" method="post">
                        <input type="hidden" name="action" value="updateSupplementRequest">
                        <input type="hidden" name="requestID" value="${request.requestID}">
                        <input type="hidden" name="status" value="approved">
                        <button class="btn btn-primary" type="submit" 
                                onclick="return confirm('Are you sure you want to approve this request?');">
                            Approve
                        </button>
                    </form>
                </td>
                <td>
                    <form action="supplementRequest" method="post">
                        <input type="hidden" name="action" value="updateSupplementRequest">
                        <input type="hidden" name="requestID" value="${request.requestID}">
                        <input type="hidden" name="status" value="cancel">
                        <button class="btn btn-danger" type="submit"
                                onclick="return confirm('Are you sure you want to cancel this request?');">
                            Cancel
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<jsp:include page="/includes/pagination.jsp" />
                        </main>


                <jsp:include page="/includes/footer.jsp" />
            </div>

        </div>

        <script src="js/app.js"></script><div class="notyf"></div><div class="notyf-announcer" aria-atomic="true" aria-live="polite" style="border: 0px; clip: rect(0px, 0px, 0px, 0px); height: 1px; margin: -1px; overflow: hidden; padding: 0px; position: absolute; width: 1px; outline: 0px;"></div>
        <script src="js/format-input.js"></script>
    </body>
</html>
