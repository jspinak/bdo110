package com.bdo.automation.instructions;

import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.action.basic.find.PatternFindOptions;
import io.github.jspinak.brobot.action.basic.click.ClickOptions;
import io.github.jspinak.brobot.logging.correlation.ActionSessionManager;
import io.github.jspinak.brobot.model.state.StateImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Example showing how to use Brobot's enhanced logging features.
 *
 * This demonstrates:
 * - Using transparent action logging controlled by application.properties
 * - Session management for workflow tracking with ActionSessionManager
 * - Integration with ActionConfig custom logging messages
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessingWorkflowExample {

    private final Action action; // Standard Brobot action with transparent logging
    private final ActionSessionManager sessionManager; // Session correlation manager

    /**
     * Example 1: Simple action with automatic transparent logging
     */
    public void simpleActionExample(StateImage button) {
        // Logging happens transparently based on application.properties
        // No special logging service needed!
        action.click(button);
        // If logging is enabled in properties, it automatically logs:
        // → CLICK button
        // ✓ CLICK button | loc:(520,380) | sim:0.92 | 45ms
    }

    /**
     * Example 2: Complex workflow with session tracking
     * Actions are automatically tracked - no need for nextAction() calls!
     */
    public void processItemsWorkflow(StateImage inventoryButton, StateImage processingButton,
                                     StateImage itemSlot, StateImage processAllButton) {
        // Start a session - all actions within are automatically tracked
        sessionManager.startSession("Process Items Workflow");

        try {
            // Each action is automatically assigned a sequence number
            action.click(inventoryButton);    // Automatically seq:001
            action.click(processingButton);   // Automatically seq:002
            action.click(itemSlot);          // Automatically seq:003
            action.click(processAllButton);   // Automatically seq:004

            log.info("Items processing started successfully");

        } finally {
            // End session - logs summary with total actions
            sessionManager.endSession();
            // Logs: === Completed Task: Process Items Workflow | Session: abc123 | Total Actions: 4 ===
        }
    }

    /**
     * Example 3: Using ActionConfig custom logging messages
     */
    public void verifyStateArrival(StateImage stateHeader) {
        // Create options with custom logging messages
        PatternFindOptions findOptions = new PatternFindOptions.Builder()
            .withBeforeActionLog("Verifying arrival at Processing state...")
            .withSuccessLog("Successfully confirmed Processing state")
            .withFailureLog("WARNING: Not at Processing state - may need to navigate")
            .setSimilarity(0.85)
            .build();

        // Transparent logging will include both standard and custom messages
        action.perform(findOptions, stateHeader);
        // If logging is enabled, it automatically logs:
        // Verifying arrival at Processing state...
        // → FIND stateHeader
        // ✓ FIND stateHeader | loc:(100,50) | sim:0.87 | 150ms
        // Successfully confirmed Processing state
    }

    /**
     * Example 4: Automatic session management with lambda
     */
    public void automaticSessionExample(StateImage saveButton) {
        // Session is automatically started and ended, even if exception occurs
        sessionManager.executeWithSession("Save Configuration", () -> {
            // All actions within are tracked in the session
            PatternFindOptions verifyOptions = new PatternFindOptions.Builder()
                .withBeforeActionLog("Looking for save button...")
                .build();

            action.perform(verifyOptions, saveButton);  // Automatically seq:001

            ClickOptions clickOptions = new ClickOptions.Builder()
                .withBeforeActionLog("Clicking save...")
                .withSuccessLog("Configuration saved!")
                .build();

            action.perform(clickOptions, saveButton);   // Automatically seq:002
        });
    }

    /**
     * Example 5: Session management with transparent logging
     */
    public void mixedExample(StateImage target) {
        // Regular action without session - transparent logging still happens
        action.find(target);

        // Use session manager when you want correlated logging
        sessionManager.startSession("Critical Operation");
        action.click(target); // Automatically tracked as seq:001
        sessionManager.endSession();
    }
}