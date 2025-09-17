package com.bdo.automation.transitions;

import com.bdo.automation.states.*;
import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.annotations.IncomingTransition;
import io.github.jspinak.brobot.annotations.OutgoingTransition;
import io.github.jspinak.brobot.annotations.TransitionSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@TransitionSet(state = MainScreenState.class)
@RequiredArgsConstructor
@Slf4j
public class MainScreenTransitions {

    private final MainScreenState mainScreenState;
    private final Action action;

    @OutgoingTransition(to = BlackSpiritsAdventureState.class, priority = 1)
    public boolean toBlackSpiritsAdventure() {
        return action.click(mainScreenState.getDie()).isSuccess();
    }

    @OutgoingTransition(to = StorageKeeperState.class, priority = 2)
    public boolean toStorageKeeper() {
        return action.type(mainScreenState.getTalk()).isSuccess();
    }

    @OutgoingTransition(to = ProcessingState.class, priority = 3)
    public boolean toProcessing() {
        return action.type(mainScreenState.getProcessing()).isSuccess();
    }

    @OutgoingTransition(to = InventoryState.class, priority = 4)
    public boolean toInventory() {
        return action.type(mainScreenState.getInventory()).isSuccess();
    }

    @IncomingTransition
    public boolean verifyArrival() {
        return true;
    }
}