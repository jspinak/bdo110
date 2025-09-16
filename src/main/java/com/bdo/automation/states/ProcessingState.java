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
 * Processing window state
 * State classes are pure data containers - ONLY objects, NO methods
 */
@State
@Getter
@Slf4j
public class ProcessingState {
    // State name is automatically derived: "Processing"

    private final StateImage processingWindow;
    private final StateImage massProductionButton;
    private final StateImage grindButton;
    private final StateString close;  // Special key: ESC

    public ProcessingState() {
        log.info("Initializing ProcessingState");

        // Define regions for mock mode (optional)
        Region windowRegion = new Region(700, 300, 520, 480);
        Region massProductionRegion = new Region(750, 400, 120, 40);
        Region grindRegion = new Region(900, 400, 120, 40);

        // Processing window identifier
        processingWindow = new StateImage.Builder()
            .addPatterns("processing/processing")
            .setName("Processing Window")
            .withActionHistory(MockActionHistoryFactory.reliableButton(windowRegion))
            .build();

        // Mass Production button
        massProductionButton = new StateImage.Builder()
            .addPatterns("processing/mass-production")
            .setName("Mass Production Button")
            .withActionHistory(MockActionHistoryFactory.reliableButton(massProductionRegion))
            .build();

        // Grind button
        grindButton = new StateImage.Builder()
            .addPatterns("processing/grind")
            .setName("Grind Button")
            .withActionHistory(MockActionHistoryFactory.reliableButton(grindRegion))
            .build();

        // Special key for pressing Escape to close
        close = new StateString.Builder()
            .withString(Key.ESC)  // Uses SikuliX Key.ESC constant
            .setName("Escape Key")
            .build();
    }
}