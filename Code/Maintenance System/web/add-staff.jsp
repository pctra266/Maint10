<%-- 
    Document   : add-staff
    Created on : Jan 18, 2025, 3:24:41 AM
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
            }

            .add {
                width: 100%;
                max-width: 500px;
                background: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            }

            .add__signin-info {
                font-size: 20px;
                font-weight: bold;
                margin-bottom: 15px;
                text-align: center;
                color: #333;
            }

            .alert {
                background: #ffefef;
                color: #d9534f;
                padding: 10px;
                border-radius: 5px;
                text-align: center;
                margin-bottom: 10px;
            }
            .alert:empty {
                display: none;
            }

            .add__signin-input {
                position: relative;
                margin-bottom: 20px;
            }

            .add__input {
                width: 100%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 14px;
            }

            .add__input-label {
                position: absolute;
                top: 50%;
                left: 10px;
                transform: translateY(-50%);
                font-size: 14px;
                color: #999;
                transition: 0.3s;
                pointer-events: none;
            }

            .add__input:focus + .add__input-label,
            .add__input.has-content + .add__input-label {
                top: -4px;
                font-size: 18px;
                color: #007bff;
            }

            .add__signin-next {
                text-align: center;
            }

            button {
                background: #007bff;
                color: white;
                padding: 10px 15px;
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

            /* Image Upload */
            .image-upload-container {
                display: flex;
                flex-direction: column;
                align-items: center;
                gap: 10px; /* Khoảng cách giữa ảnh và nút */
            }

            .image-box {
                width: 120px; /* Kích thước khung vuông */
                height: 120px;
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
                object-fit: cover; /* Giữ tỉ lệ ảnh */
                border-radius: 10px;
            }

            input[type="file"] {
                text-align: center;
                display: block;
            }
        </style>        
    </head>
    <body>
        <div class="add">
            
            <div class="add__signin">
                <div class="add__signin-info">Enter information</div>
                <c:if test="${not empty errorMessage}">
                    <div class="alert">${errorMessage}</div>
                </c:if>
                <c:if test="${not empty ErrorImage}">
                    <div class="alert">${ErrorImage}</div>
                </c:if>
                <form action="StaffController" method="post"  enctype="multipart/form-data">
                    <input type="hidden" name="action" value="AddStaff">
                    <div class="add__signin-input">
                        <input
                            id="usename"
                            type="text"
                            class="add__input"
                            oninput="checkInput(this)"
                            name="usename"
                            required
                        />
                        <label for="password" class="add__input-label"
                            >Use Name</label
                        >
                    </div>
                    <div class="add__signin-input">
                        <input
                            id="password"
                            type="password"
                            class="add__input"
                            oninput="checkInput(this)"
                            name="password"
                            required
                        />
                        <label for="password" class="add__input-label"
                            >Password</label
                        >
                    </div>
                    
                    <div class="add__signin-input">
                        <select
                            id="role"
                            class="add__input"
                            oninput="checkInput(this)"
                            name="role"
                            required
                            >
                            <option value="option1"></option>
                            <option value="Admin">Admin</option>
                            <option value="Technician">Technician</option>
                            <option value="Inventory Manager">Inventory Manager</option>
                            <option value="Customer">Customer</option>
                            <option value="Repair Contractor">Repair Contractor</option>
                            <option value="Customer Service Agent">Customer Service Agent</option>
                        </select>
                        <label for="password" class="add__input-label"
                            >Role</label
                        >
                    </div>
                    <div class="add__signin-input">
                        <input
                            id="name"
                            type="text"
                            class="add__input"
                            oninput="checkInput(this)"
                            name="name"
                            required
                        />
                        <label for="password" class="add__input-label"
                            >Name</label
                        >
                    </div>
                    <div class="add__signin-input">
                        <input
                            id="email"
                            type="email"
                            class="add__input"
                            oninput="checkInput(this)"
                            name="email"
                            required
                        />
                        <label for="email" class="add__input-label"
                            >Email</label
                        >
                    </div>
                    <div class="add__signin-input">
                        <input
                            id="phone"
                            type="tel"
                            class="add__input"
                            oninput="checkInput(this)"
                            name="phone"
                            required
                        />
                        <label for="password" class="add__input-label"
                            >Phone</label
                        >
                    </div>
                    <div class="add__signin-input">
                        <input
                            id="address"
                            type="text"
                            class="add__input"
                            oninput="checkInput(this)"
                            name="address"
                            required
                        />
                        <label for="password" class="add__input-label"
                            >Address</label
                        >
                    </div>
                    

                    <div class="image-upload-container">
                        <div class="image-box">
                            <img src="${list.image}" id="currentImage" alt="Profile Image">
                        </div>
                        <input type="file" name="newImage" id="newImage" accept="image/*" onchange="previewImage(event)">
                    </div>
                    <div class="add__signin-next">
                        <button type="submit">Submit</button>
                        
                    </div>
                         
                </form>
                            
                
            
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