package com.bdo.automation.transitions;

import com.bdo.automation.states.*;
import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.action.basic.find.PatternFindOptions;
import io.github.jspinak.brobot.annotations.IncomingTransition;
import io.github.jspinak.brobot.annotations.OutgoingTransition;
import io.github.jspinak.brobot.annotations.TransitionSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@TransitionSet(state = BlackSpiritsAdventureState.class)
@RequiredArgsConstructor
@Slf4j
public class BlackSpiritsAdventureTransitions {

    private final BlackSpiritsAdventureState blackSpiritsAdventureState;
    private final Action action;

    @OutgoingTransition(activate = {MainScreenState.class})
    public boolean toMainScreen() {
        return action.type(blackSpiritsAdventureState.getClose().toObjectCollection()).isSuccess();
    }

    @IncomingTransition
    public boolean verifyArrival() {
        PatternFindOptions findOptions = new PatternFindOptions.Builder()
            .withBeforeActionLog("Verifying Black Spirit's Adventure window...")
            .withSuccessLog("Black Spirit's Adventure is open")
            .withFailureLog("Black Spirit's Adventure not detected")
            .setSearchDuration(5.0)
            .setPauseAfterEnd(0.5)
            .build();

        return action.perform(findOptions, blackSpiritsAdventureState.getAbholen()).isSuccess();
    }
}