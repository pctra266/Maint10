/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.ChatBox;

import DAO.ChatMessagesDAO;
import Model.ChatMessages;
import Utils.FormatUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class ChatHistory extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChatHistory</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChatHistory at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ChatMessagesDAO chatMessagesDao = new ChatMessagesDAO();
        String sender = request.getParameter("sender");
        String receiver = request.getParameter("receiver");
        
        String senderType = request.getParameter("senderType");
        String receiverType = request.getParameter("receiverType");
        String sortBy = request.getParameter("sort");
        String sortOrder = request.getParameter("order");
        String pageSizeC = request.getParameter("page-size");
        String pageC = request.getParameter("page");

// Xử lý số trang và kích thước trang
        int page = (FormatUtils.tryParseInt(pageC) != null) ? FormatUtils.tryParseInt(pageC) : 1;
        int pageSize = (FormatUtils.tryParseInt(pageSizeC) != null) ? FormatUtils.tryParseInt(pageSizeC) : 5;
        int offset = (page - 1) * pageSize;

        int totalMessages = chatMessagesDao.getChatPage(senderType, receiverType, sender, receiver);
        int totalPages = (int) Math.ceil((double) totalMessages / pageSize);

        ArrayList<ChatMessages> listChat = chatMessagesDao.getMessagesByReceiverAndSender(
                senderType, receiverType, sender, receiver, sortBy, sortOrder, offset, pageSize
        );
        System.out.println("Total Messages: " + totalMessages);
        System.out.println("Total Pages: " + totalPages);
        System.out.println("Current Page: " + page);
        request.setAttribute("listChat", listChat);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("sender", sender);
        request.setAttribute("receiver", receiver);
       
        request.setAttribute("senderType", senderType);
        request.setAttribute("receiverType", receiverType);

        request.getRequestDispatcher("ChatHistory.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
