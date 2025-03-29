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
            /* Định dạng chung cho body */
            body {
                font-family: 'Inter', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f3f3f3;
            }

            /* Container chia 2 cột & căn giữa nội dung trong main */
            main.content .container {
                max-width: 1200px;  /* Điều chỉnh kích thước tối đa theo ý bạn */
                margin: 0 auto;     /* Căn giữa theo chiều ngang */
                display: flex;
                gap: 20px;
                padding: 20px;
            }

            /* Styling cho main content */
            main.content {
                background: #ffffff;
                border-radius: 8px;
                padding: 25px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }

            /* Styling cho hai panel */
            .left-panel, .right-panel {
                flex: 1;
                background: #fff;
                padding: 25px;
                border: 1px solid #e0e0e0;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.05);
            }

            /* Tiêu đề các panel và section sử dụng màu xanh dương chủ đạo */
            .left-panel h1,
            .left-panel h2,
            .right-panel h2 {
                font-family: 'Inter', sans-serif;
                color: #326ABC;  /* Màu xanh dương chủ đạo */
                border-bottom: 2px solid #cce5ff;
                padding-bottom: 5px;
                margin-bottom: 15px;
            }

            .section {
                margin-bottom: 25px;
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

            /* Arrow button (slider) */
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

            /* Styling cho Process History */
            .process-history-container {
                max-height: 300px;       /* Nếu danh sách quá dài, thanh cuộn sẽ xuất hiện */
                overflow-y: auto;
                border: 1px solid #ddd;
                border-radius: 4px;
                padding: 10px;
                background: #fafafa;
            }

            .process-history-container table {
                width: 100%;
                border-collapse: collapse;
            }

            .process-history-container table th,
            .process-history-container table td {
                padding: 10px;
                border-bottom: 1px solid #eee;
                text-align: left;
            }

            .process-history-container table th {
                background: #f9f9f9;
                font-weight: 600;
            }

            /* Tùy chỉnh thanh cuộn cho trình duyệt Webkit (Chrome, Safari) */
            .process-history-container::-webkit-scrollbar {
                width: 8px;
            }

            .process-history-container::-webkit-scrollbar-track {
                background: #f1f1f1;
                border-radius: 4px;
            }

            .process-history-container::-webkit-scrollbar-thumb {
                background: #c1c1c1;
                border-radius: 4px;
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

            /* Styling chung cho các nút */
            .btn {
                padding: 10px 15px;
                border: none;
                cursor: pointer;
                font-size: 16px;
                border-radius: 4px;
                color: #fff;
                transition: background 0.3s ease;
            }

            /* Nút với màu chức năng riêng (đã điều chỉnh theo màu chủ đạo xanh dương cho các nút chính) */
            /* Nút chờ: sử dụng màu xanh nhạt */
            .btn-waiting {
                background-color: #5bc0de;
            }
            .btn-waiting:hover {
                background-color: #3399cc;
                color: white;
                text-decoration: none;
            }

            /* Nút nhận: sử dụng màu xanh dương chủ đạo */
            .btn-receive {
                background-color: #007BFF;
            }
            .btn-receive:hover {
                background-color: #0056b3;
                color: white;
                text-decoration: none;
            }

            /* Nút hủy: giữ màu đỏ để báo hiệu hành động quan trọng */
            .btn-cancel {
                background-color: #dc3545;
            }
            .btn-cancel:hover {
                background-color: #c82333;
                color: white;
                text-decoration: none;
            }

            /* Nút hoàn thành: giữ màu xanh lá cây để báo hiệu thành công */
            .btn-done {
                background-color: #28a745;
            }
            .btn-done:hover {
                background-color: #218838;
                color: white;
                text-decoration: none;
            }

            /* Nút tạo hóa đơn: sử dụng màu xanh dương chủ đạo */
            .btn-invoice {
                background-color: #326ABC;
            }
            .btn-invoice:hover {
                background-color: #0056b3;
                color: white;
                text-decoration: none;
            }

        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">

                    <c:if test="${not empty errorMessage}">
                        <div style="padding: 10px; display: flex; justify-content: center" class="alert alert-danger">${errorMessage}</div>
                    </c:if>
                    <c:if test="${not empty successMessage}">
                        <div style="padding: 10px; display: flex; justify-content: center" class="alert alert-success">${successMessage}</div>
                    </c:if>

                    <div class="container">
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
                            <!-- Phần hiển thị Process History -->
                            <div class="section process-history-container">
                                <h2>Process History</h2>
                                <c:if test="${not empty warrantyDetails.processList}">
                                    <table border="1" cellpadding="5" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>Technician</th>
                                                <th>Action</th>
                                                <th>Action Date</th>
                                                <th>Note</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="process" items="${warrantyDetails.processList}">
                                                <tr>
                                                    <td>${warrantyDetails.staffName}</td>
                                                    <td>${process.Action}</td>
                                                    <td>${process.ActionDate}</td>
                                                    <td>${process.Note}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:if>
                                <c:if test="${empty warrantyDetails.processList}">
                                    <p>No process history available.</p>
                                </c:if>
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
                                    <c:choose>
                                        <c:when test="${warrantyDetails.lastProcessStatus eq 'receive_outsource'}">
                                            <form action="warrantyCardDetailContractor" method="post">
                                                <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                                                <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                                                <input type="hidden" name="subStatus" value="fixed_outsource">
                                                <input type="hidden" name="status" value="done">
                                                <input type="hidden" name="staffId" value="${warrantyDetails.staffID}">
                                                <button type="submit" class="btn btn-done">Fixed</button>
                                            </form>

                                            <form action="warrantyCardDetailContractor" method="post">
                                                <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                                                <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                                                <input type="hidden" name="subStatus" value="lost">
                                                <input type="hidden" name="status" value="cancel">
                                                <input type="hidden" name="staffId" value="${warrantyDetails.staffID}">
                                                <button type="submit" class="btn btn-dark">Lost</button>
                                            </form>

                                            <form action="warrantyCardDetailContractor" method="post">
                                                <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                                                <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                                                <input type="hidden" name="subStatus" value="unfixable_outsource">
                                                <input type="hidden" name="status" value="cancel">
                                                <input type="hidden" name="staffId" value="${warrantyDetails.staffID}">
                                                <button type="submit" class="btn btn-cancel">Unfixed</button>
                                            </form>

                                            <form action="warrantyCardDetailContractor" method="post">
                                                <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                                                <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                                                <input type="hidden" name="subStatus" value="back_outsource">
                                                <input type="hidden" name="status" value="done">
                                                <input type="hidden" name="staffId" value="${warrantyDetails.staffID}">
                                                <button type="submit" class="btn btn-done">Back Product</button>
                                            </form>
                                        </c:when>
                                        <c:when test="${warrantyDetails.lastProcessStatus eq 'send_outsource'}">
                                            <form action="warrantyCardDetailContractor" method="post">
                                                <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                                                <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                                                <input type="hidden" name="subStatus" value="receive_outsource">
                                                <input type="hidden" name="status" value="receive">
                                                <input type="hidden" name="staffId" value="${warrantyDetails.staffID}">
                                                <button type="submit" class="btn btn-cancel">Received Product</button>
                                            </form>

                                            <form action="warrantyCardDetailContractor" method="post">
                                                <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                                                <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                                                <input type="hidden" name="subStatus" value="back_outsource">
                                                <input type="hidden" name="status" value="done">
                                                <input type="hidden" name="staffId" value="${warrantyDetails.staffID}">
                                                <button type="submit" class="btn btn-done">Back Product</button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <form action="warrantyCardDetailContractor" method="post">
                                                <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                                                <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                                                <input type="hidden" name="subStatus" value="accept_outsource">
                                                <input type="hidden" name="status" value="receive">
                                                <input type="hidden" name="staffId" value="${warrantyDetails.staffID}">
                                                <button type="submit" class="btn btn-done">Accept Request</button>
                                            </form>

                                            <form action="warrantyCardDetailContractor" method="post">
                                                <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                                                <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                                                <input type="hidden" name="subStatus" value="refuse_outsource">
                                                <input type="hidden" name="status" value="cancel">
                                                <input type="hidden" name="staffId" value="${warrantyDetails.staffID}">
                                                <button type="submit" class="btn btn-danger">Refuse Request</button>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>

                                    <c:if test="${warrantyDetails.contractorStatuses eq '[done]'}">
                                        <form action="repairCreateInvoice" method="post">
                                            <input type="hidden" name="code" value="${warrantyDetails.contractorCardID}">
                                            <input type="hidden" name="cardId" value="${warrantyDetails.warrantyCardID}">
                                            <input type="hidden" name="staffId" value="${warrantyDetails.staffID}">
                                            <input type="hidden" name="code1" value="${warrantyDetails.warrantyCardCode}">
                                            <button type="submit" class="btn btn-invoice">Create Invoice</button>
                                        </form>
                                    </c:if>

                                    <div class="section">
                                        <a href="warrantyCardRepairContractor" class="btn btn-facebook" style="text-decoration: none;">Back</a>
                                    </div>
                                </div>       
                            </div>
                        </div>
                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
        <script>
                                            let currentIndex = 0;
                                            const imageList = document.getElementById('imageList');
                                            const images = imageList.getElementsByTagName('li');
                                            const totalImages = images.length;

                                            function updateImages() {
                                                for (let i = 0; i < totalImages; i++) {
                                                    images[i].style.display = 'none';
                                                }
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
                                            updateImages();
        </script>
    </body>
</html>


