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
@Profile("!test") // Don't run in test profile
public class BdoAutomationRunner implements CommandLineRunner {

    private final BlackSpiritsAdventureState blackSpiritsAdventureState;
    private final StateNavigator navigation;
    private final Action action;
    private final MainScreenState mainScreenState;
    private final CornProcessor cornProcessor;

    @Override
    public void run(String... args) throws Exception {
        cornProcessor.makeCornFlour();
    }
}
