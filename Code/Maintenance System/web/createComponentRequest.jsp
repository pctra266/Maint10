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
                    <h1>Create Component Request</h1>
                    <div>
                        ${mess}
                    </div>
                    <form action="componentRequest" method="get">
                        <input type="hidden" name="action" value="createComponentRequest">
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
                                 <select style="margin-top: 15px" class="form-select" name="warrantyStatus" onchange="this.form.submit()">
                                     <option ${(warrantyStatus=='')?"selected":""} value="all" >Warranty Status </option>
                                    <option ${(warrantyStatus=='fixing')?"selected":""} value="fixing">Fixing</option>
                                    <option ${(warrantyStatus=='done')?"selected":""} value="done">Done</option>
                                    <option ${(warrantyStatus=='completed')?"selected":""} value="completed">Completed</option>
                                    <option ${(warrantyStatus=='cancel')?"selected":""} value="cancel">Cancel</option>
                                </select>
                                 <select style="margin-top: 15px" class="form-select" name="typeMaintain" onchange="this.form.submit()">
                                    <option value="">Type Maintain</option>
                                    <option ${(typeMaintain=='maintain')?"selected":""} value="maintain">Maintain</option>
                                    <option ${(typeMaintain=='repair')?"selected":""} value="repair">Repair</option>
                                </select>
                                <div style="float: right">
                                <button class="btn btn-primary" style="margin-top: 15px" type="submit">Search</button>
                                </div>
                        </div>
                                </div>
                    </form>
                    <form action="componentRequest" method="post">
                        <input type="hidden" name="action" value="createComponentRequest">
                        <input type="hidden"  name="warrantyCardID" value="${warrantyCardID}">
                        <table class="table table-hover my-0">
                            <thead>
                            <tr>
                                <th>Component Code</th>
                                <th>Component Type</th>
                                <th>Component Brand</th>
                                <th>Component Name</th>
                                <th>Quantity</th>
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
                                <td><input type="number" name="quantities"  min="0" required></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        
                        </table>
                            <div><textarea name="note"></textarea></div>
                            <button class="btn btn-primary" type="submit">Submit</button>
                    </form>
                    <div>
                         <form method="get" action="componentRequest" >
                            <input type="hidden" name="action" value="viewComponentRequestDashboard">
                            
                        
                         </form>   
                    </div>
                     <jsp:include page="/includes/pagination.jsp" />
                </main>
                <jsp:include page="/includes/footer.jsp" />

            </div>

        </div>

        <script src="js/app.js"></script>

    </body>
</html>
