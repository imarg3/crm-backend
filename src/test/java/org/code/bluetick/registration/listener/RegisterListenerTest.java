package org.code.bluetick.registration.listener;

import org.code.bluetick.persistence.model.User;
import org.code.bluetick.registration.OnRegistrationCompleteEvent;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RegisterListenerTest {
    @Autowired
    private ApplicationEventPublisher testEventPublisher;

    @Test
    @Disabled("we are testing application events")
    void whenPublishingUserRegistrationEvent_thenSendUserRegistrationEmail() {
        User.UserBuilder userBuilder = User.builder();
        User user = userBuilder
                .fullName("Arpit Gupta")
                .businessName("Company")
                .build();
        OnRegistrationCompleteEvent event = new OnRegistrationCompleteEvent(user, Locale.ENGLISH, "");
        testEventPublisher.publishEvent(event);

        // assertThat()
    }
}