package com.bdo.automation.states;

import io.github.jspinak.brobot.primatives.region.Region;
import io.github.jspinak.brobot.state.annotations.State;
import io.github.jspinak.brobot.stateStructure.model.state.StateImage;
import io.github.jspinak.brobot.model.state.StateString;
import io.github.jspinak.brobot.tools.testing.mock.history.MockActionHistoryFactory;
import org.sikuli.script.Key;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Inventory window state
 * State classes are pure data containers - ONLY objects, NO methods
 */
@State
@Getter
@Slf4j
public class InventoryState {
    // State name is automatically derived: "Inventory"

    private final StateImage inventoryWindow;
    private final StateImage searchField;
    private final StateString close;  // Special key: ESC

    public InventoryState() {
        log.info("Initializing InventoryState");

        // Define regions for mock mode (optional)
        Region windowRegion = new Region(1300, 200, 520, 680);
        Region searchRegion = new Region(1350, 250, 200, 30);

        // Inventory window identifier
        inventoryWindow = new StateImage.Builder()
            .addPatterns("inventory/inventory")
            .setName("Inventory Window")
            .withActionHistory(MockActionHistoryFactory.reliableButton(windowRegion))
            .build();

        // Search field
        searchField = new StateImage.Builder()
            .addPatterns("inventory/search-inventory")
            .setName("Search Field")
            .withActionHistory(MockActionHistoryFactory.reliableButton(searchRegion))
            .build();

        // Special key for pressing Escape to close
        close = new StateString.Builder()
            .withString(Key.ESC)  // Uses SikuliX Key.ESC constant
            .setName("Escape Key")
            .build();
    }
}