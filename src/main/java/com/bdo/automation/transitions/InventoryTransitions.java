package com.bdo.automation.transitions;

import com.bdo.automation.states.*;
import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.action.basic.find.PatternFindOptions;
import io.github.jspinak.brobot.annotations.IncomingTransition;
import io.github.jspinak.brobot.annotations.OutgoingTransition;
import io.github.jspinak.brobot.annotations.TransitionSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@TransitionSet(state = InventoryState.class)
@RequiredArgsConstructor
@Slf4j
public class InventoryTransitions {

    private final InventoryState inventoryState;
    private final Action action;

    @OutgoingTransition(activate = {MainScreenState.class})
    public boolean toMainScreen() {
        log.info("Navigating from Inventory to MainScreen");
        return action.type(inventoryState.getClose().toObjectCollection()).isSuccess();
    }

    @IncomingTransition
    public boolean verifyArrival() {
        PatternFindOptions findOptions = new PatternFindOptions.Builder()
            .setSearchDuration(5.0)
            .setPauseAfterEnd(0.5)
            .withSuccessLog("Successfully arrived at Inventory")
            .withFailureLog("Failed to verify arrival at Inventory")
            .withBeforeActionLog("Verifying arrival at Inventory")
            .build();

        return action.perform(findOptions, inventoryState.getInventoryWindow()).isSuccess();
    }
}