<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            .media-preview {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
            }
            .media-item {
                position: relative;
                display: inline-block;
            }
            .media-item img, .media-item video {
                max-width: 100px;
                margin: 5px;
            }
            .remove-btn {
                position: absolute;
                top: 0;
                right: 0;
                background: red;
                color: white;
                border: none;
                border-radius: 50%;
                width: 20px;
                height: 20px;
                cursor: pointer;
            }
            .is-invalid {
                border-color: red;
            }
            .invalid-feedback {
                color: red;
                font-size: 0.9rem;
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
                        <input type="hidden" name="target" value="${empty createWarrantyCardFrom ? '/MaintenanceSystem/WarrantyCard' : createWarrantyCardFrom}">
                        <button type="submit" class="btn btn-primary d-flex align-items-center justify-content-center" style="transform: translate(-30%, -60%); height: 2.5rem; width: 5.2rem">
                            <i class="fas fa-arrow-left fa-4"></i> <span class="ms-2">Back</span>
                        </button>
                    </form>

                    <c:if test="${createWarrantyCardFom eq '/MaintenanceSystem/WarrantyCard' or empty createWarrantyCardFom}">
                        <h1 class="text-center text-primary" style="font-size: 4rem">Create Warranty Card</h1>
                        <div class="d-flex flex-column col justify-content-center align-items-center vh-50">
                            <form action="WarrantyCard/Add" method="POST" class="w-50 row" enctype="multipart/form-data">
                                <div class="row d-flex align-items-center">
                                    <div class="col bg-primary bg-opacity-75 p-4 rounded-pill shadow">
                                        <input type="text" class="form-control w-100 border-0 text-white" style="font-size: 2rem; background-color: rgba(59,125,221,0)" name="productCode" id="ProductCode" value="${ProductCode}" required>
                                    </div>
                                    <div class="col-auto">
                                        <button type="submit" class="btn col-auto bg-primary bg-opacity-75 p-4 rounded-circle shadow h-100" style="font-size: 1.5rem;">
                                            <i class="align-middle fas fa-fw fa-search text-white text-opacity-75"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                            <c:if test="${not empty NotFoundProduct}">
                                <div class="col-md-7 alert alert-dark alert-dismissible mt-4" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message text-center"><strong>${NotFoundProduct}</strong></div>
                                </div>
                            </c:if>
                            <c:if test="${not empty pd}">
                                <div class="col-md-7 alert alert-success alert-dismissible mt-4" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message text-center"><strong>Found product</strong></div>
                                </div>
                            </c:if>
                        </div>
                    </c:if>

                    <c:if test="${not empty pd}">
                        <hr>
                        <h1 class="text-center text-primary" style="font-size: 4rem">Warranty Card</h1>
                        <c:if test="${not empty createFail}">
                            <div class="d-flex justify-content-center">
                                <div class="col-md-7 alert alert-danger alert-dismissible mt-4 text-center" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message"><strong>${createFail}</strong></div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${not empty pictureAlert}">
                            <div class="d-flex justify-content-center">
                                <div class="col-md-7 alert alert-danger alert-dismissible mt-4 text-center" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message"><strong>${pictureAlert}</strong></div>
                                </div>
                            </div>
                        </c:if>

                        <form action="WarrantyCard/Add" method="POST" class="row g-3" enctype="multipart/form-data" onsubmit="return validateForm()">
                            <div class="col-md-6">
                                <div class="row g-3 text-lg ms-7">
                                <input type="hidden" name="productDetailID" value="${pd.productDetailID}">
                                <div class="col-md-12"><h3>CUSTOMER INFORMATION</h3></div>
                                <div class="col-md-12"><div>Name: ${pd.usernameC}</div></div>
                                <div class="col-md-12"><div>Phone: ${pd.phone}</div></div>
                                <div class="col-md-12"><div>Email: ${pd.email}</div></div>
                                <div class="col-md-12"><h3>HANDLER INFORMATION</h3></div>
                                <div class="col-md-12"><div>Name: ${staff.name}</div></div>
                                <div class="col-md-12"><div>Phone: ${staff.phone}</div></div>
                                <div class="col-md-12"><div>Employee ID: ${staff.usernameS}</div></div>
                                <div class="col-md-12"><h3>PRODUCT</h3></div>
                                <div class="col-md-12"><div>Product Code: ${pd.productCode}</div></div>
                                <div class="col-md-12"><div>Product Name: ${pd.productName}</div></div>
                                <div class="col-md-12"><div>Purchased Date: ${pd.getFormatPurchaseDate()}</div></div>
                                <div class="col-md-12">
                                    <div>Warranty Period (Months): ${pd.warrantyPeriod}</div>
                                </div>
                                <div class="col-md-12">
                                    <div id="warrantyStatus"></div>
                                </div>
                                </div>
                            </div>
                            <input type="hidden" name="productCode" value="${pd.productCode}">
                            <div class="col-md-6">
                                <div class="row g-3">
                                       <div class="col-md-12">
                                    <label for="returnDate" class="form-label">Return Date</label>
                                    <input type="date" class="form-control w-25" name="returnDate" id="returnDate">
                                    <div class="invalid-feedback" id="returnDateFeedback"></div>
                                </div>
                                <div class="col-md-12">
                                    <textarea class="form-control" placeholder="Describe issue" name="issue" rows="2" style="height: 10rem;"></textarea>
                                </div>
                                <div class="col-md-12">
                                    <label for="mediaFiles" class="form-label">Upload Images/Videos</label>
                                    <input type="file" class="form-control" name="mediaFiles" id="mediaFiles" accept="image/*,video/*" multiple onchange="previewMedia(event)">
                                    <div id="previewContainer" class="media-preview mt-3"></div>
                                </div>
                                </div>
                            </div>

                            <div class="col-6 text-center d-flex justify-content-end">
                                <button type="submit" class="btn btn-primary rounded-3 p-3 w-25" name="action" value="create" style="font-size: 1.3rem">Create</button>
                            </div>
                            <div class="col-6 text-center d-flex justify-content-start">
                                <button type="submit" class="btn btn-primary rounded-3 p-3 w-25" name="action" value="receive" style="font-size: 1.3rem">Receive</button>
                            </div>
                        </form>
                    </c:if>
                </main>
                <jsp:include page="../../includes/footer.jsp" />
            </div>
        </div>

        <script src="js/app.js"></script>
        <script>
                                    let selectedFiles = []; // Lưu danh sách file để preview và xóa

                                    // Preview và xóa file
                                    function previewMedia(event) {
                                        const files = Array.from(event.target.files);
                                        selectedFiles = files;
                                        const previewContainer = document.getElementById('previewContainer');
                                        previewContainer.innerHTML = '';

                                        selectedFiles.forEach((file, index) => {
                                            const reader = new FileReader();
                                            reader.onload = function (e) {
                                                const div = document.createElement('div');
                                                div.className = 'media-item';

                                                if (file.type.startsWith('image/')) {
                                                    const img = document.createElement('img');
                                                    img.src = e.target.result;
                                                    div.appendChild(img);
                                                } else if (file.type.startsWith('video/')) {
                                                    const video = document.createElement('video');
                                                    video.src = e.target.result;
                                                    video.controls = true;
                                                    div.appendChild(video);
                                                }

                                                const removeBtn = document.createElement('button');
                                                removeBtn.className = 'remove-btn';
                                                removeBtn.innerText = 'X';
                                                removeBtn.onclick = () => removeMedia(index);
                                                div.appendChild(removeBtn);

                                                previewContainer.appendChild(div);
                                            };
                                            reader.readAsDataURL(file);
                                        });
                                    }

                                    function removeMedia(index) {
                                        selectedFiles.splice(index, 1);
                                        const input = document.getElementById('mediaFiles');
                                        const dataTransfer = new DataTransfer();
                                        selectedFiles.forEach(file => dataTransfer.items.add(file));
                                        input.files = dataTransfer.files;
                                        previewMedia({target: input});
                                    }

                                    // Validation Return Date và tính toán trạng thái bảo hành
                                    function validateForm() {
                                        const returnDateInput = document.getElementById('returnDate');
                                        const returnDate = new Date(returnDateInput.value);
                                        const today = new Date();
                                        today.setHours(0, 0, 0, 0); // Chỉ lấy ngày, bỏ giờ

                                        if (returnDate < today) {
                                            returnDateInput.classList.add('is-invalid');
                                            document.getElementById('returnDateFeedback').innerText = 'Return Date must be today or later.';
                                            return false;
                                        } else {
                                            returnDateInput.classList.remove('is-invalid');
                                            document.getElementById('returnDateFeedback').innerText = '';
                                            return true;
                                        }
                                    }

                                    // Tính toán trạng thái bảo hành
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