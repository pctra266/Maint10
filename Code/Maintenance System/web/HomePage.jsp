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
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="Responsive Admin &amp; Dashboard Template based on Bootstrap 5">
        <meta name="author" content="AdminKit">
        <meta name="keywords" content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">

        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link rel="shortcut icon" href="img/icons/icon-48x48.png" />

        <link rel="canonical" href="https://demo-basic.adminkit.io/" />

        <title>Main10</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
             .omnitrix-button {
            width: 100px;
            height: 100px;
            background: radial-gradient(circle, #00aaff 40%, #444 60%);
            border: 4px solid black;
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            cursor: pointer;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .omnitrix-button::before {
            content: '';
            width: 10%;
            height: 10%;
            background: radial-gradient(circle, #00aaff 50%, transparent 50%);
            clip-path: polygon(50% 0%, 100% 50%, 50% 100%, 0% 50%);
            position: absolute;
        }
        </style>
    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main style="content">
                    <div><h1 class="text-center">Click and wait to become Main10</h1></div>
                    <div style="display: flex; justify-content: center; align-content: center" class="text-center">
                        
                    
                    <div style=""></div>
                        ${check}
                    <button class="omnitrix-button" onclick="playMusic()">Main10</button>
                    <div id="player" style="display: none;"></div>
                    </div>
                    

                </main>
                <jsp:include page="/includes/footer.jsp" />

            </div>

        </div>
                   <script>
        function playMusic() {
            let playerDiv = document.getElementById("player");
            playerDiv.innerHTML = '<iframe width="560" height="315" src="https://www.youtube.com/embed/_iIUGEOreiw?autoplay=1" allow="autoplay; encrypted-media" allowfullscreen></iframe>';
        }
    </script>

        <script src="js/app.js"></script>

    </body>

</html>
