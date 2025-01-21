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
    </head>
    <body>
        <div class="add">
            
            <div class="add__signin">
                <div class="add__signin-info">Enter information</div>
                
                <form action="addStaffController" method="post">
                    <div class="add__signin-input">
                        <input
                            id="usename"
                            type="usename"
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
                        <label for="password" class="add__input-label"
                            >Role</label
                        >
                    </div>
                    <div class="add__signin-input">
                        <input
                            id="name"
                            type="name"
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
                            type="phone"
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
                            type="address"
                            class="add__input"
                            oninput="checkInput(this)"
                            name="address"
                            required
                        />
                        <label for="password" class="add__input-label"
                            >Address</label
                        >
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
        </script>
    </body>
</html>