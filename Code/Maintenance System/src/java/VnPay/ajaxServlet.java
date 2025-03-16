/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VnPay;

import Controller.WarrantyCard.WarrantyCardDetailServlet;
import DAO.InvoiceDAO;
import DAO.StaffDAO;
import DAO.WarrantyCardDAO;
import DAO.WarrantyCardProcessDAO;
import Model.Invoice;
import Model.Staff;
import Model.WarrantyCard;
import Model.WarrantyCardProcess;
import Utils.FormatUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.Instant;
import java.util.Date;

/**
 *
 * @author CTT VNPAY
 */
public class ajaxServlet extends HttpServlet {

    private final WarrantyCardDAO wcDAO = new WarrantyCardDAO();
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final WarrantyCardProcessDAO wcpDao = new WarrantyCardProcessDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = Integer.parseInt(req.getParameter("amount"));
        String bankCode = req.getParameter("bankCode");
        String warrantyCardIdParam = req.getParameter("ID");
        Integer warrantyCardId = FormatUtils.tryParseInt(warrantyCardIdParam);
        
        if (warrantyCardId == null || wcDAO.getWarrantyCardById(warrantyCardId) == null) {
            resp.sendRedirect(req.getContextPath() + "/404Page.jsp");
            return;
        }
                WarrantyCard wc = wcDAO.getWarrantyCardById(warrantyCardId);
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = Config.getIpAddress(req);

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Payment for: " + wc.getWarrantyCardCode() + " " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = req.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        com.google.gson.JsonObject job = new JsonObject();
        job.addProperty("code", "00");
        job.addProperty("message", "success");
        job.addProperty("data", paymentUrl);
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(job));
    }

    private void addInvoicc(HttpServletRequest req, HttpServletResponse resp, Integer warrantyCardId) throws IOException, ServletException {
        //

        if (warrantyCardId == null || wcDAO.getWarrantyCardById(warrantyCardId) == null) {
            resp.sendRedirect(req.getContextPath() + "/WarrantyCard");
            return;
        }

        WarrantyCardProcess wcp = wcpDao.getLatestProcessByWarrantyCardId(warrantyCardId);

        if (wcp == null || !wcp.getAction().equals("fixed")) {
            req.setAttribute("updateAlert0", "Product isn't fixed");
            resp.sendRedirect(req.getContextPath() + "/WarrantyCard");
            return;
        }

        HttpSession session = req.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        WarrantyCard wc = wcDAO.getWarrantyCardById(warrantyCardId);
        String invoiceType = req.getParameter("invoiceType");
        int amount = wcDAO.getPriceOfWarrantyCard(warrantyCardId);
        // Integer receivedBy =;
        Integer customerId = wc.getCustomerID();
        Date dueDate = Date.from(Instant.now().plusSeconds(3 * 24 * 60 * 3600));

        // Kiểm tra dữ liệu đầu vào
        if (invoiceType == null || (!"RepairContractorToTechnician".equals(invoiceType) && !"TechnicianToCustomer".equals(invoiceType))) {
            req.setAttribute("updateAlert0", "Invalid invoice type.");
            req.getRequestDispatcher("/views/WarrantyCard/WarrantyCardDetail.jsp").forward(req, resp);
        }

        if ("TechnicianToCustomer".equals(invoiceType) && customerId == null) {
            req.setAttribute("updateAlert0", "Customer ID is required for this invoice type.");
            req.getRequestDispatcher("/views/WarrantyCard/WarrantyCardDetail.jsp").forward(req, resp);
        }

        // Tạo mã hóa đơn tự động (ví dụ: INV-YYYYMMDD-<random>)
        String invoiceNumber = "INV-" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-" + (int) (Math.random() * 10000);

        // Tạo đối tượng Invoice
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setInvoiceType(invoiceType);
        invoice.setWarrantyCardID(warrantyCardId);
        invoice.setAmount(amount);
        invoice.setDueDate(dueDate);
        invoice.setStatus("pending");
        invoice.setCreatedBy(staff.getStaffID());
        //invoice.setReceivedBy("RepairContractorToTechnician".equals(invoiceType) ? receivedBy : null);
        invoice.setCustomerID("TechnicianToCustomer".equals(invoiceType) ? customerId : null);

        // Lưu invoice vào cơ sở dữ liệu
        boolean success = invoiceDAO.addInvoice(invoice);
        if (success) {
            req.setAttribute("updateAlert1", "Invoice created successfully!");
        } else {
            req.setAttribute("updateAlert0", "Failed to create invoice.");
            req.getRequestDispatcher("/views/WarrantyCard/WarrantyCardDetail.jsp").forward(req, resp);
        }
        //
    }

}
