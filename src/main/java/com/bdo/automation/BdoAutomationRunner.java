package com.bdo.automation;

import com.bdo.automation.states.BlackSpiritsAdventureState;
import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.navigation.transition.StateNavigator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Simple BDO automation runner
 * Opens Black Spirit's Adventure and clicks the Abholen button
 */
@Slf4j
@Component
@Profile("!test") // Don't run in test profile
public class BdoAutomationRunner implements CommandLineRunner {

    @Autowired
    private StateNavigator navigation;

    @Autowired
    private Action action;

    @Autowired
    private BlackSpiritsAdventureState blackSpiritsAdventureState;

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting BDO Automation - Black Spirit's Adventure");

        // Open Black Spirit's Adventure state
        log.info("Opening Black Spirit's Adventure...");
        boolean opened = navigation.openState("BlackSpiritsAdventure");

        if (opened) {
            log.info("Successfully opened Black Spirit's Adventure");
            boolean clicked = action.click(blackSpiritsAdventureState.getAbholen()).isSuccess();

            if (clicked) {
                log.info("Gift collection completed!");
            } else {
                log.warn("Could not click Abholen button");
            }
        } else {
            log.error("Failed to open Black Spirit's Adventure");
        }

        log.info("BDO Automation completed");
    }
}