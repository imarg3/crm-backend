package org.code.bluetick.registration.listener;

import lombok.AllArgsConstructor;
import org.code.bluetick.persistence.model.Mail;
import org.code.bluetick.persistence.model.User;
import org.code.bluetick.registration.OnRegistrationCompleteEvent;
import org.code.bluetick.service.MailService;
import org.code.bluetick.service.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegisterListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private final UserService userService;

    private final MailService mailSender;

    private final Environment environment;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        System.out.println("Event received : User - " + event.getUser().getEmail() + ", AppURL - " + event.getAppUrl() + ", Locale -" + event.getLocale());
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final User user = event.getUser();
        System.out.println("User is : " + user.getEmail());

        final String subject = "Registration Confirmation";
        final String message = "You registered successfully. To confirm your registration, please click on the below link.";
        final String token = "token123";
        final String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;

        Mail mail = new Mail();
        mail.setMailFrom(environment.getProperty("support.email"));
        mail.setMailTo(user.getEmail());
        mail.setMailSubject(subject);
        mail.setMailContent(message + " \r\n" + confirmationUrl);

        mailSender.sendEmail(mail);
    }
}
