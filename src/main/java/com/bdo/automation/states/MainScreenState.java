package com.bdo.automation.states;

import io.github.jspinak.brobot.primatives.region.Region;
import io.github.jspinak.brobot.state.annotations.State;
import io.github.jspinak.brobot.stateStructure.model.state.StateImage;
import io.github.jspinak.brobot.tools.testing.mock.history.MockActionHistoryFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Main screen state - the primary game interface
 * State classes are pure data containers - ONLY objects, NO methods
 * @State annotation includes @Component - don't add it separately
 */
@State(initial = true)  // This is the initial state when automation starts
@Getter
@Slf4j
public class MainScreenState {
    // State name is automatically derived from class name: "MainScreen"

    // StateImages for UI elements - ONLY objects, NO methods
    private final StateImage die;
    private final StateImage processing;
    private final StateImage inventory;
    private final StateImage talk;

    public MainScreenState() {
        log.info("Initializing MainScreenState");

        // Define regions for mock mode (optional - only if you want to test without real GUI)
        Region dieRegion = new Region(100, 100, 50, 50);
        Region processingRegion = new Region(200, 100, 80, 40);
        Region inventoryRegion = new Region(300, 100, 80, 40);
        Region talkRegion = new Region(400, 100, 80, 40);

        // Die icon for Black Spirit's Adventure
        die = new StateImage.Builder()
            .addPatterns("main-screen/die")
            .setName("Die Icon")
            // ActionHistory is OPTIONAL - only needed for mock mode testing
            .withActionHistory(MockActionHistoryFactory.reliableButton(dieRegion))
            .build();

        // Processing indicator
        processing = new StateImage.Builder()
            .addPatterns("main-screen/processing")
            .setName("Processing")
            .withActionHistory(MockActionHistoryFactory.reliableButton(processingRegion))
            .build();

        // Inventory indicator
        inventory = new StateImage.Builder()
            .addPatterns("main-screen/inventory")
            .setName("Inventory")
            .withActionHistory(MockActionHistoryFactory.reliableButton(inventoryRegion))
            .build();

        // Talk indicator
        talk = new StateImage.Builder()
            .addPatterns("main-screen/talk")
            .setName("Talk")
            .withActionHistory(MockActionHistoryFactory.reliableButton(talkRegion))
            .build();
    }
    // NO METHODS HERE - States are data containers only!
}