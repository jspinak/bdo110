package com.bdo.automation.states;

import io.github.jspinak.brobot.annotations.State;
import io.github.jspinak.brobot.model.element.Region;
import io.github.jspinak.brobot.model.state.StateImage;
import io.github.jspinak.brobot.model.state.StateString;
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
    private final StateImage fisher;
    private final StateString processing;
    private final StateString inventory;
    private final StateString talk;
    private final StateString go;

    public MainScreenState() {
        log.info("Initializing MainScreenState");

        // Define regions for mock mode (optional - only if you want to test without real GUI)
        Region dieRegion = new Region(100, 100, 50, 50);

        // Die icon for Black Spirit's Adventure
        die = new StateImage.Builder()
            .addPatterns("main-screen/die")
            .setName("Die Icon")
            // ActionHistory is OPTIONAL - only needed for mock mode testing
            .withActionHistory(MockActionHistoryFactory.reliableButton(dieRegion))
            .build();

        fisher = new StateImage.Builder()
                .addPatterns("main-screen/npc-fisher")
                .setName("Fisher NPC")
                .build();

        processing = new StateString.Builder()
                .setString("l")
                .setName("open processing")
                .build();

        inventory = new StateString.Builder()
                .setString("i")
                .setName("open inventory")
                .build();

        talk = new StateString.Builder()
                .setString("r")
                .setName("open NPC menu")
                .build();

        go = new StateString.Builder()
                .setString("t")
                .setName("go to location")
                .build();
    }
}