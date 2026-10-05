package org.code.bluetick.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executors;

/**
 * Java 21 Virtual Threads Configuration.
 *
 * Virtual threads are lightweight threads that dramatically reduce the cost of creating
 * and managing threads. They are ideal for I/O-bound operations like database queries
 * and external API calls - common in CRM applications.
 *
 * Benefits for CRM application:
 * - Handle thousands of concurrent customer requests efficiently
 * - Better resource utilization for database operations
 * - Improved scalability for lead processing
 *
 * Enable with: spring.threads.virtual.enabled=true
 */
@Configuration
@EnableAsync
@ConditionalOnProperty(
    value = "spring.threads.virtual.enabled",
    havingValue = "true",
    matchIfMissing = false
)
@Slf4j
public class VirtualThreadsConfig {

    /**
     * Creates an AsyncTaskExecutor using Java 21 virtual threads.
     * Replaces the default thread pool with virtual threads for better concurrency.
     */
    @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
    public AsyncTaskExecutor asyncTaskExecutor() {
        log.info("Configuring Virtual Threads for async task execution");
        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }

    /**
     * Creates a TaskExecutorBuilder configured for virtual threads.
     */
    @Bean
    public TaskExecutorBuilder taskExecutorBuilder() {
        return new TaskExecutorBuilder()
            .customizers(taskExecutor -> {
                log.info("Virtual Threads enabled for Spring Boot application");
                log.info("This will improve handling of concurrent CRM operations");
            });
    }
}
