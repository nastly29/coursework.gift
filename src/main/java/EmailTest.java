
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailTest {
    public static void main(String[] args) {
        String to = "anastasiadavid312@gmail.com";
        String from = "nastiadavid76@gmail.com";
        String host = "smtp.gmail.com";
        String username = "nastiadavid76@gmail.com";
        String password = "hmyv dnhb hfki enbn";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("[TEST] Logback SMTP Test");
            message.setText("This is a test email from Logback SMTPAppender.");

            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}