package org.code.bluetick.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.code.bluetick.persistence.model.Mail;
import org.code.bluetick.web.exception.SendMailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(Mail mail) {
        try {
            log.info("Sending email to: {} with subject: {}", mail.getMailTo(), mail.getMailSubject());

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setSubject(mail.getMailSubject());
            helper.setFrom(mail.getMailFrom());
            helper.setTo(mail.getMailTo());
            helper.setText(mail.getMailContent(), true);
            
            mailSender.send(mimeMessage);
            log.info("Email sent successfully to: {}", mail.getMailTo());
        } catch (MessagingException e) {
            log.error("Error sending email to {}: {}", mail.getMailTo(), e.getMessage(), e);
            throw new SendMailException("Failed to send email: " + e.getMessage());
        }
    }
}
