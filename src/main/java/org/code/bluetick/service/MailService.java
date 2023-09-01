package org.code.bluetick.service;

import org.code.bluetick.persistence.model.Mail;

public interface MailService {
    void sendEmail(Mail mail);
}
