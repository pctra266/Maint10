<%-- 
    Document   : feedbackDashBoard
    Created on : Feb 7, 2025, 7:48:02 PM
    Author     : Tra Pham
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedback </title>
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
                         <form method="get" action="feedback" >
                            <input type="hidden" name="action" value="viewFeedbackDashboard">
                            <div class="row"  style="justify-content: space-between">
                        <div class="col-sm-6 col-md-6" style="width: 500px">
                           <input style="margin-top: 15px" class="form-control" type="search" name="WarrantyCardCode" placeholder="Warranty Card Code"  value="${WarrantyCardCode}" >
                           <div style="margin-top: 15px"> 
                               <div>
                                   <input type="text" readonly="" style="border: none; background: none; ">
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
                                 <select style="margin-top: 15px" class="form-select" name="WarrantyStatus">
                                    <option value="">Warranty Status </option>
                                    <option ${(WarrantyStatus=='fixing')?"selected":""} value="fixing">Fixing</option>
                                    <option ${(WarrantyStatus=='done')?"selected":""} value="done">Done</option>
                                    <option ${(WarrantyStatus=='completed')?"selected":""} value="completed">Completed</option>
                                    <option ${(WarrantyStatus=='cancel')?"selected":""} value="cancel">Cancel</option>
                                </select>
                                 <select style="margin-top: 15px" class="form-select" name="typeMaintain">
                                    <option value="">Type </option>
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
                                <th>Created Date</th>
                                <th>Product Name</th>
                                <th>Type</th>
                                <th>Issue Description</th>
                                <th>Warranty Status</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            
                            <c:forEach items="${listProductCreateFeedback}" var="o">
                                <tr>
                                    <td>${o.warrantyCardCode}</td>
                                    <td>${o.createdDate}</td>
                                    <td>${o.productName!=null?o.productName:o.unknownProductName}</td>
                                    <td>${o.productName!=null?"Maintain":o.unknownProductName!=null?"Repair":"None"}</td>
                                    <td>${o.issueDescription}</td>
                                    <td>${o.warrantyStatus}</td>
                                    <td><a class="btn btn-primary" href="feedback?action=createFeedback&warrantyCardID=${o.warrantyCardID}">Create Feedback</a></td>
                                </tr>
                            </c:forEach>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td><a class="btn btn-primary" href="feedback?action=createFeedback">Create Feedback</a></td>
                                </tr>
                        </tbody>
                        
                        </table>
                         </form>   
                    </div>
                     <jsp:include page="/includes/pagination.jsp" />
                     <a  href="feedback?action=viewListFeedbackByCustomerId">Feedback History</a>
                     
                </main>
                    
               <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
