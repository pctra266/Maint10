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
                            id="staffId"
                            type="staffId"
                            class="add__input"
                            oninput="checkInput(this)"
                            name="staffId"
                            value="${staff.getStaffId()}"
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
                            placeholder="${staff.getUseNameS()}"
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
                            placeholder="${staff.getPasswordS()}"
                            required
                        />
                        
                    </div>
                    
                    <div class="add__signin-input">
                        <label for="password" class="add__input-label"
                            >Role</label
                        ><br>
                        <input
                            id="role"
                            type="role"
                            class="add__input"
                            oninput="checkInput(this)"
                            name="role"
                            placeholder="${staff.getRole()}"
                            required
                        />
                        
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
                            placeholder="${staff.getName()}"
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
                            placeholder="${staff.getEmail()}"
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
                            placeholder="${staff.getPhone()}"
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
                            placeholder="${staff.getAddress()}"
                            required
                        />
                        
                    </div>
                    <div class="add__signin-next">
                        
                        
                            <button type="submit">Change</button>
                        
                    </div>
                    </form>
                    <div class="add__signin-next">
                        
                        <form action="seeMoreController" method="post">
                            <button type="submit">Cancle</button>
                        </form>
                    </div>
                    
                    <div class="add__signin-next">
                        
                        <button type="submit">Delete</button>
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
