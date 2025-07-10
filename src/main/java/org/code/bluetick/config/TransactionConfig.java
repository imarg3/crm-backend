package org.code.bluetick.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    // Transaction management is enabled and will use Spring's default settings
    // Read-only transactions are properly configured in service methods
    // Rollback policies are set to rollback on all unchecked exceptions
} 