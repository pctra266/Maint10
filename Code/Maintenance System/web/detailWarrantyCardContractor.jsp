<%-- 
    Document   : detailWarrantyCardContractor
    Created on : Mar 19, 2025, 7:46:13 PM
    Author     : sonNH
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Warranty Card Details</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            img {
                max-width: 200px;
                margin: 5px;
            }
            .section {
                margin-bottom: 20px;
            }
            .btn {
                padding: 10px 15px;
                border: none;
                cursor: pointer;
                font-size: 16px;
                margin-right: 10px;
            }
            .btn-waiting {
                background-color: #ffc107;
                color: white;
            }
            .btn-receive {
                background-color: #17a2b8;
                color: white;
            }
            .btn-cancel {
                background-color: #dc3545;
                color: white;
            }
            .btn-done {
                background-color: #28a745;
                color: white;
            }
            .btn-invoice {
                background-color: #007bff;
                color: white;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">

                    <!-- Hiển thị thông báo lỗi (nếu có) -->
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger">
                            ${errorMessage}
                        </div>
                    </c:if>

                    <!-- Hiển thị thông báo thành công (nếu có) -->
                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success">
                            ${successMessage}
                        </div>
                    </c:if>
                    
                    <h1>Warranty Card Details</h1>

                    <!-- Thông tin cơ bản -->
                    <div class="section">
                        <p><strong>Warranty Card Code:</strong> ${warrantyDetails.warrantyCardCode}</p>
                        <p><strong>Issue Description:</strong> ${warrantyDetails.issueDescription}</p>
                    </div>

                    <!-- Thông tin Staff -->
                    <div class="section">
                        <h2>Staff</h2>
                        <p><strong>Name:</strong> ${warrantyDetails.staffName}</p>
                        <p><strong>Phone Number:</strong> ${warrantyDetails.staffPhone}</p>
                        <p><strong>Email:</strong> ${warrantyDetails.staffEmail}</p>
                    </div>

                    <!-- Thông tin Sản phẩm -->
                    <div class="section">
                        <h2>Product Information</h2>
                        <c:choose>
                            <c:when test="${not empty warrantyDetails.productCode}">
                                <p><strong>Product Code:</strong> ${warrantyDetails.productCode}</p>
                                <p><strong>Product Name:</strong> ${warrantyDetails.productName}</p>
                                <p><strong>Brand:</strong> ${warrantyDetails.brandName}</p>
                                <p><strong>Product Type:</strong> ${warrantyDetails.typeName}</p>
                            </c:when>
                            <c:otherwise>
                                <p><strong>Product Name:</strong> ${warrantyDetails.unknownProductName}</p>
                                <p><strong>Product Code:</strong> ${warrantyDetails.unknownProductCode}</p>
                                <p><strong>Description:</strong> ${warrantyDetails.unknownProductDescription}</p>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Ảnh liên quan -->
                    <div class="section">
                        <h2>Related Images</h2>
                        <c:if test="${not empty warrantyDetails.mediaUrls}">
                            <ul style="list-style-type: none; padding: 0;">
                                <c:forEach var="url" items="${warrantyDetails.mediaUrls}">
                                    <li><img src="${url}" alt="Warranty Card Image"/></li>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </div>

                    <!-- Contractor Status -->
                    <div class="section">
                        <h2>Contractor Status</h2>
                        <c:if test="${not empty warrantyDetails.contractorStatuses}">
                            <ul>
                                <c:forEach var="status" items="${warrantyDetails.contractorStatuses}">
                                    <li>${status}</li>
                                </c:forEach>
                            </ul>
                        </c:if>
                        <!-- Hiển thị trạng thái hiện tại (nếu có) -->
                        <!-- 
                            Nếu bạn muốn hiển thị status hiện tại của warrantyDetails,
                            bạn có thể thêm dòng:
                            <p>Current status: ${warrantyDetails.status}</p>
                        -->
                    </div>

                    <!-- Các nút cập nhật trạng thái -->
                    <div class="section">
                        <!-- Waiting -->
                        <form action="warrantyCardDetailContractor" method="post" style="display:inline;">
                            <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                            <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                            <input type="hidden" name="status" value="waiting">
                            <button type="submit" class="btn btn-waiting">Waiting</button>
                        </form>
                        
                        <!-- Receive (chỉ hiển thị nếu status != 'receive') -->
                        <c:if test="${warrantyDetails.status ne 'receive'}">
                            <form action="warrantyCardDetailContractor" method="post" style="display:inline;">
                                <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                                <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                                <input type="hidden" name="status" value="receive">
                                <button type="submit" class="btn btn-receive">Receive</button>
                            </form>
                        </c:if>
                        
                        <!-- Nếu status == 'receive', hiển thị lựa chọn subStatus -->
                        <c:if test="${warrantyDetails.status eq 'receive'}">
                            <div style="display:inline-block; margin-left:20px;">
                                <form action="warrantyCardDetailContractor" method="post">
                                    <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                                    <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                                    <input type="hidden" name="status" value="receive">
                                    
                                    <p>Chọn trạng thái phụ:</p>
                                    <input type="radio" name="subStatus" value="send_outsource" id="send_outsource">
                                    <label for="send_outsource">Send Outsource</label><br>
                                    
                                    <input type="radio" name="subStatus" value="lost" id="lost">
                                    <label for="lost">Lost</label><br>
                                    
                                    <input type="radio" name="subStatus" value="receive_outsource" id="receive_outsource">
                                    <label for="receive_outsource">Receive Outsource</label><br>
                                    
                                    <input type="radio" name="subStatus" value="unfixed_outsource" id="unfixed_outsource">
                                    <label for="unfixed_outsource">Unfixed Outsource</label><br>
                                    
                                    <input type="radio" name="subStatus" value="back_outsource" id="back_outsource">
                                    <label for="back_outsource">Back Outsource</label><br>
                                    
                                    <button type="submit" class="btn btn-receive">Cập nhật trạng thái phụ</button>
                                </form>
                            </div>
                        </c:if>
                        
                        <!-- Refuse -->
                        <form action="warrantyCardDetailContractor" method="post" style="display:inline;">
                            <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                            <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                            <input type="hidden" name="status" value="cancel">
                            <button type="submit" class="btn btn-cancel">Refuse</button>
                        </form>
                        
                        <!-- Done -->
                        <form action="warrantyCardDetailContractor" method="post" style="display:inline;">
                            <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                            <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                            <input type="hidden" name="status" value="done">
                            <button type="submit" class="btn btn-done">Done</button>
                        </form>
                        
                        <!-- Create Invoice -->
                        <form action="createInvoice" method="post" style="display:inline;">
                            <input type="hidden" name="code" value="${warrantyDetails.warrantyCardCode}">
                            <button type="submit" class="btn btn-invoice">Create Invoice</button>
                        </form>
                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
    </body>
</html>
