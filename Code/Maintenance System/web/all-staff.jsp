<%-- 
    Document   : all-staff
    Created on : Jan 18, 2025, 2:59:09 AM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Model.Staff" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Dashboard Admin</title>

        <link rel="stylesheet" href="./css/staff.css" />
        <link rel="stylesheet" href="./assets/css/all-orders.css" />
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link
            href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,500;1,500&family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
            rel="stylesheet"
        />
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
        />
    </head>
    <body>
        <!-- Navbar -->
        <nav class="navbar">
            <h4>Dashboard</h4>
            <div class="profile">
                <span class="fas fa-search"></span>
                <img
                    src="./assets/img/admin/blank-profile-picture-973460_1280.webp"
                    alt=""
                    class="profile-image"
                />
                <p class="profile-name">${sessionScope.username}</p>
            </div>
        </nav>
        <!-- Side bar -->
        <input type="checkbox" id="toggle" />
        <label for="toggle" class="side-toggle">
            <span class="fas fa-bars"></span>
        </label>

        <div class="sidebar">
            <div class="sidebar-menu">
                <span class="fas fa-clipboard-list">
                    <p>Overview</p>
                </span>
            </div>
            <div class="sidebar-menu">
                <span class="fas fa-users"> <p>Teams</p> </span>
            </div>
            <div class="sidebar-menu">
                <span class="fas fa-credit-card"> <p>Payment</p> </span>
            </div>
            <div class="sidebar-menu">
                <span class="fas fa-chart-line"> <p>Statistic</p> </span>
            </div>
            <div class="sidebar-menu">
                <span class="fas fa-id-card"> <p>Contact</p> </span>
            </div>
            <div class="sidebar-menu">
                <span class="fas fa-cog"> <p>Setting</p> </span>
            </div>
        </div>

        <!-- main board -->
        <main>
            <div class="dashboard-container">
                <!-- 2 cards bottom -->
                <div class="card detail">
                    <div class="detail-header">
                        <h2>All Orders</h2>
                    </div>
                    <table>
                        <tr>
                                <th>ID #</th>
                                <th>Name</th>
                                <th>Status</th>
                                <th>Role</th>
                                <th>Email</th>
                                <th>Change</th>

                        </tr> 
                        <c:forEach var="List" items="${listMore}">
                                <tr>
                                    <td>#${List.getStaffId()}</td>
                                    <td>${List.getUseNameS()}</td>
                                    <td>
                                        <span class="status confirmed">
                                            <i class="fas fa-circle"> ON</i>
                                        </span>
                                    </td>
                                    <td>${List.getRole()}</td>
                                    <td>${List.getEmail()}</td>
                                    <td>
                                        
                                        <a href="./changeStaffController?staffId=${List.getStaffId()}">
                                            <button type="submit">Change </button>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                    </table>
                </div>
            </div>
        </main>
    </body>
</html>