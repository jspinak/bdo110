package com.bdo.automation.transitions;

import com.bdo.automation.states.*;
import io.github.jspinak.brobot.action.Action;
import io.github.jspinak.brobot.action.basic.find.PatternFindOptions;
import io.github.jspinak.brobot.state.annotations.IncomingTransition;
import io.github.jspinak.brobot.state.annotations.OutgoingTransition;
import io.github.jspinak.brobot.state.annotations.TransitionSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * TransitionSet for ItemsState - handles all transitions for the Items state
 * Note: ItemsState is more of a utility state for item patterns
 * Transition classes have ONLY methods, no state objects
 */
@TransitionSet(state = ItemsState.class)
@RequiredArgsConstructor
@Slf4j
public class ItemsTransitions {

    // Only inject the state this TransitionSet manages and Action service
    private final ItemsState itemsState;
    private final Action action;

    // Items state typically doesn't navigate to other states directly
    // It's used as a pattern repository for item recognition

    @IncomingTransition
    public boolean verifyArrival() {
        log.info("Verifying arrival at Items");

        PatternFindOptions findOptions = new PatternFindOptions.Builder()
            .setWaitTime(5.0)
            .setPauseAfterEnd(0.5)
            .build();

        // Check if any item patterns are visible
        boolean found = action.find(itemsState.getCorn(), findOptions).isSuccess() ||
                       action.find(itemsState.getCornSearched(), findOptions).isSuccess();

        if (found) {
            log.info("Successfully found item patterns");
        } else {
            log.info("No item patterns currently visible");
        }

        return found;
    }
}