package org.code.bluetick.web.requests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.regex.Pattern;

@SpringBootTest(
        classes = {
                LogRequestIdAspect.class,
        }
)
@Slf4j
class LogRequestIdAspectTest {

    @TestConfiguration
    @EnableAspectJAutoProxy
    static class ConfigurationForTesting {
        @Bean
        LogRequestIdAspectTest.AnnotatedTestClass makeAnnotatedRequestIdTestClass() {
            return new LogRequestIdAspectTest.AnnotatedTestClass();
        }
    }

    private static final Pattern pattern = Pattern.compile(".*request-id=\"[0-9a-f\\-]*\".*",
            Pattern.CASE_INSENSITIVE);

    @Autowired
    private LogRequestIdAspectTest.AnnotatedTestClass annotatedTestClass;

    @BeforeEach
    public void init() {
        MDC.clear();
    }

    @Test
    void test() {
        annotatedTestClass.test();
    }

    static class AnnotatedTestClass {
        @LogRequestId
        void test() {
            log.info("Something, doesn't matter!");
        }
    }

}