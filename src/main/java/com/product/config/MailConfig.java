package com.product.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender getMailConfig() {
        JavaMailSenderImpl emailConfig = new JavaMailSenderImpl();

        // Set Mail Server
        emailConfig.setHost("localhost");  // Change to your SMTP host
        emailConfig.setPort(25);                  // Change if needed

        // Set credentials
        emailConfig.setUsername("ViratGowri@gs.comm");
        emailConfig.setPassword("1234");

        // Set mail properties
        Properties props = emailConfig.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return emailConfig;
    }
}


