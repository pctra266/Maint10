<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Add Customer</title>
    <link href="css/light.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
</head>

<body>
    <div class="wrapper">
        <jsp:include page="/includes/navbar-left.jsp" />
        <div class="main">
            <jsp:include page="/includes/navbar-top.jsp" />
            <main class="content">
                <h2>Add Customer</h2>

                
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>${error}</strong>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>

                <form class="row" action="customer?action=add" method="POST" enctype="multipart/form-data" onsubmit="return validateForm()">
                    <div class="col-md-8">
                        <div class="col-md-12 row g-3">
                            <div class="col-md-10">
                                <label for="username" class="form-label">Username</label>
                                <input type="text" class="form-control" name="username" id="username" value="${username}" required>
                            </div>
                            <div class="col-md-10">
                                <label for="password" class="form-label">Password</label>
                                <input type="password" class="form-control" name="password" id="password" required>
                            </div>
                            <div class="col-md-10">
                                <label for="customerName" class="form-label">Customer Name</label>
                                <input type="text" class="form-control" name="name" id="customerName" value="${customerName}" required>
                            </div>
                            <div class="col-md-10">
                                <label class="form-label">Gender</label>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="gender" id="genderMale" value="Male" ${gender == 'Male' ? 'checked' : ''} required>
                                    <label class="form-check-label" for="genderMale">Male</label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="gender" id="genderFemale" value="Female" ${gender == 'Female' ? 'checked' : ''} required>
                                    <label class="form-check-label" for="genderFemale">Female</label>
                                </div>
                            </div>
                            <div class="col-md-10">
                                <label for="customerEmail" class="form-label">Email</label>
                                <input type="email" class="form-control" name="email" id="customerEmail" value="${customerEmail}" required>
                            </div>
                            <div class="col-md-10">
                                <label for="customerPhone" class="form-label">Phone</label>
                                <input type="tel" class="form-control" name="phone" id="customerPhone" value="${customerPhone}" required>
                            </div>
                            <div class="col-md-10">
                                <label for="customerAddress" class="form-label">Address</label>
                                <input type="text" class="form-control" name="address" id="customerAddress" value="${customerAddress}" required>
                            </div>
                            <div class="col-md-10">
                                <label for="customerImage" class="form-label">Image</label>
                                <input type="file" class="form-control" name="image" id="customerImage" accept="image/*" onchange="previewImage(event)">
                                <img id="preview" src="" alt="Image Preview" style="display:none; max-width:100px; margin-top:10px;">
                            </div>
                            <div class="col-md-12" style="margin-bottom: 1rem">
                                <button class="btn btn-success" type="submit">Add</button>
                            </div>
                        </div>
                    </div>
                </form>
            </main>
            <jsp:include page="/includes/footer.jsp" />
        </div>
    </div>

    <script>
        // Hiển thị ảnh preview khi chọn file
        function previewImage(event) {
            const file = event.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function() {
                    const preview = document.getElementById('preview');
                    preview.src = reader.result;
                    preview.style.display = 'block';
                }
                reader.readAsDataURL(file);
            }
        }

        // Kiểm tra dữ liệu đầu vào
        function validateForm() {
            let username = document.getElementById("username").value.trim();
            let password = document.getElementById("password").value.trim();
            let customerName = document.getElementById("customerName").value.trim();
            let email = document.getElementById("customerEmail").value.trim();
            let phone = document.getElementById("customerPhone").value.trim();
            let address = document.getElementById("customerAddress").value.trim();

            if (!username || !password || !customerName || !email || !phone || !address) {
                alert("All fields are required!");
                return false;
            }

            return true;
        }
    </script>

    <script src="js/app.js"></script>
</body>

</html>
