package com.product.helper;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Component
public class MailMessenger {

    private static JavaMailSender sender;

    public MailMessenger(JavaMailSender sender) {
        this.sender = sender;
    }

    public static void htmlEmailMessenge(String from, String to, String subject, String body) throws MessagingException {
        if (from == null || to == null || subject == null || body == null) {
            throw new IllegalArgumentException("Email parameters must not be null");
        }
        
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper htmlMessage = new MimeMessageHelper(message, true);

        htmlMessage.setFrom(from);
        htmlMessage.setTo(to);
        htmlMessage.setSubject(subject);
        htmlMessage.setText(body, true);  // true means HTML content

        sender.send(message);
    }
}
