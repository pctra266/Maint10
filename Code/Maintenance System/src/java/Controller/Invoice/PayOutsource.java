/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Invoice;

import DAO.InvoiceDAO;
import DAO.PaymentDAO;
import DAO.WarrantyCardDAO;
import DAO.WarrantyCardProcessDAO;
import Model.Invoice;
import Model.Payment;
import Model.Staff;
import Model.WarrantyCard;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="PayOutsource", urlPatterns={"/Invoice/PayOutsource"})
public class PayOutsource extends HttpServlet {
   
      private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    private final WarrantyCardProcessDAO wcpDao = new WarrantyCardProcessDAO();
    private final PaymentDAO paymentDAO = new PaymentDAO();

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            String invoiceIDParam = request.getParameter("invoiceID");
        String warrantyCardIDParam = request.getParameter("warrantyCardID");

        int invoiceID = Integer.parseInt(invoiceIDParam);
        int warrantyCardID = Integer.parseInt(warrantyCardIDParam);

        //HandlerID
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            request.setAttribute("updateAlert0", "You must be logged in to perform this action.");
            request.getRequestDispatcher("/Invoice/List?ID=" + warrantyCardID).forward(request, response);
            return;
        }
        // Kiểm tra invoice và warranty card
        Invoice invoice = invoiceDAO.getInvoiceById(invoiceID);
        WarrantyCard warrantyCard = warrantyCardDAO.getWarrantyCardById(warrantyCardID);

        if (invoice == null || warrantyCard == null || invoice.getWarrantyCardID() != warrantyCardID) {
            request.setAttribute("paymentSuccess", false);
            request.setAttribute("paymentMessage", "Invalid invoice or warranty card.");
            request.getRequestDispatcher("/Invoice/List?ID=" + warrantyCardID).forward(request, response);
            return;
        }

        // Kiểm tra trạng thái invoice
        if (!invoice.getStatus().equals("pending")) {
            request.setAttribute("paymentSuccess", false);
            request.setAttribute("paymentMessage", "Invoice is already paid or not pending.");
            request.getRequestDispatcher("/Invoice/List?ID=" + warrantyCardID).forward(request, response);
            return;
        }
        
        Payment payment = new Payment();
        payment.setAmount(invoice.getAmount());
        payment.setPaymentMethod("cash");
        payment.setPaymentDate(new Date());
        payment.setInvoiceID(invoice.getInvoiceID());
        payment.setStatus("complete");
        boolean add = paymentDAO.addPayment(payment);

        // Cập nhật trạng thái invoice thành "paid"
        invoice.setStatus("paid");
        boolean invoiceUpdated = invoiceDAO.updateInvoice(invoice);

        if (!invoiceUpdated) {
            request.setAttribute("paymentSuccess", false);
            request.setAttribute("paymentMessage", "Failed to update invoice status.");
            request.getRequestDispatcher("/Invoice/List?ID=" + warrantyCardID).forward(request, response);
        }
        else{
              request.setAttribute("paymentSuccess", true);
            request.setAttribute("paymentMessage", "Paid for outsource.");
            request.getRequestDispatcher("/Invoice/List?ID=" + warrantyCardID).forward(request, response);
        }
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
