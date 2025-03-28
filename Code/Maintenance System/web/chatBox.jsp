<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chat Box</title>

        <!-- Font Awesome để hiển thị icon -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

        <script>
            var socket;

            function connectWebSocket() {
                socket = new WebSocket("ws://" + window.location.host + "/MaintenanceSystem/chatRoomServer");

                socket.onopen = function () {
                    console.log("✅ WebSocket đã kết nối.");
                };

                socket.onmessage = function (event) {
                    var chatMessages = document.getElementById("chatMessages");
                    var newMessage = document.createElement("div");
                    newMessage.textContent = event.data;
                    newMessage.style.padding = "5px";
                    newMessage.style.borderBottom = "1px solid #ddd";
                    chatMessages.appendChild(newMessage);
                    chatMessages.scrollTop = chatMessages.scrollHeight; // Cuộn xuống tin nhắn mới
                };

                socket.onclose = function () {
                    console.log("❌ WebSocket đã đóng.");
                    setTimeout(connectWebSocket, 3000); // Tự động kết nối lại sau 3 giây
                };

                socket.onerror = function (error) {
                    console.log("⚠️ Lỗi WebSocket:", error);
                };
            }

            function sendMessage() {
                var messageInput = document.getElementById("messageInput");
                var message = messageInput.value.trim();

                if (message !== "" && socket && socket.readyState === WebSocket.OPEN) {
                    socket.send(message);
                    messageInput.value = ""; // Xóa input sau khi gửi
                } else {
                    console.log("❌ WebSocket chưa kết nối hoặc tin nhắn rỗng!");
                }
            }

            function toggleChat() {
                var chatContainer = document.getElementById("chatContainer");

                if (chatContainer.style.display === "none" || chatContainer.style.display === "") {
                    chatContainer.style.display = "block";
                } else {
                    chatContainer.style.display = "none";
                }
            }


            window.onload = function () {
                connectWebSocket();
            };

            document.addEventListener("DOMContentLoaded", function () {
                document.getElementById("messageInput").addEventListener("keypress", function (event) {
                    if (event.key === "Enter") {
                        sendMessage();
                    }
                });
            });
        </script>

        <style>
            body {
                font-family: Arial, sans-serif;
            }

            /* Nút chat icon */
            #chatHeader {
                position: fixed;
                bottom: 20px;
                right: 20px;
                width: 60px;
                height: 60px;
                background: #007bff;
                color: white;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 24px;
                cursor: pointer;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                transition: background 0.3s, transform 0.2s;
            }

            #chatHeader:hover {
                background: #0056b3;
                transform: scale(1.1);
            }

            /* Hộp chat */
            #chatContainer {
                position: fixed;
                bottom: 90px;
                right: 20px;
                width: 320px;
                background: white;
                border: 1px solid #ccc;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                overflow: hidden;
                display: none;
            }

            #chatBox {
                height: 300px;
                overflow-y: auto;
                padding: 10px;
                background: #f9f9f9;
            }

            #chatMessages {
                height: 240px;
                overflow-y: auto;
                padding: 5px;
            }

            #messageInput {
                width: 75%;
                padding: 5px;
                border: none;
                outline: none;
            }

            #sendButton {
                width: 20%;
                background: #007bff;
                color: white;
                border: none;
                cursor: pointer;
            }

            #chatFooter {
                display: flex;
                border-top: 1px solid #ccc;
            }

            #loginPrompt {
                text-align: center;
                padding: 10px;
                background: #f8f9fa;
                font-size: 14px;
                color: #555;
            }

            #loginButton {
                display: block;
                width: 80%;
                margin: 10px auto;
                padding: 8px;
                background: #28a745;
                color: white;
                text-align: center;
                text-decoration: none;
                border-radius: 5px;
            }

            /* Hiệu ứng hover */
            #sendButton:hover, #loginButton:hover {
                opacity: 0.8;
            }
        </style>
    </head>
    <body>

        <c:set var="isLoggedIn" value="false" />
        <c:if test="${not empty sessionScope.staff or not empty sessionScope.customer}">
            <c:set var="isLoggedIn" value="true" />
        </c:if>

        <!-- Nút chat icon -->
        <div id="chatHeader" onclick="toggleChat()">
            <i class="fas fa-comments"></i>
        </div>

        <!-- Khung chat -->
        <div id="chatContainer">
            <c:choose>
                <c:when test="${isLoggedIn}">
                    <div id="chatBox">
                        <div id="chatMessages"></div>
                        <div id="chatFooter">
                            <input type="text" id="messageInput" placeholder="Nhập tin nhắn...">
                            <button id="sendButton" onclick="sendMessage()">Gửi</button>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="loginPrompt">
                        Hãy đăng nhập để có trải nghiệm tốt hơn!
                        <a href="LoginForm.jsp" id="loginButton">Đăng nhập</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

    </body>
</html>
