<%-- 
    Document   : WarrantyCardDetail
    Created on : Feb 19, 2025, 12:05:43 PM
    Author     : ADMIN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="Responsive Admin & Dashboard Template based on Bootstrap 5">
        <meta name="author" content="AdminKit">
        <meta name="keywords" content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">
        <base href="${pageContext.request.contextPath}/">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link rel="shortcut icon" href="icons/icon-48x48.png" />
        <link rel="canonical" href="https://demo-basic.adminkit.io/" />
        <title>Warranty Card</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            .media-gallery {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
                margin-top: 10px;
            }
            .media-item {
                position: relative;
                cursor: pointer;
            }
            .media-item img, .media-item video {
                height: auto;
                border-radius: 5px;
                transition: transform 0.2s;
            }
            .media-item:hover img, .media-item:hover video {
                transform: scale(1.1);
            }
            .modal {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.8);
                justify-content: center;
                align-items: center;
                z-index: 1000;
            }
            .modal-content {
                width: 90vw;
                height: 100vh;
                position: relative;
                overflow: hidden;
                border-radius: 5px;
            }
            .modal-content img, .modal-content video {
                width: auto;
                height: auto;
                max-width: 100%;
                max-height: 100%;
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                transition: transform 0.1s ease;
                cursor: grab;
                user-select: none; /* Ngăn chọn ảnh/video */
            }
            .modal-close {
                position: absolute;
                top: 20px;
                right: 20px;
                color: white;
                font-size: 30px;
                cursor: pointer;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="../../includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="../../includes/navbar-top.jsp" />
                <main class="content">
                    <form action="Redirect" enctype="multipart/form-data">
                        <input type="hidden" name="target" value="${empty detailWarrantyCardFrom ? '/MaintenanceSystem/WarrantyCard' : detailWarrantyCardFrom}">
                        <button type="submit" class="btn btn-primary d-flex align-items-center justify-content-center" style="transform:translate(-30%,-60%); height: 2.5rem; width: 5.2rem">
                            <i class="fas fa-arrow-left fa-4"></i> <span class="ms-2">Back</span>
                        </button>
                    </form>

                    <!-- Alerts -->
                    <c:if test="${not empty addAlert1}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message"><strong>${addAlert1}</strong></div>
                        </div>
                    </c:if>
                    <c:if test="${not empty updateAlert1}">
                        <div class="alert alert-success alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message"><strong>${updateAlert1}</strong></div>
                        </div>
                    </c:if>
                    <c:if test="${not empty updateAlert0}">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            <div class="alert-message"><strong>${updateAlert0}</strong></div>
                        </div>
                    </c:if>             
                    <c:if test="${latestProcess!=null && !(latestProcess.action=='create'||latestProcess.action=='refuse')}">
                        <!-- Process Buttons -->
                        <div class="mb-3">
                            <h3>Process Actions ${l}</h3>
                            <form action="WarrantyCard/Detail" method="post" class="d-inline">
                                <input type="hidden" name="action" value="process">
                                <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                <input type="hidden" name="processAction" value="fixing">
                                <button type="submit" class="btn btn-primary me-2" ${latestProcess != null && latestProcess.action == 'receive' ? '' : 'disabled'}>Fixing</button>
                            </form>
                            <form action="WarrantyCard/Detail" method="post" class="d-inline">
                                <input type="hidden" name="action" value="process">
                                <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                <input type="hidden" name="processAction" value="refix">
                                <button type="submit" class="btn btn-warning me-2" ${latestProcess != null && (latestProcess.action == 'fixed' || latestProcess.action == 'completed' || latestProcess.action == 'cancel') ? '' : 'disabled'}>Refix</button>
                            </form>
                            <form action="WarrantyCard/Detail" method="post" class="d-inline">
                                <input type="hidden" name="action" value="process">
                                <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                <input type="hidden" name="processAction" value="outsource">
                                <button type="submit" class="btn btn-info me-2" ${latestProcess != null && latestProcess.action != 'completed' && (latestProcess.action == 'fixing'||latestProcess.action == 'refix') ? '' : 'disabled'}>Outsource</button>
                            </form>
                            <form action="WarrantyCard/Detail" method="post" class="d-inline">
                                <input type="hidden" name="action" value="process">
                                <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                <input type="hidden" name="processAction" value="fixed">
                                <button type="submit" class="btn btn-success me-2" ${latestProcess != null && latestProcess.action != 'completed' && (latestProcess.action == 'fixing'||latestProcess.action == 'refix'||latestProcess.action == 'outsource') ? '' : 'disabled'}>Fixed</button>
                            </form>
                            <form action="WarrantyCard/Detail" method="post" class="d-inline">
                                <input type="hidden" name="action" value="process">
                                <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                <input type="hidden" name="processAction" value="completed">
                                <button type="submit" class="btn btn-success me-2" ${latestProcess != null && latestProcess.action == 'fixed' ? '' : 'disabled'}>Completed</button>
                            </form>
                            <form action="WarrantyCard/Detail" method="post" class="d-inline">
                                <input type="hidden" name="action" value="process">
                                <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                <input type="hidden" name="processAction" value="cancel">
                                <button type="submit" class="btn btn-danger me-2" ${latestProcess != null && latestProcess.action != 'completed' && latestProcess.action != 'fixed' && latestProcess.action != 'cancel' ? '' : 'disabled'}>Cancel</button>
                            </form>
                            <form action="WarrantyCard/Detail" method="post" class="d-inline">
                                <input type="hidden" name="action" value="process">
                                <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                <input type="hidden" name="processAction" value="refuse">
                                <button type="submit" class="btn btn-instagram" ${latestProcess != null && latestProcess.action != 'completed' && latestProcess.action != 'fixed' ? '' : 'disabled'}>Refuse</button>
                            </form>
                        </div>

                        <h2>Repair List</h2>
                        <div class="row">
                            <div class="col-md-8">  
                                <div class="row">
                                    <div class="mb-2 col-auto">
                                        <a href="WarrantyCard/AddComponent?ID=${card.warrantyCardID}" class="btn btn-primary">
                                            <i class="fas fa-plus"></i> Add New Component
                                        </a>
                                    </div>
                                    <form action="/MaintenanceSystem/componentRequest" class="mb-2 col-auto">
                                        <input type="hidden" name="action" value="createComponentRequest" readonly> 
                                        <input type="hidden" name="warrantyCardID" value="${card.warrantyCardID}" readonly>  
                                        <input type="hidden" name="productCode" value="${card.productCode}" readonly>  
                                        <button type="submit" class="btn btn-success"><i class="fas fa-add"></i> Request Component</button>
                                    </form>
                                </div>
                                <table class="table table-hover my-0">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Name</th>
                                            <th>Status</th>
                                            <th>Price</th>
                                            <th>Quantity</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="detail" items="${cardDetails}" varStatus="status">
                                            <tr class="${status.index % 2 == 0 ? 'table-primary' : ''}">
                                                <td>${status.index + 1}</td>
                                                <td>${detail.component.componentName}</td>
                                                <td>
                                                    <form action="WarrantyCard/Detail" method="post" id="updateForm-${status.index}">
                                                        <input type="hidden" name="action" value="update">
                                                        <input type="hidden" name="warrantyCardDetailID" value="${detail.warrantyCardDetailID}">
                                                        <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                                        <select name="status" class="form-select status-select">
                                                            <option value="fixing" ${detail.status == 'fixing' ? 'selected' : ''}>Fixing</option>
                                                            <option value="warranty_repaired" ${detail.status == 'warranty_repaired' ? 'selected' : ''}>Repaired (Warranty)</option>
                                                            <option value="warranty_replaced" ${detail.status == 'warranty_replaced' ? 'selected' : ''}>Replaced (Warranty)</option>
                                                            <option value="repaired" ${detail.status == 'repaired' ? 'selected' : ''}>Repaired</option>
                                                            <option value="replace" ${detail.status == 'replace' ? 'selected' : ''}>Replace</option>
                                                        </select>
                                                </td>
                                                <td>
                                                    <input type="number" name="price" class="form-control form-control-sm price-input" value="${detail.price}" min="0" step="0.01" ${detail.status == 'warranty_repaired' || detail.status == 'warranty_replaced' ? 'readonly' : ''}>
                                                </td>
                                                <td>
                                                    <input type="number" name="quantity" class="form-control form-control-sm" value="${detail.quantity}" min="0">
                                                    </form>
                                                </td>
                                                <td class="table-action">
                                                    <button type="submit" form="updateForm-${status.index}" class="btn btn-sm me-1 save" disabled title="Save">
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-save align-middle"><path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"></path><polyline points="17 21 17 13 7 13 7 21"></polyline><polyline points="7 3 7 8 15 8"></polyline></svg>
                                                    </button>
                                                    <form action="WarrantyCard/Detail" method="post" class="d-inline" id="deleteForm-${status.index}">
                                                        <input type="hidden" name="action" value="delete">
                                                        <input type="hidden" name="warrantyCardDetailID" value="${detail.warrantyCardDetailID}">
                                                        <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                                        <button type="submit" class="btn btn-sm" onclick="return confirm('Are you sure you want to delete this component?');" title="Delete">
                                                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash align-middle"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path></svg>
                                                        </button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <div class="col-md-4">
                                For see component request

                            </div>
                        </div>

                    </c:if>

                    <h2 class="mt-4">Warranty Code: ${card.warrantyCardCode}</h2>
                    <div class="row ms-0">
                        <div class="col-md-8 row ">
                            <div class="col-md-6 row g-2">
                                <div class="col-md-12"><h3>CUSTOMER INFORMATION</h3></div>
                                <div class="col-md-12"><div>Name: ${customer.name}</div></div>
                                <div class="col-md-12"><div>Phone: ${customer.phone}</div></div>
                                <div class="col-md-12"><div>Email: ${customer.email}</div></div>
                                <div class="col-md-12"><h3>HANDLER INFORMATION</h3></div>
                                <div class="col-md-12"><div>Name: ${handler.name}</div></div>
                                <div class="col-md-12"><div>Phone: ${handler.phone}</div></div>
                                <div class="col-md-12"><div>Employee ID: ${handler.usernameS}</div></div>
                                <div class="col-md-12"><h3>PRODUCT</h3></div>
                                <div class="col-md-12"><div>Product Code: ${card.productDetailCode}</div></div>
                                <div class="col-md-12"><div>Product Name: ${card.productName}</div></div>
                                <div class="col-md-12"><div>Purchased Date: ${pd.getFormatPurchaseDate()}</div></div>
                                <div class="col-md-12">
                                    <div>Warranty Period (Months): ${pd.warrantyPeriod}</div>
                                </div>
                                <div class="col-md-12">
                                    <div id="warrantyStatus"></div>
                                </div>
                                <div class="col-md-12">
                                    <div>Issue Description:</div>
                                </div>
                            </div>
                            <div class="col-md-6 ">
                                <div class="row g-2 mt-4">
                                    <table class="table table-bordered">
                                        <tbody>
                                            <tr>
                                                <th>Canceled Date</th>
                                                <td>${card.getFormatCanceldDate()}</td>
                                            </tr>
                                            <tr>
                                                <th>Created Date</th>
                                                <td>${card.getFormatCreatedDate()}</td>
                                            </tr>
                                            <tr>
                                                <th>Estimated Return Date</th>
                                                <td>${card.getFormatReturnDate()}</td>
                                            </tr>
                                            <tr>
                                                <th>Repaired Date</th>
                                                <td>${card.getFormatDonedDate()}</td>
                                            </tr>
                                            <tr>
                                                <th>Completed Date</th>
                                                <td>${card.getFormatCompletedDate()}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <input type="hidden" class="form-control" value="${card.warrantyCardID}" readonly>  
                            <div class="col-md-12">
                                <textarea class="form-control" readonly>${card.issueDescription}</textarea>
                            </div>

                        </div>
                        <%--For showing images --%>
                        <c:set var="count" value="${fn:length(card.images)+fn:length(card.videos)}"/>
                        <div class="col-md-4">
                            <div class="media-gallery">
                                <c:forEach var="image" items="${card.images}">
                                    <div class="media-item">
                                        <img src="${image}" style="max-width: ${count<3?25.5/count:8.5}rem" alt="Warranty Image" onclick="showModal(this.src, 'image')">
                                    </div>
                                </c:forEach>
                                <c:forEach var="video" items="${card.videos}">
                                    <div class="media-item">
                                        <video src="${video}" style="max-width: ${count<3?25.5/count:8.5}rem" controls onclick="showModal(this.src, 'video')"></video>
                                    </div>
                                </c:forEach>
                            </div>

                        </div>

                        <c:if test="${latestProcess!=null && (latestProcess.action=='create' || latestProcess.action == 'refuse')}">
                            <div class="col-md-12 d-flex justify-content-center mt-2">
                                <form action="WarrantyCard/Detail" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="process">
                                    <input type="hidden" name="ID" value="${card.warrantyCardID}">
                                    <input type="hidden" name="processAction" value="receive">
                                    <button type="submit" class="btn-lg btn-primary me-2" ${latestProcess != null && (latestProcess.action == 'create'||latestProcess.action == 'cancel' || latestProcess.action == 'refuse' ) ? '' : 'disabled'}>RECEIVE</button>
                                </form>                    
                            </div>
                        </c:if>
                    </div>
                    <!-- Modal for Zoom -->
                    <div id="mediaModal" class="modal">
                        <span class="modal-close" onclick="hideModal()">&times;</span>
                        <div id="modalContent" class="modal-content"></div>
                    </div>
                </main>
                <jsp:include page="../../includes/footer.jsp" />
            </div>
        </div>

        <script src="js/app.js"></script>
        <script src="js/format-input.js"></script>
        <script>
                            var zoomLevel = 1; // Mức zoom ban đầu
                            var minZoom = 0.5; // Giới hạn thu nhỏ tối thiểu
                            var maxZoom = 3; // Giới hạn phóng to tối đa
                            var isDragging = false;
                            var startX, startY, translateX = 0, translateY = 0;

                            function showModal(src, type) {
                                var modal = document.getElementById('mediaModal');
                                var modalContent = document.getElementById('modalContent');
                                modalContent.innerHTML = '';
                                zoomLevel = 1; // Reset zoom
                                translateX = 0; // Reset vị trí
                                translateY = 0;

                                var mediaElement;
                                if (type === 'image') {
                                    mediaElement = document.createElement('img');
                                    mediaElement.src = src;
                                    mediaElement.draggable = false;
                                } else if (type === 'video') {
                                    mediaElement = document.createElement('video');
                                    mediaElement.src = src;
                                    mediaElement.controls = true;
                                    mediaElement.draggable = false;
                                    mediaElement.play();
                                }
                                modalContent.appendChild(mediaElement);
                                modal.style.display = 'flex';

                                // Zoom bằng lăn chuột
                                modalContent.onwheel = function (e) {
                                    e.preventDefault();
                                    var delta = e.deltaY > 0 ? -0.1 : 0.1;
                                    zoomLevel += delta;
                                    zoomLevel = Math.min(Math.max(zoomLevel, minZoom), maxZoom);
                                    applyTransform(mediaElement);
                                };

                                // Kéo thả bằng chuột
                                mediaElement.onmousedown = function (e) {
                                    e.preventDefault(); // Ngăn hành vi kéo mặc định của trình duyệt
                                    if (zoomLevel > 0) { // Chỉ kéo khi đã phóng to
                                        isDragging = true;
                                        // Tính toán vị trí ban đầu dựa trên tọa độ hiện tại của chuột và kích thước phần tử
                                        startX = e.clientX - translateX;
                                        startY = e.clientY - translateY;
                                        mediaElement.style.cursor = 'grabbing';
                                    }
                                };

                                document.onmousemove = function (e) {
                                    if (isDragging) {
                                        translateX = e.clientX - startX;
                                        translateY = e.clientY - startY;
                                        applyTransform(mediaElement);
                                    }
                                };

                                document.onmouseup = function () {
                                    isDragging = false;
                                    mediaElement.style.cursor = 'grab';
                                };

                                // Ngăn sự kiện kéo mặc định của trình duyệt
                                mediaElement.ondragstart = function (e) {
                                    e.preventDefault();
                                    return false;
                                };
                            }

                            function applyTransform(element) {
                                // Chỉ áp dụng transform khi zoom hoặc kéo, không ghi đè translate(-50%, -50%) ban đầu
                                if (zoomLevel !== 1 || translateX !== 0 || translateY !== 0) {
                                    element.style.transform = 'translate(-50%, -50%) translate(' + translateX + 'px, ' + translateY + 'px) scale(' + zoomLevel + ')';
                                } else {
                                    element.style.transform = 'translate(-50%, -50%)'; // Giữ căn giữa mặc định
                                }
                            }

                            function hideModal() {
                                var modal = document.getElementById('mediaModal');
                                var modalContent = document.getElementById('modalContent');
                                var video = modalContent.getElementsByTagName('video')[0];
                                if (video)
                                    video.pause();
                                modal.style.display = 'none';
                                zoomLevel = 1;
                                translateX = 0;
                                translateY = 0;
                                var mediaElement = modalContent.querySelector('img') || modalContent.querySelector('video');
                                if (mediaElement) {
                                    mediaElement.style.transform = 'translate(-50%, -50%)'; // Reset về căn giữa CSS
                                }
                            }

                            document.getElementById('mediaModal').onclick = function (e) {
                                if (e.target === this)
                                    hideModal();
                            };



                            // Enable Save button on input change in table
                            document.querySelectorAll('.form-select, .form-control').forEach(input => {
                                input.addEventListener('input', function () {
                                    this.closest('tr').querySelector('.save').disabled = false;
                                });
                            });
                            document.querySelectorAll('.save').forEach(button => {
                                button.disabled = true;
                            });

                            // Table: Disable price input for warranty states
                            document.querySelectorAll('.status-select').forEach(select => {
                                const priceInput = select.closest('tr').querySelector('.price-input');
                                select.addEventListener('change', function () {
                                    priceInput.readOnly = (this.value === 'warranty_repaired' || this.value === 'warranty_replaced');
                                    if (this.value === 'warranty_repaired' || this.value === 'warranty_replaced')
                                        priceInput.value = 0;
                                });
                            });

                            window.onload = function () {
                                const purchasedDateStr = "${pd.getFormatPurchaseDate()}"; // Giả sử định dạng "dd/MM/yyyy"
                                const warrantyPeriod = ${pd.warrantyPeriod}; // Số tháng bảo hành
                                const statusDiv = document.getElementById('warrantyStatus');

                                // Kiểm tra purchasedDateStr có hợp lệ không
                                console.log("purchasedDateStr:", purchasedDateStr); // Debug giá trị

                                if (!purchasedDateStr || purchasedDateStr.trim() === '' || !purchasedDateStr.includes('-')) {
                                    statusDiv.innerText = "Status: Cannot determine warranty status (invalid purchase date)";
                                    statusDiv.style.color = "orange";
                                    return;
                                }

                                // Tách chuỗi thủ công thay vì dùng destructuring
                                const dateParts = purchasedDateStr.split('-');
                                if (dateParts.length !== 3) {
                                    statusDiv.innerText = "Status: Cannot determine warranty status (invalid date format)";
                                    statusDiv.style.color = "orange";
                                    return;
                                }

                                // Gán giá trị truyền thống
                                const day = parseInt(dateParts[0], 10);
                                const month = parseInt(dateParts[1], 10);
                                const year = parseInt(dateParts[2], 10);

                                // Kiểm tra các giá trị có hợp lệ không
                                if (isNaN(day) || isNaN(month) || isNaN(year)) {
                                    statusDiv.innerText = "Status: Cannot determine warranty status (invalid date components)";
                                    statusDiv.style.color = "orange";
                                    return;
                                }

                                // Tạo Date object (month - 1 vì JS đếm từ 0)
                                const purchasedDate = new Date(year, month - 1, day);

                                // Kiểm tra Date có hợp lệ không
                                if (isNaN(purchasedDate.getTime())) {
                                    statusDiv.innerText = "Status: Cannot determine warranty status (invalid date)";
                                    statusDiv.style.color = "orange";
                                    return;
                                }

                                // Tính ngày hết bảo hành
                                const warrantyEndDate = new Date(purchasedDate);
                                warrantyEndDate.setMonth(purchasedDate.getMonth() + warrantyPeriod);

                                // So sánh với ngày hiện tại
                                const today = new Date();
                                today.setHours(0, 0, 0, 0);

                                if (today <= warrantyEndDate) {
                                    statusDiv.innerText = "Status: Still under warranty";
                                    statusDiv.style.color = "green";
                                } else {
                                    statusDiv.innerText = "Status: Out of warranty";
                                    statusDiv.style.color = "red";
                                }
                            };
        </script>
    </body>
</html>