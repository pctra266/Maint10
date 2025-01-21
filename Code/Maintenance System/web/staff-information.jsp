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
    </head>
    <body>
        <div class="add">
            
            <div class="add__signin">
                <div class="add__signin-info">Information</div>
                <form action="updateStaffController" method="post">    
                <div class="add__signin-input">
                        <label for="password" class="add__input-label"
                            >Staff ID</label
                        ><br>
                        <input
                            id="staffID"
                            type="staffID"
                            class="add__input"
                            oninput="checkInput(this)"
                            name="staffID"
                            value="${staff.staffID}"
                            required
                        />
                        
                    </div>
                    
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

                            value="${staff.getUseNameS()}"
                            required
                        />
                        
                    </div>
                    <div class="add__signin-input">
                        <label for="password" class="add__input-label"
                            >Password</label
                        ><br>
                        <input
                            id="password"
                            type="password"
                            class="add__input"
                            oninput="checkInput(this)"
                            name="password"

                            value="${staff.getPasswordS()}"
                            required
                        />
                        
                    </div>
                    
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
                            <option value="option1"></option>
                            <option value="Admin">Admin</option>
                            <option value="Technician">Technician</option>
                            <option value="Inventory Manager">Inventory Manager</option>
                            <option value="Customer">Customer</option>
                            <option value="Repair Contractor">Repair Contractor</option>
                            <option value="Customer Service Agen">Customer Service Agent</option>
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
                    <div class="add__signin-next">
                        
                        
                            <button type="submit">Change</button>
                        
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
        </script>
    </body>
</html>