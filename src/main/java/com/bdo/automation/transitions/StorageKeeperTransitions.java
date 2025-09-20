package com.bdo.automation.transitions;

import com.bdo.automation.states.*;
import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.action.basic.find.PatternFindOptions;
import io.github.jspinak.brobot.annotations.IncomingTransition;
import io.github.jspinak.brobot.annotations.OutgoingTransition;
import io.github.jspinak.brobot.annotations.TransitionSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@TransitionSet(state = StorageKeeperState.class)
@RequiredArgsConstructor
@Slf4j
public class StorageKeeperTransitions {

    private final StorageKeeperState storageKeeperState;
    private final Action action;

    @OutgoingTransition(activate = {StorageState.class})
    public boolean toStorage() {
        log.info("Navigating from StorageKeeper to Storage");
        return action.type(storageKeeperState.getStorage().toObjectCollection()).isSuccess();
    }

    @OutgoingTransition(activate = {MainScreenState.class})
    public boolean toMainScreen() {
        log.info("Navigating from StorageKeeper to MainScreen");
        return action.type(storageKeeperState.getClose().toObjectCollection()).isSuccess();
    }

    @IncomingTransition
    public boolean verifyArrival() {
        PatternFindOptions findOptions = new PatternFindOptions.Builder()
            .setSearchDuration(5.0)
            .setPauseAfterEnd(0.5)
            .withSuccessLog("Successfully arrived at StorageKeeper")
            .withFailureLog("Failed to verify arrival at StorageKeeper")
            .withBeforeActionLog("Verifying arrival at StorageKeeper")
            .build();

        return action.perform(findOptions, storageKeeperState.getStorageKeeperDialog()).isSuccess();
    }
}