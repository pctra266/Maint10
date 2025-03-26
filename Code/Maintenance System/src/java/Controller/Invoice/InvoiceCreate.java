/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Invoice;

import DAO.InvoiceDAO;
import DAO.StaffDAO;
import DAO.WarrantyCardDAO;
import DAO.WarrantyCardProcessDAO;
import Model.Invoice;
import Model.Staff;
import Model.WarrantyCard;
import Model.WarrantyCardProcess;
import Utils.FormatUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "InvoiceCreate", urlPatterns = {"/Invoice/Create"})
public class InvoiceCreate extends HttpServlet {

    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    private final StaffDAO staffDAO = new StaffDAO();
    private final WarrantyCardProcessDAO wcpDAO = new WarrantyCardProcessDAO();
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();

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
        String idPara = request.getParameter("ID");
        Integer warrantyCardId = FormatUtils.tryParseInt(idPara);

        if (warrantyCardId == null || warrantyCardDAO.getWarrantyCardById(warrantyCardId) == null) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard");
            return;
        }
        if (!checkRightHanderlerId(request, response, warrantyCardId)) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard/Detail?canChange=false&ID=" + warrantyCardId);
            return;
        }
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        WarrantyCard wc = warrantyCardDAO.getWarrantyCardById(warrantyCardId);
        WarrantyCardProcess latestProcess = wcpDAO.getLatestProcessByWarrantyCardId(warrantyCardId);
        if(latestProcess==null || latestProcess.getAction().equals("completed") || latestProcess.getAction().equals("cancel") ){
           response.sendRedirect(request.getContextPath() + "/WarrantyCard");
            return; 
        }

        int price = warrantyCardDAO.getPriceOfWarrantyCard(warrantyCardId);
        request.setAttribute("price", price);
        request.setAttribute("warrantyCard", wc);
        request.getRequestDispatcher("/views/Invoice/InvoiceCreate.jsp").forward(request, response);
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
        //
        String warrantyCardIdParam = request.getParameter("ID");
        Integer warrantyCardId = FormatUtils.tryParseInt(warrantyCardIdParam);
        String dueDateParam = request.getParameter("dueDate");
        if (warrantyCardId == null || warrantyCardDAO.getWarrantyCardById(warrantyCardId) == null) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard");
            return;
        }
        if (!checkRightHanderlerId(request, response, warrantyCardId)) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard/Detail?canChange=false&ID=" + warrantyCardId);
            return;
        }
        WarrantyCardProcess wcp = wcpDAO.getLatestProcessByWarrantyCardId(warrantyCardId);

        if (wcp == null || !wcp.getAction().equals("fixed")) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard/Detail?ID="+warrantyCardId+"&invoiceCreate=false");
            return;
        }

        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        WarrantyCard wc = warrantyCardDAO.getWarrantyCardById(warrantyCardId);
        String invoiceType = request.getParameter("invoiceType");

        // Integer receivedBy =;
        Integer customerId = wc.getCustomerID();
        Date dueDate = FormatUtils.parseDate(dueDateParam);
        int price = warrantyCardDAO.getPriceOfWarrantyCard(warrantyCardId);
        request.setAttribute("price", price);
        request.setAttribute("warrantyCard", wc);

        if (dueDate.before(new Date())) {
            request.setAttribute("updateAlert0", "Duedate need after today.");
            request.getRequestDispatcher("/views/Invoice/InvoiceCreate.jsp").forward(request, response);
        }

        // Kiểm tra dữ liệu đầu vào
        if (invoiceType == null || (!"RepairContractorToTechnician".equals(invoiceType) && !"TechnicianToCustomer".equals(invoiceType))) {
            request.setAttribute("updateAlert0", "Invalid invoice type.");
            request.getRequestDispatcher("/views/Invoice/InvoiceCreate.jsp").forward(request, response);
        }

        if ("TechnicianToCustomer".equals(invoiceType) && customerId == null) {
            request.setAttribute("updateAlert0", "Customer ID is required for this invoice type.");
            request.getRequestDispatcher("/views/Invoice/InvoiceCreate.jsp").forward(request, response);
        }

        // Tạo mã hóa đơn tự động (ví dụ: INV-YYYYMMDD-<random>)
        String invoiceNumber = "INV-" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-" + (int) (Math.random() * 10000);

        // Tạo đối tượng Invoice
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setInvoiceType(invoiceType);
        invoice.setWarrantyCardID(warrantyCardId);
        invoice.setAmount(price);
        invoice.setDueDate(dueDate);
        invoice.setStatus("pending");
        invoice.setCreatedBy(staff.getStaffID());
        //invoice.setReceivedBy("RepairContractorToTechnician".equals(invoiceType) ? receivedBy : null);
        invoice.setCustomerID("TechnicianToCustomer".equals(invoiceType) ? customerId : null);

        // Lưu invoice vào cơ sở dữ liệu
        boolean success = invoiceDAO.addInvoice(invoice);
        if (success) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard/Detail?invoice=true&ID=" + warrantyCardId);
        } else {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard/Detail?invoice=false&ID=" + warrantyCardId);
        }
        //
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

        private boolean checkRightHanderlerId(HttpServletRequest request, HttpServletResponse response, int warrantyCardId) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("componentWarehouseFrom", request.getContextPath() + request.getServletPath() + "?ID=" + warrantyCardId);
        Staff staff = (Staff) session.getAttribute("staff");
        WarrantyCard card = warrantyCardDAO.getWarrantyCardById(warrantyCardId);
        System.out.println(staff.getStaffID()+" "+card.getHandlerID());
        return !(staff == null || card.getHandlerID() != staff.getStaffID());
    }
}
