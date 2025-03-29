<%-- 
    Document   : createComponentRequest
    Created on : Feb 19, 2025, 11:28:53 PM
    Author     : Tra Pham
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Component Request</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h1 class="text-center">Create Component Request</h1>
                    <div class="alert-primary" >
                        ${param.mess}
                    </div>
                    <a class="btn btn-primary" href="componentRequest?action=viewComponentRequestDashboard" >Back</a>
                    <form action="componentRequest" method="get">
                        <input type="hidden" name="action" value="createComponentRequest">
                        <input type="hidden" name="productCode" value="${productCode}"> 
                        <input type="hidden"  name="warrantyCardID" value="${warrantyCardID}">
                        <div class="row"  style="justify-content: space-between">
                        <div class="col-sm-6 col-md-6" style="width: 500px">
                           <input style="margin-top: 15px" class="form-control" type="search" name="componentName" placeholder="Component Name"  value="${componentName}" >
                           <div style="margin-top: 15px"> 
                               <div>
                            <input style="margin-top: 15px" class="form-control" type="search" name="componentCode" placeholder="Component Code"  value="${componentCode}" >
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
                                <button class="btn btn-primary" style="margin-top: 15px" type="submit">Search</button>
                                </div>
                        </div>
                                </div>
 
                         </form>
                               

                        <table class="table table-hover my-0">
                            <thead>
                            <tr>
                                <th>Component Code</th>
                                <th>Component Type</th>
                                <th>Component Brand</th>
                                <th>Component Name</th>
                                <th>Add to request</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="o" items="${listComponentByProductCode}" >
                                <tr>
                            <input type="hidden" name="componentIDs" value="${o.componentID}">
                                <td>${o.componentCode}</td>
                                <td>${o.type}</td>
                                <td>${o.brand}</td>
                                <td>${o.componentName}</td>
                                <td>
                                    <form action="componentRequest" method="get" style="display:inline;">
                                                <input type="hidden" name="action" value="addComponent">
                                                <input type="hidden" name="componentID" value="${o.componentID}">
                                                <input type="hidden" name="productCode" value="${productCode}">
                                                <input type="hidden" name="warrantyCardID" value="${warrantyCardID}">
                                                <input type="hidden" name="page-size" value="${pagination.pageSize}">
                                                <input type="hidden" name="page" value="${pagination.currentPage}">
                                                <input type="hidden" name="componentName" value="${componentName}">
                                                <input type="hidden" name="componentCode" value="${componentCode}">
                                                <input type="hidden" name="typeID" value="${typeID}">
                                                <input type="hidden" name="brandID" value="${brandID}">
                                                <button type="submit" class="btn btn-primary">Add</button>
                                    </form>
                                </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        
                        </table>
                               <jsp:include page="/includes/pagination.jsp" />
                    <form action="componentRequest" method="post">
                        <input type="hidden" name="action" value="createComponentRequest">
                        <input type="hidden" name="productCode" value="${productCode}"> 
                        <input type="hidden"  name="warrantyCardID" value="${warrantyCardID}">
                        <!-- bang moi -->
                        <div style="display: flex; justify-content: space-between; align-items: center;"><a>
                        <h3>Selected Components</h3>
                        </a><a class="btn btn-secondary" href="supplementRequest?action=createSupplementRequest&ComponentType=${productCode!=""?"product":"unknown product"}" target="_blank">Supplement Request</a>
                    </div>
                        
                        <table class="table table-hover my-0">
                            <thead>
                                <tr>
                                    <th>Component Code</th>
                                    <th>Component Type</th>
                                    <th>Component Brand</th>
                                    <th>Component Name</th>
                                    <th>Quantity</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="selected" items="${selectedComponents}">
                                    <tr>
                                        <td>${selected.componentCode}</td>
                                        <td>${selected.type}</td>
                                        <td>${selected.brand}</td>
                                        <td>${selected.componentName}</td>
                                        <td><input type="number" name="quantities" min="0" value="${selected.quantity}"></td>
                                        <td>
                                            <a href="componentRequest?action=removeComponent&componentID=${selected.componentID}&productCode=${productCode}&warrantyCardID=${warrantyCardID}&page-size=${pagination.pageSize}&page=${pagination.currentPage}&componentName=${componentName}&componentCode=${componentCode}&typeID=${typeID}&brandID=${brandID}" class="btn btn-danger">Remove</a>
                                        </td>
                                        <input type="hidden" name="componentIDs" value="${selected.componentID}">
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
 
                            <div><textarea name="note"></textarea></div>
                            <button class="btn btn-primary" type="submit">Submit</button>
                    </form>
                     
                </main>
                <jsp:include page="/includes/footer.jsp" />

            </div>

        </div>
        <script src="js/app.js"></script>

    </body>
</html>
