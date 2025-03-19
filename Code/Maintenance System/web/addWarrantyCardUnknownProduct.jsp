<%-- 
    Document   : viewProduct
    Created on : Feb 21, 2025, 11:49:16 PM
    Author     : sonNH
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product List</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
            integrity="sha512-ac6U9Cqcbn6K6q/73mITJGF0fjLWWZwUczEzKNnZHRrRLz63HJfiAgPRQd3kNn6BW9rBS71hbPkdoL7q2/EvWA=="
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
            />
        <link rel="stylesheet" href="style.css">
        <style>
            /* CSS chuyên dụng cho phần nội dung chính (main.content) */

            /* Container của nội dung chính */
            main.content {
                background-color: #fff;
                padding: 30px 40px;
                margin: 10px;
                border-radius: 8px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            }

            /* Tiêu đề */
            main.content h2 {
                font-size: 1.8rem;
                color: #2c3e50;
                margin-bottom: 20px;
                font-weight: 600;
            }

            main.content h3 {
                font-size: 1.4rem;
                color: #2c3e50;
                margin-bottom: 20px;
                font-weight: 500;
            }

            /* Bảng thông tin */
            main.content table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 30px;
            }

            main.content table th,
            main.content table td {
                padding: 12px 15px;
                border: 1px solid #ddd;
                text-align: left;
            }

            main.content table th {
                background-color: #C4E8FE;
                font-weight: 600;
                color: black;
            }

            /* Form và các thành phần trong form */
            main.content form {
                margin-top: 20px;
            }

            main.content form label {
                display: block;
                margin-top: 15px;
                font-weight: 600;
                color: #555;
            }

            main.content form input[type="text"],
            main.content form input[type="datetime-local"],
            main.content form textarea,
            main.content form select {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 1rem;
                transition: border-color 0.3s ease;
            }

            main.content form input[type="text"]:focus,
            main.content form input[type="datetime-local"]:focus,
            main.content form textarea:focus,
            main.content form select:focus {
                border-color: #3498db;
                outline: none;
            }

            main.content form textarea {
                resize: vertical;
                min-height: 80px;
            }

            /* File input và xem trước hình ảnh */
            main.content form input[type="file"] {
                margin-top: 10px;
            }

            main.content img#imagePreview {
                margin-top: 10px;
                max-width: 300px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }

            /* Nút submit và link chuyển trang */
            main.content form input[type="submit"],
            main.content form a.btn-update {
                display: inline-block;
                background-color: #3498db;
                color: #fff;
                border: none;
                padding: 12px 20px;
                border-radius: 4px;
                font-size: 1rem;
                text-decoration: none;
                cursor: pointer;
                margin-top: 20px;
                transition: background-color 0.3s ease;
            }

            main.content form input[type="submit"]:hover,
            main.content form a.btn-update:hover {
                background-color: #2980b9;
            }

            main.content form a.btn-update {
                background-color: #7f8c8d;
                margin-left: 10px;
            }

            /* Đảm bảo cả hai bảng có chung class để áp dụng cùng một quy tắc */
            table.info-table {
                width: 100%;
                border-collapse: collapse;
                table-layout: fixed; /* Cố định cách chia cột */
                margin-bottom: 30px;
            }

            table.info-table th,
            table.info-table td {
                border: 1px solid #ddd;
                padding: 12px 15px;
                text-align: left;
            }

            /* Cột th màu xám và có độ rộng nhất định */
            table.info-table th {
                width: 200px; /* bạn có thể thay đổi tùy ý */
                /* Nếu muốn chữ không xuống dòng: white-space: nowrap; */
            }
            /* Responsive cho màn hình nhỏ */
            @media (max-width: 768px) {
                main.content {
                    padding: 20px;
                    margin: 10px;
                }
                main.content table th,
                main.content table td {
                    padding: 10px;
                }
            }
            .toolbox {
                background-color: #f0f0f0; /* màu nền nhẹ */
                border: 1px solid #ddd;    /* viền nhẹ */
                border-radius: 5px;        /* bo góc */
                padding: 15px;             /* khoảng cách bên trong */
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* tạo bóng nhẹ */
                max-width: 2000px;          /* giới hạn chiều rộng nếu cần */
                margin: 20px auto;         /* căn giữa container trên trang */
            }

            .toolbox label {
                display: block;
                font-weight: bold;
                margin-bottom: 8px;
            }

            .toolbox textarea {
                width: 100%;
                min-height: 100px;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                resize: vertical; /* cho phép thay đổi kích thước theo chiều dọc */
            }

            /* Tổng thể vùng editor */
            .editor-container {
                width:100%;              /* Độ rộng mong muốn */
                border: 1px solid #ccc;    /* Viền ngoài */
                border-radius: 5px;        /* Bo góc */
                margin: auto;         /* Căn giữa trang */
                font-family: sans-serif;   /* Font chữ cơ bản */
                background-color: #f9f9f9; /* Nền nhẹ */
            }

            /* Thanh công cụ (toolbar) */
            .editor-toolbar {
                display: flex;
                flex-wrap: wrap;           /* Nếu thiếu chỗ, icon sẽ xuống dòng */
                border-bottom: 1px solid #ddd;
                background-color: #fafafa;
                padding: 5px 10px;
                border-top-left-radius: 5px;
                border-top-right-radius: 5px;
            }

            /* Nút công cụ (tool-btn) */
            .tool-btn {
                background: none;
                border: none;
                cursor: pointer;
                margin-right: 8px;
                font-size: 16px;
                color: #555;
                padding: 6px;
                transition: background-color 0.2s ease, color 0.2s ease;
            }

            .tool-btn:hover {
                background-color: #e0e0e0;
                color: #000;
            }

            .tool-btn:focus {
                outline: none; /* Tắt khung focus mặc định, tuỳ chọn */
            }

            /* Vùng soạn thảo */
            .editor-content {
                min-height: 200px;         /* Chiều cao tối thiểu */
                padding: 10px;
                font-size: 14px;
                line-height: 1.4;
                background-color: #fff;
                border-bottom-left-radius: 5px;
                border-bottom-right-radius: 5px;
            }

            /* Hiển thị placeholder khi contenteditable rỗng */
            .editor-content:empty:before {
                content: attr(data-placeholder);
                color: #aaa;
            }
            .editor-content:focus:before {
                content: "";
            }

        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <h2>Create Warranty Card</h2>
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger" style="height: 30px; align-items: center; padding: 2px;justify-content: center">
                            ${errorMessage}
                        </div>
                    </c:if>

                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success" style="height: 30px; align-items: center; padding: 2px;justify-content: center">
                            ${successMessage}
                        </div>
                    </c:if>
                    <h3>Customer Information</h3>
                    <table class="info-table"> 
                        <tr><th>Name</th><td>${customer.name}</td></tr>
                        <tr><th>Gender</th><td>${customer.gender}</td></tr>
                        <tr><th>Email</th><td>${customer.email}</td></tr>
                        <tr><th>Phone</th><td>${customer.phone}</td></tr>
                        <tr><th>Address</th><td>${customer.address}</td></tr>
                    </table>
                    <h3>Product Information</h3>
                    <table class="info-table">
                        <tr><th>Product Name</th><td>${unknownProduct.productName}</td></tr>
                        <tr><th>Product Code</th><td>${unknownProduct.productCode}</td></tr>
                        <tr><th>Description</th><td>${unknownProduct.description}</td></tr>
                        <tr><th>Received Date</th><td>${unknownProduct.receivedDate}</td></tr>
                    </table>

                    <h3>Enter Warranty Card Details</h3>
                    <form action="addWUP" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="customerId" value="${customer.customerID}" />
                        <input type="hidden" name="productId" value="${unknownProduct.unknownProductId}" />
                        <input type="hidden" name="warrantyProductId" value="${warrantyProductId}" />
                        <label for="issueDescription">Issue Description:</label>

                        <div class="editor-container">
                            <div class="editor-toolbar">
                                <button type="button" class="tool-btn" title="Bold"><i class="fas fa-bold"></i></button>
                                <button type="button" class="tool-btn" title="Italic"><i class="fas fa-italic"></i></button>
                                <button type="button" class="tool-btn" title="Strikethrough"><i class="fas fa-strikethrough"></i></button>
                                <button type="button" class="tool-btn" title="Underline"><i class="fas fa-underline"></i></button>
                                <button type="button" class="tool-btn" title="Bulleted List"><i class="fas fa-list-ul"></i></button>
                                <button type="button" class="tool-btn" title="Numbered List"><i class="fas fa-list-ol"></i></button>
                                <button type="button" class="tool-btn" title="Align Left"><i class="fas fa-align-left"></i></button>
                                <button type="button" class="tool-btn" title="Align Center"><i class="fas fa-align-center"></i></button>
                                <button type="button" class="tool-btn" title="Align Right"><i class="fas fa-align-right"></i></button>
                                <button type="button" class="tool-btn" title="Link"><i class="fas fa-link"></i></button>
                                <button type="button" class="tool-btn" title="Quote"><i class="fas fa-quote-right"></i></button>
                                <button type="button" class="tool-btn" title="Source"><i class="fas fa-code"></i></button>
                            </div>

                            <textarea id="issueDescription" name="issueDescription" required>${param.issueDescription}</textarea>
                        </div>

                        <label>Expected Return Date:</label>
                        <select name="returnDateOption" onchange="toggleDateInput('returnDate')" required>
                            <option value="" ${empty param.returnDate ? 'selected="selected"' : ''}>Undetermined date</option>
                            <option value="select" ${not empty param.returnDate ? 'selected="selected"' : ''}>Select date</option>
                        </select>
                        <input type="datetime-local" name="returnDate" id="returnDate" value="${param.returnDate}" style="display: none;"/>
                        <br/>

                        <label>Done Date:</label>
                        <select name="doneDateOption" onchange="toggleDateInput('doneDate')">
                            <option value="" ${empty param.doneDate ? 'selected="selected"' : ''}>Undetermined date</option>
                            <option value="select" ${not empty param.doneDate ? 'selected="selected"' : ''}>Select date</option>
                        </select>
                        <input type="datetime-local" name="doneDate" id="doneDate" value="${param.doneDate}" style="display: none;" />

                        <label>Complete Date:</label>
                        <select name="completeDateOption" onchange="toggleDateInput('completeDate')">
                            <option value="" ${empty param.completeDate ? 'selected="selected"' : ''}>Undetermined date</option>
                            <option value="select" ${not empty param.completeDate ? 'selected="selected"' : ''}>Select date</option>
                        </select>
                        <input type="datetime-local" name="completeDate" id="completeDate" value="${param.completeDate}" style="display: none;" />

                        <label>Cancel Date:</label>
                        <select name="cancelDateOption" onchange="toggleDateInput('cancelDate')">
                            <option value="" ${empty param.cancelDate ? 'selected="selected"' : ''}>Undetermined date</option>
                            <option value="select" ${not empty param.cancelDate ? 'selected="selected"' : ''}>Select date</option>
                        </select>
                        <input type="datetime-local" name="cancelDate" id="cancelDate" value="${param.cancelDate}" style="display: none;" />

                        <label>Warranty Status:</label>
                        <select name="warrantyStatus" required>
                            <option value="waiting" ${param.warrantyStatus eq 'waiting' ? 'selected="selected"' : ''}>Waiting</option>
                            <option value="fixing" ${param.warrantyStatus eq 'fixing' ? 'selected="selected"' : ''}>Fixing</option>
                            <option value="refix" ${param.warrantyStatus eq 'refix' ? 'selected="selected"' : ''}>Refix</option>
                            <option value="done" ${param.warrantyStatus eq 'done' ? 'selected="selected"' : ''}>Done</option>
                            <option value="completed" ${param.warrantyStatus eq 'completed' ? 'selected="selected"' : ''}>Completed</option>
                            <option value="cancel" ${param.warrantyStatus eq 'cancel' ? 'selected="selected"' : ''}>Cancel</option>
                        </select>
                        <br/>

                        <label>Upload Warranty Media (Images/Videos):</label>
                        <input type="file" name="warrantyMedia" accept="image/*,video/*" multiple required onchange="previewMedia(event)" />
                        <br/>
                        <div id="mediaPreview"></div>

                        <input type="submit" value="Create Warranty Card"/>

                        <a href="listUnknown" class="btn-update">
                            <i></i>Back
                        </a>

                    </form>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
        <script>
                            document.addEventListener("DOMContentLoaded", function () {
                                var warrantyCodeInput = document.getElementById("warrantyCardCode");

                                // Tạo phần tử hiển thị lỗi ngay bên dưới trường nhập liệu
                                var errorMessageElement = document.createElement("div");
                                errorMessageElement.style.color = "red";
                                errorMessageElement.style.fontWeight = "bold";
                                warrantyCodeInput.parentNode.insertBefore(errorMessageElement, warrantyCodeInput.nextSibling);

                                // Lắng nghe sự kiện keypress để ngăn chặn nhập ký tự không hợp lệ
                                warrantyCodeInput.addEventListener("keypress", function (e) {
                                    var key = e.key;
                                    // Cho phép các phím điều khiển (Backspace, Delete, Arrow keys, ...)
                                    if (key.length > 1) {
                                        return;
                                    }
                                    // Regex cho phép chỉ nhập chữ cái và số
                                    var regex = /^[A-Za-z0-9]$/;
                                    if (!regex.test(key)) {
                                        e.preventDefault();  // Ngăn không cho ký tự xuất hiện
                                        errorMessageElement.textContent = "Chỉ cho phép nhập chữ và số!";
                                    } else {
                                        errorMessageElement.textContent = "";
                                    }
                                });

                                // Xử lý trường hợp dán nội dung vào ô input
                                warrantyCodeInput.addEventListener("paste", function (e) {
                                    var pastedData = e.clipboardData.getData('text');
                                    var regex = /^[A-Za-z0-9]+$/;
                                    if (!regex.test(pastedData)) {
                                        e.preventDefault();  // Ngăn không cho dán nội dung không hợp lệ
                                        errorMessageElement.textContent = "Chỉ cho phép nhập chữ và số!";
                                    } else {
                                        errorMessageElement.textContent = "";
                                    }
                                });
                            });
                            function showDateIfExist(dateField) {
                                var input = document.getElementsByName(dateField)[0];
                                var select = document.getElementsByName(dateField + "Option")[0];
                                if (input.value) {
                                    select.value = "select";
                                    input.style.display = "inline-block";
                                    input.required = true;
                                }
                            }
                            showDateIfExist("returnDate");
                            showDateIfExist("doneDate");
                            showDateIfExist("completeDate");
                            showDateIfExist("cancelDate");

                            function toggleDateInput(field) {
                                var select = document.getElementsByName(field + "Option")[0];
                                var input = document.getElementsByName(field)[0];

                                if (select.value === "select") {
                                    input.style.display = "inline-block";
                                    input.required = true;
                                } else {
                                    input.style.display = "none";
                                    input.required = false;
                                    input.value = ""; // Reset giá trị khi chọn "Undetermined date"
                                }
                            }
        </script>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                var descriptionInput = document.getElementById("issueDescription");
                var descErrorElement = document.createElement("div");
                descErrorElement.style.color = "red";
                descErrorElement.style.fontWeight = "bold";
                descriptionInput.parentNode.insertBefore(descErrorElement, descriptionInput.nextSibling);

                function validateDescription() {
                    var value = descriptionInput.value;
                    var trimmedValue = value.trim();
                    var normalized = trimmedValue.replace(/\s+/g, " ");
                    // Nếu sau khi trim mà chuỗi rỗng, nghĩa là toàn khoảng trắng
                    if (trimmedValue === "") {
                        descErrorElement.textContent = "Lỗi: Description không được nhập toàn khoảng trắng.";
                        return false;
                    }
                    // Nếu chuỗi nhập khác với phiên bản chuẩn (trim và collapse khoảng trắng)
                    if (value !== normalized) {
                        descErrorElement.textContent = "Lỗi: Description phải loại bỏ khoảng trắng đầu/cuối và chỉ cách nhau 1 dấu cách giữa các từ.";
                        return false;
                    }
                    descErrorElement.textContent = "";
                    return true;
                }
                descriptionInput.addEventListener("input", validateDescription);

                // --- Validate Date Fields (không cho phép nhập ngày trong quá khứ) ---
                var dateFields = ["returnDate", "doneDate", "completeDate", "cancelDate"];
                dateFields.forEach(function (fieldId) {
                    var dateInput = document.getElementById(fieldId);
                    if (dateInput) {
                        var dateErrorElement = document.createElement("div");
                        dateErrorElement.style.color = "red";
                        dateErrorElement.style.fontWeight = "bold";
                        dateInput.parentNode.insertBefore(dateErrorElement, dateInput.nextSibling);

                        function validateDate() {
                            if (dateInput.value !== "") {
                                var inputDate = new Date(dateInput.value);
                                var now = new Date();
                                // So sánh thời gian hiện tại, nếu ngày nhập vào nhỏ hơn hiện tại thì là ngày trong quá khứ
                                if (inputDate < now) {
                                    dateErrorElement.textContent = "Lỗi: Ngày không được nhập trong quá khứ.";
                                    return false;
                                } else {
                                    dateErrorElement.textContent = "";
                                    return true;
                                }
                            } else {
                                dateErrorElement.textContent = "";
                                return true;
                            }
                        }
                        dateInput.addEventListener("change", validateDate);
                        dateInput.addEventListener("input", validateDate);
                    }
                });

                // --- Kiểm tra tổng hợp khi submit form ---
                var form = document.querySelector("form");
                form.addEventListener("submit", function (e) {
                    var validDesc = validateDescription();
                    var validDates = true;
                    dateFields.forEach(function (fieldId) {
                        var dateInput = document.getElementById(fieldId);
                        if (dateInput && dateInput.value !== "") {
                            var inputDate = new Date(dateInput.value);
                            if (inputDate < new Date()) {
                                validDates = false;
                            }
                        }
                    });
                    if (!validDesc || !validDates) {
                        e.preventDefault();
                        alert("Vui lòng sửa các lỗi đã báo trước khi submit form.");
                    }
                });
            });
        </script>

        <script>
            function toggleDateInput(id) {
                var select = document.getElementsByName(id + "Option")[0];
                var input = document.getElementById(id);
                if (select.value === "select") {
                    input.style.display = "inline";
                } else {
                    input.style.display = "none";
                    input.value = "";
                }
            }
            function previewMedia(event) {
                var preview = document.getElementById('mediaPreview');
                preview.innerHTML = "";
                var files = event.target.files;
                var maxSize = 5 * 1024 * 1024; // 5MB tính theo bytes

                for (var i = 0; i < files.length; i++) {
                    var file = files[i];
                    // Kiểm tra kích thước file
                    if (file.size > maxSize) {
                        alert("File " + file.name + " vượt quá giới hạn 5MB.");
                        continue; // bỏ qua file này, không hiển thị preview
                    }

                    var fileType = file.type;
                    var element;
                    if (fileType.startsWith("image/")) {
                        element = document.createElement("img");
                        element.style.maxWidth = "300px";
                        element.style.margin = "10px";
                        element.src = URL.createObjectURL(file);
                    } else if (fileType.startsWith("video/")) {
                        element = document.createElement("video");
                        element.style.maxWidth = "300px";
                        element.style.margin = "10px";
                        element.controls = true;
                        element.src = URL.createObjectURL(file);
                    }
                    if (element) {
                        preview.appendChild(element);
                    }
                }
            }

        </script>
    </body>
</html>