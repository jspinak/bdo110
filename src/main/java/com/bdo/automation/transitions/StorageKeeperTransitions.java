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

@TransitionSet(state = StorageKeeperState.class)
@RequiredArgsConstructor
@Slf4j
public class StorageKeeperTransitions {

    private final StorageKeeperState storageKeeperState;
    private final Action action;

    @OutgoingTransition(to = StorageState.class, priority = 1)
    public boolean toStorage() {
        log.info("Navigating from StorageKeeper to Storage");

        TypeOptions typeOptions = new TypeOptions.Builder()
            .setPauseBeforeBegin(0.5)
            .setPauseAfterEnd(1.5)
            .build();

        // Press '2' for storage
        return action.type("2", typeOptions).isSuccess();
    }

    @OutgoingTransition(to = MainScreenState.class, priority = 2)
    public boolean toMainScreen() {
        log.info("Navigating from StorageKeeper to MainScreen");

        TypeOptions typeOptions = new TypeOptions.Builder()
            .setPauseBeforeBegin(0.5)
            .setPauseAfterEnd(1.0)
            .build();

        // Press ESC to close - using SikuliX Key constant
        return action.type(Key.ESC, typeOptions).isSuccess();
    }

    @IncomingTransition
    public boolean verifyArrival() {
        log.info("Verifying arrival at StorageKeeper");

        PatternFindOptions findOptions = new PatternFindOptions.Builder()
            .setWaitTime(5.0)
            .setPauseAfterEnd(0.5)
            .build();

        boolean found = action.find(storageKeeperState.getStorageKeeperDialog(), findOptions).isSuccess();

        if (found) {
            log.info("Successfully arrived at StorageKeeper");
        } else {
            log.error("Failed to verify arrival at StorageKeeper");
        }

        return found;
    }
}