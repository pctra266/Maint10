package Email;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

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
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Email email = new Email();
        email.sendEmail("anhdhhe186569@fpt.edu.vn", "Test Subject", "This is a test email!");
    }
}
