package com.bdo.automation.instructions;

import com.bdo.automation.states.BlackSpiritsAdventureState;
import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.navigation.transition.StateNavigator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlackSpiritAdventurer {

    private final BlackSpiritsAdventureState blackSpiritsAdventureState;
    private final StateNavigator navigation;
    private final Action action;

    public boolean execute() {
        if (!navigation.openState("BlackSpiritsAdventure")) return false;
        return action.click(blackSpiritsAdventureState.getAbholen()).isSuccess();
    }
}
