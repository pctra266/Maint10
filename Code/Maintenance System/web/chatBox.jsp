<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chat Box</title>

    <script>
        var socket;

        function connectWebSocket() {
            socket = new WebSocket("ws://" + window.location.host + "/MaintenanceSystem/chatRoomServer");

            socket.onopen = function () {
                console.log("WebSocket đã kết nối.");
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
                console.log("Lỗi WebSocket:", error);
            };
        }

        function sendMessage() {
            var messageInput = document.getElementById("messageInput");
            var message = messageInput.value.trim();

            if (message !== "" && socket && socket.readyState === WebSocket.OPEN) {
                socket.send(message);
                messageInput.value = ""; // Xóa input sau khi gửi
            } else {
                console.log("WebSocket chưa kết nối hoặc tin nhắn rỗng!");
            }
        }

        function toggleChat() {
            var chatBox = document.getElementById("chatBox");
            chatBox.style.display = (chatBox.style.display === "block") ? "none" : "block";

            if (chatBox.style.display === "block") {
                var chatMessages = document.getElementById("chatMessages");
                chatMessages.scrollTop = chatMessages.scrollHeight; // Cuộn xuống cuối tin nhắn khi mở
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
        body { font-family: Arial, sans-serif; }
        
        /* Hộp chat cố định */
        #chatContainer {
            position: fixed;
            bottom: 20px;
            right: 20px;
            width: 300px;
            background: white;
            border: 1px solid #ccc;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }

        #chatHeader {
            background: #007bff;
            color: white;
            padding: 10px;
            text-align: center;
            cursor: pointer;
            font-weight: bold;
        }

        #chatBox {
            display: none;
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

        /* Hiệu ứng hover */
        #chatHeader:hover, #sendButton:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>

    <c:set var="username" value="Khách" />
    <c:if test="${not empty sessionScope.staff}">
        <c:set var="username" value="${sessionScope.staff.usernameS}" />
    </c:if>
    <c:if test="${not empty sessionScope.customer}">
        <c:set var="username" value="${sessionScope.customer.usernameC}" />
    </c:if>

    <div id="chatContainer">
        <div id="chatHeader" onclick="toggleChat()">Chat với ${username}</div>
        <div id="chatBox">
            <div id="chatMessages"></div>
            <div id="chatFooter">
                <input type="text" id="messageInput" placeholder="Nhập tin nhắn...">
                <button id="sendButton" onclick="sendMessage()">Gửi</button>
            </div>
        </div>
    </div>

</body>
</html>
