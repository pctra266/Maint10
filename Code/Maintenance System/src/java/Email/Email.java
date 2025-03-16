package Email;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import java.io.File;

public class Email {

    private String host = "smtp.gmail.com";
    private String from = "dohoanganh92004@gmail.com";
    private String password = "lcif ynyc arbe fzmh";

    public void sendEmail(String toEmail, String subject, String body) {
        // Thiết lập cấu hình SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Tạo session với thông tin xác thực
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Tạo email
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(body);

            // Gửi email
            Transport.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            System.out.println(e);
        }
    }

    // Phương thức gửi email với định dạng HTML và đính kèm tệp
    public void sendEmailWithHtmlAndAttachment(String toEmail, String subject, String htmlContent, String filePath) {
        // Thiết lập cấu hình SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Tạo session với thông tin xác thực
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Tạo email
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);

            // Tạo multipart để chứa nội dung HTML và tệp đính kèm
            Multipart multipart = new MimeMultipart();

            // Phần nội dung HTML
            BodyPart htmlBodyPart = new MimeBodyPart();
            htmlBodyPart.setContent(htmlContent, "text/html; charset=utf-8");
            multipart.addBodyPart(htmlBodyPart);

            // Phần tệp đính kèm
            BodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            // Lấy tên tệp từ đường dẫn
            File file = new File(filePath);
            attachmentBodyPart.setFileName(file.getName());
            multipart.addBodyPart(attachmentBodyPart);

            // Gán nội dung multipart cho message
            message.setContent(multipart);

            // Gửi email
            Transport.send(message);
            System.out.println("Email with HTML content and attachment sent successfully to " + toEmail);
        } catch (MessagingException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Email email = new Email();
        // Gửi email dạng văn bản thuần
        //email.sendEmail("anhdhhe186569@fpt.edu.vn", "Test Subject", "This is a test email!");
       // String uriPath = "C:/Users/sonNH/Downloads/NguyenHoangSon_BackEndIntern.pdf";

        // Ví dụ gửi email với nội dung HTML và đính kèm tệp (bỏ comment khi cần sử dụng)
        //email.sendEmailWithHtmlAndAttachment("quenmk249@gmail.com", "HTML Email with Attachment","<h1>Tiêu đề Email</h1><p>Nội dung email với <strong>định dạng HTML</strong>.</p>", uriPath);
    }
}
