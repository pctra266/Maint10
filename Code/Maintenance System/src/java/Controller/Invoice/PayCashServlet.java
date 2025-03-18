package Controller.Invoice;

import DAO.InvoiceDAO;
import DAO.PaymentDAO;
import DAO.WarrantyCardDAO;
import DAO.WarrantyCardProcessDAO;
import Model.Invoice;
import Model.Payment;
import Model.Staff;
import Model.WarrantyCard;
import Model.WarrantyCardProcess;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "PayCashServlet", urlPatterns = {"/Invoice/PayCash"})
public class PayCashServlet extends HttpServlet {

    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    private final WarrantyCardProcessDAO wcpDao = new WarrantyCardProcessDAO();
    private final PaymentDAO paymentDAO = new PaymentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
            return;
        }

        // Cập nhật CompletedDate trong WarrantyCard
        warrantyCard.setCompletedDate(new Date());
        warrantyCard.setWarrantyStatus("completed");
        boolean warrantyCardUpdated = warrantyCardDAO.updateWarrantyCard(warrantyCard);
        WarrantyCardProcess newProcess = new WarrantyCardProcess();
        newProcess.setWarrantyCardID(warrantyCard.getWarrantyCardID());
        newProcess.setHandlerID(staff.getStaffID());
        newProcess.setAction("completed");
        boolean success = wcpDao.addWarrantyCardProcess(newProcess);

        if (!warrantyCardUpdated) {
            request.setAttribute("paymentSuccess", false);
            request.setAttribute("paymentMessage", "Failed to update warranty card.");
            request.getRequestDispatcher("/Invoice/List?ID=" + warrantyCardID).forward(request, response);
            return;
        }

        // Redirect với thông báo thành công
        request.setAttribute("paymentSuccess", true);
        request.setAttribute("paymentMessage", "Cash payment processed successfully for Invoice #" + invoiceID);
        request.getRequestDispatcher("/Invoice/List?ID=" + warrantyCardID).forward(request, response);
    }
}
