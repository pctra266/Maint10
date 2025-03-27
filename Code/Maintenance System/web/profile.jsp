<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"/>


        <style>
            /* 
             * CHỈ CSS CHO CÁC PHẦN TRONG main
             */

            /***** MAIN LAYOUT *****/
            main.content {
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 30px 15px;
                background: linear-gradient(135deg, #f7f7f7, #eaeaea);
            }

            /* PROFILE CONTAINER BÊN TRONG MAIN */
            .profile-container {
                background: #ffffff;
                box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
                border-radius: 12px;
                max-width: 1200px;
                width: 100%;
                padding: 40px;
                margin: 20px;
            }

            /* TOP SECTION: Avatar & Info */
            .top-section {
                display: flex;
                gap: 30px;
                flex-wrap: wrap;
            }

            /* AVATAR BOX */
            .avatar-box {
                flex: 0 0 250px;
                text-align: center;
                border-right: 1px solid #e0e0e0;
                padding-right: 20px;
            }
            .profile-image {
                width: 140px;
                height: 140px;
                border-radius: 50%;
                object-fit: cover;
                border: 4px solid #3498db;
                margin-bottom: 15px;
            }
            .avatar-actions {
                display: flex;
                flex-direction: column;
                gap: 10px;
                align-items: center;
            }
            .avatar-button {
                background: #3498db;
                color: #fff;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
                font-size: 14px;
                transition: background 0.3s ease;
            }
            .avatar-button:hover {
                background: #2980b9;
            }

            /* INFO BOX */
            .info-box {
                flex: 1;
                display: flex;
                flex-direction: column;
            }

            /* INFO DISPLAY */
            #profileDisplay {
                margin-bottom: 20px;
            }
            .info-grid {
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 20px 40px;
                margin-bottom: 20px;
            }
            .info-grid > div {
                display: flex;
                flex-direction: column;
            }
            .info-grid strong {
                font-weight: 500;
                color: #3498db;
                margin-bottom: 5px;
                font-size: 15px;
            }
            .info-grid span {
                color: #555;
                font-size: 15px;
            }

            /* BUTTON ROW (Edit Profile, Change Password, Back to Home) */
            .btn-row {
                display: flex;
                gap: 15px;
                align-items: center;
                flex-wrap: wrap;
            }
            .update-setting-button, .blue-link-button {
                background: #3498db;
                color: #fff;
                border: none;
                padding: 10px 25px;
                border-radius: 30px;
                cursor: pointer;
                font-size: 14px;
                text-decoration: none;
                transition: background 0.3s ease;
            }
            .update-setting-button:hover,
            .blue-link-button:hover {
                background: #2980b9;
                text-decoration: none;
            }

            /* FORM STYLES (bên trong main) */
            form {
                background: #fafafa;
                padding: 25px;
                border-radius: 8px;
                margin-bottom: 20px;
            }
            form h2 {
                margin-bottom: 20px;
                color: #3498db;
                font-size: 20px;
            }
            .edit-grid {
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 20px 40px;
                margin-bottom: 20px;
            }
            .form-group {
                display: flex;
                flex-direction: column;
            }
            .form-group label {
                color: #3498db;
                margin-bottom: 8px;
                font-size: 14px;
            }
            .form-group input[type="text"],
            .form-group input[type="email"],
            .form-group input[type="date"],
            .form-group select,
            .form-group input[type="file"] {
                border: none;
                border-bottom: 1px solid #ccc;
                padding: 8px 5px;
                font-size: 14px;
                background: transparent;
                color: #333;
            }
            .form-group input:focus,
            .form-group select:focus {
                border-bottom-color: #3498db;
                outline: none;
            }

            /* RESPONSIVE CHO main NẾU MUỐN */
            @media (max-width: 768px) {
                .top-section {
                    flex-direction: column;
                    align-items: center;
                }
                .avatar-box {
                    border-right: none;
                    padding-right: 0;
                    margin-bottom: 20px;
                }
                .info-grid {
                    grid-template-columns: 1fr;
                    gap: 15px;
                }
            }

            /* CSS cho thông báo (alert) chỉ trong phần main */

            .alert {
                padding: 15px 20px;
                margin-bottom: 20px;
                border-radius: 5px;
                font-size: 14px;
                text-align: center;
                box-shadow: 0 2px 6px rgba(0,0,0,0.1);
                transition: background-color 0.3s ease;
            }

            .alert-danger {
                background-color: #f8d7da;
                color: #842029;
                border: 1px solid #f5c2c7;
            }

            .alert-success {
                background-color: #d1e7dd;
                color: #0f5132;
                border: 1px solid #badbcc;
            }

            /* Tạo dòng kẻ hồng dưới mỗi cặp (label + span) */
            .info-grid > div {
                position: relative;
                padding-bottom: 10px;
                margin-bottom: 20px;
                border-bottom: 1px solid #2778AE; /* màu hồng */
            }

            /* Nếu không muốn dòng kẻ cho ô cuối cùng, bỏ border ở ô cuối */
            .info-grid > div:last-child {
                border-bottom: none;
            }

            .timeline {
                margin-top: 20px;
                position: relative;
                padding-left: 40px; /* chừa chỗ cho cột timeline */
            }
            .timeline::before {
                content: "";
                position: absolute;
                left: 20px;
                top: 0;
                bottom: 0;
                width: 2px;
                background: #3498db;
            }
            .timeline-item {
                margin-bottom: 20px;
                position: relative;
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

                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger" >
                                ${errorMessage}
                            </div>
                        </c:if>

                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-success">
                                ${successMessage}
                            </div>
                        </c:if>
                        
                        <div class="top-section">
                            <div class="avatar-box">
                                <c:choose>
                                    <c:when test="${not empty customerProfile}">
                                        <img src="${empty customerProfile.image ? 'default.png' : customerProfile.image}"
                                             alt="Profile Image" class="profile-image">
                                    </c:when>
                                    <c:when test="${not empty staffProfile}">
                                        <img src="${empty staffProfile.image ? 'default.png' : staffProfile.image}"
                                             alt="Profile Image" class="profile-image">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="default.png" alt="Profile Image" class="profile-image">
                                    </c:otherwise>
                                </c:choose>
                                <div class="avatar-actions">
                                    <button class="avatar-button" onclick="toggleEditAvatar()">Change Avatar</button>                             
                                </div>

                                <div class="social-icons" style="margin-top: 20px;">
                                    <a href="#"><i class="fab fa-facebook-f"></i></a>
                                    <a href="#"><i class="fab fa-twitter"></i></a>
                                    <a href="#"><i class="fab fa-instagram"></i></a>
                                    <a href="#"><i class="fab fa-tiktok"></i></a>
                                </div>

                                <div class="skill-section" style="margin-top: 15px;">
                                    <div class="skill">
                                        <span>Java</span>
                                        <div class="bar" >
                                            <div class="progress"  style="width: 90%; background-color:  #A6D2EF "></div>
                                        </div>
                                    </div>
                                    <div class="skill">
                                        <span>CSS</span>
                                        <div class="bar">
                                            <div class="progress" style="width: 80%;background-color:  #A6D2EF "></div>
                                        </div>
                                    </div>
                                    <div class="skill">
                                        <span>HTML</span>
                                        <div class="bar">
                                            <div class="progress" style="width: 60%;background-color:  #A6D2EF "></div>
                                        </div>
                                    </div>
                                </div>

                            </div>

                            <!-- Khối hiển thị thông tin (2 cột) hoặc form sửa thông tin -->
                            <div class="info-box">
                                <!-- Hiển thị thông tin (không sửa) -->
                                <div id="profileDisplay">
                                    <div class="info-grid">
                                        <c:choose>
                                            <c:when test="${not empty customerProfile}">
                                                <div>
                                                    <strong>Name</strong>
                                                    <span>${customerProfile.name}</span>

                                                </div>
                                                <div>
                                                    <strong>Username</strong>
                                                    <span>${customerProfile.usernameC}</span>
                                                </div>
                                                <div>
                                                    <strong>Date Of Birth</strong>
                                                    <span>${customerProfile.dateOfBirth}</span>
                                                </div>
                                                <div>
                                                    <strong>Gender</strong>
                                                    <span>${customerProfile.gender}</span>
                                                </div>
                                                <div>
                                                    <strong>Email</strong>
                                                    <span>${customerProfile.email}</span>
                                                </div>
                                                <div>
                                                    <strong>Phone</strong>
                                                    <span>${customerProfile.phone}</span>
                                                </div>
                                                <div>
                                                    <strong>Address</strong>
                                                    <span>${customerProfile.address}</span>
                                                </div>
                                            </c:when>
                                            <c:when test="${not empty staffProfile}">
                                                <div>
                                                    <strong>Name</strong>
                                                    <span>${staffProfile.name}</span>
                                                </div>
                                                <div>
                                                    <strong>Username</strong>
                                                    <span>${staffProfile.usernameS}</span>
                                                </div>
                                                <div>
                                                    <strong>Date Of Birth</strong>
                                                    <span>${staffProfile.date}</span>
                                                </div>
                                                <div>
                                                    <strong>Gender</strong>
                                                    <span>${staffProfile.gender}</span>
                                                </div>
                                                <div>
                                                    <strong>Email</strong>
                                                    <span>${staffProfile.email}</span>
                                                </div>
                                                <div>
                                                    <strong>Phone</strong>
                                                    <span>${staffProfile.phone}</span>
                                                </div>
                                                <div>
                                                    <strong>Address</strong>
                                                    <span>${staffProfile.address}</span>
                                                </div>
                                                <div>
                                                    <strong>Role</strong>
                                                    <span>${roleName}</span>
                                                </div>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                    <!-- Nút bật form sửa và 2 link ngang hàng -->
                                    <div class="btn-row">
                                        <button class="update-setting-button" onclick="toggleEditProfile()">Edit Profile</button>
                                        <!-- Dùng link kiểu nút xanh dương -->
                                        <a class="blue-link-button" href="ChangePasswordForm.jsp">Change Password</a>
                                        <a class="blue-link-button" href="HomePage.jsp">Back to Home</a>
                                    </div>
                                </div>

                                <!-- Form cập nhật Avatar -->
                                <form id="profileEditAvatarForm" action="profile" method="post" enctype="multipart/form-data" style="display: none;">
                                    <h2>Update Avatar</h2>
                                    <input type="hidden" name="userType" value="updateImage">
                                    <input type="hidden" name="username" value="${not empty customerProfile ? customerProfile.usernameC : staffProfile.usernameS}">
                                    <input type="hidden" name="id" value="${not empty customerProfile ? customerProfile.customerID : staffProfile.staffID}">
                                    <div class="form-group">
                                        <label>Choose Avatar</label>
                                        <input type="file" name="image" accept="image/*" required>
                                    </div>
                                    <div class="btn-row">
                                        <button type="submit" class="update-setting-button">Update Avatar</button>
                                        <button type="button" class="cancel-button" onclick="cancelEdit()">Cancel</button>
                                    </div>
                                </form>

                                <!-- Form cập nhật thông tin (2 cột) -->
                                <form id="profileEditForm" name="profileEditForm" action="profile" method="post"
                                      onsubmit="return validateForm()" style="display: none;">
                                    <h2>Edit Profile</h2>
                                    <input type="hidden" name="userType" value="${not empty customerProfile ? 'customer' : 'staff'}">
                                    <input type="hidden" name="username" value="${not empty customerProfile ? customerProfile.usernameC : staffProfile.usernameS}">
                                    <input type="hidden" name="id" value="${not empty customerProfile ? customerProfile.customerID : staffProfile.staffID}">

                                    <div class="edit-grid">
                                        <div class="form-group">
                                            <label>Name</label>
                                            <input type="text" name="name"
                                                   value="${not empty customerProfile ? customerProfile.name : staffProfile.name}" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Gender</label>
                                            <select name="gender" required>
                                                <option value="Male" ${not empty customerProfile ? (customerProfile.gender == 'Male' ? 'selected' : '') : (staffProfile.gender == 'Male' ? 'selected' : '')}>Male</option>
                                                <option value="Female" ${not empty customerProfile ? (customerProfile.gender == 'Female' ? 'selected' : '') : (staffProfile.gender == 'Female' ? 'selected' : '')}>Female</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Date of Birth</label>
                                            <input type="date" name="dateOfBirth"
                                                   value="${not empty customerProfile ? customerProfile.dateOfBirth : staffProfile.date}" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Email</label>
                                            <input type="email" name="email"
                                                   value="${not empty customerProfile ? customerProfile.email : staffProfile.email}" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Phone</label>
                                            <input type="text" name="phone"
                                                   value="${not empty customerProfile ? customerProfile.phone : staffProfile.phone}" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Address</label>
                                            <input type="text" name="address"
                                                   value="${not empty customerProfile ? customerProfile.address : staffProfile.address}" required>
                                        </div>
                                    </div>
                                    <div class="btn-row">
                                        <button type="submit" class="update-setting-button">Update</button>
                                        <button type="button" class="update-setting-button" onclick="cancelEdit()">Cancel</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </main>

                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
    </body>
</html>
