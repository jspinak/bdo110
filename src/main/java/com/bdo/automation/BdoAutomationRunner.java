package com.bdo.automation;

import com.bdo.automation.instructions.CornProcessor;
import com.bdo.automation.states.BlackSpiritsAdventureState;
import com.bdo.automation.states.MainScreenState;
import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.navigation.transition.StateNavigator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Simple BDO automation runner
 * Opens Black Spirit's Adventure and clicks the Abholen button
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BdoAutomationRunner implements CommandLineRunner {

    private final BlackSpiritsAdventureState blackSpiritsAdventureState;
    private final StateNavigator navigation;
    private final Action action;
    private final MainScreenState mainScreenState;
    private final CornProcessor cornProcessor;

    @Override
    public void run(String... args) throws Exception {
        log.info("BdoAutomationRunner starting...");

        // Wait a bit for all states to be initialized
        Thread.sleep(2000);

        log.info("Starting BDO Automation...");

        try {
            log.info("Running corn processor...");
            cornProcessor.makeCornFlour();
            log.info("Corn processor completed successfully");
        } catch (Exception e) {
            log.error("Failed to run corn processor: {}", e.getMessage(), e);
            log.error("Make sure the game is running and on the main screen");
        }

        // Keep application running
        log.info("BDO Automation is running. Press Ctrl+C to stop.");
        Thread.currentThread().join();
    }
}
