package com.bdo.automation.transitions;

import com.bdo.automation.states.*;
import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.action.basic.find.PatternFindOptions;
import io.github.jspinak.brobot.annotations.IncomingTransition;
import io.github.jspinak.brobot.annotations.OutgoingTransition;
import io.github.jspinak.brobot.annotations.TransitionSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@TransitionSet(state = ProcessingState.class)
@RequiredArgsConstructor
@Slf4j
public class ProcessingTransitions {

    private final ProcessingState processingState;
    private final Action action;

    @OutgoingTransition(activate = {MainScreenState.class})
    public boolean toMainScreen() {
        return action.type(processingState.getClose().toObjectCollection()).isSuccess();
    }

    @IncomingTransition
    public boolean verifyArrival() {
        PatternFindOptions findOptions = new PatternFindOptions.Builder()
            .setSearchDuration(5.0)
            .setPauseAfterEnd(0.5)
            .withSuccessLog("Successfully arrived at Processing")
            .withFailureLog("Failed to verify arrival at Processing")
            .withBeforeActionLog("Verifying arrival at Processing")
            .build();

        return action.perform(findOptions, processingState.getProcessingWindow()).isSuccess();
    }
}