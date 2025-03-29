/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatBox;

import DAO.ChatMessagesDAO;
import Model.ChatMessages;
import Model.Customer;
import Model.Staff;
import jakarta.websocket.Session;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author PC
 */
@ServerEndpoint(value = "/chatRoomServer", configurator = ChatServerConfigurator.class)
public class ChatServerEndpoint {

    private static final Set<Session> customers = Collections.synchronizedSet(new HashSet<>());
    private static final Set<Session> staffs = Collections.synchronizedSet(new HashSet<>());
    private static final Map<Session, Session> chatPairs = Collections.synchronizedMap(new HashMap<>());
    private static final Map<Session, String> userNames = Collections.synchronizedMap(new HashMap<>());

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get("httpSession");
        boolean isStaff = httpSession != null && httpSession.getAttribute("staff") != null;

        if (isStaff) {
            Staff staff = (Staff) httpSession.getAttribute("staff");
            String usernameS = staff.getUsernameS();
            int staffId = staff.getStaffID();
            int roleID = staff.getRole();

            if (roleID != 1 && roleID != 2 && roleID != 5) {
                session.close(); 
                return;
            }
            userNames.put(session, usernameS);
            staffs.add(session);

            session.getUserProperties().put("type", "staff");
            session.getUserProperties().put("id", staffId);

            session.getBasicRemote().sendText("System: Bạn đã đăng nhập với tư cách " + usernameS);
            matchWaitingCustomer();
        } else {
            Customer customer = httpSession != null ? (Customer) httpSession.getAttribute("customer") : null;
            String customerName;
            int customerId;
            if (customer != null) {
                customerName = customer.getUsernameC();
                customerId = customer.getCustomerID();

            } else {
                customerName = "Customer" + randomCustomerID(4);
                customerId = Integer.parseInt(randomCustomerID(6));
            }
            userNames.put(session, customerName);
            customers.add(session);

            session.getUserProperties().put("type", "customer");
            session.getUserProperties().put("id", customerId);

            session.getBasicRemote().sendText("System: Bạn đã kết nối. Đang tìm nhân viên CSKH...");
            matchCustomerWithStaff(session);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        ChatMessagesDAO chatmessagesDao = new ChatMessagesDAO();
        Session receiverSession = chatPairs.get(session);
        if (receiverSession != null && receiverSession.isOpen()) {
            try {

                String senderType = session.getUserProperties().get("type").toString();
                int senderID = (int) session.getUserProperties().get("id");
                String senderName = userNames.get(session);

                String receiverType = receiverSession.getUserProperties().get("type").toString();
                int receiverID = (int) receiverSession.getUserProperties().get("id");

                String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());

                receiverSession.getBasicRemote().sendText("[" + timestamp + "] " + senderName + ": " + message);
                session.getBasicRemote().sendText("[" + timestamp + "] You: " + message);
                System.out.println("SenderID: " + senderID + ", SenderType: " + senderType);
                System.out.println("ReceiverID: " + receiverID + ", ReceiverType: " + receiverType);
                System.out.println("Message: " + message);
                chatmessagesDao.addMessages(senderID, senderType, receiverID, receiverType, message);
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }

    private void matchWaitingCustomer() throws IOException {
        synchronized (customers) {
            if (!customers.isEmpty() && !staffs.isEmpty()) {
                Session customer = customers.iterator().next();
                Session staff = staffs.iterator().next();

                customers.remove(customer);
                staffs.remove(staff);

                chatPairs.put(customer, staff);
                chatPairs.put(staff, customer);

                String staffName = userNames.get(staff);
                String customerName = userNames.get(customer);

                customer.getBasicRemote().sendText("System: Bạn đã được ghép với nhân viên " + staffName);
                staff.getBasicRemote().sendText("System: Bạn đang trò chuyện với khách hàng " + customerName);
            }
        }
    }

    private void matchCustomerWithStaff(Session customerSession) throws IOException {
        synchronized (staffs) {
            if (!staffs.isEmpty()) {
                Session staff = staffs.iterator().next();

                staffs.remove(staff);

                chatPairs.put(customerSession, staff);
                chatPairs.put(staff, customerSession);

                String staffName = userNames.get(staff);
                String customerName = userNames.get(customerSession);

                customerSession.getBasicRemote().sendText("System: Bạn đã được ghép với nhân viên " + staffName);
                staff.getBasicRemote().sendText("System: Bạn đang trò chuyện với khách hàng " + customerName);
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        String userType = (String) session.getUserProperties().get("type");

        synchronized (customers) {
            synchronized (staffs) {

                Session pairedSession = chatPairs.remove(session);
                if (pairedSession != null) {
                    chatPairs.remove(pairedSession);
                    try {
                        pairedSession.getBasicRemote().sendText("System: Người trò chuyện với bạn đã rời đi.");

                        if ("staff".equals(pairedSession.getUserProperties().get("type"))) {
                            staffs.add(pairedSession);
                            pairedSession.getBasicRemote().sendText("System: Bạn đang chờ khách hàng mới...");
                        }
                    } catch (IOException e) {
                        System.out.println("Lỗi khi gửi thông báo ngắt kết nối: " + e.getMessage());
                    }
                }

                if ("customer".equals(userType)) {
                    customers.remove(session);
                } else if ("staff".equals(userType)) {
                    staffs.remove(session);
                }
            }
        }

        userNames.remove(session);
        System.out.println("Phiên đã đóng: " + session.getId());
    }

    // Hma lran fpm ID
    private String randomCustomerID(int length) {
        String character = "0123456789";
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(character.length());
            password.append(character.charAt(index));
        }
        return password.toString();

    }
}
