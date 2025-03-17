/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package VnPay;

import DAO.InvoiceDAO;
import DAO.PaymentDAO;
import DAO.WarrantyCardDAO;
import DAO.WarrantyCardProcessDAO;
import Model.Invoice;
import Model.Payment;
import Model.Staff;
import Model.WarrantyCard;
import Model.WarrantyCardProcess;
import java.io.IOException;
import Utils.FormatUtils;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="PaymentReturn", urlPatterns={"/Payment/Return"})
public class PaymentReturn extends HttpServlet {
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final PaymentDAO paymentDAO = new PaymentDAO();
    private final WarrantyCardDAO wcDAO = new WarrantyCardDAO();
    private final WarrantyCardProcessDAO wcpDao = new WarrantyCardProcessDAO();
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String amountParam = request.getParameter("vnp_Amount");
        Integer amount = FormatUtils.tryParseInt(amountParam);
        String vnp_PayDate = request.getParameter("vnp_PayDate");
        String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");
        String[] orderInfo = vnp_OrderInfo.split(" ");
        String invoiceNumber = orderInfo[1];
        Invoice invoice = invoiceDAO.getInvoiceByCode(invoiceNumber);
        //format date 
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = formatter.parse(vnp_PayDate);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean payStatus = false;
        String status = request.getParameter("vnp_ResponseCode");
        //HandlerID
         HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            request.setAttribute("updateAlert0", "You must be logged in to perform this action.");
            processRequest(request, response);
            return;
        }
        
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setPaymentMethod("bank_transfer");
        payment.setPaymentDate(date);
        payment.setInvoiceID(invoice.getInvoiceID());
        if("00".equals(status) || "07".equals(status)) payment.setStatus("complete");
        else payment.setStatus("fail");
        boolean add = paymentDAO.addPayment(payment);
        if(add) {
            if("00".equals(status) || "07".equals(status)){
                invoice.setStatus("paid");
                payStatus=true;
                WarrantyCard wc = wcDAO.getWarrantyCardById(invoice.getWarrantyCardID());
                wc.setWarrantyStatus("completed");
                wcDAO.updateWarrantyCard(wc);
                 WarrantyCardProcess newProcess = new WarrantyCardProcess();
                            newProcess.setWarrantyCardID(wc.getWarrantyCardID());
                            newProcess.setHandlerID(staff.getStaffID());
                            newProcess.setAction("completed");
                            boolean success = wcpDao.addWarrantyCardProcess(newProcess);
                            
            }
            invoiceDAO.updateInvoiceStatus(invoice.getInvoiceID(), invoice.getStatus());
        }
        request.setAttribute("cardId", invoice.getWarrantyCardID());
        request.setAttribute("status", payStatus);
        request.getRequestDispatcher("/views/VnPay/vnpay_return.jsp").forward(request, response);
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
