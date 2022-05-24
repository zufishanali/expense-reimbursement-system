package com.revature.emailApi.services;

import com.revature.emailApi.dtos.EmailDTO;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;


/**
 * Send Email Example:
 *      https://www.tutorialspoint.com/spring_boot/spring_boot_sending_email.htm
 */


@Service
public class EmailService {

    public String sendEmail(EmailDTO emailRequest) throws MessagingException {
        System.out.println("Email API: Received request to send email.");

        String emailBody = emailRequest.getSubject() + "\n\n" + "Reimbursement Id: " + emailRequest.getReimbursementId();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("zali.projects22@gmail.com", "somepassword!");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("zali.projects22@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("zali.projects22@gmail.com")); // this would become: emailRequest.getEmail()
        msg.setSubject(emailRequest.getSubject());
        msg.setContent(emailBody, "text/html");
        msg.setSentDate(new Date());
        Transport.send(msg);

        return "Responding from email-api: Email successfully sent.";
    }
}
