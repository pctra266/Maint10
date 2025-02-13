<%-- 
    Document   : staff-information
    Created on : Jan 18, 2025, 7:29:40 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Document</title>
        <link rel="stylesheet" href="./css/add-staff.css" />
        <style>
            body {
    font-family: 'Poppins', sans-serif;
    background-color: #f4f7f6;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    margin: 0;
    overflow: auto;
}

.add {
    margin-top: 100px;
    width: 90%;
    max-width: 450px;
    background: #fff;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    overflow: auto;
}

.add__signin-input {
    position: relative;
    margin-bottom: 15px;
}

.add__input {
    width: 90%;
    padding: 4px;
    border-radius: 5px;
    font-size: 14px;
    margin-left: 16px;
    background-color: white;
}

.add__input-label {
    position: absolute;
    top: 10px;
    left: 12px;
    font-size: 12px;
    color: #666;
    transition: 0.3s;
    background: white;
    padding: 0 5px;
}

.add__input:focus + .add__input-label,
.add__input.has-content + .add__input-label {
    top: -10px;
    font-size: 12px;
    color: #007bff;
}

button {
    background: #007bff;
    color: white;
    padding: 12px 15px;
    border: none;
    border-radius: 5px;
    font-size: 16px;
    cursor: pointer;
    width: 100%;
    transition: 0.3s;
}

button:hover {
    background: #0056b3;
}

.image-upload-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 10px;
}

.image-box {
    width: 100px;
    height: 100px;
    display: flex;
    justify-content: center;
    align-items: center;
    border: 2px solid #ddd;
    border-radius: 10px;
    overflow: hidden;
    background-color: #f8f8f8;
}

.image-box img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 10px;
}
.alert {
    background-color: #ffcccc; /* Màu nền đỏ nhạt */
    color: #d8000c; /* Màu chữ đỏ đậm */
    padding: 10px;
    border-radius: 5px;
    font-weight: bold;
}
.alert:empty {
    display: none;
}

        </style> 
         
    </head>
    <body>
        <div class="add">
            
            <div class="add__signin">
                <div class="add__signin-info">Information</div>
                <c:if test="${not empty errorMessage}">
                    <div class="alert">${errorMessage}</div>
                </c:if>
                <c:if test="${not empty ErrorImage}">
                    <div class="alert">${ErrorImage}</div>
                </c:if>
                <form action="StaffController" method="post" enctype="multipart/form-data">    
                    <input type="hidden" name="action" value="Update">
                            <input
                                id="staffID"
                                type="hidden"
                                class="add__input"
                                oninput="checkInput(this)"
                                name="staffID"
                                value="${staff.getStaffID()}"
                                readonly
                            />


                        <div class="add__signin-input">    
                            <label for="password" class="add__input-label"
                                >Use Name
                            </label><br>
                            <input
                                id="usename"
                                type="usename"
                                class="add__input"
                                oninput="checkInput(this)"
                                name="usename"
                                value="${staff.getUsernameS()}"
                                required
                            />

                        </div>
                        
                            <input
                                id="password"
                                type="hidden"
                                class="add__input"
                                oninput="checkInput(this)"
                                name="password"
                                value="${staff.getPasswordS()}"
                                required
                            />


                        <div class="add__signin-input">
                            <label for="password" class="add__input-label"
                                >Role</label
                            ><br>
                            <select
                                id="role"
                                type="role"
                                class="add__input"
                                oninput="checkInput(this)"
                                name="role"
                                required
                                >
                                <option value="Admin" ${staff.getRole() == 'Admin' ? 'selected' : ''}>Admin</option>
                                <option value="Technician" ${staff.getRole() == 'Technician' ? 'selected' : ''}>Technician</option>
                                <option value="Inventory Manager" ${staff.getRole() == 'Inventory Manager' ? 'selected' : ''}>Inventory Manager</option>
                                <option value="Customer" ${staff.getRole() == 'Customer' ? 'selected' : ''}>Customer</option>
                                <option value="Repair Contractor" ${staff.getRole() == 'Repair Contractor' ? 'selected' : ''}>Repair Contractor</option>
                                <option value="Customer Service Agent" ${staff.getRole() == 'Customer Service Agent' ? 'selected' : ''}>Customer Service Agent</option>
                            </select>

                        </div>
                        <div class="add__signin-input">
                            <label for="password" class="add__input-label"
                                >Name</label
                            ><br>
                            <input
                                id="name"
                                type="name"
                                class="add__input"
                                oninput="checkInput(this)"
                                name="name"
                                value="${staff.getName()}"
                                required
                            />

                        </div>
                        <div class="add__signin-input">
                            <label for="email" class="add__input-label"
                                >Email</label
                            ><br>
                            <input
                                id="email"
                                type="email"
                                class="add__input"
                                oninput="checkInput(this)"
                                name="email"
                                value="${staff.getEmail()}"
                                required
                            />

                        </div>
                        <div class="add__signin-input">
                            <label for="password" class="add__input-label"
                                >Phone</label
                            ><br>
                            <input
                                id="phone"
                                type="phone"
                                class="add__input"
                                oninput="checkInput(this)"
                                name="phone"
                                value="${staff.getPhone()}"
                                required
                            />

                        </div>
                        <div class="add__signin-input">
                            <label for="password" class="add__input-label"
                                >Address</label
                            ><br>
                            <input
                                id="address"
                                type="address"
                                class="add__input"
                                oninput="checkInput(this)"
                                name="address"
                                value="${staff.getAddress()}"
                                required
                            />

                        </div>
                        
                        <div class="image-upload-container">
                            <div class="image-box">
                                <img src="${staff.getImgage()}" id="currentImage" alt="Profile Image">
                            </div>
                            <input type="file" name="newImage" id="newImage" accept="image/*" onchange="previewImage(event)">
                        </div>    
                           
                        
                        <div class="add__signin-next">  
                            <c:if test="${not empty change}">
                                <button type="submit" onclick="return change">Change</button>  
                            </c:if>
                        </div>
                    </form>
                    <div class="add__signin-next">                        
                        <form action="StaffController" method="get">
                            <button type="submit">Cancle</button>
                        </form>                        
                    </div>
                    
                    
                
            
        </div>
        <script>
                function checkInput(input) {
                    if (input.value !== "") {
                        input.classList.add("has-content");
                    } else {
                        input.classList.remove("has-content");
                    }
                }   
                function previewImage(event) {
                                    const reader = new FileReader();
                                    reader.onload = function () {
                                        const output = document.getElementById('currentImage');
                                        output.src = reader.result;
                                    };
                                    reader.readAsDataURL(event.target.files[0]);
                }
        </script>
    </body>
</html>