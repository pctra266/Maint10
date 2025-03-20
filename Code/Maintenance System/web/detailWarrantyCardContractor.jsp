<%-- 
    Document   : updateProduct1
    Created on : Feb 6, 2025, 5:07:44 AM
    Author     : sonNH
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            body {
                font-family: 'Inter', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f3f3f3;
            }
            /* Container chia 2 cột */
            .container {
                display: flex;
                gap: 20px;
                margin: 20px;
            }
            .left-panel, .right-panel {
                flex: 1;
                background: #fff;
                padding: 20px;
                border: 1px solid #ddd;
                border-radius: 8px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            .left-panel h1, .left-panel h2,
            .right-panel h2 {
                margin-top: 0;
            }
            .section {
                margin-bottom: 20px;
            }
            .section p {
                margin: 8px 0;
            }
            /* Image container & slider */
            .image-container {
                position: relative;
                margin-bottom: 20px;
                border: 1px solid #ccc;
                border-radius: 4px;
                overflow: hidden;
            }
            .image-list {
                list-style: none;
                padding: 0;
                margin: 0;
                display: flex;
                justify-content: center;
            }
            .image-list li {
                display: none;
                width: 100%;
                text-align: center;
            }
            .image-list li.active {
                display: block;
            }
            .image-list img {
                max-width: 100%;
                height: auto;
                border-radius: 4px;
            }
            .arrow-btn {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                background-color: rgba(0, 0, 0, 0.4);
                border: none;
                color: #fff;
                padding: 10px;
                cursor: pointer;
                font-size: 20px;
                border-radius: 50%;
            }
            .arrow-btn.left {
                left: 10px;
            }
            .arrow-btn.right {
                right: 10px;
            }
            /* Action buttons */
            .action-buttons {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
                justify-content: center;
            }
            .action-buttons form {
                margin: 0;
            }
            .btn {
                padding: 10px 15px;
                border: none;
                cursor: pointer;
                font-size: 16px;
                border-radius: 4px;
            }
            .btn-waiting {
                background-color: #ffc107;
                color: #fff;
            }
            .btn-receive {
                background-color: #17a2b8;
                color: #fff;
            }
            .btn-cancel {
                background-color: #dc3545;
                color: #fff;
            }
            .btn-done {
                background-color: #28a745;
                color: #fff;
            }
            .btn-invoice {
                background-color: #007bff;
                color: #fff;
            }

        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">

                    <!-- Thông báo lỗi/thành công -->
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger">${errorMessage}</div>
                    </c:if>
                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success">${successMessage}</div>
                    </c:if>

                    <div class="container">
                        <!-- Left Panel: Thông tin phiếu bảo hành, Staff, Sản phẩm, Status -->
                        <div class="left-panel">
                            <h1>Warranty Card Details</h1>
                            <div class="section">
                                <p><strong>Warranty Card Code:</strong> ${warrantyDetails.warrantyCardCode}</p>
                                <p><strong>Issue Description:</strong> ${warrantyDetails.issueDescription}</p>
                            </div>
                            <div class="section">
                                <h2>Staff</h2>
                                <p><strong>Name:</strong> ${warrantyDetails.staffName}</p>
                                <p><strong>Phone:</strong> ${warrantyDetails.staffPhone}</p>
                                <p><strong>Email:</strong> ${warrantyDetails.staffEmail}</p>
                            </div>
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
                            <div class="section">
                                <h2>Contractor Status</h2>
                                <c:if test="${not empty warrantyDetails.contractorStatuses}">
                                    <ul>
                                        <c:forEach var="status" items="${warrantyDetails.contractorStatuses}">
                                            <li>${status}</li>
                                            </c:forEach>
                                    </ul>
                                </c:if>
                            </div>
                        </div>

                        <!-- Right Panel: Hình ảnh và nút hành động -->
                        <div class="right-panel">
                            <div class="section">
                                <h2>Related Images</h2>
                                <c:if test="${not empty warrantyDetails.mediaUrls}">
                                    <div class="image-container">
                                        <button class="arrow-btn left" onclick="prevImage()">&#9664;</button>
                                        <ul class="image-list" id="imageList">
                                            <c:forEach var="url" items="${warrantyDetails.mediaUrls}" varStatus="loop">
                                                <li class="${loop.index == 0 ? 'active' : ''}">
                                                    <img src="${url}" alt="Warranty Card Image"/>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                        <button class="arrow-btn right" onclick="nextImage()">&#9654;</button>
                                    </div>
                                </c:if>
                            </div>
                            <div class="section">
                                <h2>Actions</h2>
                                <div class="action-buttons">
                                    <!-- Nếu trạng thái chưa là 'done' thì hiển thị nút cập nhật -->
                                    <c:if test="${warrantyDetails.contractorStatuses[0] ne 'done'}">
                                        <!-- Nếu chưa ở trạng thái 'receive' hiển thị nút Receive -->
                                        <c:forEach var="status" items="${warrantyDetails.contractorStatuses}">
                                            <c:if test="${status ne 'receive'}">
                                                <form action="warrantyCardDetailContractor" method="post">
                                                    <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                                                    <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                                                    <input type="hidden" name="status" value="receive">
                                                    <input type="hidden" name="subStatus" value="accept_outsource">
                                                    <button type="submit" class="btn btn-receive">Receive</button>
                                                </form>
                                            </c:if>
                                        </c:forEach>
                                        <!-- Nút Refuse -->
                                        <form action="warrantyCardDetailContractor" method="post">
                                            <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                                            <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                                            <input type="hidden" name="status" value="cancel">
                                            <input type="hidden" name="subStatus" value="refuse_outsource">
                                            <button type="submit" class="btn btn-cancel">Refuse</button>
                                        </form>
                                    </c:if>

                                    <!-- Nếu trạng thái là 'receive', hiển thị lựa chọn cập nhật trạng thái phụ -->
                                    <c:forEach var="status" items="${warrantyDetails.contractorStatuses}">
                                        <c:if test="${status eq 'receive'}">
                                            <form action="warrantyCardDetailContractor" method="post">
                                                <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                                                <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                                                <input type="hidden" name="status" value="receive">
                                                <p>Select Process:</p>
                                                <input type="radio" name="subStatus" value="lost" id="lost">
                                                <label for="lost">Lost Product</label><br>
                                                <input type="radio" name="subStatus" value="receive_outsource" id="receive_outsource">
                                                <label for="receive_outsource">Product Received</label><br>
                                                <input type="radio" name="subStatus" value="unfixed_outsource" id="unfixed_outsource">
                                                <label for="unfixed_outsource">Unable to Repair</label><br>
                                                <input type="radio" name="subStatus" value="back_outsource" id="back_outsource">
                                                <label for="back_outsource">Return Product</label><br>
                                                <button type="submit" class="btn btn-receive">Update Process</button>
                                            </form>
                                        </c:if>
                                    </c:forEach>
                                    <!-- Nút Done luôn hiển thị -->
                                    <form action="warrantyCardDetailContractor" method="post">
                                        <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                                        <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                                        <input type="hidden" name="subStatus" value="fixed_outsource">
                                        <input type="hidden" name="status" value="done">
                                        <button type="submit" class="btn btn-done">Done</button>
                                    </form>
                                    <!-- Nút Create Invoice -->
                                    <form action="repairCreateInvoice" method="post">
                                        <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                                        <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                                        <input type="hidden" name="staffId" value="${warrantyDetails.staffID}">
                                        <input type="hidden" name="code1" value="${warrantyDetails.warrantyCardCode}">
                                       
                                        
                                        <button type="submit" class="btn btn-invoice">Create Invoice</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
        <!-- JavaScript đơn giản để điều khiển chuyển ảnh -->
        <script>
                                            // Giả sử danh sách ảnh được hiển thị theo dạng dòng ngang và có thể trượt
                                            // Đây là ví dụ đơn giản, cần điều chỉnh thêm cho phù hợp với yêu cầu thật sự
                                            let currentIndex = 0;
                                            const imageList = document.getElementById('imageList');
                                            const images = imageList.getElementsByTagName('li');
                                            const totalImages = images.length;

                                            function updateImages() {
                                                // Ẩn tất cả các ảnh
                                                for (let i = 0; i < totalImages; i++) {
                                                    images[i].style.display = 'none';
                                                }
                                                // Hiển thị ảnh hiện tại
                                                if (totalImages > 0) {
                                                    images[currentIndex].style.display = 'block';
                                                }
                                            }

                                            function prevImage() {
                                                currentIndex = (currentIndex - 1 + totalImages) % totalImages;
                                                updateImages();
                                            }

                                            function nextImage() {
                                                currentIndex = (currentIndex + 1) % totalImages;
                                                updateImages();
                                            }

                                            // Khởi tạo hiển thị ảnh đầu tiên
                                            updateImages();
        </script>
    </body>
</html>
