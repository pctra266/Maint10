/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.WarrantyCard;

import Email.Email;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author sonNH
 */
public class SendWarrantyCard extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số từ form
        String warrantyCardCode = request.getParameter("warrantyCardCode");
        String customerEmail = request.getParameter("customerEmail");
        String customerName = request.getParameter("customerName");

        // Tạo tiêu đề email
        String subject = "Your Warranty Card " + warrantyCardCode;

        // Tạo nội dung email dạng HTML, có thể tùy chỉnh thêm thông tin phiếu bảo hành
        String htmlContent = "<h1>Warranty Card</h1>"
                + "<p>Dear " + customerName + "</p>"
                + "<p>Please find attached your warranty card with the code: <strong>"
                + warrantyCardCode + "</strong>.</p>"
                + "<p>Thank you for choosing our service.</p>";

        // Nếu bạn đã tạo file PDF của phiếu bảo hành, cung cấp đường dẫn đến file đó.
        // Ví dụ: file PDF được lưu tại server (chú ý: cần đảm bảo đường dẫn hợp lệ và quyền truy cập)
        String filePath = "C:/Users/sonNH/Downloads/WarrantyCard" + warrantyCardCode + ".pdf";

        // Tạo instance của Email util
        Email emailUtil = new Email();

        // Gửi email với nội dung HTML và đính kèm file PDF
        emailUtil.sendEmailWithHtmlAndAttachment(customerEmail, subject, htmlContent, filePath);

        request.setAttribute("successMessage", "Send To Customer Successfully");
        request.getRequestDispatcher("searchWarrantyCard.jsp").forward(request, response);

    }

}
