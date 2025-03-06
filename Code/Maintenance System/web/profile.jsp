<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

        <style>
            main.content {
                display: flex;
                justify-content: center; /* Căn giữa theo chiều ngang */
                background: linear-gradient(135deg, #f0f0f0, #dcdcdc);
            }

            .profile-container {
                background: #ffffff;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                border-radius: 12px;
                padding: 30px;
                max-width : 800px;
                width: 100%;
                text-align: center;
            }

            .profile-image-container {
                position: relative;
                margin-bottom: 20px;
            }

            .profile-image {
                width: 120px;
                height: 120px;
                border-radius: 50%;
                object-fit: cover;
                border: 4px solid #3498db;
            }

            .edit-button {
                background: #3498db;
                color: #fff;
                border: none;
                padding: 10px 15px;
                border-radius: 5px;
                cursor: pointer;
                transition: 0.3s;
                font-size: 14px;
            }

            .edit-button:hover {
                background: #2980b9;
            }

            .profile-details {
                text-align: left;
            }

            .profile-details h2 {
                color: #333;
                margin-bottom: 10px;
            }

            .profile-details p {
                color: #555;
                font-size: 16px;
                margin: 5px 0;
            }

            .profile-actions {
                margin-top: 20px;
            }

            .update-button, .cancel-button {
                padding: 10px 15px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: 0.3s;
                font-size: 14px;
            }

            .update-button {
                background: #27ae60;
                color: #fff;
            }

            .update-button:hover {
                background: #219150;
            }

            .cancel-button {
                background: #e74c3c;
                color: #fff;
                margin-left: 10px;
            }

            .cancel-button:hover {
                background: #c0392b;
            }

            .back-button {
                display: inline-block;
                margin-top: 20px;
                text-decoration: none;
                color: #3498db;
                font-weight: bold;
                transition: 0.3s;
            }

            .back-button:hover {
                color: #2980b9;
            }

        </style>
        <script>
            function toggleEditProfile() {
                document.getElementById("profileDisplay").style.display = "none";
                document.getElementById("profileEditForm").style.display = "block";
            }

            function toggleEditAvatar() {
                document.getElementById("profileDisplay").style.display = "none";
                document.getElementById("profileEditAvatarForm").style.display = "block";
            }

            function cancelEdit() {
                document.getElementById("profileDisplay").style.display = "block";
                document.getElementById("profileEditForm").style.display = "none";
                document.getElementById("profileEditAvatarForm").style.display = "none";
            }

            function validateForm() {
                let name = document.forms["profileEditForm"]["name"].value;
                let email = document.forms["profileEditForm"]["email"].value;
                let phone = document.forms["profileEditForm"]["phone"].value;
                let address = document.forms["profileEditForm"]["address"].value;

                let namePattern = /^[A-Za-zÀ-ỹ]+( [A-Za-zÀ-ỹ]+)*$/;
                let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                let phonePattern = /^0\d{9}$/;
                let addressPattern = /^[A-Za-z0-9,./ ]+$/;

                if (!namePattern.test(name)) {
                    alert("Invalid name. Only letters and a single space between words are allowed.");
                    return false;
                }
                if (!emailPattern.test(email)) {
                    alert("Invalid email format.");
                    return false;
                }
                if (!phonePattern.test(phone)) {
                    alert("Invalid phone number. It must start with 0 and have exactly 10 digits.");
                    return false;
                }
                if (!addressPattern.test(address) || address.trim().length === 0) {
                    alert("Invalid address. It must contain only letters, numbers, and ., / characters.");
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />

                <main class="content">
                    <div class="profile-container">

                        <c:if test="${not empty message}">
                            <div class="alert">${message}</div>
                            <c:remove var="message" scope="session"/>
                        </c:if>

                        <div class="profile-image-container">
                            <c:choose>
                                <c:when test="${not empty customerProfile}">
                                    <img src="${empty customerProfile.image ? 'default.png' : customerProfile.image}" alt="Profile Image" class="profile-image">
                                </c:when>
                                <c:when test="${not empty staffProfile}">
                                    <img src="${empty staffProfile.image ? 'default.png' : staffProfile.image}" alt="Profile Image" class="profile-image">
                                </c:when>
                                <c:otherwise>
                                    <img src="default.png" alt="Profile Image" class="profile-image">
                                </c:otherwise>
                            </c:choose>
                            <button class="edit-button" onclick="toggleEditAvatar()">Edit Avatar</button>
                        </div>

                        <div class="profile-details">
                            <div id="profileDisplay">
                                <c:choose>
                                    <c:when test="${not empty customerProfile}">
                                        <h2>Customer Profile</h2>
                                        <p><strong>Username:</strong> ${customerProfile.usernameC}</p>
                                        <p><strong>Name:</strong> ${customerProfile.name}</p>
                                        <p><strong>Date Of Birth:</strong> ${customerProfile.dateOfBirth}</p>
                                        <p><strong>Gender:</strong> ${customerProfile.gender}</p>
                                        <p><strong>Email:</strong> ${customerProfile.email}</p>
                                        <p><strong>Phone:</strong> ${customerProfile.phone}</p>
                                        <p><strong>Address:</strong> ${customerProfile.address}</p>
                                    </c:when>
                                    <c:when test="${not empty staffProfile}">
                                        <h2>Staff Profile</h2>
                                        <p><strong>Username:</strong> ${staffProfile.usernameS}</p>
                                        <p><strong>Name:</strong> ${staffProfile.name}</p>
                                        <p><strong>Gender:</strong> ${staffProfile.gender}</p>
                                        <p><strong>Date Of Birth:</strong> ${staffProfile.date}</p>
                                        <p><strong>Email:</strong> ${staffProfile.email}</p>
                                        <p><strong>Phone:</strong> ${staffProfile.phone}</p>
                                        <p><strong>Address:</strong> ${staffProfile.address}</p>
                                        <p><strong>Role:</strong> ${roleName}</p>
                                    </c:when>
                                </c:choose>
                                <button class="edit-button" onclick="toggleEditProfile()">Edit Profile</button>
                            </div>

                            <!-- Form cập nhật Avatar -->
                            <form id="profileEditAvatarForm" action="profile" method="post" enctype="multipart/form-data" style="display: none;">
                                <h2>Update Avatar</h2>
                                <input type="hidden" name="userType" value="updateImage">
                                <input type="hidden" name="username" value="${not empty customerProfile ? customerProfile.usernameC : staffProfile.usernameS}">
                                <input type="hidden" name="id" value="${not empty customerProfile ? customerProfile.customerID : staffProfile.staffID}">
                                <p><strong>Choose Avatar:</strong> <input type="file" name="image" accept="image/*" required></p>
                                <div class="profile-actions">
                                    <button type="submit" class="update-button">Update Avatar</button>
                                    <button type="button" class="cancel-button" onclick="cancelEdit()">Cancel</button>
                                </div>
                            </form>

                            <!-- Form cập nhật thông tin cá nhân -->
                            <form id="profileEditForm" name="profileEditForm" action="profile" method="post" onsubmit="return validateForm()" style="display: none;">
                                <h2>Edit Profile</h2>
                                <input type="hidden" name="userType" value="${not empty customerProfile ? 'customer' : 'staff'}">
                                <input type="hidden" name="username" value="${not empty customerProfile ? customerProfile.usernameC : staffProfile.usernameS}">
                                <input type="hidden" name="id" value="${not empty customerProfile ? customerProfile.customerID : staffProfile.staffID}">
                                <p><strong>Name:</strong> <input type="text" name="name" value="${not empty customerProfile ? customerProfile.name : staffProfile.name}" required></p>
                                <p><strong>Gender:</strong> 
                                    <select name="gender" required>
                                        <option value="Male" ${not empty customerProfile ? (customerProfile.gender == 'Male' ? 'selected' : '') : (staffProfile.gender == 'Male' ? 'selected' : '')}>Male</option>
                                        <option value="Female" ${not empty customerProfile ? (customerProfile.gender == 'Female' ? 'selected' : '') : (staffProfile.gender == 'Female' ? 'selected' : '')}>Female</option>
                                    </select>
                                </p>
                                <p><strong>Date of Birth:</strong> 
                                    <input type="date" name="dateOfBirth" 
                                           value="${not empty customerProfile ? customerProfile.dateOfBirth : staffProfile.date}" 
                                           required>
                                </p>

                                <p><strong>Email:</strong> <input type="email" name="email" value="${not empty customerProfile ? customerProfile.email : staffProfile.email}" required></p>
                                <p><strong>Phone:</strong> <input type="text" name="phone" value="${not empty customerProfile ? customerProfile.phone : staffProfile.phone}" required></p>
                                <p><strong>Address:</strong> <input type="text" name="address" value="${not empty customerProfile ? customerProfile.address : staffProfile.address}" required></p>
                                <div class="profile-actions">
                                    <button type="submit" class="update-button">Update Profile</button>
                                    <button type="button" class="cancel-button" onclick="cancelEdit()">Cancel</button>
                                </div>
                            </form>
                            <a class="back-button" href="ChangePasswordForm.jsp">Change Password</a><br>
                            <a href="HomePage.jsp" class="back-button">Back to Home</a>
                        </div>
                    </div>
                </main>

                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>