package org.code.bluetick.registration.listener;

import org.code.bluetick.persistence.model.User;
import org.code.bluetick.registration.OnRegistrationCompleteEvent;
import org.code.bluetick.service.IUserService;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RegisterListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private final IUserService userService;

    private final JavaMailSender mailSender;

    private final Environment environment;

    public RegisterListener(IUserService userService, JavaMailSender mailSender, Environment environment) {
        this.userService = userService;
        this.mailSender = mailSender;
        this.environment = environment;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        System.out.println("Event received : User - " + event.getUser().getEmail() + ", AppURL - " + event.getAppUrl() + ", Locale -" + event.getLocale());
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final User user = event.getUser();
        System.out.println("User is : " + user.getEmail());

        final SimpleMailMessage email = constructEmailMessage(event, user);
        mailSender.send(email);
    }

    private SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final User user) {
        final String token = "token123";
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        final String message = "You registered successfully. To confirm your registration, please click on the below link.";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(environment.getProperty("support.email"));
        return email;
    }
}
