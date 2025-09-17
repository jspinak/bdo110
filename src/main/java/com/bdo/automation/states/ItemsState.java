package com.bdo.automation.states;

import io.github.jspinak.brobot.annotations.State;
import io.github.jspinak.brobot.model.element.Region;
import io.github.jspinak.brobot.model.state.StateImage;
import io.github.jspinak.brobot.tools.testing.mock.history.MockActionHistoryFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Items state - represents various item patterns
 * State classes are pure data containers - ONLY objects, NO methods
 */
@State
@Getter
@Slf4j
public class ItemsState {
    // State name is automatically derived: "Items"

    private final StateImage corn;
    private final StateImage cornSearched;

    public ItemsState() {
        log.info("Initializing ItemsState");

        // Define regions for mock mode (optional)
        Region cornRegion = new Region(1400, 350, 50, 50);
        Region cornSearchedRegion = new Region(1400, 350, 50, 50);

        // Corn item
        corn = new StateImage.Builder()
            .addPatterns("items/corn")
            .setName("Corn Item")
            .withActionHistory(MockActionHistoryFactory.reliableButton(cornRegion))
            .build();

        // Corn item when searched
        cornSearched = new StateImage.Builder()
            .addPatterns("items/corn-searched")
            .setName("Corn Searched")
            .withActionHistory(MockActionHistoryFactory.reliableButton(cornSearchedRegion))
            .build();
    }
}