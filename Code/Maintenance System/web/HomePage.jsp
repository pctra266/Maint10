<%-- 
    Document   : ComponentWarehouse
    Created on : Jan 17, 2025, 8:24:34 PM
    Author     : ADMIN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page </title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        
        
        <style>
            .logo-img {
            height: 50px; 
            width: auto; 
        }
        </style>
        
    </head>

    <body class="d-flex flex-column">
        <div class="wrapper">
            <div class="main">
                
                <nav class="navbar navbar-expand navbar-light navbar-bg">
                    <a href="Home" class="navbar-brand">
                            <img src="${pageContext.request.contextPath}/img/logo/logoText2.png" alt="Main10 Logo" class="logo-img">
                        </a>

                    <ul class="navbar-nav d-none d-lg-flex"> <!-- start ul 1 -->
                        <li class="nav-item">
                                <a class="nav-link d-flex align-items-center" href="#">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-home align-middle me-1">
                                        <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
                                        <polyline points="9 22 9 12 15 12 15 22"></polyline>
                                    </svg>
                                    Home
                                </a>
                        </li>
                        <li class="nav-item ms-1">
                                <a class="nav-link d-flex align-items-center" href="#"  >
                                   <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-award align-middle">
                                   <circle cx="12" cy="8" r="7"></circle>
                                   <polyline points="8.21 13.89 7 23 12 20 17 23 15.79 13.88"></polyline>
                                   </svg>
                                About
                            </a>
                        </li>
                        <li class="nav-item ms-1">
                                <a class="nav-link d-flex align-items-center" href="#"  >
                                 <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-book align-middle">
                                 <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"></path>
                                 <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"></path>
                                 </svg>
                                Blog
                            </a>
                        </li>
<!--                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="megaDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Look Up Online
                            </a>
                            <div class="dropdown-menu dropdown-menu-start dropdown-mega" aria-labelledby="megaDropdown">
                                <div class="d-md-flex align-items-start justify-content-start">
                                    <div class="dropdown-mega-list">
                                        <a class="dropdown-item" href="LookUpOnline.jsp">Warranty Card</a>
                                        <a class="dropdown-item" href="#">Product</a>
                                    </div>

                                </div>
                            </div>
                        </li>-->
                    </ul> <!-- end ul 1 -->
                    <ul class="navbar-nav navbar-align"><!-- start ul2 -->
                        <div class="navbar-collapse collapse">
                            <li class="navbar-nav navbar-align">
                                <c:if test="${not empty sessionScope.staff or not empty sessionScope.customer}">
                                    <a href="#">Back to dashboard</a>
                                </c:if>
                            </li>
                            <li class="navbar-nav navbar-align">
                                <c:if test="${empty sessionScope.staff and empty sessionScope.customer}">
                                    <a href="LoginForm.jsp">Login</a>
                                </c:if>
                            </li>
                            <li class="navbar-nav navbar-align">
                                <c:if test="${not empty sessionScope.staff or not empty sessionScope.customer}">
                                    <a href="logout">Logout</a>
                                </c:if>
                            </li>
                        </div>
                    </ul><!-- end ul2 -->
                </nav>
                        
                  <main style="background-image: url('${pageContext.request.contextPath}/img/backgrounds/bg1.jpg'); background-size: cover; background-position: center; background-attachment: fixed;">
                      <div style="height: 40%" class="bg-primary bg-opacity-50  rounded shadow-lg p-5">
                        <div class="container  ">
                            <div>
                                <div style="padding: 5% 1% 1% 1%" class="text-center">
                                    <h1 style="font-size: 60px; color: white; 
                                         text-shadow: -2px -2px 0 rgba(0, 0, 0, 0.25),  
                                         2px -2px 0 rgba(0, 0, 0, 0.25),
                                         -2px 2px 0 rgba(0, 0, 0, 0.25),
                                         2px 2px 0 rgba(0, 0, 0, 0.25);"  
                                        class="fw-bolder" >
                                        Warranty information lookup
                                    </h1>
                                    <h6 style="font-size: 20px; color: white; 
                                         text-shadow: -2px -2px 0 rgba(0, 0, 0, 0.25),  
                                         2px -2px 0 rgba(0, 0, 0, 0.25),
                                         -2px 2px 0 rgba(0, 0, 0, 0.25),
                                         2px 2px 0 rgba(0, 0, 0, 0.25);"
                                         class="fw-bold" >Fill in your information to view detailed warranty information for your product
                                    </h6>
                                </div>
                                <div>
                                    
                                    <form > <!-- form here -->
                                        
                                        <div style="height: 55px;" class="input-group mx-auto w-50">
                                            <div class="position-absolute bg-secondary opacity-25 rounded-3"
                                                 style="top: -10px; left: -10px; right: -10px; bottom: -10px; z-index: 0;">
                                            </div>
                                            <input type="search" class="form-control" placeholder="Information code">
                                            <div class="input-group-append">
                                                <button style="height: 55px" class="btn btn-primary" type="submit">Search</button>
                                            </div>
                                        </div>
                                        <div class="mx-auto text-center">
                                        <div style="margin-top: 15px" >
                                            <label class="form-check form-check-inline">
                                                <input checked="" class="form-check-input" type="radio" name="searchType" value="">
                                                <span style=" color: white; 
                                         text-shadow: -2px -2px 0 rgba(0, 0, 0, 0.15),  
                                         2px -2px 0 rgba(0, 0, 0, 0.15),
                                         -2px 2px 0 rgba(0, 0, 0, 0.15),
                                         2px 2px 0 rgba(0, 0, 0, 0.15);" class="form-check-label fs-6">
                                                    Search by phone number
                                                </span>
                                            </label>
                                            <label class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="searchType" value="">
                                                <span style=" color: white; 
                                         text-shadow: -2px -2px 0 rgba(0, 0, 0, 0.15),  
                                         2px -2px 0 rgba(0, 0, 0, 0.15),
                                         -2px 2px 0 rgba(0, 0, 0, 0.15),
                                         2px 2px 0 rgba(0, 0, 0, 0.15);" class="form-check-label fs-6">
                                                    Search by email
                                                </span>
                                            </label>
                                            <label class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="searchType" value="">
                                                <span style=" color: white; 
                                         text-shadow: -2px -2px 0 rgba(0, 0, 0, 0.15),  
                                         2px -2px 0 rgba(0, 0, 0, 0.15),
                                         -2px 2px 0 rgba(0, 0, 0, 0.15),
                                         2px 2px 0 rgba(0, 0, 0, 0.15);" class="form-check-label fs-6">
                                                    Search by product code
                                                </span>
                                            </label>
                                        </div>
                                            </div>
                                           
                                    </form> <!-- end form -->
                                </div>
                                
                        </div>
                    </div>
                        </div> <!-- end div 1 -->
                       
                       
                        <div class="bg-white" style="margin: none">
                        <h2>Content Section</h2>
                        <p>The morning sun cast a golden glow over the quiet town, where birds chirped lazily on telephone wires. A gentle breeze rustled the leaves of the old oak tree in the park, sending a few drifting to the ground. Nearby, a child laughed as they chased a stray balloon, their tiny feet pattering against the cobblestone path. In the distance, the aroma of freshly baked bread wafted from a small bakery, luring in early customers. Life moved at its own slow, steady pace here, untouched by the rush of the outside world.</p>
                        <p>The morning sun cast a golden glow over the quiet town, where birds chirped lazily on telephone wires. A gentle breeze rustled the leaves of the old oak tree in the park, sending a few drifting to the ground. Nearby, a child laughed as they chased a stray balloon, their tiny feet pattering against the cobblestone path. In the distance, the aroma of freshly baked bread wafted from a small bakery, luring in early customers. Life moved at its own slow, steady pace here, untouched by the rush of the outside world.</p>
                        <p>The morning sun cast a golden glow over the quiet town, where birds chirped lazily on telephone wires. A gentle breeze rustled the leaves of the old oak tree in the park, sending a few drifting to the ground. Nearby, a child laughed as they chased a stray balloon, their tiny feet pattering against the cobblestone path. In the distance, the aroma of freshly baked bread wafted from a small bakery, luring in early customers. Life moved at its own slow, steady pace here, untouched by the rush of the outside world.</p>
                        <p>The morning sun cast a golden glow over the quiet town, where birds chirped lazily on telephone wires. A gentle breeze rustled the leaves of the old oak tree in the park, sending a few drifting to the ground. Nearby, a child laughed as they chased a stray balloon, their tiny feet pattering against the cobblestone path. In the distance, the aroma of freshly baked bread wafted from a small bakery, luring in early customers. Life moved at its own slow, steady pace here, untouched by the rush of the outside world.</p>
                        The morning sun cast a golden glow over the quiet town, where birds chirped lazily on telephone wires. A gentle breeze rustled the leaves of the old oak tree in the park, sending a few drifting to the ground. Nearby, a child laughed as they chased a stray balloon, their tiny feet pattering against the cobblestone path. In the distance, the aroma of freshly baked bread wafted from a small bakery, luring in early customers. Life moved at its own slow, steady pace here, untouched by the rush of the outside world.
                    </div><!-- end div 2 -->
                    
                    <div class="bg-primary bg-opacity-25" >
                        <div class="container ">
                            <div>
                                <div class="text-center" >
                                    <h1  >Warranty information lookup</h1>
                                    <h6 >Fill in your information to view detailed warranty information for your product</h6>
                                </div>
                                <div>
                                    
                                    <form > <!-- form here -->
                                        <div class="input-group mx-auto">
                                            <input type="search" class="form-control" placeholder="Information code">
                                            <div class="input-group-append">
                                                <button class="btn btn-primary" type="submit">Search</button>
                                            </div>
                                        </div>
                                        <div style="margin-top: 15px" >
                                            <label class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="searchType" value="">
                                                <span class="form-check-label">
                                                    Search by phone number
                                                </span>
                                            </label>
                                            <label class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="searchType" value="">
                                                <span class="form-check-label">
                                                    Search by email
                                                </span>
                                            </label>
                                            <label class="form-check form-check-inline">
                                                <input class="form-check-input" type="radio" name="searchType" value="">
                                                <span class="form-check-label">
                                                    Search by product code
                                                </span>
                                            </label>
                                        </div>
                                    </form> <!-- end form -->
                                </div>
                        </div>
                    </div>
                        </div> <!-- end div 3 -->
                    
                   <div class="bg-white">
                        <h2>Content Section</h2>
                        <p>The morning sun cast a golden glow over the quiet town, where birds chirped lazily on telephone wires. A gentle breeze rustled the leaves of the old oak tree in the park, sending a few drifting to the ground. Nearby, a child laughed as they chased a stray balloon, their tiny feet pattering against the cobblestone path. In the distance, the aroma of freshly baked bread wafted from a small bakery, luring in early customers. Life moved at its own slow, steady pace here, untouched by the rush of the outside world.</p>
                        <p>The morning sun cast a golden glow over the quiet town, where birds chirped lazily on telephone wires. A gentle breeze rustled the leaves of the old oak tree in the park, sending a few drifting to the ground. Nearby, a child laughed as they chased a stray balloon, their tiny feet pattering against the cobblestone path. In the distance, the aroma of freshly baked bread wafted from a small bakery, luring in early customers. Life moved at its own slow, steady pace here, untouched by the rush of the outside world.</p>
                        <p>The morning sun cast a golden glow over the quiet town, where birds chirped lazily on telephone wires. A gentle breeze rustled the leaves of the old oak tree in the park, sending a few drifting to the ground. Nearby, a child laughed as they chased a stray balloon, their tiny feet pattering against the cobblestone path. In the distance, the aroma of freshly baked bread wafted from a small bakery, luring in early customers. Life moved at its own slow, steady pace here, untouched by the rush of the outside world.</p>
                        <p>The morning sun cast a golden glow over the quiet town, where birds chirped lazily on telephone wires. A gentle breeze rustled the leaves of the old oak tree in the park, sending a few drifting to the ground. Nearby, a child laughed as they chased a stray balloon, their tiny feet pattering against the cobblestone path. In the distance, the aroma of freshly baked bread wafted from a small bakery, luring in early customers. Life moved at its own slow, steady pace here, untouched by the rush of the outside world.</p>
                        The morning sun cast a golden glow over the quiet town, where birds chirped lazily on telephone wires. A gentle breeze rustled the leaves of the old oak tree in the park, sending a few drifting to the ground. Nearby, a child laughed as they chased a stray balloon, their tiny feet pattering against the cobblestone path. In the distance, the aroma of freshly baked bread wafted from a small bakery, luring in early customers. Life moved at its own slow, steady pace here, untouched by the rush of the outside world.
                    </div><!-- end div 4 -->
                </main>
                     <footer class="footer bg-white text-white text-center py-3 mt-auto">
                         <div class="container-fluid">
                             <div class="row text-muted">
                                 <div class="col-6 text-start">
                                     <p class="mb-0">
                                         <a href="#" target="_blank" class="text-muted"><strong>Main10</strong></a> ©
                                     </p>
                                 </div>
                                 <div class="col-6 text-end">
                                     <ul class="list-inline">
                                         <li class="list-inline-item">
                                             <a class="text-muted" href="#">Support</a>
                                         </li>
                                     </ul>
                                 </div>
                             </div>
                         </div>
                     </footer>
            </div>
        </div>

        <script src="js/app.js"></script>

    </body>

</html>
