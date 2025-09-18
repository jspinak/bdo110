package com.bdo.automation.transitions;

import com.bdo.automation.states.*;
import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.action.basic.find.PatternFindOptions;
import io.github.jspinak.brobot.annotations.IncomingTransition;
import io.github.jspinak.brobot.annotations.OutgoingTransition;
import io.github.jspinak.brobot.annotations.TransitionSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@TransitionSet(state = StorageState.class)
@RequiredArgsConstructor
@Slf4j
public class StorageTransitions {

    private final StorageState storageState;
    private final Action action;

    @OutgoingTransition(to = MainScreenState.class, priority = 1)
    public boolean toMainScreen() {
        log.info("Navigating from Storage to MainScreen");
        return action.type(storageState.getClose().toObjectCollection()).isSuccess();
    }

    @IncomingTransition
    public boolean verifyArrival() {
        PatternFindOptions findOptions = new PatternFindOptions.Builder()
            .setSearchDuration(5.0)
            .setPauseAfterEnd(0.5)
            .withSuccessLog("Successfully arrived at Storage")
            .withFailureLog("Failed to verify arrival at Storage")
            .withBeforeActionLog("Verifying arrival at Storage")
            .build();

        return action.perform(findOptions, storageState.getStorageWindow()).isSuccess();
    }
}