package com.bdo.automation.transitions;

import com.bdo.automation.states.*;
import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.action.basic.find.PatternFindOptions;
import io.github.jspinak.brobot.annotations.IncomingTransition;
import io.github.jspinak.brobot.annotations.OutgoingTransition;
import io.github.jspinak.brobot.annotations.TransitionSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * TransitionSet for AmountState - handles all transitions for the Amount dialog
 * Transition classes have ONLY methods, no state objects
 */
@TransitionSet(state = AmountState.class)
@RequiredArgsConstructor
@Slf4j
public class AmountTransitions {

    private final AmountState amountState;
    private final Action action;

    @OutgoingTransition(activate = {MainScreenState.class})
    public boolean toMainScreen() {
        return action.type(amountState.getClose().toObjectCollection()).isSuccess();
    }

    @IncomingTransition
    public boolean verifyArrival() {
        PatternFindOptions findOptions = new PatternFindOptions.Builder()
            .withBeforeActionLog("Verifying arrival at Amount dialog...")
            .withSuccessLog("Successfully arrived at Amount dialog")
            .withFailureLog("Failed to verify arrival at Amount dialog")
            .setSearchDuration(5.0)
            .setPauseAfterEnd(0.5)
            .build();

        return action.perform(findOptions, amountState.getEingabe()).isSuccess();
    }
}