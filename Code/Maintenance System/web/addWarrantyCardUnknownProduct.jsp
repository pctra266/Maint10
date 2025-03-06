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

        <style>
            /* Hiệu ứng fade-in cho nội dung main */
            @keyframes fadeIn {
                from {
                    opacity: 0;
                    transform: translateY(10px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            /* Vùng main.content với giao diện sang trọng và hiệu ứng */
            main.content {
                background-color: #ffffff;
                padding: 30px;
                margin: 20px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
                font-family: 'Inter', sans-serif;
                animation: fadeIn 0.8s ease-in-out; /* Áp dụng hiệu ứng fade-in */
            }

            /* Tiêu đề chính (h2) và tiêu đề phụ (h3) */
            main.content h2 {
                font-size: 2em;
                font-weight: 600;
                margin-bottom: 20px;
                color: #333333;
            }

            main.content h3 {
                font-size: 1.6em;
                margin: 25px 0 15px;
                color: #555555;
            }

            /* Bảng hiển thị thông tin */
            main.content table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
            }

            /* Thống nhất kiểu chữ cho cả th và td */
            main.content table th,
            main.content table td {
                padding: 12px 15px;
                border-bottom: 1px solid #e0e0e0;
                color: #444444;
            }

            /* Riêng th sẽ đậm hơn, đồng nhất cho cả hai bảng */
            main.content table th {
                font-weight: 600;
                background-color: transparent;
                text-align: left;
            }

            /* Hiệu ứng hover cho các dòng trong bảng */
            main.content table tr:hover {
                background-color: #f1f1f1;
                transition: background-color 0.3s ease;
            }

            /* Form tạo Warranty Card */
            main.content form {
                margin-top: 20px;
            }

            main.content form label {
                display: block;
                margin-bottom: 8px;
                font-weight: 600;
                color: #444444;
            }

            /* Các trường nhập liệu, textarea, select */
            main.content form input[type="text"],
            main.content form input[type="datetime-local"],
            main.content form textarea,
            main.content form select {
                width: 100%;
                padding: 10px 12px;
                margin-bottom: 15px;
                border: 1px solid #cccccc;
                border-radius: 4px;
                font-size: 1rem;
                box-sizing: border-box;
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }

            /* Hiệu ứng focus cho input, textarea, select */
            main.content form input[type="text"]:focus,
            main.content form input[type="datetime-local"]:focus,
            main.content form textarea:focus,
            main.content form select:focus {
                border-color: #3B7DDD;
                box-shadow: 0 0 5px rgba(59, 125, 221, 0.5);
                outline: none;
            }

            /* Trường chọn file */
            main.content form input[type="file"] {
                margin-bottom: 15px;
            }

            /* Nút submit với màu #3B7DDD và hiệu ứng */
            main.content form input[type="submit"] {
                background-color: #3B7DDD;
                color: #ffffff;
                padding: 12px 25px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 1rem;
                transition: background-color 0.3s ease, box-shadow 0.3s ease, transform 0.2s ease;
            }

            main.content form input[type="submit"]:hover {
                background-color: #326ac3;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
                transform: translateY(-2px);
            }

            /* Hiển thị ảnh preview với hiệu ứng fade-in */
            main.content img#imagePreview {
                display: none;
                max-width: 100%;
                height: auto;
                margin-top: 10px;
                border: 1px solid #cccccc;
                border-radius: 4px;
                opacity: 0;
                transition: opacity 0.5s ease;
            }

            /* Khi preview được kích hoạt, thêm class "show" để fade-in */
            main.content img#imagePreview.show {
                opacity: 1;
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
                    <!-- Display error message -->
                    <c:if test="${not empty errorMessage}">
                        <div style="color: red; font-weight: bold;">${errorMessage}</div>
                    </c:if>
                    <h3>Customer Information</h3>
                    <table border="1">
                        <tr><th>Name</th><td>${customer.name}</td></tr>
                        <tr><th>Gender</th><td>${customer.gender}</td></tr>
                        <tr><th>Email</th><td>${customer.email}</td></tr>
                        <tr><th>Phone</th><td>${customer.phone}</td></tr>
                        <tr><th>Address</th><td>${customer.address}</td></tr>
                    </table>

                    <h3>Product Information</h3>
                    <table border="1">
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

                        <label>Warranty Card Code:</label>
                        <input id="warrantyCardCode" type="text" name="warrantyCardCode" value="${param.warrantyCardCode}" required/><br/>

                        <label>Issue Description:</label>
                        <textarea id="issueDescription" name="issueDescription" required>${param.issueDescription}</textarea>

                        <label>Assigned Staff:</label>
                        <select id="staffID" name="staffId" required>
                            <c:forEach var="staff" items="${staffList}">
                                <option value="${staff.staffID}" <c:if test="${param.staffId eq staff.staffID}">selected</c:if>>
                                    ${staff.name}
                                </option>
                            </c:forEach>
                        </select><br/>

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

                        <label>Upload Warranty Image:</label>
                        <input type="file" name="warrantyImage" accept="image/*" required onchange="previewImage(event)" />
                        <br/>
                        <img id="imagePreview" src="#" alt="Preview Image" style="display: none; max-width: 300px; margin-top: 10px;" />
                        <input type="submit" value="Create Warranty Card"/>
                        <a href="listUnknown" class="btn-update">
                            <i class="fas fa-backward  "></i>Back
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
            function previewImage(event) {
                var file = event.target.files[0];
                if (file) {
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        var imagePreview = document.getElementById("imagePreview");
                        imagePreview.src = e.target.result;
                        imagePreview.style.display = "block";
                    }
                    reader.readAsDataURL(file);
                }
            }

        </script>
    </body>
</html>