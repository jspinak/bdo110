package com.bdo.automation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import io.github.jspinak.brobot.logging.config.LoggingAutoConfiguration;

/**
 * Logging configuration for BDO Automation.
 *
 * This configuration imports Brobot's logging system which provides:
 * - ActionLoggingService for enhanced action logging
 * - ActionSessionManager for session correlation
 * - ActionLogFormatter for consistent formatting
 *
 * The actual logging configuration is done through application.properties
 * using the brobot.logging.* properties.
 */
@Configuration
@Import(LoggingAutoConfiguration.class)
public class LoggingConfig {
    // All logging configuration is handled through application.properties
    // and Brobot's LoggingAutoConfiguration
}