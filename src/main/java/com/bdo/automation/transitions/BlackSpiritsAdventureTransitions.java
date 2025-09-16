package com.bdo.automation.transitions;

import com.bdo.automation.states.*;
import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.action.basic.find.PatternFindOptions;
import io.github.jspinak.brobot.action.basic.type.TypeOptions;
import io.github.jspinak.brobot.state.annotations.IncomingTransition;
import io.github.jspinak.brobot.state.annotations.OutgoingTransition;
import io.github.jspinak.brobot.state.annotations.TransitionSet;
import org.sikuli.script.Key;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@TransitionSet(state = BlackSpiritsAdventureState.class)
@RequiredArgsConstructor
@Slf4j
public class BlackSpiritsAdventureTransitions {

    private final BlackSpiritsAdventureState blackSpiritsAdventureState;
    private final Action action;

    @OutgoingTransition(to = MainScreenState.class, priority = 1)
    public boolean toMainScreen() {
        TypeOptions typeOptions = new TypeOptions.Builder()
            .withBeforeActionLog("Closing Black Spirit's Adventure...")
            .withSuccessLog("Black Spirit's Adventure closed")
            .withFailureLog("Failed to close Black Spirit's Adventure")
            .setPauseBeforeBegin(0.5)
            .setPauseAfterEnd(1.0)
            .build();

        // Press ESC to close - using SikuliX Key constant
        return action.type(Key.ESC, typeOptions).isSuccess();
    }

    @IncomingTransition
    public boolean verifyArrival() {
        PatternFindOptions findOptions = new PatternFindOptions.Builder()
            .withBeforeActionLog("Verifying Black Spirit's Adventure window...")
            .withSuccessLog("Black Spirit's Adventure is open")
            .withFailureLog("Black Spirit's Adventure not detected")
            .setWaitTime(5.0)
            .setPauseAfterEnd(0.5)
            .build();

        // Only check for Abholen button to verify arrival (close is now ESC key, not a visible button)
        boolean found = action.find(blackSpiritsAdventureState.getAbholen(), findOptions).isSuccess();

        return found;
    }
}