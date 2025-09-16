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

@TransitionSet(state = InventoryState.class)
@RequiredArgsConstructor
@Slf4j
public class InventoryTransitions {

    private final InventoryState inventoryState;
    private final Action action;

    @OutgoingTransition(to = MainScreenState.class, priority = 1)
    public boolean toMainScreen() {
        log.info("Navigating from Inventory to MainScreen");

        TypeOptions typeOptions = new TypeOptions.Builder()
            .setPauseBeforeBegin(0.5)
            .setPauseAfterEnd(1.0)
            .build();

        // Press ESC to close - using SikuliX Key constant
        return action.type(Key.ESC, typeOptions).isSuccess();
    }

    @IncomingTransition
    public boolean verifyArrival() {
        log.info("Verifying arrival at Inventory");

        PatternFindOptions findOptions = new PatternFindOptions.Builder()
            .setWaitTime(5.0)
            .setPauseAfterEnd(0.5)
            .build();

        boolean found = action.find(inventoryState.getInventoryWindow(), findOptions).isSuccess();

        if (found) {
            log.info("Successfully arrived at Inventory");
        } else {
            log.error("Failed to verify arrival at Inventory");
        }

        return found;
    }
}