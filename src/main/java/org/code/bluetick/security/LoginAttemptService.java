package org.code.bluetick.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@CacheConfig(cacheNames = "loginAttempts")
public class LoginAttemptService {
    public static final int MAX_ATTEMPT = 10;

    private final HttpServletRequest request;

    @Cacheable
    public void loginFailed(final String key) {
        int attempts;
        attempts = 5;
    }
}
