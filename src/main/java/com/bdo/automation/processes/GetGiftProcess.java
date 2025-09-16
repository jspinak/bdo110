package com.bdo.automation.processes;

import com.bdo.automation.states.BlackSpiritsAdventureState;
import com.bdo.automation.states.MainScreenState;
import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.action.basic.click.ClickOptions;
import io.github.jspinak.brobot.navigation.Navigation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Automation process to collect Black Spirit's Adventure rewards
 * Process:
 * 1. Navigate to Black Spirit's Adventure from Main Screen
 * 2. Click "abholen" button to collect reward
 * 3. Navigate back to Main Screen
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetGiftProcess {

    private final Navigation navigation;
    private final Action action;
    private final BlackSpiritsAdventureState blackSpiritsAdventureState;

    /**
     * Execute the getGift automation process
     * @return true if the gift was successfully collected
     */
    public boolean execute() {
        log.info("Starting GetGift process");

        try {
            // Step 1: Navigate to Black Spirit's Adventure
            log.info("Step 1: Navigating to Black Spirit's Adventure");
            if (!navigation.openState("BlackSpiritsAdventure")) {
                log.error("Failed to navigate to Black Spirit's Adventure");
                return false;
            }

            // Step 2: Click abholen button to collect reward
            log.info("Step 2: Collecting reward");

            ClickOptions clickOptions = new ClickOptions.Builder()
                .setPauseBeforeBegin(1.0)
                .setPauseAfterEnd(2.0)
                .build();

            if (!action.click(blackSpiritsAdventureState.getAbholen(), clickOptions).isSuccess()) {
                log.warn("Failed to click abholen button - reward might not be available");
                // Continue anyway to close the window
            } else {
                log.info("Reward collection attempted");
            }

            // Step 3: Navigate back to Main Screen
            log.info("Step 3: Returning to Main Screen");
            if (!navigation.openState("MainScreen")) {
                log.warn("Failed to navigate back to Main Screen");
                return false;
            }

            log.info("GetGift process completed successfully");
            return true;

        } catch (Exception e) {
            log.error("Unexpected error during GetGift process", e);
            return false;
        }
    }

    /**
     * Execute the process with retry logic
     * @param maxRetries maximum number of retry attempts
     * @return true if the gift was successfully collected
     */
    public boolean executeWithRetry(int maxRetries) {
        log.info("Starting GetGift process with {} max retries", maxRetries);

        for (int i = 0; i <= maxRetries; i++) {
            if (i > 0) {
                log.info("Retry attempt {}/{}", i, maxRetries);
            }

            if (execute()) {
                return true;
            }

            if (i < maxRetries) {
                log.info("Waiting before retry...");
                // Use pause in action config instead of Thread.sleep
                ClickOptions pauseOptions = new ClickOptions.Builder()
                    .setPauseBeforeBegin(3.0)
                    .build();
                // Perform a dummy action just for the pause
                action.click(null, pauseOptions);
            }
        }

        log.error("GetGift process failed after {} attempts", maxRetries + 1);
        return false;
    }
}