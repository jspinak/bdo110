package com.bdo.automation;

import com.bdo.automation.states.BlackSpiritsAdventureState;
import com.bdo.automation.states.MainScreenState;
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

    private final BlackSpiritsAdventureState blackSpiritsAdventureState;
    private final StateNavigator navigation;
    private final Action action;
    private final MainScreenState mainScreenState;

    public BdoAutomationRunner(BlackSpiritsAdventureState blackSpiritsAdventureState, MainScreenState mainScreenState,
                               StateNavigator navigation, Action action) {
        this.mainScreenState = mainScreenState;
        this.blackSpiritsAdventureState = blackSpiritsAdventureState;
        this.navigation = navigation;
        this.action = action;
    }

    @Override
    public void run(String... args) throws Exception {
        if (navigation.openState("BlackSpiritsAdventure"))
            action.click(blackSpiritsAdventureState.getAbholen());

        if (action.click(mainScreenState.getFisher().toObjectCollection()).isSuccess())
            action.type(mainScreenState.getGo().toObjectCollection());
    }
}