<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">

        <script>
            function toggleEdit() {
                document.getElementById("profileDisplay").style.display = "none";
                document.getElementById("profileEdit").style.display = "block";
            }
            function cancelEdit() {
                document.getElementById("profileDisplay").style.display = "block";
                document.getElementById("profileEdit").style.display = "none";
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
                        <div class="profile-image-container">
                            <img src="${empty profile.image ? 'default.png' : profile.image}" alt="Profile Image" class="profile-image">
                            <a href="updateAvatar.jsp" class="update-avatar-button">Update Avatar</a>
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
                                <button class="edit-button" onclick="toggleEdit()">Edit</button>
                            </div>
                            <form id="profileEdit" action="profile" method="post" style="display: none;">
                                <h2>Edit Profile</h2>
                                <input type="hidden" name="userType" value="${not empty customerProfile ? 'customer' : 'staff'}">
                                <input type="hidden" name="username" value="${not empty customerProfile ? customerProfile.usernameC : staffProfile.usernameS}">
                                <input type="hidden" name="id" value="${not empty customerProfile ? customerProfile.customerID : staffProfile.staffID}">

                                <p><strong>Name:</strong> 
                                    <input type="text" name="name" value="${not empty customerProfile ? customerProfile.name : staffProfile.name}" required>
                                </p>
                                <p><strong>Gender:</strong> 
                                    <select name="gender" required>
                                        <option value="Male" ${not empty customerProfile ? (customerProfile.gender == 'Male' ? 'selected' : '') : (staffProfile.gender == 'Male' ? 'selected' : '')}>Male</option>
                                        <option value="Female" ${not empty customerProfile ? (customerProfile.gender == 'Female' ? 'selected' : '') : (staffProfile.gender == 'Female' ? 'selected' : '')}>Female</option>
                                    </select>
                                </p>
                                <p><strong>Date of Birth:</strong> 
                                    <input type="text" name="dateOfBirth" value="${not empty customerProfile ? customerProfile.dateOfBirth : staffProfile.date}" required>
                                </p>
                                <p><strong>Email:</strong> 
                                    <input type="email" name="email" value="${not empty customerProfile ? customerProfile.email : staffProfile.email}" required>
                                </p>
                                <p><strong>Phone:</strong> 
                                    <input type="text" name="phone" value="${not empty customerProfile ? customerProfile.phone : staffProfile.phone}" required>
                                </p>
                                <p><strong>Address:</strong> 
                                    <input type="text" name="address" value="${not empty customerProfile ? customerProfile.address : staffProfile.address}" required>
                                </p>

                                <div class="profile-actions">
                                    <button type="submit" class="update-button">Update Profile</button>
                                    <button type="button" class="cancel-button" onclick="cancelEdit()">Cancel</button>
                                </div>
                            </form>

                            <a href="HomePage.jsp" class="back-button">Back to Home</a>
                        </div>
                    </div>
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>

        <script>
                                        function toggleEdit() {
                                            document.getElementById("profileDisplay").style.display = "none";
                                            document.getElementById("profileEdit").style.display = "block";
                                        }

                                        function cancelEdit() {
                                            document.getElementById("profileDisplay").style.display = "block";
                                            document.getElementById("profileEdit").style.display = "none";
                                        }

        </script>

    </body>
</html>
